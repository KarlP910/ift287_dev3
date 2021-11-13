package AubergeInn.tuples;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Date m_date_debut;
    private Date m_date_fin;

    @OneToOne
    private Chambre m_chambres;
    @OneToOne
    private Client m_clients;

    public Reservation(){

    }
    //Constructeur de Reservation
    public Reservation(Client clients, Chambre chambres, Date date_debut, Date date_fin){
        m_clients = clients;
        m_chambres = chambres;
        m_date_debut=date_debut;
        m_date_fin=date_fin;
    }


    //Divers fonctions, getters, setters pour les paramètres de Client

    public Chambre getChambre(){ return m_chambres;}

    public Date getDate_debut(){ return m_date_debut;}

    public Date getDate_fin(){ return m_date_fin;}

    public Client getClient(){ return m_clients;}
}
