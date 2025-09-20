// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class Factor_new extends Factor {

    private Type Type;
    private FactorNewMore FactorNewMore;

    public Factor_new (Type Type, FactorNewMore FactorNewMore) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.FactorNewMore=FactorNewMore;
        if(FactorNewMore!=null) FactorNewMore.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public FactorNewMore getFactorNewMore() {
        return FactorNewMore;
    }

    public void setFactorNewMore(FactorNewMore FactorNewMore) {
        this.FactorNewMore=FactorNewMore;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(FactorNewMore!=null) FactorNewMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(FactorNewMore!=null) FactorNewMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(FactorNewMore!=null) FactorNewMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Factor_new(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FactorNewMore!=null)
            buffer.append(FactorNewMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Factor_new]");
        return buffer.toString();
    }
}
