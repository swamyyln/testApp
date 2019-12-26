package com.thf.users;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jcr.Repository;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ReadJcrUserDetails {

	
public static void main(String[] args) {
		
		try {
			ReadJcrUserDetails thfUserReferences=new ReadJcrUserDetails();
			Row row =null;
            int i=0;
       
            List<UserNameDetailsBean> userBeanList=new ArrayList<UserNameDetailsBean>();
            
           
            //local aem
			String aemUrl = "http://"+"localhost:4502" +"/crx/server" ;
			
			//Dev
			//String aemUrl = "http://"+"10.39.3.18:5402" +"/crx/server" ;
			
			//SIT
			//String aemUrl = "http://"+"10.38.3.12:4502" +"/crx/server" ;
			
			//PROD
			//String aemUrl = "http://"+"10.37.3.7:4502" +"/crx/server" ;
			
	        //Create a connection to the CQ repository running on local host
	        Repository repository = JcrUtils.getRepository(aemUrl);

	        //Create a Session
	       //javax.jcr.Session session = repository.login( new SimpleCredentials("admin", "Sandb0x@123".toCharArray()));
	       
	       //javax.jcr.Session session = repository.login( new SimpleCredentials("admin", "AwsAuth0r@sit".toCharArray()));
	       //javax.jcr.Session session = repository.login( new SimpleCredentials("admin", "Wipro@123".toCharArray()));
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
	        	   String referenceType=null;
	        	   String userName=null;
	               String firstName=null;
	               String lastName=null;
	               String emailId=null;
	               String nodePath=null;
	               String jcrCreatedDate=null;
	               String jcrUUID=null;
	               
	               javax.jcr.Node node = nodeIter.nextNode();
	               
	               if(!(node.getPath().contains("/home/users/system/")) || !(node.getProperty("jcr:createdBy").getString().equalsIgnoreCase("system"))){
	        	   UserNameDetailsBean userNameDetailsBean=thfUserReferences.invokeuserNameDetailsBean();
	        	   
	        	        i++;
	        	       
	          
	            
	            if(node.hasProperty("jcr:created")){
	            	String rawDate=node.getProperty("jcr:created").getString();
	            	if(rawDate.contains("T")){
	            		jcrCreatedDate=rawDate.split("T")[0];
	            	}
	            }
	            
	            if(node.hasNode("profile")){
	            javax.jcr.Node node1 =node.getNode("profile");
	            
	            
	            if(node1.hasProperty("givenName"))
		          {
		          firstName=node1.getProperty("givenName").getString();
		          }
		          
		          if(node1.hasProperty("familyName"))
		          {
		          lastName=node1.getProperty("familyName").getString();
		          }
		          
		          if(node1.hasProperty("email"))
		          {
		        	  emailId=node1.getProperty("email").getString();
		          }  
		          
	            }
	           if(node.hasProperty("rep:principalName")) {
	            userName=node.getProperty("rep:principalName").getString();
	           }        
	            if(node.getProperty("jcr:primaryType")!=null)
	            {
	            referenceType= node.getProperty("jcr:primaryType").getString();
	            }
	            if(node.hasProperty("rep:principalName"))
	            {
		            if(node.getProperty("rep:principalName")!=null)
		            {
		            userName=node.getProperty("rep:principalName").getString();
		            }
	            }
	            if(node.hasProperty("jcr:uuid")) {
	            	jcrUUID=node.getProperty("jcr:uuid").getString();
		           }  
	            
	            //System.out.println("id : "+i+"  Username :"+userName +"   :: Node Path --> "+node.getPath());
	         
	      // System.out.println("FirstName :"+firstName);
	            
	            //--get the User groups
	            String myQuery2="select  group.* from [rep:Group] as group where group.[rep:members]=\""+jcrUUID+"\"";
				  
				//prepare jcr-sql2 query
		        javax.jcr.query.Query queryJcr2=queryManager.createQuery(myQuery2, "JCR-SQL2");
		        
				//execute your query
		        javax.jcr.query.QueryResult queryRes2=queryJcr2.execute();
		          
		        //Iterate your result node here
		           javax.jcr.NodeIterator nodeIter2 = queryRes2.getNodes();
		           List<String> userGroupList=new ArrayList<String>();
		           System.out.println("before while loop2");
		           while ( nodeIter2.hasNext() ) {
		        	   javax.jcr.Node node2 = nodeIter2.nextNode();
		        	   
		        	   if(node2.hasProperty("jcr:primaryType")){
		        		   if(node2.getProperty("jcr:primaryType").getString().equalsIgnoreCase("rep:Group")){
		        			   if(node2.hasProperty("rep:principalName")){
		        				   String groupName=node2.getProperty("rep:principalName").getString();
		        				  
		        				   userGroupList.add(groupName);
		        			   }
		        		   }
		        	   }
		        	  
		           }
		           System.out.println(userName + "memberOf "+userGroupList);
		           System.out.println("After while loop2");
	            //----------fetch groups end
	        
	        userNameDetailsBean.setId(i);
	        userNameDetailsBean.setUserName(userName);
	        userNameDetailsBean.setFirstName(firstName);
	        userNameDetailsBean.setLastName(lastName);
	        userNameDetailsBean.setEmailid(emailId);
	        userNameDetailsBean.setNodePath(node.getPath());
	        userNameDetailsBean.setJcrCreatedDate(jcrCreatedDate);
	        userNameDetailsBean.setGroups(String.join(", ", userGroupList));
	        
	        userBeanList.add(userNameDetailsBean);
	           }
	           }
	           System.out.println("user list size:"+userBeanList.size());
	           System.out.println("after while loop");
	   		
	            Workbook wb = new HSSFWorkbook(); 
		        Sheet sheet = wb.createSheet("User_Details");
		        
		        for (int m=0;m<userBeanList.size();m++)
		        {
		        	if(m==0){
		        	    row = sheet.createRow((short) (m));

		   		        row.createCell(0).setCellValue("ID");
		   		        row.createCell(1).setCellValue("USER_NAME");
		   		        row.createCell(2).setCellValue("FIRST_NAME");
		   		        row.createCell(3).setCellValue("LAST_NAME");
		   		        row.createCell(4).setCellValue("MAIL_ID");
		   		        row.createCell(5).setCellValue("JCR_CREATED_ON");
		   		        row.createCell(6).setCellValue("JCR_PATH");
		   		     row.createCell(7).setCellValue("Groups_Assigned");
		        	}
		        row = sheet.createRow((short) (m+1));

		        row.createCell(0).setCellValue(userBeanList.get(m).getId());
		        row.createCell(1).setCellValue(userBeanList.get(m).getUserName());
		        row.createCell(2).setCellValue(userBeanList.get(m).getFirstName());
		        row.createCell(3).setCellValue(userBeanList.get(m).getLastName());
		        row.createCell(4).setCellValue(userBeanList.get(m).getEmailid());
		        row.createCell(5).setCellValue(userBeanList.get(m).getJcrCreatedDate());
		        row.createCell(6).setCellValue(userBeanList.get(m).getNodePath());
		        row.createCell(7).setCellValue(userBeanList.get(m).getGroups());
		        
		        }
		        
		        //D:\swamy\Aem_Author_lock
		       FileOutputStream fileOut = new FileOutputStream("/home/ec2-user/Aem_author_lock/Prod_UserDetails_Report.xls");
		        //FileOutputStream fileOut = new FileOutputStream("C:/Users/YE20004956/Desktop/local_UserDetails_Report1.xls");
		        //FileOutputStream fileOut = new FileOutputStream("C:/Users/user/Desktop/local_UserDetails_Report1.xls");
		        wb.write(fileOut);
		        fileOut.close();		
			System.out.println("End Of The List");		   		
	        session.logout();
			}
			catch(Exception e) {
				e.printStackTrace();
				
			}
	
	}
	 
	public  UserNameDetailsBean invokeuserNameDetailsBean() {
		 UserNameDetailsBean userNameDetailsBean=new UserNameDetailsBean();
		 return userNameDetailsBean;
	}
	
	  class UserNameDetailsBean {

			public UserNameDetailsBean() {
				
			}

			private int id;
			private String userName;
			private String firstName;
			private String lastName;
			private String emailid;
			private String nodePath;
			private String jcrCreatedDate;
			private String groups; 
			
			public int getId() {
				return id;
			}
			public void setId(int id) {
				this.id = id;
			}
			public String getUserName() {
				return userName;
			}
			public void setUserName(String userName) {
				this.userName = userName;
			}
			public String getFirstName() {
				return firstName;
			}
			public void setFirstName(String firstName) {
				this.firstName = firstName;
			}
			public String getLastName() {
				return lastName;
			}
			public void setLastName(String lastName) {
				this.lastName = lastName;
			}
			public String getEmailid() {
				return emailid;
			}
			public void setEmailid(String emailid) {
				this.emailid = emailid;
			}
			public String getNodePath() {
				return nodePath;
			}
			public void setNodePath(String nodePath) {
				this.nodePath = nodePath;
			}
			public String getJcrCreatedDate() {
				return jcrCreatedDate;
			}
			public void setJcrCreatedDate(String jcrCreatedDate) {
				this.jcrCreatedDate = jcrCreatedDate;
			}
			public String getGroups() {
				return groups;
			}
			public void setGroups(String groups) {
				this.groups = groups;
			}
			
}

	}

