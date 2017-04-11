//system imports

import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;
import java.io.IOException;

// Tester for the Java-DB Connection
//====================================================================================
public class CollegeDBTester {

    //-------------------------------------------------------------------------------
    // Printing the result-set in a readable format
    //-------------------------------------------------------------------------------
    public static Scanner sc = new Scanner(System.in);

    private static void printValues(Vector<Properties> data) {
        // Now, we have to print out these rows in a user-understandable form
        if ((data == null) || (data.size() == 0)) {
            System.out.println("No results were returned from database for this query");
        } else {
            // Print the headers
            System.out.println("==============================================");
            Properties p1 = data.firstElement();
            Enumeration props1 = p1.propertyNames();
            while (props1.hasMoreElements()) {
                System.out.print(props1.nextElement() + "\t");
            }
            System.out.println();
            System.out.println("----------------------------------------------");

            // Now go thru the entire 'data' Vector, get each Properties object out of it
            // and print out the contents of the Properties object
            for (Properties p : data) {
                Enumeration props = p.propertyNames();
                while (props.hasMoreElements()) {
                    System.out.print(p.getProperty((String) (props.nextElement())) + "\t");
                }
                System.out.println();
            }
            System.out.println("==============================================");
        }
    }
    //-------------------------------------------------------------------------------
    // This is a generic SELECT statement when the user knows how to write SQL code
    //-------------------------------------------------------------------------------

    public static void retrieveFromTable(String queryString) {
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

    //----------------------------------------------------------------------------
    public static void insertIntoTable(String insertQueryString) {
        DatabaseMutator dbMut = new DatabaseMutator();

        dbMut.setSQLStatement(insertQueryString);
        Integer returnedValue = dbMut.executeSQLMutateStatement();

        if (returnedValue != 1) {
            System.out.println("Error in db insertion");
        } else {
            System.out.println("Row inserted successfully");
        }
    }

    //----------------------------------------------------------------------------
    public static void updateTable(String updateQueryString) {
        DatabaseMutator dbMut = new DatabaseMutator();

        dbMut.setSQLStatement(updateQueryString);
        Integer returnedValue = dbMut.executeSQLMutateStatement();

        if (returnedValue < 0) {
            System.out.println("Error in db update");
        } else {
            System.out.println("Row updated successfully");
        }
    }

    //------------------------------------------------------------------------
    public static void deleteFromTable(String deleteQueryString) {
        DatabaseMutator dbMut = new DatabaseMutator();

        dbMut.setSQLStatement(deleteQueryString);
        Integer returnedValue = dbMut.executeSQLMutateStatement();

        if (returnedValue < 0) {
            System.out.println("Error in db Delete");
        } else {
            System.out.println("Row deleted successfully");
        }
    }

    public static void handleQuery1() {
        /*Show all the information from Professor table*/
        retrieveFromTable("SELECT * FROM PROFESSOR_15;");
    }

    public static void handleQuery2() {
        /*Show the BannerId and Names of all students with this <status>*/
        retrieveFromTable("SELECT * FROM ;");
    }

    public static void handleQuery3() {
        /*Show the professor id, professor name, and Department for this <dept id>*/
        retrieveFromTable("SELECT * FROM ;");
    }

    public static void handleQuery4() {
        /*Show the Professor Name, Course Name, Semester and Section for all courses in this <deptId>.*/
        retrieveFromTable("SELECT p.ProfessorName, c.CourseName, t.TA_Semester, t.TA_Section FROM PROFESSOR_15 p JOIN TEACHING_ASSIGNMENT_15 t ON t.ProfessorId = p.ProfessorId JOIN COURSE_15 c ON c.CourseCode = t.CourseCode WHERE p.DepartmentId = 'CSC';");
    }

    public static void handleQuery5() {
        /*Show the Student name, Course Name, Semester and Section and grade for the student with this <bannerId> */
        retrieveFromTable(
                "SELECT "
                + "    s.StudentName,"
                + "    c.CourseName,"
                + "    t.TR_Semester,"
                + "    t.TR_Section,"
                + "    t.TR_Grade"
                + " FROM"
                + "    STUDENT_15 s"
                + "        JOIN"
                + "    TRANSCRIPT_15 t ON t.StudentId = s.BannerId"
                + "        JOIN"
                + "    COURSE_15 c ON c.CourseCode = t.CourseCode"
                + " WHERE"
                + "    s.BannerId = '101';");
    }

    public static void handleQuery6() {
        /*Insert a new department*/
        insertIntoTable("INSERT INTO `DEPARTMENT_15` (`DepartmentId`, `DepartmentName`) VALUES ('CIS', 'Computer Information Systems');");
    }

    public static void handleQuery7() {
        /*Update a Student Status*/
        updateTable("UPDATE `STUDENT_15` SET `Status`='Junior' WHERE `BannerId`='102';");
    }

    public static void handleQuery8() {
        /*Delete a department*/
        deleteFromTable("DELETE FROM `DEPARTMENT_15` WHERE `DepartmentId`='CIS';");
    }

    private static void handleInteractiveQueries() {
        String building = "";
        String department = "";
        String office = "";
        String query = "";
        String[] englishQuestions = {
            "Show all the information from Professor table",
            "Show the BannerId and Names of all students with this <status>",
            "Show the professor id, professor name, and Department for this <dept id>",
            "Show the Professor Name, Course Name, Semester and Section for all courses in"
            + " this <deptId>.",
            "Show the Student name, Course Name, Semester and Section and grade for the "
            + " student with this <bannerId>",
            "Insert a new department",
            "Update a Student Status",
            "Delete a department"
        };
        System.out.println("\n\n---------------------------------------------------");
        System.out.println("Which query do you want answered? Enter 1 .."
                + englishQuestions.length);
        System.out.println("Enter 0 (zero) to quit \n");
        System.out.println("---------------------------------------------------");
        for (int k = 0; k < englishQuestions.length; k++) {
            System.out.println((k + 1) + "   " + englishQuestions[k] + "\n");
        }
        System.out.println("---------------------------------------------------");
        String answerString = sc.nextLine();
        int answer = Integer.parseInt(answerString);

        if (answer == 0) {
            System.exit(0);
        }
        if (answer == 1) {
            handleQuery1();
        } else if (answer == 2) {
            handleQuery2();
        } else if (answer == 3) {
            handleQuery3();
        } else if (answer == 4) {
            handleQuery4();
        } else if (answer == 5) {
            handleQuery5();
        } else if (answer == 6) {
            handleQuery6();
        } else if (answer == 7) {
            handleQuery7();
        } else if (answer == 8) {
            handleQuery8();
        }

    }

    public static void main(String[] args) {
        String goOn = "Y";
        goOn = goOn.toLowerCase();

        while (goOn.startsWith("y")) {
            handleInteractiveQueries();
            System.out.println("Do you wish to continue (y/n)?");
            goOn = sc.nextLine();
            goOn = goOn.toLowerCase();
        }
    }
}
