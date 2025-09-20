// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class NoActParsList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private StartOfActParsList StartOfActParsList;

    public NoActParsList (StartOfActParsList StartOfActParsList) {
        this.StartOfActParsList=StartOfActParsList;
        if(StartOfActParsList!=null) StartOfActParsList.setParent(this);
    }

    public StartOfActParsList getStartOfActParsList() {
        return StartOfActParsList;
    }

    public void setStartOfActParsList(StartOfActParsList StartOfActParsList) {
        this.StartOfActParsList=StartOfActParsList;
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
        if(StartOfActParsList!=null) StartOfActParsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StartOfActParsList!=null) StartOfActParsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StartOfActParsList!=null) StartOfActParsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoActParsList(\n");

        if(StartOfActParsList!=null)
            buffer.append(StartOfActParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NoActParsList]");
        return buffer.toString();
    }
}
