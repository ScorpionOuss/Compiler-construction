package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.context.StringType;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

public class TestGestionBoolean {
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
        AbstractProgram source =
            new Program(
                new ListDeclClass(),
                new Main(listVar , linst));
      
        
        /***************************declaration variables*********************/
        SymbolTable table = new SymbolTable();
        Symbol create = table.create("boolean");
        Symbol create2 = table.create("x");
        AbstractIdentifier id1 = new Identifier(create);
        AbstractIdentifier id2 = new Identifier(create2);
        id1.setDefinition(new TypeDefinition(new StringType(create), new Location(0, 0, null)));
        id2.setDefinition(new VariableDefinition(new BooleanType(create2), new Location(0, 0, null)));
        listVar.add(new DeclVar(id1, id2, new Initialization(new BooleanLiteral(true))));
        Symbol create3 = table.create("boolean");
        Symbol create4 = table.create("y");
        AbstractIdentifier id3 = new Identifier(create3);
        AbstractIdentifier id4 = new Identifier(create4);
        id3.setDefinition(new TypeDefinition(new StringType(create3), new Location(0, 0, null)));
        id4.setDefinition(new VariableDefinition(new BooleanType(create4), new Location(0, 0, null)));
        listVar.add(new DeclVar(id3, id4, new Initialization(new BooleanLiteral(false))));
        /*********************************************************************/
        
        /***************************declaration des instructions****************/
        AbstractExpr or = new And(id2, id4);
        AbstractExpr plus = new Plus(new IntLiteral(1), new IntLiteral(2));
        linst.add(or);
        linst.add(plus);
        return source;
    }
}
