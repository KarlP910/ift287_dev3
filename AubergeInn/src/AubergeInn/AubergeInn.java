// Travail fait par :
//   NomEquipier1 - Matricule
//   NomEquipier2 - Matricule

package AubergeInn;

import AubergeInn.tuples.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.StringTokenizer;
import java.sql.*;

/**
 * Fichier de base pour le TP2 du cours IFT287
 *
 * Karl Plourde et Kevin Bissonnette
 *  * Universite de Sherbrooke
 *  * Version 2.1 - 15 octobre 2021
 *  * IFT287 - Exploitation de BD relationnelles et OO
 * <pre>
 * 
 * Vincent Ducharme
 * Universite de Sherbrooke
 * Version 1.0 - 7 juillet 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Ce programme permet d'appeler des transactions d'un systeme
 * de gestion utilisant une base de donnees.
 *
 * Paramètres du programme
 * 0- site du serveur SQL ("local" ou "dinf")
 * 1- nom de la BD
 * 2- user id pour etablir une connexion avec le serveur SQL
 * 3- mot de passe pour le user id
 * 4- fichier de transaction [optionnel]
 *           si non spécifié, les transactions sont lues au
 *           clavier (System.in)
 *
 * Pré-condition
 *   - La base de donnees doit exister
 *
 * Post-condition
 *   - Le programme effectue les mises à jour associees à chaque
 *     transaction
 * </pre>
 */
public class AubergeInn
{

    private static GestionSystem gestionSystem;
    private static Connexion cx;
    private static boolean echo;
    private static SimpleDateFormat formatAMJ;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        if (args.length < 4)
        {
            System.out.println("Usage: java AubergeInn.AubergeInn <serveur> <bd> <user> <password> [<fichier-transactions>]");
            return;
        }
        
        AubergeInn aubergeInnInstance = null;
        
