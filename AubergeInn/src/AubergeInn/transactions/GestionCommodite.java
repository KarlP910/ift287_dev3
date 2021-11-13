package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.Commodites;
import AubergeInn.tables.Reservations;
import AubergeInn.tuples.Commodite;

import java.sql.SQLException;

public class GestionCommodite {

    private final Connexion cx;
    private final Commodites commodites;
    private Reservations reserv;


    //Constructeur dde Gestion Commodite
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
            if(this.commodites.existe(idCommodite))
                throw new IFT287Exception("La commodite: "+idCommodite +" est deja inclus");


            //Ajoute le client dans la base de données
            commodites.ajouterCommodite(c);

            //Commit
            cx.commit();
        }
        catch (Exception e)
        {
            this.cx.rollback();
            throw e;
        }
    }
}
