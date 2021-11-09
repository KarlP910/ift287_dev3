package AubergeInn.tuples;

import java.security.PublicKey;

public class TupleClient {



    private int idClient;
    private String prenom;
    private String nom;
    private int age;


    public TupleClient(){

    }
    public TupleClient(int idClient,String prenom,String nom,int age){

        this.setIdClient(idClient);
        this.setPrenom(prenom);
        this.setNom(nom);
        this.setAge(age);

    }
    //Chaque fonction est un get et un set de nos attributs de la classe

    public int getIdClient(){ return idClient;}

    public void setIdClient(int idClient){ this.idClient=idClient;}

    public String getPrenom(){ return prenom;}

    public void setPrenom(String prenom){ this.prenom=prenom;}

    public String getNom(){ return nom;}

    public void setNom(String nom){  this.nom=nom;}

    public int getAge(){ return age;}

    public void setAge(int age){ this.age=age;}

}
