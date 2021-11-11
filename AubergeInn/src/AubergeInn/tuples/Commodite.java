package AubergeInn.tuples;

import javax.persistence.*;

@Entity
public class Commodite {

    @Id
    @GeneratedValue
    private int m_idCommodite;
    private String m_description;
    private Float m_surplus_prix;


    //Peut etre une relation one to many

    public Commodite(){

    }

    public Commodite(int idCommodite, String description, Float surplus_prix){
        m_idCommodite = idCommodite;
        m_description = description;
        m_surplus_prix = surplus_prix;

    }

    /***
     * SUREMENT ENLVER LES SETTERS
     * @return
     */

    //Chaque fonction est un get et un set de nos attributs de la classe

    public int getIdCommodite(){ return m_idCommodite;}

    public void setIdCommodite(int idCommodite){ this.m_idCommodite=idCommodite;}

    public String getDescription(){ return m_description;}

    public void setDescription(String description){ this.m_description=description;}

    public Float getSurplus_prix(){ return m_surplus_prix;}

    public void setSurplus_prix(Float surplus_prix){ this.m_surplus_prix=surplus_prix;}
}
