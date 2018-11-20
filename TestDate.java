package myJavaProject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class TestDate {

	public static void main(String[] args) {
		
		final Calendar dateCalanderValue = Calendar.getInstance();
		
		String Date_Format="MM/dd/yyyy hh:mm:ss a";
		
		SimpleDateFormat sdfMMDDYYYY=new SimpleDateFormat(Date_Format);
		
		Date now=new Date();
		
		System.out.println(sdfMMDDYYYY.format(now));
		
		String metadata_Date="11/18/2018 17:24:55 PM";
		
		String[] splitDate=metadata_Date.split(" ");
		
		String splitStringDate=splitDate[0];
		
		System.out.println(splitDate[0]);
		
		String MMDDYYYY_PATTERN = "^([0]?[1-9]|[1][0-2])[/]([0]?[1-9]|"
	 			+ "[1|2][0-9]|[3][0|1])[/]([0-9]{4}|[0-9]{2})$";
		
		List<String> myList1=null;
		  List<String> myList2=null;
		
		try {
			Date prismRevisonDate = sdfMMDDYYYY.parse(metadata_Date);
			
			System.out.println("Prism Revision Date : "+prismRevisonDate);
			dateCalanderValue.setTime(prismRevisonDate);
			System.out.println("dateCalanderValue : "+dateCalanderValue);
			
			//--
			/* String sDate1="11/08/2018 05:24:55 PM";  
			    Date date1=new SimpleDateFormat("MM-dd-yyyy").parse(sDate1);  
			    System.out.println(sDate1+"\t"+date1);*/
			    
			    myList1=Arrays.asList("File","FileName","c");
			    myList2=Arrays.asList("File","FileName1213","c");
			    
			    List<String> toReturn = new ArrayList<String>(myList2);
			    toReturn.removeAll(myList1);
			    
			   
			    
			   // myList2.removeAll(myList1);
			    
			    System.out.println("result List size :"+toReturn.size() +"and the values are :"+toReturn);
			//--
			Pattern mmddyyyyPattern = Pattern.compile(MMDDYYYY_PATTERN);
			
			Matcher dateMatcherMMDDYYYY = null;
			
			if (StringUtils.isNotEmpty(metadata_Date)) {
				   
				dateMatcherMMDDYYYY = mmddyyyyPattern.matcher(splitStringDate);
				
				if (dateMatcherMMDDYYYY.find()) {
					prismRevisonDate = sdfMMDDYYYY.parse(metadata_Date);
					dateCalanderValue.setTime(prismRevisonDate);
					
					System.out.println("final prism revision Date : "+ prismRevisonDate);
				}else{
					System.out.println("Not a valid Date");
				}
					
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		

	}

}
