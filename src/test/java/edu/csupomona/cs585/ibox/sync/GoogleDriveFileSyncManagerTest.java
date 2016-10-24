package edu.csupomona.cs585.ibox.sync;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GoogleDriveFileSyncManagerTest {

    @Mock
    private Drive drive;

    @InjectMocks
    private FileSyncManager fileSyncManager = new GoogleDriveFileSyncManager(drive);

    @Mock
    private Drive.Files files;

    @Mock
    private Drive.Files.Insert insertFiles;

    @Mock
    private Drive.Files.List list;

    @Mock
    private Drive.Files.Update updateFiles;

    @Before
    public void setUp(){
        when(drive.files()).thenReturn(files);
        try{
            when(files.list()).thenReturn(list);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testAddFile(){
        java.io.File localFile = Mockito.mock(java.io.File.class);
        try {
            when(drive.files().insert(Mockito.any(), Mockito.any())).thenReturn(insertFiles);
            when(insertFiles.execute()).thenReturn(new File());
            fileSyncManager.addFile(localFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void testUpdateFile(){
        java.io.File fileToUpdate = Mockito.mock(java.io.File.class);
        try {
            when(drive.files().update(Mockito.any(), Mockito.any())).thenReturn(updateFiles);
            when(updateFiles.execute()).thenReturn(new File());
            fileSyncManager.updateFile(fileToUpdate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void testDeleteFile(){
        FileList fileList = new FileList();
        fileList.put("Test", 1);
        java.io.File fileToDelete = Mockito.mock(java.io.File.class);
        try {
            when(list.execute()).thenReturn(fileList);
            when(list.execute().getItems()).thenReturn(fileList.getItems());
            fileSyncManager.deleteFile(fileToDelete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}