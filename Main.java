
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.io.*;
import java.util.*;
public class Main
{
        // JDBC driver name and database URL
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        static final String DB_URL = "jdbc:mysql://hostname:3306/database_name";

        //  Database credentials
        static final String USER = "<username>";
        static final String PASS = "<password>";

        static String names[] = new String[55];

        public static void main(String[] args) throws IOException, SQLException,ClassNotFoundException
        {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            /*String array[] ={"Job_Size_Max_Core_Count", "Job_Size_Min_Core_Count",
                            "Job_Size_Per_Job_Core_Count","Number_of_Jobs_Running",
                            "Number_of_Jobs_Started","Number_of_Jobs_Submitted",
                            "Wait_Hours_Per_Job","Wall_Hours_Per_Job",
                            "Wall_Hours_Total"};*/
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            String fileName = "Wall_Hours_Total.csv";
            String names[] = new String[1000];
            Scanner read = new Scanner(new FileReader(fileName));
            names = getNames(read);

            String data[][] = new String[90][57];
            data = getData(read);
            for (int row = 0; row < 90; row++) {
                for (int col = 0; col < 55; col++) {
                    sql = "UPDATE OtherInformation SET Wall_Hours_Total = "
                            + data[row][col + 2] + " WHERE day = " + (row+1) + " AND name = '" + names[col] + "';";
                    stmt.executeUpdate(sql);
                }
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

    public static String[] getNames(Scanner read) throws IOException
    {
        int i = 0;
        String str[] = new String [1000];
        String name = "";
        String line = read.nextLine();
        char c = line.charAt(0);
        int loc = 0;
        boolean isBraket = false;
        while(loc < line.length())
        {
            if(c == '[')
            {
                isBraket = true;
                c = line.charAt(loc++);
            }
            else if (c == ']')
            {
                isBraket = false;
                str[i++] = name;
                name = "";
            }
            if(isBraket)
            {
                name = name + c;
            }
            c = line.charAt(loc++);
        }

        return str;
    }

    public static String[][] getData(Scanner read)
    {
        int loc = 0; char c = ' ';
        String data[][] = new String[90][57];
        int row = 0, col = 0; String field = "";

        while(read.hasNextLine())
        {
            loc = 0; col = 0;
            String line = read.nextLine();

            while(loc < line.length())
            {
                c = line.charAt(loc++);
                if(c != ',' && loc != line.length())
                {
                    field = field + c;
                }
                else
                {
                    data[row][col++] = field;
                    field = "";
                }
            }
            row++;
        }
        return data;
    }
}//end FirstExample

