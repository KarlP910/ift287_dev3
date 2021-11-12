package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.Chambre;
import AubergeInn.tuples.Client;
import AubergeInn.tuples.Reservation;

import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.Date;
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
            stmtExiste = cx.getConnection().createQuery("select r from Reservation r where r.m_chambres = :chambre and r.m_clients = : client", Reservation.class);
            stmtExisteChambre = cx.getConnection().createQuery("select r from Reservation r where r.m_chambres = :chambre", Reservation.class);
            stmtExisteClient = cx.getConnection().createQuery("select r from Reservation r where r.m_clients = :client", Reservation.class);
            stmtExisteReservation=cx.getConnection().createQuery("select r from Reservation r where r.m_chambres = :chambre and r.m_date_debut <= :Date and r.m_date_fin > :Date", Reservation.class);


        }
    public Connexion getConnexion(){
        return cx;
    }

    public void reserver(Reservation r) throws SQLException
    {
        /* Ajout de la reservation. */
      cx.getConnection().persist(r);
    }
    public Reservation getReservation(Chambre c,Date date_debut, Date date_fin)throws SQLException
    {
        stmtExisteReservation.setParameter("chambre", c);
        stmtExisteReservation.setParameter("Date", date_debut);
        stmtExisteReservation.setParameter("Date", date_fin);
        List<Reservation> reservations = stmtExisteReservation.getResultList();
        if(!reservations.isEmpty())
        {
            return reservations.get(0);
        }
        return null;

    }
    /**
     * Lecture de la réservation d'une chambre
     */
    public Reservation getReservationChambre(Chambre chambre) throws SQLException
    {
        stmtExisteChambre.setParameter("chambre", chambre);
        List<Reservation> reservations = stmtExisteChambre.getResultList();
        if(!reservations.isEmpty())
        {
            return reservations.get(0);
        }
        return null;
    }

    public List<Reservation> listeReservationClient(Client c) throws SQLException
    {
        stmtExisteClient.setParameter("client", c);
        List<Reservation> reservations=stmtExisteClient.getResultList();

        return reservations;
    }

}


