package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.*;
import AubergeInn.tuples.Chambre;
import AubergeInn.tuples.Commodite;

import java.sql.SQLException;
import java.util.*;

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

            this.cx.demarreTransaction();

            Chambre chambre = new Chambre(idChambre, nom_chambre, type_lit, prix_base);

            if (this.chambres.existe(idChambre))
                throw new IFT287Exception("La chambre existe déjà: " + idChambre);
            //chambre.ajouterChambre(idChambre,nom_chambre,type_lit,prix_base);

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

            this.cx.demarreTransaction();

            Chambre chambre = chambres.getChambre(idChambre);
            if (chambre == null)
                throw new IFT287Exception("La chambre n'existe pas 1: " + idChambre);
            if (!this.chambres.existe(idChambre))
                throw new IFT287Exception("La chambre n'existe pas 2: " + idChambre);
            if (this.reserv.getReservationChambre(chambre) != null) {
                throw new IFT287Exception("La chambre " + idChambre + " a encore des réservations.");
            }

            if (!this.chambres.supprimerChambre(chambre))
                throw new IFT287Exception("Chambre" + idChambre + "inexistant");

            chambres.supprimerChambre(chambre);
           cx.commit();

        } catch (Exception e) {
            this.cx.rollback();
            throw e;
        }
    }

    /**
     * À faire
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
     * Affiche les chambres avec leurs informations et avec les commodités offertes
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

    public List<Chambre> afficherChambresLibres() throws SQLException, IFT287Exception, Exception {

        try {


        cx.demarreTransaction();

        List<Chambre> listeChambre = chambres.afficherChambreLibre();
        List<Chambre> listBonneChambre=new LinkedList<Chambre>();
        Date calendar = Calendar.getInstance().getTime();
        for (Chambre ci : listeChambre) {
            //if (ci.getLouer() == null) {
         //   if(!reserv.getReservationChambre(ci).getDate_debut().after(calendar) && !reserv.getReservationChambre(ci).getDate_fin().before(calendar)){
         //       listBonneChambre.add(ci);
        //    }
            // if(reserv.getReservationChambre(ci).getDate_debut()!=null || reserv.getReservationChambre(ci).getDate_fin()!=null){
             if(reserv.getReservationChambre(ci)==null)
                listBonneChambre.add(ci);
         //   }
        }


        //  }
        cx.commit();
        return listBonneChambre;
    }catch (Exception e){
            cx.rollback();
            throw e;
        }
    }

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


   /* public float prixServices(int idChambre, GestionCommodite gCommodites) throws SQLException, IFT287Exception, Exception {
        try {
            cx.demarreTransaction();
            float total = 0;
            Chambre c =chambres.getChambre(idChambre);
         //   ArrayList<Integer> listeCommoditeId = service.getService(idChambre);
            List<Commodite> listeCommoditeId = c.
         /*
            if(listeCommoditeId.isEmpty()){
           return 0;

            }
            for (int i=0;i< listeCommoditeId.size();i++) {

                total += gCommodites.getPrix(i);
            }


            cx.commit();
            return total;
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
        */
   // }





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

    public Chambre getEntiteChambre(int idChambre) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            cx.demarreTransaction();
            Chambre chambre = chambres.getChambre(idChambre);
           // if(chambre == null)
          //      throw new IFT287Exception("La chambre n'existe pas.");

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
