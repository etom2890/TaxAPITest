import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ListFileClass {

    public static String[] listFiles() {
        File folder = new File("C:\\Users\\enlil.tom\\Documents\\sd-2677-run");
        File[] fileNames = folder.listFiles();

        int length = fileNames.length;
        int i=0;
        String[] files;
        files = new String[length];
        for (File file : fileNames) {
            files[i] = file.getAbsolutePath();
            i++;

        }

        return files;
    }
}