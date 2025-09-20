package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {
	
	Logger log = Logger.getLogger(getClass());
	private boolean errorDetected = false;
	private boolean hasMainMethod = false;
	private boolean mainMethodHasPars = false;
	private boolean hasReturnStatement = false;
	private Struct currentType = Tab.noType;
	private Struct inadequateMethodType = Tab.noType;
	private Obj currentMethod = Tab.noObj;
	private Obj currentDesignator = Tab.noObj;
	private Obj currentAssignopDesignator = Tab.noObj;
	private String currentDesignatorName = "";
	private String currentArrayDesignatorName = "";
	private int doWhileCounter = 0;
	private List<Struct> actualFormParsList = new ArrayList<>();
	private List<List<Struct>> allActFormParsLists = new ArrayList<List<Struct>>();
	int numberOfVars;

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0) {
			msg.append(" na liniji ").append(line);
		}
		log.error(msg.toString());
	}
	
	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0) {
			msg.append(" na liniji ").append(line);
		}
		log.info(msg.toString());
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
	// Program
	
	@Override
	public void visit(ProgramName programName) {
		programName.obj = Tab.insert(Obj.Prog, programName.getProgramName(), Tab.noType);
		Tab.openScope();
	}
	
	@Override
	public void visit(Program program) {
		numberOfVars = Tab.currentScope().getnVars();
		Tab.chainLocalSymbols(program.getProgramName().obj);
		Tab.closeScope();
		
		if (hasMainMethod == false) {
			report_error("U programu ne postoji odgovarajuca main metoda", program);
		}
	}
	
	// Globalne deklarisane konstante
	
	@Override
	public void visit(ConstDeclElem_number constDeclElem_number) {
		Obj numConstObj = Tab.find(constDeclElem_number.getIdentName());
		if (numConstObj == Tab.noObj) { // ako unutar trenutnog opsega ne postoji konstanta sa istim imenom, onda je ubacujemo u tabelu simbola
			
			if (Tab.intType.assignableTo(currentType)) { // provera da li se vrednost promenljive moze dodeliti promenljivoj
				numConstObj = Tab.insert(Obj.Con, constDeclElem_number.getIdentName(), currentType);
				numConstObj.setAdr(constDeclElem_number.getValue());
			}
			else {
				report_error("[ConstDeclElem_number] Greska prilikom dodele vrednosti konstanti: " + constDeclElem_number.getIdentName() + ", zbog nepodudaranja tipova podataka", constDeclElem_number);
			}
		}
		else {
			report_error("[ConstDeclElem_number] Ime konstante je vec definisano unutar istog opsega: " + constDeclElem_number.getIdentName(), constDeclElem_number);
		} 
	}
	
	@Override
	public void visit(ConstDeclElem_character constDeclElem_character) {
		Obj charConstObj = Tab.find(constDeclElem_character.getIdentName());
		if (charConstObj == Tab.noObj) { // ako unutar trenutnog opsega ne postoji konstanta sa istim imenom, onda je ubacujemo u tabelu simbola
			
			if (Tab.charType.assignableTo(currentType)) { // provera da li se vrednost promenljive moze dodeliti promenljivoj
				charConstObj = Tab.insert(Obj.Con, constDeclElem_character.getIdentName(), currentType);
				charConstObj.setAdr(constDeclElem_character.getValue());
			}
			else {
				report_error("[ConstDeclElem_character] Greska prilikom dodele vrednosti konstanti: " + constDeclElem_character.getIdentName() + ", zbog nepodudaranja tipova podataka", constDeclElem_character);
			}
		}
		else {
			report_error("[ConstDeclElem_character] Ime konstante je vec definisano unutar istog opsega: " + constDeclElem_character.getIdentName(), constDeclElem_character);
		}
	}
	
	@Override
	public void visit(ConstDeclElem_bool constDeclElem_bool) {
		Obj boolConstObj = Tab.find(constDeclElem_bool.getIdentName());
		if (boolConstObj == Tab.noObj) { // ako unutar trenutnog opsega ne postoji konstanta sa istim imenom, onda je ubacujemo u tabelu simbola
			
			Struct boolType = Tab.find("bool").getType();
			
			if (boolType.assignableTo(currentType)) { // provera da li se vrednost promenljive moze dodeliti promenljivoj
				boolConstObj = Tab.insert(Obj.Con, constDeclElem_bool.getIdentName(), currentType);
				boolConstObj.setAdr(constDeclElem_bool.getValue());
			}
			else {
				report_error("[ConstDeclElem_bool] Greska prilikom dodele vrednosti konstanti: " + constDeclElem_bool.getIdentName() + ", zbog nepodudaranja tipova podataka", constDeclElem_bool);
			}
		}
		else {
			report_error("[ConstDeclElem_bool] Ime konstante je vec definisano unutar istog opsega: " + constDeclElem_bool.getIdentName(), constDeclElem_bool);
		} 
	}
	
	// Globalne deklarisane promenljive
	
	@Override
	public void visit(GlobalVarDeclElem_var globalVarDeclElem_var) {
		Obj varObj = Tab.find(globalVarDeclElem_var.getIdentName());
		if (varObj == Tab.noObj) { // ako unutar trenutnog opsega ne postoji promenljiva sa istim imenom, onda je ubacujemo u tabelu simbola
			varObj = Tab.insert(Obj.Var, globalVarDeclElem_var.getIdentName(), currentType);
		}
		else {
			report_error("[GlobalVarDeclElem_var] Ime promenljive je vec deklarisano unutar istog opsega: " + globalVarDeclElem_var.getIdentName(), globalVarDeclElem_var);
		} 
	}
	
	@Override
	public void visit(GlobalVarDeclElem_array globalVarDeclElem_array) {
		Obj varObj = Tab.find(globalVarDeclElem_array.getIdentName());
		if (varObj == Tab.noObj) { // ako unutar trenutnog opsega ne postoji promenljiva sa istim imenom, onda je ubacujemo u tabelu simbola
			varObj = Tab.insert(Obj.Var, globalVarDeclElem_array.getIdentName(), new Struct(Struct.Array, currentType));
		}
		else {
			report_error("[GlobalVarDeclElem_array] Ime promenljive je vec deklarisano unutar istog opsega: " + globalVarDeclElem_array.getIdentName(), globalVarDeclElem_array);
		} 
	}
	
	// Metode
	
		// Ime metode
	
	@Override
	public void visit(MethodName methodName) {		
		Obj methObj = Tab.find(methodName.getMethodName());
		if (methObj != Tab.noObj) {
			
			methodName.obj = Tab.noObj;
			methObj.setAdr(-2);
			currentMethod = methObj;
			
			if (methodName.getMethodName().equals("main")) {
				report_error("Dvostruka definicija main metode", methodName);
			}
			else {
				report_error("Dvostruka definicija metode: " + methodName.getMethodName(), methodName);
			}
		}
		else {
			if (methodName.getMethodName().equals("main") && !currentType.equals(Tab.noType)) {
				methodName.obj = Tab.noObj;
				methodName.obj.setAdr(-2);				
				currentMethod = methodName.obj;
				inadequateMethodType = currentType;
				
				report_error("Pokusaj neadekvatne definicije main metode", methodName);
			}
			else {
				if (methodName.getMethodName().equals("main") && currentType.equals(Tab.noType)) hasMainMethod = true;
				
				methodName.obj = currentMethod = Tab.insert(Obj.Meth, methodName.getMethodName(), currentType);
				Tab.openScope();
			}
		}
	}
	
	@Override
	public void visit(MethodDeclElemLeftBrace methodDeclElemLeftBrace) {
		if (currentMethod.getAdr() != -2) {
			Tab.chainLocalSymbols(currentMethod);
		}
	}
	
	@Override
	public void visit(MethodDeclElem methodDeclElem) {		
		if (currentMethod.getAdr() != -2) {
			// Tab.chainLocalSymbols(currentMethod);
			Tab.closeScope();
		} 
		
		if (currentMethod.getType() != Tab.noType && hasReturnStatement == false) {
			report_error("[MethodDeclElem] Ne postoji return iskaz unutar tela metode: " + currentMethod.getName(), methodDeclElem);
		}
		else if (inadequateMethodType != Tab.noType && hasReturnStatement == false) {
			report_error("[MethodDeclElem] Ne postoji return iskaz unutar tela metode: main koja je neadekvatno definisana", methodDeclElem);
		}
		
		currentMethod = null;
		hasReturnStatement = false;
		inadequateMethodType = Tab.noType;
	}
	
		// Povratni tip metode - void
	
	@Override 
	public void visit(MethodsType_void methodsType_void) {
		currentType = Tab.noType;
	}
	
		// Lokalne deklarisane promenljive
	
	@Override
	public void visit(LocalVarDeclElem_var localVarDeclElem_var) {
		Obj varObj = Tab.currentScope().findSymbol(localVarDeclElem_var.getIdentName());
		
		if (varObj == null) { // ako unutar trenutnog opsega ne postoji promenljiva sa istim imenom, onda je ubacujemo u tabelu simbola
			varObj = Tab.insert(Obj.Var, localVarDeclElem_var.getIdentName(), currentType);
		}
		else {
			report_error("[LocalVarDeclElem_var] Ime promenljive je vec deklarisano unutar iste metode: " + localVarDeclElem_var.getIdentName(), localVarDeclElem_var);
		} 
	}
	
	@Override
	public void visit(LocalVarDeclElem_array localVarDeclElem_array) {
		Obj varObj = Tab.currentScope().findSymbol(localVarDeclElem_array.getIdentName());
		
		if (varObj == null) { // ako unutar trenutnog opsega ne postoji promenljiva sa istim imenom, onda je ubacujemo u tabelu simbola
			varObj = Tab.insert(Obj.Var, localVarDeclElem_array.getIdentName(), new Struct(Struct.Array, currentType));
		}
		else {
			report_error("[LocalVarDeclElem_array] Ime promenljive je vec deklarisano unutar iste metode: " + localVarDeclElem_array.getIdentName(), localVarDeclElem_array);
		} 
	}
	
		// Formalni parametri metode
	
	@Override
	public void visit(FormParsElem_param formParsElem_param) {
		if (currentMethod != null) {
			Obj formParObj = Tab.currentScope().findSymbol(formParsElem_param.getIdentName());
			
			if (formParObj == null) { // ako unutar opsega metode ne postoji formalni parametar sa istim imenom, onda ga ubacujemo u tabelu simbola
				
				if (currentMethod.getName().equals("main")) {
					report_error("Pokusaj deklaracije formalnog parametra unutar main metode: " + formParsElem_param.getIdentName(), formParsElem_param);
				}
				else {
					formParObj = Tab.insert(Obj.Var, formParsElem_param.getIdentName(), currentType);
					formParObj.setFpPos(1);
					currentMethod.setLevel(currentMethod.getLevel() + 1);
				}
			}
			else {
				report_error("[FormParsElem_param] Ime formalnog parametra je vec deklarisano unutar iste metode: " + formParsElem_param.getIdentName(), formParsElem_param);
			}
		}
		else {
			report_error("Pokusaj deklaracije formalnog parametra unutar necega sto nije metoda", formParsElem_param);
		} 
	}
	
	@Override
	public void visit(FormParsElem_array_param formParsElem_array_param) {
		if (currentMethod != null) {
			Obj formParObj = Tab.currentScope().findSymbol(formParsElem_array_param.getIdentName());
			
			if (formParObj == null) { // ako unutar opsega metode ne postoji formalni parametar sa istim imenom, onda ga ubacujemo u tabelu simbola
				
				if (currentMethod.getName().equals("main")) {
					report_error("Pokusaj deklaracije formalnog parametra unutar main metode: " + formParsElem_array_param.getIdentName(), formParsElem_array_param);
				}
				else {
					formParObj = Tab.insert(Obj.Var, formParsElem_array_param.getIdentName(), new Struct(Struct.Array, currentType));
					formParObj.setFpPos(1);
					currentMethod.setLevel(currentMethod.getLevel() + 1);
				}
			}
			else {
				report_error("[FormParsElem_array_param] Ime formalnog parametra je vec deklarisano unutar iste metode: " + formParsElem_array_param.getIdentName(), formParsElem_array_param);
			}
		}
		else {
			report_error("Pokusaj deklaracije formalnog parametra unutar necega sto nije metoda", formParsElem_array_param);
		}	 
	}
	
	// Tip
	
	@Override
	public void visit(Type_type type_type) {
		Obj typeObj = Tab.find(type_type.getTypeName());
		
		if (typeObj == Tab.noObj) { // nepostojeci tip podataka
			type_type.struct = currentType = Tab.noType;
			report_error("[Type_type] Upotreba tipa podataka koji ne postoji: " + type_type.getTypeName(), type_type);
		}
		else if (typeObj.getKind() != Obj.Type) { // ako objektni cvor postoji, ali njegov kind nije deklarisan kao Type
			type_type.struct = currentType = Tab.noType;
			report_error("[Type_type] Cvor postoji, ali njegov kind nije deklarisan kao tip podataka (Type): " + type_type.getTypeName(), type_type);
		}
		else { // odgovarajuci tip
			type_type.struct = currentType = typeObj.getType();
		}
	}
	
	@Override
	public void visit(Type_set type_set) {
		Obj typeObj = Tab.find("set");
		
		if (typeObj == Tab.noObj) { // nepostojeci tip
			type_set.struct = currentType = Tab.noType;
			report_error("[Type_set] Upotreba tipa podataka koji ne postoji: ", type_set);
		}
		else if (typeObj.getKind() != Obj.Type) { // ako objektni cvor postoji, ali njegov kind nije deklarisan kao Type
			type_set.struct = currentType = Tab.noType;
			report_error("[Type_set] Cvor postoji, ali njegov kind nije deklarisan kao tip podataka (Type): set", type_set);
		}
		else { // odgovarajuci tip
			type_set.struct = currentType = typeObj.getType();
		}
	}
	
	// Designator
	
	@Override
	public void visit(DesignatorName designatorName) {
		Obj designatorObj = Tab.find(designatorName.getIdentName());
		
		if (designatorObj == Tab.noObj) {
			currentDesignatorName = designatorName.getIdentName();
			currentDesignator = Tab.noObj;
			designatorName.obj = Tab.noObj;
			report_error("[DesignatorName] Pristup promenljivoj koja nije deklarisana: " + designatorName.getIdentName(), designatorName);
		}
		else {
			currentDesignatorName = designatorName.getIdentName();
			if (designatorObj.getType().getKind() == Struct.Array) {
				currentDesignatorName = currentArrayDesignatorName = designatorName.getIdentName();
			}
			designatorName.obj = designatorObj;
			currentDesignator = designatorObj;
		}
	}
	
	@Override
	public void visit(DesignatorArrayName designatorArrayName) {
		Obj designatorObj = Tab.find(designatorArrayName.getIdentName());
		
		if (designatorObj == Tab.noObj) {
			currentDesignatorName = currentArrayDesignatorName = designatorArrayName.getIdentName();
			currentDesignator = Tab.noObj;
			designatorArrayName.obj = Tab.noObj;
			report_error("[DesignatorArrayName] Pristup promenljivoj koja nije deklarisana: " + designatorArrayName.getIdentName(), designatorArrayName);
		}
		else {
			currentDesignatorName = currentArrayDesignatorName = designatorArrayName.getIdentName();
			designatorArrayName.obj = designatorObj;
			currentDesignator = designatorObj;
		}
	}
	
	@Override
	public void visit(Designator designator) {
		Obj ident_var = designator.getDesignatorName().obj;
		
		if (ident_var == Tab.noObj) {
			currentDesignatorName = designator.getDesignatorName().getIdentName();
			designator.obj = Tab.noObj;
			currentDesignator = Tab.noObj;
		}
		else if (ident_var.getKind() == Obj.Var && ident_var.getType().getKind() == Struct.Array) {
			currentDesignatorName = currentArrayDesignatorName = designator.getDesignatorName().getIdentName(); 
			designator.obj = ident_var;
			currentDesignator = designator.obj;
			
			// detektovanje koriscenja simbola - koriscenje formalnog argumenta
			if (designator.getDesignatorName().obj.getKind() == Obj.Var && designator.getDesignatorName().obj.getLevel() == 1 && designator.getDesignatorName().obj.getFpPos() == 1) {
				if (currentMethod.getLocalSymbols().contains(designator.getDesignatorName().obj)) {
					Object[] allLocalSymbols = currentMethod.getLocalSymbols().toArray();
					int numOfFormPars = currentMethod.getLevel();
					for (int i = 0; i < numOfFormPars; i++) {
						if (((Obj)allLocalSymbols[i]).equals(designator.getDesignatorName().obj)) {
							report_info("Koriscenje formalnog argumenta: " + designator.getDesignatorName().obj.getName() + " funkcije: " + currentMethod.getName() + " - [Obj] Kind: " + designator.getDesignatorName().obj.getKind() + ", Type: " + designator.getDesignatorName().obj.getType().getElemType().getKind() + ", Adr: " + designator.getDesignatorName().obj.getAdr() + ", Level: " + designator.getDesignatorName().obj.getLevel() + ", FpPos: " + designator.getDesignatorName().obj.getFpPos(), designator);
						}
					}
				}
			}
			
			// detektovanje koriscenja simbola - koriscenje globalne promenljive
			if (designator.getDesignatorName().obj.getKind() == Obj.Var && designator.getDesignatorName().obj.getLevel() == 0) {
				report_info("Koriscenje globalne promenljive: " + designator.getDesignatorName().obj.getName() + " - [Obj] Kind: " + designator.getDesignatorName().obj.getKind() + ", Type: " + designator.getDesignatorName().obj.getType().getElemType().getKind() + ", Adr: " + designator.getDesignatorName().obj.getAdr() + ", Level: " + designator.getDesignatorName().obj.getLevel() + ", FpPos: " + designator.getDesignatorName().obj.getFpPos(), designator);
			}
			
			// detektovanje koriscenja simbola - koriscenje lokalne promenljive
			if (designator.getDesignatorName().obj.getKind() == Obj.Var && designator.getDesignatorName().obj.getLevel() == 1 && designator.getDesignatorName().obj.getFpPos() == 0) {
				if (currentMethod.getLocalSymbols().contains(designator.getDesignatorName().obj)) {
					Object[] allLocalSymbols = currentMethod.getLocalSymbols().toArray();
					int numOfFormPars = currentMethod.getLevel();
					for (int i = numOfFormPars; i < allLocalSymbols.length; i++) {
						if (((Obj)allLocalSymbols[i]).equals(designator.getDesignatorName().obj)) {
							report_info("Koriscenje lokalne promenljive: " + designator.getDesignatorName().obj.getName() + " funkcije: " + currentMethod.getName() + " - [Obj] Kind: " + designator.getDesignatorName().obj.getKind() + ", Type: " + designator.getDesignatorName().obj.getType().getElemType().getKind() + ", Adr: " + designator.getDesignatorName().obj.getAdr() + ", Level: " + designator.getDesignatorName().obj.getLevel() + ", FpPos: " + designator.getDesignatorName().obj.getFpPos(), designator);
						}
					}
				}
			}
		}
		else if (ident_var.getKind() == Obj.Var || ident_var.getKind() == Obj.Con || ident_var.getKind() == Obj.Meth) {
			currentDesignatorName = designator.getDesignatorName().getIdentName(); 
			designator.obj = ident_var;
			currentDesignator = designator.obj;
			
			// detektovanje koriscenja simbola - koriscenje formalnog argumenta
			if (designator.getDesignatorName().obj.getKind() == Obj.Var && designator.getDesignatorName().obj.getLevel() == 1 && designator.getDesignatorName().obj.getFpPos() == 1) {
				if (currentMethod.getLocalSymbols().contains(designator.getDesignatorName().obj)) {
					Object[] allLocalSymbols = currentMethod.getLocalSymbols().toArray();
					int numOfFormPars = currentMethod.getLevel();
					for (int i = 0; i < numOfFormPars; i++) {
						if (((Obj)allLocalSymbols[i]).equals(designator.getDesignatorName().obj)) {
							report_info("Koriscenje formalnog argumenta: " + designator.getDesignatorName().obj.getName() + " funkcije: " + currentMethod.getName() + " - [Obj] Kind: " + designator.getDesignatorName().obj.getKind() + ", Type: " + designator.getDesignatorName().obj.getType().getKind() + ", Adr: " + designator.getDesignatorName().obj.getAdr() + ", Level: " + designator.getDesignatorName().obj.getLevel() + ", FpPos: " + designator.getDesignatorName().obj.getFpPos(), designator);
						}
					}
				}
			}
			
			// detektovanje koriscenja simbola - koriscenje simbolicke konstante
			if (designator.getDesignatorName().obj.getKind() == Obj.Con) {
				report_info("Koriscenje simbolicke konstante: " + designator.getDesignatorName().obj.getName() + " - [Obj] Kind: " + designator.getDesignatorName().obj.getKind() + ", Type: " + designator.getDesignatorName().obj.getType().getKind() + ", Adr: " + designator.getDesignatorName().obj.getAdr() + ", Level: " + designator.getDesignatorName().obj.getLevel() + ", FpPos: " + designator.getDesignatorName().obj.getFpPos(), designator);
			}
			
			// detektovanje koriscenja simbola - koriscenje globalne promenljive
			if (designator.getDesignatorName().obj.getKind() == Obj.Var && designator.getDesignatorName().obj.getLevel() == 0) {
				report_info("Koriscenje globalne promenljive: " + designator.getDesignatorName().obj.getName() + " - [Obj] Kind: " + designator.getDesignatorName().obj.getKind() + ", Type: " + designator.getDesignatorName().obj.getType().getKind() + ", Adr: " + designator.getDesignatorName().obj.getAdr() + ", Level: " + designator.getDesignatorName().obj.getLevel() + ", FpPos: " + designator.getDesignatorName().obj.getFpPos(), designator);
			}
			
			// detektovanje koriscenja simbola - koriscenje lokalne promenljive
			if (designator.getDesignatorName().obj.getKind() == Obj.Var && designator.getDesignatorName().obj.getLevel() == 1 && designator.getDesignatorName().obj.getFpPos() == 0) {
				if (currentMethod.getLocalSymbols().contains(designator.getDesignatorName().obj)) {
					Object[] allLocalSymbols = currentMethod.getLocalSymbols().toArray();
					int numOfFormPars = currentMethod.getLevel();
					for (int i = numOfFormPars; i < allLocalSymbols.length; i++) {
						if (((Obj)allLocalSymbols[i]).equals(designator.getDesignatorName().obj)) {
							report_info("Koriscenje lokalne promenljive: " + designator.getDesignatorName().obj.getName() + " funkcije: " + currentMethod.getName() + " - [Obj] Kind: " + designator.getDesignatorName().obj.getKind() + ", Type: " + designator.getDesignatorName().obj.getType().getKind() + ", Adr: " + designator.getDesignatorName().obj.getAdr() + ", Level: " + designator.getDesignatorName().obj.getLevel() + ", FpPos: " + designator.getDesignatorName().obj.getFpPos(), designator);
						}
					}
				}
			}
			
			// detektovanje koriscenja simbola - poziv globalne funkcije
			if (designator.getDesignatorName().obj.getKind() == Obj.Meth) {
				report_info("Poziv globalne funkcije: " + designator.getDesignatorName().obj.getName() + " - [Obj] Kind: " + designator.getDesignatorName().obj.getKind() + ", Type: " + designator.getDesignatorName().obj.getType().getKind() + ", Level: " + designator.getDesignatorName().obj.getLevel() + ", Size of locals: " + designator.getDesignatorName().obj.getLocalSymbols().size(), designator);
			}
		}
		else {
			currentDesignatorName = designator.getDesignatorName().getIdentName();
			designator.obj = Tab.noObj;
			currentDesignator = Tab.noObj;
			report_error("[Designator] Pristup neadekvatnoj promenljivoj: " + currentDesignatorName, designator);
		}
	}
	
	@Override
	public void visit(DesignatorArray designatorArray) {
		Obj ident_var = designatorArray.getDesignatorArrayName().obj;
		
		if (ident_var == Tab.noObj) {
			currentDesignatorName = designatorArray.getDesignatorArrayName().getIdentName();
			designatorArray.obj = Tab.noObj;
			currentDesignator = Tab.noObj;
		}
		else if (ident_var.getKind() == Obj.Var && ident_var.getType().getKind() == Struct.Array) {
			
			if (designatorArray.getDesignatorArrayMore().obj != Tab.noObj) {
				currentDesignatorName = currentArrayDesignatorName = designatorArray.getDesignatorArrayName().getIdentName();
				designatorArray.obj = designatorArray.getDesignatorArrayMore().obj;
				currentDesignator = designatorArray.obj;
			}
			else {
				currentDesignatorName = currentArrayDesignatorName = designatorArray.getDesignatorArrayName().getIdentName(); 
				designatorArray.obj = ident_var;
				currentDesignator = designatorArray.obj;
			}
			
			// detektovanje koriscenja simbola - koriscenje formalnog argumenta
			if (designatorArray.getDesignatorArrayName().obj.getKind() == Obj.Var && designatorArray.getDesignatorArrayName().obj.getLevel() == 1 && designatorArray.getDesignatorArrayName().obj.getFpPos() == 1) {
				if (currentMethod.getLocalSymbols().contains(designatorArray.getDesignatorArrayName().obj)) {
					Object[] allLocalSymbols = currentMethod.getLocalSymbols().toArray();
					int numOfFormPars = currentMethod.getLevel();
					for (int i = 0; i < numOfFormPars; i++) {
						if (((Obj)allLocalSymbols[i]).equals(designatorArray.getDesignatorArrayName().obj)) {
							report_info("Koriscenje formalnog argumenta: " + designatorArray.getDesignatorArrayName().obj.getName() + " funkcije: " + currentMethod.getName() + " - [Obj] Kind: " + designatorArray.getDesignatorArrayName().obj.getKind() + ", Type: " + designatorArray.getDesignatorArrayName().obj.getType().getElemType().getKind() + ", Adr: " + designatorArray.getDesignatorArrayName().obj.getAdr() + ", Level: " + designatorArray.getDesignatorArrayName().obj.getLevel() + ", FpPos: " + designatorArray.getDesignatorArrayName().obj.getFpPos(), designatorArray);
						}
					}
				}
			}
			
			// detektovanje koriscenja simbola - koriscenje globalne promenljive
			if (designatorArray.getDesignatorArrayName().obj.getKind() == Obj.Var && designatorArray.getDesignatorArrayName().obj.getLevel() == 0) {
				report_info("Koriscenje globalne promenljive: " + designatorArray.getDesignatorArrayName().obj.getName() + " - [Obj] Kind: " + designatorArray.getDesignatorArrayName().obj.getKind() + ", Type: " + designatorArray.getDesignatorArrayName().obj.getType().getElemType().getKind() + ", Adr: " + designatorArray.getDesignatorArrayName().obj.getAdr() + ", Level: " + designatorArray.getDesignatorArrayName().obj.getLevel() + ", FpPos: " + designatorArray.getDesignatorArrayName().obj.getFpPos(), designatorArray);
			}
			
			// detektovanje koriscenja simbola - koriscenje lokalne promenljive
			if (designatorArray.getDesignatorArrayName().obj.getKind() == Obj.Var && designatorArray.getDesignatorArrayName().obj.getLevel() == 1 && designatorArray.getDesignatorArrayName().obj.getFpPos() == 0) {
				if (currentMethod.getLocalSymbols().contains(designatorArray.getDesignatorArrayName().obj)) {
					Object[] allLocalSymbols = currentMethod.getLocalSymbols().toArray();
					int numOfFormPars = currentMethod.getLevel();
					for (int i = numOfFormPars; i < allLocalSymbols.length; i++) {
						if (((Obj)allLocalSymbols[i]).equals(designatorArray.getDesignatorArrayName().obj)) {
							report_info("Koriscenje lokalne promenljive: " + designatorArray.getDesignatorArrayName().obj.getName() + " funkcije: " + currentMethod.getName() + " - [Obj] Kind: " + designatorArray.getDesignatorArrayName().obj.getKind() + ", Type: " + designatorArray.getDesignatorArrayName().obj.getType().getElemType().getKind() + ", Adr: " + designatorArray.getDesignatorArrayName().obj.getAdr() + ", Level: " + designatorArray.getDesignatorArrayName().obj.getLevel() + ", FpPos: " + designatorArray.getDesignatorArrayName().obj.getFpPos(), designatorArray);
						}
					}
				}
			}
		}
		else if (ident_var.getKind() == Obj.Var || ident_var.getKind() == Obj.Con || ident_var.getKind() == Obj.Meth) {
			
			if (designatorArray.getDesignatorArrayName().obj != Tab.noObj) {
				currentDesignatorName = designatorArray.getDesignatorArrayName().getIdentName(); 
				designatorArray.obj = designatorArray.getDesignatorArrayName().obj;
				currentDesignator = designatorArray.obj;
			}
			else {
				currentDesignatorName = designatorArray.getDesignatorArrayName().getIdentName(); 
				designatorArray.obj = ident_var;
				currentDesignator = designatorArray.obj;
			}
			
			// detektovanje koriscenja simbola - koriscenje formalnog argumenta
			if (designatorArray.getDesignatorArrayName().obj.getKind() == Obj.Var && designatorArray.getDesignatorArrayName().obj.getLevel() == 1 && designatorArray.getDesignatorArrayName().obj.getFpPos() == 1) {
				if (currentMethod.getLocalSymbols().contains(designatorArray.getDesignatorArrayName().obj)) {
					Object[] allLocalSymbols = currentMethod.getLocalSymbols().toArray();
					int numOfFormPars = currentMethod.getLevel();
					for (int i = 0; i < numOfFormPars; i++) {
						if (((Obj)allLocalSymbols[i]).equals(designatorArray.getDesignatorArrayName().obj)) {
							report_info("Koriscenje formalnog argumenta: " + designatorArray.getDesignatorArrayName().obj.getName() + " funkcije: " + currentMethod.getName() + " - [Obj] Kind: " + designatorArray.getDesignatorArrayName().obj.getKind() + ", Type: " + designatorArray.getDesignatorArrayName().obj.getType().getKind() + ", Adr: " + designatorArray.getDesignatorArrayName().obj.getAdr() + ", Level: " + designatorArray.getDesignatorArrayName().obj.getLevel() + ", FpPos: " + designatorArray.getDesignatorArrayName().obj.getFpPos(), designatorArray);
						}
					}
				}
			}
			
			// detektovanje koriscenja simbola - koriscenje simbolicke konstante
			if (designatorArray.getDesignatorArrayName().obj.getKind() == Obj.Con) {
				report_info("Koriscenje simbolicke konstante: " + designatorArray.getDesignatorArrayName().obj.getName() + " - [Obj] Kind: " + designatorArray.getDesignatorArrayName().obj.getKind() + ", Type: " + designatorArray.getDesignatorArrayName().obj.getType().getKind() + ", Adr: " + designatorArray.getDesignatorArrayName().obj.getAdr() + ", Level: " + designatorArray.getDesignatorArrayName().obj.getLevel() + ", FpPos: " + designatorArray.getDesignatorArrayName().obj.getFpPos(), designatorArray);
			}
			
			// detektovanje koriscenja simbola - koriscenje globalne promenljive
			if (designatorArray.getDesignatorArrayName().obj.getKind() == Obj.Var && designatorArray.getDesignatorArrayName().obj.getLevel() == 0) {
				report_info("Koriscenje globalne promenljive: " + designatorArray.getDesignatorArrayName().obj.getName() + " - [Obj] Kind: " + designatorArray.getDesignatorArrayName().obj.getKind() + ", Type: " + designatorArray.getDesignatorArrayName().obj.getType().getKind() + ", Adr: " + designatorArray.getDesignatorArrayName().obj.getAdr() + ", Level: " + designatorArray.getDesignatorArrayName().obj.getLevel() + ", FpPos: " + designatorArray.getDesignatorArrayName().obj.getFpPos(), designatorArray);
			}
			
			// detektovanje koriscenja simbola - koriscenje lokalne promenljive
			if (designatorArray.getDesignatorArrayName().obj.getKind() == Obj.Var && designatorArray.getDesignatorArrayName().obj.getLevel() == 1 && designatorArray.getDesignatorArrayName().obj.getFpPos() == 0) {
				if (currentMethod.getLocalSymbols().contains(designatorArray.getDesignatorArrayName().obj)) {
					Object[] allLocalSymbols = currentMethod.getLocalSymbols().toArray();
					int numOfFormPars = currentMethod.getLevel();
					for (int i = numOfFormPars; i < allLocalSymbols.length; i++) {
						if (((Obj)allLocalSymbols[i]).equals(designatorArray.getDesignatorArrayName().obj)) {
							report_info("Koriscenje lokalne promenljive: " + designatorArray.getDesignatorArrayName().obj.getName() + " funkcije: " + currentMethod.getName() + " - [Obj] Kind: " + designatorArray.getDesignatorArrayName().obj.getKind() + ", Type: " + designatorArray.getDesignatorArrayName().obj.getType().getKind() + ", Adr: " + designatorArray.getDesignatorArrayName().obj.getAdr() + ", Level: " + designatorArray.getDesignatorArrayName().obj.getLevel() + ", FpPos: " + designatorArray.getDesignatorArrayName().obj.getFpPos(), designatorArray);
						}
					}
				}
			}
			
			// detektovanje koriscenja simbola - poziv globalne funkcije
			if (designatorArray.getDesignatorArrayName().obj.getKind() == Obj.Meth) {
				report_info("Poziv globalne funkcije: " + designatorArray.getDesignatorArrayName().obj.getName() + " - [Obj] Kind: " + designatorArray.getDesignatorArrayName().obj.getKind() + ", Type: " + designatorArray.getDesignatorArrayName().obj.getType().getKind() + ", Level: " + designatorArray.getDesignatorArrayName().obj.getLevel() + ", Size of locals: " + designatorArray.getDesignatorArrayName().obj.getLocalSymbols().size(), designatorArray);
			}
		}
		else {
			currentDesignatorName = designatorArray.getDesignatorArrayName().getIdentName();
			designatorArray.obj = Tab.noObj;
			currentDesignator = Tab.noObj;
			report_error("[DesignatorArray] Pristup neadekvatnoj promenljivoj: " + currentDesignatorName, designatorArray);
		}
	}
	
	@Override
	public void visit(DesignatorArrayMore_more designatorArrayMore_more) {
		Obj arrayObj = Tab.find(currentArrayDesignatorName);
		
		if (arrayObj.equals(Tab.noObj)) {
			designatorArrayMore_more.obj = Tab.noObj;
			currentDesignator = Tab.noObj;
		}
		else if (arrayObj.getKind() == Obj.Var && arrayObj.getType().getKind() == Struct.Array) {
			
			if (designatorArrayMore_more.getExpr().struct != null) {
				if (designatorArrayMore_more.getExpr().struct.equals(Tab.intType)) { // provera da li je broj kojim se indeksira tipa int
					designatorArrayMore_more.obj = new Obj(Obj.Elem, "element niza " + arrayObj.getName(), arrayObj.getType().getElemType());
					currentDesignator = designatorArrayMore_more.obj;
					
					// detektovanje koriscenja simbola - pristup elementu niza
					report_info("Pristup elementu niza: " + arrayObj.getName() + " - [Obj] Kind: " + designatorArrayMore_more.obj.getKind() + ", Type of element: " + arrayObj.getType().getElemType().getKind(), designatorArrayMore_more);
				}
				else {
					designatorArrayMore_more.obj = Tab.noObj;
					currentDesignator = Tab.noObj;
					report_error("[DesignatorArrayMore_more] Pokusaj pristupa elementu niza sa indeksom koji nije celobrojnog tipa", designatorArrayMore_more);
				}
			}
		}
		else { // ako se ne pristupa nizu
			designatorArrayMore_more.obj = Tab.noObj;
			currentDesignator = Tab.noObj;
			report_error("[DesignatorArrayMore_more] Greska prilikom pristupa elementu necega sto nije niz: " + arrayObj.getName(), designatorArrayMore_more);
		}
	}
	
	@Override
	public void visit(DesignatorOption_designator designatorOption_designator) {
		designatorOption_designator.obj = designatorOption_designator.getDesignator().obj;
	}
	
	@Override
	public void visit(DesignatorOption_designator_array designatorOption_designator_array) {
		designatorOption_designator_array.obj = designatorOption_designator_array.getDesignatorArray().obj;
	}
	
	// DesignatorStatement
	
		// assignop
	
	@Override
	public void visit(DesignatorStatementAssignop_expr designatorStatementAssignop_expr) {
		currentAssignopDesignator = designatorStatementAssignop_expr.getDesignatorOption().obj;
		
		if (currentAssignopDesignator.getKind() != Obj.Var && currentAssignopDesignator.getKind() != Obj.Elem) {
			report_error("[DesignatorStatementAssignop_expr] Dodela vrednosti u neadekvatnu promenljivu: " + currentAssignopDesignator.getName(), designatorStatementAssignop_expr);
		}
		else {
			if (designatorStatementAssignop_expr.getExpr().struct != null) {
				if(!designatorStatementAssignop_expr.getExpr().struct.assignableTo(currentAssignopDesignator.getType()) &&			// MOZDA OVDE TREBA ILI ???
				   !(currentAssignopDesignator.getType().getKind() == Struct.Interface && designatorStatementAssignop_expr.getExpr().struct.getKind() == Struct.Interface)) { 
					report_error("[DesignatorStatementAssignop_expr] Greska prilikom dodele vrednosti promenljivoj: " + designatorStatementAssignop_expr.getDesignatorOption().obj.getName() + ", zbog nepodudaranja tipova podataka", designatorStatementAssignop_expr);
				}
			}
		}
	}
	
	@Override
	public void visit(DesignatorStatementAssignop_setop_union designatorStatementAssignop_setop_union) {
		Struct setType = Tab.find("set").getType();
		
		if (!(designatorStatementAssignop_setop_union.getDesignatorOption().obj.getType().equals(setType) &&
			designatorStatementAssignop_setop_union.getDesignatorOption1().obj.getType().equals(setType) && 
			designatorStatementAssignop_setop_union.getDesignatorOption2().obj.getType().equals(setType))) {
			report_error("[DesignatorStatementAssignop_setop_union] Greska prilikom dodele vrednosti promenljivoj: " + designatorStatementAssignop_setop_union.getDesignatorOption().obj.getName() + ", zbog nepodudaranja tipova podataka", designatorStatementAssignop_setop_union);
		}
	}
	
		// inc
	
	@Override
	public void visit(DesignatorStatement_inc designatorStatement_inc) {
		if (currentDesignator.getKind() != Obj.Var && currentDesignator.getKind() != Obj.Elem) {
			report_error("[DesignatorStatement_inc] Pokusaj inkrementiranja neadekvatne promenljive: " + currentDesignatorName, designatorStatement_inc);
		}
		else if (!currentDesignator.getType().equals(Tab.intType)) {
			report_error("[DesignatorStatement_inc] Pokusaj inkrementiranja promenljive koja nije celobrojnog tipa: " + currentDesignatorName, designatorStatement_inc);
		}
	}
	
		// dec
	
	@Override
	public void visit(DesignatorStatement_dec designatorStatement_dec) {
		if (currentDesignator.getKind() != Obj.Var && currentDesignator.getKind() != Obj.Elem) {
			report_error("[DesignatorStatement_dec] Pokusaj dekrementiranja neadekvatne promenljive: " + currentDesignator.getName(), designatorStatement_dec);
		}
		else if (!currentDesignator.getType().equals(Tab.intType)) {
			report_error("[DesignatorStatement_dec] Pokusaj dekrementiranja promenljive koja nije celobrojnog tipa: " + currentDesignator.getName(), designatorStatement_dec);
		}
	}
	
		// metode
	
	@Override
	public void visit(DesignatorStatement_no_params designatorStatement_no_params) {
		if (designatorStatement_no_params.getDesignatorOption().obj.getKind() == Obj.Meth) {
			
			Obj designatorMeth = designatorStatement_no_params.getDesignatorOption().obj;
			List<Struct> formParsList = new ArrayList<>();
			for (Obj currLocal: designatorMeth.getLocalSymbols()) {
				if (currLocal.getKind() == Obj.Var && currLocal.getLevel() == 1 && currLocal.getFpPos() == 1) { 
					formParsList.add(currLocal.getType());
				}
			}
			
			if (formParsList.size() != actualFormParsList.size()) {
				report_error("[DesignatorStatement_no_params] Nekompatibilnost parametara pri pozivu metode: " + designatorStatement_no_params.getDesignatorOption().obj.getName() + " - [Greska u broju parametara]", designatorStatement_no_params);
			}
			else {
				for (int i = 0; i < actualFormParsList.size(); i++) {
					if (!actualFormParsList.get(i).assignableTo(formParsList.get(i))) {
						report_error("[DesignatorStatement_no_params] Nekompatibilnost parametara pri pozivu metode: " + designatorStatement_no_params.getDesignatorOption().obj.getName() + " - [Greska u tipovima parametara]", designatorStatement_no_params);
						break;
					}
				}
				
				// detektovanje koriscenja simbola - poziv globalne funkcije
				report_info("Poziv globalne funkcije: " + designatorMeth.getName() + " - [Obj] Kind: " + designatorMeth.getKind() + ", Type: " + designatorMeth.getType().getKind() + ", Level: " + designatorMeth.getLevel() + ", Size of locals: " + designatorMeth.getLocalSymbols().size(), designatorStatement_no_params);
			}
			
			actualFormParsList = new ArrayList<Struct>();
		}
		else {
			report_error("[DesignatorStatement_no_params] Poziv necega sto nije deklarisano kao globalna funkcija glavnog programa: " + designatorStatement_no_params.getDesignatorOption().obj.getName(), designatorStatement_no_params);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_params designatorStatement_params) {
		if (designatorStatement_params.getDesignatorOption().obj.getKind() == Obj.Meth) {
			
			Obj designatorMeth = designatorStatement_params.getDesignatorOption().obj;
			List<Struct> formParsList = new ArrayList<>();
			for (Obj currLocal: designatorMeth.getLocalSymbols()) {
				if (currLocal.getKind() == Obj.Var && currLocal.getLevel() == 1 && currLocal.getFpPos() == 1) {
					formParsList.add(currLocal.getType());
				}
			}
			
			// chr(e)
			if (designatorStatement_params.getDesignatorOption().obj.getName().equals("chr") &&
				designatorStatement_params.getDesignatorOption().obj.getType().getKind() == Struct.Char &&
				formParsList.size() == 1) {

				if (actualFormParsList.size() != 1) {
					report_error("Nekompatibilnost parametara pri pozivu metode: " + designatorStatement_params.getDesignatorOption().obj.getName() + " - [Greska u broju parametara]", designatorStatement_params);
				}
				else {
					if (actualFormParsList.get(0).getKind() != Struct.Int) {
						report_error("Nekompatibilnost parametara pri pozivu metode: " + designatorStatement_params.getDesignatorOption().obj.getName() + " - [Greska u tipovima parametara]", designatorStatement_params);
					}
					else {
						// detektovanje koriscenja simbola - poziv globalne funkcije
						report_info("Poziv globalne funkcije: " + designatorMeth.getName() + " - [Obj] Kind: " + designatorMeth.getKind() + ", Type: " + designatorMeth.getType().getKind() + ", Level: " + designatorMeth.getLevel() + ", Size of locals: " + designatorMeth.getLocalSymbols().size(), designatorStatement_params);
					}
				}
			}
			// ord(c)
			else if (designatorStatement_params.getDesignatorOption().obj.getName().equals("ord") && 
					designatorStatement_params.getDesignatorOption().obj.getType().getKind() == Struct.Int &&
					formParsList.size() == 1) {
				if (actualFormParsList.size() != 1) {
					report_error("Nekompatibilnost parametara pri pozivu metode: " + designatorStatement_params.getDesignatorOption().obj.getName() + " - [Greska u broju parametara]", designatorStatement_params);
				}
				else {
					if (actualFormParsList.get(0).getKind() != Struct.Char) {
						report_error("Nekompatibilnost parametara pri pozivu metode: " + designatorStatement_params.getDesignatorOption().obj.getName() + " - [Greska u tipovima parametara]", designatorStatement_params);
					}
					else {
						// detektovanje koriscenja simbola - poziv globalne funkcije
						report_info("Poziv globalne funkcije: " + designatorMeth.getName() + " - [Obj] Kind: " + designatorMeth.getKind() + ", Type: " + designatorMeth.getType().getKind() + ", Level: " + designatorMeth.getLevel() + ", Size of locals: " + designatorMeth.getLocalSymbols().size(), designatorStatement_params);
					}
				}
			}
			// len(a)
			else if (designatorStatement_params.getDesignatorOption().obj.getName().equals("len") && 
					designatorStatement_params.getDesignatorOption().obj.getType().getKind() == Struct.Int &&
					formParsList.size() == 1) {
				if (actualFormParsList.size() != 1) {
					report_error("Nekompatibilnost parametara pri pozivu metode: " + designatorStatement_params.getDesignatorOption().obj.getName() + " - [Greska u broju parametara]", designatorStatement_params);
				}
				else {
					if (actualFormParsList.get(0).getKind() != Struct.Array) {
						report_error("Nekompatibilnost parametara pri pozivu metode: " + designatorStatement_params.getDesignatorOption().obj.getName() + " - [Greska u tipovima parametara]", designatorStatement_params);
					}
					else {
						// detektovanje koriscenja simbola - poziv globalne funkcije
						report_info("Poziv globalne funkcije: " + designatorMeth.getName() + " - [Obj] Kind: " + designatorMeth.getKind() + ", Type: " + designatorMeth.getType().getKind() + ", Level: " + designatorMeth.getLevel() + ", Size of locals: " + designatorMeth.getLocalSymbols().size(), designatorStatement_params);
					}
				}
			}
			// add(a, b)
			else if (designatorStatement_params.getDesignatorOption().obj.getName().equals("add") && 
					designatorStatement_params.getDesignatorOption().obj.getType().getKind() == Struct.None &&
					formParsList.size() == 2) {
				if (actualFormParsList.size() != 2) {
					report_error("Nekompatibilnost parametara pri pozivu metode: " + designatorStatement_params.getDesignatorOption().obj.getName() + " - [Greska u broju parametara]", designatorStatement_params);
				}
				else {
					if (actualFormParsList.get(0).getKind() != Struct.Interface || actualFormParsList.get(1).getKind() != Struct.Int) {
						report_error("Nekompatibilnost parametara pri pozivu metode: " + designatorStatement_params.getDesignatorOption().obj.getName() + " - [Greska u tipovima parametara]", designatorStatement_params);
					}
					else {
						// detektovanje koriscenja simbola - poziv globalne funkcije
						report_info("Poziv globalne funkcije: " + designatorMeth.getName() + " - [Obj] Kind: " + designatorMeth.getKind() + ", Type: " + designatorMeth.getType().getKind() + ", Level: " + designatorMeth.getLevel() + ", Size of locals: " + designatorMeth.getLocalSymbols().size(), designatorStatement_params);
					}
				}
			}
			// addAll(a, b)
			else if (designatorStatement_params.getDesignatorOption().obj.getName().equals("addAll") && 
					designatorStatement_params.getDesignatorOption().obj.getType().getKind() == Struct.None &&
					formParsList.size() == 2) {
				if (actualFormParsList.size() != 2) {
					report_error("Nekompatibilnost parametara pri pozivu metode: " + designatorStatement_params.getDesignatorOption().obj.getName() + " - [Greska u broju parametara]", designatorStatement_params);
				}
				else {
					if (actualFormParsList.get(0).getKind() != Struct.Interface || actualFormParsList.get(1).getKind() != Struct.Array || actualFormParsList.get(1).getElemType() != Tab.intType) {
						report_error("Nekompatibilnost parametara pri pozivu metode: " + designatorStatement_params.getDesignatorOption().obj.getName() + " - [Greska u tipovima parametara]", designatorStatement_params);
					}
					else {
						// detektovanje koriscenja simbola - poziv globalne funkcije
						report_info("Poziv globalne funkcije: " + designatorMeth.getName() + " - [Obj] Kind: " + designatorMeth.getKind() + ", Type: " + designatorMeth.getType().getKind() + ", Level: " + designatorMeth.getLevel() + ", Size of locals: " + designatorMeth.getLocalSymbols().size(), designatorStatement_params);
					}
				}
			}
			else {
				if (formParsList.size() != actualFormParsList.size()) {
					report_error("[DesignatorStatement_params] Nekompatibilnost parametara pri pozivu metode: " + designatorStatement_params.getDesignatorOption().obj.getName() + " - [Greska u broju parametara]", designatorStatement_params);
				}
				else {
					for (int i = 0; i < actualFormParsList.size(); i++) {
						if (actualFormParsList.get(i).assignableTo(formParsList.get(i)) == false) {
							report_error("[DesignatorStatement_params] Nekompatibilnost parametara pri pozivu metode: " + designatorStatement_params.getDesignatorOption().obj.getName() + " - [Greska u tipovima parametara]", designatorStatement_params);
							break;
						}
					}
					// detektovanje koriscenja simbola - poziv globalne funkcije
					report_info("Poziv globalne funkcije: " + designatorMeth.getName() + " - [Obj] Kind: " + designatorMeth.getKind() + ", Type: " + designatorMeth.getType().getKind() + ", Level: " + designatorMeth.getLevel() + ", Size of locals: " + designatorMeth.getLocalSymbols().size(), designatorStatement_params);
				}
			}
			
			actualFormParsList = new ArrayList<Struct>();
		}
		else {
			report_error("[DesignatorStatement_params] Poziv necega sto nije deklarisano kao globalna funkcija glavnog programa: " + designatorStatement_params.getDesignatorOption().obj.getName(), designatorStatement_params);
		}
	}
	
	// Statement
	
		// do while
	
	@Override
	public void visit(StartOfDoWhileLoop startOfDoWhileLoop) {
		doWhileCounter++;
	}
	
	@Override
	public void visit(Statement_do_while statement_do_while) {
		doWhileCounter--;
	}
	
	@Override
	public void visit(Statement_do_while_condition statement_do_while_condition) {
		doWhileCounter--;
	}
	
	@Override
	public void visit(Statement_do_while_condition_comma_statement statement_do_while_condition_comma_statement) {
		doWhileCounter--;
	}
	
		// break
	
	@Override
	public void visit(Statement_break statement_break) {
		if (doWhileCounter == 0) {
			report_error("Break naredba se ne nalazi unutar do-while petlje.", statement_break);
		}
	}
	
		// continue
	
	@Override
	public void visit(Statement_continue statement_continue) {
		if (doWhileCounter == 0) {
			report_error("Continue naredba se ne nalazi unutar do-while petlje.", statement_continue);
		}
	}
	
		// read
	
	@Override
	public void visit(Statement_read statement_read) {
		Struct boolType = Tab.find("bool").getType();
		
		if (currentDesignator.getKind() != Obj.Var && currentDesignator.getKind() != Obj.Elem) {
			report_error("Read neadekvatne promenljive: " + currentDesignator.getName(), statement_read);
		}
		else if (!currentDesignator.getType().equals(Tab.intType) && !currentDesignator.getType().equals(Tab.charType) && !currentDesignator.getType().equals(boolType)) {
			report_error("Pokusaj operacije read nad promenljivom koja nije int, char ili bool tipa: " + currentDesignator.getName(), statement_read);
		}
	} 
	
		// print
	
	@Override
	public void visit(Statement_print_expr statement_print_expr) {
		Struct boolType = Tab.find("bool").getType();
		Struct exprType = statement_print_expr.getExpr().struct;
		
		if (!exprType.equals(Tab.intType) && !exprType.equals(Tab.charType) && !exprType.equals(boolType) && exprType.getKind() != Struct.Interface) {
			report_error("Pokusaj operacije print nad promenljivom koja nije set niti int, char, bool tipa: ", statement_print_expr);
		}
	}
	
	@Override
	public void visit(Statement_print_expr_comma_number statement_print_expr_comma_number) {
		Struct boolType = Tab.find("bool").getType();
		Struct exprType = statement_print_expr_comma_number.getExpr().struct;
		
		if (!exprType.equals(Tab.intType) && !exprType.equals(Tab.charType) && !exprType.equals(boolType) && exprType.getKind() != Struct.Interface) {
			report_error("Pokusaj operacije print nad promenljivom koja nije set niti int, char, bool tipa: ", statement_print_expr_comma_number);
		}
	}
	
		// return
	
	@Override
	public void visit(Statement_return statement_return) {
		hasReturnStatement = true;
		if (currentMethod.getType() != Tab.noType) {
			report_error("[Statement_return] Nepoklapanje povratnog tipa metode i vracene vrednosti unutar metode: " + currentMethod.getName(), statement_return);
		}
	}
	
	@Override
	public void visit(Statement_return_expr statement_return_expr) {		
		if (currentMethod == null) {
			report_error("[Statement_return_expr] Return iskaz se nalazi izvan tela metode: " + currentMethod.getName(), statement_return_expr);
		}
		else {
			hasReturnStatement = true;
			if (!currentMethod.getType().equals(statement_return_expr.getExpr().struct)) {
				report_error("[Statement_return_expr] Nepoklapanje povratnog tipa metode i vracene vrednosti unutar metode: " + currentMethod.getName(), statement_return_expr);
			}
		}
	}
	
	// Actual Parameters
	
	@Override
	public void visit(StartOfActParsList startOfActParsList) {
		allActFormParsLists.add(new ArrayList<>());
	}
	
	@Override
	public void visit(ActPar actPar) {
		allActFormParsLists.get(allActFormParsLists.size() - 1).add(actPar.getExpr().struct);
	}
	
	@Override
	public void visit(ActParsList actParsList) {
		actualFormParsList = allActFormParsLists.remove(allActFormParsLists.size() - 1);
	}
	
	@Override
	public void visit(NoActParsList noActParsList) {
		actualFormParsList = allActFormParsLists.remove(allActFormParsLists.size() - 1);
	}	
	
	// Conditions
	
	@Override
	public void visit(CondFact_expr condFact_expr) {
		Struct boolType = Tab.find("bool").getType();
		
		if (condFact_expr.getExpr().struct.equals(boolType)) {
			condFact_expr.struct = boolType;
		}
		else {
			condFact_expr.struct = Tab.noType;
			report_error("[CondFact_expr] Logicki izraz nije tipa bool", condFact_expr);
		}
	}
	
	@Override
	public void visit(CondFact_expr_relop_expr condFact_expr_relop_expr) {
		Struct boolType = Tab.find("bool").getType();
		
		// oba expr moraju da budu compatible da bi se mogli porediti
		if (condFact_expr_relop_expr.getExpr().struct.compatibleWith(condFact_expr_relop_expr.getExpr1().struct)) {
			
			if (condFact_expr_relop_expr.getExpr().struct.getKind() == Struct.Array || condFact_expr_relop_expr.getExpr1().struct.getKind() == Struct.Array) {
				if (condFact_expr_relop_expr.getRelop() instanceof Relop_is_equal || condFact_expr_relop_expr.getRelop() instanceof Relop_is_not_equal) {
					condFact_expr_relop_expr.struct = boolType;
				}
				else {
					condFact_expr_relop_expr.struct = Tab.noType;
					report_error("[CondFact_expr_relop_expr] Greska zbog poredjenja nizova sa neadekvatnim relacionim operatorom", condFact_expr_relop_expr);
				}
			}
			else {
				condFact_expr_relop_expr.struct = boolType;
			}
		}
		else {
			condFact_expr_relop_expr.struct = Tab.noType;
			report_error("[CondFact_expr_relop_expr] Logicki izrazi nisu kompatibilni za poredjenje", condFact_expr_relop_expr);
		}
	}
	
	@Override
	public void visit(CondTermListMore_more condTermListMore_more) {
		Struct boolType = Tab.find("bool").getType();
		
		if (condTermListMore_more.getCondFact().struct.equals(boolType)) {
			if (condTermListMore_more.getCondTermListMore() instanceof CondTermListMore_more) {
				if (condTermListMore_more.getCondTermListMore().struct.equals(boolType)) {
					condTermListMore_more.struct = boolType;
				}
				else {
					condTermListMore_more.struct = Tab.noType;
				}
			}
			else {
				condTermListMore_more.struct = boolType;
			}
		}
		else {
			condTermListMore_more.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(CondTermList condTermList) {
		Struct boolType = Tab.find("bool").getType();
		
		if (condTermList.getCondFact().struct.equals(boolType)) {
			if (condTermList.getCondTermListMore() instanceof CondTermListMore_more) {
				if (condTermList.getCondTermListMore().struct.equals(boolType)) {
					condTermList.struct = boolType;
				}
				else {
					condTermList.struct = Tab.noType;
				}
			}
			else {
				condTermList.struct = boolType;
			}
		}
		else {
			condTermList.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(ConditionListMore_more conditionListMore_more) {
		Struct boolType = Tab.find("bool").getType();
		
		if (conditionListMore_more.getCondTermList().struct.equals(boolType)) {
			if (conditionListMore_more.getConditionListMore() instanceof ConditionListMore_more) {
				if (conditionListMore_more.getConditionListMore().struct.equals(boolType)) {
					conditionListMore_more.struct = boolType;
				}
				else {
					conditionListMore_more.struct = Tab.noType;
				}
			}
			else {
				conditionListMore_more.struct = boolType;
			}
		}
		else {
			conditionListMore_more.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(ConditionList conditionList) {
		Struct boolType = Tab.find("bool").getType();
		
		if (conditionList.getCondTermList().struct.equals(boolType)) {
			if (conditionList.getConditionListMore() instanceof ConditionListMore_more) {
				if (conditionList.getConditionListMore().struct.equals(boolType)) {
					conditionList.struct = boolType;
				}
				else {
					conditionList.struct = Tab.noType;
				}
			}
			else {
				conditionList.struct = boolType;
			}
		}
		else {
			conditionList.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(IfConditionList_if ifConditionList_if) {
		Struct boolType = Tab.find("bool").getType();
		
		if (ifConditionList_if.getConditionList().struct.equals(boolType)) {
			ifConditionList_if.struct = boolType;
		}
		else {
			ifConditionList_if.struct = Tab.noType;
		}
	}
	
	// Expr
	
	@Override
	public void visit(MinusTerm minusTerm) {
		if (minusTerm.getTerm().struct.equals(Tab.intType)) {
			minusTerm.struct = Tab.intType;
		}
		else {
			minusTerm.struct = Tab.noType; 
			report_error("[MinusTerm] Pokusaj negacije vrednosti promenljive koja nije celobrojnog tipa", minusTerm);
		}
	}
		
	@Override
	public void visit(Expr_minus expr_minus) {
		if (expr_minus.getMinusTerm().struct.equals(Tab.intType)) {
			if (expr_minus.getExprMore() instanceof ExprMore_more) {
				if (expr_minus.getExprMore().struct.equals(Tab.intType)) {
					expr_minus.struct = Tab.intType;
				}
				else {
					expr_minus.struct = Tab.noType;
				}
			}
			else {
				expr_minus.struct = Tab.intType;
			}
		}
		else {
			if (expr_minus.getExprMore() instanceof ExprMore_more) {
				expr_minus.struct = Tab.noType;
				report_error("[Expr_minus] Pokusaj operacije ADDOP sa vrednostima koje nisu celobrojnog tipa", expr_minus);
			}
			else {
				expr_minus.struct = expr_minus.getMinusTerm().struct;				// ZASTO SAM OVO STAVILA? Nesto sa nizovima?
			}
		}
	}
	
	@Override
	public void visit(Expr_term expr_term) {
		if (expr_term.getTerm().struct.equals(Tab.intType)) {
			if (expr_term.getExprMore() instanceof ExprMore_more) {
				if (expr_term.getExprMore().struct.equals(Tab.intType)) {
					expr_term.struct = Tab.intType;
				}
				else {
					expr_term.struct = Tab.noType;
				}
			}
			else {
				expr_term.struct = Tab.intType;
			}
		}
		else {
			if (expr_term.getExprMore() instanceof ExprMore_more) {
				expr_term.struct = Tab.noType;
				report_error("[Expr_term] Pokusaj operacije ADDOP sa vrednostima koje nisu celobrojnog tipa", expr_term);
			}
			else {
				expr_term.struct = expr_term.getTerm().struct;					// ZASTO SAM OVO STAVILA? Nesto sa nizovima?
			}
		}
	}
	
	@Override
	public void visit(ExprMore_more exprMore_more) {
		if (exprMore_more.getTerm().struct.equals(Tab.intType)) {
			if (exprMore_more.getExprMore() instanceof ExprMore_more) {
				if (exprMore_more.getExprMore().struct.equals(Tab.intType)) {
					if (exprMore_more.getTerm().struct.compatibleWith(exprMore_more.getExprMore().struct)) {
						exprMore_more.struct = Tab.intType;
					}
					else {
						exprMore_more.struct = Tab.noType;
						report_error("[ExprMore_more] Pokusaj operacije ADDOP sa vrednostima koje nisu kompatibilne", exprMore_more);	// BRISI
					}
				}
				else {
					exprMore_more.struct = Tab.noType;
				}
			}
			else {
				exprMore_more.struct = Tab.intType;
			}
		}
		else {
			exprMore_more.struct = Tab.noType;
			report_error("[ExprMore_more] Pokusaj operacije ADDOP sa vrednostima koje nisu celobrojnog tipa", exprMore_more);	// BRISI
		}
	}
	
	@Override
	public void visit(Expr_map expr_map) {
		Obj leftDesignator = expr_map.getDesignatorOption().obj;
		Obj rightDesignator = expr_map.getDesignatorOption1().obj;
		
		if (leftDesignator.getKind() == Obj.Meth && leftDesignator.getType().equals(Tab.intType)) {
			
			List<Struct> formParsList = new ArrayList<>();
			for (Obj currLocal: leftDesignator.getLocalSymbols()) {
				if (currLocal.getKind() == Obj.Var && currLocal.getLevel() == 1 && currLocal.getFpPos() == 1) { 
					formParsList.add(currLocal.getType());
				}
			}
			
			if (formParsList.size() == 1) {
				if (formParsList.get(0).getKind() == Struct.Int) { // sve je u redu sa levim designatorom
					// provera za desni designator
					if (rightDesignator.getType().getKind() == Struct.Array && rightDesignator.getType().getElemType().equals(Tab.intType)) {
						expr_map.struct = Tab.intType;
					}
					else {
						expr_map.struct = Tab.noType;
						report_error("[Expr_map] Pokusaj operacije map sa neadekvatnom promenljivom (desnom): " + rightDesignator.getName() + " [Treba biti niz celobrojnih vrednosti]", expr_map);
					}
				}
				else {
					expr_map.struct = Tab.noType;
					report_error("[Expr_map] Pokusaj operacije map sa neadekvatnom promenljivom (levom): " + leftDesignator.getName() + " [Treba biti funkcija ciji je jedini parametar celobrojnog tipa]", expr_map);
				}
			}
			else {
				expr_map.struct = Tab.noType;
				report_error("[Expr_map] Pokusaj operacije map sa neadekvatnom promenljivom (levom): " + leftDesignator.getName() + " [Treba biti funkcija koja prima jedan parametar]", expr_map);
			}
		}
		else {
			expr_map.struct = Tab.noType;
			report_error("[Expr_map] Pokusaj operacije map sa neadekvatnom promenljivom (levom): " + leftDesignator.getName() + " [Treba biti funkcija cija je povratna vrednost celobrojnog tipa]", expr_map);
		}
	}
	
	// Term

	@Override
	public void visit(Term term) {
		if (term.getFactor().struct.equals(Tab.intType)) {
			if (term.getTermMore() instanceof TermMore_more) {
				if (term.getTermMore().struct.equals(Tab.intType)) {
					term.struct = Tab.intType;
				}
				else {
					term.struct = Tab.noType;
				}
			}
			else {
				term.struct = Tab.intType;
			}
		}
		else {
			if (term.getTermMore() instanceof TermMore_more) {
				term.struct = Tab.noType;
				report_error("[Term] Pokusaj operacije MULOP sa vrednostima koje nisu celobrojnog tipa", term);
			}
			else {
				term.struct = term.getFactor().struct;			// ZASTO OVO???
			}
		}
	}
		
	@Override
	public void visit(TermMore_more termMore_more) {
		if (termMore_more.getFactor().struct.equals(Tab.intType)) {
			if (termMore_more.getTermMore() instanceof TermMore_more) {
				if (termMore_more.getTermMore().struct.equals(Tab.intType)) {
					termMore_more.struct = Tab.intType;
				}
				else {
					termMore_more.struct = Tab.noType;
				}
			}
			else {
				termMore_more.struct = Tab.intType;
			}
		}
		else {
			termMore_more.struct = Tab.noType;
			report_error("[TermMore_more] Pokusaj operacije MULOP sa vrednostima koje nisu celobrojnog tipa", termMore_more);
		}
	}
	
	// Factor
	
	@Override
	public void visit(Factor_designator factor_designator) {
		if (factor_designator.getDesignatorOption().obj != null) { // provera u uslovu i razlicito od notype???
			factor_designator.struct = factor_designator.getDesignatorOption().obj.getType();
		}
		else {
			factor_designator.struct = Tab.noType;
		}	
	}
	
	@Override
	public void visit(Factor_designator_fun_no_params factor_designator_fun_no_params) {
		if (factor_designator_fun_no_params.getDesignatorOption().obj.getKind() == Obj.Meth) {	
			if (factor_designator_fun_no_params.getDesignatorOption().obj != null) {
				factor_designator_fun_no_params.struct = factor_designator_fun_no_params.getDesignatorOption().obj.getType();
			}	
			else {
				factor_designator_fun_no_params.struct = Tab.noType;
			}
			
			// provera ActPar
			
			Obj designatorMeth = factor_designator_fun_no_params.getDesignatorOption().obj;
			List<Struct> formParsList = new ArrayList<>();
			for (Obj currLocal: designatorMeth.getLocalSymbols()) {
				if (currLocal.getKind() == Obj.Var && currLocal.getLevel() == 1 && currLocal.getFpPos() == 1) { // currLocal.getKind() == Obj.Var && currLocal.getLevel() == 1 - ??
					formParsList.add(currLocal.getType());
				}
			}
			
			if (formParsList.size() != actualFormParsList.size()) {
				report_error("[Factor_designator_fun_no_params] Nekompatibilnost parametara pri pozivu metode: " + factor_designator_fun_no_params.getDesignatorOption().obj.getName() + " - [Greska u broju parametara]", factor_designator_fun_no_params);
			}
			else {
				for (int i = 0; i < actualFormParsList.size(); i++) {
					if (actualFormParsList.get(i).assignableTo(formParsList.get(i)) == false) {
						report_error("[Factor_designator_fun_no_params] Nekompatibilnost parametara pri pozivu metode: " + factor_designator_fun_no_params.getDesignatorOption().obj.getName() + " - [Greska u tipovima parametara]", factor_designator_fun_no_params);
						break;
					}
				}
				
				// detektovanje koriscenja simbola - poziv globalne funkcije
				report_info("Poziv globalne funkcije: " + designatorMeth.getName() + " - [Obj] Kind: " + designatorMeth.getKind() + ", Type: " + designatorMeth.getType().getKind() + ", Level: " + designatorMeth.getLevel() + ", Size of locals: " + designatorMeth.getLocalSymbols().size(), factor_designator_fun_no_params);
			}

			actualFormParsList = new ArrayList<Struct>();
		}
		else {
			factor_designator_fun_no_params.struct = Tab.noType;
			report_error("[Factor_designator_fun_no_params] Poziv necega sto nije deklarisano kao globalna funkcija glavnog programa: " + factor_designator_fun_no_params.getDesignatorOption().obj.getName(), factor_designator_fun_no_params);
		}
	}
	
	@Override
	public void visit(Factor_designator_fun_params factor_designator_fun_params) {
		if (factor_designator_fun_params.getDesignatorOption().obj.getKind() == Obj.Meth) {
			if (factor_designator_fun_params.getDesignatorOption().obj != null) {
				factor_designator_fun_params.struct = factor_designator_fun_params.getDesignatorOption().obj.getType();
			}
			else {
				factor_designator_fun_params.struct = Tab.noType;
			}
			
			Obj designatorMeth = factor_designator_fun_params.getDesignatorOption().obj;
			List<Struct> formParsList = new ArrayList<>();
			for (Obj currLocal: designatorMeth.getLocalSymbols()) {
				if (currLocal.getKind() == Obj.Var && currLocal.getLevel() == 1 && currLocal.getFpPos() == 1) { // currLocal.getKind() == Obj.Var && currLocal.getLevel() == 1 - ??
					formParsList.add(currLocal.getType());
				}
			}
			
			// chr(e)
			if (factor_designator_fun_params.getDesignatorOption().obj.getName().equals("chr") &&
				factor_designator_fun_params.getDesignatorOption().obj.getType().getKind() == Struct.Char &&
				formParsList.size() == 1) {

				if (actualFormParsList.size() != 1) {
					report_error("Nekompatibilnost parametara pri pozivu metode: " + factor_designator_fun_params.getDesignatorOption().obj.getName() + " - [Greska u broju parametara]", factor_designator_fun_params);
				}
				else {
					if (actualFormParsList.get(0).getKind() != Struct.Int) {
						report_error("Nekompatibilnost parametara pri pozivu metode: " + factor_designator_fun_params.getDesignatorOption().obj.getName() + " - [Greska u tipovima parametara]", factor_designator_fun_params);
					}
					else {
						// detektovanje koriscenja simbola - poziv globalne funkcije
						report_info("Poziv globalne funkcije: " + designatorMeth.getName() + " - [Obj] Kind: " + designatorMeth.getKind() + ", Type: " + designatorMeth.getType().getKind() + ", Level: " + designatorMeth.getLevel() + ", Size of locals: " + designatorMeth.getLocalSymbols().size(), factor_designator_fun_params);
					}
				}
			}
			// ord(c)
			else if (factor_designator_fun_params.getDesignatorOption().obj.getName().equals("ord") && 
					factor_designator_fun_params.getDesignatorOption().obj.getType().getKind() == Struct.Int &&
					formParsList.size() == 1) {
				if (actualFormParsList.size() != 1) {
					report_error("Nekompatibilnost parametara pri pozivu metode: " + factor_designator_fun_params.getDesignatorOption().obj.getName() + " - [Greska u broju parametara]", factor_designator_fun_params);
				}
				else {
					if (actualFormParsList.get(0).getKind() != Struct.Char) {
						report_error("Nekompatibilnost parametara pri pozivu metode: " + factor_designator_fun_params.getDesignatorOption().obj.getName() + " - [Greska u tipovima parametara]", factor_designator_fun_params);
					}
					else {
						// detektovanje koriscenja simbola - poziv globalne funkcije
						report_info("Poziv globalne funkcije: " + designatorMeth.getName() + " - [Obj] Kind: " + designatorMeth.getKind() + ", Type: " + designatorMeth.getType().getKind() + ", Level: " + designatorMeth.getLevel() + ", Size of locals: " + designatorMeth.getLocalSymbols().size(), factor_designator_fun_params);
					}
				}
			}
			// len(a)
			else if (factor_designator_fun_params.getDesignatorOption().obj.getName().equals("len") && 
					factor_designator_fun_params.getDesignatorOption().obj.getType().getKind() == Struct.Int &&
					formParsList.size() == 1) {
				if (actualFormParsList.size() != 1) {
					report_error("Nekompatibilnost parametara pri pozivu metode: " + factor_designator_fun_params.getDesignatorOption().obj.getName() + " - [Greska u broju parametara]", factor_designator_fun_params);
				}
				else {
					if (actualFormParsList.get(0).getKind() != Struct.Array) {
						report_error("Nekompatibilnost parametara pri pozivu metode: " + factor_designator_fun_params.getDesignatorOption().obj.getName() + " - [Greska u tipovima parametara]", factor_designator_fun_params);
					}
					else {
						// detektovanje koriscenja simbola - poziv globalne funkcije
						report_info("Poziv globalne funkcije: " + designatorMeth.getName() + " - [Obj] Kind: " + designatorMeth.getKind() + ", Type: " + designatorMeth.getType().getKind() + ", Level: " + designatorMeth.getLevel() + ", Size of locals: " + designatorMeth.getLocalSymbols().size(), factor_designator_fun_params);
					}
				}
			}
			// add(a, b)
			else if (factor_designator_fun_params.getDesignatorOption().obj.getName().equals("add") && 
					factor_designator_fun_params.getDesignatorOption().obj.getType().getKind() == Struct.None &&
					formParsList.size() == 2) {
				if (actualFormParsList.size() != 2) {
					report_error("Nekompatibilnost parametara pri pozivu metode: " + factor_designator_fun_params.getDesignatorOption().obj.getName() + " - [Greska u broju parametara]", factor_designator_fun_params);
				}
				else {
					if (actualFormParsList.get(0).getKind() != Struct.Interface || actualFormParsList.get(1).getKind() != Struct.Int) {
						report_error("Nekompatibilnost parametara pri pozivu metode: " + factor_designator_fun_params.getDesignatorOption().obj.getName() + " - [Greska u tipovima parametara]", factor_designator_fun_params);
					}
					else {
						// detektovanje koriscenja simbola - poziv globalne funkcije
						report_info("Poziv globalne funkcije: " + designatorMeth.getName() + " - [Obj] Kind: " + designatorMeth.getKind() + ", Type: " + designatorMeth.getType().getKind() + ", Level: " + designatorMeth.getLevel() + ", Size of locals: " + designatorMeth.getLocalSymbols().size(), factor_designator_fun_params);
					}
				}
			}
			// addAll(a, b)
			else if (factor_designator_fun_params.getDesignatorOption().obj.getName().equals("addAll") && 
					factor_designator_fun_params.getDesignatorOption().obj.getType().getKind() == Struct.None &&
					formParsList.size() == 2) {
				if (actualFormParsList.size() != 2) {
					report_error("Nekompatibilnost parametara pri pozivu metode: " + factor_designator_fun_params.getDesignatorOption().obj.getName() + " - [Greska u broju parametara]", factor_designator_fun_params);
				}
				else {
					if (actualFormParsList.get(0).getKind() != Struct.Interface || actualFormParsList.get(1).getKind() != Struct.Array || actualFormParsList.get(1).getElemType() != Tab.intType) {
						report_error("Nekompatibilnost parametara pri pozivu metode: " + factor_designator_fun_params.getDesignatorOption().obj.getName() + " - [Greska u tipovima parametara]", factor_designator_fun_params);
					}
					else {
						// detektovanje koriscenja simbola - poziv globalne funkcije
						report_info("Poziv globalne funkcije: " + designatorMeth.getName() + " - [Obj] Kind: " + designatorMeth.getKind() + ", Type: " + designatorMeth.getType().getKind() + ", Level: " + designatorMeth.getLevel() + ", Size of locals: " + designatorMeth.getLocalSymbols().size(), factor_designator_fun_params);
					}
				}
			}
			else {
				if (formParsList.size() != actualFormParsList.size()) {
					report_error("[Factor_designator_fun_params] Nekompatibilnost parametara pri pozivu metode: " + factor_designator_fun_params.getDesignatorOption().obj.getName() + " - [Greska u broju parametara]", factor_designator_fun_params);
				}
				else {
					
					for (int i = 0; i < actualFormParsList.size(); i++) {
						if (actualFormParsList.get(i).assignableTo(formParsList.get(i)) == false) {
							report_error("[Factor_designator_fun_params] Nekompatibilnost parametara pri pozivu metode: " + factor_designator_fun_params.getDesignatorOption().obj.getName() + " - [Greska u tipovima parametara]", factor_designator_fun_params);
							break;
						}
					}
					
					// detektovanje koriscenja simbola - poziv globalne funkcije
					report_info("Poziv globalne funkcije: " + designatorMeth.getName() + " - [Obj] Kind: " + designatorMeth.getKind() + ", Type: " + designatorMeth.getType().getKind() + ", Level: " + designatorMeth.getLevel() + ", Size of locals: " + designatorMeth.getLocalSymbols().size(), factor_designator_fun_params);
				}
			}

			actualFormParsList = new ArrayList<Struct>();
		}
		else {
			factor_designator_fun_params.struct = Tab.noType;
			report_error("[Factor_designator_fun_params] Poziv necega sto nije deklarisano kao globalna funkcija glavnog programa: " + factor_designator_fun_params.getDesignatorOption().obj.getName(), factor_designator_fun_params);
		}
	}
	
	@Override
	public void visit(Factor_number factor_number) {
		factor_number.struct = Tab.intType;
	}
	
	@Override
	public void visit(Factor_character factor_character) {
		factor_character.struct = Tab.charType;
	}
	
	@Override
	public void visit(Factor_bool factor_bool) {
		factor_bool.struct = Tab.find("bool").getType();
	}
	
	@Override
	public void visit(Factor_new factor_new) {
		factor_new.struct = factor_new.getFactorNewMore().struct;
	}
	
	@Override
	public void visit(FactorNewMore_array factorNewMore_array) {
		if (factorNewMore_array.getExpr().struct != null) {
			if (factorNewMore_array.getExpr().struct.equals(Tab.intType)) {
				if (currentType.getKind() == Struct.Interface) {
					factorNewMore_array.struct = new Struct(Struct.Interface, Tab.intType);
				}
				else {
					factorNewMore_array.struct = new Struct(Struct.Array, currentType);
				}
			}
			else {
				factorNewMore_array.struct = Tab.noType;
				if (currentType.getKind() == Struct.Interface) {
					report_error("Pokusaj generisanja seta sa kapacitetom koji nije celobrojnog tipa", factorNewMore_array);
				}
				else {
					report_error("Pokusaj generisanja niza sa velicinom koja nije celobrojnog tipa", factorNewMore_array);
				}
			}
		}
	}
	
	@Override
	public void visit(Factor_expr factor_expr) {
		factor_expr.struct = factor_expr.getExpr().struct;
	}
}
