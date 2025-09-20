// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class CondTermListMore_more extends CondTermListMore {

    private CondFact CondFact;
    private CondTermListMore CondTermListMore;

    public CondTermListMore_more (CondFact CondFact, CondTermListMore CondTermListMore) {
        this.CondFact=CondFact;
        if(CondFact!=null) CondFact.setParent(this);
        this.CondTermListMore=CondTermListMore;
        if(CondTermListMore!=null) CondTermListMore.setParent(this);
    }

    public CondFact getCondFact() {
        return CondFact;
    }

    public void setCondFact(CondFact CondFact) {
        this.CondFact=CondFact;
    }

    public CondTermListMore getCondTermListMore() {
        return CondTermListMore;
    }

    public void setCondTermListMore(CondTermListMore CondTermListMore) {
        this.CondTermListMore=CondTermListMore;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondFact!=null) CondFact.accept(visitor);
        if(CondTermListMore!=null) CondTermListMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondFact!=null) CondFact.traverseTopDown(visitor);
        if(CondTermListMore!=null) CondTermListMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondFact!=null) CondFact.traverseBottomUp(visitor);
        if(CondTermListMore!=null) CondTermListMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondTermListMore_more(\n");

        if(CondFact!=null)
            buffer.append(CondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondTermListMore!=null)
            buffer.append(CondTermListMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondTermListMore_more]");
        return buffer.toString();
    }
}
