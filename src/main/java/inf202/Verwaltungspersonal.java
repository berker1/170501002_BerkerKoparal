package inf202;

public class Verwaltungspersonal extends Person{

    private String userName;
    private String userPassword;
    private int loginVerwaltung = 3;

    public Verwaltungspersonal(String vorname, String nachname, int tcNummer, String email, String kontaktNummer,
                               String geschlecht, int alter, String userName, String userPassword) {
        super(vorname, nachname, tcNummer, email, kontaktNummer, geschlecht, alter);
        this.userName = userName;
        this.userPassword = userPassword;
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

    public int getLoginVerwaltung() {
        return loginVerwaltung;
    }

    public void setLoginVerwaltung(int loginVerwaltung) {
        this.loginVerwaltung = loginVerwaltung;
    }
}
