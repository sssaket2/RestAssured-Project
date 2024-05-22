package utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class ExcelUtils {
	@Test
	public Map<String,String> readData() {
	
	String excelPath = "./TestData/Test_Data.xlsx";
	
	HashMap<String, String> map = new HashMap<String, String>();
	
	try {
		XSSFWorkbook workbook = new XSSFWorkbook(excelPath);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		
		int rows = sheet.getLastRowNum();
		int columns = sheet.getRow(0).getLastCellNum();
		
		//System.out.println(rows);
		
		for(int i=0;i<=rows;i++) {
			
			map.put(sheet.getRow(i).getCell(0).toString(),sheet.getRow(i).getCell(1).toString());
		}
		
		//System.out.println(map.get("Json_Schema"));
		
		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return map;
	
	}
	

}
