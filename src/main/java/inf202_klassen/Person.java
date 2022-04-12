package inf202_klassen;

public class Person {

    private String vorname;
    private String nachname;
    private String tcNummer;
    private String email;
    private String kontaktNummer;
    private String geschlecht;
    private int alter;
    private String userName;
    private String userPassword;

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

    public String getTcNummer() {
        return tcNummer;
    }

    public void setTcNummer(String tcNummer) {
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

    public Person(String vorname, String nachname, String tcNummer,
                  String email, String kontaktNummer, String geschlecht, int alter,
                  String userName, String userPassword) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.tcNummer = tcNummer;
        this.email = email;
        this.kontaktNummer = kontaktNummer;
        this.geschlecht = geschlecht;
        this.alter = alter;
        this.userName = userName;
        this.userPassword = userPassword;
    }
}
