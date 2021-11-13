package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.*;
import AubergeInn.tuples.Chambre;
import AubergeInn.tuples.Commodite;

import java.sql.SQLException;
import java.util.*;

public class GestionChambre {

    private final Connexion cx;
    private final Chambres chambres;
    private final Reservations reserv;
    private final Commodites commodites;

    //Constructeur de Gestion Chambre
    public GestionChambre(Chambres chambres, Reservations reserv,Commodites commodites) {
        this.chambres = chambres;
        this.reserv = reserv;
        this.cx = chambres.getConnexion();
        this.commodites=commodites;
    }

    /**
     * Ajoute une chambre
     */
    public void ajouterChambre(int idChambre, String nom_chambre, String type_lit, float prix_base) //a revoir pour commodite et reserv
            throws SQLException, IFT287Exception, Exception {

        try {

            cx.demarreTransaction();

            Chambre chambre = new Chambre(idChambre, nom_chambre, type_lit, prix_base);

            if (this.chambres.existe(idChambre))
                throw new IFT287Exception("La chambre existe déjà: " + idChambre);

            this.chambres.ajouterChambre(chambre);

            this.cx.commit();
        } catch (Exception e) {
            this.cx.rollback();
            throw e;
        }
    }

    /**
     * Supprime une chambre si elle n'a pas de réservation future (ajouter aussi s'Il n'a plus de commodite)
     */
    public void supprimerChambre(int idChambre) //a revoir pour commodite et reserv
            throws SQLException, IFT287Exception, Exception {
        try {

            cx.demarreTransaction();

            Chambre chambre = chambres.getChambre(idChambre);
            if (chambre == null)
                throw new IFT287Exception("La chambre n'existe pas 1: " + idChambre);
            if (!chambres.existe(idChambre))
                throw new IFT287Exception("La chambre n'existe pas 2: " + idChambre);
            if (reserv.getReservationChambre(chambre) != null) {
                throw new IFT287Exception("La chambre " + idChambre + " a encore des réservations.");
            }

            if (!chambres.supprimerChambre(chambre))
                throw new IFT287Exception("Chambre" + idChambre + "inexistant");

            chambres.supprimerChambre(chambre);
            cx.commit();

        } catch (Exception e) {
            this.cx.rollback();
            throw e;
        }
    }

    /**
     * Affiche les commodites d'une chambre
     */
    public List<Commodite> afficherChambreCommodite(int idChambre)
            throws SQLException, IFT287Exception, Exception {

        try {
            cx.demarreTransaction();

            Chambre chambre=chambres.getChambre(idChambre);
            if(chambre==null){
                throw new IFT287Exception("La chambre n'existe pas");
            }
            List<Commodite> listeCommodites=chambres.getAllCommodites(chambre);
            cx.commit();
            return  listeCommodites;

        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    /**
     * Affiche les chambres avec leurs informations
     *
     * @return
     */
    public Chambre afficherChambre(int idChambre)
            throws SQLException, IFT287Exception, Exception {

        try {
            cx.demarreTransaction();

            Chambre c = chambres.getChambre(idChambre);

            cx.commit();
            return c;
        }catch (Exception e){
            cx.rollback();
            throw e;
        }
    }

    //Affiche les chambres libres (retourne la liste)
    public List<Chambre> afficherChambresLibres() throws SQLException, IFT287Exception, Exception {

        try {

        cx.demarreTransaction();

        List<Chambre> listeChambre = chambres.afficherChambreLibre();
        List<Chambre> listBonneChambre=new LinkedList<Chambre>();
        for (Chambre ci : listeChambre) {
             if(reserv.getReservationChambre(ci)==null)
                listBonneChambre.add(ci);

        }

        cx.commit();
        return listBonneChambre;
    }catch (Exception e){
            cx.rollback();
            throw e;
        }
    }

    // Inclue une commodite à une chambre
    public void inclureCommodite(int idChambre, int idCommodite) throws SQLException, IFT287Exception, Exception {
        try {

            cx.demarreTransaction();
            Chambre chambre = chambres.getChambre(idChambre);
            Commodite comm = commodites.getCommodite(idCommodite);
            if (chambre == null && comm == null)
                throw new IFT287Exception("La chambre et la commodite n'existent pas");
            if (chambre == null)
                throw new IFT287Exception("La chambre n'existe pas");
            if (comm == null)
                throw new IFT287Exception("La commodite n'existe pas");

            chambres.inclureCommodite(chambre,comm);
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    // Enleve une commodite a une chambre
    public void enleverCommodite(int idChambre, int idCommodite) throws SQLException, IFT287Exception, Exception {
        try {
            cx.demarreTransaction();
            Chambre chambre = chambres.getChambre(idChambre);
            Commodite comm = commodites.getCommodite(idCommodite);
            if (comm == null)
                throw new IFT287Exception("Ce service n'existe pas");
            chambres.enleverCommodite(chambre, comm);
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
}
