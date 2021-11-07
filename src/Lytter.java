import javafx.scene.Scene;

    /** LytterKlasse
     * @author Benjamin Nese
     */
public class Lytter {

    /**
     * boolean variabler for å holde styr på
     * hva tast som er trykket ned
     */
    static boolean venstreTast, nedTast, oppTast, hoyreTast, space;

    /**
     *
     * @param scene setter lytter på scene.
     * Sjekker hvilken tast som er trykket ned
     * setter relevant boolean verdi til true
     */
    public static void startLytter(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> oppTast = true;
                case DOWN -> nedTast = true;
                case LEFT -> venstreTast = true;
                case RIGHT -> hoyreTast = true;
                case SPACE -> space = true;
            }

        });
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP -> oppTast = false;
                case DOWN -> nedTast = false;
                case LEFT -> venstreTast = false;
                case RIGHT -> hoyreTast = false;
                case SPACE -> space = false;
            }
        });
    }
}
