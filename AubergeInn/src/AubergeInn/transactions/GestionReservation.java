package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.Chambre;
import AubergeInn.tables.Client;
import AubergeInn.tables.Reservations;
import AubergeInn.tuples.Reservation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class GestionReservation {

    private Connexion cx;
    private Reservations reservation;
    private Client client;
    private Chambre chambre;

    public GestionReservation(Reservations reservation,Chambre chambre,Client client){
        this.reservation=reservation;
        this.chambre=chambre;
        this.client=client;
        this.cx=reservation.getConnexion();
    }



    /**
     * Affiche une liste des reservations faites par les clients
     */
    public List<Reservation> listReservationClient(int idClient)
            throws SQLException, IFT287Exception,Exception{
        try {
            if (!client.existe(idClient))
                throw new IFT287Exception("Le client" + idClient + " n'existe pas");

                List<Reservation> reserv=reservation.listeReservationClient(idClient);
            cx.commit();
            return reservation.listeReservationClient(idClient);

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
            Reservation reservation = this.reservation.getReservation(idChambre, dateDebut, dateFin);
            if(dateDebut.after(dateFin))
                throw new IFT287Exception("La date de la reservation n'est pas valide.");
            if(reservation != null)
                throw new IFT287Exception("Il y a un conflit avec une autre reservation");

            this.reservation.reserver(idClient,idChambre,dateDebut,dateFin);

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
