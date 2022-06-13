package inf202;

import java.util.ArrayList;

public class Manager extends Person{

    private String branche;
    private String userName;
    private String userPassword;
    private ArrayList<Integer> fallIds;
    private int loginManager = 2;

    public Manager(String vorname, String nachname, int tcNummer, String email, String kontaktNummer,
                   String geschlecht, int alter,String branche, String userName, String userPassword,
                   ArrayList<Integer> fallIds ) {
        super(vorname, nachname, tcNummer, email, kontaktNummer, geschlecht, alter);
        this.branche = branche;
        this.userName = userName;
        this.userPassword = userPassword;
        this.fallIds = fallIds;
    }

    public Manager(String vorname, String nachname, String branche, int tcNummer) {
        super(vorname, nachname, tcNummer);
        this.branche = branche;
    }


    public String getBranche() {
        return branche;
    }

    public void setBranche(String branche) {
        this.branche = branche;
    }


    public ArrayList<Integer> getFallIds() {
        return fallIds;
    }

    public void setFallIds(ArrayList<Integer> fallIds) {
        this.fallIds = fallIds;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getLoginManager() {
        return loginManager;
    }

    public void setLoginManager(int loginManager) {
        this.loginManager = loginManager;
    }
}
