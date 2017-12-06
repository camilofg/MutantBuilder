import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;

public class Main {

    public static void main(String[] args) {
        File testFile = new File("/home/camilo/Documentos/Proyectos_Camilo/carReport/MDroidPlus/tmp/mutants/carreport-mutant11");
        File destDir = new File("/home/camilo/Escritorio/CarReport/app/src/main");
        try{
        FileUtils.copyDirectory(testFile, destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Execute command
            String command = "gradle build -p /home/camilo/Documentos/Proyectos_Camilo/gnucash/gnucash-android_M713\n";//"gradle build -p /home/camilo/Escritorio/CarReport/";
            Process child = Runtime.getRuntime().exec(command);

            System.out.println("Waiting for build apk ...");
            child.waitFor();
            System.out.println("Build apk done.");

        } catch (IOException e) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //File.copy(testFile, "/home/camilo/Escritorio/CarReport/app/src/main");
        //File root = new File("/home/camilo/Escritorio/CarReport/app/src/main");
        //deleteFile(root);
    }

    public static void deleteFile(File file){
        File folderContainer = null;
        String[]entries = file.list();
        for(String s: entries) {
            File currentFile0 = new File(file.getPath(),s);
            if (currentFile0.isDirectory()) {
                folderContainer = currentFile0;
                deleteFile(currentFile0);
            } else {
                currentFile0.delete();
            }
            folderContainer.delete();
        }
    }
}
