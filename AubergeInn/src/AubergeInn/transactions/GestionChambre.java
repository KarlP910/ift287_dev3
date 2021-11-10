package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.*;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleCommodite;
import AubergeInn.tuples.TupleService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GestionChambre {

    private Connexion cx;
    private Chambre chambre;
    private Reservations reserv;
    private Commodite commodite;
    private Service service;

    public GestionChambre(Chambre chambre, Reservations reserv, Commodite commodite, Service service){
        this.chambre=chambre;
        this.reserv=reserv;
        this.cx=chambre.getConnexion();
        this.commodite = commodite;
        this.service=service;
    }
    /**
     * Ajoute une chambre
     */
    public void ajouterChambre(int idChambre,String nom_chambre,String type_lit,float prix_base) //a revoir pour commodite et reserv
            throws SQLException, IFT287Exception, Exception{
        try{
            if(chambre.existe(idChambre))
                throw new IFT287Exception("La chambre existe déjà: "+idChambre);
            chambre.ajouterChambre(idChambre,nom_chambre,type_lit,prix_base);

            cx.commit();
        }
        catch (Exception e){
            cx.rollback();
            throw e;
        }
    }
    /**
     * Supprime une chambre si elle n'a pas de réservation future (ajouter aussi s'Il n'a plus de commodite)
     */
    public void supprimerChambre(int idChambre) //a revoir pour commodite et reserv
            throws SQLException, IFT287Exception, Exception{
        try{
            if(!chambre.existe(idChambre))
                throw new IFT287Exception("La chambre n'existe pas: "+idChambre);
            if(reserv.getReservationChambre(idChambre) != null){
                throw new IFT287Exception("La chambre "+idChambre+" a encore des réservations.");
            }

           int nb= chambre.supprimerChambre(idChambre);
            if (nb==0){
                throw new IFT287Exception("Chambre " + idChambre + " inexistante");
            }
            cx.commit();
        }
        catch (Exception e){
            cx.rollback();
            throw e;
        }
    }
    public TupleCommodite afficherChambreCommodite(int idChambre)
            throws SQLException, IFT287Exception, Exception {
        try {


            cx.commit();
            return commodite.getCommodite(idChambre);



        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
    /**
     * Affiche les chambres avec leurs informations et avec les commodités offertes
     */
    public TupleChambre afficherChambre(int idChambre)
            throws SQLException, IFT287Exception, Exception{
        try{
            if(chambre.existe(idChambre)){

                cx.commit();
                return chambre.afficherChambre(idChambre);
            }
            else{
                throw new IFT287Exception("La chambre que vous voulez affichez n'existe pas "+ idChambre);
            }


        }catch (Exception e){
            cx.rollback();
            throw e;
        }
    }
    public void inclureCommodite(int idChambre, int idCommodite,Commodite commodites) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            TupleChambre chambres = chambre.getChambre(idChambre);
            TupleCommodite comm = commodites.getCommodite(idCommodite);
            if(chambres == null && comm == null)
                throw new IFT287Exception("La chambre et la commodite n'existent pas");
            if(chambres == null)
                throw new IFT287Exception("La chambre n'existe pas");
            if(comm == null)
                throw new IFT287Exception("La commodite n'existe pas");
            TupleService tupleService = service.getService(idChambre, idCommodite);
            if(tupleService != null)
                throw new IFT287Exception("Ce service existe deja.");

            service.inclureCommodite(idChambre, idCommodite);
            cx.commit();
        }
        catch(Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    public void enleverCommodite(int idChambre, int idCommodite) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            TupleService tupleService = service.getService(idChambre, idCommodite);
            if(tupleService == null)
                throw new IFT287Exception("Ce service n'existe pas");
            service.enleverCommodite(idChambre, idCommodite);
            cx.commit();
        }
        catch(Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
    public List<TupleCommodite> getListeCommodite(int idChambre, Commodite tableCommodite) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            ArrayList<Integer> listeCommoditeId = service.getService(idChambre);
            List<TupleCommodite> listeCommodite = tableCommodite.getAll(listeCommoditeId);
            cx.commit();
            return listeCommodite;
        }
        catch(Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
    public double prixServices(int idChambre, GestionCommodite gestionCommodite) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            double total = 0;
            ArrayList<Integer> listeCommoditeId = service.getService(idChambre);
            for(int id : listeCommoditeId)
            {
                total += gestionCommodite.getPrix(id);
            }
            cx.commit();
            return total;
        }
        catch(Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
    public List<TupleChambre> afficherChambresLibres() throws SQLException, IFT287Exception, Exception
    {
        try
        {
            List<TupleChambre> listeChambres = chambre.getAll();
            // code venant de stack overflow https://stackoverflow.com/questions/18257648/get-the-current-date-in-java-sql-date-format
            ArrayList<Integer> listeChambreReserveId = reserv.getAllChambre(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

            for(Integer id : listeChambreReserveId)
            {
                TupleChambre c = getTupleChambre(id);
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
    }
    public TupleChambre getTupleChambre(int idChambre) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            TupleChambre tupleChambre = chambre.getChambre(idChambre);
            if(tupleChambre == null)
                throw new IFT287Exception("La chambre n'existe pas.");

            cx.commit();
            return tupleChambre;
        }
        catch(Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
}
