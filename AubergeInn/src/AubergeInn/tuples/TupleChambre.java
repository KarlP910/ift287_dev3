package AubergeInn.tuples;

public class TupleChambre {

    private int idChambre;
    private String type_lit;
    private String nom_chambre;
    private float prix_base;
    private int idCommodite;
    private int idReservation;
    private boolean idChambreNull;

    public TupleChambre(){

    }
    public TupleChambre(int idChambre,String nom_chambre,String type_lit,float prix_base,int idCommodite){

        this.setIdChambre(idChambre);
        this.setType_lit(type_lit);
        this.setNom_chambre(nom_chambre);
        this.setPrix_base(prix_base);
        this.setIdCommodite(idCommodite);

    }

    //Chaque fonction est un get et un set de nos attributs de la classe

    public int getIdChambre(){return idChambre;}

    public void setIdChambre(int idChambre){ this.idChambre=idChambre;}

    public String getType_lit(){ return type_lit;}

    public void setType_lit(String type_lit){ this.type_lit=type_lit;}

    public String getNom_chambre(){return nom_chambre;}

    public void setNom_chambre(String nom_chambre){ this.nom_chambre=nom_chambre;}

    public float getPrix_base(){ return prix_base;}

    public void setPrix_base(Float prix_base){ this.prix_base=prix_base;}

    public int getIdCommodite(){ return idCommodite;}

    public void setIdCommodite(int idCommodite){ this.idCommodite=idCommodite;}

    public boolean isIdChambreNull() {
        return idChambreNull;
    }
}
