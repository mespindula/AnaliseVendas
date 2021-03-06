package com.agibank.prova.AnaliseVendas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.agibank.prova.service.HandleFiles;
import com.agibank.prova.service.SalesAnalizeJob;

@SpringBootTest
class SalesAnalizeJobTests {
	
	@Value("${folder_in}")
	private String folderIn;
	
	@Value("${folder_out}")
	private String folderOut;
	
	@Autowired
	private SalesAnalizeJob job;
	
	@Autowired
	private HandleFiles handleFiles;
	
	@Test
	void processFileOutputNoFile() {
		clearFolder(folderIn);
		clearFolder(folderOut);
		
		job.runAnalize();
		
		File[] files = handleFiles.getListOfFiles(HandleFiles.CURRENT_USER_HOME_DIR + folderOut);
		
		assertEquals(true, files.length == 0);
	}

	@Test
	void processFileOutput() {
		clearFolder(folderOut);
		
		handleFiles.createDirectoty(HandleFiles.CURRENT_USER_HOME_DIR + folderIn);
		handleFiles.fileCopy(HandleFiles.CURRENT_USER_DIR + "\\teste.dat", HandleFiles.CURRENT_USER_HOME_DIR + folderIn + "teste.dat");
		
		job.runAnalize();
		
		assertEquals(true, handleFiles.fileExists(HandleFiles.CURRENT_USER_HOME_DIR + folderOut));
	}
	
	private void clearFolder(String folder) {
		if (handleFiles.fileExists(HandleFiles.CURRENT_USER_HOME_DIR + folder)) {
			File[] files = handleFiles.getListOfFiles(HandleFiles.CURRENT_USER_HOME_DIR + folder);
			
			for (int index = 0; index < files.length; index++) {
				handleFiles.deleteFileOrDir(HandleFiles.CURRENT_USER_HOME_DIR + folder + files[index].getName());
			}
		}
		
		handleFiles.deleteFileOrDir(HandleFiles.CURRENT_USER_HOME_DIR + folder);
	}

}
