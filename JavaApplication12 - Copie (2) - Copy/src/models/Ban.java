package models;

import java.time.LocalDate;

public class Ban {
    private int ID_Ban,ID_User;
    private LocalDate Date;
    private String Reason;
    private String Nickname;

    public Ban(int ID_Ban, int ID_User, LocalDate date, String reason, String nickname) {
        this.ID_Ban = ID_Ban;
        this.ID_User = ID_User;
        Date = date;
        Reason = reason;
        Nickname = nickname;
    }

    public Ban(int ID_User, LocalDate date, String reason, String nickname) {
        this.ID_User = ID_User;
        Date = date;
        Reason = reason;
        Nickname = nickname;
    }

    public Ban(int ID_User, String reason) {
        this.ID_User = ID_User;
        Reason = reason;
    }

    public Ban() {

    }

    public int getID_Ban() {
        return ID_Ban;
    }

    public void setID_Ban(int ID_Ban) {
        this.ID_Ban = ID_Ban;
    }

    public int getID_User() {
        return ID_User;
    }

    public void setID_User(int ID_User) {
        this.ID_User = ID_User;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    @Override
    public String toString() {
        return "Ban{" +
                "ID_Ban=" + ID_Ban + System.lineSeparator()+
                ", ID_User=" + ID_User + System.lineSeparator()+
                ", Date='" + Date + '\'' + System.lineSeparator()+
                ", Reason='" + Reason + '\'' + System.lineSeparator()+
                ", Nickname='" + Nickname + '\'' + System.lineSeparator()+
                '}';
    }
}
