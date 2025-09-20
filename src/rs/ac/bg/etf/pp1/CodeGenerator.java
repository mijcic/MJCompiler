package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.HashTableDataStructure;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc = 0;
	private List<Integer> falseJumpAddressAnd = new ArrayList<>();
	private List<Integer> falseJumpAddressOr = new ArrayList<>();
	private List<Integer> addressForSkipThen = new ArrayList<>();
	private List<Integer> addressForSkipElse = new ArrayList<>();
	private List<Integer> startOfDoWhile = new ArrayList<>();
	private List<Integer> conditionOfDoWhile = new ArrayList<>();
	private List<List<Integer>> breakAddresses = new ArrayList<>();
	private List<List<Integer>> continueAddresses = new ArrayList<>();
	private HashTableDataStructure localsAdd = new HashTableDataStructure();
	private Integer sizeOfLocals;
	
	private String currentSetName;
	private Obj currentMethod;
	private Struct currentType;
	private Obj rValueSet;
	
	public int getMainPc() {
		return mainPc;
	}
	
	// Metode
	
		// Ime metode
	
	@Override
	public void visit(MethodName methodName) {
		// mora da se zapamti da adresa da bi se znalo gde da se skoci kada dodje do poziva metode
		methodName.obj.setAdr(Code.pc);
		if (methodName.getMethodName().equals("main")) {
			mainPc = methodName.obj.getAdr();
		}
		
		currentMethod = methodName.obj;
		Tab.openScope();
		
		sizeOfLocals = currentMethod.getLocalSymbols().size();
		
		localsAdd = new HashTableDataStructure();
		Object[] allLocalSimbols = currentMethod.getLocalSymbols().toArray();
		
		for (Object localSimbol: allLocalSimbols) {
			localsAdd.insertKey((Obj)localSimbol);
		}
		
		Obj noviObj = Tab.insert(Obj.Var, "noviObj", Tab.intType);	// nova vrednost koju dodajemo
		noviObj.setAdr(sizeOfLocals++);     
		noviObj.setLevel(1);  
		noviObj.setFpPos(0);
		
		Obj setAddressObj = Tab.insert(Obj.Var, "setAddressObj", Tab.intType); // adresa set-a
		setAddressObj.setAdr(sizeOfLocals++);     
		setAddressObj.setLevel(1);  
		setAddressObj.setFpPos(0);
		
		Obj iCounterObj = Tab.insert(Obj.Var, "iCounterObj", Tab.intType); // brojac unutar for petlje, i = 0
		iCounterObj.setAdr(sizeOfLocals++);     
		iCounterObj.setLevel(1);  
		iCounterObj.setFpPos(0);
		
		Obj maxSetLength = Tab.insert(Obj.Var, "maxSetLength", Tab.intType);	// kapacitet set-a
		maxSetLength.setAdr(sizeOfLocals++);     
		maxSetLength.setLevel(1);  
		maxSetLength.setFpPos(0);
		
		Obj arrayAddressObj = Tab.insert(Obj.Var, "arrayAddressObj", Tab.intType); // nova vrednost koju dodajemo
		arrayAddressObj.setAdr(sizeOfLocals++);     
		arrayAddressObj.setLevel(1);  
		arrayAddressObj.setFpPos(0);
		
		Obj jCounterObj = Tab.insert(Obj.Var, "jCounterObj", Tab.intType); // brojac unutar unutrasnje for petlje, j = 0
		jCounterObj.setAdr(sizeOfLocals++);     
		jCounterObj.setLevel(1);  
		jCounterObj.setFpPos(0);
		
		Obj arrayLengthObj = Tab.insert(Obj.Var, "arrayLengthObj", Tab.intType);
		arrayLengthObj.setAdr(sizeOfLocals++);     
		arrayLengthObj.setLevel(1);  
		arrayLengthObj.setFpPos(0);
		
		Obj setStoreObj = Tab.insert(Obj.Var, "setStoreObj", Tab.intType); 
		setStoreObj.setAdr(sizeOfLocals++);     
		setStoreObj.setLevel(1);  
		setStoreObj.setFpPos(0);
		
		Obj setLoad1Obj = Tab.insert(Obj.Var, "setLoad1Obj", Tab.intType);
		setLoad1Obj.setAdr(sizeOfLocals++);     
		setLoad1Obj.setLevel(1);  
		setLoad1Obj.setFpPos(0);
		
		Obj setLoad2Obj = Tab.insert(Obj.Var, "setLoad2Obj", Tab.intType);
		setLoad2Obj.setAdr(sizeOfLocals++);     
		setLoad2Obj.setLevel(1);  
		setLoad2Obj.setFpPos(0);
		
		Obj currentResultMapObj = Tab.insert(Obj.Var, "currentResultMapObj", Tab.intType);
		currentResultMapObj.setAdr(sizeOfLocals++);     
		currentResultMapObj.setLevel(1);  
		currentResultMapObj.setFpPos(0);
		
		Obj wholeSumMapObj = Tab.insert(Obj.Var, "wholeSumMapObj", Tab.intType);
		wholeSumMapObj.setAdr(sizeOfLocals++);     
		wholeSumMapObj.setLevel(1);  
		wholeSumMapObj.setFpPos(0);
		
		localsAdd.insertKey(noviObj);
		localsAdd.insertKey(setAddressObj);
		localsAdd.insertKey(iCounterObj);
		localsAdd.insertKey(maxSetLength);
		localsAdd.insertKey(arrayAddressObj);
		localsAdd.insertKey(jCounterObj);
		localsAdd.insertKey(arrayLengthObj);
		localsAdd.insertKey(setStoreObj);
		localsAdd.insertKey(setLoad1Obj);
		localsAdd.insertKey(setLoad2Obj);
		localsAdd.insertKey(currentResultMapObj);
		localsAdd.insertKey(wholeSumMapObj);
		
		currentMethod.setLocals(localsAdd);
		
		Code.put(Code.enter);
		Code.put(methodName.obj.getLevel()); // b1 - broj formalnih parametara
		Code.put(methodName.obj.getLocalSymbols().size()); // b2 - broj formalnih parametara i lokalnih promenljivih
	}
	
	@Override
	public void visit(MethodDeclElem methodDeclElem) {
		if (currentMethod != null) Tab.chainLocalSymbols(currentMethod);
		//Tab.closeScope();
		currentMethod = null;
//		localsAdd = new HashTableDataStructure();
//		sizeOfLocals = 0;
		
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	// Tip
	
	@Override
	public void visit(Type type) {
		currentType = type.struct;
	}
	
	// Designator
	
	@Override
	public void visit(DesignatorName designatorName) {
		if (designatorName.obj.getType().getKind() == Struct.Interface) {
			currentSetName = designatorName.getIdentName();
		}
	}
	
	@Override
	public void visit(DesignatorArrayName designatorArrayName) {
		Code.load(designatorArrayName.obj);
	}
	
	@Override 
	public void visit(DesignatorOption_designator designatorOption_designator) {
		if (designatorOption_designator.obj.getType().getKind() == Struct.Interface) {
			currentSetName = designatorOption_designator.obj.getName();
			rValueSet = designatorOption_designator.obj;
		}
	}
	
	@Override 
	public void visit(DesignatorOption_designator_array designatorOption_designator_array) {
		if (designatorOption_designator_array.obj.getType().getKind() == Struct.Interface) {
			rValueSet = designatorOption_designator_array.obj;
		}
	}
	
	// DesignatorStatement
	
		// assignop
	
	@Override
	public void visit(DesignatorStatementAssignop_expr designatorStatementAssignop_expr) {
		Code.store(designatorStatementAssignop_expr.getDesignatorOption().obj);
		
		if (designatorStatementAssignop_expr.getDesignatorOption().obj.getType().getKind() == Struct.Interface) {
			currentSetName = designatorStatementAssignop_expr.getDesignatorOption().obj.getName();
		}
		
		if (designatorStatementAssignop_expr.getDesignatorOption().obj.getType().getKind() == Struct.Interface && 
			designatorStatementAssignop_expr.getExpr().struct.getKind() == Struct.Interface) {
			if (!designatorStatementAssignop_expr.getDesignatorOption().obj.getName().equals(rValueSet.getName())) {
				
				Obj setLengthObj1 = Tab.find("setLength" + designatorStatementAssignop_expr.getDesignatorOption().obj.getName());
				Obj setLengthObj2 = Tab.find("setLength" + rValueSet.getName());
				
				Code.load(setLengthObj2);
				Code.store(setLengthObj1);
			}
		}
	}
	
	@Override
	public void visit(DesignatorStatementAssignop_setop_union designatorStatementAssignop_setop_union) {
		Obj setStoreObj = Tab.find("setStoreObj"); 
		Obj setLoad1Obj = Tab.find("setLoad1Obj");
		Obj setLoad2Obj = Tab.find("setLoad2Obj");
		Obj iCounterObj = Tab.find("iCounterObj"); // brojac unutar spoljasnje for petlje, i = 0
		Obj jCounterObj = Tab.find("jCounterObj"); // brojac unutar unutrasnje for petlje, j = 0
		Obj storeSetLengthObj = Tab.find("setLength" + designatorStatementAssignop_setop_union.getDesignatorOption().obj.getName());
	    Obj set1LengthObj = Tab.find("setLength" + designatorStatementAssignop_setop_union.getDesignatorOption1().obj.getName());
	    Obj set2LengthObj = Tab.find("setLength" + designatorStatementAssignop_setop_union.getDesignatorOption2().obj.getName());
	    Obj maxSetLength = Tab.insert(Obj.Var, "maxSetLength", Tab.intType);	// maksimalna duzina set-a u koji se smestaju elementi
		
		Code.load(designatorStatementAssignop_setop_union.getDesignatorOption().obj);
		Code.store(setStoreObj);
		Code.load(designatorStatementAssignop_setop_union.getDesignatorOption1().obj);
		Code.store(setLoad1Obj);
		Code.load(designatorStatementAssignop_setop_union.getDesignatorOption2().obj);
		Code.store(setLoad2Obj);
		
		Code.loadConst(0);
	    Code.store(iCounterObj);
	    
		Code.loadConst(0);
	    Code.store(jCounterObj);
        
        Code.load(setStoreObj);
        Code.put(Code.arraylength);
        Code.store(maxSetLength);
        
        // dodavanje elemenata prvog set-a
        
        int startOfForLoop1 = Code.pc;

        Code.load(iCounterObj);      
        Code.load(set1LengthObj);    
        Code.putFalseJump(Code.lt, 0); 
        int exitFromLoopAddress1 = Code.pc - 2;

        Code.loadConst(0);
        Code.store(jCounterObj);

        int startOfForLoop2 = Code.pc;

        Code.load(jCounterObj);      
        Code.load(storeSetLengthObj);    
        Code.putFalseJump(Code.lt, 0); 
        int exitFromLoopAddress2 = Code.pc - 2;

        // provera jednakosti
        Code.load(setLoad1Obj);
        Code.load(iCounterObj);
        Code.put(Code.aload);

        Code.load(setStoreObj);
        Code.load(jCounterObj);
        Code.put(Code.aload);

        Code.putFalseJump(Code.ne, 0);
        int elseDoNotAdd = Code.pc - 2;

        Code.load(jCounterObj);    
        Code.loadConst(1);    
        Code.put(Code.add);   
        Code.store(jCounterObj);

        Code.putJump(startOfForLoop2);

        Code.fixup(exitFromLoopAddress2);

        // proveri da li ima mesta u set-u
        Code.load(storeSetLengthObj);
        Code.load(maxSetLength);
        Code.putFalseJump(Code.lt, 0);
        int skipAddToSet = Code.pc - 2;

        // dodaj element
        Code.load(setStoreObj);
        Code.load(storeSetLengthObj);
        Code.load(setLoad1Obj);
        Code.load(iCounterObj);
        Code.put(Code.aload);
        Code.put(Code.astore);

        // povecaj duzinu
        Code.load(storeSetLengthObj);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(storeSetLengthObj);

        Code.fixup(elseDoNotAdd);

        // uvek inkrementiraj iCounter
        Code.load(iCounterObj);    
        Code.loadConst(1);    
        Code.put(Code.add);   
        Code.store(iCounterObj);

        Code.putJump(startOfForLoop1);
        
        Code.fixup(skipAddToSet);

        Code.fixup(exitFromLoopAddress1);
        
        // dodavanje elemenata drugog set-a
        
        Code.loadConst(0);
        Code.store(iCounterObj);

        int startOfForLoop3 = Code.pc;

        Code.load(iCounterObj);      
        Code.load(set2LengthObj);    
        Code.putFalseJump(Code.lt, 0); 
        int exitFromLoopAddress3 = Code.pc - 2;

        Code.loadConst(0);
        Code.store(jCounterObj);

        int startOfForLoop4 = Code.pc;

        Code.load(jCounterObj);      
        Code.load(storeSetLengthObj);    
        Code.putFalseJump(Code.lt, 0); 
        int exitFromLoopAddress4 = Code.pc - 2;

        // provera jednakosti
        Code.load(setLoad2Obj);
        Code.load(iCounterObj);
        Code.put(Code.aload);

        Code.load(setStoreObj);
        Code.load(jCounterObj);
        Code.put(Code.aload);

        Code.putFalseJump(Code.ne, 0);
        int elseDoNotAdd1 = Code.pc - 2;

        Code.load(jCounterObj);    
        Code.loadConst(1);    
        Code.put(Code.add);   
        Code.store(jCounterObj);

        Code.putJump(startOfForLoop4);

        Code.fixup(exitFromLoopAddress4);

        // proveri da li ima mesta
        Code.load(storeSetLengthObj);
        Code.load(maxSetLength);
        Code.putFalseJump(Code.lt, 0);
        int skipAddToSet1 = Code.pc - 2;

        // dodaj element
        Code.load(setStoreObj);
        Code.load(storeSetLengthObj);
        Code.load(setLoad2Obj);
        Code.load(iCounterObj);
        Code.put(Code.aload);
        Code.put(Code.astore);

        // povecaj duzinu
        Code.load(storeSetLengthObj);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(storeSetLengthObj);

        Code.fixup(elseDoNotAdd1);

        // uvek inkrementiraj iCounter
        Code.load(iCounterObj);    
        Code.loadConst(1);    
        Code.put(Code.add);   
        Code.store(iCounterObj);

        Code.putJump(startOfForLoop3);
        
        Code.fixup(skipAddToSet1);

        Code.fixup(exitFromLoopAddress3);
	}
	
		// inc
	
	@Override
	public void visit(DesignatorStatement_inc designatorStatement_inc) {
		// da bi se adresa i indeks niza duplirale, u suprotnom se javlja stack underflow zbog store-a
		if (designatorStatement_inc.getDesignatorOption().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designatorStatement_inc.getDesignatorOption().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorStatement_inc.getDesignatorOption().obj);
	}
	
		// dec
	
	@Override
	public void visit(DesignatorStatement_dec designatorStatement_dec) {
		// da bi se adresa i indeks niza duplirale, u suprotnom se javlja stack underflow zbog store-a
		if (designatorStatement_dec.getDesignatorOption().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designatorStatement_dec.getDesignatorOption().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designatorStatement_dec.getDesignatorOption().obj);
	}
	
		// metode
	
	@Override
	public void visit(DesignatorStatement_no_params designatorStatement_no_params) {
		// relativni pomeraj u odnosu na adresu gde se sada nalazimo
		int offsetAddress = designatorStatement_no_params.getDesignatorOption().obj.getAdr() - Code.pc;
		
		Code.put(Code.call);
		Code.put2(offsetAddress);
		
		if (designatorStatement_no_params.getDesignatorOption().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_params designatorStatement_params) {
		if (designatorStatement_params.getDesignatorOption().obj.getName().equals("add") && designatorStatement_params.getDesignatorOption().obj.getType().getKind() == Struct.None) {						
			Obj noviObj = Tab.find("noviObj");
			Obj setAddressObj = Tab.find("setAddressObj");
			Obj iCounterObj = Tab.find("iCounterObj");
			Obj setLengthObj = Tab.find("setLength" + currentSetName);	// koliko elemenata ima dodatih u setu
			Obj maxSetLength = Tab.find("maxSetLength");
			 
			Code.store(noviObj);
			Code.store(setAddressObj);
			
			Code.loadConst(0);
		    Code.store(iCounterObj);	

		    // for petlja
			
		    int startOfForLoop = Code.pc; // adresa pocetka petlje
		    
		    Code.load(iCounterObj);      
		    Code.load(setLengthObj);    
		    Code.putFalseJump(Code.lt, 0); // ako je vece ili jednako ide se na else granu koja pocinje na adresi exitFromLoopAddress
		    int exitFromLoopAddress = Code.pc - 2;
		    
		    // poredjenje elementa set-a sa odredjenim indeksom i novog elementa koji ubacujemo
		    Code.load(setAddressObj);
		    Code.load(iCounterObj);
		    Code.put(Code.aload);

		    Code.load(noviObj);
		    Code.putFalseJump(Code.ne, 0); // ako su isti, skace se na kraj da se ne bi dodao element jer vec postoji 
		    int elseDoNotAdd = Code.pc - 2;
		    
		    // i++
		    Code.load(iCounterObj);    
		    Code.loadConst(1);    
		    Code.put(Code.add);   
		    Code.store(iCounterObj); 

		    Code.putJump(startOfForLoop); // skok na početak petlje
		    
		    Code.fixup(exitFromLoopAddress);  // fixup adrese izlaska iz petlje
		    
		    // kraj for petlje
	        
	        Code.load(setAddressObj);
	        Code.put(Code.arraylength);
	        Code.store(maxSetLength);
	        
	        Code.load(setLengthObj);
	        Code.load(maxSetLength);
	        Code.putFalseJump(Code.lt, 0); // ako je vece ili jednako - nema mesta da se doda - else
	        int skipToElse = Code.pc - 2;
	        
	        // dodavanje elementa
	        Code.load(setAddressObj);
	        Code.load(setLengthObj);
	        Code.load(noviObj);
	        Code.put(Code.astore);
	        
	        // povecavanje duzine set-a
	        Code.load(setLengthObj);
	        Code.loadConst(1);
	        Code.put(Code.add);
	        Code.store(setLengthObj);
	        
	        // fixup-ovi
	        Code.fixup(skipToElse);
	        
	        Code.fixup(elseDoNotAdd); 
		}
		else if (designatorStatement_params.getDesignatorOption().obj.getName().equals("addAll") && designatorStatement_params.getDesignatorOption().obj.getType().getKind() == Struct.None) {						
			Obj arrayAddressObj = Tab.find("arrayAddressObj"); // nova vrednost koju dodajemo
			Obj setAddressObj = Tab.find("setAddressObj"); // adresa set-a
			Obj iCounterObj = Tab.find("iCounterObj"); // brojac unutar spoljasnje for petlje, i = 0
			Obj jCounterObj = Tab.find("jCounterObj"); // brojac unutar unutrasnje for petlje, j = 0
			Obj setLengthObj = Tab.find("setLength" + currentSetName);  // koliko elemenata ima dodatih u setu
			Obj arrayLengthObj = Tab.find("arrayLengthObj");  // duzina niza
			Obj maxSetLength = Tab.find("maxSetLength"); // kapacitet set-a
			
			Code.store(arrayAddressObj);
			Code.store(setAddressObj);
			
			Code.loadConst(0);
		    Code.store(iCounterObj);
		    
			Code.loadConst(0);
		    Code.store(jCounterObj);
		    
	        Code.load(arrayAddressObj);
	        Code.put(Code.arraylength);
	        Code.store(arrayLengthObj);
	        
	        
	        Code.load(setAddressObj);
	        Code.put(Code.arraylength);
	        Code.store(maxSetLength);
		    
		    // for petlja
		   
	        int startOfForLoop1 = Code.pc;

	        Code.load(iCounterObj);      
	        Code.load(arrayLengthObj);    
	        Code.putFalseJump(Code.lt, 0); 
	        int exitFromLoopAddress1 = Code.pc - 2;

	        // jCounter = 0;
	        Code.loadConst(0);
	        Code.store(jCounterObj);

	        int startOfForLoop2 = Code.pc;

	        Code.load(jCounterObj);      
	        Code.load(setLengthObj);    
	        Code.putFalseJump(Code.lt, 0); 
	        int exitFromLoopAddress2 = Code.pc - 2;

	        // provera da li su jednaki
	        Code.load(arrayAddressObj);
	        Code.load(iCounterObj);
	        Code.put(Code.aload);

	        Code.load(setAddressObj);
	        Code.load(jCounterObj);
	        Code.put(Code.aload);

	        Code.putFalseJump(Code.ne, 0); // ako su isti, skace se na kraj da se ne bi dodao element jer vec postoji 
		    int elseDoNotAdd = Code.pc - 2;

	        Code.load(jCounterObj);    
	        Code.loadConst(1);    
	        Code.put(Code.add);   
	        Code.store(jCounterObj);

	        Code.putJump(startOfForLoop2);

	        Code.fixup(exitFromLoopAddress2);

	        // proveri da li ima mesta u set-u
	        Code.load(setLengthObj);
	        Code.load(maxSetLength);
	        Code.putFalseJump(Code.lt, 0); // ako nema mesta da se doda, skace se na adresu skipAddToSet
	        int skipAddToSet = Code.pc - 2;

	        // dodaj element u set
	        Code.load(setAddressObj);
	        Code.load(setLengthObj);
	        Code.load(arrayAddressObj);
	        Code.load(iCounterObj);
	        Code.put(Code.aload);
	        Code.put(Code.astore);

	        // povecaj duzinu seta
	        Code.load(setLengthObj);
	        Code.loadConst(1);
	        Code.put(Code.add);
	        Code.store(setLengthObj);

	        // proveri dalje elemente niza
	        Code.load(iCounterObj);    
	        Code.loadConst(1);    
	        Code.put(Code.add);   
	        Code.store(iCounterObj);

	        Code.putJump(startOfForLoop1);

	        Code.fixup(elseDoNotAdd); // ako se desila jednakost , ne dodaje se vec se prelazi na sledeci element niza
	        Code.load(iCounterObj);    
	        Code.loadConst(1);    
	        Code.put(Code.add);   
	        Code.store(iCounterObj);

	        Code.putJump(startOfForLoop1);
	        
	        Code.fixup(skipAddToSet);

	        Code.fixup(exitFromLoopAddress1);
		}
		else if (designatorStatement_params.getDesignatorOption().obj.getName().equals("len") && designatorStatement_params.getDesignatorOption().obj.getType().getKind() == Struct.Int) {						
			Code.put(Code.arraylength);
		}
		else if (!(designatorStatement_params.getDesignatorOption().obj.getName().equals("chr") && designatorStatement_params.getDesignatorOption().obj.getType().getKind() == Struct.Char) &&
		!(designatorStatement_params.getDesignatorOption().obj.getName().equals("ord") && designatorStatement_params.getDesignatorOption().obj.getType().getKind() == Struct.Int) &&
		!(designatorStatement_params.getDesignatorOption().obj.getName().equals("len") && designatorStatement_params.getDesignatorOption().obj.getType().getKind() == Struct.Int)) {
			
			// relativni pomeraj u odnosu na adresu gde se sada nalazimo
			int offsetAddress = designatorStatement_params.getDesignatorOption().obj.getAdr() - Code.pc;
			
			Code.put(Code.call);
			Code.put2(offsetAddress);
		}
		
		if (designatorStatement_params.getDesignatorOption().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}
	
	// Statement
	
		// do while
	
	@Override
	public void visit(StartOfDoWhileLoop startOfDoWhileLoop) {
		startOfDoWhile.add(Code.pc);
		conditionOfDoWhile.add(null);
		breakAddresses.add(new ArrayList<>());
		continueAddresses.add(new ArrayList<>());
	}
	
	@Override
	public void visit(StartOfCondition startOfCondition) {
	    conditionOfDoWhile.set(conditionOfDoWhile.size() - 1, Code.pc);

	    // fixup svih continue skokova
	    List<Integer> listOfContinues = continueAddresses.remove(continueAddresses.size() - 1);
	    for (int continueAddress : listOfContinues) {
	        Code.fixup(continueAddress);
	    }
	}
	
	@Override
	public void visit(Statement_do_while statement_do_while) {		
		// tacne vracamo na pocetak do while petlje
		Code.putJump(startOfDoWhile.get(startOfDoWhile.size() - 1));
		startOfDoWhile.remove(startOfDoWhile.size() - 1);
		
		// netacne pustamo ispod do while petlje
		
		// fixup svih break skokova
		List<Integer> listOfBreaks = breakAddresses.remove(breakAddresses.size() - 1);
	    for (int breakAddress : listOfBreaks) {
	        Code.fixup(breakAddress);
	    }
	}
	
	@Override
	public void visit(Statement_do_while_condition statement_do_while_condition) {  // netacni su sacuvani u addressForSkipThen
		// tacne vracamo na pocetak do while petlje
		Code.putJump(startOfDoWhile.get(startOfDoWhile.size() - 1));
		startOfDoWhile.remove(startOfDoWhile.size() - 1);
		
		// netacne pustamo ispod do while petlje
		Code.fixup(addressForSkipThen.remove(addressForSkipThen.size() - 1));
		
		// fixup svih break skokova
		List<Integer> listOfBreaks = breakAddresses.remove(breakAddresses.size() - 1);
	    for (int breakAddress : listOfBreaks) {
	        Code.fixup(breakAddress);
	    }
	}
	
	@Override
	public void visit(Statement_do_while_condition_comma_statement statement_do_while_condition_comma_statement) {  // netacni su sacuvani u addressForSkipThen		
		// tacne vracamo na pocetak do while petlje
		Code.putJump(startOfDoWhile.get(startOfDoWhile.size() - 1));
		startOfDoWhile.remove(startOfDoWhile.size() - 1);
		
		// netacne pustamo ispod do while petlje
		Code.fixup(addressForSkipThen.remove(addressForSkipThen.size() - 1));
		
		// fixup svih break skokova
		List<Integer> listOfBreaks = breakAddresses.remove(breakAddresses.size() - 1);
	    for (int breakAddress : listOfBreaks) {
	        Code.fixup(breakAddress);
	    }
	}
	
		// break
	
	@Override
	public void visit(Statement_break statement_break) {		
		Code.putJump(0);
		breakAddresses.get(breakAddresses.size() - 1).add(Code.pc - 2);
	}
	
		// continue
	
	@Override
	public void visit(Statement_continue statement_continue) {
		Code.putJump(0);
	    continueAddresses.get(continueAddresses.size() - 1).add(Code.pc - 2);
	}
	
		// read
	
	@Override
	public void visit(Statement_read statement_read) {
		if (statement_read.getDesignatorOption().obj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		}
		else {
			Code.put(Code.read);
		}
		Code.store(statement_read.getDesignatorOption().obj);
	}
	
		// print
	
	@Override
	public void visit(Statement_print_expr statement_print_expr) {
		if (statement_print_expr.getExpr().struct.equals(Tab.charType)) {
			Code.loadConst(0); // sirina ispisa
			Code.put(Code.bprint);
		}
		else {
			if (statement_print_expr.getExpr().struct.getKind() == Struct.Interface) {				
				Obj setAddressObj = Tab.find("setAddressObj");
				Obj iCounterObj = Tab.find("iCounterObj");
				Obj setLengthObj = Tab.find("setLength" + currentSetName);
				
				Code.store(setAddressObj);
				
				Code.loadConst(0);
			    Code.store(iCounterObj);
			    
			    int startOfForLoop = Code.pc; // adresa pocetka petlje
			    
			    Code.load(iCounterObj);      
			    Code.load(setLengthObj);    
			    Code.putFalseJump(Code.lt, 0); // ako je vece ili jednako ide se na else granu koja pocinje na adresi exitFromLoopAddress
			    int exitFromLoopAddress = Code.pc - 2;
			    
			    // ucitavanje elementa niza na stek
			    Code.load(setAddressObj);
			    Code.load(iCounterObj);
			    Code.put(Code.aload);

			    Code.loadConst(0); // sirina ispisa
				Code.put(Code.print);
				
				Code.loadConst(32);
				Code.loadConst(1);
				Code.put(Code.bprint);
				
				Code.load(iCounterObj);
				Code.loadConst(1);
				Code.put(Code.add);
				Code.store(iCounterObj);

				// jump to start
				Code.putJump(startOfForLoop);
			    
			    Code.fixup(exitFromLoopAddress);  // fixup adrese izlaska iz petlje
			}
			else {
				Code.loadConst(0); // sirina ispisa
				Code.put(Code.print);
			}
		}
	}
	
	@Override
	public void visit(Statement_print_expr_comma_number statement_print_expr_comma_number) {
		if (statement_print_expr_comma_number.getExpr().struct.equals(Tab.charType)) {
			Code.loadConst(statement_print_expr_comma_number.getN2()); // sirina ispisa
			Code.put(Code.bprint);
		}
		else {
			if (statement_print_expr_comma_number.getExpr().struct.getKind() == Struct.Interface) {
				Obj setAddressObj = Tab.find("setAddressObj");
				Obj iCounterObj = Tab.find("iCounterObj");
				Obj setLengthObj = Tab.find("setLength" + currentSetName);
				
				Code.store(setAddressObj);
				
				Code.loadConst(0);
			    Code.store(iCounterObj);
			    
			    int startOfForLoop = Code.pc; // adresa pocetka petlje
			    
			    Code.load(iCounterObj);      
			    Code.load(setLengthObj);    
			    Code.putFalseJump(Code.lt, 0); // ako je vece ili jednako ide se na else granu koja pocinje na adresi exitFromLoopAddress
			    int exitFromLoopAddress = Code.pc - 2;
			    
			    // ucitavanje elementa niza na stek
			    Code.load(setAddressObj);
			    Code.load(iCounterObj);
			    Code.put(Code.aload);

			    // ako je prvi element, ispisi sirinu, u suprotnom sirina je 0
			    Code.load(iCounterObj);
			    Code.loadConst(0);
			    Code.putFalseJump(Code.eq, 0);
			    int notFirstElemJumpAddr = Code.pc - 2;

			    Code.loadConst(statement_print_expr_comma_number.getN2());
			    Code.putJump(0);
			    int afterSetupAddr = Code.pc - 2;

			    Code.fixup(notFirstElemJumpAddr);
			    Code.loadConst(0);

			    Code.fixup(afterSetupAddr);

				Code.put(Code.print);
				
				Code.loadConst(32);
				Code.loadConst(1);
				Code.put(Code.bprint);
				
				Code.load(iCounterObj);
				Code.loadConst(1);
				Code.put(Code.add);
				Code.store(iCounterObj);

				// jump to start
				Code.putJump(startOfForLoop);
			    
			    Code.fixup(exitFromLoopAddress);  // fixup adrese izlaska iz petlje
			}
			else {
				Code.loadConst(statement_print_expr_comma_number.getN2()); // sirina ispisa
				Code.put(Code.print);
			}
		}
	}
	
		// return
	
	@Override
	public void visit(Statement_return statement_return) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(Statement_return_expr statement_return_expr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	// Conditions
	
	@Override
	public void visit(CondFact_expr condFact_expr) {
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0); // expr == 0 (znaci ako je expr netacan), skacemo na else granu
		falseJumpAddressAnd.add(Code.pc - 2);	// adresa koju cemo kasnije da fixup-ujemo
	}
	
	@Override
	public void visit(CondFact_expr_relop_expr condFact_expr_relop_expr) {
		if (condFact_expr_relop_expr.getRelop() instanceof Relop_is_equal) {
			Code.putFalseJump(Code.eq, 0); // expr1 != expr2 (znaci ako nisu jednaki), skacemo na else granu
		}
		else if (condFact_expr_relop_expr.getRelop() instanceof Relop_is_not_equal) {
			Code.putFalseJump(Code.ne, 0);
		}
		else if (condFact_expr_relop_expr.getRelop() instanceof Relop_greater) {
			Code.putFalseJump(Code.gt, 0);
		}
		else if (condFact_expr_relop_expr.getRelop() instanceof Relop_greater_or_equal) {
			Code.putFalseJump(Code.ge, 0);
		}
		else if (condFact_expr_relop_expr.getRelop() instanceof Relop_less) {
			Code.putFalseJump(Code.lt, 0);
		}
		else if (condFact_expr_relop_expr.getRelop() instanceof Relop_less_or_equal) {
			Code.putFalseJump(Code.le, 0);
		}
		falseJumpAddressAnd.add(Code.pc - 2);	// adresa koju cemo kasnije da fixup-ujemo
	}
	
	@Override
	public void visit(CondTermList condTermList) { // ulaze nam samo tacne niti jer su prosle sve AND iskaze jednog OR-a
		Code.putJump(0); // samo bezuslovno skoci na then deo jer je tacna
		falseJumpAddressOr.add(Code.pc - 2);
		
		// netacne moramo da vratimo ovde, jer je ispod mene sledeci OR za koji treba isto da se ispituje
		for (int i = 0; i < falseJumpAddressAnd.size(); i++) {
			Code.fixup(falseJumpAddressAnd.get(i));
		}
		falseJumpAddressAnd.clear();
	}
	
	@Override
	public void visit(ConditionList conditionList) {
		// netacne koji su proveravani u OR usmeravamo na else granu, jer nema sanse da vise budu tacni (nema vise or-ova = nema vise sta da se proverava)
		Code.putJump(0);
		addressForSkipThen.add(Code.pc - 2);
		
		// ovde vracamo one koje su tacne
		for (int i = 0; i < falseJumpAddressOr.size(); i++) {
			Code.fixup(falseJumpAddressOr.get(i));
		}
		falseJumpAddressOr.clear();
	}
	
	@Override
	public void visit(Statement_if statement_if) {
		Code.fixup(addressForSkipThen.get(addressForSkipThen.size() - 1));
		addressForSkipThen.remove(addressForSkipThen.size() - 1);
	}
	
	@Override
	public void visit(Else elseInStatement) {
		// prvo izbacimo tacne koji su zavrsili then granu i treba da preskoce else, pa ubacimo netacne da bi izvrsili else granu
		Code.putJump(0); // tacne bacamo na kraj else, jer oni ne treba da rade else
		addressForSkipElse.add(Code.pc - 2);
		
		Code.fixup(addressForSkipThen.get(addressForSkipThen.size() - 1)); // netacne se bacaju da rade else
		addressForSkipThen.remove(addressForSkipThen.size() - 1);
	}
	
	@Override
	public void visit(Statement_if_else statement_if_else) {
		// fixup-ujemo adresu za tacne koje su preskocile else
		Code.fixup(addressForSkipElse.get(addressForSkipElse.size() - 1));
		addressForSkipElse.remove(addressForSkipElse.size() - 1);
		// posle ovoga nastavljaju i netacne i tacne
	}
	
	// Expr
	
	@Override
	public void visit(MinusTerm minusTerm) {
		Code.put(Code.neg);
	}
	
	@Override
	public void visit(ExprMore_more exprMore_more) {
		if (exprMore_more.getAddop() instanceof Addop_plus) {
			Code.put(Code.add);
		}
		else if (exprMore_more.getAddop() instanceof Addop_minus) {
			Code.put(Code.sub);
		}
	}
	
	@Override
	public void visit(Expr_map expr_map) {
		Obj functionDesignator = expr_map.getDesignatorOption().obj;
		Obj arrayDesignator = expr_map.getDesignatorOption1().obj;
		
		Obj iCounterObj = Tab.find("iCounterObj");
		Obj arrayLengthObj = Tab.find("arrayLengthObj");
		Obj currentResultMapObj = Tab.find("currentResultMapObj");
		Obj wholeSumMapObj = Tab.find("wholeSumMapObj");
		
		Code.load(arrayDesignator);
		Code.put(Code.arraylength);
        Code.store(arrayLengthObj);
		
		// inicijalizacija
		
		Code.loadConst(0);
		Code.store(iCounterObj);

		Code.loadConst(0);
		Code.store(wholeSumMapObj);
		
		// for petlja

		int startOfForLoop = Code.pc; // adresa pocetka petlje
	    
	    Code.load(iCounterObj);      
	    Code.load(arrayLengthObj);    
	    Code.putFalseJump(Code.lt, 0); // ako je vece ili jednako ide se na else granu koja pocinje na adresi exitFromLoopAddress
	    int exitFromLoopAddress = Code.pc - 2;
	    
	    // ucitavanje elementa niza, kako bi se nasao na steku kada udjemo u funkciju
	    Code.load(arrayDesignator);
	    Code.load(iCounterObj);
	    Code.put(Code.aload);
	    
	    int offsetAddress = functionDesignator.getAdr() - Code.pc;
	    
	    // skok na funkciju sa parametrom koji je na steku
	    Code.put(Code.call);
		Code.put2(offsetAddress);
		
		Code.store(currentResultMapObj);
		
		// dodaje se u sumu
		Code.load(wholeSumMapObj);
		Code.load(currentResultMapObj);
		Code.put(Code.add);
		Code.store(wholeSumMapObj);
  
	    // i++
	    Code.load(iCounterObj);    
	    Code.loadConst(1);    
	    Code.put(Code.add);   
	    Code.store(iCounterObj); 

	    Code.putJump(startOfForLoop); // skok na početak petlje
	    
	    Code.fixup(exitFromLoopAddress);  // fixup adrese izlaska iz petlje
	    
	    Code.load(wholeSumMapObj);	// da bi se sacuvao u designator za dodelu
	    
	    // kraj for petlje
	}	
	
	// Term
	
	@Override
	public void visit(TermMore_more termMore_more) {
		if (termMore_more.getMulop() instanceof Mulop_mul) {
			Code.put(Code.mul);
		}
		else if (termMore_more.getMulop() instanceof Mulop_div) {
			Code.put(Code.div);
		}
		else if (termMore_more.getMulop() instanceof Mulop_mod) {
			Code.put(Code.rem);
		}
	}
	
	// Factor
	
	@Override
	public void visit(Factor_designator factor_designator) {
		Code.load(factor_designator.getDesignatorOption().obj);
	}
	
	@Override
	public void visit(Factor_designator_fun_no_params factor_designator_fun_no_params) {
		// relativni pomeraj u odnosu na adresu gde se sada nalazimo
		int offsetAddress = factor_designator_fun_no_params.getDesignatorOption().obj.getAdr() - Code.pc;
		
		Code.put(Code.call);
		Code.put2(offsetAddress);
	}
	
	@Override
	public void visit(Factor_designator_fun_params factor_designator_fun_params) {
		if (factor_designator_fun_params.getDesignatorOption().obj.getName().equals("add") && factor_designator_fun_params.getDesignatorOption().obj.getType().getKind() == Struct.None) {						
			Obj noviObj = Tab.find("noviObj");
			Obj setAddressObj = Tab.find("setAddressObj");
			Obj iCounterObj = Tab.find("iCounterObj");
			Obj setLengthObj = Tab.find("setLength" + currentSetName);	// koliko elemenata ima dodatih u setu
			Obj maxSetLength = Tab.find("maxSetLength");
			 
			Code.store(noviObj);
			Code.store(setAddressObj);
			
			Code.loadConst(0);
		    Code.store(iCounterObj);	

		    // for petlja
			
		    int startOfForLoop = Code.pc; // adresa pocetka petlje
		    
		    Code.load(iCounterObj);      
		    Code.load(setLengthObj);    
		    Code.putFalseJump(Code.lt, 0); // ako je vece ili jednako ide se na else granu koja pocinje na adresi exitFromLoopAddress
		    int exitFromLoopAddress = Code.pc - 2;
		    
		    // poredjenje elementa set-a sa odredjenim indeksom i novog elementa koji ubacujemo
		    Code.load(setAddressObj);
		    Code.load(iCounterObj);
		    Code.put(Code.aload);

		    Code.load(noviObj);
		    Code.putFalseJump(Code.ne, 0); // ako su isti, skace se na kraj da se ne bi dodao element jer vec postoji 
		    int elseDoNotAdd = Code.pc - 2;
		    
		    // i++
		    Code.load(iCounterObj);    
		    Code.loadConst(1);    
		    Code.put(Code.add);   
		    Code.store(iCounterObj); 

		    Code.putJump(startOfForLoop); // skok na početak petlje
		    
		    Code.fixup(exitFromLoopAddress);  // fixup adrese izlaska iz petlje
		    
		    // kraj for petlje
	        
	        Code.load(setAddressObj);
	        Code.put(Code.arraylength);
	        Code.store(maxSetLength);
	        
	        Code.load(setLengthObj);
	        Code.load(maxSetLength);
	        Code.putFalseJump(Code.lt, 0); // ako je vece ili jednako - nema mesta da se doda - else
	        int skipToElse = Code.pc - 2;
	        
	        // dodavanje elementa
	        Code.load(setAddressObj);
	        Code.load(setLengthObj);
	        Code.load(noviObj);
	        Code.put(Code.astore);
	        
	        // povecavanje duzine set-a
	        Code.load(setLengthObj);
	        Code.loadConst(1);
	        Code.put(Code.add);
	        Code.store(setLengthObj);
	        
	        // fixup-ovi
	        Code.fixup(skipToElse);
	        
	        Code.fixup(elseDoNotAdd); 
		}
		else if (factor_designator_fun_params.getDesignatorOption().obj.getName().equals("addAll") && factor_designator_fun_params.getDesignatorOption().obj.getType().getKind() == Struct.None) {						
			Obj arrayAddressObj = Tab.find("arrayAddressObj"); // nova vrednost koju dodajemo
			Obj setAddressObj = Tab.find("setAddressObj"); // adresa set-a
			Obj iCounterObj = Tab.find("iCounterObj"); // brojac unutar spoljasnje for petlje, i = 0
			Obj jCounterObj = Tab.find("jCounterObj"); // brojac unutar unutrasnje for petlje, j = 0
			Obj setLengthObj = Tab.find("setLength" + currentSetName);  // koliko elemenata ima dodatih u setu
			Obj arrayLengthObj = Tab.find("arrayLengthObj");  // duzina niza
			Obj maxSetLength = Tab.find("maxSetLength"); // kapacitet set-a
			
			Code.store(arrayAddressObj);
			Code.store(setAddressObj);
			
			Code.loadConst(0);
		    Code.store(iCounterObj);
		    
			Code.loadConst(0);
		    Code.store(jCounterObj);
		    
	        Code.load(arrayAddressObj);
	        Code.put(Code.arraylength);
	        Code.store(arrayLengthObj);
	        
	        
	        Code.load(setAddressObj);
	        Code.put(Code.arraylength);
	        Code.store(maxSetLength);
		    
		    // for petlja
		   
	        int startOfForLoop1 = Code.pc;

	        Code.load(iCounterObj);      
	        Code.load(arrayLengthObj);    
	        Code.putFalseJump(Code.lt, 0); 
	        int exitFromLoopAddress1 = Code.pc - 2;

	        // jCounter = 0;
	        Code.loadConst(0);
	        Code.store(jCounterObj);

	        int startOfForLoop2 = Code.pc;

	        Code.load(jCounterObj);      
	        Code.load(setLengthObj);    
	        Code.putFalseJump(Code.lt, 0); 
	        int exitFromLoopAddress2 = Code.pc - 2;

	        // provera da li su jednaki
	        Code.load(arrayAddressObj);
	        Code.load(iCounterObj);
	        Code.put(Code.aload);

	        Code.load(setAddressObj);
	        Code.load(jCounterObj);
	        Code.put(Code.aload);

	        Code.putFalseJump(Code.ne, 0); // ako su isti, skace se na kraj da se ne bi dodao element jer vec postoji 
		    int elseDoNotAdd = Code.pc - 2;

	        Code.load(jCounterObj);    
	        Code.loadConst(1);    
	        Code.put(Code.add);   
	        Code.store(jCounterObj);

	        Code.putJump(startOfForLoop2);

	        Code.fixup(exitFromLoopAddress2);

	        // proveri da li ima mesta u set-u
	        Code.load(setLengthObj);
	        Code.load(maxSetLength);
	        Code.putFalseJump(Code.lt, 0); // ako nema mesta da se doda, skace se na adresu skipAddToSet
	        int skipAddToSet = Code.pc - 2;

	        // dodaj element u set
	        Code.load(setAddressObj);
	        Code.load(setLengthObj);
	        Code.load(arrayAddressObj);
	        Code.load(iCounterObj);
	        Code.put(Code.aload);
	        Code.put(Code.astore);

	        // povecaj duzinu seta
	        Code.load(setLengthObj);
	        Code.loadConst(1);
	        Code.put(Code.add);
	        Code.store(setLengthObj);

	        // proveri dalje elemente niza
	        Code.load(iCounterObj);    
	        Code.loadConst(1);    
	        Code.put(Code.add);   
	        Code.store(iCounterObj);

	        Code.putJump(startOfForLoop1);

	        Code.fixup(elseDoNotAdd); // ako se desila jednakost , ne dodaje se vec se prelazi na sledeci element niza
	        Code.load(iCounterObj);    
	        Code.loadConst(1);    
	        Code.put(Code.add);   
	        Code.store(iCounterObj);

	        Code.putJump(startOfForLoop1);
	        
	        Code.fixup(skipAddToSet);

	        Code.fixup(exitFromLoopAddress1);
		}
		else if (factor_designator_fun_params.getDesignatorOption().obj.getName().equals("len") && factor_designator_fun_params.getDesignatorOption().obj.getType().getKind() == Struct.Int) {
			Code.put(Code.arraylength);
		}
		else if (!(factor_designator_fun_params.getDesignatorOption().obj.getName().equals("chr") && factor_designator_fun_params.getDesignatorOption().obj.getType().getKind() == Struct.Char) &&
			!(factor_designator_fun_params.getDesignatorOption().obj.getName().equals("ord") && factor_designator_fun_params.getDesignatorOption().obj.getType().getKind() == Struct.Int)  && 
			!(factor_designator_fun_params.getDesignatorOption().obj.getName().equals("len") && factor_designator_fun_params.getDesignatorOption().obj.getType().getKind() == Struct.Int)
			) {
			// relativni pomeraj u odnosu na adresu gde se sada nalazimo
			int offsetAddress = factor_designator_fun_params.getDesignatorOption().obj.getAdr() - Code.pc;
			
			Code.put(Code.call);
			Code.put2(offsetAddress);
		}
	}
	
	@Override
	public void visit(Factor_number factor_number) {
		Code.loadConst(factor_number.getN1());
	}
	
	@Override
	public void visit(Factor_character factor_character) {
		Code.loadConst(factor_character.getC1());
	}
	
	@Override
	public void visit(Factor_bool factor_bool) {
		Code.loadConst(factor_bool.getB1());
	}
	
	@Override
	public void visit(Factor_new factor_new) {
		Code.put(Code.newarray);
		if (factor_new.getType().struct.equals(Tab.charType)) {
			Code.put(0);
		}
		else {
			Code.put(1);
		}
		
		if (factor_new.getType().struct.getKind() == Struct.Interface) {	
			Obj setLengthObj = Tab.insert(Obj.Var, "setLength" + currentSetName, Tab.intType);		// koliko elemenata ima dodatih u setu
			setLengthObj.setAdr(sizeOfLocals++);     
			setLengthObj.setLevel(1);  
			setLengthObj.setFpPos(0);
			
			localsAdd.insertKey(setLengthObj);
			
//			programObj.setLocals(localsAdd);
			currentMethod.setLocals(localsAdd);
			
			Code.loadConst(0);
			Code.store(setLengthObj);
		}
	}
}