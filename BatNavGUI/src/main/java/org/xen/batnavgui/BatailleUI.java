package org.xen.batnavgui;

import java.util.Random;

/**
 * @author Mathis BÉAL
 * @version 2.0
 * @see <a href="https://github.com/MathisBeal/BatNav/tree/CommandLine">Version ligne de commande</a>
 */
public class BatailleUI {

    // Taille des grilles
    private static int m_nTailleGrille = 10;
    private static String[] nomsBateaux = {"Porte-avion", "Croiseur", "Contre-torpilleur", "Sous-marin", "Torpilleur"};

    // Générateur de nombres aléatoires
    private static Random rand = new Random();

    // Contient la grille de l'ordinateur adverse
    int[][] grilleOrdi = new int[m_nTailleGrille][m_nTailleGrille];

    // Contient la grille du joueur
    int[][] grilleJoueur = new int[m_nTailleGrille][m_nTailleGrille];

    /**
     * Génère un nombre aléatoire
     *
     * @param a Borne inférieure (comprise)
     * @param b Borne supérieure (non comprise)
     * @return L'entier généré aléatoirement
     */
    private static int randint(int a, int b) {
        return rand.nextInt(b - a) + a;
    }


    /**
     * Fonction servant à savoir si il est possible de placer un bateau selon des coordonnées données
     *
     * @param grille    Grille dans laquelle vérifier le placement
     * @param ligne     Ligne de départ du placement du bateau
     * @param colonne   Colonne de départ de placement du bateau
     * @param direction Direction dans laquelle placer le bateau (1 pour horizontal, 2 pour vertical)
     * @param taille    Taille du bateau en nombre de cases qu'il va occuper
     * @return True si il est possible de placer le bateau, False sinon
     */
    private static boolean positionValide(int[][] grille, int ligne, int colonne, int direction, int taille) {

        // Vérifie si le bateau ne dépasse pas de la grille
        if (direction == 1) {
            if (colonne + taille > grille[0].length) {
                return false;
            }
        } else {
            if (ligne + taille > grille.length) {
                return false;
            }
        }

        // Vérifie si le bateau ne sera pas placé sur un autre
        if (direction == 1) {
            for (int i = 0; i < taille; i++) {
                if (grille[ligne][colonne + i] != 0) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < taille; i++) {
                if (grille[ligne + i][colonne] != 0) {
                    return false;
                }
            }
        }

        return true;
    }


    /**
     * Place un bateau sur la grille de l'ordi, suivant la taille donnée
     *
     * @param taille int, Taille du bateau
     * @param type   int, Type du bateau //voir sujet / future doc
     * @return Coord avec la position et l'orientation du bateau placé
     */
    Coord placerBatOrdi(int taille, int type) {

        int col;
        int lig;
        int or;

        do {
            col = randint(0, m_nTailleGrille);
            lig = randint(0, m_nTailleGrille);
            or = randint(1, 3);
        }
        while (!positionValide(grilleOrdi, lig, col, or, taille));

        if (or == 1) {
            for (int i = 0; i < taille; i++) {
                grilleOrdi[lig][col + i] = type;
            }
        } else {
            for (int i = 0; i < taille; i++) {
                grilleOrdi[lig + i][col] = type;
            }
        }

        return new Coord(col, lig, or);
    }


    /**
     * Initialise la grille de l'ordi avec les 5 bateaux
     */
    public void initGrilleOrdi() {
        /*
        1 - Porte-avions (5 cases)
        2 - Croiseur (4 cases)
        3 - Contre-torpilleur (3 cases)
        4 - Sous-marin (3 cases)
        5 - Torpilleur (2 cases)
         */

        Coord co;

        co = placerBatOrdi(5, 5);
        coordsBateauxAdversaire[4] = co;

        co = placerBatOrdi(4, 4);
        coordsBateauxAdversaire[3] = co;

        co = placerBatOrdi(3, 3);
        coordsBateauxAdversaire[2] = co;

        co = placerBatOrdi(3, 2);
        coordsBateauxAdversaire[1] = co;

        co = placerBatOrdi(2, 1);
        coordsBateauxAdversaire[0] = co;
    }

    /**
     * Vérifie si tous les booléens du tableau sont true
     *
     * @param tab Tableau de booléens à vérifier
     * @return true si ils sont tous à vrai, false sinon
     */
    static boolean TousVrais(boolean[] tab) {
        for (int i = 0; i < tab.length; i++) {
            if (!tab[i])
                return false;
        }
        return true;
    }

