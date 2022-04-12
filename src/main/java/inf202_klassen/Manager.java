package inf202_klassen;

import java.util.ArrayList;

public class Manager extends Person{

    private String branche;
    private String fall;
    private ArrayList<int> fallIds;

    public Manager(String vorname, String nachname, String tcNummer, String email, String kontaktNummer, String geschlecht,
                   int alter, String userName, String userPassword, String branche, String fall, ArrayList<int> fallIds) {
        super(vorname, nachname, tcNummer, email, kontaktNummer, geschlecht, alter, userName, userPassword);
        this.branche = branche;
        this.fall = fall;
        this.fallIds = fallIds;
    }

    public String getBranche() {
        return branche;
    }

    public void setBranche(String branche) {
        this.branche = branche;
    }

    public String getFall() {
        return fall;
    }

    public void setFall(String fall) {
        this.fall = fall;
    }

    public ArrayList<int> getFallIds() {
        return fallIds;
    }

    public void setFallIds(ArrayList<int> fallIds) {
        this.fallIds = fallIds;
    }
}
