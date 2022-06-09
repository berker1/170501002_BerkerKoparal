package inf202;

public class Person {

    private String vorname;
    private String nachname;
    private int tcNummer;
    private String email;
    private String kontaktNummer;
    private String geschlecht;
    private int alter;

    public Person(String vorname, String nachname, int tcNummer) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.tcNummer = tcNummer;
    }


    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public int getTcNummer() {
        return tcNummer;
    }

    public void setTcNummer(int tcNummer) {
        this.tcNummer = tcNummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKontaktNummer() {
        return kontaktNummer;
    }

    public void setKontaktNummer(String kontaktNummer) {
        this.kontaktNummer = kontaktNummer;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }


    public Person(String vorname, String nachname, int tcNummer,
                  String email, String kontaktNummer, String geschlecht, int alter
                  ) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.tcNummer = tcNummer;
        this.email = email;
        this.kontaktNummer = kontaktNummer;
        this.geschlecht = geschlecht;
        this.alter = alter;
    }
}
