import javafx.animation.RotateTransition;
import javafx.util.Duration;

/**
 * Figuren pacman er den som blir styrt av
 * spilleren. Den har en unik forekomst
 * og unike egenskaper
 */
public class PacMan extends LevendeElement {


    private static int score = 0;
    private static int liv = 3;

    private boolean superMode = false;

    /**
     * Setter posisjon til pacman
     *
     * @param rad hvilken rad pacman skal ha
     * @param kol hvilken kollone pacman skal ha
     */
    PacMan(int rad, int kol) {
        super(ElementNavn.PACMAN, rad, kol);
    }

    /**
     * Pacman kan spise andre elementer
     * disse vil bli fjernet eller endret
     * i banen
     *
     * @param element elementet som blir spist
     *                og dermed påvirket.
     */
    @Override
    public void spiser(Element element) {

        if (element.type == ElementNavn.SUPERMAT) {
            score += 10;
            superMode = true;
            Bane.setRedd();
        } else if ((element.type == ElementNavn.MAT)) {
            ++score;
        } else if ((element instanceof Ghost)) {
            Bane.setGulv(element);
        }
        GUI.txtscore.setText("Score: " + score);

        element.setImageView(ElementNavn.GULV);

        Main.reFresh();
    }

    /**
     * Sjekker posisjonen til pacman mot spøkelsene
     * @param spokelse som blir sjekket mot
     * @return sant om de har samme rad og kollone
     */
    public boolean kollidere(LevendeElement spokelse) {
        return (this.getRad() == spokelse.getRad() && this.getKol() == spokelse.getKol());
    }

    /**
     * Roterer pacman
     * @param vinkel hvor mange grader
     *               pacman skal roteres
     */
    public void roterMeg(int vinkel) {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1), this);
        rotateTransition.setToAngle(vinkel);
        rotateTransition.play();
    }

    /**
     * Henter score til pacman
     * @return score
     */

    public static int getScore() {
        return score;
    }

    /**
     * Setter ny score
     * @param score
     */
    public static void setScore(int score) {
        PacMan.score = score;
    }

    /**
     * Henter antall liv igjen for pacman
     * @return antallliv
     */
    public static int getLiv() {
        return liv;
    }

    /**
     * Setter ny antall liv
     * @param liv
     */
    public static void setLiv(int liv) {
        PacMan.liv = liv;
    }

    /**
     * Retunere sant om pacman er i supermodus
     * @return supermodustilstand
     */
    public boolean isSuperMode() {
        return superMode;
    }

    /**
     * Setter pacman i supermodus
     * @param superMode
     */
    public void setSuperMode(boolean superMode) {
        this.superMode = superMode;
    }
}
