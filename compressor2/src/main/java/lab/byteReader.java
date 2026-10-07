package lab;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class byteReader {
    public static byte[] readBytes(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return bytes;
    }

    public static void writeBytes(String path, String text) throws IOException {
        byte[] bytes = text.getBytes(StandardCharsets.ISO_8859_1);
        Files.write(Paths.get(path), bytes);
    }
}