        try {
            boolean lectureAuClavier = true;
            InputStream sourceTransaction = System.in;
            // Il est possible que vous ayez à déplacer la connexion ailleurs.
            // N'hésitez pas à le faire!
            if (args.length > 4) {
                sourceTransaction = new FileInputStream(args[4]);
                lectureAuClavier = false;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(sourceTransaction));

            aubergeInnInstance = new AubergeInn(args[0], args[1], args[2], args[3]);
            aubergeInnInstance.setFaireEcho(!lectureAuClavier);
            aubergeInnInstance.traiterTransactions(reader);


        }
        catch (Exception e)
            {
                e.printStackTrace(System.out);
            }


        }

    public void setFaireEcho(boolean echo)
    {
        this.echo = echo;
    }

    public AubergeInn(String serveur, String bd, String user, String pass) throws Exception
    {
        gestionSystem = new GestionSystem(serveur, bd, user, pass);
    }

    public void traiterTransactions(BufferedReader reader) throws Exception
    {

        String transaction = lireTransaction(reader);
        while (!finTransaction(transaction))
        {
            // Découpage de la transaction en mots
           // StringTokenizer tokenizer = new StringTokenizer(transaction, " ");
         //   if (tokenizer.hasMoreTokens())
            try {

                executerTransaction(transaction);

            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            System.out.println();
            transaction = lireTransaction(reader);





        }
    }


    /**
     * Decodage et traitement d'une transaction
     */
    private void executerTransaction(String transaction) throws Exception, IFT287Exception
    {
        try {
            System.out.print(transaction);
            // Decoupage de la transaction en mots
            StringTokenizer tokenizer = new StringTokenizer(transaction, " ");

            if (tokenizer.hasMoreTokens()) {
                String command = tokenizer.nextToken();
                // Vous devez remplacer la chaine "commande1" et "commande2" par
                // les commandes de votre programme. Vous pouvez ajouter autant
                // de else if que necessaire. Vous n'avez pas a traiter la
                // commande "quitter".

                // *********************
                // Ajoute un client
                // *********************
                if (command.equals("ajouterClient")) {
                    // Lecture des parametres
                    int idClient = this.readInt(tokenizer);
                    String prenom = this.readString(tokenizer);
                    String nom = this.readString(tokenizer);
                    int age = this.readInt(tokenizer);
                    this.gestionSystem.getGestionClient().ajoutClient(idClient,prenom,nom,age);

                }
                // *********************
                // Supprime un client
                // *********************
                else if (command.equals("supprimerClient")) {
                    int idClient = this.readInt(tokenizer);
                    this.gestionSystem.getGestionClient().supprimerClient(idClient);

                }
                // *********************
                // Ajoute une chambre
                // *********************
                else if (command.equals("ajouterChambre")) {
                    int idChambre = this.readInt(tokenizer);
                    String nomChambre = this.readString(tokenizer);
                    String typeLit = this.readString(tokenizer);
                    float prix = this.readfloat(tokenizer);
                    this.gestionSystem.getGestionChambre().ajouterChambre(idChambre,nomChambre,typeLit,prix);
                   // gestionSystem.getGestionChambre().ajouterChambre(idChambre);

                }
                // *********************
                // Supprime une chambre
                // *********************


                else if (command.equals("supprimerChambre")) {
                    int idChambre = this.readInt(tokenizer);
                    this.gestionSystem.getGestionChambre().supprimerChambre(idChambre);

                }
                // *********************
                // Ajoute une commodite
                // *********************
                else if (command.equals("ajouterCommodite")) {
                    int idCommodite = this.readInt(tokenizer);
                    String description = this.readString(tokenizer);
                    float surplus_prix = this.readfloat(tokenizer);
                    this.gestionSystem.getGestionCommodite().ajouterCommodite(idCommodite,description,surplus_prix);

                }
                // *********************
                // Inclus un commodité
                // *********************
                else if (command.equals("inclureCommodite")) {
                    int idChambre = readInt(tokenizer);
                    int idCommodite = readInt(tokenizer);
                    gestionSystem.getGestionChambre().inclureCommodite(idChambre,idCommodite);


                }
                // *********************
                // Enlève une commodité
                // *********************
                else if (command.equals("enleverCommodite")) {
                    int idChambre = readInt(tokenizer);
                    int idCommodite = readInt(tokenizer);
                    gestionSystem.getGestionChambre().enleverCommodite(idChambre,idCommodite);

                }
                // *********************
                // Affiche les chambres libres
                // *********************
                else if (command.equals("afficherChambresLibres")) {
                    //TODO




                   List<Chambre>listChambre= gestionSystem.getGestionChambre().afficherChambresLibres();
                    System.out.println("\nidChambre\tnom\ttype de lit\tprix total");
                   for(Chambre c:listChambre){
                       System.out.println(c.getIdChambre()+"\t"+c.getNom_chambre()+"\t"+c.getType_lit()+"\t"+c.getPrix_base());
                   }

                //    gestionSystem.getGestionReservation().afficherChambreVide();

                    /*

                    List<Chambre> listChambreLibres= gestionSystem.getGestionChambre().afficherChambresLibres();

                    System.out.println("\nidChambre\tnom\ttype de lit\tprix total");
                    for(Chambre chambre : listChambreLibres)
                    {
                        double prixSupplementaire = gestionSystem.getGestionChambre().prixServices(chambre.getIdChambre(), gestionSystem.getGestionCommodite());
                        double total =prixSupplementaire+chambre.getPrix_base();

                        System.out.println(chambre.getIdChambre() + "\t" + chambre.getNom_chambre() + "\t" + chambre.getType_lit() + "\t" + total);
                    }

                     */


                }

                // *********************
                // AFFICHER LES INFORMATIONS D'UN CLIENT AINSI QUE SES RÉSERVATIONS
                // *********************
                else if (command.equals("afficherClient")) {

                    int idClient = readInt(tokenizer);
                    Client client = gestionSystem.getGestionClient().getClient(idClient);
                   // List<Reservation> listreReserv = gestionSystem.getGestionReservation().
                    //il faut aujouter ;les autres attributs de clients
                    System.out.println("\nidClient\tPrenom\tNom\tAge");
                    System.out.println(client.getIdClient()+"\t"+client.getPrenom()+"\t"+client.getNom()+"\t"+client.getAge());
                    List<Reservation> listreserv=gestionSystem.getGestionReservation().listReservationClient(client);

                    System.out.println("\nidClient\tprenom\tnom\tage\tnomChambre\tPrix\tdate début\tdate départ");
                    if(listreserv.isEmpty()){
                        // System.out.println("idClient,Prenom, Nom, Age");
                        System.out.println(client.getIdClient()+ " "+
                                client.getPrenom()+ " "+
                                client.getNom()+ " "+
                                client.getAge()+ " ");
                    }
                    else {
                        for (Reservation t : listreserv) {
                            System.out.println(client.getIdClient() + "\t " +
                                    client.getPrenom() + "\t " +
                                    client.getNom() + " \t" +
                                    client.getAge() + " \t" +
                                    t.getChambre().getNom_chambre() + " \t" +
                                    t.getChambre().getPrix_base()+" \t"+
                                    t.getDate_debut() + " \t" +
                                    t.getDate_fin());
                        }
                        //Faut accéder a la lsite de commodité et print ceux qui sont reliés avec la chambre



                    }//TODO
                }
                // *********************
                // AFFICHER LES INFORMATIONS D'UNE CHAMBRE ET DE SES COMMODITÉS
                // *********************
                else if (command.equals("afficherChambre")) {
                    //TODO

                    int idChambre = readInt(tokenizer);

                    Chambre b=gestionSystem.getGestionChambre().afficherChambre(idChambre);
                    System.out.println("\nNuméro de chambre\tNom\tType de lit\tPrix\t\n"+b.getIdChambre()+"\t"+b.getNom_chambre()+"\t"+b.getType_lit()+"\t"+b.getPrix_base());
                    List<Commodite> listeCommodite= gestionSystem.getGestionChambre().afficherChambreCommodite(idChambre);
                    if(!listeCommodite.isEmpty()) {
                        System.out.println("\nidCommodite\tdescription\tprix");
                        for (Commodite commo : listeCommodite) {
                            System.out.println(commo.getIdCommodite() + "\t" + commo.getDescription() + "\t" + commo.getSurplus_prix() + "\t");
                        }
                   }

                }
                // *********************
                // RÉSERVE UNE CHAMBRE PAR UN CLIENT
                // *********************
                else if (command.equals("reserver")) {
                    int idClient = readInt(tokenizer);
                    int idChambre = readInt(tokenizer);
                    Date dateDebut = readDate(tokenizer);
                    Date dateFin = readDate(tokenizer);
                    gestionSystem.getGestionReservation().reserver(idClient,idChambre,dateDebut,dateFin);

                }
                // *********************
                // QUITTE LE PROGRAMME
                // *********************
                else if (command.equals("quitter")) {
                    System.exit(0);

                }
                // *********************
                // NE RECONNAÎT PAS UNE COMMANDE
                // *********************
                else {
                    System.out.println(" : Transaction non reconnue");
                }
                }

        }

        catch (IFT287Exception e)
        {
            System.out.println(" " + e.toString());
            // Ce rollback est ici seulement pour vous aider et éviter des problèmes lors de la correction
            // automatique. En théorie, il ne sert à rien et ne devrait pas apparaître ici dans un programme
            // fini et fonctionnel sans bogues.
           // cx.rollback();
        }


    }
    // *********************
    // LIS UNE DATE ET LA CONVERTIT EN STRING
    // *********************
    //Code pris de l'exemple bilbio-jdbc-master
    static String lectureDate(StringTokenizer tokenizer) throws Exception {
        if (tokenizer.hasMoreElements())
        {
            String token = tokenizer.nextToken();
            try
            {
                convertirDate(token);
                return token;
            }
            catch (ParseException e)
            {
                throw new IFT287Exception("Date en format YYYY-MM-DD attendue à la place de \"" + token + "\"");
            }
        }
        else
        {
            throw new IFT287Exception("Autre paramètre attendu");
        }

    }

    //Code pris de l'exemple bilbio-jdbc-master
    public static java.util.Date convertirDate(String dateString) throws ParseException
    {
        formatAMJ = new SimpleDateFormat("yyyy-MM-dd");
        formatAMJ.setLenient(false);
        return formatAMJ.parse(dateString);
    }
    static float readfloat(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
        {
            String token = tokenizer.nextToken();
            try
            {
                return Float.valueOf(token).floatValue();
            }
            catch (NumberFormatException e)
            {
                throw new Exception("Nombre attendu a la place de \"" + token + "\"");
            }
        }
        else
            throw new Exception("Autre parametre attendu");
    }
    
    // ****************************************************************
    // *   Les methodes suivantes n'ont pas besoin d'etre modifiees   *
    // ****************************************************************

    /**
     * Ouvre le fichier de transaction, ou lit à partir de System.in
     */
    public static BufferedReader ouvrirFichier(String[] args) throws FileNotFoundException
    {
        if (args.length < 5)
            // Lecture au clavier
            return new BufferedReader(new InputStreamReader(System.in));
        else
            // Lecture dans le fichier passe en parametre
            return new BufferedReader(new InputStreamReader(new FileInputStream(args[4])));
    }

    /**
     * Lecture d'une transaction
     */
    static String lireTransaction(BufferedReader reader) throws IOException
    {
        System.out.print("> ");
        String transaction = reader.readLine();
        // Echo si lecture dans un fichier
        if (echo)
            System.out.println(transaction);
        return transaction;
    }

    /**
     * Verifie si la fin du traitement des transactions est atteinte.
     */
    static boolean finTransaction(String transaction)
    {
        // fin de fichier atteinte
        return (transaction == null || transaction.equals("quitter"));
    }

    /** Lecture d'une chaine de caracteres de la transaction entree a l'ecran */
    static String readString(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
            return tokenizer.nextToken();
        else
            throw new Exception("Autre parametre attendu");
    }

    /**
     * Lecture d'un int java de la transaction entree a l'ecran
     */
    static int readInt(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
        {
            String token = tokenizer.nextToken();
            try
            {
                return Integer.valueOf(token).intValue();
            }
            catch (NumberFormatException e)
            {
                throw new Exception("Nombre attendu a la place de \"" + token + "\"");
            }
        }
        else
            throw new Exception("Autre parametre attendu");
    }

    static Date readDate(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
        {
            String token = tokenizer.nextToken();
            try
            {
                return Date.valueOf(token);
            }
            catch (IllegalArgumentException e)
            {
                throw new Exception("Date dans un format invalide - \"" + token + "\"");
            }
        }
        else
            throw new Exception("Autre parametre attendu");
    }

}
