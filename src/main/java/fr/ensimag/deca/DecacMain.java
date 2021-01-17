package fr.ensimag.deca;

import fr.ensimag.deca.tree.AbstractProgram;
import java.io.File;
import org.apache.log4j.Logger;
import fr.ensimag.deca.DecacCompiler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl16
 * @date 01/01/2021
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);
    
    public static void main(String[] args) throws DecacFatalError {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }
        if (options.getPrintBanner()) {
           System.out.println("Equipe GL: 16");
        }
          if (options.getVerifOption()&& options.getDecompile()) {
           System.err.println("Les options -p et -v sont icompatibles");
        }
       
        
        if (options.getDecompile()){
             for (File source : options.getSourceFiles()) {
                 DecacCompiler compiler = new DecacCompiler(options, source);
                 AbstractProgram prog = compiler.doLexingAndParsing(source.getAbsolutePath(), System.err);
                 System.out.println(prog.decompile());
            }
            
           
        }
        if (!options.hasOptions() && options.getSourceFiles().isEmpty()) {

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
        if (options.getParallel()) {
            // A FAIRE : instancier DecacCompiler pour chaque fichier à
            // compiler, et lancer l'exécution des méthodes compile() de chaque
            // instance en parallèle. Il est conseillé d'utiliser
            // java.util.concurrent de la bibliothèque standard Java.
            int fileCount = options.getSourceFiles().size();
            
           ExecutorService executor = Executors.newFixedThreadPool(fileCount);
            List<Callable<Boolean>> callableTasks = new ArrayList<>();
           for (File source : options.getSourceFiles()) {
                Callable<Boolean> cal = ()->{
                   
                    DecacCompiler c = new DecacCompiler(options, source);
                    System.out.println("Ici");
                    c.compile();
                    return c.compile();
                
           };
                       
                callableTasks.add(cal);
           }
           System.out.println(callableTasks.size());
           Future<Boolean> future = executor.submit(()->{
                   
                    
                    System.out.println("Ici");
                    return true ;
                
           });
////            throw new UnsupportedOperationException("Parallel build not yet implemented");
        } else {
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                if (compiler.compile()) {
                    error = true;
                }
            }
        }
        System.exit(error ? 1 : 0);
    }
}
