public class pret {
    private String pretdate;
    private String idadherant;
    private int idoeuvre;

    public pret() {}

    public pret(String pretdate, String idadherant, int idoeuvre) {
        this.pretdate = pretdate;
        this.idadherant = idadherant;
        this.idoeuvre = idoeuvre;
    }
    
    public String getPretdate() {
        return pretdate;
    }
    
    public void setPretdate(String pretdate) {
        this.pretdate = pretdate;
    }
    
    public String getIdadherant() {
        return idadherant;
    }
    
    public void setIdadherant(String idadherant) {
        this.idadherant = idadherant;
    }
    
    public int getIdoeuvre() {
        return idoeuvre;
    }
    
    public void setIdoeuvre(int idoeuvre) {
        this.idoeuvre = idoeuvre;
    }

    public String toString() {
        return "Lending [LendingDate=" + pretdate + ", MemberID=" + idadherant + ", BookID=" + idoeuvre + "]";
    }
}
