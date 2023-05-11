package models;

import java.time.LocalDate;

public class AllUsers {
    private int ID_User;
    private String Name;
    private String Last_Name;

    private String Nickname;
    private String Email;
    private LocalDate Birthday;
    private String Password;
    private String Nationality;
    private String type;
    private String Salt;

    private String Avatar;
    private String Background;
    private String Description;
    private String Bio;

    public AllUsers() {
    }

    public AllUsers(int ID_User, String name, String last_Name, String nickname, String email, LocalDate birthday, String password, String nationality, String type, String salt, String avatar, String background, String description, String bio) {
        this.ID_User = ID_User;
        Name = name;
        Last_Name = last_Name;
        Nickname = nickname;
        Email = email;
        Birthday = birthday;
        Password = password;
        Nationality = nationality;
        this.type = type;
        Salt = salt;
        Avatar = avatar;
        Background = background;
        Description = description;
        Bio = bio;
    }

    public AllUsers(String name, String last_Name, String nickname, String email, LocalDate birthday, String password, String nationality, String type, String salt, String avatar, String background, String description, String bio) {
        Name = name;
        Last_Name = last_Name;
        Nickname = nickname;
        Email = email;
        Birthday = birthday;
        Password = password;
        Nationality = nationality;
        this.type = type;
        Salt = salt;
        Avatar = avatar;
        Background = background;
        Description = description;
        Bio = bio;
    }

    public int getID_User() {
        return ID_User;
    }

    public void setID_User(int ID_User) {
        this.ID_User = ID_User;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public LocalDate getBirthday() {
        return Birthday;
    }

    public void setBirthday(LocalDate birthday) {
        Birthday = birthday;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSalt() {
        return Salt;
    }

    public void setSalt(String salt) {
        Salt = salt;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getBackground() {
        return Background;
    }

    public void setBackground(String background) {
        Background = background;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    @Override
    public String toString() {
        return "AllUsers{" +
                "ID_User=" + ID_User +
                ", Name='" + Name + '\'' +
                ", Last_Name='" + Last_Name + '\'' +
                ", Nickname='" + Nickname + '\'' +
                ", Email='" + Email + '\'' +
                ", Birthday=" + Birthday +
                ", Password='" + Password + '\'' +
                ", Nationality='" + Nationality + '\'' +
                ", type='" + type + '\'' +
                ", Salt='" + Salt + '\'' +
                ", Avatar='" + Avatar + '\'' +
                ", Background='" + Background + '\'' +
                ", Description='" + Description + '\'' +
                ", Bio='" + Bio + '\'' +
                '}';
    }
}

