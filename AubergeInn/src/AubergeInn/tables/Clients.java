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


    //Constructeur de clients
    public Clients(Connexion cx) throws SQLException
    {
        this.cx=cx;
        stmtExiste = cx.getConnection().createQuery("select c from Client c where c.m_idClient = :idClient",Client.class);

    }
    //Établie la connexion
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
