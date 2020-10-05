package com.agibank.prova.AnaliseVendas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.agibank.prova.controller.SalesAnalizeJob;
import com.agibank.prova.util.HandleFiles;

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
		
		if (!handleFiles.fileExists(folderIn)) {
			job.runAnalize();
			
			File[] files = handleFiles.getListOfFiles(folderOut);
			
			assertEquals(true, files.length == 0);
		}

	}

	@Test
	void processFileOutput() {
		
		String data = "001ç1234567891234çPedroç50000\n" + 
				"001ç3245678865434çPauloç40000.99\n" + 
				"002ç2345675434544345çJose da SilvaçRural\n" + 
				"002ç2345675433444345çEduardo PereiraçRural\n" + 
				"003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro\n" + 
				"003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo";
		
		clearFolder(folderOut);
		
		handleFiles.createDirectoty(folderIn);
		
		handleFiles.writeOnFile(folderIn + "teste.dat", data);

		if (handleFiles.fileExists(folderIn + "teste.dat")) {
			job.runAnalize();
			
			assertEquals(true, handleFiles.fileExists(folderOut));
		}
	}
	
	private void clearFolder(String folder) {
		if (handleFiles.fileExists(folder)) {
			File[] files = handleFiles.getListOfFiles(folder);
			
			for (int index = 0; index < files.length; index++) {
				handleFiles.deleteFileOrDir(folder + files[index].getName());
			}
		}
		
		handleFiles.deleteFileOrDir(folder);
	}

}
