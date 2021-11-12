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

/*
    public boolean existe(Clients clients, Chambres chambres) throws SQLException
    {

        stmtExiste.setParameter("1", chambres);
        stmtExiste.setParameter("2", clients);
        return !stmtExiste.getResultList().isEmpty();
    }


 */
    public boolean existe(int idReservation)
    {
        stmtExiste.setParameter("idReservation", idReservation);
        return !stmtExiste.getResultList().isEmpty();
    }

    public void reserver(Reservation r) throws SQLException
    {
        /* Ajout de la reservation. */
      cx.getConnection().persist(r);
    }

    public Reservation getReservation(int idChambre, Date debut, Date fin)throws SQLException
    {

        stmtExisteReservation.setParameter("idChambre", idChambre);
        stmtExisteReservation.setParameter("debut", debut);
        stmtExisteReservation.setParameter("fin", fin);
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
    public List<Reservation> getReservationChambre(Chambre chambre) throws SQLException
    {
        stmtExisteChambre.setParameter("chambre", chambre);
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
    public List<Reservation> getReservationClient(Client clients) throws SQLException
    {
        stmtExisteClient.setParameter("clients", clients);
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


