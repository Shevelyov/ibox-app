package edu.csupomona.cs585.ibox.sync;

import edu.csupomona.cs585.ibox.WatchDir;
import org.junit.*;
import org.junit.runner.JUnitCore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GoogleDriveFileSyncManagerIT {

    private File file;
    private FileSyncManager fileSyncManager;

    @Before
    public void setUp(){
        Path dir = Paths.get("D:\\GOdir");
        file = new File("D:\\GOdir\\test.txt");
        fileSyncManager = new GoogleDriveFileSyncManager(
                GoogleDriveServiceProvider.get().getGoogleDriveClient());
        try {
            new WatchDir(dir, fileSyncManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddFile(){
        try {
            file.createNewFile();
            fileSyncManager.addFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            file.delete();
        }
    }

    @Test
    public void testUpdateFile(){
        BufferedWriter bufferWritter = null;
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            bufferWritter = new BufferedWriter(new FileWriter(file, true));
            bufferWritter.write("File updated");
            if(bufferWritter != null){
                bufferWritter.close();
            }
            fileSyncManager.updateFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteFile(){
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            fileSyncManager.deleteFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //workaround for the console input to work
    public static void main(String args[]) {
        JUnitCore.runClasses(GoogleDriveFileSyncManagerIT.class);
    }

}
