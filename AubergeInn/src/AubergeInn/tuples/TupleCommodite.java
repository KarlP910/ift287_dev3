package AubergeInn.tuples;

public class TupleCommodite {

    private int idCommodite;
    private String description;
    private Float surplus_prix;

    public TupleCommodite(){

    }
    public TupleCommodite(int idCommodite, String description,Float surplus_prix){
        this.setIdCommodite(idCommodite);
        this.setDescription(description);
        this.setSurplus_prix(surplus_prix);

    }

    //Chaque fonction est un get et un set de nos attributs de la classe

    public int getIdCommodite(){ return idCommodite;}

    public void setIdCommodite(int idCommodite){ this.idCommodite=idCommodite;}

    public String getDescription(){ return description;}

    public void setDescription(String description){ this.description=description;}

    public Float getSurplus_prix(){ return surplus_prix;}

    public void setSurplus_prix(Float surplus_prix){ this.surplus_prix=surplus_prix;}
}
