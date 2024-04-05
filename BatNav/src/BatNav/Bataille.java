/*
Développeur : Mathis BÉAL
Programme : Classe servant à mettre en place une bataille navale
Dernière modification : 25/02/2024
 */

package BatNav;

import java.util.Random;
import java.util.Scanner;

/**
 * Classe principale mettant en place le jeu de Bataille Navale
 * @author Mathis BÉAL
 * @version 1.0
 * @see <a href="https://fr.wikipedia.org/wiki/Bataille_navale_(jeu)">Bataille navale</a>
 */
public class Bataille {

    // Couleur pour modifier l'affichage
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_NOIR = "\u001B[30m";
    private static final String ANSI_ROUGE = "\u001B[31m";
    private static final String ANSI_VERT = "\u001B[32m";
    private static final String ANSI_JAUNE = "\u001B[33m";
    private static final String ANSI_BLEU = "\u001B[34m";
    private static final String ANSI_VIOLET = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_BLANC = "\u001B[37m";

    // Map pour afficher en différentes couleures la grille suivant si c'est une case d'eau, de bateau, ou une case touchée
    private static final String[] SetCouleur = {ANSI_BLEU, ANSI_VERT, ANSI_VERT, ANSI_VERT, ANSI_VERT, ANSI_VERT, ANSI_ROUGE};

    // Taille des grilles
    private static int m_nTailleGrille = 10;
    private static String[] nomsBateaux = {"Porte-avion", "Croiseur", "Contre-torpilleur", "Sous-marin", "Torpilleur"};

    private static Random rand = new Random();


     // Contient la grille de l'ordinateur adverse

    private static int [][] grilleOrdi = new int [m_nTailleGrille][m_nTailleGrille];

     // Contient la grille du joueur
    private static int [][] grilleJoueur = new int [m_nTailleGrille][m_nTailleGrille];

    static Scanner m_sScanner = new Scanner(System.in);

    /**
     * Génère un nombre aléatoire
     * @param a Borne inférieure (comprise)
     * @param b Borne supérieure (non comprise)
     * @return L'entier généré aléatoirement
     */
    private static int randint(int a, int b){
        return rand.nextInt(b-a)+a;
    }


    /**
     * Fonction servant à savoir si il est possible de placer un bateau selon des coordonnées données
     *
     * @param grille Grille dans laquelle vérifier le placement
     * @param ligne Ligne de départ du placement du bateau
     * @param colonne Colonne de départ de placement du bateau
     * @param direction Direction dans laquelle placer le bateau (1 pour horizontal, 2 pour vertical)
     * @param t Taille du bateau en nombre de cases qu'il va occuper
     * @return True si il est possible de placer le bateau, False sinon
     */
    private static boolean positionValide(int[][] grille, int ligne, int colonne, int direction, int t){

        // Vérifie si le bateau ne dépasse pas de la grille
        if (direction==1){
            if (colonne + t > grille[0].length) {
                return false;
            }
        }
        else {
            if (ligne + t > grille.length){
                return false;
            }
        }

        // Vérifie si le bateau ne sera pas placé sur un autre
        if (direction==1){
            for (int i = 0; i < t; i++) {
                if (grille[ligne][colonne+i] != 0) {
                    return false;
                }
            }
        }
        else {
            for (int i = 0; i < t; i++) {
                if (grille[ligne+i][colonne] != 0) {
                    return false;
                }
            }
        }

        return true;
    }


