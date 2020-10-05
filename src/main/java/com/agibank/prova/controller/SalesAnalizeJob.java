package com.agibank.prova.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.agibank.prova.util.HandleFiles;

@Service
public class SalesAnalizeJob {
	
	Logger logger = LoggerFactory.getLogger(SalesAnalizeJob.class);
	
	@Autowired
	private HandleFiles handleFiles;
	
	@Autowired
	private SalesAnalize salesAnalize;
	
	@Value("${folder_in}")
	private String folderIn;
	
	@Value("${folder_out}")
	private String folderOut;

	@Scheduled(cron = "* */5 * * * ?")
	private void process() {
		runAnalize();
	}
	
	public void runAnalize() {
		/*
		 * Cria diretório saída se năo existir
		 */
		handleFiles.createDirectoty(folderOut);
		
		/*
		 * Carrega lista de arquivos .dat no diretório de entrada
		 */
		File[] files = handleFiles.getListOfFiles(folderIn, ".dat");
		
		if(files != null){
			
			for (int index = 0; index < files.length; index++) {
				
				String newName = files[index].getName().substring(0, files[index].getName().length() - 4) + ".done.dat";
				
				/*
				 * Verifica se o arquivo já foi importado, caso contrário
				 * procede leitura do arquivo e somatórios
				 */
				if (!handleFiles.fileExists(folderOut + newName)) {
					
					/*
					 * Cria o arquivo de saída
					 */
					handleFiles.createFile(folderOut + newName);
					
					BufferedReader in = handleFiles.readFileContents(folderIn + files[index].getName());

					logger.info("%%% Processando arquivo de entrada " + files[index].getName() + "...");					
					
					if(in != null){
						salesAnalize.clearLines();
						
						try {
							
							while (in.ready()) {
								salesAnalize.addLine(in.readLine());
							}
								
							in.close();

							/*
							 * Grava resultados no arquivo de saída
							 */
							handleFiles.writeOnFile(folderOut + newName, salesAnalize.createReport().toString());
							
							logger.info("%%% Arquivo " + newName + " exportado!\n");
								
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			}
		}
	}
	
}
