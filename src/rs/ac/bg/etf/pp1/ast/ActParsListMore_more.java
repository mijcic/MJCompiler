// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class ActParsListMore_more extends ActParsListMore {

    private ActPar ActPar;
    private ActParsListMore ActParsListMore;

    public ActParsListMore_more (ActPar ActPar, ActParsListMore ActParsListMore) {
        this.ActPar=ActPar;
        if(ActPar!=null) ActPar.setParent(this);
        this.ActParsListMore=ActParsListMore;
        if(ActParsListMore!=null) ActParsListMore.setParent(this);
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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActPar!=null) ActPar.accept(visitor);
        if(ActParsListMore!=null) ActParsListMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActPar!=null) ActPar.traverseTopDown(visitor);
        if(ActParsListMore!=null) ActParsListMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActPar!=null) ActPar.traverseBottomUp(visitor);
        if(ActParsListMore!=null) ActParsListMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActParsListMore_more(\n");

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
        buffer.append(") [ActParsListMore_more]");
        return buffer.toString();
    }
}
