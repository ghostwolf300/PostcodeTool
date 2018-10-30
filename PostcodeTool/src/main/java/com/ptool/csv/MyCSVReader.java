package com.ptool.csv;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class MyCSVReader {
	
	public static MyCSVReader instance;
	
	private MyCSVReader() {
		
	}
	
	public static synchronized MyCSVReader getInstance() {
		if(instance==null) {
			instance=new MyCSVReader();
		}
		return instance;
	}
	
	public List<String[]> readPostcodeFile(String fileName){
		
		Reader fileReader=null;
		CSVReader csvReader=null;
		List<String[]> postcodes=null;//new ArrayList<String[]>();
		//String[] postcode=null;
		
		try {
			fileReader=Files.newBufferedReader(Paths.get(fileName));
			csvReader=new CSVReaderBuilder(fileReader).withSkipLines(1).build();
			postcodes=csvReader.readAll();
			/*while((postcode=csvReader.readNext())!=null) {
				postcodes.add(postcode);
				System.out.println(postcode[0]+"\t"+postcode[1]+"\t"+postcode[2]);
			}*/
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return postcodes;
	}
	
	
	
}
