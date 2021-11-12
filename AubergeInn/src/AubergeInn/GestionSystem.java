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


        this.cx = new Connexion(serveur,bd,user,password);
        this.chambres = new Chambres(this.cx);
        this.clients = new Clients(this.cx);
        this.commodites = new Commodites(this.cx);
        this.reservations = new Reservations(this.cx);

        // Verifier les arguments passer dans les setter, argument bidon pour l'instant
        //**********************************************************************
        //**************************************************************************
        //TODO
        //TODO
        this.setGestionChambre(new GestionChambre(this.chambres, this.reservations,this.commodites));
        this.setGestionClient(new GestionClient(this.clients, this.reservations));
        this.setGestionCommodite(new GestionCommodite(this.commodites, this.reservations));
        this.setGestionReservation(new GestionReservation(this.reservations, this.chambres, this.clients));

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
