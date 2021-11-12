package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.Client;

import javax.persistence.TypedQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Clients {

    /*
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
   // private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;
    private final PreparedStatement stmtExisteReserv;
*/
    private final Connexion cx;
    private TypedQuery<Client> stmtExiste;



    public Clients(Connexion cx) throws SQLException
    {
        this.cx=cx;
        stmtExiste = cx.getConnection().createQuery("select c from Client c where c.m_idClient = :idClient",Client.class);


        /*
        stmtExiste =cx.getConnection().prepareStatement(
                "select idClient, prenom, nom, age from client where idClient = ?");
        stmtInsert =cx.getConnection().prepareStatement(
                "insert into Client(idClient, prenom, nom, age) "+ " values(?,?,?,?)");
     //   stmtUpdate=cx.getConnection().prepareStatement(
      //          "update client set idReservation = ?");
        stmtDelete=cx.getConnection().prepareStatement(
                "delete from client where idClient = ?");
        stmtExisteReserv=cx.getConnection().prepareStatement(
                // A REVOIR
                //******************************************************************************
                "select client.prenom, client.nom, client.age,idReservation,chambre.prix_base,idReservation.date_debut,idReservation.date_fin from client " +
                        "INNER JOIN reservation on client.idReservation=idReservation.idReservation" +
                        "INNER JOIN chambre on reservation.idChambre=chambre.idReservation" +
                        "");// check s'il existe un reserv a une chambre
                 // check s'il existe un reserv a une chambre


         */
    }

    public Connexion getConnexion(){
        return cx;
    }

    /**
     * Vérifie si un client existe dans le système
     */
    public boolean existe(int idClient) throws SQLException
    {
        stmtExiste.setParameter("idClient", idClient);
        return !stmtExiste.getResultList().isEmpty();
    }


    /**
     * Ajout d'un nouveau client.
     */
    public Client ajoutClient(Client c) throws SQLException
    {
        cx.getConnection().persist(c);
        return c;
    }
    /**
     * Supression d'un client de la base de données.
     */
    public boolean supprimerClient(Client client) throws SQLException{
        if(client != null)
        {
            cx.getConnection().remove(client);
            return true;
        }
        return false;

    }

//Peut-etre pas a faire car on va afficher le clients correspondant dans le main
    /**  A faire
     * Afficher les clients

    public Client afficherClient(int idClient) throws SQLException{
        stmtExiste.setInt(1,idClient);
       ResultSet rset= stmtExiste.executeQuery();
        if (rset.next())
        {
            Client tupleClient = new Client();
            tupleClient.setIdClient(idClient);
            tupleClient.setPrenom(rset.getString(2));
            tupleClient.setNom(rset.getString(3));
            tupleClient.setAge(rset.getInt(4));
            rset.close();
            return tupleClient;
        }
        else {
            return null;
        }
    }
*/
    /** A faire
     * Lecture d'un client
     */
    public Client getClient(int idClient) throws SQLException
    {
        stmtExiste.setParameter("idClient", idClient);
        List<Client> clients = stmtExiste.getResultList();
        if(!clients.isEmpty())
        {
            return clients.get(0);
        }
        else
        {
            return null;
        }
    }


}
