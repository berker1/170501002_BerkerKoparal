package inf202_klassen;

import java.util.ArrayList;

public class Kunde extends Person{

    private ArrayList<int> fallIds;

    public Kunde(String vorname, String nachname, String tcNummer, String email, String kontaktNummer, String geschlecht,
                 int alter, String userName, String userPassword, ArrayList<int> fallIds) {
        super(vorname, nachname, tcNummer, email, kontaktNummer, geschlecht, alter, userName, userPassword);
        this.fallIds = fallIds;
    }

    public ArrayList<int> getFallIds() {
        return fallIds;
    }

    public void setFallIds(ArrayList<int> fallIds) {
        this.fallIds = fallIds;
    }
}
