package tech.tresearchgroup.libraries.compression.controller;

import com.aayushatharva.brotli4j.Brotli4jLoader;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import tech.tresearchgroup.schemas.compression.model.CompressionMethodEnum;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompressDecompress {
    private static final String TEST_DATA = "This is some lovely data to compress";
    private static byte[] brotliCompressed;
    private static byte[] bzipCompressed;
    private static byte[] deflateCompressed;
    private static byte[] gzipCompressed;
    private static byte[] lz4BlockCompressed;
    private static byte[] lz4FramedCompressed;
    private static byte[] lzmaCompressed;
    private static byte[] snappyFramedCompressed;
    private static byte[] xzCompressed;
    private static byte[] zstdCompressed;
    private static byte[] lzoCompressed;
    private static byte[] smazCompressed;

    @Test
    @Order(1)
    void brotliCompress() throws IOException {
        try {
            Brotli4jLoader.ensureAvailability();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TEST_DATA.getBytes(StandardCharsets.UTF_8));
            byte[] compressed = ByteCompressionController.compress(byteArrayInputStream, CompressionMethodEnum.BROTLI);
            assertNotNull(compressed);
            assertTrue(compressed.length > 0);
            System.out.println("Brotli compressed length: " + compressed.length);
            brotliCompressed = compressed;
        } catch (UnsatisfiedLinkError ignored) {
            System.err.println("Brotli not supported");
        }
    }

    @Test
    @Order(2)
    void brotliDecompress() throws IOException {
        try {
            Brotli4jLoader.ensureAvailability();
            assertNotNull(brotliCompressed);
            byte[] decompressed = ByteCompressionController.decompress(new ByteArrayInputStream(brotliCompressed), CompressionMethodEnum.BROTLI);
            assertNotNull(decompressed);
            System.out.println(decompressed.length);
            assertEquals(TEST_DATA, new String(decompressed, StandardCharsets.UTF_8));
        } catch (UnsatisfiedLinkError ignored) {
            System.err.println("Brotli not supported");
        }
    }

    @Test
    @Order(3)
    void bzip2Compress() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TEST_DATA.getBytes(StandardCharsets.UTF_8));
        byte[] compressed = ByteCompressionController.compress(byteArrayInputStream, CompressionMethodEnum.BZIP2);
        assertNotNull(compressed);
        assertTrue(compressed.length > 0);
        System.out.println("BZip2 compressed length: " + compressed.length);
        bzipCompressed = compressed;
    }

    @Test
    @Order(4)
    void bzip2Decompress() throws IOException {
        assertNotNull(bzipCompressed);
        byte[] decompressed = ByteCompressionController.decompress(new ByteArrayInputStream(bzipCompressed), CompressionMethodEnum.BZIP2);
        assertNotNull(decompressed);
        assertEquals(TEST_DATA, new String(decompressed, StandardCharsets.UTF_8));
    }

    @Test
    @Order(5)
    void deflateCompress() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TEST_DATA.getBytes(StandardCharsets.UTF_8));
        byte[] compressed = ByteCompressionController.compress(byteArrayInputStream, CompressionMethodEnum.DEFLATE);
        assertNotNull(compressed);
        assertTrue(compressed.length > 0);
        System.out.println("Deflate compressed length: " + compressed.length);
        deflateCompressed = compressed;
    }

    @Test
    @Order(6)
    void deflateDecompress() throws IOException {
        assertNotNull(deflateCompressed);
        byte[] decompressed = ByteCompressionController.decompress(new ByteArrayInputStream(deflateCompressed), CompressionMethodEnum.DEFLATE);
        assertNotNull(decompressed);
        assertEquals(TEST_DATA, new String(decompressed, StandardCharsets.UTF_8));
    }

    @Test
    @Order(7)
    void gzipCompress() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TEST_DATA.getBytes(StandardCharsets.UTF_8));
        byte[] compressed = ByteCompressionController.compress(byteArrayInputStream, CompressionMethodEnum.GZIP);
        assertNotNull(compressed);
        assertTrue(compressed.length > 0);
        System.out.println("GZip compressed length: " + compressed.length);
        gzipCompressed = compressed;
    }

    @Test
    @Order(8)
    void gzipDecompress() throws IOException {
        assertNotNull(gzipCompressed);
        byte[] decompressed = ByteCompressionController.decompress(new ByteArrayInputStream(gzipCompressed), CompressionMethodEnum.GZIP);
        assertNotNull(decompressed);
        assertEquals(TEST_DATA, new String(decompressed, StandardCharsets.UTF_8));
    }

    @Test
    @Order(9)
    void lz4blockCompress() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TEST_DATA.getBytes(StandardCharsets.UTF_8));
        byte[] compressed = ByteCompressionController.compress(byteArrayInputStream, CompressionMethodEnum.LZ4_BLOCK);
        assertNotNull(compressed);
        assertTrue(compressed.length > 0);
        System.out.println("LZ4 blocked compressed length: " + compressed.length);
        lz4BlockCompressed = compressed;
    }

    @Test
    @Order(10)
    void lz4blockDecompress() throws IOException {
        assertNotNull(lz4BlockCompressed);
        byte[] decompressed = ByteCompressionController.decompress(new ByteArrayInputStream(lz4BlockCompressed), CompressionMethodEnum.LZ4_BLOCK);
        assertNotNull(decompressed);
        assertEquals(TEST_DATA, new String(decompressed, StandardCharsets.UTF_8));
    }

    @Test
    @Order(11)
    void lz4framedCompress() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TEST_DATA.getBytes(StandardCharsets.UTF_8));
        byte[] compressed = ByteCompressionController.compress(byteArrayInputStream, CompressionMethodEnum.LZ4_FRAMED);
        assertNotNull(compressed);
        assertTrue(compressed.length > 0);
        System.out.println("LZ4 framed compressed length: " + compressed.length);
        lz4FramedCompressed = compressed;
    }

    @Test
    @Order(12)
    void lz4framedDecompress() throws IOException {
        assertNotNull(lz4FramedCompressed);
        byte[] decompressed = ByteCompressionController.decompress(new ByteArrayInputStream(lz4FramedCompressed), CompressionMethodEnum.LZ4_FRAMED);
        assertNotNull(decompressed);
        assertEquals(TEST_DATA, new String(decompressed, StandardCharsets.UTF_8));
    }

    @Test
    @Order(13)
    void lzmaCompress() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TEST_DATA.getBytes(StandardCharsets.UTF_8));
        byte[] compressed = ByteCompressionController.compress(byteArrayInputStream, CompressionMethodEnum.LZMA);
        assertNotNull(compressed);
        assertTrue(compressed.length > 0);
        System.out.println("LZMA compressed length: " + compressed.length);
        lzmaCompressed = compressed;
    }

    @Test
    @Order(14)
    void lzmaDecompress() throws IOException {
        assertNotNull(lzmaCompressed);
        byte[] decompressed = ByteCompressionController.decompress(new ByteArrayInputStream(lzmaCompressed), CompressionMethodEnum.LZMA);
        assertNotNull(decompressed);
        assertEquals(TEST_DATA, new String(decompressed, StandardCharsets.UTF_8));
    }

    @Test
    @Order(15)
    void snappyFramedCompress() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TEST_DATA.getBytes(StandardCharsets.UTF_8));
        byte[] compressed = ByteCompressionController.compress(byteArrayInputStream, CompressionMethodEnum.SNAPPY_FRAMED);
        assertNotNull(compressed);
        assertTrue(compressed.length > 0);
        System.out.println("Snappy framed compressed length: " + compressed.length);
        snappyFramedCompressed = compressed;
    }

    @Test
    @Order(16)
    void snappyFramedDecompress() throws IOException {
        assertNotNull(snappyFramedCompressed);
        byte[] decompressed = ByteCompressionController.decompress(new ByteArrayInputStream(snappyFramedCompressed), CompressionMethodEnum.SNAPPY_FRAMED);
        assertNotNull(decompressed);
        assertEquals(TEST_DATA, new String(decompressed, StandardCharsets.UTF_8));
    }

    @Test
    @Order(17)
    void xzCompress() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TEST_DATA.getBytes(StandardCharsets.UTF_8));
        byte[] compressed = ByteCompressionController.compress(byteArrayInputStream, CompressionMethodEnum.XZ);
        assertNotNull(compressed);
        assertTrue(compressed.length > 0);
        System.out.println("XZ compressed length: " + compressed.length);
        xzCompressed = compressed;
    }

    @Test
    @Order(18)
    void xzDecompress() throws IOException {
        assertNotNull(xzCompressed);
        byte[] decompressed = ByteCompressionController.decompress(new ByteArrayInputStream(xzCompressed), CompressionMethodEnum.XZ);
        assertNotNull(decompressed);
        assertEquals(TEST_DATA, new String(decompressed, StandardCharsets.UTF_8));
    }

    @Test
    @Order(19)
    void zstdCompress() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TEST_DATA.getBytes(StandardCharsets.UTF_8));
        byte[] compressed = ByteCompressionController.compress(byteArrayInputStream, CompressionMethodEnum.ZSTD);
        assertNotNull(compressed);
        assertTrue(compressed.length > 0);
        System.out.println("ZStd compressed length: " + compressed.length);
        zstdCompressed = compressed;
    }

    @Test
    @Order(20)
    void zstdDecompress() throws IOException {
        assertNotNull(zstdCompressed);
        byte[] decompressed = ByteCompressionController.decompress(new ByteArrayInputStream(zstdCompressed), CompressionMethodEnum.ZSTD);
        assertNotNull(decompressed);
        assertEquals(TEST_DATA, new String(decompressed, StandardCharsets.UTF_8));
    }

    @Test
    @Order(21)
    void lzoCompress() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TEST_DATA.getBytes(StandardCharsets.UTF_8));
        byte[] compressed = ByteCompressionController.compress(byteArrayInputStream, CompressionMethodEnum.LZO);
        assertNotNull(compressed);
        assertTrue(compressed.length > 0);
        System.out.println("LZO compressed length: " + compressed.length);
        lzoCompressed = compressed;
    }

    @Test
    @Order(22)
    void lzoDecompress() throws IOException {
        assertNotNull(lzoCompressed);
        byte[] decompressed = ByteCompressionController.decompress(new ByteArrayInputStream(lzoCompressed), CompressionMethodEnum.LZO);
        assertNotNull(decompressed);
        assertEquals(TEST_DATA, new String(decompressed, StandardCharsets.UTF_8));
    }

    @Test
    @Order(23)
    void smazCompress() {
        Smaz smaz = new Smaz();
        byte[] compressed = smaz.compress(TEST_DATA);
        assertNotNull(compressed);
        assertTrue(compressed.length > 0);
        System.out.println("Smaz compressed length: " + compressed.length);
        smazCompressed = compressed;
    }

    @Test
    @Order(24)
    void smazDecompress() {
        assertNotNull(smazCompressed);
        Smaz smaz = new Smaz();
        String decompressed = smaz.decompress(smazCompressed);
        System.out.println("Decompressed: " + decompressed);
        assertEquals(decompressed, TEST_DATA);
    }
}