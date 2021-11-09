package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleReservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Reservation {

    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtExisteChambre;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtDelete;
    private final PreparedStatement stmtExisteClient;
    private final PreparedStatement stmtInsertReservation;
    private final PreparedStatement stmtGetAllChambre;
    private final PreparedStatement stmtExisteReservation;

    private final Connexion cx;

        public Reservation(Connexion cx) throws SQLException
        {
            this.cx=cx;

            stmtExiste =cx.getConnection().prepareStatement(
                    "select date_debut, date_fin, idChambre, idClient from reservation where idclient = ? AND idchambre=? AND date_debut=? AND date_fin=?");
            stmtInsert =cx.getConnection().prepareStatement(
                    "insert into reservation(idClient,idchambre, date_debut,date_fin) "+ " values(?,?,?,?)");
            stmtInsertReservation = cx.getConnection().prepareStatement(
                    "insert into reservation(idClient,idChambre , date_debut, date_fin) "+ " values(?,?,?,?)");
            stmtDelete=cx.getConnection().prepareStatement(
                    "delete from reservation where idClient = ?");
            stmtExisteChambre =cx.getConnection().prepareStatement(
                    "select idClient,idChambre, date_debut, date_fin from reservation where idChambre = ?");
            stmtExisteClient =cx.getConnection().prepareStatement(
                    "select idClient, idChambre, date_debut, date_fin from reservation where idClient = ?");

            stmtGetAllChambre  = cx.getConnection().prepareStatement(
                    "select all idchambre from Reservation where date_debut <= ? and date_fin > ?");

            stmtExisteReservation = cx.getConnection().prepareStatement(
                    "select idClient, idChambre, date_debut, date_fin from Reservation where idchambre = ? and date_debut <= ? and date_fin > ?");

        }
    public Connexion getConnexion(){
        return cx;
    }


    public boolean existe(int idClient,int idChambre) throws SQLException
    {

        stmtExiste.setInt(1, idClient);
        stmtExiste.setInt(2, idChambre);
        ResultSet rset = stmtExiste.executeQuery();
        boolean reservationExiste = rset.next();
        rset.close();
        return reservationExiste;
    }

    public void reserver(int idClient, int idChambre, Date dateDebut, Date dateFin) throws SQLException
    {
        /* Ajout de la reservation. */
        stmtInsertReservation.setInt(1, idClient);
        stmtInsertReservation.setInt(2, idChambre);
        stmtInsertReservation.setDate(3, dateDebut);
        stmtInsertReservation.setDate(4, dateFin);
        stmtInsertReservation.executeUpdate();
    }
    public TupleReservation getReservation(int idChambre, Date debut, Date fin)throws SQLException
    {

        stmtExisteReservation.setInt(1, idChambre);
        stmtExisteReservation.setDate(2, debut);
        stmtExisteReservation.setDate(3, fin);
        ResultSet rset = stmtExisteReservation.executeQuery();
        if (rset.next())
        {
            TupleReservation tupleReservation = new TupleReservation(rset.getInt(1), idChambre, debut, fin);
            rset.close();
            return tupleReservation;
        }
        else
        {
            return null;
        }
    }
    /**
     * Lecture de la réservation d'une chambre
     */
    public TupleReservation getReservationChambre(int idChambre) throws SQLException
    {
        stmtExisteChambre.setInt(1, idChambre);
        ResultSet rset = stmtExisteChambre.executeQuery();
        if (rset.next())
        {
            TupleReservation tupleReservation = new TupleReservation();
            tupleReservation.setIdClient(rset.getInt(1));
            tupleReservation.setIdChambre(rset.getInt(2));
            tupleReservation.setDate_debut(rset.getDate(3));
            tupleReservation.setDate_fin(rset.getDate(4));
            return tupleReservation;
        }
        else
        {
            return null;
        }
    }
    /**
     * Lecture de la réservation d'un client
     */
    public TupleReservation getReservationClient(int idClient) throws SQLException
    {
        stmtExisteClient.setInt(1, idClient);
        ResultSet rset = stmtExisteClient.executeQuery();
        if (rset.next())
        {
            TupleReservation tupleReservation = new TupleReservation();
            tupleReservation.setIdClient(rset.getInt(1));
            tupleReservation.setIdChambre(rset.getInt(2));
            tupleReservation.setDate_debut(rset.getDate(3));
            tupleReservation.setDate_fin(rset.getDate(4));
            return tupleReservation;
        }
        else
        {
            return null;
        }
    }
    /**
     * Lecture d'une liste de reservation de client
     */
    public List<TupleReservation> listeReservationClient(int idClient) throws SQLException
    {
        stmtExisteClient.setInt(1, idClient);
        ResultSet rset = stmtExisteClient.executeQuery();
        List<TupleReservation> listeReserv=new LinkedList<TupleReservation>();
        //idClient,idChambre, date_debut, date_fin
        while (rset.next())
        {
            TupleReservation tupleReservation = new TupleReservation(
                    rset.getInt("idClient"),
                    rset.getInt("idChambre"),
                    rset.getDate("date_debut"),
                    rset.getDate("date_fin"));
           listeReserv.add(tupleReservation);
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
}


