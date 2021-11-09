package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Chambre {
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;
    private final PreparedStatement stmtUpdateCommodite;
    private final PreparedStatement  stmtSelectAll;

    private final Connexion cx;



    public Chambre(Connexion cx) throws SQLException {
        this.cx=cx;
        stmtExiste =cx.getConnection().prepareStatement(
                "select idChambre, nom_chambre, type_lit,prix_base,idCommodite from chambre where idChambre = ?");
        stmtInsert =cx.getConnection().prepareStatement(
                "insert into chambre(idChambre,nom_chambre, type_lit, prix_base,idCommodite) "+ " values(?,?,?,?,null)");
        stmtUpdate=cx.getConnection().prepareStatement(
                "update chambre set idCommodite = ? where idchambre= ?"); //inclure une commodité
        stmtDelete=cx.getConnection().prepareStatement(
                "delete from chambre where idChambre = ?"); //delete une chambre
        stmtUpdateCommodite=cx.getConnection().prepareStatement(
                "update chambre set idcommodite=null where idchambre=? AND idcommodite=?"); //delete une chambre
        stmtSelectAll = cx.getConnection()
                .prepareStatement("select * from Chambre");
    }
    //Vérifie si une chambre existe
    public boolean existe(int idChambre) throws SQLException
    {
        stmtExiste.setInt(1, idChambre);
        ResultSet rset = stmtExiste.executeQuery();
        boolean reservationExiste = rset.next();
        rset.close();
        return reservationExiste;
    }

    public Connexion getConnexion(){
        return cx;
    }

    /**
     * Ajoute une chambre au système
     */
    public void ajouterChambre(int idChambre,String nom_chambre,String type_lit,float prix_base)
    throws SQLException{

        stmtInsert.setInt(1,idChambre);
        stmtInsert.setString(2,nom_chambre);
        stmtInsert.setString(3,type_lit);
        stmtInsert.setFloat(4,prix_base);
        stmtInsert.executeUpdate();

    }

    /**
     * Supprime une chambre au système
     */
    public int supprimerChambre(int idChambre)
            throws SQLException{

        stmtDelete.setInt(1,idChambre);
        return stmtDelete.executeUpdate();

    }
    /**
     * Affiche une chambre
     */
    public TupleChambre afficherChambre(int idChambre)
    throws SQLException{
        stmtExiste.setInt(1,idChambre);
        ResultSet rset=stmtExiste.executeQuery();

        if (rset.next())
        {
            // idChambre, nom_chambre, type_lit,prix_base,idCommodite
            TupleChambre chambre= new TupleChambre(rset.getInt("idChambre"),
                    rset.getString("nom_chambre"),
                    rset.getString("type_lit"),
                    rset.getFloat("prix_base"),
                    rset.getInt("idCommodite"));

            return  chambre;
        }
        else {
            return null;
        }
    }
    /**
     * Lecture d'une chambre
     */
    public TupleChambre getChambre(int idChambre) throws SQLException {
        stmtExiste.setInt(1, idChambre);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            TupleChambre tupleChambre = new TupleChambre();
            tupleChambre.setIdChambre(rset.getInt(1));
            tupleChambre.setNom_chambre(rset.getString(2));
            tupleChambre.setType_lit(rset.getString(3));
            tupleChambre.setPrix_base(rset.getFloat(4));
            tupleChambre.setIdCommodite(rset.getInt(5));
            rset.close();
            return tupleChambre;
        }
        else
            return null;
    }
    public List<TupleChambre> getAll() throws SQLException
    {

        ResultSet rset = stmtSelectAll.executeQuery();

        List<TupleChambre> listeChambre= new LinkedList<TupleChambre>();
        while (rset.next())
        {
            TupleChambre chambre = new TupleChambre(rset.getInt("idChambre"),
                    rset.getString("nom_chambre"),
                    rset.getString("type_lit"),
                    rset.getFloat("prix_base"),
                    rset.getInt("idCommodite")//nom_chambre, type_lit,prix_base,idCommodite
            );

            listeChambre.add(chambre);
        }
        rset.close();
        return listeChambre;
    }
}
