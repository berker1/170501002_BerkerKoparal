package inf202;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import java.sql.*;

public class Database {

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

    public void connection_db() {
        try {
            Connection connection = connectDB();
            Statement statement = connection.createStatement();
            System.out.println("SQL connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int login_db(String userName, String password) throws SQLException {
        int login = 0;
        Connection connection = connectDB();
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
            //PreparedStatement statement = connection.prepareStatement("update user set userTC = ? where userTC = 333");
            PreparedStatement statement = connection.prepareStatement("update user set userTC = ? where userName = ?");
            statement.setInt(1,rs.getInt("TcNummer"));
            statement.setString(2,"currentUser");
            statement.executeUpdate();
            getUserTC();
        }
        return login;
    }


    public void add_case(String caseDate, String caseCode, String caseClass, String caseState, String description) throws SQLException {
        try {
            Connection connection = connectDB();
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

    public static Fall show_case_details(int value) throws SQLException {
        Fall selectedFall = null;
        try {
            Connection connection = connectDB();
            PreparedStatement statement = connection.prepareStatement("select * from fall where id_fall = ?");
            statement.setInt(1, value);

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

    public static ObservableList<Fall> getDataFall(int tc, int index){
        Connection connection = connectDB();
        ObservableList<Fall> list = FXCollections.observableArrayList();
        String sql;
        try {
            if(index == 3){
                sql = "select * from fall where caseForLawyer = ?";
            }else {
                sql = "select * from fall where caseForManager = ?";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,tc);
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

    public static ObservableList<Fall> getAssignableCases(int tc){
        Connection connection = connectDB();
        ObservableList<Fall> list = FXCollections.observableArrayList();
        String sql = "select * from fall where caseForLawyer = ? and caseForManager = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,-1);
            ps.setInt(2,tc);
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
                        rs.getString("branche"), rs.getInt("tcNummer")));
                System.out.println("lawyer in manager group: " + rs.getString("branche"));
                System.out.println("lawyer in manager group: " + rs.getString("vorname"));
                System.out.println("lawyer in manager group: " + rs.getString("nachname"));
                System.out.println("lawyer in manager group: " + rs.getString("tcNummer"));
            }
        }catch (Exception e){

        }
        return list;
    }

    public static void assignCase(int tc, int caseID, int index) throws SQLException {
        Connection connection = connectDB();
        String sql;
        if(index == 3){
            sql = "update fall set caseForLawyer = ? where id_fall = ?";
        }else{
            sql = "update fall set caseForManager = ? where id_fall = ?";
        }
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,tc);
        statement.setInt(2,caseID);
        statement.executeUpdate();
    }

    public static ObservableList<Fall> getDataCaseAssignedForLawyer(int tc, int index){
        Connection connection = connectDB();
        ObservableList<Fall> list = FXCollections.observableArrayList();
        String sql;
        if(index == 3){
            sql = "select * from fall where caseForLawyer = ?";
        }else{
            sql = "update fall set caseForManager = ?";
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, tc);
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

    public static void retainCase(int tc, int caseID, int index) throws SQLException {
        Connection connection = connectDB();
        String sql;
        if(index == 3){
            sql = "update fall set caseForLawyer = -1 where caseForLawyer = ? and id_fall = ?";
        }else{
            sql = "update fall set caseForManager = ? where caseForManager = ? and id_fall = ?";
        }
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,tc);
        statement.setInt(2,caseID);
        statement.executeUpdate();
    }
}

