import java.io.*;

import org.apache.commons.io.FileUtils;

public class Main {

    public static void main(String[] args) {
        mutateApp("/home/camilo/Documentos/Proyectos_Camilo/MDroidPlus","/home/camilo/Documentos/Proyectos_Camilo/gnucash-android","org.gnucash.android");

        File testFile = new File("/home/camilo/Documentos/Proyectos_Camilo/carReport/MDroidPlus/tmp/mutants/carreport-mutant11");
        File destDir = new File("/home/camilo/Escritorio/CarReport/app/src/main");
        try{
            overWriteAppSrc(testFile, destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Execute command
            generateApk();

        } catch (IOException e) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void overWriteAppSrc(File testFile, File destDir) throws IOException {
        FileUtils.copyDirectory(testFile, destDir);
    }

    private static void generateApk() throws IOException, InterruptedException {
        String command = "gradle build -p /home/camilo/Documentos/Proyectos_Camilo/gnucash/gnucash-android_M713\n";//"gradle build -p /home/camilo/Escritorio/CarReport/";
        Process child = Runtime.getRuntime().exec(command);

        System.out.println("Waiting for build apk ...");
        child.waitFor();
        System.out.println("Build apk done.");
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



    public static void mutateApp(String mDroidPath, String sourceCodePath, String packageName){
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-jar",mDroidPath + "/target/MDroidPlus-1.0.0.jar", "libs4ast", sourceCodePath+ "/app/src/main", packageName, mDroidPath + "/tmp/mutants");
            pb.redirectErrorStream(true);
            System.out.println("Mutating the app ...");
            Process p = pb.start();
            InputStreamReader isr = new InputStreamReader(p.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            String lineRead;
            while ((lineRead = br.readLine()) != null) {
                System.out.println(lineRead);
            }

            p.waitFor();
            System.out.println("Mutation complete");
        } catch (IOException e) {
            e.printStackTrace(); // or log it, or otherwise handle it
        }
        catch (InterruptedException ie) {
            ie.printStackTrace(); // or log it, or otherwise handle it
        }

    }
}
