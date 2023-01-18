package tech.tresearchgroup.libraries.compression.controller;

import com.aayushatharva.brotli4j.Brotli4jLoader;
import com.aayushatharva.brotli4j.encoder.BrotliOutputStream;
import com.aayushatharva.brotli4j.encoder.Encoder;
import com.github.luben.zstd.ZstdOutputStream;
import net.jpountz.lz4.LZ4BlockOutputStream;
import net.jpountz.lz4.LZ4FrameOutputStream;
import org.anarres.lzo.LzoAlgorithm;
import org.anarres.lzo.LzoCompressor;
import org.anarres.lzo.LzoLibrary;
import org.anarres.lzo.LzoOutputStream;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.LZMAOutputStream;
import org.tukaani.xz.XZ;
import org.tukaani.xz.XZOutputStream;
import org.xbib.io.compress.bzip2.Bzip2OutputStream;
import org.xerial.snappy.SnappyOutputStream;
import tech.tresearchgroup.schemas.compression.model.CompressionMethodEnum;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

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
        return byteOutputStream.toByteArray();
    }

    public static byte[] decompress(InputStream data, CompressionMethodEnum method) throws IOException {
        switch (method) {
            case BROTLI -> {

            }
            case BZIP2 -> {

            }
            case DEFLATE -> {

            }
            case GZIP -> {

            }
            case LZ4_BLOCK -> {

            }
            case LZ4_FRAMED -> {

            }
            case LZMA -> {

            }
            case SNAPPY_FRAMED -> {

            }
            case XZ -> {

            }
            case ZSTD -> {

            }
            case LZO -> {

            }
        }
        return null;
    }

    public static Map<String, Double> getBestCompressor(File file) throws IOException {
        Map<String, Double> results = new HashMap<>();
        long size = Files.size(file.toPath());
        for (CompressionMethodEnum method : CompressionMethodEnum.values()) {
            if (!method.equals(CompressionMethodEnum.SMAZ)) {
                FileInputStream fis = new FileInputStream(file);
                byte[] data = ByteCompressionController.compress(fis, method);
                if(data != null) {
                    results.put(method.name().toLowerCase(), (double) size - data.length);
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
                    results.put(method.name().toLowerCase(), (double) string.length() - data.length);
                }
                byteArrayInputStream.close();
            }
        }
        return results;
    }
}
