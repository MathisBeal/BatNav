package org.xen.batnavgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static final String MENU_FXML = "MainMenu.fxml";
    public static final String PLACE_BOATS_FXML = "PlaceBoats.fxml";
    public static final String PLAY_FXML = "PlayActivity.fxml";

    public static BatailleUI batailleUI = new BatailleUI();

    static Stage m_Stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(MENU_FXML));
        Scene scene = new Scene(fxmlLoader.load());

        m_Stage = stage;

        m_Stage.setResizable(false);

        ChangeScene(MENU_FXML, "BatNavGUI" ,800, 600);

        m_Stage.show();
//        m_Stage.setTitle("BatNavGUI");
//        m_Stage.setScene(scene);
//
//        m_Stage.setWidth(800);
//        m_Stage.setHeight(600);
    }

    static void ChangeScene(String FXML_file, String WindowName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(FXML_file));
        Scene scene = new Scene(fxmlLoader.load(), m_Stage.getWidth(), m_Stage.getHeight());

        m_Stage.setScene(scene);
        m_Stage.setTitle(WindowName);
    }

    static void ChangeScene(String FXML_file, String WindowName, double Width, double Height) throws IOException {
        m_Stage.setWidth(Width);
        m_Stage.setHeight(Height);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(FXML_file));
        Scene scene = new Scene(fxmlLoader.load(), m_Stage.getWidth(), m_Stage.getHeight());

        m_Stage.setScene(scene);
        m_Stage.setTitle(WindowName);
    }

    public static void PreparerNouvellePartie() throws IOException {
        batailleUI = new BatailleUI();
        ChangeScene(MENU_FXML, "BatNavGUI", 800, 600);
    }


    public static void main(String[] args) {
        launch();
    }
}

