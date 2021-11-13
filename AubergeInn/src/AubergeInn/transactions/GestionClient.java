package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.Clients;
import AubergeInn.tables.Reservations;
import AubergeInn.tuples.Client;

import java.sql.SQLException;


public class GestionClient {

    private final Connexion cx;
    private final Clients clients;

    //Constructeur de GestionClient
    public GestionClient(Clients clients){
        this.clients = clients;
        this.cx= clients.getConnexion();
    }

    /**
     * Ajout d'un nouveau client dans la base de données. S'il existe déjà, une
     * exception est levée.
     */
    //Ajoute un client a la db
    public void ajoutClient(int idClient,String prenom,String nom,int age)
    throws SQLException,IFT287Exception,Exception
    {
        try{
            cx.demarreTransaction();
            Client c=new Client(idClient,prenom,nom,age);
            //Vérifie si le client est déjà dans la base de données
            if(clients.existe(idClient))
                throw new IFT287Exception("Le client: "+idClient +" existe déjà.");

            //Ajoute le client dans la base de données
            clients.ajoutClient(c);

            //Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    /**
     * Suppression d'un client de la base de données si le client existe et que le client n'a plus de réservation
     */
    public void supprimerClient(int idClient)
            throws SQLException,IFT287Exception,Exception
    {
        try{
            cx.demarreTransaction();
            Client client= clients.getClient(idClient);
            //Vérifie si le client est déjà dans la base de données
            if(!clients.existe(idClient))
                throw new IFT287Exception("Le client: "+idClient +" n'existe pas.");

            clients.supprimerClient(client);
            //Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    /**
     * Retourne toutes les informations du client
     */
    public Client getClient(int idClient) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            cx.demarreTransaction();

            Client client = clients.getClient(idClient);
            if (client == null)
                throw new IFT287Exception("Le client n'existe pas.");
            cx.commit();
            return client;
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
}
