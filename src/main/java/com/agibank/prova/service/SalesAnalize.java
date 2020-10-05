package com.agibank.prova.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.agibank.prova.builder.SalesAnalizeBuilder;
import com.agibank.prova.model.Report;

@Service
public class SalesAnalize {
	
	Logger logger = LoggerFactory.getLogger(SalesAnalize.class);
	
	private List<String> lines;
	
	public void clearLines() {
		lines = new ArrayList<String>();
	}
	
	public void addLine(String dataLine) {
		if (lines == null) {
			lines = new ArrayList<String>();
		}
		
		lines.add(dataLine);
	}
	
	public Report createReport() {
		SalesAnalizeBuilder builder = new SalesAnalizeBuilder();
		
		try {
			lines.forEach(line -> {
				String[] dataLine = line.split("ç");
				
				switch (dataLine[0]) {
				case "001":
					builder.addCountSalesman(1);
					break;
				case "002":
					builder.addCountClient(1);
					break;
				case "003":
					builder.addSale(line);
				}
			});
		} catch (Exception ex) {
			logger.error("Falha ao executar criação de relatório", ex);
			new RuntimeException("Falha ao executar criação de relatório");
		}
		
		return builder.build();
	}

}
