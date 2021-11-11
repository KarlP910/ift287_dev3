package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.Commodites;
import AubergeInn.tables.Reservations;
import AubergeInn.tuples.Commodite;

import java.sql.SQLException;

public class GestionCommodite {

    private Connexion cx;
    private Commodites commodites;
    private Reservations reserv;

    public GestionCommodite(Commodites commodites, Reservations reserv){
        this.commodites = commodites;
        this.reserv=reserv;
        this.cx= commodites.getConnexion();
    }
    /**
     * Ajoute une commodité au système
     */
    public void ajouterCommodite(int idCommodite,String description,float surplus_prix )
            throws SQLException, IFT287Exception,Exception
    {
        try{
            cx.demarreTransaction();
            Commodite c=new Commodite(idCommodite,description,surplus_prix);
            //Vérifie si la commodite est déjà dans la base de données
            if(commodites.existe(idCommodite))
                throw new IFT287Exception("La commodite: "+idCommodite +" est deja inclus");


            //Ajoute le client dans la base de données
            commodites.ajouterCommodite(c);

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
            Commodite tupleCommodite = this.commodites.getCommodite(id);
            if (tupleCommodite == null)
                throw new IFT287Exception("La commodite n'existe deja.");

            Commodite commodite = this.commodites.getCommodite(id);
            cx.commit();
            return commodite.getSurplus_prix();
        }
        catch(Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

}
