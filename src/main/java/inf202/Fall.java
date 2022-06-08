package inf202;

public class Fall {

    private int fallId;
    private String fallArt;
    private String fallCode;
    private String fallDescription;
    private String caseDate;
    private String fallState; //on-going, closed
    private String caseForLawyer;
    private String caseForManager;

    public Fall(int fallId, String fallArt, String fallCode, String fallDescription, String caseDate, String fallState) {
        this.fallId = fallId;
        this.fallArt = fallArt;
        this.fallCode = fallCode;
        this.fallDescription = fallDescription;
        this.caseDate = caseDate;
        this.fallState = fallState;
    }

    public Fall(int fallId, String fallArt, String fallCode, String fallDescription, String caseDate, String fallState,
                String caseForManager, String caseForLawyer) {
        this.fallId = fallId;
        this.fallArt = fallArt;
        this.fallCode = fallCode;
        this.fallDescription = fallDescription;
        this.caseDate = caseDate;
        this.fallState = fallState;
        this.caseForManager = caseForManager;
        this.caseForLawyer = caseForLawyer;
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

    public String getCaseDate() {
        return caseDate;
    }

    public void setCaseDate(String caseDate) {
        this.caseDate = caseDate;
    }

    public String getCaseForLawyer() {
        return caseForLawyer;
    }

    public void setCaseForLawyer(String caseForLawyer) {
        this.caseForLawyer = caseForLawyer;
    }

    public String getCaseForManager() {
        return caseForManager;
    }

    public void setCaseForManager(String caseForManager) {
        this.caseForManager = caseForManager;
    }
}