    /**
     * Place un bateau sur la grille de l'ordi, suivant la taille donnée
     * @param taille int, Taille du bateau
     * @param type int, Type du bateau //voir sujet / future doc
     */
    private static void placerBatOrdi(int taille, int type){

        int col;
        int lig;
        int or;

        do {
            col=randint(0, m_nTailleGrille);
            lig=randint(0, m_nTailleGrille);
            or=randint(1,3);
//            System.out.println("Picked : col="+col+"; lig="+lig+"; or="+or+";");
        }
        while (!positionValide(grilleOrdi, lig, col, or, taille));
        //System.out.println("Size="+taille+";");

        if (or==1){
//            System.out.println("Size="+taille+";");
            for (int i = 0; i < taille; i++) {
                grilleOrdi[lig][col+i] = type;
//                System.out.println("Put : col="+i+"; lig="+lig+"; type="+type+";");
            }
        }
        else {
//            System.out.println("Size="+taille+";");
            for (int i = 0; i < taille; i++) {
                grilleOrdi[lig+i][col] = type;
//                System.out.println("Put : col="+col+"; lig="+i+"; type="+type+";");
            }
        }

    //return;
    }


    /**
     * Initialise la grille de l'ordi avec les 5 bateaux
     */
    private static void initGrilleOrdi(){
        /*
        1 - Porte-avions (5 cases)
        2 - Croiseur (4 cases)
        3 - Contre-torpilleur (3 cases)
        4 - Sous-marin (3 cases)
        5 - Torpilleur (2 cases)
         */
//        System.out.println("placerBatOrdi(5, 1);");
        placerBatOrdi(5, 1);
//        System.out.println("placerBatOrdi(4, 2);");
        placerBatOrdi(4, 2);
//        System.out.println("placerBatOrdi(3, 3);");
        placerBatOrdi(3, 3);
//        System.out.println("placerBatOrdi(3, 4);");
        placerBatOrdi(3, 4);
//        System.out.println("placerBatOrdi(2, 5);");
        placerBatOrdi(2, 5);
    }

    /**
     * Affiche la grille passée en paramètre
     * @param grille Grille (array de type int[][])
     */
    private static void afficherGrille(int[][] grille) {
        System.out.println();
        char[] nom_cols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        System.out.print("\t");
        for(int i = 0; i<grille.length; i++){
            System.out.print(nom_cols[i]+" ");
        }
        System.out.println();

        for (int i = 0; i < grille.length; i++) {
            System.out.print(i+1+"\t");
            for (int j = 0; j < grille[0].length; j++) {
                System.out.print(SetCouleur[grille[i][j]] + grille[i][j]+ ANSI_RESET +" ");
            }
            System.out.println();
        }
    }


    /**
     * Vérifie si tous les bateaux du joueur sont placés
     * @param tab Grille du joueur
     * @return Un booléen
     */
    private static boolean tousPlaces(boolean[] tab){
        for (int i = 0; i < tab.length; i++) {
            if (!tab[i])
                return false;
        }
        return true;
    }


