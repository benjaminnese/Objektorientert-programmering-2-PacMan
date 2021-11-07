import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Koblingsled mellom textfiler og bane lageren
 */
public class BaneDesign {

    private static final int[][][] mazeTabell = new int[GUI.ANTALL_LEVEL][GUI.ANTALL_RAD_KOL][GUI.ANTALL_RAD_KOL];

    /**
     * Henter inn 2 dimensonal array fra
     * .txt fil. Splitter og fjerner utnyttig
     * text data
     * @param level Får inn level tall for å avgjøre
     *              hvilket level som skal hentes
     */
    static public void lagMaze(int level) {

        Scanner leser = null;
        try {
            leser = new Scanner(new File("src/Maze" + level + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int teller = 0;
        if (leser != null) {
            while (leser.hasNextLine()) {
                String rad = leser.nextLine();
                rad = rad.replaceAll("[{},]", ""); //Fjerner '{','}',',' fra linjen
                String[] radSplit = rad.split(" "); //Deretter splittes de på mellomrom
                for (int i = 0; i < radSplit.length; i++)
                    mazeTabell[level - 1][teller][i] = Integer.parseInt(radSplit[i]);
                teller++;
            }
        }
        if (leser != null) {
            leser.close();
        }
    }

    /**
     * Sjekk om posisjon er tom
     * @param x Hvilket level den er på
     * @param y Hvilken rad som sjekkes
     * @param z Hvilken kollone som sjekkes
     * @return sant om påstand stemmer
     */
    static boolean erTom(int x, int y, int z) {
        return (mazeTabell[x][y][z] == 0);
    }

    /**
     * Sjekk om posisjon er en vegg
     * @param x Hvilket level den er på
     * @param y Hvilken rad som sjekkes
     * @param z Hvilken kollone som sjekkes
     * @return sant om påstand stemmer
     */
    static boolean erVegg(int x, int y, int z) {
        return (mazeTabell[x][y][z] == 1);
    }

    /**
     * Sjekk om posisjon er mat
     * @param x Hvilket level den er på
     * @param y Hvilken rad som sjekkes
     * @param z Hvilken kollone som sjekkes
     * @return sant om påstand stemmer
     */
    static boolean erMat(int x, int y, int z) {
        return (mazeTabell[x][y][z] == 2);
    }

    /**
     * Sjekk om posisjon er pacman
     * @param x Hvilket level den er på
     * @param y Hvilken rad som sjekkes
     * @param z Hvilken kollone som sjekkes
     * @return sant om påstand stemmer
     */
    static boolean erPacMan(int x, int y, int z) {
        return (mazeTabell[x][y][z] == 3);
    }

    /**
     * Sjekk om posisjon er et spøkelse
     * @param x Hvilket level den er på
     * @param y Hvilken rad som sjekkes
     * @param z Hvilken kollone som sjekkes
     * @return sant om påstand stemmer
     */
    static boolean erSpokelse(int x, int y, int z) {
        return (mazeTabell[x][y][z] == 4);
    }

    /**
     * Sjekk om posisjon er utvei
     * @param x Hvilket level den er på
     * @param y Hvilken rad som sjekkes
     * @param z Hvilken kollone som sjekkes
     * @return sant om påstand stemmer
     */
    static boolean erFerdigPos(int x, int y, int z) {
        return (mazeTabell[x][y][z] == 5);
    }

    /**
     * Sjekk om posisjon er super mat
     * @param x Hvilket level den er på
     * @param y Hvilken rad som sjekkes
     * @param z Hvilken kollone som sjekkes
     * @return sant om påstand stemmer
     */
    static boolean erSuperMat(int x, int y, int z) {
        return (mazeTabell[x][y][z] == 6);
    }
}
