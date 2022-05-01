package inf202;

import java.sql.*;

public class Database {

    public void connection_db(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inf202db",
                    "root", "Yazmuh18proje_");
            Statement statement = connection.createStatement();
            System.out.println("SQL connected");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int login_db(String userName, String password) throws SQLException {
        int login = 0;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inf202db",
                "root", "Yazmuh18proje_");
        PreparedStatement loginStatement = connection.prepareStatement("select * from person where userName = ? " +
                "and userPassword = ?");
        loginStatement.setString(1,userName);
        loginStatement.setString(2,password);

        ResultSet rs = loginStatement.executeQuery();
        while (rs.next()) {
            System.out.println("User Informations: ");
            System.out.println("Login Code "+ rs.getInt("login"));
            System.out.println("TC Number: "+ rs.getInt("TcNummer"));
            System.out.println("User Name "+ rs.getInt("userName"));
            login = rs.getInt("login");
        }
        return login;
    }
}
