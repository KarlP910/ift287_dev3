package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.Client;
import AubergeInn.tables.Reservations;
import AubergeInn.tuples.TupleClient;

import java.sql.SQLException;


public class GestionClient {

    private Connexion cx;
    private Client client;
    private Reservations reserv;

    public GestionClient(Client client, Reservations reserv){
        this.client=client;
        this.reserv=reserv;
        this.cx=client.getConnexion();
    }
    /**
     * Ajout d'un nouveau membre dans la base de données. S'il existe déjà, une
     * exception est levée.
     */
    public void ajoutClient(int idClient,String prenom,String nom,int age)
    throws SQLException,IFT287Exception,Exception
    {
        try{
            //Vérifie si le client est déjà dans la base de données
            if(client.existe(idClient))
                throw new IFT287Exception("Le client: "+idClient +" existe déjà.");

            //Ajoute le client dans la base de données
            client.ajoutClient(idClient,prenom,nom,age);

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
    public void supprimerClient(int idClient )
            throws SQLException,IFT287Exception,Exception
    {
        try{
            Client clients=client.getClient(idClient);
            //Vérifie si le client est déjà dans la base de données
            if(!client.existe(idClient))
                throw new IFT287Exception("Le client: "+idClient +" n'existe pas.");
            //if(client.get PROBLEME POTENTIEL DE BD
            if(reserv.getReservationClient(clients) != null){
                throw new IFT287Exception("Le client "+idClient+" a encore des réservations.");
            }
            client.supprimerClient(idClient);
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
     * Affiche toutes les informations du client qui ont des réservations en cours et antérieure
     */
    public TupleClient afficherClient(int idClient)
            throws SQLException,IFT287Exception,Exception{
        try {
            if (!client.existe(idClient))
                throw new IFT287Exception("Le client " + idClient + " n'existe pas");


            cx.commit();
            return client.afficherClient(idClient);

        }
        catch(Exception e){
            cx.rollback();
            throw e;
        }


    }

}
