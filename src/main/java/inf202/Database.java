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
        } // #92C7C7
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

    private static String userInfo;
    private static int userTcInfo;
    private static  int loginID;

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
            System.out.println("name:" + rs.getString("vorname"));
            System.out.println("surname:" + rs.getString("nachname"));
            userInfo = rs.getString("vorname") + " " + rs.getString("nachname");
            loginID = rs.getInt("login");
            System.out.println("username: " + userInfo);
            System.out.println("Login Code " + rs.getInt("login"));
            System.out.println("TC Number: " + rs.getInt("TcNummer"));
            userTcInfo =  rs.getInt("TcNummer");
            System.out.println("User Name " + rs.getString("userName"));
            login = rs.getInt("login");
            //PreparedStatement statement = connection.prepareStatement("update user set userTC = ? where userTC = 333");
            PreparedStatement statement = connection.prepareStatement("update user set userTC = ? where userName = ?");
            statement.setInt(1, rs.getInt("TcNummer"));
            statement.setString(2, "currentUser");
            statement.executeUpdate();
            //getUserTC();
        }
        return login;
    }

    public static String getUserInfo(){
        return userInfo;
    }

    public static int getUserTC(){
        return userTcInfo;
    }

    public static  int getUserLogin(){return loginID; }


    public static void add_case(String caseDate, String caseCode, String caseClass, String caseState, String description) throws SQLException {
        try {
            Connection connection = connectDB();
            PreparedStatement addStatement = connection.prepareStatement("insert into fall (fallArt, fallCode, fallState, caseDate, fallDescription)" +
                    "values(?, ?, ?, ?, ?)");
            addStatement.setString(1, caseClass);
            addStatement.setString(2, caseCode);
            addStatement.setString(3, caseState);
            addStatement.setString(4, caseDate);
            addStatement.setString(5, description);
            addStatement.execute();
            System.out.println("New Case Added");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void edit_case(String caseDate, String caseCode, String caseClass, String caseState, String description,
                                    int caseID) throws SQLException {
        try {
            Connection connection = connectDB();
            String sql = "update fall set fallArt = ? , fallCode = ?, fallState = ?, caseDate = ?, fallDescription = ?" +
                    "where id_fall = ?";
            PreparedStatement addStatement = connection.prepareStatement(sql);
            addStatement.setString(1, caseClass);
            addStatement.setString(2, caseCode);
            addStatement.setString(3, caseState);
            addStatement.setString(4, caseDate);
            addStatement.setString(5, description);
            addStatement.setInt(6,caseID);
            addStatement.executeUpdate();
            System.out.println("Case edited");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editPersonLawyer(Anwalt lawyer) throws SQLException {
        try {
            Connection connection = connectDB();
            String sql = "update person set vorname = ? , nachname = ?, tcNummer = ?, email = ?, kontaktNummer = ?, geschlecht = ?," +
                    "alterAnwalt = ?, userName = ?, userPassword = ?, branche = ?" +
                    "where tcNummer = ?";
            PreparedStatement addStatement = connection.prepareStatement(sql);
            addStatement.setString(1, lawyer.getVorname());
            addStatement.setString(2, lawyer.getNachname());
            addStatement.setInt(3, lawyer.getTcNummer());
            addStatement.setString(4, lawyer.getEmail());
            addStatement.setString(5, lawyer.getKontaktNummer());
            addStatement.setString(6,lawyer.getGeschlecht());
            addStatement.setInt(7,lawyer.getAlter());
            addStatement.setString(8,lawyer.getUserName());
            addStatement.setString(9,lawyer.getUserPassword());
            addStatement.setInt(10, lawyer.getTcNummer());
            addStatement.executeUpdate();
            System.out.println("Lawyer edited");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editPersonManager(Manager manager) throws SQLException {
        try {
            Connection connection = connectDB();
            String sql = "update person set vorname = ? , nachname = ?, tcNummer = ?, email = ?, kontaktNummer = ?, geschlecht = ?," +
                    "alterAnwalt = ?, userName = ?, userPassword = ?, branche = ?" +
                    "where tcNummer = ?";
            PreparedStatement addStatement = connection.prepareStatement(sql);
            addStatement.setString(1, manager.getVorname());
            addStatement.setString(2, manager.getNachname());
            addStatement.setInt(3, manager.getTcNummer());
            addStatement.setString(4, manager.getEmail());
            addStatement.setString(5, manager.getKontaktNummer());
            addStatement.setString(6,manager.getGeschlecht());
            addStatement.setInt(7,manager.getAlter());
            addStatement.setString(8,manager.getUserName());
            addStatement.setString(9,manager.getUserPassword());
            addStatement.setInt(10, manager.getTcNummer());
            addStatement.executeUpdate();
            System.out.println("Manager edited");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deletePerson(int tc) throws SQLException {
        Connection connection = connectDB();
        PreparedStatement statement = connection.prepareStatement("delete from person where tcNummer = ?");
        statement.setInt(1, tc);
        statement.executeUpdate();
    }

    public static void deleteCase(int caseID) throws SQLException {
        Connection connection = connectDB();
        PreparedStatement statement = connection.prepareStatement("delete from fall where id_fall = ?");
        statement.setInt(1, caseID);
        statement.executeUpdate();
    }

    public static Fall show_case_details(int value) throws SQLException {
        Fall selectedFall = null;
        try {
            Connection connection = connectDB();
            PreparedStatement statement = connection.prepareStatement("select * from fall where id_fall = ?");
            statement.setInt(1, value);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                selectedFall = new Fall(Integer.parseInt(rs.getString("id_fall")), rs.getString("fallArt"),
                        rs.getString("fallCode"), rs.getString("fallDescription"),
                        rs.getString("fallState"), rs.getString("caseDate"));
                System.out.println(rs.getString("id_fall"));
                System.out.println(rs.getString("fallArt"));
                System.out.println(rs.getString("fallState"));
                System.out.println(rs.getString("caseDate"));
                System.out.println(rs.getString("fallDescription"));
                System.out.println(rs.getString("fallCode"));
            }
            return selectedFall;
        } catch (Exception e) {
            e.printStackTrace();
            return selectedFall;
        }
    }

    public static ObservableList<Fall> getDataFall(int tc, int index) {
        Connection connection = connectDB();
        ObservableList<Fall> list = FXCollections.observableArrayList();
        String sql;
        try {
            if (index == 3) {
                sql = "select * from fall where caseForLawyer = ?";
            } else {
                sql = "select * from fall where caseForManager = ?";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, tc);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Fall(Integer.parseInt(rs.getString("id_fall")), rs.getString("fallArt"),
                        rs.getString("fallCode"), rs.getString("fallDescription"),
                        rs.getString("fallState"), rs.getString("caseDate")));
            }
        } catch (Exception e) {

        }

        return list;
    }

    public static ObservableList<Fall> getAssignableCasesInManager(int tc) {
        Connection connection = connectDB();
        ObservableList<Fall> list = FXCollections.observableArrayList();
        String sql = "select * from fall where caseForLawyer = ? and caseForManager = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, -1);
            ps.setInt(2, tc);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Fall(Integer.parseInt(rs.getString("id_fall")), rs.getString("fallArt"),
                        rs.getString("fallCode"), rs.getString("fallDescription"),
                        rs.getString("fallState"), rs.getString("caseDate")));
            }
        } catch (Exception e) {

        }

        return list;
    }


    public static ObservableList<Anwalt> getLawyersForManager(int manager_tc) {
        //public static ObservableList<Anwalt> getLawyersForManager(){
        Connection connection = connectDB();
        ObservableList<Anwalt> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("select * from person where managersTC = ?");
            //PreparedStatement ps = connection.prepareStatement("select * from person");
            ps.setInt(1, manager_tc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Anwalt(rs.getString("vorname"), rs.getString("nachname"),
                        rs.getString("branche"), rs.getInt("tcNummer")));
                System.out.println("lawyer in manager group: " + rs.getString("branche"));
                System.out.println("lawyer in manager group: " + rs.getString("vorname"));
                System.out.println("lawyer in manager group: " + rs.getString("nachname"));
                System.out.println("lawyer in manager group: " + rs.getString("tcNummer"));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public static ObservableList<Manager> getManagers() {
        Connection connection = connectDB();
        ObservableList<Manager> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from person where login = 2");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Manager(rs.getString("vorname"), rs.getString("nachname"),
                        rs.getString("branche"), rs.getInt("tcNummer")));
                System.out.println("manager: " + rs.getString("branche"));
                System.out.println("manager: " + rs.getString("vorname"));
                System.out.println("manager: " + rs.getString("nachname"));
                System.out.println("manager: " + rs.getInt("tcNummer"));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public static ObservableList<Anwalt> getLawyers() {
        Connection connection = connectDB();
        ObservableList<Anwalt> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("select vorname, nachname, tcNummer, branche " +
                    "from person where login = 3");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Anwalt(rs.getString("vorname"), rs.getString("nachname"),
                        rs.getString("branche"), rs.getInt("tcNummer")));
                System.out.println("lawyer: " + rs.getString("branche"));
                System.out.println("lawyer: " + rs.getString("vorname"));
                System.out.println("lawyer: " + rs.getString("nachname"));
                System.out.println("lawyer: " + rs.getInt("tcNummer"));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public static ObservableList<Fall> getCases() {
        Connection connection = connectDB();
        ObservableList<Fall> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from fall");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Fall(Integer.parseInt(rs.getString("id_fall")), rs.getString("fallArt"),
                        rs.getString("fallCode"), rs.getString("fallDescription"),
                        rs.getString("fallState"), rs.getString("caseDate")));
            }
        } catch (Exception e) {

        }
        return list;
    }


    public static void assignCase(int tc, int caseID, int index) throws SQLException {
        Connection connection = connectDB();
        String sql;
        if (index == 3) {
            sql = "update fall set caseForLawyer = ? where id_fall = ?";
        } else {
            sql = "update fall set caseForManager = ? where id_fall = ?";
        }
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, tc);
        statement.setInt(2, caseID);
        statement.executeUpdate();
    }

    public static void assignLawyer(int tcLawyer, int tcManager) throws SQLException {
        Connection connection = connectDB();
        String sql;
        sql = "update person set managersTC = ? where tcNummer = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, tcManager);
        statement.setInt(2, tcLawyer);
        statement.executeUpdate();
    }

    public static ObservableList<Fall> getDataAssignedCases(int tc, int index) { // on click
        Connection connection = connectDB();
        ObservableList<Fall> list = FXCollections.observableArrayList();
        String sql;
        if (index == 3) {
            sql = "select * from fall where caseForLawyer = ?";
        } else {
            sql = "select * from fall where caseForManager = ?";
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, tc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Fall(Integer.parseInt(rs.getString("id_fall")), rs.getString("fallArt"),
                        rs.getString("fallCode"), rs.getString("fallDescription"),
                        rs.getString("fallState"), rs.getString("caseDate")));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public static void retainCase(int tc, int caseID, int index) throws SQLException {
        Connection connection = connectDB();
        String sql;
        if (index == 3) {
            sql = "update fall set caseForLawyer = -1 where caseForLawyer = ? and id_fall = ?";
        } else {
            sql = "update fall set caseForManager = ? where caseForManager = ? and id_fall = ?";
        }
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, tc);
        statement.setInt(2, caseID);
        statement.executeUpdate();
    }

    public static ObservableList<Anwalt> getAssignableLawyers() {
        Connection connection = connectDB();
        ObservableList<Anwalt> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("select vorname, nachname, tcNummer, branche " +
                    "from person where managersTC = -1 and login = 3");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Anwalt(rs.getString("vorname"), rs.getString("nachname"),
                        rs.getString("branche"), rs.getInt("tcNummer")));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public static ObservableList<Fall> getAssignableCasesInAdmin(int index) {
        Connection connection = connectDB();
        ObservableList<Fall> list = FXCollections.observableArrayList();
        String sql;
        if (index == 3) {
            sql = "select * from fall where caseForLawyer = ?";
        } else {
            sql = "select * from fall where caseForManager = ?";
        }

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, -1);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Fall(Integer.parseInt(rs.getString("id_fall")), rs.getString("fallArt"),
                        rs.getString("fallCode"), rs.getString("fallDescription"),
                        rs.getString("fallState"), rs.getString("caseDate")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static Anwalt getLawyerDetails(int tc) {
        Connection connection = connectDB();
        Anwalt lawyer = null;
        try {
            PreparedStatement statement = connection.prepareStatement("select * from person where tcNummer = ?");
            statement.setInt(1,tc);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                lawyer =new Anwalt(rs.getString("vorname"), rs.getString("nachname"),
                         rs.getInt("tcNummer"), rs.getString("email"), rs.getString("kontaktnummer"),
                        rs.getString("geschlecht"),rs.getInt("alterAnwalt"), rs.getString("userName"),
                        rs.getString("userPassword"), rs.getString("branche"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lawyer;
    }
}

