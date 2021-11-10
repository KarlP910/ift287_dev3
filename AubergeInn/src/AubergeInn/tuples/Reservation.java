package AubergeInn.tuples;

import AubergeInn.tables.Chambre;
import AubergeInn.tables.Client;

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

    private Chambre m_chambre;
    private Client m_client;

    public Reservation(){

    }
    public Reservation(Client client, Chambre chambre, Date date_debut, Date date_fin){


        m_client=client;
        m_chambre=chambre;
        m_date_debut=date_debut;
        m_date_fin=date_fin;
    }

    //Chaque fonction est un get et un set de nos attributs de la classe


    public Chambre getChambre(){ return m_chambre;}

    public Date getDate_debut(){ return m_date_debut;}

    public Date getDate_fin(){ return m_date_fin;}

    public Client getClient(){ return m_client;}
}
