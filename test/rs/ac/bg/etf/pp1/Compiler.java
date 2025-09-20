package rs.ac.bg.etf.pp1;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.HashTableDataStructure;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class Compiler {
	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(Compiler.class);
		
		Reader br = null;
		try {
			File sourceCode = new File("test/program.mj");
//			File sourceCode = new File("test/test301.mj");
//			File sourceCode = new File("test/test302.mj");
//			File sourceCode = new File("test/oporavakOdGresakaNeispravanTest1.mj");
//			File sourceCode = new File("test/oporavakOdGresakaIspravanTest1.mj");
//			File sourceCode = new File("test/oporavakOdGresakaNeispravanTest2.mj");
//			File sourceCode = new File("test/oporavakOdGresakaIspravanTest2.mj");
//			File sourceCode = new File("test/ispravanTest1.mj");
//			File sourceCode = new File("test/neispravanTest1.mj");
//			File sourceCode = new File("test/ispravanTest2.mj");
			
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			// Formiranje abstraktnog sintaksnog stabla
			MJParser p = new MJParser(lexer);
			Symbol s = p.parse();
			
			if (p.errorDetected == true) {
				log.info("Parsiranje NIJE uspesno zavrseno!");
				return;
			} 
			
			Program prog = (Program)(s.value);
			
			// Ispis abstraktnog sintaksnog stabla
			log.info(prog.toString(""));
			log.info("=========================================================================");
			
			// Inicijalizacija tabele simbola
			Tab.init();
			
			// Dodavanje objektnog cvora za Bool unutar tabele simbola
			Struct boolType = new Struct(Struct.Bool);
			Obj boolObj = Tab.insert(Obj.Type, "bool", boolType);
			boolObj.setAdr(-1);
			boolObj.setLevel(-1);
			
			// Dodavanje objektnog cvora za set unutar tabele simbola
			Struct setType = new Struct(Struct.Interface);
			Obj setObj = Tab.insert(Obj.Type, "set", setType);
			setObj.setAdr(-1);
			setObj.setLevel(-1);
			
			// Podesavanje parametara metode ord
			Obj methOrd = Tab.find("ord");
			Object[] allLocalSimbolsOrd = methOrd.getLocalSymbols().toArray();
			
			for (Object localSimbol: allLocalSimbolsOrd) {
				Obj localSym = (Obj)localSimbol;
				localSym.setFpPos(1);
				localSym.setLevel(1);
			}
			
			// Podesavanje parametara metode ord
			Obj methChr = Tab.find("chr");
			Object[] allLocalSimbolsChr = methChr.getLocalSymbols().toArray();
			
			for (Object localSimbol: allLocalSimbolsChr) {
				Obj localSym = (Obj)localSimbol;
				localSym.setFpPos(1);
				localSym.setLevel(1);
			}
			
			// Podesavanje parametara metode ord
			Obj methLen = Tab.find("len");
			Object[] allLocalSimbolsLen = methLen.getLocalSymbols().toArray();
			
			for (Object localSimbol: allLocalSimbolsLen) {
				Obj localSym = (Obj)localSimbol;
				localSym.setFpPos(1);
				localSym.setLevel(1);
			}
			
			// Dodavanje metode add unutar tabele simbola
			Obj methAdd = Tab.insert(Obj.Meth, "add", Tab.noType);
			methAdd.setLevel(2);
			HashTableDataStructure localsAdd = new HashTableDataStructure();
			Obj formPar1 = new Obj(Obj.Var, "a", setType); 
			formPar1.setAdr(0);     
			formPar1.setLevel(1);  
			formPar1.setFpPos(1);  
			Obj formPar2 = new Obj(Obj.Var, "b", Tab.intType);
			formPar2.setAdr(1);
			formPar2.setLevel(1);
			formPar2.setFpPos(1);
			localsAdd.insertKey(formPar1);
			localsAdd.insertKey(formPar2);
			methAdd.setLocals(localsAdd);
			
			// Dodavanje metode addAll unutar tabele simbola
			Obj methAddAll = Tab.insert(Obj.Meth, "addAll", Tab.noType);
			methAddAll.setLevel(2);
			HashTableDataStructure localsAddAll = new HashTableDataStructure();
			Obj formPar3 = new Obj(Obj.Var, "a", setType); 
			formPar3.setAdr(0);     
			formPar3.setLevel(1);  
			formPar3.setFpPos(1);  
			Obj formPar4 = new Obj(Obj.Var, "b", new Struct(Struct.Array, Tab.intType));
			formPar4.setAdr(1);
			formPar4.setLevel(1);
			formPar4.setFpPos(1);
			localsAddAll.insertKey(formPar3);
			localsAddAll.insertKey(formPar4);
			methAddAll.setLocals(localsAddAll);
			
			// Treca faza - semanticka analiza
			SemanticAnalyzer semAnalyzer = new SemanticAnalyzer();
			prog.traverseBottomUp(semAnalyzer); 
			
			// Ispis tabele simbola
			log.info("=========================================================================");
			MyDumpSymbolTableVisitor myVisitor = new MyDumpSymbolTableVisitor();
			Tab.dump(myVisitor);
			
			if (semAnalyzer.passed()) {
				// Cetvrta faza - generisanje koda
				File objOutputFile = new File("test/programResult.obj"); // fajl za smestanje generisanog bytecode-a
//				File objOutputFile = new File("test/test301out.obj");
//				File objOutputFile = new File("test/test302out.obj");
				if (objOutputFile.exists()) objOutputFile.delete();
				
				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				
				Code.dataSize = semAnalyzer.numberOfVars;
				Code.mainPc = codeGenerator.getMainPc();
				Code.write(new FileOutputStream(objOutputFile));
				
				log.info("Generisanje koda uspesno zavrseno!");	
			}
			else {
				log.info("Semanticka analiza NIJE uspesno zavrsena!");
			}
		}
		finally {
			if (br != null) try { br.close(); } catch(IOException e1) { log.error(e1.getMessage(), e1); }
		}
	} 
	// java -cp build\classes MJCompiler\test\rs\ac\bg\etf\pp1\Compiler.java test\test301.MJ > test\test301.out 2> test\test301.err
}
