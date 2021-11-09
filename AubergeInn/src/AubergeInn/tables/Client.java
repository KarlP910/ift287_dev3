package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleClient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
   // private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;
    private final PreparedStatement stmtExisteReserv;

    private final Connexion cx;

    public Client(Connexion cx) throws SQLException
    {
        this.cx=cx;
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

    }
    /**
     * Vérifie si un client existe dans le système
     */
    public boolean existe(int idClient) throws SQLException
    {
        stmtExiste.setInt(1, idClient);
        ResultSet rset = stmtExiste.executeQuery();
        boolean reservationExiste = rset.next();
        rset.close();
        return reservationExiste;
    }

    public Connexion getConnexion(){
        return cx;
    }
    /**
     * Ajout d'un nouveau client.
     */
    public void ajoutClient(int idClient,String prenom,String nom,int age) throws SQLException
    {
        /* Ajout du membre. */
        stmtInsert.setInt(1, idClient);
        stmtInsert.setString(2, prenom);
        stmtInsert.setString(3, nom);
        stmtInsert.setInt(4, age);
        stmtInsert.executeUpdate();
    }
    /**
     * Supression d'un client de la base de données.
     */
    public int supprimerClient(int idClient) throws SQLException{
        stmtDelete.setInt(1,idClient);
        return stmtDelete.executeUpdate();
    }
    /**
     * Afficher les clients
     */
    public TupleClient afficherClient(int idClient) throws SQLException{
        stmtExiste.setInt(1,idClient);
       ResultSet rset= stmtExiste.executeQuery();
        if (rset.next())
        {
            TupleClient tupleClient = new TupleClient();
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

    /**
     * Lecture d'un client
     */
    public TupleClient getClient(int idClient) throws SQLException
    {
        stmtExiste.setInt(1, idClient);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            TupleClient tupleClient = new TupleClient();
            tupleClient.setIdClient(idClient);
            tupleClient.setPrenom(rset.getString(2));
            tupleClient.setNom(rset.getString(3));
            tupleClient.setAge(rset.getInt(4));
            rset.close();
            return tupleClient;
        }
        else
        {
            return null;
        }
    }


}
