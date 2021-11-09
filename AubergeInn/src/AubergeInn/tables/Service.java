package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Service {

    // Code inspire de l'exemple de bibliotheque
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;
    private final PreparedStatement stmtGetAll;


    private final Connexion cx;

    public Service(Connexion cx) throws SQLException
    {
        this.cx = cx;
        stmtExiste = cx.getConnection().prepareStatement(
                "select idChambre, idCommodite from Service where idChambre = ? and idCommodite = ?");
        stmtInsert = cx.getConnection()
                .prepareStatement("insert into Service (idChambre, idCommodite) "
                        + "values (?,?)");
        stmtUpdate = cx.getConnection()
                .prepareStatement("update Service set idChambre = ?, idCommodite = ? " + "where idChambre = ? and idCommodite = ? ");
        stmtDelete = cx.getConnection().prepareStatement("delete from Service where idChambre = ? and idCommodite = ?");
        stmtGetAll = cx.getConnection().prepareStatement(
                "select idCommodite from Service where idChambre = ?");
    }

    public TupleService getService(int idChambre, int idCommodite) throws SQLException
    {
        stmtExiste.setInt(1, idChambre);
        stmtExiste.setInt(2, idCommodite);

        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            TupleService tupleService = new TupleService(idChambre, idCommodite);
            rset.close();
            return tupleService;
        }
        else
            return null;
    }

    public ArrayList<Integer> getService(int idChambre) throws SQLException
    {
        ArrayList<Integer> listeCommoditeId = new ArrayList<Integer>();
        stmtGetAll.setInt(1, idChambre);
        ResultSet rset = stmtGetAll.executeQuery();
        while(rset.next())
        {
            listeCommoditeId.add(rset.getInt("idCommodite"));
        }
        rset.close();
        return listeCommoditeId;
    }

    public Connexion getConnexion()
    {
        return cx;
    }

    public void inclureCommodite(int idChambre, int idCommodite) throws SQLException
    {
        stmtInsert.setInt(1, idChambre);
        stmtInsert.setInt(2, idCommodite);
        stmtInsert.executeUpdate();
    }

    public void enleverCommodite(int idChambre, int idCommodite) throws SQLException
    {
        stmtDelete.setInt(1, idChambre);
        stmtDelete.setInt(2, idCommodite);
        stmtDelete.executeUpdate();
    }
}