package AubergeInn.tuples;

import java.util.Date;

public class TupleReservation {


    private Date date_debut;
    private Date date_fin;
    private int idChambre;
    private int idClient;

    public TupleReservation(){

    }
    public TupleReservation(int idClient,int idChambre, Date date_debut, Date date_fin){


        this.setDate_debut(date_debut);
        this.setDate_fin(date_fin);
        this.setIdChambre(idChambre);
        this.setIdClient(idClient);
    }

    //Chaque fonction est un get et un set de nos attributs de la classe


    public int getIdChambre(){ return idChambre;}

    public void setIdChambre(int idChambre){ this.idChambre=idChambre;}

    public Date getDate_debut(){ return date_debut;}

    public void setDate_debut(Date date_debut){ this.date_debut=date_debut;}

    public Date getDate_fin(){ return date_fin;}

    public void setDate_fin(Date date_fin){ this.date_fin=date_fin;}

    public void setIdClient(int idClient){ this.idClient=idClient;}

    public int getIdClient(){ return idClient;}
}
