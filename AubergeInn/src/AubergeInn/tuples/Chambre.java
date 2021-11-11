package AubergeInn.tuples;

import javax.persistence.*;


@Entity
public class Chambre {

    @Id
    @GeneratedValue
    private long c_id;

    private int c_idChambre;
    private String c_type_lit;
    private String c_nom_chambre;
    private float c_prix_base;
    private int c_idCommodite;

    // PAS SUR DU TOUT
    @ManyToOne(fetch = FetchType.LAZY)
    private Reservation idReservation;
    private boolean idChambreNull;

    public Chambre(){

    }
    public Chambre(int idChambre, String nom_chambre, String type_lit, float prix_base, int idCommodite){

        c_idChambre = idChambre;
        c_type_lit = type_lit;
        c_nom_chambre = nom_chambre;
        c_prix_base = prix_base;
        c_idCommodite = idCommodite;

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

    public int getIdCommodite(){ return c_idCommodite;}

    public void setIdCommodite(int idCommodite){ this.c_idCommodite=idCommodite;}

    public boolean isIdChambreNull() {
        return idChambreNull;
    }
}
