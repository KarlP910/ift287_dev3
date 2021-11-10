package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.Reservation;

import javax.persistence.TypedQuery;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Reservations {

    private TypedQuery<Reservation> stmtExiste;
    private TypedQuery<Reservation> stmtExisteChambre;
    private TypedQuery<Reservation> stmtExisteClient;
    private TypedQuery<Reservation> stmtExisteReservation;
    private final Connexion cx;

        public Reservations(Connexion cx) throws SQLException
        {
            //pt verif sur reserv
            this.cx=cx;
            stmtExiste = cx.getConnection().createQuery("select r from Reservation r where r.m_chambre = :chambre and r.m_client = : client", Reservation.class);
            stmtExisteChambre = cx.getConnection().createQuery("select r from Reservation r where r.m_chambre = :chambre", Reservation.class);
            stmtExisteClient = cx.getConnection().createQuery("select r from Reservation r where r.m_client = :client", Reservation.class);
            stmtExisteReservation=cx.getConnection().createQuery("select r from Reservation r where r.m_chambre = :chambre and r.m_date_debut <= :date and r.m_date_fin >:date", Reservation.class);


        }
    public Connexion getConnexion(){
        return cx;
    }


    public boolean existe(Client client,Chambre chambre) throws SQLException
    {

        stmtExiste.setParameter("1", chambre);
        stmtExiste.setParameter("2", client);
        return !stmtExiste.getResultList().isEmpty();
    }

    public void reserver(Reservation r) throws SQLException
    {
        /* Ajout de la reservation. */
      cx.getConnection().persist(r);
    }
    /**
    public Reservation getReservation(int idChambre, Date debut, Date fin)throws SQLException
    {

        stmtExisteReservation.setInt(1, idChambre);
        stmtExisteReservation.setDate(2, debut);
        stmtExisteReservation.setDate(3, fin);
        ResultSet rset = stmtExisteReservation.executeQuery();
        if (rset.next())
        {
            Reservation reservation = new Reservation(rset.getInt(1), idChambre, debut, fin);
            rset.close();
            return reservation;
        }
        else
        {
            return null;
        }
    }
    /**
     * Lecture de la réservation d'une chambre
     */
    public List<Reservation> getReservationChambre(Chambre chambre) throws SQLException
    {
        stmtExisteChambre.setParameter(1, chambre);
        List<Reservation> reservations = stmtExisteChambre.getResultList();
        if(!reservations.isEmpty())
        {
            return reservations;
        }
        return null;
    }
    /**
     * Lecture de la réservation d'un client
     */
    public List<Reservation> getReservationClient(Client client) throws SQLException
    {
        stmtExisteClient.setParameter(1, client);
        List<Reservation> reservations = stmtExisteClient.getResultList();
        if(!reservations.isEmpty())
        {
            return reservations;
        }
        return null;
    }
    /**
     * Lecture d'une liste de reservation de client

    public List<Reservation> listeReservationClient(int idClient) throws SQLException
    {
        stmtExisteClient.setInt(1, idClient);
        ResultSet rset = stmtExisteClient.executeQuery();
        List<Reservation> listeReserv=new LinkedList<Reservation>();
        //idClient,idChambre, date_debut, date_fin
        while (rset.next())
        {
         //   Reservation reservation = new Reservation(
                  //  rset.getInt("idClient"),
                  //  rset.getInt("idChambre"),
            //        rset.getDate("date_debut"),
            //        rset.getDate("date_fin"));
        //   listeReserv.add(reservation);
        }
       rset.close();
        return listeReserv;
    }

    public ArrayList<Integer> getAllChambre(Date date) throws SQLException
    {
        ArrayList<Integer> listeChambre= new ArrayList<Integer>();

        stmtGetAllChambre.setDate(1, date);
        stmtGetAllChambre.setDate(2, date);

        ResultSet rset = stmtGetAllChambre.executeQuery();

        while (rset.next())
        {
            listeChambre.add(rset.getInt(1));
        }
        rset.close();
        return listeChambre;
    }
     */
}


