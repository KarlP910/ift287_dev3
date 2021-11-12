package AubergeInn.tuples;

import javax.persistence.*;

import java.security.PublicKey;

@Entity
public class Client {


    @Id
    @GeneratedValue
    private long m_id;

    private int m_idClient;
    private String m_prenom;
    private String m_nom;
    private int m_age;

    // Manque un One to many avec chambre ?



    public Client(int idClient, String prenom, String nom, int age){

        this.m_idClient=idClient;
        this.m_prenom=prenom;
        this.m_nom = nom;
        this.m_age = age;

    }

    public Client() {

    }

    /***
     * PAS MAL SUR QUON VA POUVOIR ENLEVER LES SETTER ICI AUSSI
     * @return
     */

    //Chaque fonction est un get et un set de nos attributs de la classe

    public int getIdClient(){ return m_idClient;}

    public void setIdClient(int idClient){ this.m_idClient=idClient;}

    public String getPrenom(){ return m_prenom;}

    public void setPrenom(String prenom){ this.m_prenom=prenom;}

    public String getNom(){ return m_nom;}

    public void setNom(String nom){  this.m_nom=nom;}

    public int getAge(){ return m_age;}

    public void setAge(int age){ this.m_age=age;}

}
