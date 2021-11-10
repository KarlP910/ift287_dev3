package AubergeInn;

import AubergeInn.tables.*;
import AubergeInn.transactions.GestionChambre;
import AubergeInn.transactions.GestionClient;
import AubergeInn.transactions.GestionCommodite;
import AubergeInn.transactions.GestionReservation;

import java.sql.SQLException;

public class GestionSystem {


    private Connexion cx;
    private Chambre chambre;
    private Client client;
    private Commodite commodite;
    private Reservations reservations;
    private Service service;
    private GestionChambre gestionChambre;
    private GestionClient gestionClient;
    private GestionCommodite gestionCommodite;
    private GestionReservation gestionReservation;



    public GestionSystem(String serveur, String bd, String user, String password) throws SQLException, IFT287Exception {


        cx = new Connexion(serveur,bd,user,password);
        chambre = new Chambre(cx);
        client = new Client(cx);
        commodite = new Commodite(cx);
        reservations = new Reservations(cx);
        service=new Service(cx);

        // Verifier les arguments passer dans les setter, argument bidon pour l'instant
        //**********************************************************************
        //**************************************************************************
        //TODO
        //TODO
        setGestionChambre(new GestionChambre(chambre, reservations,commodite,service));
        setGestionClient(new GestionClient(client, reservations));
        setGestionCommodite(new GestionCommodite(commodite, reservations));
        setGestionReservation(new GestionReservation(reservations,chambre,client));

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
    public Commodite getTableCommodite() { return commodite; }
}
