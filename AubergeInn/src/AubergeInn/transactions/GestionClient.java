package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.Clients;
import AubergeInn.tables.Reservations;
import AubergeInn.tuples.Client;

import java.sql.SQLException;


public class GestionClient {

    private Connexion cx;
    private Clients clients;
    private Reservations reserv;

    public GestionClient(Clients clients, Reservations reserv){
        this.clients = clients;
        this.reserv=reserv;
        this.cx= clients.getConnexion();
    }
    /**
     * Ajout d'un nouveau membre dans la base de données. S'il existe déjà, une
     * exception est levée.
     */
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
    public void supprimerClient(int idClient )
            throws SQLException,IFT287Exception,Exception
    {
        try{
            cx.demarreTransaction();
            Client clients= this.clients.getClient(idClient);
            //Vérifie si le client est déjà dans la base de données
            if(!this.clients.existe(idClient))
                throw new IFT287Exception("Le client: "+idClient +" n'existe pas.");
            //if(client.get PROBLEME POTENTIEL DE BD
            if(reserv.getReservationClient(clients) != null){
                throw new IFT287Exception("Le client "+idClient+" a encore des réservations.");
            }
            this.clients.supprimerClient(clients);
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
    /*
    public Client afficherClient(int idClient)
            throws SQLException,IFT287Exception,Exception{
        try {
            if (!clients.existe(idClient))
                throw new IFT287Exception("Le client " + idClient + " n'existe pas");


            cx.commit();
            return clients.afficherClient(idClient);

        }
        catch(Exception e){
            cx.rollback();
            throw e;
        }


    }
    */

}
