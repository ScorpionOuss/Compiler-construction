package fr.ensimag.deca;

import fr.ensimag.deca.tree.AbstractProgram;
import fr.ensimag.deca.tree.LocationException;

import java.io.File;
import org.apache.log4j.Logger;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl16
 * @date 01/01/2021
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);
    
    public static void main(String[] args) throws DecacFatalError, InterruptedException, ExecutionException, ContextualError {
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
           System.out.println("Equipe GL : 16");
        }
          if (options.getVerification()&& options.getDecompile()) {
           System.err.println("Les options -p et -v sont icompatibles");
        }
         if (options.getVerification()) {
            
            for (File source : options.getSourceFiles()) {
                 DecacCompiler compiler = new DecacCompiler(options, source);
                 AbstractProgram prog = compiler.doLexingAndParsing(source.getAbsolutePath(), System.err);
  
                assert(prog.checkAllLocations());
            
                
                try {
                    prog.verifyProgram(compiler);
                    assert(prog.checkAllDecorations());
                } catch (ContextualError ex) {
                    ex.display(System.err);
                     error = true;
                }
              
            }
        }
        
        if (options.getDecompile()){
             for (File source : options.getSourceFiles()) {
                 DecacCompiler compiler = new DecacCompiler(options, source);
                 AbstractProgram prog = compiler.doLexingAndParsing(source.getAbsolutePath(), System.err);
                 System.out.println(prog.decompile());
            }
            
           
        }
          if (options.getRegisters()){
            System.out.println("Nombre de registres"+options.getNbRegistres());
            
           
        }
        if (!options.hasOptions() && options.getSourceFiles().isEmpty()) {

           options.displayUsage();
                    
        }
        if (options.getParallel()) {
            // A FAIRE : instancier DecacCompiler pour chaque fichier à
            // compiler, et lancer l'exécution des méthodes compile() de chaque
            // instance en parallèle. Il est conseillé d'utiliser
            // java.util.concurrent de la bibliothèque standard Java.
           int nbFiles = options.getSourceFiles().size();
            
           ExecutorService executor = Executors.newFixedThreadPool(nbFiles);
           List<Callable<Boolean>> callables = new ArrayList<>();
           
           for (File source : options.getSourceFiles()) {
                Callable<Boolean> callable = ()->{
                 
                    DecacCompiler c = new DecacCompiler(options, source);
                    
                    c.compile();
                    return c.compile();
                
           };
                       
                callables.add(callable);
           }
           
           
           List<Future<Boolean>> futures = new ArrayList<>();
           for(Callable<Boolean> callable: callables){
               executor.submit( callable).get();
           }
           
        } 
        if(!options.hasOptions()) {
            
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
