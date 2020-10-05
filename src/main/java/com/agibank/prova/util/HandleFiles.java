package com.agibank.prova.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class HandleFiles {
	
	private File fl;

	public File[] getListOfFiles(String dir) {
		fl = new File(dir);
		return fl.listFiles();
	}
	
	public File[] getListOfFiles(String dir, String ext) {

		File[] arquivos = null;

		FilenameFilter filter = new Filter(ext);

		fl = new File(dir);
		arquivos = fl.listFiles(filter);

		return arquivos;
	}

	public boolean createFile(String fileName) {
		fl = new File(fileName);

		try {
			return fl.createNewFile();
		} catch (IOException e) {
			System.out.println("Năo foi possível criar arquivo!");
			e.printStackTrace();

			return false;
		}
	}

	public boolean createDirectoty(String fileName) {
		fl = new File(fileName);

		return fl.mkdir();
	}
	
	public boolean deleteFileOrDir(String fileName) {
		fl = new File(fileName);

		return fl.delete();
	}

	public boolean writeOnFile(String fileName, String text) {

		fl = new File(fileName);

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fl);

			try {
				fos.write(text.getBytes());

				fos.close();

				return true;
			} catch (IOException e) {
				System.out.println("Năo foi possível escrever no arquivo!");
				e.printStackTrace();
				return false;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Năo foi possível encontrar arquivo: " + fileName);
			e.printStackTrace();
			return false;
		}

	}

	public boolean fileExists(String fileName) {
		fl = new File(fileName);

		return fl.exists();
	}

	public BufferedReader readFileContents(String fileName) {

		BufferedReader in = null;

		try {
			in = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Năo foi possível encontrar arquivo: " + fileName);
			e.printStackTrace();
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
