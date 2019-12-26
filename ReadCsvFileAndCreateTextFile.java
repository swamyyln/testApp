package com.thf.users;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static void main(String[] args) {

        String csvFile = "C:/Users/YE20004956/Desktop/aem_dam_csv_importer/SIT_Dam_Absolute_Path_data.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        
        List<String> finalArrayList=new ArrayList<String>();
        
       

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] csvoutput = line.split(cvsSplitBy);

                
                if(csvoutput[1].equals("null")){
                	finalArrayList.add(csvoutput[0]);
                }
                //System.out.println("[piececode= " + csvoutput[0] + " , abstargetpath=" + csvoutput[1] + "]");   

            }
            
            System.out.println(finalArrayList.size());
            
            createTextFile(finalArrayList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    public static void createTextFile(List<String> notmatchList){
    	  FileWriter writer = null;
    	  
    	 try {
			writer = new FileWriter("C:/Users/YE20004956/Desktop/piececode_not_found_list.txt");
			
			for(String str: notmatchList) {
				  writer.write(str + System.lineSeparator());
				}
				writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}