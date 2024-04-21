package org.xen.batnavgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Mathis BÉAL
 * @version 1.0
 */
public class ApplicationPrincipale extends Application {

    public static final String MENU_PRINCIPAL_FXML = "MenuPrincipal.fxml";
    public static final String PLACER_BATEAUX_FXML = "PlacerBateaux.fxml";
    public static final String JOUER_FXML = "Jouer.fxml";

    public static BatailleUI batailleUI = new BatailleUI();

    static Stage m_Stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationPrincipale.class.getResource(MENU_PRINCIPAL_FXML));
        Scene scene = new Scene(fxmlLoader.load());

        m_Stage = stage;

        m_Stage.setResizable(false);

        ChangerScene(MENU_PRINCIPAL_FXML, "BatNavGUI" ,800, 600);

        m_Stage.show();
    }

    /**
     * Change la scène actuelle à celle donnée en paramètre
     * @param Ficher_FXML FXML de la scène à charger
     * @param NomFenetre Nom qui sera donné à la fenêtre
     * @throws IOException
     */
    static void ChangerScene(String Ficher_FXML, String NomFenetre) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationPrincipale.class.getResource(Ficher_FXML));
        Scene scene = new Scene(fxmlLoader.load(), m_Stage.getWidth(), m_Stage.getHeight());

        m_Stage.setScene(scene);
        m_Stage.setTitle(NomFenetre);
    }

    /**
     * Change la scène actuelle à celle donnée en paramètre
     * @param Ficher_FXML FXML de la scène à charger
     * @param NomFenetre Nom qui sera donné à la fenêtre
     * @param largeur Largeur de la fenêtre
     * @param hauteur Hauteur de la fenêtre
     * @throws IOException
     */
    static void ChangerScene(String Ficher_FXML, String NomFenetre, double largeur, double hauteur) throws IOException {
        m_Stage.setWidth(largeur);
        m_Stage.setHeight(hauteur);

        ChangerScene(Ficher_FXML, NomFenetre);
    }

    /**
     * Permet de préparer le lancement d'une nouvelle partie en allant au menu principal
     * @throws IOException
     */
    public static void PreparerNouvellePartie() throws IOException {
        batailleUI = new BatailleUI();
        ChangerScene(MENU_PRINCIPAL_FXML, "BatNavGUI", 800, 600);
    }


    public static void main(String[] args) {
        launch();
    }
}

