package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.Chambre;
import AubergeInn.tuples.Commodite;

import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class Chambres {

    private Connexion cx;
    /*
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;
    private final PreparedStatement stmtUpdateCommodite;
    private final PreparedStatement  stmtSelectAll;
    */
    private TypedQuery<Chambre> stmtExiste;

    private TypedQuery<Chambre> stmtUpdateCommodite;
    private TypedQuery<Chambre> stmtSelectAll;
    private TypedQuery<Chambre> stmtAfficherChambre;
    private TypedQuery<Chambre> stmtAfficherChambreLibre;





    public Chambres(Connexion cx) {

        this.cx = cx;
        this.stmtExiste = cx.getConnection().createQuery("select l from Chambre l where l.c_idChambre = :idChambre", Chambre.class);

       // stmtUpdateCommodite = cx.getConnection().createQuery("select l from Chambre l where l.c_idCommodite = :Commodite", Chambre.class);

        this.stmtSelectAll = cx.getConnection().createQuery("select l from Chambre l", Chambre.class);

        this.stmtAfficherChambre = cx.getConnection().createQuery("select c from Chambre c", Chambre.class);

        //TODO
        this.stmtAfficherChambreLibre = cx.getConnection().createQuery("select c from Chambre c where c.idReservation is null", Chambre.class);
     



        /*
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

         */
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
        /*
        stmtExiste.setInt(1, idChambre);
        ResultSet rset = stmtExiste.executeQuery();
        boolean reservationExiste = rset.next();
        rset.close();
        return reservationExiste;

         */
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
        /*
        stmtInsert.setInt(1,idChambre);
        stmtInsert.setString(2,nom_chambre);
        stmtInsert.setString(3,type_lit);
        stmtInsert.setFloat(4,prix_base);
        stmtInsert.executeUpdate();


         */
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
    /** A faire
     * Affiche une chambre
     */
    public List<Chambre> afficherChambre(int idChambre)
    throws SQLException{

        stmtExiste.setParameter("idChambre", idChambre);
        //stmtAfficherChambre.setParameter("idChambre", idChambre);
        List<Chambre> chambres = stmtAfficherChambre.getResultList();
        return chambres;


        /*
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

         */

    }
    public List<Chambre> afficherChambreLibre()
            throws SQLException {
        //stmtAfficherChambre.setParameter("idChambre", idChambre);
        List<Chambre> chambres = stmtAfficherChambreLibre.getResultList();
        return chambres;
    }

    /**  A faire
     * Lecture d'une chambre
     */
    public Chambre getChambre(int idChambre) throws SQLException {

        this.stmtExiste.setParameter("idChambre", idChambre);
        List<Chambre> chambres = this.stmtExiste.getResultList();
        return !chambres.isEmpty() ? (Chambre)chambres.get(0) : null;
        /*

        if(!chambres.isEmpty())
        {
            return chambres.get(0);
        }
        else
        {
            return null;
        }

         */
        /*
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

         */
    }



    /**
     * A faire
     */

    /*
    public List<Chambre> getAll() throws SQLException
    {

        ResultSet rset = stmtSelectAll.executeQuery();

        List<Chambre> listeChambre= new LinkedList<Chambre>();
        while (rset.next())
        {
            Chambre chambre = new Chambre(rset.getInt("idChambre"),
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

     */

  // Vu qu'on est dans le modèle object une chambre a un attribut de commdité, alors c'est tout a fait normal d'avoir commodité pour un object chambre

    public void inclureCommodite(Chambre chambre, Commodite commodite) throws SQLException {
        chambre.inclureCommodite(commodite);
    }

    // A faire
    public void enleverCommodite(Chambre chambre, Commodite commodite) throws SQLException {
        chambre.enleverCommodite(commodite);
    }



}
