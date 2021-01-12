package fr.ensimag.deca.tree;

import javax.swing.event.ListDataEvent;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.StringType;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

public class TestDeclaraction {

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
        ListDeclVar listVar = new ListDeclVar();
        AbstractProgram source =
            new Program(
                new ListDeclClass(),
                new Main(listVar ,new ListInst()));
        SymbolTable table = new SymbolTable();
        Symbol create = table.create("int");
        Symbol create2 = table.create("x");
        AbstractIdentifier id1 = new Identifier(create);
        AbstractIdentifier id2 = new Identifier(create2);
        id1.setDefinition(new TypeDefinition(new StringType(create), new Location(0, 0, null)));
        id2.setDefinition(new VariableDefinition(new StringType(create2), new Location(0, 0, null)));
        listVar.add(new DeclVar(id1, id2, new Initialization(new IntLiteral(1))));
        Symbol create3 = table.create("int");
        Symbol create4 = table.create("y");
        AbstractIdentifier id3 = new Identifier(create3);
        AbstractIdentifier id4 = new Identifier(create4);
        id3.setDefinition(new TypeDefinition(new StringType(create3), new Location(0, 0, null)));
        id4.setDefinition(new VariableDefinition(new StringType(create4), new Location(0, 0, null)));
        listVar.add(new DeclVar(id3, id4, new Initialization(new FloatLiteral(2))));




//        linst.add(new Print(false,lexp1));
//        linst.add(new Println(false,lexp2));
//        lexp1.add(new StringLiteral("Hello "));
//        lexp2.add(new StringLiteral("everybody !"));
        return source;
    }
}
