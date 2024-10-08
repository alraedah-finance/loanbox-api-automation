package util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {


    public static String getFileAsString(String relativePath) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get("src/test/java/testData" + relativePath));
            return new String(encoded, Charset.defaultCharset());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativePath;
    }
}
