package interfaces;

import models.Ban;

import java.sql.SQLException;
import java.util.List;

public interface BanInterface {
    public void AddBan(Ban B) throws SQLException;

    public void DeleteBan(int ID) throws SQLException;

    public void ModifyBan(Ban B,int ID) throws SQLException;

    public void ModifyBanByUser(Ban B, int ID) throws SQLException;

    public List<Ban> fetchBan();

    public Ban fetchBanbyID(int ID) throws SQLException;
    public Ban fetchBanbyIDUser(int ID) throws SQLException;
}
