package Hive;

import java.sql.*;
import java.util.ArrayList;

public class ConfigDB {
    private String url = "jdbc:mysql://localhost:3306/";
    private String user = DBCredentials.user;
    private String pass = DBCredentials.pass;
    public ConfigDB(){
        try{
            Connection con = DriverManager.getConnection(url, user, pass);
            String query = "SHOW DATABASES;";
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(query);
            ArrayList<String> list = new ArrayList<>();
            while(res.next()){
                list.add(res.getString(1));
            }
            if(!list.contains("hive")){
                query = "CREATE DATABASE hive;";
                stmt.executeUpdate(query);

                query = "USE hive;";
                stmt.executeUpdate(query);

                query = "CREATE TABLE books (BookID int primary key, Name varchar(20), Author varchar(20), Quantity int);";
                stmt.executeUpdate(query);

                query = "CREATE TABLE issue (Reader_ID varchar(10), Reader_Name varchar(20), Book_Name varchar(20), DOI date, DOS date, status varchar(10));";
                stmt.executeUpdate(query);

                query = "CREATE TABLE login (Username varchar(255), Password varchar(255));";
                stmt.executeUpdate(query);
            }
            else{
                query = "USE hive;";
                stmt.executeUpdate(query);

                query = "SHOW TABLES;";
                res =stmt.executeQuery(query);
                list.clear();
                while(res.next()){
                    list.add(res.getString(1));
                }
                if(!list.contains("books")){
                    query = "CREATE TABLE books (BookID int primary key, Name varchar(20), Author varchar(20), Quantity int);";
                    stmt.executeUpdate(query);
                }
                if(!list.contains("issue")){
                    query = "CREATE TABLE issue (Reader_ID varchar(10), Reader_Name varchar(20), Book_Name varchar(20), DOI date, DOS date, status varchar(10));";
                    stmt.executeUpdate(query);
                }
                if(!list.contains("login")){
                    query = "CREATE TABLE login (Username varchar(255), Password varchar(255));";
                    stmt.executeUpdate(query);
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
