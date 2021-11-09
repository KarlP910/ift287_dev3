package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleCommodite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Commodite {
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;
 //   private final PreparedStatement stmtExisteReserv;

    private final Connexion cx;

    public Commodite(Connexion cx) throws SQLException {
        this.cx=cx;
        stmtExiste =cx.getConnection().prepareStatement(
                "select idCommodite, description, surplus_prix from commodite where idCommodite = ?");
        stmtInsert =cx.getConnection().prepareStatement(
                "insert into commodite(idCommodite, description, surplus_prix) "+ " values(?,?,?)");
        stmtUpdate=cx.getConnection().prepareStatement(
                "update commodite set idCommodite = ?"); //inclure une commodité
        stmtDelete=cx.getConnection().prepareStatement(
                "delete from commodite where idCommodite = ?");
        //stmtExisteCommoddite=cx.getConnection().prepareStatement(
               // "select idClient, prenom, nom, age,idReservation from client where idReservation=?");

    }
    /**
     * Vérifie si une commodité existe dans le système
     */
    public boolean existe(int idCommodite) throws SQLException
    {
        stmtExiste.setInt(1, idCommodite);
        ResultSet rset = stmtExiste.executeQuery();
        boolean commoditeExiste = rset.next();
        rset.close();
        return commoditeExiste;
    }

    public Connexion getConnexion(){
        return cx;
    }
    /**
     * Lecture d'une commodite
     */
    public TupleCommodite getCommodite(int idCommodite)
            throws SQLException{
        stmtExiste.setInt(1,idCommodite);
        ResultSet rset=stmtExiste.executeQuery();

        if (rset.next())
        {
            // idChambre, nom_chambre, type_lit,prix_base,idCommodite
            TupleCommodite commodite= new TupleCommodite(rset.getInt("idCommodite"),
                    rset.getString("description"),
                    rset.getFloat("surplus_prix"));


            return  commodite;
        }
        else {
            return null;
        }
    }
    /**
     * Ajoute une commodité au système
     */
    public void ajouterCommodite(int idCommodite,String description,float surplus_prix) throws SQLException
    {
        /* Ajout du membre. */
        stmtInsert.setInt(1, idCommodite);
        stmtInsert.setString(2, description);
        // pas sur du string.valueOf  à voir *************************
        stmtInsert.setFloat(3, surplus_prix);
        stmtInsert.executeUpdate();
    }
    public List<TupleCommodite> getAll(ArrayList<Integer> listeId) throws SQLException
    {
        List<TupleCommodite> listeCommodite = new LinkedList<TupleCommodite>();
        for (int id : listeId)
        {
            listeCommodite.add(getCommodite(id));
        }
        return listeCommodite;
    }
}
