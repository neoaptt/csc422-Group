//

//system imports
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;

import java.io.IOException;

// project imports (if any)

// Tester for the Department DB
//====================================================================================
public class DepartmentDBTester {
	
	//-------------------------------------------------------------------------------
	// Printing the result-set in a readable format
	//-------------------------------------------------------------------------------
	public static Scanner sc = new Scanner(System.in);
	private static void printValues(Vector<Properties> data)
	{
		// Now, we have to print out these rows in a user-understandable form
		if ((data == null) || (data.size() == 0))
		{
			System.out.println("No results were returned from database for this query");
		}
		else
		{
			// Find out how many values you got back
			int numValues = data.size();
			
			// Now go thru the entire 'data' Vector, get each Properties object out of it
			// and print out the contents of the Properties object
			for (int cnt = 0; cnt < numValues; cnt++)
			{
				Properties nextDataRow = data.elementAt(cnt);
				
				Enumeration columnNames = nextDataRow.propertyNames();
				while (columnNames.hasMoreElements())
				{
					String colName = (String)columnNames.nextElement();
					String colValue = nextDataRow.getProperty(colName);
					System.out.println(colName + " = " + colValue);
				}
				System.out.println("---------------------------------------------");
			}
			System.out.println("==============================================");
		}
	}
	//-------------------------------------------------------------------------------
	// This is a generic SELECT statement when the user knows how to write SQL code
	//-------------------------------------------------------------------------------
	public static void retrieveFromTable(String queryString)
	{
		// First, set up an instance of the DatabaseAccessor class
		DatabaseAccessor dbAcc = new DatabaseAccessor();
		

		// Now that you have created the query string, you set that on the DatabaseAccessor
		// object you created using the 'setSQLStatement()' method as shown below 
		dbAcc.setSQLStatement(queryString);
		
		// Then invoke the method 'executeSQLSelectStatement()' on the DatabaseAccessor object
		// as shown below to run the query. The result of running this query is a Vector of
		// Properties objects. Each Properties object in this Vector contains the data from
		// one of the db table rows matching the query
		Vector<Properties> returnedValues = dbAcc.executeSQLSelectStatement();
		
		// Print the results
		printValues(returnedValues);
	}
	
	//-------------------------------------------------------------------------------
	// This is a retrieval from a specific table. 
	//-------------------------------------------------------------------------------
	public static void retrieveFromDepartment(String departmentName)
	{
		// First, set up an instance of the DatabaseAccessor class
		DatabaseAccessor dbAcc = new DatabaseAccessor();
		
		// Then, set up the query as a Java String object, including the parameters
		// needed (such as 'departmentName' from the method's parameter list)
		
		String queryString = "SELECT * FROM DepartmentChairs " + " WHERE (DepartmentName = '" +
			departmentName + "')";
		System.out.println("DEBUG: retrieveFromTable query string: " + queryString);
		
		// Now that you have created the query string, you set that on the DatabaseAccessor
		// object you created using the 'setSQLStatement()' method as shown below 
		dbAcc.setSQLStatement(queryString);
		
		// Then invoke the method 'executeSQLSelectStatement()' on the DatabaseAccessor object
		// as shown below to run the query. The result of running this query is a Vector of
		// Properties objects. Each Properties object in this Vector contains the data from
		// one of the db table rows matching the query
		Vector<Properties> returnedValues = dbAcc.executeSQLSelectStatement();
		
		// Print the results
		printValues(returnedValues);
	}
	
	//----------------------------------------------------------------------------
	
	public static void insertIntoTable(String insertQueryString )
	{
		DatabaseMutator dbMut = new DatabaseMutator();
		
		dbMut.setSQLStatement(insertQueryString);
		Integer returnedValue = dbMut.executeSQLMutateStatement();
		
		if (returnedValue != 1)
			System.out.println("Error in db insertion");
		else
			System.out.println("Row inserted successfully");
	}
	
