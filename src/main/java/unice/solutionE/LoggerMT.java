package unice.solutionE;

import java.io.PrintStream;

public class LoggerMT {

    private static boolean mode;
    private static final PrintStream out = System.out;


    /**
     * initialisation du Logger
     * @param displayMode si il faut afficher ou non dans la sortie standard
     */
    public static void init(boolean displayMode) {
        mode = displayMode;
    }


    /**
     * Affiche dans la sortie standard l'objet str
     * @param str la chaine de caractère / nombre etc...
     */
    public static void show(Object str) {
        if (mode)
            out.println(str);
    }
}
