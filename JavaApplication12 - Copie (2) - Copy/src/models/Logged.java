package models;

public final class Logged {
    private AllUsers user;
    private final static Logged Loggedin = new Logged();

    public static Logged get_instance() {
        return Loggedin;
    }

    public void setUser(AllUsers u) {
        this.user = u;
    }

    public AllUsers getUser() {
        return this.user;
    }
}


