package inf202;

public class Fall {

    private int fallId;
    private String fallArt;
    private String fallCode;
    private String fallDescription;
    private String fallState; //on-going, closed

    public Fall(int fallId, String fallArt, String fallCode, String fallDescription, String fallState) {
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

    public String getFallState() {
        return fallState;
    }

    public void setFallState(String fallState) {
        this.fallState = fallState;
    }
}
