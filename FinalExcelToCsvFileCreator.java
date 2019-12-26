import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.jcr.Repository;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FinalExcelToCsvFileGenerator {
	
	  static Map<String,String> invenotryTypeDropDownMap=new HashMap<String,String>();
	 
	  static Map<String,String> colorsDropDownMap=new HashMap<String,String>();
	 
	  static Map<String,String> pageSizeDropDownMap=new HashMap<String,String>();
	  
	  static Map<String,String> bindingDropDownMap=new HashMap<String,String>();
	  
	  static Map<String,String> finishDropDownMap=new HashMap<String,String>();
	  
	  static Map<String,String> stockDropDownMap=new HashMap<String,String>();
	  
	  static Map<String,String> coatingDropDownMap=new HashMap<String,String>();
	  
	  static Map<String,String> printMethodDropDownMap=new HashMap<String,String>();
	  
	  static Map<String,String> fundTypeDropDownMap=new HashMap<String,String>();
	  
	  static Map<String,String> pieceTypeDropDownMap=new HashMap<String,String>();
	  
	  static Map<String,String> finalDCsvpieceCodeDampathMap=new HashMap<String,String>();
	  
	  static Map<String,String> viewInDropDownMap=new HashMap<String,String>();
	  
	  static Map<String,String> userGroupDropDownMap=new HashMap<String,String>();
	  
	
	public static void main(String[] args) {
		
		
		System.out.println("Jai hanuman");	
	
		
		 FileInputStream fis = null;
	    
	     XSSFRow row = null;
	     XSSFRow headerRow = null;
	     XSSFCell cell = null;
	     
	     List<VeritasMetadataBean> veritasMetadataBeanList=new ArrayList<VeritasMetadataBean>();
	     
	     VeritasMetadataBean veritasMetadataBean=null;
	     
	     List<String> assetPieceCodeList=new ArrayList<String>();
	     
	     Set<String> notInvenotrySet=new HashSet<String>();
	     
	     Set<String> notColorsSet=new HashSet<String>();
	     Set<String> notpageSizesSet=new HashSet<String>();
	     Set<String> notBindingsSet=new HashSet<String>();
	     Set<String> notFinishsSet=new HashSet<String>();
	     Set<String> notStockSet=new HashSet<String>();
	     Set<String> notCoatingSet=new HashSet<String>();
	     Set<String> notPrintMethodSet=new HashSet<String>();
	     
	     Set<String> notFundTypeSet=new HashSet<String>();
	     Set<String> notPieceTypeSet=new HashSet<String>();
	     
	     Set<String> notViewInSet=new HashSet<String>();
	     Set<String> notUserGroupSet=new HashSet<String>();
	     
		
		 try{
			 
			 generateInventoryTypeMap();
			 
			 generateColorsMap();
			 
			 generatePageSizeMap();
			 
			 generateStockMap();
			 
			 generateFinishMap();
			 
			 generateCoatingMap();
			 
			 generateBindingMap();
			 
			 generatePrintMethodMap();
			 
			 generateCategoryFundType();
			 
			 generateCategoryPieceType();
			 
			 generateViewIn();
			 
			 generateUserGroup();
			 
			 
			 File veritas_excel_file = new File("C:/Users/YE20004956/Desktop/aem_dam_csv_importer/20191206 - Hartford Funds Active Parts Dump - IMPORT.xlsx");
			 
			 
	            FileInputStream file = new FileInputStream(veritas_excel_file);
	 
	            //Create Workbook instance holding reference to .xlsx file
	            XSSFWorkbook workbook = new XSSFWorkbook(file);
	 
	            //Get first/desired sheet from the workbook
	            XSSFSheet sheet = workbook.getSheetAt(0);
	            
	            SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000-04:00'", Locale.US);
	            		//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
	            
	          for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
	           
	        	   
	        	   veritasMetadataBean=new VeritasMetadataBean();
	        	   
	                  headerRow=sheet.getRow(0);
	            	  row = sheet.getRow(rowIndex);
	            	  if (row != null) {
	            		  
	            	    for(int i = 0; i < row.getLastCellNum(); i++)
	     		        {
	     		       
	     		            cell = row.getCell(i);
	     		            
	     		            if(cell!=null){
	     		            	
	     		            	//effective Date
	     		            	if(cell.getCellTypeEnum() == CellType.NUMERIC){
	     		            		if(headerRow.getCell(i).getStringCellValue().trim().equals("EffectiveDate")) {
	     		            			Date d1=cell.getDateCellValue();
	     		            			String dateStr=formatter6.format(d1);
	     		   					veritasMetadataBean.setEffectiveDate(dateStr);
	     		   					System.out.println(dateStr);
	     		   				}	
	     		            		
	     		            	}
	     		            	
	     		            	  cell.setCellType(CellType.STRING);
	     		         if(cell.getCellTypeEnum() == CellType.STRING)
	     		         { 
	     		            	 
	     		if(headerRow.getCell(i).getStringCellValue().trim().equals("PieceCode")) {
				           veritasMetadataBean.setPieceCode(cell.getStringCellValue());
								//assetPieceCodeList.add(cell.getStringCellValue());
					}
	     		
	     		if(headerRow.getCell(i).getStringCellValue().trim().equals("OrderQtyMax")) {
	     			if(null!=cell.getStringCellValue() && !cell.getStringCellValue().equals("null") ){
	     				
					veritasMetadataBean.setMaxCopiesAvailable(cell.getStringCellValue());
	     			}else{
	     				System.out.println("insideElse");
	     		    veritasMetadataBean.setMaxCopiesAvailable("");
	     			}
				}
	     		
	     		if(headerRow.getCell(i).getStringCellValue().trim().equals("VeritasPieceCode")) {
					veritasMetadataBean.setVeritasPieceCode(cell.getStringCellValue());				
				}
	     		        
				if(headerRow.getCell(i).getStringCellValue().trim().equals("InventoryType")) {
					
					if((invenotryTypeDropDownMap.containsKey(cell.getStringCellValue()))){
					veritasMetadataBean.setInventoryType(invenotryTypeDropDownMap.get(cell.getStringCellValue()));
					}else{
						//System.out.println("Invenotry Type Option not found :"+cell.getStringCellValue());
						notInvenotrySet.add(cell.getStringCellValue());
						veritasMetadataBean.setInventoryType("");
					}
				}
				
				if(headerRow.getCell(i).getStringCellValue().trim().equals("PageSize")) {
					if(pageSizeDropDownMap.containsKey(cell.getStringCellValue())){
					veritasMetadataBean.setPageSize(pageSizeDropDownMap.get(cell.getStringCellValue()));
					}else{
						notpageSizesSet.add(cell.getStringCellValue());
						veritasMetadataBean.setPageSize("");
					}
				}
				
				if(headerRow.getCell(i).getStringCellValue().trim().equals("Colors")) {
					if(colorsDropDownMap.containsKey(cell.getStringCellValue())){
					veritasMetadataBean.setColors(colorsDropDownMap.get(cell.getStringCellValue()));
					}else{
						notColorsSet.add(cell.getStringCellValue());
						veritasMetadataBean.setColors("");
					}
				}
				
				if(headerRow.getCell(i).getStringCellValue().trim().equals("Stock")) {
					if(stockDropDownMap.containsKey(cell.getStringCellValue())){
					veritasMetadataBean.setStock(stockDropDownMap.get(cell.getStringCellValue()));
					}else{
						notStockSet.add(cell.getStringCellValue());
						veritasMetadataBean.setStock("");
					}
				}
				
				if(headerRow.getCell(i).getStringCellValue().trim().equals("Binding")) {
					if(bindingDropDownMap.containsKey(cell.getStringCellValue())){
					veritasMetadataBean.setBinding(bindingDropDownMap.get(cell.getStringCellValue()));
				}else{
					notBindingsSet.add(cell.getStringCellValue());
					veritasMetadataBean.setBinding("");
				}
				}
				if(headerRow.getCell(i).getStringCellValue().trim().equals("PrintMethod")) {
					if(printMethodDropDownMap.containsKey(cell.getStringCellValue())){
					veritasMetadataBean.setPrintMethod(printMethodDropDownMap.get(cell.getStringCellValue()));
					}else{
						notPrintMethodSet.add(cell.getStringCellValue());
						veritasMetadataBean.setPrintMethod("");
					}
				}
				if(headerRow.getCell(i).getStringCellValue().trim().equals("Finish")) {
					if(finishDropDownMap.containsKey(cell.getStringCellValue())){
					veritasMetadataBean.setFinish(finishDropDownMap.get(cell.getStringCellValue()));
					}else{
						notFinishsSet.add(cell.getStringCellValue());
						veritasMetadataBean.setFinish("");
					}
				}
				if(headerRow.getCell(i).getStringCellValue().trim().equals("Coating")) {
					if(coatingDropDownMap.containsKey(cell.getStringCellValue())){
					veritasMetadataBean.setCoating(coatingDropDownMap.get(cell.getStringCellValue()));
					}else{
						notCoatingSet.add(cell.getStringCellValue());
						veritasMetadataBean.setCoating("");
					}
				}
				
				if(headerRow.getCell(i).getStringCellValue().trim().equals("FormOwner")) {
					veritasMetadataBean.setFormOwner(String.valueOf(cell.getStringCellValue()));
				}
				
				if(headerRow.getCell(i).getStringCellValue().trim().equals("FirmIncludeExclude")) {
					veritasMetadataBean.setFirmExcludeInclude(cell.getStringCellValue());
				}
				if(headerRow.getCell(i).getStringCellValue().trim().equals("FirmTags")) {
					veritasMetadataBean.setFirmTags(cell.getStringCellValue());					
				}
				if(headerRow.getCell(i).getStringCellValue().trim().equals("SpecialMessage")) {
					veritasMetadataBean.setSpecialMessage(cell.getStringCellValue());				
				}
				
				if(headerRow.getCell(i).getStringCellValue().trim().equals("FundType")) {
					
					if(fundTypeDropDownMap.containsKey(cell.getStringCellValue())){
						veritasMetadataBean.setCategoryFundType(fundTypeDropDownMap.get(cell.getStringCellValue()));	
						}else{
							notFundTypeSet.add(cell.getStringCellValue());
							veritasMetadataBean.setCategoryFundType("");
						}
								
				}
				
				//viewIn
				
					if(headerRow.getCell(i).getStringCellValue().trim().equals("ViewIn")) {
										veritasMetadataBean.setViewIn(cell.getStringCellValue());				
								}
				
				//userGroup
					
					if(headerRow.getCell(i).getStringCellValue().trim().equals("UserGroup")) {
						
						if(userGroupDropDownMap.containsKey(cell.getStringCellValue())){
							veritasMetadataBean.setUserGroup(userGroupDropDownMap.get(cell.getStringCellValue()));	
							}else{
								notUserGroupSet.add(cell.getStringCellValue());
								veritasMetadataBean.setUserGroup("");
							}
									
					}
				
				   //OfPages
					
                    if(headerRow.getCell(i).getStringCellValue().trim().equals("#OfPages")) {
                    	
                    	if(cell.getStringCellValue()!=null){
                    		System.out.println("ofPages VALUE : "+cell.getStringCellValue());
							veritasMetadataBean.setOfPages(cell.getStringCellValue());
                    	}else{
                    		veritasMetadataBean.setOfPages("");
                    	}
					}
									
					
				
				
				if(headerRow.getCell(i).getStringCellValue().trim().equals("PieceType")) {

					
					if(pieceTypeDropDownMap.containsKey(cell.getStringCellValue())){
						veritasMetadataBean.setCategoryPieceType(pieceTypeDropDownMap.get(cell.getStringCellValue()));	
						}else{
							notPieceTypeSet.add(cell.getStringCellValue());
							veritasMetadataBean.setCategoryPieceType("");
						}
								
								
				}
				
				
	     		         }
							
						 else if(cell.getCellTypeEnum() == CellType.NUMERIC)
						 {
							/*if(headerRow.getCell(i).getStringCellValue().trim().equals("EffectiveDate")) {
								
							    String d1=formatter6.format(cell.getDateCellValue());
								veritasMetadataBean.setEffectiveDate(d1);
							  //System.out.println(d1);
							}
							
							else if(headerRow.getCell(i).getStringCellValue().trim().equals("FormOwner")) {
								veritasMetadataBean.setFormOwner(String.valueOf(cell.getNumericCellValue()));
							}
								
								else{
								//System.out.println(cell.getNumericCellValue());
							}*/
							    System.out.println("CellType Numeric");	
							
							if(headerRow.getCell(i).getStringCellValue().trim().equals("PieceCode")) {
								//veritasMetadataBean.setPieceCode(String.valueOf(cell.getStringCellValue()));  
								System.out.println("Inside numeric");
								assetPieceCodeList.add(cell.getStringCellValue());
							}
				         }
						else if(cell.getCellTypeEnum() == CellType.FORMULA)
						{
							System.out.println("inside formula");
							//System.out.println(cell.getRichStringCellValue());
							
							
							
							//System.out.println("Data Header : "+headerRow.getCell(i).getStringCellValue());
							//System.out.println("CellType :::::::::: Formula");
							
							/*if(headerRow.getCell(i).getStringCellValue().trim().equals("PieceCode")) {
								veritasMetadataBean.setPieceCode(cell.getStringCellValue());     		        		
							}*/
							
						}  
						else
						{
							//System.out.println("No value for cell :"+cell.getColumnIndex());
						    // String.valueOf(cell.getBooleanCellValue());
						}
	            	    
	            	    }
	            	  
	           }
	            	    veritasMetadataBeanList.add(veritasMetadataBean);
	            	  
	            	  }
	         
	            	  
	          }
	          /*int i=0;
	          
	           for(VeritasMetadataBean veritasBean:veritasMetadataBeanList){
	        	   i++;
	        	   System.out.println(i+" : "+veritasBean.getPieceCode());
	           }*/
	          
	          //generateCsvFile(assetPieceCodeList);
	          
	          // System.out.println("Piece code"+veritasMetadataBean.getPieceCode());
	            file.close();
	            
	            //notInvenotrySet
	            System.out.println("not invenotry : "+ notInvenotrySet);
	            //notColorsSet
	            System.out.println("not colors : "+ notColorsSet);
	            //notBindingsSet
	            System.out.println("not binding : "+ notBindingsSet); 
	            //notPrintMethodSet
	            System.out.println("not printMethod : "+ notPrintMethodSet);
	            //notFinishsSet
	            System.out.println("not finish : "+ notFinishsSet);
	            //notpageSizesSet
	            System.out.println("not pageSize : "+ notpageSizesSet);
	            //notStockSet
	            System.out.println("not stock : "+ notStockSet);
	            //notCoatingSet
	            System.out.println("not coating : "+ notCoatingSet);
	            //notFundTypeSet
	            System.out.println("not FundType : "+ notFundTypeSet);
	            //notPieceTypeSet
	            System.out.println("not PieceType : "+ notPieceTypeSet);
	            
	            //  notViewInSet
	            System.out.println("not viewIn : "+ notViewInSet);
	   	        // notUserGroupSet
	            System.out.println("not userGroup : "+ notUserGroupSet);
	            
	            readAbsoluteDAMpaths();
	            
	            generateCsvFile(veritasMetadataBeanList);
	            
	            
		 } 
		 
		 catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	           
	}
	
	public static void generateCsvFile(List<VeritasMetadataBean>  assetPieceCodeList) {

					  FileWriter writer = null;
					
	
					 
				 try {
					 
			   writer = new FileWriter("C:/Users/YE20004956/Desktop/aem_dam_csv_importer/test/SIT_test_csv_importer_data.csv");
				//0
			   //writer.append("piececode");
			   //writer.append(',');
			   //1
			   writer.append("absTargetPath");
			   writer.append(',');
			   //2
			   writer.append("relSrcPath");
			   writer.append(',');
			   //3
			   writer.append("veritasPieceCode {{ String }}");
			   writer.append(',');
			   //4
			   writer.append("dc:maxcopiesavail {{ Long }}");
			   writer.append(',');
			   //5
			   writer.append("inventoryType {{ String }}");
			   writer.append(',');
			   //6
			   writer.append("colors {{ String }}");
			   writer.append(',');
			   //7
			   writer.append("pageSize {{ String }}");
			   writer.append(',');
			   //8
			   writer.append("stock {{ String }}");
			   writer.append(',');
			   //9
			   writer.append("binding {{ String }}");
			   writer.append(',');
			   //10
			   writer.append("printMethod {{ String }}");
			   writer.append(',');
			   //11
			   writer.append("finish {{ String }}");
			   writer.append(',');
			   //12
			   writer.append("coating {{ String }}");
			   writer.append(',');
			   //13
			   writer.append("effectiveDate {{ Date }}");
			   writer.append(',');
			   //14
			   writer.append("formOwner{{ String }}");
			   writer.append(',');
			   //15
			   writer.append("firmRulesMode {{ String }}");
			   writer.append(',');
			   //16
			   writer.append("firmRulesFirm {{String : multi}}");
			   writer.append(',');
			   //17
			   writer.append("specialMessage {{ String }}");
			   writer.append(','); 
			   //18
			   writer.append("fundType {{ String }}");
			   writer.append(','); 
			   //19
			   writer.append("pieceType {{ String }}");
			   writer.append(','); 
			   
			   //20 viewIn {{String : multi}}
			   writer.append("viewIn {{String : multi}}");
			   writer.append(','); 
			   
               //21 userGroup {{ String }}
			   writer.append("userGroup {{ String }}");
			   writer.append(',');
			   
			   //22 OfPages {{ Long }}
			   writer.append("OfPages {{ Long }}");
			   writer.append('\n');
			
			   for(VeritasMetadataBean veritasBean:assetPieceCodeList){
				   
				  if(finalDCsvpieceCodeDampathMap.containsValue(finalDCsvpieceCodeDampathMap.get(veritasBean.getPieceCode()))){
			  // writer.append(veritasBean.getPieceCode());
			   //writer.append(',');
			   writer.append(finalDCsvpieceCodeDampathMap.get(veritasBean.getPieceCode()));
			   writer.append(",");
			   writer.append("");
			   writer.append(",");
			   writer.append(veritasBean.getVeritasPieceCode());
			   writer.append(",");
			   
			   if(null!=veritasBean.getMaxCopiesAvailable()){
			   writer.append(veritasBean.getMaxCopiesAvailable());
			   }else{
			   writer.append("");   
			   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getInventoryType()){
			   writer.append(veritasBean.getInventoryType());
			   }else{
				   writer.append(""); 
			   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getColors()){
			   writer.append(veritasBean.getColors());
			   }else{
			   writer.append(""); 
			   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getPageSize()){
			   writer.append(veritasBean.getPageSize());
				 }else{
			   writer.append("");   
				   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getStock()){
				   writer.append(veritasBean.getStock());
					 }else{
				   writer.append("");   
					   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getBinding()){
				   writer.append(veritasBean.getBinding());
					 }else{
				   writer.append("");   
					   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getPrintMethod()){
				   writer.append(veritasBean.getPrintMethod());
					 }else{
				   writer.append("");   
					   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getFinish()){
				   writer.append(veritasBean.getFinish());
					 }else{
				   writer.append("");   
					   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getCoating()){
				   writer.append(veritasBean.getCoating());
					 }else{
				   writer.append("");   
					   }
			   
               writer.append(",");
			   if(null!=veritasBean.getEffectiveDate()){
				   writer.append(veritasBean.getEffectiveDate());
					 }else{
				   writer.append("");   
					   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getFormOwner()){
				   writer.append(veritasBean.getFormOwner());
					 }else{
				   writer.append("");   
					   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getFirmExcludeInclude()){
				   writer.append(veritasBean.getFirmExcludeInclude());
					 }else{
				   writer.append("");   
					   }
			   
			   
			   writer.append(",");
			   if(null!=veritasBean.getFirmTags()){
				   String currFirmTags=removeDupicateTags(veritasBean.getFirmTags(),true);
				   
				   if(null!=currFirmTags){
					   writer.append(currFirmTags);
					   }else{
				      writer.append("");   
					   }
					 }else{
				      writer.append("");   
					   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getSpecialMessage()){
				   writer.append(veritasBean.getSpecialMessage());
					 }else{
				   writer.append("");   
					   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getCategoryFundType()){
				   writer.append(veritasBean.getCategoryFundType());
					 }else{
				   writer.append("");   
					   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getCategoryPieceType()){
				   writer.append(veritasBean.getCategoryPieceType());
					 }else{
				   writer.append("");   
					   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getViewIn()){
				   String currViewIn=removeDupicateTags(veritasBean.getViewIn(),false);
				   
				   if(null!=currViewIn){
					   writer.append(currViewIn);
					   }else{
				      writer.append("");   
					   }
					 }else{
				      writer.append("");   
					   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getUserGroup()){
				   writer.append(veritasBean.getUserGroup());
					 }else{
				   writer.append("");   
					   }
			   
			   writer.append(",");
			   if(null!=veritasBean.getOfPages()){
				   writer.append(veritasBean.getOfPages());
					 }else{
				   writer.append("");   
					   }
			   
			   
			   writer.append('\n');
			   }
			   }
			   
				 
				} catch (Exception e) {
				     e.printStackTrace();
				  } finally {
			      try {
			      	//veritasMetadataBean=null;
			      	//veritasMetadataBeanList.clear();
			    writer.flush();
			    writer.close();
			      } catch (IOException e) {
			    e.printStackTrace();
			}
			}
			
			
			   System.out.println("CSV file is created...");
   
	}
	
	private static void generateInventoryTypeMap(){
		 invenotryTypeDropDownMap.put("Application – POD", "Application - POD");
		 invenotryTypeDropDownMap.put("Application - POD", "Application - POD");
		 invenotryTypeDropDownMap.put("Application – Static", "Application - Static");
		 invenotryTypeDropDownMap.put("Application - Static", "Application - Static");
		 invenotryTypeDropDownMap.put("Book Builder", "Book Builder");
		 invenotryTypeDropDownMap.put("Box", "Box");
		 invenotryTypeDropDownMap.put("Brochure – POD", "Brochure - POD");
		 invenotryTypeDropDownMap.put("Brochure - POD", "Brochure - POD");
		 invenotryTypeDropDownMap.put("Brochure - Static", "Brochure - Static");
		 invenotryTypeDropDownMap.put("Business Card", "Business Card");
		 invenotryTypeDropDownMap.put("Commentaries - POD", "Commentaries - POD");
		 invenotryTypeDropDownMap.put("Commentaries - Static", "Commentaries - Static");
		 invenotryTypeDropDownMap.put("Drip Campaign", "Drip Campaign");
		 invenotryTypeDropDownMap.put("Envelope", "Envelope");
		 invenotryTypeDropDownMap.put("Fixed Body Book", "Fixed Body Book");
		 invenotryTypeDropDownMap.put("Flyer  - POD", "Flyer - POD");
		 invenotryTypeDropDownMap.put("Flyer  - Static", "Flyer - Static");
		 invenotryTypeDropDownMap.put("Folder", "Folder");
		 invenotryTypeDropDownMap.put("Forms Booklet", "Forms Booklet");
		 invenotryTypeDropDownMap.put("Kit", "Kit");
		 invenotryTypeDropDownMap.put("Kit - On the Fly", "Kit - On the Fly");
		 invenotryTypeDropDownMap.put("Letter", "Letter");
		 invenotryTypeDropDownMap.put("Postacard - POD", "Postacard - POD");
		 invenotryTypeDropDownMap.put("Postcard - Static", "Postcard - Static");
		 invenotryTypeDropDownMap.put("Poster - Static", "Poster - Static");
		 invenotryTypeDropDownMap.put("PowerPoint - POD", "PowerPoint - POD");
		 invenotryTypeDropDownMap.put("PowerPoint - Static", "PowerPoint - Static");
		 invenotryTypeDropDownMap.put("Print to Self", "Print to Self");
		 invenotryTypeDropDownMap.put("Promotional Items", "Promotional Items");
		 invenotryTypeDropDownMap.put("Prospectus", "Prospectus");
		 invenotryTypeDropDownMap.put("Stationary - POD", "Stationary - POD");
		 invenotryTypeDropDownMap.put("Stationary – Static", "Stationary - Static");
		 invenotryTypeDropDownMap.put("Stationery - Static", "Stationary - Static");
		 invenotryTypeDropDownMap.put("Summary Prospectus – POD", "Summary Prospectus - POD");
		 invenotryTypeDropDownMap.put("Summary Prospectus - Static", "Summary Prospectus - Static");
		 invenotryTypeDropDownMap.put("Supplement - POD", "Supplement - POD");
		 invenotryTypeDropDownMap.put("Stationary - Static", "Supplement - Static");
		 invenotryTypeDropDownMap.put("Whitepaper - POD", "Whitepaper - POD");
		 invenotryTypeDropDownMap.put("Whitepaper - Static", "Whitepaper - Static");	 
	}
	
	
	
	public static void generateColorsMap(){
		colorsDropDownMap.put("1/0", "1/0");
		colorsDropDownMap.put("1/1", "1/1");
		colorsDropDownMap.put("4/0", "4/0");
		colorsDropDownMap.put("4/1", "4/1");
		colorsDropDownMap.put("4/4", "4/4");
		colorsDropDownMap.put("1/4", "1/4");
	}
	
	
	public static void generatePageSizeMap(){
		pageSizeDropDownMap.put("8.5 x 11", "8.5 x 11");
		pageSizeDropDownMap.put("1/2\" x 1/2\"", "1/2\" x 1/2\"");
		pageSizeDropDownMap.put("11 x 17", "11 x 17");
		pageSizeDropDownMap.put("18 x 24 (poster)", "18 x 24 (poster)");
		pageSizeDropDownMap.put("20 x 5", "20 x 5");
		pageSizeDropDownMap.put("24 x 36 (poster)", "24 x 36 (poster)");
		pageSizeDropDownMap.put("8.25 x 10.75", "8.25 x 10.75");
		pageSizeDropDownMap.put("3.5 x 7", "3.5 x 7");
		pageSizeDropDownMap.put("3.5 x 8.5", "3.5 x 8.5");
		pageSizeDropDownMap.put("4 x 5.5", "4 x 5.5");
		pageSizeDropDownMap.put("9 x 11", "9 x 11");
		pageSizeDropDownMap.put("4 x 9.25", "4 x 9.25");
		pageSizeDropDownMap.put("4.25 x 2.75", "4.25 x 2.75");
		pageSizeDropDownMap.put("4.25 x 7.25", "4.25 x 7.25");
		pageSizeDropDownMap.put("5 x 3.75", "5 x 3.75");
		pageSizeDropDownMap.put("6 x 11", "6 x 11");
		pageSizeDropDownMap.put("6 x 4.25", "6 x 4.25");
		pageSizeDropDownMap.put("6 x 9", "20 x 5");
		pageSizeDropDownMap.put("7 x 5", "7 x 5");
		pageSizeDropDownMap.put("8.5 x 3.75", "8.5 x 3.75");
		pageSizeDropDownMap.put("8.5 x 5.5", "8.5 x 5.5");
		pageSizeDropDownMap.put("9 x 12", "9 x 12");
		pageSizeDropDownMap.put("6 x 4", "6 x 4");
		pageSizeDropDownMap.put("7.5 x 9", "7.5 x 9");
		pageSizeDropDownMap.put("7.75 x 9.25", "7.75 x 9.25");
		pageSizeDropDownMap.put("9 x 4.5", "9 x 4.5");
		pageSizeDropDownMap.put("8 x 9.25", "8 x 9.25");
		
		pageSizeDropDownMap.put("5.5 x 8.5", "5.5 x 8.5");
		pageSizeDropDownMap.put("5.5 X 8.5", "5.5 x 8.5");
		
		pageSizeDropDownMap.put("12 x 17", "12 x 17");
		pageSizeDropDownMap.put("8.5 x 12", "8.5 x 12");
		pageSizeDropDownMap.put("9.25 x 7.5", "9.25 x 7.5");
		
		pageSizeDropDownMap.put("10.75 x 16.50", "10.75 x 16.5");
		pageSizeDropDownMap.put("10.75 x 16.5", "10.75 x 16.5");
		
	}
	
	public static void generateStockMap(){
		stockDropDownMap.put("100# Text Dull", "100# Text Dull");
		stockDropDownMap.put("80# Text Dull", "80# Text Dull");
		stockDropDownMap.put("Label Stock", "Label Stock");
		stockDropDownMap.put("100# Cover Dull", "100# Cover Dull");
		stockDropDownMap.put("80# Cover Dull", "80# Cover Dull");
		stockDropDownMap.put("69# Text", "69# Text");
		stockDropDownMap.put("60# White Opaque", "60# White Opaque");
		stockDropDownMap.put("Trim", "Trim");
	}
	
	public static void generateFinishMap(){
		finishDropDownMap.put("Fold", "Fold");
		finishDropDownMap.put("Collate", "Collate");
		finishDropDownMap.put("Gate Fold", "Gate Fold");
		finishDropDownMap.put("Perforate", "Perforate");
		finishDropDownMap.put("Saddle-Stitch", "Saddle-Stitch");
		finishDropDownMap.put("Score", "Score");
		finishDropDownMap.put("Staple", "Staple");
		finishDropDownMap.put("Trim", "Trim");
		finishDropDownMap.put("None", "None");
	}
	
	public static void generateCoatingMap(){
		coatingDropDownMap.put("Dull", "Dull");
		coatingDropDownMap.put("None", "None");
	}
	
	public static void generateBindingMap(){
		bindingDropDownMap.put("Fold", "Fold");
		bindingDropDownMap.put("Perforate", "Perforate");
		bindingDropDownMap.put("Saddle-Stitch", "Saddle-Stitch");
		bindingDropDownMap.put("Score", "Score");
		bindingDropDownMap.put("Staple", "Staple");
		bindingDropDownMap.put("Wire-O", "Wire-O");
	}
	
	public static void generatePrintMethodMap(){
		printMethodDropDownMap.put("Offset", "Offset");
		printMethodDropDownMap.put("Simplex", "Simplex");
		printMethodDropDownMap.put("Duplex", "Duplex");
	}
	
	public static void generateCategoryFundType(){
	     fundTypeDropDownMap.put("Equity", "Equity");
	     fundTypeDropDownMap.put("Fixed Income", "Fixed Income");
		
	}
	
	public static void generateCategoryPieceType(){
          pieceTypeDropDownMap.put("Brochures", "Brochures");
          pieceTypeDropDownMap.put("Fact Sheets", "Fact Sheets");
          pieceTypeDropDownMap.put("Commentaries", "Commentaries");
          pieceTypeDropDownMap.put("White Papers", "White Papers");
          pieceTypeDropDownMap.put("Pitchbooks", "Pitchbooks");
		}
	
	public static void generateViewIn(){
		viewInDropDownMap.put("Fulfillment", "Fulfillment");
		viewInDropDownMap.put("Marketing campaigns", "Marketing campaigns");
		}
	
	public static void generateUserGroup(){
        userGroupDropDownMap.put("Marketing", "Marketing");
        userGroupDropDownMap.put("Continuing Education", "Continuing Education");
		}
	
	
	
	public static  void readAbsoluteDAMpaths(){
		 String csvFile = "C:/Users/YE20004956/Desktop/aem_dam_csv_importer/SIT_Dam_Absolute_Path_data.csv";
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = ",";
	        
	        try {

	            br = new BufferedReader(new FileReader(csvFile));
	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String[] csvoutput = line.split(cvsSplitBy);
	                
	                if(!csvoutput[1].equals("null")){
	                	finalDCsvpieceCodeDampathMap.put(csvoutput[0],csvoutput[1]);
	                }
	            }
	            
	            System.out.println(finalDCsvpieceCodeDampathMap.size());

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
	
	public static String removeDupicateTags(String rawTagString,boolean doRemoveSpace){
		String[] arry=null;
		if(doRemoveSpace){
		arry=rawTagString.replace(" ","").split(";");
		}else{
			arry=rawTagString.split(";");
		}
		
		//System.out.println(arry.length);
		Set<String> strArry=new HashSet<String>();
		
		for(String s:arry){
			strArry.add(s);	
		}
		//System.out.println(strArry.size());
		String finalString=String.join("|",strArry);
		
		//System.out.println(finalString);
		
		return finalString;
	}
	

/*public class VeritasMetadataBean {
	
	private String absTargetPath;
	
	private String pieceCode;
	
	private String formNumber;
	
	private String inventoryType;
	
	private String colors;
	
	private String pageSize;
	
	private String stock;
	
	private String binding;
	
	private String printMethod;
	
	private String finish;
	
	private String coating;
	
	private String effectiveDate;
	
	private String formOwner;
	
	private String firmExcludeInclude;
	
	private String firmTags;
	
	private String includeFirmTagName;
	
	private String specialMessage;
	
	private String veritasPieceCode;
	
	private String maxCopiesAvailable;
	
	private String categoryFundType;
	
	private String categoryPieceType;
	
	private String viewIn;
	
	private String userGroup;
	
	private String ofPages;
	

	
	
	public String getCategoryPieceType() {
		return categoryPieceType;
	}

	public void setCategoryPieceType(String categoryPieceType) {
		this.categoryPieceType = categoryPieceType;
	}

	public String getPieceCode() {
		return pieceCode;
	}

	public void setPieceCode(String pieceCode) {
		this.pieceCode = pieceCode;
	}

	public String getFormNumber() {
		return formNumber;
	}

	public void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
	}

	public String getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getColors() {
		return colors;
	}

	public void setColors(String colors) {
		this.colors = colors;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public String getPrintMethod() {
		return printMethod;
	}

	public void setPrintMethod(String printMethod) {
		this.printMethod = printMethod;
	}

	public String getFinish() {
		return finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	public String getCoating() {
		return coating;
	}

	public void setCoating(String coating) {
		this.coating = coating;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getFormOwner() {
		return formOwner;
	}

	public void setFormOwner(String formOwner) {
		this.formOwner = formOwner;
	}

	public String getFirmExcludeInclude() {
		return firmExcludeInclude;
	}

	public void setFirmExcludeInclude(String firmExcludeInclude) {
		this.firmExcludeInclude = firmExcludeInclude;
	}

	public String getIncludeFirmTagName() {
		return includeFirmTagName;
	}

	public void setIncludeFirmTagName(String includeFirmTagName) {
		this.includeFirmTagName = includeFirmTagName;
	}

	public String getSpecialMessage() {
		return specialMessage;
	}

	public void setSpecialMessage(String specialMessage) {
		this.specialMessage = specialMessage;
	}

	public String getVeritasPieceCode() {
		return veritasPieceCode;
	}

	public void setVeritasPieceCode(String veritasPieceCode) {
		this.veritasPieceCode = veritasPieceCode;
	}

	public String getAbsTargetPath() {
		return absTargetPath;
	}

	public void setAbsTargetPath(String absTargetPath) {
		this.absTargetPath = absTargetPath;
	}

	public String getFirmTags() {
		return firmTags;
	}

	public void setFirmTags(String firmTags) {
		this.firmTags = firmTags;
	}

	public String getMaxCopiesAvailable() {
		return maxCopiesAvailable;
	}

	public void setMaxCopiesAvailable(String maxCopiesAvailable) {
		this.maxCopiesAvailable = maxCopiesAvailable;
	}

	public String getCategoryFundType() {
		return categoryFundType;
	}

	public void setCategoryFundType(String categoryFundType) {
		this.categoryFundType = categoryFundType;
	}

	public String getViewIn() {
		return viewIn;
	}

	public void setViewIn(String viewIn) {
		this.viewIn = viewIn;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getOfPages() {
		return ofPages;
	}

	public void setOfPages(String ofPages) {
		this.ofPages = ofPages;
	}
	
}*/


}
