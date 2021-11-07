import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Oblig i OBJ2100 av Benjamin Nese
 * Spillet pacman laget med JavaFX
 */
public class Main extends Application {

    static final ArrayList<Ghost> ghostArrayList = new ArrayList<>();
    static Bane bane;
    static int level = 1;

    @Override
    public void start(Stage primaryStage) {
        Main.initSpill(GUI.setStage(primaryStage));

    }

    /**
     * Ved spillstart kjøres initBane, lytter og timeline
     * @param scene tar inn scene så funksjonen startLytter()
     * får brukt den
     */
    static public void initSpill(Scene scene) {
        initBane(1);
        Lytter.startLytter(scene);
        startAnimationTimer();
        GUI.timeline.play();
    }

    /**
     * Fjerner alle barn i spillPanelet
     * legger dem til på nytt sånn at spillPanelet
     * blir oppdatert visuelt
     */
    static public void reFresh() {
        GridPane baneElementer = new GridPane();
        GUI.spillPanel.getChildren().clear();
        for (int i = 0; i < Bane.getBaneGrid().length; i++) {
            for (int j = 0; j < Bane.getBaneGrid()[0].length; j++) {
                baneElementer.add(Bane.getBaneGrid()[j][i], i, j);
            }
        }
        GUI.spillPanel.getChildren().add(baneElementer);
    }

    /**
     * Klarer og lager nye bane
     * @param level tar inn et tall level
     * som viser til hvilken Maze som
     * skal bli hentet
     */
    public static void initBane(int level) {
        if (GUI.ANTALL_LEVEL >= level) {
            GUI.panel.getChildren().clear();
            GUI.panel.requestFocus();
            GUI.panel.setCenter(GUI.spillPanel);
            GUI.panel.setTop(GUI.topPanel());
            GUI.panel.setBottom(GUI.bunnPanel());

            bane = new Bane(level);
            ghostArrayList.clear();
            for (int i = 0; i < Bane.getBaneGrid().length; i++) {
                for (int j = 0; j < Bane.getBaneGrid()[0].length; j++) {
                    if (Bane.getBaneGrid()[i][j].isGhost()) {
                        ghostArrayList.add((Ghost) Bane.getBaneGrid()[i][j]);
                    }
                }
            }
            reFresh();
        } else {

            GUI.timeline.stop();
        }
    }

    /**
     * Lager timeline, som kjøres hvert 150 milisekund(0.15 sekund)
     * for hver kall på timelinen vil oppdater(pacman)
     * bli kjørt i Oppdater klassen
     */
    public static void startAnimationTimer() {
        GUI.timeline = new Timeline(new KeyFrame(Duration.millis(150), actionEvent -> {
            try {
                Oppdater.oppdater(bane.getPacMan());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        GUI.timeline.setCycleCount(Timeline.INDEFINITE);
    }
}
