package lab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class renameFile {
    public static void rename (String oldName, String newName) throws IOException {
        Files.move(Paths.get(oldName),Paths.get(newName), StandardCopyOption.REPLACE_EXISTING);
    }
}
