package AubergeInn.tuples;

import javax.persistence.*;


@Entity
public class Client {


    @Id
    @GeneratedValue
    private int m_idClient;
    private String m_prenom;
    private String m_nom;
    private int m_age;

    //Constructeur de Client
    public Client(int idClient, String prenom, String nom, int age){

        this.m_idClient=idClient;
        this.m_prenom=prenom;
        this.m_nom = nom;
        this.m_age = age;

    }

    public Client() {

    }

    /***
     * Divers fonctions, getters, setters pour les paramètres de Client
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
