package inf202;

import java.util.ArrayList;

public class Kunde extends Person{

    private ArrayList<Integer> fallIds;

    public Kunde(String vorname, String nachname, int tcNummer, String email, String kontaktNummer, String geschlecht,
                 int alter, ArrayList<Integer> fallIds) {
        super(vorname, nachname, tcNummer, email, kontaktNummer, geschlecht, alter);
        this.fallIds = fallIds;
    }

    public ArrayList<Integer> getFallIds() {
        return fallIds;
    }

    public void setFallIds(ArrayList<Integer> fallIds) {
        this.fallIds = fallIds;
    }
}
