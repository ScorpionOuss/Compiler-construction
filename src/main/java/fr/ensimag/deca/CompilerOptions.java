package fr.ensimag.deca;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl16
 * @date 01/01/2021
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO  = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;
    private int nbRegisters;
    public int getDebug() {
        return debug;
    }

    public boolean getParallel() {
        return parallel;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }
 
    public boolean getDecompile(){
        return decompile;
    }
    public boolean getVerification(){
        return verification;
    }
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    private int debug = 0;
    
    private boolean parallel = false;
    private boolean printBanner = false;
    private boolean decompile = false;
    private boolean verification = false;
    private boolean registers = false;

    private List<File> sourceFiles = new ArrayList<File>();
   
  
    public void parseArgs(String[] args) throws CLIException {

    	for (int i = 0; i<args.length; i++) {
                String argument = args[i];
                if (argument.equals("-p")){
                    decompile = true;
                }
                else if(argument.equals("-b")){
                    printBanner = true;
                }
               
                   else if(argument.equals("-P")){
                    parallel = true;
                }
                    else if(argument.equals("-v")){
                    verification = true;
                }
                    else if(argument.equals("-r")){
                    	registers = true;
                    	nbRegisters = Integer.parseInt(args[i+1]);
                    	try {
                    	assert nbRegisters >= 2;
                    	assert nbRegisters <= 15;}
                    	catch (AssertionError e) {
                    		System.err.println("Nombre de registres non compris entre 2 et 15!");
						}
                }  
                    
                else{
                    sourceFiles.add(new File(argument));
                }
    	}
    	
    	Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
        case QUIET: break; // keep default
        case INFO:
            logger.setLevel(Level.INFO); break;
        case DEBUG:
            logger.setLevel(Level.DEBUG); break;
        case TRACE:
            logger.setLevel(Level.TRACE); break;
        default:
            logger.setLevel(Level.ALL); break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }

        //throw new UnsupportedOperationException("not yet implemented");
    }
    public boolean hasOptions(){
        return parallel || printBanner || decompile||verification||registers;
    } 

    protected void displayUsage() {
         System.out.println("La syntaxe d’utilisation de l’exécutable decac est :\n"+
                    "decac [[-p | -v] [-n] [-r X] [-d]* [-P] [-w] <fichier deca>...] | [-b]\n"
            +"-b:    affiche une bannière indiquant le nom de l’équipe\n"
            +"-p:    arrête decac après l’étape de construction de l’arbre, et affiche la décompilation de ce dernier\n" +
             "       (i.e. s’il n’y a qu’un fichier source à compiler, la sortie doit être un programmedeca syntaxiquement correct)\n" 
            +"-v:    arrête decac après l’étape de vérifications (ne produit aucune sortie en l’absence d’erreur)\n"+
            "-n:    supprime les tests de débordement à l’exécution\n" +
             "         - débordement arithmétique\n" +
             "         - débordement mémoire\n" +
             "         - déréférencement de null\n"+
             "-r X:   limite les registres banalisés disponibles à R0 ... R{X-1}, avec 4 <= X <= 16\n"+
             "-d:    active les traces de debug. Répéter l’option plusieurs fois pour avoir plus de traces. \n"+
             "-P:    s’il y a plusieurs fichiers sources, lance la compilation des fichiers en parallèle (pour accélérer la compilation)");
    }

    public boolean getRegisters() {
        return registers;
    }

    public int getNbRegistres() {
        return nbRegisters;
    }
}
