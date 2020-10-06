package com.agibank.prova.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HandleFiles {

	private Logger logger = LoggerFactory.getLogger(SalesAnalizeJob.class);

	public static final String CURRENT_USER_HOME_DIR = System.getProperty("user.home");

	public File[] getListOfFiles(String dir) {
		File fl = new File(dir);
		return fl.listFiles();
	}

	public File[] getListOfFiles(String dir, String ext) {

		File[] arquivos = null;

		FilenameFilter filter = new Filter(ext);

		File fl = new File(dir);
		arquivos = fl.listFiles(filter);

		return arquivos;
	}

	public boolean createFile(String fileName) {
		File fl = new File(fileName);

		try {
			return fl.createNewFile();
		} catch (IOException e) {
			logger.error("Năo foi possível criar arquivo!", e);
			return false;
		}
	}

	public boolean createDirectoty(String fileName) {
		File fl = new File(fileName);
		return fl.mkdirs();
	}

	public boolean deleteFileOrDir(String fileName) {
		File fl = new File(fileName);
		return fl.delete();
	}

	public boolean writeOnFile(String fileName, String text) {

		File fl = new File(fileName);

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fl);

			try {
				fos.write(text.getBytes());

				fos.close();

				return true;
			} catch (IOException e) {
				logger.error("Năo foi possível escrever no arquivo!", e);
				return false;
			}
		} catch (FileNotFoundException e) {
			logger.error("Năo foi possível encontrar arquivo: " + fileName, e);
			return false;
		}

	}

	public void fileCopy(String fileToCopy, String newFile) {
		try {
			File fl = new File(fileToCopy);
			FileInputStream inputStream = new FileInputStream(fl);
			FileChannel inChannel = inputStream.getChannel();

			File newFl = new File(newFile);
			FileOutputStream outputStream = new FileOutputStream(newFl);
			FileChannel outChannel = outputStream.getChannel();

			inChannel.transferTo(0, inChannel.size(), outChannel);
			inputStream.close();
			outputStream.close();
		} catch (IOException e) {
			logger.error("Erro ao copiar arquivo: " + fileToCopy, e);
		}
	}

	public boolean fileExists(String fileName) {
		File fl = new File(fileName);
		return fl.exists();
	}

	public BufferedReader readFileContents(String fileName) {

		BufferedReader in = null;

		try {
			in = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			logger.error("Năo foi possível encontrar arquivo: " + fileName, e);
		}

		return in;
	}
}

class Filter implements FilenameFilter {
	private String extFile;

	public Filter(String extFile) {
		this.extFile = extFile;
	}

	@Override
	public boolean accept(File dir, String name) {
		return (name.endsWith(this.extFile));
	}

}
