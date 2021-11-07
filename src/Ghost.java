/**
 * Abstract klassen som alle spøkelser arver
 * vieworder samt poisjon
 */
public abstract class Ghost extends LevendeElement {
    public Ghost(ElementNavn ghost, int rad, int kol) {
        super(ghost,rad, kol);
        this.setViewOrder(10); //høyere enn pacman
    }


}