	//----------------------------------------------------------------------------
	public static void insertIntoDeptChairs(String deptName, String office, String chairName,
		String secretaryName, String chairPhone, String secretaryPhone, String chairEmail,
		String secretaryEmail)
	{
		DatabaseMutator dbMut = new DatabaseMutator();
		
		String queryString = "INSERT INTO DepartmentChairs " + 
			" (DepartmentName, Office, ChairName, SecretaryName, ChairPhone, SecretaryPhone, ChairEMail, SecretaryEMail) " +
			" VALUES " +
			"('" + 
				deptName + "', '" + 
				office + "', '" + 
				chairName + "', '" +
				secretaryName + "', '" +
				chairPhone + "', '" +
				secretaryPhone + "', '" +
			    chairEmail + "', '" +
				secretaryEmail + 
			"')";
			
		System.out.println("INSERT request: " + queryString);
		System.out.println();
		
		dbMut.setSQLStatement(queryString);
		Integer returnedValue = dbMut.executeSQLMutateStatement();
		
		if (returnedValue != 1)
			System.out.println("Error in db insertion");
		else
			System.out.println("Row inserted successfully");
		
	}
	
	//----------------------------------------------------------------------------
	public static void updateTable(String updateQueryString)
	{
		DatabaseMutator dbMut = new DatabaseMutator();
		
		dbMut.setSQLStatement(updateQueryString);
		Integer returnedValue = dbMut.executeSQLMutateStatement();
		
		if (returnedValue < 0)
			System.out.println("Error in db update");
		else
			System.out.println("Row updated successfully");
	}
	
	//------------------------------------------------------------------------
	public static void updateDeptChairs(String newChairName, String deptName)
	{
		DatabaseMutator dbMut = new DatabaseMutator();
		
		String queryString = "UPDATE DepartmentChairs " + 
			" SET ChairName = '" + newChairName + "' " + 
			" WHERE (DepartmentName = '" + deptName + "')";
			
		System.out.println("UPDATE request: " + queryString);
		System.out.println();
		
		dbMut.setSQLStatement(queryString);
		Integer returnedValue = dbMut.executeSQLMutateStatement();
		
		if (returnedValue < 0)
			System.out.println("Error in db update");
		else
			System.out.println("Row updated successfully");
		
	}
	
	//------------------------------------------------------------------------
	public static void deleteFromTable(String deleteQueryString)
	{
		DatabaseMutator dbMut = new DatabaseMutator();
		
		dbMut.setSQLStatement(deleteQueryString);
		Integer returnedValue = dbMut.executeSQLMutateStatement();
		
		if (returnedValue < 0)
			System.out.println("Error in db Delete");
		else
			System.out.println("Row deleted successfully");
	}
	
	//------------------------------------------------------------------------
	public static void deleteFromDeptChairs(String deptName)
	{
		DatabaseMutator dbMut = new DatabaseMutator();
		
		String queryString = "DELETE FROM DepartmentChairs " + 
			" WHERE (DepartmentName = '" + deptName + "')";
			
		System.out.println("DELETE request: " + queryString);
		System.out.println();
		
		dbMut.setSQLStatement(queryString);
		Integer returnedValue = dbMut.executeSQLMutateStatement();
		
		if (returnedValue < 0)
			System.out.println("Error in db Delete");
		else
			System.out.println("Row deleted successfully");
		
	}
	
	//-----------------------------------------------------------------------------
	// Methods to test individual database operations: SELECT, INSERT, DELETE, UPDATE
	//-----------------------------------------------------------------------------
	private static void retrieveDeptInfo()
	{
		System.out.println("Attempt to retrieve Department information with a SELECT statement");
		System.out.println("Enter the name of the department: ");
		String deptName = sc.nextLine();
		retrieveFromDepartment(deptName);
	}
	