    /**
     * Vérifie si un bateau a été coulé
     *
     * @param grille     Grille sur laquelle le tir a eu lieu
     * @param typeBateau Type du bateau à vérifier
     * @return true si le bateau a été coulé, false sinon
     */
    private static boolean couler(int[][] grille, int typeBateau) {
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                if (grille[i][j] == typeBateau)
                    return false;
            }
        }
        return true;
    }


    /**
     * Fait un tir dans la grille donnée à la position donnée
     *
     * @param grille Grille dans laquelle tirer
     * @param lig    Ligne où tirer
     * @param col    Colonne où tirer
     * @return <li>0 - Dans l'eau</li><li>-1 - A tiré sur un emplacement déjà touché</li><li>1 - A coulé un bâteau</li><li>2 - A touché un bâteau</li>
     */
    int EffectuerTir(int[][] grille, int lig, int col) {
        if (grille[lig][col] == 0) {
            grille[lig][col] = 6;
            return 0;
        } else if (grille[lig][col] == 6) {
            return -1;
        } else {
            int carreau = grille[lig][col];
            grille[lig][col] = 6;
            if (couler(grille, carreau)) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    /**
     * Donne des coordonnées de tir aléatoires sur une grille
     *
     * @return Un Coord, avec la ligne et la colonne
     */
    private Coord tirRandom() {
        return new Coord(randint(0, m_nTailleGrille), randint(0, m_nTailleGrille), 0);
    }

    /**
     * Fait faire un tir à l'ordinateur
     *
     * @return Un Coord avec les coordonnées d'ou le tir à eu lieu
     */
    Coord EffectuerTirOrdi() {

        Coord coo;

        int res;
        do {
            coo = tirRandom();
            res = EffectuerTir(grilleJoueur, coo.y, coo.x);
        }
        while (res == -1);

        // On utilise l'int qui sert d'habitude à l'orientation pour ici récupérer le résultat du tir
        return new Coord(coo.x, coo.y, res);
    }

    /**
     * Indique si un joueur à perdu (à l'aide de sa grille)
     *
     * @param grille Grille à vérifier
     * @return True si le propriétaire de la grille a perdu, False sinon
     */
    boolean EstPerdant(int[][] grille) {
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
     * Type de donnée servant à stocker des coordonnées ainsi qu'une orientation (la plupart du temps)
     */
    public class Coord {
        public int x = -1;
        public int y = -1;
        public int o = 0;

        public Coord(int _x, int _y, int _o) {
            x = _x;
            y = _y;
            o = _o;
        }

        public Coord() {

        }
    }

    // Liste des placements des bateaux du joueur
    Coord[] coordsBateauxJoueur = new Coord[5];

    // Liste des placements des bateaux de l'ordinateur
    Coord[] coordsBateauxAdversaire = new Coord[5];

    // Stocke si les bateaux ont été placés
    boolean[] bateauxPlaces = new boolean[5];

    // Stocke les tailles des bateaux correspondant aux indices
    final int[] taillesBateaux = {2, 3, 3, 4, 5};

    // Permet de savoir si l'utilisateur souhaite activer le mode triche
    boolean triche;

    /**
     * Place un bateau sur la grille joueur
     * @param bateauSelectione L'indice du bateau sélectionné (+1)
     * @param x La coordonnée x de là où il souhaite placer
     * @param y La coordonnée y de là où il souhaite placer
     * @param orientation L'orientation du bateaau
     * @return Un booléen indiquant si le bateau a été placé ou non
     */
    public boolean DemanderPlacement(Integer bateauSelectione, Integer x, Integer y, int orientation) {

        int a = x;
        x = y;
        y = a;

        if (bateauxPlaces[bateauSelectione - 1]) {
            for (int i = 0; i < m_nTailleGrille; i++) {
                for (int j = 0; j < m_nTailleGrille; j++) {
                    if (grilleJoueur[i][j] == bateauSelectione) {
                        grilleJoueur[i][j] = 0;
                    }
                }
            }
        }

        if (positionValide(grilleJoueur, x, y, orientation, taillesBateaux[bateauSelectione - 1])) {
            if (orientation == 1) {
                for (int i = 0; i < taillesBateaux[bateauSelectione - 1]; i++) {
                    grilleJoueur[x][y + i] = bateauSelectione;
                }
            } else {
                for (int i = 0; i < taillesBateaux[bateauSelectione - 1]; i++) {
                    grilleJoueur[x + i][y] = bateauSelectione;
                }
            }
            coordsBateauxJoueur[bateauSelectione - 1] = new Coord(x, y, orientation);
            return true;
        }
        return false;
    }
}
