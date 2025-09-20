// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementAssignop_setop_union extends DesignatorStatementAssignop {

    private DesignatorOption DesignatorOption;
    private Assignop Assignop;
    private DesignatorOption DesignatorOption1;
    private Setop Setop;
    private DesignatorOption DesignatorOption2;

    public DesignatorStatementAssignop_setop_union (DesignatorOption DesignatorOption, Assignop Assignop, DesignatorOption DesignatorOption1, Setop Setop, DesignatorOption DesignatorOption2) {
        this.DesignatorOption=DesignatorOption;
        if(DesignatorOption!=null) DesignatorOption.setParent(this);
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.DesignatorOption1=DesignatorOption1;
        if(DesignatorOption1!=null) DesignatorOption1.setParent(this);
        this.Setop=Setop;
        if(Setop!=null) Setop.setParent(this);
        this.DesignatorOption2=DesignatorOption2;
        if(DesignatorOption2!=null) DesignatorOption2.setParent(this);
    }

    public DesignatorOption getDesignatorOption() {
        return DesignatorOption;
    }

    public void setDesignatorOption(DesignatorOption DesignatorOption) {
        this.DesignatorOption=DesignatorOption;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public DesignatorOption getDesignatorOption1() {
        return DesignatorOption1;
    }

    public void setDesignatorOption1(DesignatorOption DesignatorOption1) {
        this.DesignatorOption1=DesignatorOption1;
    }

    public Setop getSetop() {
        return Setop;
    }

    public void setSetop(Setop Setop) {
        this.Setop=Setop;
    }

    public DesignatorOption getDesignatorOption2() {
        return DesignatorOption2;
    }

    public void setDesignatorOption2(DesignatorOption DesignatorOption2) {
        this.DesignatorOption2=DesignatorOption2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorOption!=null) DesignatorOption.accept(visitor);
        if(Assignop!=null) Assignop.accept(visitor);
        if(DesignatorOption1!=null) DesignatorOption1.accept(visitor);
        if(Setop!=null) Setop.accept(visitor);
        if(DesignatorOption2!=null) DesignatorOption2.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorOption!=null) DesignatorOption.traverseTopDown(visitor);
        if(Assignop!=null) Assignop.traverseTopDown(visitor);
        if(DesignatorOption1!=null) DesignatorOption1.traverseTopDown(visitor);
        if(Setop!=null) Setop.traverseTopDown(visitor);
        if(DesignatorOption2!=null) DesignatorOption2.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorOption!=null) DesignatorOption.traverseBottomUp(visitor);
        if(Assignop!=null) Assignop.traverseBottomUp(visitor);
        if(DesignatorOption1!=null) DesignatorOption1.traverseBottomUp(visitor);
        if(Setop!=null) Setop.traverseBottomUp(visitor);
        if(DesignatorOption2!=null) DesignatorOption2.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementAssignop_setop_union(\n");

        if(DesignatorOption!=null)
            buffer.append(DesignatorOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorOption1!=null)
            buffer.append(DesignatorOption1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Setop!=null)
            buffer.append(Setop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorOption2!=null)
            buffer.append(DesignatorOption2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementAssignop_setop_union]");
        return buffer.toString();
    }
}