    /**
     * Initialise la grille du joueur en demandant où il souhaite placer ses bateaux
     */
    private static void initGrilleJeu(){
        int[] taillesBateaux = {5,4,3,3,2};
        boolean[] bateauPlaced = {false, false, false, false, false};

        boolean inputError = false;

        int choixBateau, col, lig, or;
        char colS;

        System.out.print("Veuillez placer vos bateaux");
        while (!tousPlaces(bateauPlaced)){
            afficherGrille(grilleJoueur);

            System.out.print("\nIl reste :\n");
            for (int i = 0; i < bateauPlaced.length; i++) {
                if (!bateauPlaced[i]){
                    System.out.print(i+1+" - "+nomsBateaux[i]+" ("+taillesBateaux[i]+" cases)\n");
                }
            }
            System.out.print("Choisissez un bateau\n>>");

            try {
                choixBateau = Integer.parseInt(m_sScanner.next())-1;
            }
            catch (java.lang.NumberFormatException e) {
                System.out.print("ERREUR : Ce n'est pas un bateau\n");
                continue;
            }

            if (choixBateau > bateauPlaced.length){
                System.out.print("ERREUR : Ce n'est pas un bateau\n");
                continue;
            }
            else if (bateauPlaced[choixBateau]) {
                System.out.print("ERREUR : Ce bateau est déjà placé\n");
                continue;
            }

            System.out.print("Choisissez une ligne (1-"+m_nTailleGrille+")\n>>");
            try {
                lig = Integer.parseInt(m_sScanner.next())-1;
            }
            catch (java.lang.NumberFormatException e){
                System.out.print("ERREUR : Ce n'est pas une ligne\n");
                continue;
            }
            if (lig<0 || lig>=grilleJoueur.length){
                System.out.print("ERREUR : En dehors de la grille\n");
                continue;
            }

            System.out.print("Choisissez une colonne (A-J)\n>>");
            colS = m_sScanner.next().toUpperCase().charAt(0);
            col = colS-'A';
            if (col<0 || col>=grilleJoueur.length){
                System.out.print("ERREUR : En dehors de la grille\n");
                continue;
            }

            System.out.print("Choisissez une orientation (1 pour horizontal et 2 pour vertical)\n>>");
            try {
                or = Integer.parseInt(m_sScanner.next());
            }
            catch (java.lang.NumberFormatException e){
                System.out.print("ERREUR : Ce n'est pas une orientation\n");
                continue;
            }

            if (or!=1 && or!=2){
                System.out.print("ERREUR : Ce n'est pas une orientation\n");
                continue;
            }

            if (positionValide(grilleJoueur, lig, col, or, taillesBateaux[choixBateau])){
                if (or==1){
                    for (int i = 0; i < taillesBateaux[choixBateau]; i++) {
                        grilleJoueur[lig][col+i] = choixBateau+1;
                    }
                }
                else {
                    for (int i = 0; i < taillesBateaux[choixBateau]; i++) {
                        grilleJoueur[lig+i][col] = choixBateau+1;
                    }
                }
                bateauPlaced[choixBateau]=true;
                System.out.print("Bateau placé avec succès\n");
            }
            else {
                System.out.print("ERREUR : Impossible de placer le bateau ici\n");
            }
        }
        System.out.print("Tous les bateaux ont été placés\n");

    }

