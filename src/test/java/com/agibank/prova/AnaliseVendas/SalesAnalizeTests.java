package com.agibank.prova.AnaliseVendas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agibank.prova.controller.SalesAnalize;
import com.agibank.prova.model.Report;

@SpringBootTest
class SalesAnalizeTests {

	@Autowired
	private SalesAnalize salesAnalize;

	
	@Test
	void createValidadeReport() {
		salesAnalize.clearLines();
		
		salesAnalize.addLine("001ç1234567891234çPedroç50000");
		salesAnalize.addLine("001ç3245678865434çPauloç40000.99");
		salesAnalize.addLine("002ç2345675434544345çJose da SilvaçRural");
		salesAnalize.addLine("002ç2345675433444345çEduardo PereiraçRural");
		salesAnalize.addLine("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro");
		salesAnalize.addLine("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");
		
		Report rep = salesAnalize.createReport();
		
		assertEquals(2, rep.getCountClient());
		assertEquals(2, rep.getCountSalesman());
		assertEquals(10, rep.getIdBiggerSalle());
		assertEquals("Paulo", rep.getSellerNameSmallerAmount());
	}
	
}
