// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class MethodSignature implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethodsType MethodsType;
    private MethodName MethodName;
    private MethodSignaturesFormPars MethodSignaturesFormPars;

    public MethodSignature (MethodsType MethodsType, MethodName MethodName, MethodSignaturesFormPars MethodSignaturesFormPars) {
        this.MethodsType=MethodsType;
        if(MethodsType!=null) MethodsType.setParent(this);
        this.MethodName=MethodName;
        if(MethodName!=null) MethodName.setParent(this);
        this.MethodSignaturesFormPars=MethodSignaturesFormPars;
        if(MethodSignaturesFormPars!=null) MethodSignaturesFormPars.setParent(this);
    }

    public MethodsType getMethodsType() {
        return MethodsType;
    }

    public void setMethodsType(MethodsType MethodsType) {
        this.MethodsType=MethodsType;
    }

    public MethodName getMethodName() {
        return MethodName;
    }

    public void setMethodName(MethodName MethodName) {
        this.MethodName=MethodName;
    }

    public MethodSignaturesFormPars getMethodSignaturesFormPars() {
        return MethodSignaturesFormPars;
    }

    public void setMethodSignaturesFormPars(MethodSignaturesFormPars MethodSignaturesFormPars) {
        this.MethodSignaturesFormPars=MethodSignaturesFormPars;
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
        if(MethodsType!=null) MethodsType.accept(visitor);
        if(MethodName!=null) MethodName.accept(visitor);
        if(MethodSignaturesFormPars!=null) MethodSignaturesFormPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodsType!=null) MethodsType.traverseTopDown(visitor);
        if(MethodName!=null) MethodName.traverseTopDown(visitor);
        if(MethodSignaturesFormPars!=null) MethodSignaturesFormPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodsType!=null) MethodsType.traverseBottomUp(visitor);
        if(MethodName!=null) MethodName.traverseBottomUp(visitor);
        if(MethodSignaturesFormPars!=null) MethodSignaturesFormPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodSignature(\n");

        if(MethodsType!=null)
            buffer.append(MethodsType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodName!=null)
            buffer.append(MethodName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodSignaturesFormPars!=null)
            buffer.append(MethodSignaturesFormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodSignature]");
        return buffer.toString();
    }
}
