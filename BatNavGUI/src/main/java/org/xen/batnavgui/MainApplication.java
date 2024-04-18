package org.xen.batnavgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static final String MENU_FXML = "MainMenu.fxml";
    public static final String PLACE_BOATS_FXML = "PlaceBoats.fxml";
    public static final String PLAY_FXML = "";

    static Stage m_Stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(MENU_FXML));
        Scene scene = new Scene(fxmlLoader.load());//, 800, 600);

        m_Stage = stage;

        m_Stage.setTitle("Welcome on BatNavGUI !");
        m_Stage.setScene(scene);

        m_Stage.setWidth(800);
        m_Stage.setHeight(600);

        m_Stage.setResizable(false);

//        m_Stage.setMaxWidth(1200);
//        m_Stage.setMaxHeight(700);
//
//        m_Stage.setMinWidth(800);
//        m_Stage.setMinHeight(600);

        m_Stage.show();
    }

    static void ChangeScene(String FXML_file, String WindowName) throws IOException {



        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(FXML_file));
        Scene scene = new Scene(fxmlLoader.load(), m_Stage.getWidth(), m_Stage.getHeight());

        m_Stage.setScene(scene);
        m_Stage.setTitle(WindowName);
    }

    public static void main(String[] args) {
        launch();
    }
}

//            <ImageView fitHeight="50.0" fitWidth="100.0" pickOnBounds="true" preserveRatio="true">
//               <image>
//                  <Image url="@Boats%201.png" />
//               </image>
//            </ImageView>
//            <ImageView fitHeight="50.0" fitWidth="150.0" pickOnBounds="true" preserveRatio="true">
//               <image>
//                  <Image url="@Boats%202.png" />
//               </image>
//            </ImageView>
//            <ImageView fitHeight="50.0" fitWidth="150.0" pickOnBounds="true" preserveRatio="true">
//               <image>
//                  <Image url="@Boats%203.png" />
//               </image>
//            </ImageView>
//            <ImageView fitHeight="50.0" fitWidth="200.0" pickOnBounds="true" preserveRatio="true">
//               <image>
//                  <Image url="@Boats%204.png" />
//               </image>
//            </ImageView>
//            <ImageView fitHeight="50.0" fitWidth="250.0" pickOnBounds="true" preserveRatio="true">
//               <image>
//                  <Image url="@Boats%205.png" />
//               </image>
//            </ImageView>
