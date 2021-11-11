package AubergeInn;

import AubergeInn.tables.*;
import AubergeInn.transactions.GestionChambre;
import AubergeInn.transactions.GestionClient;
import AubergeInn.transactions.GestionCommodite;
import AubergeInn.transactions.GestionReservation;

import java.sql.SQLException;

public class GestionSystem {


    private Connexion cx;
    private Chambres chambres;
    private Clients clients;
    private Commodites commodites;
    private Reservations reservations;

    private GestionChambre gestionChambre;
    private GestionClient gestionClient;
    private GestionCommodite gestionCommodite;
    private GestionReservation gestionReservation;



    public GestionSystem(String serveur, String bd, String user, String password) throws SQLException, IFT287Exception {


        cx = new Connexion(serveur,bd,user,password);
        chambres = new Chambres(cx);
        clients = new Clients(cx);
        commodites = new Commodites(cx);
        reservations = new Reservations(cx);

        // Verifier les arguments passer dans les setter, argument bidon pour l'instant
        //**********************************************************************
        //**************************************************************************
        //TODO
        //TODO
        setGestionChambre(new GestionChambre(chambres, reservations,commodites));
        setGestionClient(new GestionClient(clients, reservations));
        setGestionCommodite(new GestionCommodite(commodites, reservations));
        setGestionReservation(new GestionReservation(reservations, chambres, clients));

    }

    public void fermer() throws  SQLException {

        cx.fermer();
    }

    public GestionChambre getGestionChambre() {
        return gestionChambre;
    }

    private void setGestionChambre (GestionChambre gestionChambre) {
        this.gestionChambre = gestionChambre;
    }

    public GestionClient getGestionClient() {
        return gestionClient;
    }

    private void setGestionClient(GestionClient gestionClient) {
        this.gestionClient = gestionClient;
    }

    public GestionReservation getGestionReservation() {
        return gestionReservation;
    }

    private void setGestionReservation(GestionReservation gestionReservation) {
        this.gestionReservation = gestionReservation;
    }

    public GestionCommodite getGestionCommodite() {

        return gestionCommodite;
    }

    private void setGestionCommodite(GestionCommodite gestionCommodite) {

        this.gestionCommodite = gestionCommodite;
    }
    public Commodites getTableCommodite() { return commodites; }
}
