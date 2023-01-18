package tech.tresearchgroup.libraries.compression.controller;

public class StringCompressionController {
    public static byte[] smazCompress(String compressThis) {
        Smaz smaz = new Smaz();
        byte[] compressed = smaz.compress(compressThis);
        return compressed;
    }
}
