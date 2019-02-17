
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.io.*;
import java.util.*;
public class Main
{
        // JDBC driver name and database URL
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        static final String DB_URL = "jdbc:mysql://localhost:3306/manohar";

        //  Database credentials
        static final String USER = "root";
        static final String PASS = "password";

        static String names[] = new String[55];

        public static void main(String[] args) throws IOException
        {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);


            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            File f = new File("/Volumes/MANOHAR/Programming/DataManager/out/production/DataManager/Job_Size_Max_(Core_Count).csv");
            Scanner sc = new Scanner(f);

            while(sc.hasNextLine())
            {
                String s = sc.nextLine();
              //sql = "INSERT INTO EmployeeName(name) Values('"+ names[i++] + "');";
                stmt.executeUpdate(sql);
            }

            stmt.close();
            conn.close();
        }catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main

    public static long getNanoDay(int n)
    {
        return ((14984352 + n*864) * (long)Math.pow(10,11));
    }
}//end FirstExample

