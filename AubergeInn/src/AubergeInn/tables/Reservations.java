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
    private final Connexion cx;

        public Reservations(Connexion cx) throws SQLException
        {
            this.cx=cx;
            stmtExiste = cx.getConnection().createQuery("select r from Reservation r where r.m_chambre = :chambre and r.m_client = : client", Reservation.class);
            stmtExisteChambre = cx.getConnection().createQuery("select r from Reservation r where r.m_chambre = :chambre", Reservation.class);
            stmtExisteClient = cx.getConnection().createQuery("select r from Reservation r where r.m_client = :client", Reservation.class);


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

    public void reserver(int idClient, int idChambre, Date dateDebut, Date dateFin) throws SQLException
    {
        /* Ajout de la reservation. */
        stmtInsertReservation.setInt(1, idClient);
        stmtInsertReservation.setInt(2, idChambre);
        stmtInsertReservation.setDate(3, dateDebut);
        stmtInsertReservation.setDate(4, dateFin);
        stmtInsertReservation.executeUpdate();
    }
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
    public Reservation getReservationChambre(int idChambre) throws SQLException
    {
        stmtExisteChambre.setInt(1, idChambre);
        ResultSet rset = stmtExisteChambre.executeQuery();
        if (rset.next())
        {
            Reservation reservation = new Reservation();
            reservation.setIdClient(rset.getInt(1));
            reservation.setIdChambre(rset.getInt(2));
            reservation.setDate_debut(rset.getDate(3));
            reservation.setDate_fin(rset.getDate(4));
            return reservation;
        }
        else
        {
            return null;
        }
    }
    /**
     * Lecture de la réservation d'un client
     */
    public Reservation getReservationClient(int idClient) throws SQLException
    {
        stmtExisteClient.setInt(1, idClient);
        ResultSet rset = stmtExisteClient.executeQuery();
        if (rset.next())
        {
            Reservation reservation = new Reservation();
            reservation.setIdClient(rset.getInt(1));
            reservation.setIdChambre(rset.getInt(2));
            reservation.setDate_debut(rset.getDate(3));
            reservation.setDate_fin(rset.getDate(4));
            return reservation;
        }
        else
        {
            return null;
        }
    }
    /**
     * Lecture d'une liste de reservation de client
     */
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
}


