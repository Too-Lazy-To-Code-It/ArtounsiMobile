package service;

import interfaces.BanInterface;
import models.Ban;
import util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BanService implements BanInterface {
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void AddBan(Ban B) throws SQLException {
        String nickname = "";
        try {
            String req = "SELECT COUNT(*) FROM allusers WHERE ID_User =" + B.getID_User();
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (!rs.next()) {
                System.out.println("Error: user does not exist.");
                return;
            }
            req = "INSERT INTO Ban(`ID_User`, `Reason`, `DateB`) VALUES (?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, B.getID_User());
            ps.setString(2, B.getReason());
            Timestamp expirationDate = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(3, expirationDate);
            ps.executeUpdate();
            System.out.println("Ban Added Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void DeleteBan(int ID) throws SQLException {
        try {
            String req = "DELETE FROM Ban WHERE ID_Ban=" + ID;
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.executeUpdate();
            System.out.println("Ban Removed Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void ModifyBan(Ban B, int ID) throws SQLException {
        try {
            String req = "UPDATE `Ban` SET `Reason`=?,`DateB`=? WHERE ID_Ban=" + ID;
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, B.getReason());
            Timestamp expirationDate = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(2, expirationDate);
            ps.executeUpdate();
            System.out.println("Ban Modified Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void ModifyBanByUser(Ban B, int ID) throws SQLException {
        try {
            String req = "UPDATE `Ban` SET `Reason`=?,`DateB`=? WHERE ID_User=" + ID;
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, B.getReason());
            ps.setDate(2, Date.valueOf(B.getDate()));
            ps.executeUpdate();
            System.out.println("Ban Modified Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public List<Ban> fetchBan() {
        List<Ban> Ban = new ArrayList<>();
        try {

            String req = "SELECT * FROM ban";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Ban B = new Ban();
                B.setID_Ban(rs.getInt(1));
                B.setID_User(rs.getInt(2));
                B.setReason(rs.getString(3));
                B.setDate(rs.getDate(4).toLocalDate());


                Ban.add(B);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Ban;

    }

    @Override
    public Ban fetchBanbyID(int ID) throws SQLException {
        Ban B = new Ban();
        try {

            String req = "SELECT * FROM ban where ID_BAN=" + ID;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                B.setID_Ban(rs.getInt(1));
                B.setID_User(rs.getInt(2));
                B.setReason(rs.getString(3));
                B.setDate(rs.getDate(4).toLocalDate());


            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return B;

    }

    @Override
    public Ban fetchBanbyIDUser(int ID) throws SQLException {
        Ban B = new Ban();
        try {

            String req = "SELECT b.*, u.nickname " + "FROM ban b " + "JOIN allusers u ON b.id_user = u.id_user " + "WHERE b.id_user =" + ID;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                B.setID_Ban(rs.getInt(1));
                B.setID_User(rs.getInt(2));
                B.setReason(rs.getString(3));
                B.setDate(rs.getDate(4).toLocalDate());
                B.setNickname(rs.getString("nickname"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return B;
    }

  
}
