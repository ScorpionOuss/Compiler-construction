package fr.ensimag.deca.tree;

import java.lang.instrument.ClassDefinition;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

public class TestBuildTable {
	public static void main(String[] args) {
		test1();
	}


    public static void test1() {
        AbstractProgram source = initTest1();
        System.out.println("---- From the following Abstract Syntax Tree ----");
        source.prettyPrint(System.out);
        System.out.println("---- We generate the following assembly code ----");        
        String result = gencodeSource(source);
        System.out.println(result);
    }
    
    public static String gencodeSource(AbstractProgram source) {
        DecacCompiler compiler = new DecacCompiler(null,null);
        source.codeGenProgram(compiler);
        return compiler.displayIMAProgram();
    }

    public static AbstractProgram initTest1() {
        ListInst linst = new ListInst();
        ListDeclVar listVar = new ListDeclVar();
        ListDeclClass listClass = new ListDeclClass();
        AbstractProgram source =
            new Program(
                listClass,
                new Main(listVar , linst));
      

        /*************/
////        Location loc = new Location(0, 0, "op");
////        SymbolTable table = new SymbolTable();
////        Symbol sA = table.create("A");
////        AbstractIdentifier idA = new Identifier(sA);
////        ClassDefinition cD = new ClassDefinition(theClass, theClassFile)
////        idA.setDefinition(new ClassDefinition(new ClassType(className, 
////        		, loc
////        		superClass), arg1));
////        
////        
////        Symbol sB = table.create("B");
////        AbstractIdentifier idA = new Identifier(sA);
////        AbstractIdentifier idB = new Identifier(sB);
//        
//        //        DeclClass a = new DeclClass("A", , listDeclField, listDeclMethod)
//        
        return source;
    }
}
