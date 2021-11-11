package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.*;
import AubergeInn.tuples.Chambre;
import AubergeInn.tuples.Commodite;
import AubergeInn.tuples.TupleService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class GestionChambre {

    private Connexion cx;
    private Chambres chambres;
    private Reservations reserv;
    private Commodites commodites;


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

            if (chambres.existe(idChambre))
                throw new IFT287Exception("La chambre existe déjà: " + idChambre);
            //chambre.ajouterChambre(idChambre,nom_chambre,type_lit,prix_base);

            chambres.ajouterChambre(chambre);

            cx.commit();
        } catch (Exception e) {
            cx.rollback();
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
                throw new IFT287Exception("La chambre n'existe pas: " + idChambre);
            if (!chambres.existe(idChambre))
                throw new IFT287Exception("La chambre n'existe pas: " + idChambre);
            if (reserv.getReservationChambre(chambre) != null) {
                throw new IFT287Exception("La chambre " + idChambre + " a encore des réservations.");
            }

            if (!chambres.supprimerChambre(chambre))
                throw new IFT287Exception("Chambre" + idChambre + "inexistant");

            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    /**
     * À faire
     */

    /*
    public Commodite afficherChambreCommodite(int idChambre)
            throws SQLException, IFT287Exception, Exception {

        cx.demarreTransaction();


        try {


            cx.commit();
            return commodite.getCommodite(idChambre);


        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }


     */

    /**
     * Affiche les chambres avec leurs informations et avec les commodités offertes
     *
     * @return
     */
    public void afficherChambre(int idChambre)
            throws SQLException, IFT287Exception, Exception {

        cx.demarreTransaction();

        List<Chambre> c = chambres.afficherChambre(idChambre);

        for (Chambre ci : c) {
            System.out.println(ci.toString());
        }
        cx.commit();

    }

    /*
        try{
            if(chambres.existe(idChambre)){

                cx.commit();
                return chambres.afficherChambre(idChambre);
            }
            else{
                throw new IFT287Exception("La chambre que vous voulez affichez n'existe pas "+ idChambre);
            }


        }catch (Exception e){
            cx.rollback();
            throw e;
        }

     */

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
        //    TupleService tupleService = service.getService(idChambre, idCommodite);
         //   if (tupleService != null)
         //       throw new IFT287Exception("Ce service existe deja.");

           chambres.inclureCommodite(chambre, comm);
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

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

/*
    public double prixServices(int idChambre, GestionCommodite gestionCommodite) throws SQLException, IFT287Exception, Exception {
        try {
            double total = 0;
            ArrayList<Integer> listeCommoditeId = service.getService(idChambre);
            for (int id : listeCommoditeId) {
                total += gestionCommodite.getPrix(id);
            }
            cx.commit();
            return total;
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

*/
    public void afficherChambresLibres(Date date) throws SQLException, IFT287Exception, Exception {

        cx.demarreTransaction();

        List<Chambre> c = chambres.afficherChambreLibre();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(GregorianCalendar.DATE, -1);
        for (Chambre ci : c) {
            if (ci.getLouer() != null) {
                if (ci.getDateLouer().before(calendar.getTime())) {
                    System.out.println(ci.toString());
                }
            }
        }

        cx.commit();

        /*
        try
        {
            List<Chambre> listeChambres = chambres.getAll();
            // code venant de stack overflow https://stackoverflow.com/questions/18257648/get-the-current-date-in-java-sql-date-format
            ArrayList<Integer> listeChambreReserveId = reserv.getAllChambre(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

            for(Integer id : listeChambreReserveId)
            {
                Chambre c = getTupleChambre(id);
                listeChambres.remove(c);
            }

            cx.commit();
            return listeChambres;
        }
        catch(Exception e)
        {
            cx.rollback();
            throw e;
        }

         */
}
    public Chambre getTupleChambre(int idChambre) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            Chambre chambre = chambres.getChambre(idChambre);
            if(chambre == null)
                throw new IFT287Exception("La chambre n'existe pas.");

            cx.commit();
            return chambre;
        }
        catch(Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
}
