package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleChambre;

import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Chambre {

    private Connexion cx;
    /*
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;
    private final PreparedStatement stmtUpdateCommodite;
    private final PreparedStatement  stmtSelectAll;
    */
    private TypedQuery<TupleChambre> stmtExiste;

    private TypedQuery<TupleChambre> stmtUpdateCommodite;
    private TypedQuery<TupleChambre> stmtSelectAll;





    public Chambre(Connexion cx) throws SQLException {
        this.cx=cx;

        stmtExiste = cx.getConnection().createQuery("select l from TupleChambre l where l.c_idChambre = :idChambre", TupleChambre.class);

        stmtUpdateCommodite = cx.getConnection().createQuery("select l from TupleChambre l where l.c_idCommodite = :Commodite", TupleChambre.class);

        stmtSelectAll = cx.getConnection().createQuery("select l from TupleChambre l", TupleChambre.class);


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
        return cx;
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
        stmtExiste.setParameter("idChambre", idChambre);
        return !stmtExiste.getResultList().isEmpty();

    }


    /**
     * Ajoute une chambre au système
     */
    public TupleChambre ajouterChambre(TupleChambre chambre)
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
    public boolean supprimerChambre(TupleChambre chambre)
            throws SQLException{

        if(chambre != null)
        {
            cx.getConnection().remove(chambre);
            return true;
        }
        return false;

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

        stmtExiste.setParameter("idChambre", idChambre);
        List<TupleChambre> chambres = stmtExiste.getResultList();
        if(!chambres.isEmpty())
        {
            return chambres.get(0);
        }
        else
        {
            return null;
        }
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
