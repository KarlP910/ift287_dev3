package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.Commodite;

import javax.lang.model.element.TypeParameterElement;
import javax.persistence.TypedQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Commodites {

    //   private final PreparedStatement stmtExisteReserv;
    private TypedQuery<Commodite> stmtExiste;
    private TypedQuery<Commodite> stmtUpdate;
    private TypedQuery<Commodite> stmtDelete;
    private final Connexion cx;

    public Commodites(Connexion cx) throws SQLException {
        this.cx=cx;

        stmtExiste=cx.getConnection().createQuery("select c from Commodite c where c.m_idCommodite = :idCommodite",Commodite.class);
        //   stmtInsert=cx.getConnection().createQuery("select c from Commodite c where c.m_idCommodite = :idCommodite",Commodites.class);
     /*   stmtInsert =cx.getConnection().prepareStatement(
                "insert into commodite(idCommodite, description, surplus_prix) "+ " values(?,?,?)");
        stmtUpdate=cx.getConnection().prepareStatement(
                "update commodite set idCommodite = ?"); //inclure une commodité
        stmtDelete=cx.getConnection().prepareStatement(
                "delete from commodite where idCommodite = ?");
        //stmtExisteCommoddite=cx.getConnection().prepareStatement(
               // "select idClient, prenom, nom, age,idReservation from client where idReservation=?");
*/
    }
    /**
     * Vérifie si une commodité existe dans le système
     */
    public boolean existe(int idCommodite) throws SQLException
    {
        stmtExiste.setParameter(1, idCommodite);
        return !stmtExiste.getResultList().isEmpty();
    }

    public Connexion getConnexion(){
        return cx;
    }
    /**
     * Lecture d'une commodite
     */
    public Commodite getCommodite(int idCommodite)
            throws SQLException{
        stmtExiste.setParameter(1,idCommodite);
        List<Commodite> commodites = stmtExiste.getResultList();
        if(!commodites.isEmpty())
        {
            return commodites.get(0);
        }
        else
        {
            return null;
        }
    }
    /**
     * Ajoute une commodité au système
     */
    public Commodite ajouterCommodite(Commodite commodite) throws SQLException
    {
        /* Ajout d'une commodité au système. */
        cx.getConnection().persist(commodite);
        return commodite;

    }
    public void supprimerCommodite(Commodite commodite) throws SQLException
    {
        cx.getConnection().remove(commodite);
    }
}