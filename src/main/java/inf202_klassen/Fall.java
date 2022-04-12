package inf202_klassen;

public class Fall {

    private int fallId;
    private String fallArt;
    private String fallCode;
    private String fallDescription;
    private int fallState; //on-going, closed

    public Fall(int fallId, String fallArt, String fallCode, String fallDescription, int fallState) {
        this.fallId = fallId;
        this.fallArt = fallArt;
        this.fallCode = fallCode;
        this.fallDescription = fallDescription;
        this.fallState = fallState;
    }

    public int getFallId() {
        return fallId;
    }

    public void setFallId(int fallId) {
        this.fallId = fallId;
    }

    public String getFallArt() {
        return fallArt;
    }

    public void setFallArt(String fallArt) {
        this.fallArt = fallArt;
    }

    public String getFallCode() {
        return fallCode;
    }

    public void setFallCode(String fallCode) {
        this.fallCode = fallCode;
    }

    public String getFallDescription() {
        return fallDescription;
    }

    public void setFallDescription(String fallDescription) {
        this.fallDescription = fallDescription;
    }

    public int getFallState() {
        return fallState;
    }

    public void setFallState(int fallState) {
        this.fallState = fallState;
    }
}
