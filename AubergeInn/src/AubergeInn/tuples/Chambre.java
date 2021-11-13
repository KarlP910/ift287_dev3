package AubergeInn.tuples;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Entity
public class Chambre {

    @Id
    @GeneratedValue
    private int c_idChambre;
    private String c_type_lit;
    private String c_nom_chambre;
    private float c_prix_base;

    @OneToMany
    private List<Commodite> listeCommodites;

    public Chambre(int idChambre, String nom_chambre, String type_lit, float prix_base) {
        c_idChambre = idChambre;
        this.c_type_lit = type_lit;
        this.c_nom_chambre = nom_chambre;
        this.c_prix_base = prix_base;
        listeCommodites= new LinkedList<Commodite>();
    }

    public Chambre() {
        listeCommodites= new LinkedList<Commodite>();
    }

    /***
     * DAPRES MOI ICI ON VA POUVOIR ENLEVER TOUTE LES SETTERS
     * @return
     */

    //Chaque fonction est un get et un set de nos attributs de la classe

    public int getIdChambre(){return c_idChambre;}

    public void setIdChambre(int idChambre){ this.c_idChambre=idChambre;}

    public String getType_lit(){ return this.c_type_lit;}

    public void setType_lit(String type_lit){ this.c_type_lit=type_lit;}

    public String getNom_chambre(){return this.c_nom_chambre;}

    public void setNom_chambre(String nom_chambre){ this.c_nom_chambre=nom_chambre;}

    public float getPrix_base(){ return c_prix_base;}


    public float prixTotal(){

        float total=0;

        for(Commodite c:this.listeCommodites){
            if(listeCommodites==null){
                break;
            }
            total+=c.getSurplus_prix();
        }

        return total+c_prix_base;
    }

    public void setPrix_base(Float prix_base){ this.c_prix_base=prix_base;}

    public void inclureCommodite(Commodite commodite) {
        this.listeCommodites = new LinkedList<Commodite>();
        listeCommodites.add(commodite);

    }
    public void enleverCommodite(Commodite commodite) {

        listeCommodites.remove(commodite);
    }
    public List<Commodite> getAllCommodite(){
        return listeCommodites;
    }


}
