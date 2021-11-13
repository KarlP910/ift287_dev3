package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.Commodite;

import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class Commodites {

    private final TypedQuery<Commodite> stmtExiste;
    private final Connexion cx;

    //Constructeur de Commodites
    public Commodites(Connexion cx) throws SQLException {
        this.cx=cx;
        stmtExiste=cx.getConnection().createQuery("select c from Commodite c where c.m_idCommodite = :idCommodite",Commodite.class);
    }
    /**
     * Vérifie si une commodité existe dans le système
     */
    public boolean existe(int idCommodite) throws SQLException
    {
        stmtExiste.setParameter("idCommodite", idCommodite);
        return !stmtExiste.getResultList().isEmpty();
    }

    //Établie la connexion
    public Connexion getConnexion(){
        return cx;
    }

    /**
     * Lecture d'une commodite
     */
    public Commodite getCommodite(int idCommodite)
            throws SQLException{
        stmtExiste.setParameter("idCommodite",idCommodite);
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
}