package AubergeInn.tuples;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;


@Entity
public class Chambre {

    @Id
    @GeneratedValue
    private long c_id;

    private int c_idChambre;
    private String c_type_lit;
    private String c_nom_chambre;
    private float c_prix_base;
    private ArrayList<Commodite> listeCommodites;


    // PAS SUR DU TOUT
    @ManyToOne(fetch = FetchType.LAZY)
    private Reservation idReservation;
    private boolean idChambreNull;
    private Date c_datePret;

    public Chambre(int idChambre, String nom_chambre, String type_lit, float prix_base) {

    }

    public Chambre(int idChambre, String nom_chambre, String type_lit, float prix_base, ArrayList<Commodite> listeCommodites){

        c_idChambre = idChambre;
        c_type_lit = type_lit;
        c_nom_chambre = nom_chambre;
        c_prix_base = prix_base;
        listeCommodites= new ArrayList<Commodite>();

    }




    /***
     * DAPRES MOI ICI ON VA POUVOIR ENLEVER TOUTE LES SETTERS
     * @return
     */

    //Chaque fonction est un get et un set de nos attributs de la classe

    public int getIdChambre(){return c_idChambre;}

    public void setIdChambre(int idChambre){ this.c_idChambre=idChambre;}

    public String getType_lit(){ return c_type_lit;}

    public void setType_lit(String type_lit){ this.c_type_lit=type_lit;}

    public String getNom_chambre(){return c_nom_chambre;}

    public void setNom_chambre(String nom_chambre){ this.c_nom_chambre=nom_chambre;}

    public float getPrix_base(){ return c_prix_base;}

    public void setPrix_base(Float prix_base){ this.c_prix_base=prix_base;}



    public boolean isIdChambreNull() {
        return idChambreNull;
    }

    public Reservation getLouer() {

        return idReservation;
    }

    public Date getDateLouer() {
        return c_datePret;
    }

    public void inclureCommodite(Commodite commodite) {
        listeCommodites.add(commodite);
    }
    public void enleverCommodite(Commodite commodite) {
        listeCommodites.remove(commodite);
    }

}
