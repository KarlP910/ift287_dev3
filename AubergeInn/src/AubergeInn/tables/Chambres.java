package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.Chambre;
import AubergeInn.tuples.Commodite;

import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class Chambres {

    private Connexion cx;

    private TypedQuery<Chambre> stmtExiste;
    private TypedQuery<Chambre> stmtSelectAll;
    private TypedQuery<Chambre> stmtAfficherChambre;

    public Chambres(Connexion cx) {

        this.cx = cx;
        this.stmtExiste = cx.getConnection().createQuery("select l from Chambre l where l.c_idChambre = :idChambre", Chambre.class);

        this.stmtSelectAll = cx.getConnection().createQuery("select l from Chambre l", Chambre.class);

        this.stmtAfficherChambre = cx.getConnection().createQuery("select c from Chambre c", Chambre.class);

    }

    /**
     * Retourner la connexion associé
     */
    public Connexion getConnexion()
    {
        return this.cx;
    }

    /**
    Vérifie si une chambre existe
     */

    public boolean existe(int idChambre) throws SQLException
    {
        this.stmtExiste.setParameter("idChambre", idChambre);
        return !stmtExiste.getResultList().isEmpty();
    }

    /**
     * Ajoute une chambre au système
     */
    public Chambre ajouterChambre(Chambre chambre)
    throws SQLException{

        // Ajout d'une chambre.
        cx.getConnection().persist(chambre);

        return chambre;
    }

    /**
     * Supprime une chambre au système
     */
    public boolean supprimerChambre(Chambre chambre)
            throws SQLException{

        if(chambre != null)
        {
            this.cx.getConnection().remove(chambre);
            return true;
        }
        return false;

    }


    public List<Chambre> afficherChambreLibre()
            throws SQLException {
        //stmtAfficherChambre.setParameter("idChambre", idChambre);
        List<Chambre> chambres = stmtAfficherChambre.getResultList();
        return chambres;
    }

    /**  A faire
     * Lecture d'une chambre
     */
    public Chambre getChambre(int idChambre) {

        stmtExiste.setParameter("idChambre", idChambre);
        List<Chambre> chambres = stmtExiste.getResultList();
        if(!chambres.isEmpty()){
            return chambres.get(0);
        }
        else
        {
            return null;
        }


    }

    public List<Commodite> getAllCommodites(Chambre c) throws SQLException
    {

       return c.getAllCommodite();
    }

  // Vu qu'on est dans le modèle object une chambre a un attribut de commdité, alors c'est tout a fait normal d'avoir commodité pour un object chambre

    public void inclureCommodite(Chambre chambre, Commodite commodite) throws SQLException {
        chambre.inclureCommodite(commodite);

    }

    // A faire
    public void enleverCommodite(Chambre chambre, Commodite commodite) throws SQLException {
        chambre.enleverCommodite(commodite);
    }



}
