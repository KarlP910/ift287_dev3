package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.Chambres;
import AubergeInn.tables.Clients;
import AubergeInn.tables.Reservations;
import AubergeInn.tuples.Chambre;
import AubergeInn.tuples.Client;
import AubergeInn.tuples.Reservation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class GestionReservation {

    private Connexion cx;
    private Reservations reservation;
    private Clients clients;
    private Chambres chambres;


    //Constructeur de GestionReservation
    public GestionReservation(Reservations reservation, Chambres chambres, Clients clients) throws IFT287Exception{

        if (chambres.getConnexion() != clients.getConnexion() || reservation.getConnexion() != clients.getConnexion())
            throw new IFT287Exception (
                    "Les collections d'objets n'utilisent pas la même connexion au serveur");
        this.reservation=reservation;
        this.chambres = chambres;
        this.clients = clients;
        this.cx=reservation.getConnexion();
    }

    // Retourne la liste des reservations d'un client
    public List<Reservation> listReservationClient(Client c)
            throws SQLException, IFT287Exception,Exception{
        try {
            cx.demarreTransaction();

            List<Reservation> reserv=reservation.listeReservationClient(c);
            cx.commit();
            return reserv;
        }
        catch(Exception e){
            cx.rollback();
            throw e;
        }
    }

    /**
     * Réserve une chambre à un client avec une date de debut et une date de fin
     */
    public void reserver(int idClient, int idChambre, Date dateDebut, Date dateFin)
            throws SQLException, IFT287Exception,Exception
    {
        try{
            cx.demarreTransaction();
            Chambre chambre = chambres.getChambre(idChambre);
            Client client = clients.getClient(idClient);
            //Reservation reservation = this.reservation.getReservation(idChambre, dateDebut, dateFin);
            if(dateDebut.after(dateFin))
                throw new IFT287Exception("La date de la reservation n'est pas valide.");
            if( client == null)
                throw new IFT287Exception("Le client n'existe pas");
            if (chambre == null)
                throw new IFT287Exception("la chambre n'existe pas");
            if(reservation.getReservation(chambre,dateDebut,dateFin)!=null)
                throw new IFT287Exception("Il y a un conflit avec une autre reservation");

            Reservation r = new Reservation(client,chambre,dateDebut,dateFin);

            reservation.reserver(r);
            //Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
}
