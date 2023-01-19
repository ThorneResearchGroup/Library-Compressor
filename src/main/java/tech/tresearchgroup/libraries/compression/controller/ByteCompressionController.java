package tech.tresearchgroup.libraries.compression.controller;

import com.aayushatharva.brotli4j.Brotli4jLoader;
import com.aayushatharva.brotli4j.decoder.BrotliInputStream;
import com.aayushatharva.brotli4j.encoder.BrotliOutputStream;
import com.aayushatharva.brotli4j.encoder.Encoder;
import com.github.luben.zstd.ZstdInputStream;
import com.github.luben.zstd.ZstdOutputStream;
import net.jpountz.lz4.LZ4BlockInputStream;
import net.jpountz.lz4.LZ4BlockOutputStream;
import net.jpountz.lz4.LZ4FrameInputStream;
import net.jpountz.lz4.LZ4FrameOutputStream;
import org.anarres.lzo.*;
import org.tukaani.xz.*;
import org.xbib.io.compress.bzip2.Bzip2InputStream;
import org.xbib.io.compress.bzip2.Bzip2OutputStream;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;
import tech.tresearchgroup.schemas.compression.model.CompressionMethodEnum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.*;

public class ByteCompressionController {
    public static byte[] compress(InputStream data, CompressionMethodEnum method) throws IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        switch (method) {
            case BROTLI -> {
                try {
                    Brotli4jLoader.ensureAvailability();
                } catch (UnsatisfiedLinkError e) {
                    return null;
                }
                Encoder.Parameters brotliParams = new Encoder.Parameters().setQuality(11);
                BrotliOutputStream compressionStream = new BrotliOutputStream(byteOutputStream, brotliParams);
                while ((len = data.read(buffer)) != -1) {
                    compressionStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case BZIP2 -> {
                Bzip2OutputStream compressionStream = new Bzip2OutputStream(byteOutputStream);
                while ((len = data.read(buffer)) != -1) {
                    compressionStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case DEFLATE -> {
                DeflaterOutputStream compressionStream = new DeflaterOutputStream(byteOutputStream);
                while ((len = data.read(buffer)) != -1) {
                    compressionStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case GZIP -> {
                GZIPOutputStream compressionStream = new GZIPOutputStream(byteOutputStream);
                while ((len = data.read(buffer)) != -1) {
                    compressionStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case LZ4_BLOCK -> {
                LZ4BlockOutputStream compressionStream = new LZ4BlockOutputStream(byteOutputStream);
                while ((len = data.read(buffer)) != -1) {
                    compressionStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case LZ4_FRAMED -> {
                LZ4FrameOutputStream compressionStream = new LZ4FrameOutputStream(byteOutputStream);
                while ((len = data.read(buffer)) != -1) {
                    compressionStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case LZMA -> {
                LZMA2Options options = new LZMA2Options();
                options.setPreset(9);
                LZMAOutputStream compressionStream = new LZMAOutputStream(byteOutputStream, options, data.available());
                while ((len = data.read(buffer)) != -1) {
                    compressionStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case SNAPPY_FRAMED -> {
                SnappyOutputStream compressionStream = new SnappyOutputStream(byteOutputStream);
                while ((len = data.read(buffer)) != -1) {
                    compressionStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case XZ -> {
                LZMA2Options options = new LZMA2Options();
                options.setPreset(9);
                XZOutputStream compressionStream = new XZOutputStream(byteOutputStream, options, XZ.CHECK_SHA256);
                while ((len = data.read(buffer)) != -1) {
                    compressionStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case ZSTD -> {
                ZstdOutputStream compressionStream = new ZstdOutputStream(byteOutputStream, 22);
                while ((len = data.read(buffer)) != -1) {
                    compressionStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case LZO -> {
                LzoAlgorithm algorithm = LzoAlgorithm.LZO1X;
                LzoCompressor compressor = LzoLibrary.getInstance().newCompressor(algorithm, null);
                LzoOutputStream compressionStream = new LzoOutputStream(byteOutputStream, compressor, 256);
                while ((len = data.read(buffer)) != -1) {
                    compressionStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
        }
        byteOutputStream.close();
        byteOutputStream.flush();
        return byteOutputStream.toByteArray();
    }

    public static byte[] decompress(InputStream data, CompressionMethodEnum method) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len;
        byte[] buffer = new byte[1024];
        switch (method) {
            case BROTLI -> {
                try {
                    Brotli4jLoader.ensureAvailability();
                } catch (UnsatisfiedLinkError e) {
                    return null;
                }
                BrotliInputStream compressionStream = new BrotliInputStream(data);
                while ((len = compressionStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case BZIP2 -> {
                Bzip2InputStream compressionStream = new Bzip2InputStream(data);
                while ((len = compressionStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case DEFLATE -> {
                InflaterInputStream compressionStream = new InflaterInputStream(data);
                while ((len = compressionStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case GZIP -> {
                GZIPInputStream compressionStream = new GZIPInputStream(data);
                while ((len = compressionStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case LZ4_BLOCK -> {
                LZ4BlockInputStream compressionStream = new LZ4BlockInputStream(data);
                while ((len = compressionStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case LZ4_FRAMED -> {
                LZ4FrameInputStream compressionStream = new LZ4FrameInputStream(data);
                while ((len = compressionStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case LZMA -> {
                LZMAInputStream compressionStream = new LZMAInputStream(data);
                while ((len = compressionStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case SNAPPY_FRAMED -> {
                SnappyInputStream compressionStream = new SnappyInputStream(data);
                while ((len = compressionStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case XZ -> {
                XZInputStream compressionStream = new XZInputStream(data);
                while ((len = compressionStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case ZSTD -> {
                ZstdInputStream compressionStream = new ZstdInputStream(data);
                while ((len = compressionStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
            case LZO -> {
                LzoAlgorithm algorithm = LzoAlgorithm.LZO1X;
                LzoDecompressor decompressor = LzoLibrary.getInstance().newDecompressor(algorithm, null);
                LzoInputStream compressionStream = new LzoInputStream(data, decompressor);
                while ((len = compressionStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                compressionStream.close();
            }
        }
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static Map<String, Double> getBestCompressor(File file) throws IOException {
        Map<String, Double> results = new HashMap<>();
        for (CompressionMethodEnum method : CompressionMethodEnum.values()) {
            if (!method.equals(CompressionMethodEnum.SMAZ)) {
                FileInputStream fis = new FileInputStream(file);
                byte[] data = ByteCompressionController.compress(fis, method);
                if(data != null) {
                    results.put(method.name().toLowerCase(), (double) data.length);
                }
                fis.close();
            }
        }
        System.out.println(results);
        if(!file.delete()) {
            System.err.println("Failed to delete: " + file.getAbsolutePath());
        }
        return results;
    }

    public static Map<String, Double> getBestCompressor(String string) throws IOException {
        Map<String, Double> results = new HashMap<>();
        for (CompressionMethodEnum method : CompressionMethodEnum.values()) {
            if (!method.equals(CompressionMethodEnum.SMAZ)) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(string.getBytes());
                byte[] data = ByteCompressionController.compress(byteArrayInputStream, method);
                if(data != null) {
                    results.put(method.name().toLowerCase(), (double) data.length);
                }
                byteArrayInputStream.close();
            }
        }
        return results;
    }
}
