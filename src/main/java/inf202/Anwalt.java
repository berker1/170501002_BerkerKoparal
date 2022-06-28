package inf202;

import java.util.ArrayList;

public class Anwalt extends Person{

    private String branche;
    private String userName;
    private String userPassword;
    private ArrayList<Integer> fallIds;
    private int loginAnwalt = 1;

    public Anwalt(String vorname, String nachname, String tcNummer, String email, String kontaktNummer,
                  String geschlecht, int alter, String userName, String userPassword,String branche) {
        super(vorname, nachname, tcNummer, email, kontaktNummer, geschlecht, alter);
        this.userName = userName;
        this.userPassword = userPassword;
        this.branche = branche;
    }

    public Anwalt(String vorname, String nachname, String branche, String tcNummer) {
        super(vorname, nachname, tcNummer );
        this.branche = branche;
    }


    public String getBranche() {
        return branche;
    }

    public void setBranche(String branche) {
        this.branche = branche;
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

    public ArrayList<Integer> getFallIds() {
        return fallIds;
    }

    public void setFallIds(ArrayList<Integer> fallIds) {
        this.fallIds = fallIds;
    }

    public int getLoginAnwalt() {
        return loginAnwalt;
    }

    public void setLoginAnwalt(int loginAnwalt) {
        this.loginAnwalt = loginAnwalt;
    }
}

//davacı ekle
//foreign key
