package AubergeInn.tuples;

import javax.persistence.*;
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

    //Constructeur de Chambre
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
     *  Divers fonctions, getters, setters pour les paramètres de chambre et inclureCommodite | enleverCommodite | getAllCommodite
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

    //Prix d'une chambre de base et le prix de leur commodité
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

    //Inclure une commodité à une chambre
    public void inclureCommodite(Commodite commodite) {
        this.listeCommodites = new LinkedList<Commodite>();
        listeCommodites.add(commodite);

    }
    //Enlever une commodité à une chambre
    public void enleverCommodite(Commodite commodite) {

        listeCommodites.remove(commodite);
    }
    //Permet d'obtenir toutes les commodités d'une chambre
    public List<Commodite> getAllCommodite(){
        return listeCommodites;
    }
}
