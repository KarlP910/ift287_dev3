package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.Chambre;
import AubergeInn.tables.Commodite;
import AubergeInn.tables.Reservation;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleCommodite;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionCommodite {

    private Connexion cx;
    private Commodite commodite;
    private Reservation reserv;

    public GestionCommodite(Commodite commodite, Reservation reserv){
        this.commodite=commodite;
        this.reserv=reserv;
        this.cx=commodite.getConnexion();
    }
    /**
     * Ajoute une commodité au système
     */
    public void ajouterCommodite(int idCommodite,String description,float surplus_prix )
            throws SQLException, IFT287Exception,Exception
    {
        try{
            //Vérifie si la commodite est déjà dans la base de données
            if(commodite.existe(idCommodite))
                throw new IFT287Exception("La commodite: "+idCommodite +" est deja inclus");


            //Ajoute le client dans la base de données
            commodite.ajouterCommodite(idCommodite,description,surplus_prix);

            //Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
    public double getPrix(int id) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            TupleCommodite tupleCommodite = commodite.getCommodite(id);
            if (tupleCommodite == null)
                throw new IFT287Exception("La commodite n'existe deja.");

            TupleCommodite commodites= commodite.getCommodite(id);
            cx.commit();
            return commodites.getSurplus_prix();
        }
        catch(Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

}
