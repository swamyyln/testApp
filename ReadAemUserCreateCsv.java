package com.thf.users;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jcr.Repository;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.poi.ss.usermodel.Row;

import com.thf.users.ReadJcrUserDetails.UserNameDetailsBean;

public class CreateCsvFile {

	
	
	private static void generateCsvFile(String fileName) {

	  FileWriter writer = null;

	 try {
		
            int i=0;
       
            Set<String> userNamesSet=new HashSet<String>();
            
           
            //local aem
			String aemUrl = "http://"+"localhost:4502" +"/crx/server" ;
			
			//Dev
			//String aemUrl = "http://"+"10.39.3.18:5402" +"/crx/server" ;
			
			
			
		
			
	        //Create a connection to the CQ repository running on local host
	        Repository repository = JcrUtils.getRepository(aemUrl);

	        //Create a Session
	       //javax.jcr.Session session = repository.login( new SimpleCredentials("admin", "Sandb0x@123".toCharArray()));
	      
	    
	      javax.jcr.Session session = repository.login( new SimpleCredentials("admin", "admin".toCharArray()));
	       
	       //get the query manager from session workspace
	        javax.jcr.query.QueryManager queryManager=session.getWorkspace().getQueryManager();
	       
		    //write your query string
			String myQuery="SELECT * FROM [rep:User] as a where ISDESCENDANTNODE([/home/users]) and NOT a.[jcr:primaryType]='rep:SystemUser'";
			  
			//prepare jcr-sql2 query
	        javax.jcr.query.Query queryJcr=queryManager.createQuery(myQuery, "JCR-SQL2");
	        
			//execute your query
	        javax.jcr.query.QueryResult queryRes=queryJcr.execute();
	          
	        //Iterate your result node here
	           javax.jcr.NodeIterator nodeIter = queryRes.getNodes();
	           
	           System.out.println("before while loop");
	           System.out.println("total node size :"+queryRes.getNodes().getSize());
	           while ( nodeIter.hasNext() ) {
	        	
	        	   String userName=null;
	              
	               javax.jcr.Node node = nodeIter.nextNode();
	               
	               
	               if(!(node.getPath().contains("system") )){
	            	   System.out.println(node.getPath());
	            	   if(node.hasProperty("rep:principalName"))
	   	            {
	   		            if(node.getProperty("rep:principalName")!=null)
	   		            {
	   		            userName=node.getProperty("rep:principalName").getString();
	   		            }
	   	            }
	            	   //anonymous,admin,adthf users elimintated
	            	   userNamesSet.add(userName);
	        	 }  
	           }

	     writer = new FileWriter(fileName);
	     writer.append("Login_Id");
	     writer.append(',');
	     writer.append("Badpassword_count");
	     writer.append(',');
	     writer.append("LAST_MODIFIED_DATE");
	     writer.append('\n');

	     for(String str:userNamesSet){
	     writer.append(str);
	     writer.append(',');
	     writer.append("0");
	     writer.append(',');
	     writer.append("NA");
	     writer.append('\n');
	     }


	     System.out.println("CSV file is created...");

	           
	           
	 } catch (IOException e) {
	     e.printStackTrace();
	  } catch (Exception e) {
		     e.printStackTrace();
		  } finally {
	        try {
	      writer.flush();
	      writer.close();
	        } catch (IOException e) {
	      e.printStackTrace();
	}
	}
	}

	public static void main(String[] args) {

	  //String location = "C:/Users/YE20004956/Desktop/August_CR_svn_branch_2019/newCsvFile.csv";
		String location="/home/ec2-user/Aem_author_lock/SIT_login_users_list.csv";
	    generateCsvFile(location);

	}
}
