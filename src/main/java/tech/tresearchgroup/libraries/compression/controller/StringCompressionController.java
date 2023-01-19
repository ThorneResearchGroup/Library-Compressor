package tech.tresearchgroup.libraries.compression.controller;

public class StringCompressionController {
    public static byte[] smazCompress(String compressThis) {
        Smaz smaz = new Smaz();
        return smaz.compress(compressThis);
    }
}
