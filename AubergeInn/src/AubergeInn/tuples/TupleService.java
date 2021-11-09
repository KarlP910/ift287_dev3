package AubergeInn.tuples;


public class TupleService {

    private int commodite;
    private int chambre;

    //Construcor
    public TupleService(int commodite, int chambre) {
        this.commodite = commodite;
        this.chambre = chambre;
    }

    //Getters & setters
    public int getCommodite() {
        return commodite;
    }

    public void setCommodite(int commodite) {
        this.commodite = commodite;
    }

    public int getChambre() {
        return chambre;
    }

    public void setChambre(int chambre) {
        this.chambre = chambre;
    }
}