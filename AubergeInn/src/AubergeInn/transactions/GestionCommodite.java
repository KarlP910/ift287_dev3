package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.Reservations;
import AubergeInn.tuples.Commodite;

import java.sql.SQLException;

public class GestionCommodite {

    private Connexion cx;
    private AubergeInn.tables.Commodite commodite;
    private Reservations reserv;

    public GestionCommodite(AubergeInn.tables.Commodite commodite, Reservations reserv){
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
            Commodite tupleCommodite = this.commodite.getCommodite(id);
            if (tupleCommodite == null)
                throw new IFT287Exception("La commodite n'existe deja.");

            Commodite commodite = this.commodite.getCommodite(id);
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
