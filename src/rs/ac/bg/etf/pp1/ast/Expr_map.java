// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class Expr_map extends Expr {

    private DesignatorOption DesignatorOption;
    private DesignatorOption DesignatorOption1;

    public Expr_map (DesignatorOption DesignatorOption, DesignatorOption DesignatorOption1) {
        this.DesignatorOption=DesignatorOption;
        if(DesignatorOption!=null) DesignatorOption.setParent(this);
        this.DesignatorOption1=DesignatorOption1;
        if(DesignatorOption1!=null) DesignatorOption1.setParent(this);
    }

    public DesignatorOption getDesignatorOption() {
        return DesignatorOption;
    }

    public void setDesignatorOption(DesignatorOption DesignatorOption) {
        this.DesignatorOption=DesignatorOption;
    }

    public DesignatorOption getDesignatorOption1() {
        return DesignatorOption1;
    }

    public void setDesignatorOption1(DesignatorOption DesignatorOption1) {
        this.DesignatorOption1=DesignatorOption1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorOption!=null) DesignatorOption.accept(visitor);
        if(DesignatorOption1!=null) DesignatorOption1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorOption!=null) DesignatorOption.traverseTopDown(visitor);
        if(DesignatorOption1!=null) DesignatorOption1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorOption!=null) DesignatorOption.traverseBottomUp(visitor);
        if(DesignatorOption1!=null) DesignatorOption1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Expr_map(\n");

        if(DesignatorOption!=null)
            buffer.append(DesignatorOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorOption1!=null)
            buffer.append(DesignatorOption1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Expr_map]");
        return buffer.toString();
    }
}