	private static void insertNewDepartment()
	{
		System.out.println("Demonstrating insert into database ..");
			//insertIntoDeptChairs();
		System.out.println("Enter the name of the department: ");
		String deptName = sc.nextLine();
		System.out.println("Enter office location for department: ");
		String officeLoc = sc.nextLine();
		System.out.println("Enter the name of the department chair: ");
		String chairName = sc.nextLine();
		System.out.println("Enter the name of the department secretary: ");
		String secyName = sc.nextLine();
		System.out.println("Enter the phone number the department chair: ");
		String chairPhone = sc.nextLine();
		System.out.println("Enter the phone number the department secretary: ");
		String secyPhone = sc.nextLine();
		System.out.println("Enter the e-mail address the department chair: ");
		String chairEmail = sc.nextLine();
		System.out.println("Enter the e-mail address the department secretary: ");
		String secyEmail = sc.nextLine();
		insertIntoDeptChairs(deptName, officeLoc, chairName, secyName, chairPhone, secyPhone,
			chairEmail, secyEmail);

		/*
		String insertQueryString = "INSERT INTO DepartmentChairs VALUES " + 
		   "(" +
			deptName + ", "
			officeLoc + ", "
			chairName + ", "
			secyName + ", "
			chairPhone + ", "
			secyPhone + ", "
			ChairEmail + ", "
			secyEmail + ");";
		insertIntoTable(insertQueryString );
                */
	}
	private static void updateADepartment()
	{
		System.out.println("Attempting update into database ..");
		System.out.println("Enter the name of the department whose chair you want to change: ");
		String deptName = sc.nextLine();
		System.out.println("Enter the name of the new chair of this department: ");
		String chairName = sc.nextLine();
		updateDeptChairs(chairName, deptName);
	}
	private static void deleteADepartment()
	{
		System.out.println("Attempting delete into database ..");
		System.out.println("Enter the name of the department whose info you want to delete: ");
		String deptName = sc.nextLine();
		deleteFromDeptChairs(deptName);
	}
	
	
	private static void handleInteractiveQueries()
	{
		
		String building = "";
		String department = "";
		String office = "";
		String query = "";
		String[] englishQuestions = {
			"What are the departments located in the <building> Building?",
			"What is the name and email of the <department> Chairperson?",		
			"Give me all the information about the <department> department.",		
			"How many Departments are there in the School?",		
			"List the secretary names, phones and e-mails of all departments in <building> building",
			"Insert a new department",
			"Update a department information",
			"Delete a department"
		};
		System.out.println("\n\n---------------------------------------------------");
		System.out.println("Which query do you want answered? Enter 1 .." + englishQuestions.length);
		System.out.println("Enter 0 (zero) to quit \n");
		System.out.println("---------------------------------------------------");
		for (int k = 0; k < englishQuestions.length; k++)
		{
			System.out.println((k+1) + "   "+englishQuestions[k]+"\n");
		}
		System.out.println("---------------------------------------------------");
		String answerString = sc.nextLine();
		int answer = Integer.parseInt(answerString);
		
		if (answer == 0) System.exit(0);
		if (answer == 1)
		{
			System.out.println("Enter the building Name");
			building = sc.nextLine();
			query = "SELECT DepartmentName, Office FROM  DepartmentChairs WHERE Office LIKE  '%" + 
			building + "%';";
		}
		else if (answer == 2)
		{
			System.out.println("Enter the Department Name");
			department = sc.nextLine();
			query = "SELECT ChairName, ChairEmail FROM  `DepartmentChairs` WHERE DepartmentName LIKE '%" + 
			department +"%';";			
		}
		else if (answer == 3)
		{
			System.out.println("Enter the Department Name");
			department = sc.nextLine();
			query = "SELECT * FROM `DepartmentChairs` WHERE DepartmentName LIKE '%" + 
			department +"%';";
			
		} 
		else if (answer == 4)
		{
			query = "SELECT COUNT( * ) FROM  `DepartmentChairs`;";			
		} 
		else if (answer == 5)
		{
			System.out.println("Enter the building Name");
			building = sc.nextLine();
			query = "SELECT SecretaryName, SecretaryPhone, SecretaryEMail FROM `DepartmentChairs` WHERE Office " +
			"LIKE '%"+building+"%';";
		} 
		if (answer >= 1 && answer <= 5)
		{
			DatabaseAccessor dbAcc = new DatabaseAccessor();
			dbAcc.setSQLStatement(query);
			Vector<Properties> returnedValues = dbAcc.executeSQLSelectStatement();		
			printValues(returnedValues);
		}
		else
		{
			if (answer == 6)
			{
				insertNewDepartment();	
			}
			else if (answer == 7)
			{
				updateADepartment();
			}
			else if (answer == 8)
			{
				deleteADepartment();
			}
		}	
	}
//	public static void main(String[] args)
//	{
//		String goOn = "Y";
//		goOn = goOn.toLowerCase();
//		
//		while (goOn.startsWith("y"))
//		{
//			handleInteractiveQueries();
//			System.out.println("Do you wish to continue (y/n)?");
//			goOn = sc.nextLine();
//			goOn = goOn.toLowerCase();
//		}
//	}
}
