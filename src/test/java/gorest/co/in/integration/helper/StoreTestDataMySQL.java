package gorest.co.in.integration.helper;

import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StoreTestDataMySQL {

    private static int elemCount;
    private static Connection ConnectToDB() throws Exception {
        //Registering the Driver
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        //Getting the connection
        String mysqlUrl = "jdbc:mysql://localhost:3306/usersdb";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "password");
        System.out.println("Connection established......");
        return con;
    }

    public static void storeUserDateInDatabase(Response response) {
        Connection connection = null;
        PreparedStatement prepared_statement = null;

        try {
            //Parsing the contents of the JSON file
            String jsonLine = response.getBody().asString();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonLine);

            //Retrieving the array
            JSONArray jsonArray = (JSONArray) json.get("data");
            connection = ConnectToDB();

            elemCount = jsonArray.size();
            int table_id = 1;
            //Insert a row into the MyPlayers table
            prepared_statement = connection.prepareStatement("INSERT INTO USERS values (?, ?, ?, ?, ?, ?, ?, ? )");
            for (Object object : jsonArray) {
                JSONObject record = (JSONObject) object;
                int user_id = Integer.parseInt(String.valueOf(record.get("id")));
                String name = (String) record.get("name");
                String email = (String) record.get("email");
                String gender = (String) record.get("gender");
                String status = (String) record.get("status");
                String created_at = (String) record.get("created_at");
                String updated_at = (String) record.get("updated_at");
                prepared_statement.setInt(1, table_id);
                prepared_statement.setInt(2, user_id);
                prepared_statement.setString(3, name);
                prepared_statement.setString(4, email);
                prepared_statement.setString(5, gender);
                prepared_statement.setString(6, status);
                prepared_statement.setString(7, created_at);
                prepared_statement.setString(8, updated_at);
                prepared_statement.executeUpdate();
                table_id++;
            }
            System.out.println("Records inserted.....");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (prepared_statement != null) {
                try {
                    prepared_statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }}
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void removeStoredDataFromDatabase() {
        Connection connection = null;
        PreparedStatement prepared_statement = null;
        int count = 1;
        try
        {
            connection = ConnectToDB();
            while(count <= elemCount) {
                prepared_statement = connection.prepareStatement("DELETE FROM USERS WHERE table_id= ?");

                prepared_statement.setString(1, String.valueOf(count));
                int result_set = prepared_statement.executeUpdate();

                if (result_set > 0) {
                     System.out.println("Successfully deleted row " + count);
                } else {
                     System.out.println("Couldn't delete row " + count);
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (prepared_statement != null) {
                try {
                    prepared_statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }}
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
