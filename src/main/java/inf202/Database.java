package inf202;

import java.sql.*;

public class Database {

    public void connection_db(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inf202db",
                    "root", "Yazmuh18proje_");
            Statement statement = connection.createStatement();
            //ResultSet resultSet = statement.executeQuery("select * from person");
            System.out.println("SQL connected");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
