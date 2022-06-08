package inf202;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import java.sql.*;

public class Database {

    public void connection_db() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inf202db",
                    "root", "Yazmuh18proje_");
            Statement statement = connection.createStatement();
            System.out.println("SQL connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int login_db(String userName, String password) throws SQLException {
        int login = 0;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inf202db",
                "root", "Yazmuh18proje_");
        PreparedStatement loginStatement = connection.prepareStatement("select * from person where userName = ? " +
                "and userPassword = ?");
        loginStatement.setString(1, userName);
        loginStatement.setString(2, password);

        ResultSet rs = loginStatement.executeQuery();
        while (rs.next()) {
            System.out.println("User Informations: ");
            System.out.println("Login Code " + rs.getInt("login"));
            System.out.println("TC Number: " + rs.getInt("TcNummer"));
            System.out.println("User Name " + rs.getString("userName"));
            login = rs.getInt("login");
            PreparedStatement statement = connection.prepareStatement("update user set userTC = ? where userTC = 333");
            statement.setInt(1,rs.getInt("TcNummer"));
            statement.executeUpdate();
            getUserTC();
        }
        return login;
    }


    public void add_case(String caseDate, String caseCode, String caseClass, String caseState, String description) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inf202db",
                    "root", "Yazmuh18proje_");
            PreparedStatement addStatement = connection.prepareStatement("insert into fall (fallArt, fallCode, fallState, caseDate, fallDescription)" +
                    "values(?, ?, ?, ?, ?)");
            addStatement.setString(1,caseClass);
            addStatement.setString(2,caseCode);
            addStatement.setString(3,caseState);
            addStatement.setString(4,caseDate);
            addStatement.setString(5,description);
            addStatement.execute();
            System.out.println("New Case Added");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getUserTC() throws SQLException {
        int userTC = 0;
        Connection connection = connectDB();
        PreparedStatement statement = connection.prepareStatement("select * from user");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            System.out.println("get TC: " + rs.getInt("userTC"));
            userTC = rs.getInt("userTC");
        }
        return userTC;
    }

    public static Connection connectDB() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inf202db",
                    "root", "Yazmuh18proje_");
            Statement statement = connection.createStatement();
            System.out.println("SQL connected");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Fall show_case_details(String value) throws SQLException {
        Fall selectedFall = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inf202db",
                    "root", "Yazmuh18proje_");
            PreparedStatement statement = connection.prepareStatement("select * from fall where fallCode = ?");
            statement.setString(1, value);

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                selectedFall = new Fall(Integer.parseInt(rs.getString("id_fall")),rs.getString("fallArt"),
                        rs.getString("fallCode"), rs.getString("fallDescription"),
                        rs.getString("fallState"), rs.getString("caseDate"));
            }
            return selectedFall;
        } catch (Exception e) {
            e.printStackTrace();
            return selectedFall;
        }
    }

    public static ObservableList<Fall> getDataFall(){
        Connection connection = connectDB();
        ObservableList<Fall> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("select * from fall");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new Fall(Integer.parseInt(rs.getString("id_fall")),rs.getString("fallArt"),
                        rs.getString("fallCode"), rs.getString("fallDescription"),
                        rs.getString("fallState"), rs.getString("caseDate")));
            }
        }catch (Exception e){

        }

        return list;
    }

    public static ObservableList<Fall> getDataFallForLawyer(String lawyer_tc){
        Connection connection = connectDB();
        ObservableList<Fall> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("select * from fall where caseForLawyer = ?");
            ps.setString(1, lawyer_tc);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new Fall(Integer.parseInt(rs.getString("id_fall")),rs.getString("fallArt"),
                        rs.getString("fallCode"), rs.getString("fallDescription"),
                        rs.getString("fallState"), rs.getString("caseDate")));
            }
        }catch (Exception e){

        }

        return list;
    }

    public static ObservableList<Fall> getDataFallForManager(String manager_tc){
        Connection connection = connectDB();
        ObservableList<Fall> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("select * from fall where caseForManager = ?");
            ps.setString(1, manager_tc);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new Fall(Integer.parseInt(rs.getString("id_fall")),rs.getString("fallArt"),
                        rs.getString("fallCode"), rs.getString("fallDescription"),
                        rs.getString("fallState"), rs.getString("caseDate")));
            }
        }catch (Exception e){

        }

        return list;
    }

    public static ObservableList<Anwalt> getLawyersForManager(int manager_tc){
    //public static ObservableList<Anwalt> getLawyersForManager(){
        Connection connection = connectDB();
        ObservableList<Anwalt> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("select * from person where managersTC = ?");
            //PreparedStatement ps = connection.prepareStatement("select * from person");
            ps.setInt(1, manager_tc);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Anwalt(rs.getString("vorname"),rs.getString("nachname"),
                        rs.getString("branche")));
                System.out.println("lawyer in manager group: " + rs.getString("branche"));
                System.out.println("lawyer in manager group: " + rs.getString("vorname"));
                System.out.println("lawyer in manager group: " + rs.getString("nachname"));
            }
        }catch (Exception e){

        }
        return list;
    }

}
