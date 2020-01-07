package com.nse.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.nse.base.BaseClass;

public class TestUtil extends BaseClass{
	public static long PAGE_LOAD_TIMEOUT=20;
	public static long IMPLICIT_WAIT=10;
	private static Workbook book;
	private static org.apache.poi.ss.usermodel.Sheet sheet;
	private static File file_csv;
	private static FileWriter fw;

	
	public  Object[][] getTestData(String SheetName) throws InvalidFormatException, IOException{
		
		
		try {
			FileInputStream file=new FileInputStream(prop.getProperty("DataFileLocation"));
			book=WorkbookFactory.create(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		sheet=book.getSheet(prop.getProperty("sheetname"));
		Object data[][]=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int j = 0; j <sheet.getRow(0).getLastCellNum(); j++) {
				data[i][j]=sheet.getRow(i+1).getCell(j).toString();
				}
			
		}
			return data;
		
	}
	
		
	
	
	public  void writeTextFile(String output) throws IOException{
		
		String idForTxtFile = new SimpleDateFormat("dd_MM_yy_HH").format(new Date());
		
		file_csv = new File(System.getProperty("user.dir") + "/OutputFile/"+idForTxtFile+".csv");
				
		fw =new FileWriter(file_csv, true);
		String lineSeparator = System.getProperty("line.separator");
		FileReader fr = new FileReader(file_csv); 
		int st; 	
		if ((st=fr.read()) == -1) {
			file_csv.createNewFile();
			fw.append("Symbol, Underlying Index, Nearest Strike Price, Calls LTP Value, Put LTP Value, "
					+ "Sum of Call and Put LTP, % of Strike Price");
			fw.append(lineSeparator);
		}
				  //if you want to write the line separator ("\n) as they are in the txt you should use the following Code:
		

		    fw.append(output);
		    fw.append(lineSeparator);
		  
		}
	
	public  void closeTextFile() throws IOException{
		  
		  fw.close();
		  
		 

		}

}
