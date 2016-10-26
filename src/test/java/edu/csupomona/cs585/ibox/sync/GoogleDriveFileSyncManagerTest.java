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
    private Drive.Files.Delete deleteFiles;

    @Mock
    private Drive.Files.List list;

    @Mock
    private Drive.Files.Update updateFiles;

    private FileList fileList;
    private File mockFile;
    private List<File> mockList;

    @Before
    public void setUp(){
        fileList = new FileList();
        mockFile = new File();
        mockFile.setTitle("test");
        mockFile.setId("test123456");
        mockList = new ArrayList<>();
        mockList.add(mockFile);
        fileList.setItems(mockList);

        when(drive.files()).thenReturn(files);
        try{
            when(list.execute()).thenReturn(fileList);
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
    public void testUpdateFile(){
        java.io.File fileToUpdate = new java.io.File("D:\\GOdir\\test");
        try {
            when(drive.files().update(Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(updateFiles);
            when(updateFiles.execute()).thenReturn(new File());
            fileSyncManager.updateFile(fileToUpdate);
            verify(updateFiles, Mockito.times(1)).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteFile(){
        java.io.File fileToDelete = new java.io.File("D:\\GOdir\\test");
        try {
            when(drive.files().delete("test123456")).thenReturn(deleteFiles);
            fileSyncManager.deleteFile(fileToDelete);
            verify(deleteFiles, Mockito.times(1)).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}