    private static boolean couler(int[][] grille, int typeBateau){
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++){
                if (grille[i][j] == typeBateau)
                    return false;
            }
        }
        return true;
    }


    /**
     * Fait un tir dans la grille donnée à la position donnée
     * @param grille Grille dans laquelle tirer
     * @param lig Ligne où tirer
     * @param col Colonne où tirer
     * @return <li>0 - Dans l'eau</li><li>-1 - A tiré sur un emplacement déjà touché</li><li>1 - A coulé un bâteau</li><li>2 - A touché un bâteau</li>
     */
    private static int mouvement(int[][] grille, int lig, int col){
        if (grille[lig][col] == 0) {
            System.out.print("Dans l'eau...\n");
            return 0;
        }
        else if (grille[lig][col] == 6) {
            System.out.print("Cette case avait déjà été pilonnée\n");
            return -1;
        }
        else {
            int carreau = grille[lig][col];
            grille[lig][col]=6;
            if (couler(grille, carreau)){
                System.out.print("Coulé ("+nomsBateaux[carreau-1]+") !!!\n");
                return 1;
            }
            else {
                System.out.print("Touché !!!\n");
                return 2;
            }
        }
    }

    /**
     * Donne des coordonnées de tir aléatoires
     * @return Un int[], avec la ligne et la colonne
     */
    private static int[] tirOrdinateur(){
        return new int[] {randint(0,m_nTailleGrille), randint(0, m_nTailleGrille)};
    }

    /**
     * Indique si un joueur a gagné
     * @param grille Grille de l'adversaire
     * @return True si il a gagné, False sinon
     */
    private static boolean vainqueur(int[][] grille){
        for (int i = 0; i < m_nTailleGrille; i++) {
            for (int j = 0; j < m_nTailleGrille; j++) {
                if (grille[i][j] != 0 && grille[i][j] != 6) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Réinitialise la grille en mettant toutes les cases à 0
     * @param grille Grille à réinitialiser
     */
    private static void ReGrille(int[][] grille){
        for (int i = 0; i < m_nTailleGrille; i++) {
            for (int j = 0; j < m_nTailleGrille; j++) {
                grille[i][j]=0;
            }
        }
    }


    /**
     * Demande au joueur où il souhaite tirer
     * @return Un int[], avec la ligne et la colonne
     */
    private static int[] tirJoueur(){

        boolean inputError = true;

        int lig = 0, col = 0;

        while (inputError){
            inputError = false;
            System.out.print("Selectionnez une position où tirer :\n");
            System.out.print("Rentrez une ligne (1-"+m_nTailleGrille+")\n>>");

            try {
                lig = Integer.parseInt(m_sScanner.next())-1;
            }
            catch (java.lang.NumberFormatException e){
                System.out.print("ERREUR : Ce n'est pas une ligne\n");
                inputError = true;
                continue;
            }
            if (lig<0 || lig>=grilleJoueur.length){
                System.out.print("ERREUR : En dehors de la grille\n");
                inputError = true;
                continue;
            }

            System.out.print("Rentrez une colonne (A-J)\n>>");
            char colS = m_sScanner.next().toUpperCase().charAt(0);
            col = colS-'A';
            if (col<0 || col>=grilleJoueur.length){
                System.out.print("ERREUR : En dehors de la grille\n");
                inputError = true;
            }
        }

        return new int[] {lig, col};
    }


    /**
     * Fait le déroulé de la bataille navale
     * @return Le résultat de l'exécution (0 si l'exécution se déroule sans problème jusqu'à la fin)
     */
    public static int engagement(){

        System.out.print(ANSI_VIOLET +"\n#########################################################\n"+ANSI_RESET+
                "Bienvenue dans une nouvelle partie de bataille navale !!!"+
                ANSI_VIOLET +"\n#########################################################\n"+ANSI_RESET);


        ReGrille(grilleOrdi);
        ReGrille(grilleJoueur);

        initGrilleOrdi();
        initGrilleJeu();

        System.out.print(ANSI_VIOLET +"\n######################\n"+ANSI_RESET+
                "Début de la partie !!!"+
                ANSI_VIOLET +"\n######################\n"+ANSI_RESET);

        boolean VicOrdi = false,
                VicJoueur = false;

        int ResOrdi, ResJoueur;

        int lig, col;
        int[] tir;// = tirOrdinateur();

        while (!VicOrdi && !VicJoueur) {

            tir = tirOrdinateur();
            lig=tir[0];
            col=tir[1];

            System.out.print("\n----------\nL'ordinateur joue son tour :\n");

            ResOrdi = mouvement(grilleJoueur, lig, col);

            if (ResOrdi == 1) {
                VicOrdi = vainqueur(grilleJoueur);
                if (VicOrdi)
                    break;
            }

            System.out.print("\nVoici l'état de votre grille :");
            afficherGrille(grilleJoueur);
            System.out.println();

            System.out.print("DEBUG : Grille adversaire :");
            afficherGrille(grilleOrdi);
            System.out.println();


            System.out.print("\n----------\nA votre tour :\n");
            tir = tirJoueur();
            lig=tir[0];
            col=tir[1];



            ResJoueur = mouvement(grilleOrdi, lig, col);

            if (ResJoueur == 1) {
                VicJoueur = vainqueur(grilleOrdi);
                if (VicJoueur)
                    break;
            }



        }


        System.out.print("#######################################################\n" +
                "Fin de partie\n");
        if (VicOrdi){
            System.out.print(ANSI_ROUGE +"Vous avez perdu la partie..."+ANSI_RESET);
        }
        if (VicJoueur){
            System.out.print(ANSI_VERT +"Vous avez gagné la partie !!!"+ANSI_RESET);
        }


        return 0;
    }
}
