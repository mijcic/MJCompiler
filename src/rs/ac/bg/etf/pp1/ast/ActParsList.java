// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class ActParsList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private StartOfActParsList StartOfActParsList;
    private ActPar ActPar;
    private ActParsListMore ActParsListMore;

    public ActParsList (StartOfActParsList StartOfActParsList, ActPar ActPar, ActParsListMore ActParsListMore) {
        this.StartOfActParsList=StartOfActParsList;
        if(StartOfActParsList!=null) StartOfActParsList.setParent(this);
        this.ActPar=ActPar;
        if(ActPar!=null) ActPar.setParent(this);
        this.ActParsListMore=ActParsListMore;
        if(ActParsListMore!=null) ActParsListMore.setParent(this);
    }

    public StartOfActParsList getStartOfActParsList() {
        return StartOfActParsList;
    }

    public void setStartOfActParsList(StartOfActParsList StartOfActParsList) {
        this.StartOfActParsList=StartOfActParsList;
    }

    public ActPar getActPar() {
        return ActPar;
    }

    public void setActPar(ActPar ActPar) {
        this.ActPar=ActPar;
    }

    public ActParsListMore getActParsListMore() {
        return ActParsListMore;
    }

    public void setActParsListMore(ActParsListMore ActParsListMore) {
        this.ActParsListMore=ActParsListMore;
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
        if(ActPar!=null) ActPar.accept(visitor);
        if(ActParsListMore!=null) ActParsListMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StartOfActParsList!=null) StartOfActParsList.traverseTopDown(visitor);
        if(ActPar!=null) ActPar.traverseTopDown(visitor);
        if(ActParsListMore!=null) ActParsListMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StartOfActParsList!=null) StartOfActParsList.traverseBottomUp(visitor);
        if(ActPar!=null) ActPar.traverseBottomUp(visitor);
        if(ActParsListMore!=null) ActParsListMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActParsList(\n");

        if(StartOfActParsList!=null)
            buffer.append(StartOfActParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPar!=null)
            buffer.append(ActPar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParsListMore!=null)
            buffer.append(ActParsListMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActParsList]");
        return buffer.toString();
    }
}
