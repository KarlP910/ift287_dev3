package AubergeInn.tuples;

import AubergeInn.tables.Chambres;
import AubergeInn.tables.Clients;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Date m_date_debut;
    private Date m_date_fin;

    private Chambre m_chambres;
    private Client m_clients;

    public Reservation(){

    }
    public Reservation(Client clients, Chambre chambres, Date date_debut, Date date_fin){


        m_clients = clients;
        m_chambres = chambres;
        m_date_debut=date_debut;
        m_date_fin=date_fin;
    }

    //Chaque fonction est un get et un set de nos attributs de la classe


    public Chambre getChambre(){ return m_chambres;}

    public Date getDate_debut(){ return m_date_debut;}

    public Date getDate_fin(){ return m_date_fin;}

    public Client getClient(){ return m_clients;}
}
