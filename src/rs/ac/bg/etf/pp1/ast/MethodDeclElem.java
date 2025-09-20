// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclElem implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethodSignature MethodSignature;
    private MethodsVarDeclList MethodsVarDeclList;
    private MethodDeclElemLeftBrace MethodDeclElemLeftBrace;
    private MethodsStatementList MethodsStatementList;

    public MethodDeclElem (MethodSignature MethodSignature, MethodsVarDeclList MethodsVarDeclList, MethodDeclElemLeftBrace MethodDeclElemLeftBrace, MethodsStatementList MethodsStatementList) {
        this.MethodSignature=MethodSignature;
        if(MethodSignature!=null) MethodSignature.setParent(this);
        this.MethodsVarDeclList=MethodsVarDeclList;
        if(MethodsVarDeclList!=null) MethodsVarDeclList.setParent(this);
        this.MethodDeclElemLeftBrace=MethodDeclElemLeftBrace;
        if(MethodDeclElemLeftBrace!=null) MethodDeclElemLeftBrace.setParent(this);
        this.MethodsStatementList=MethodsStatementList;
        if(MethodsStatementList!=null) MethodsStatementList.setParent(this);
    }

    public MethodSignature getMethodSignature() {
        return MethodSignature;
    }

    public void setMethodSignature(MethodSignature MethodSignature) {
        this.MethodSignature=MethodSignature;
    }

    public MethodsVarDeclList getMethodsVarDeclList() {
        return MethodsVarDeclList;
    }

    public void setMethodsVarDeclList(MethodsVarDeclList MethodsVarDeclList) {
        this.MethodsVarDeclList=MethodsVarDeclList;
    }

    public MethodDeclElemLeftBrace getMethodDeclElemLeftBrace() {
        return MethodDeclElemLeftBrace;
    }

    public void setMethodDeclElemLeftBrace(MethodDeclElemLeftBrace MethodDeclElemLeftBrace) {
        this.MethodDeclElemLeftBrace=MethodDeclElemLeftBrace;
    }

    public MethodsStatementList getMethodsStatementList() {
        return MethodsStatementList;
    }

    public void setMethodsStatementList(MethodsStatementList MethodsStatementList) {
        this.MethodsStatementList=MethodsStatementList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodSignature!=null) MethodSignature.accept(visitor);
        if(MethodsVarDeclList!=null) MethodsVarDeclList.accept(visitor);
        if(MethodDeclElemLeftBrace!=null) MethodDeclElemLeftBrace.accept(visitor);
        if(MethodsStatementList!=null) MethodsStatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodSignature!=null) MethodSignature.traverseTopDown(visitor);
        if(MethodsVarDeclList!=null) MethodsVarDeclList.traverseTopDown(visitor);
        if(MethodDeclElemLeftBrace!=null) MethodDeclElemLeftBrace.traverseTopDown(visitor);
        if(MethodsStatementList!=null) MethodsStatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodSignature!=null) MethodSignature.traverseBottomUp(visitor);
        if(MethodsVarDeclList!=null) MethodsVarDeclList.traverseBottomUp(visitor);
        if(MethodDeclElemLeftBrace!=null) MethodDeclElemLeftBrace.traverseBottomUp(visitor);
        if(MethodsStatementList!=null) MethodsStatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclElem(\n");

        if(MethodSignature!=null)
            buffer.append(MethodSignature.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodsVarDeclList!=null)
            buffer.append(MethodsVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclElemLeftBrace!=null)
            buffer.append(MethodDeclElemLeftBrace.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodsStatementList!=null)
            buffer.append(MethodsStatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclElem]");
        return buffer.toString();
    }
}
