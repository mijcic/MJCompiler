// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatement_params extends DesignatorStatement {

    private DesignatorOption DesignatorOption;
    private ActParsList ActParsList;

    public DesignatorStatement_params (DesignatorOption DesignatorOption, ActParsList ActParsList) {
        this.DesignatorOption=DesignatorOption;
        if(DesignatorOption!=null) DesignatorOption.setParent(this);
        this.ActParsList=ActParsList;
        if(ActParsList!=null) ActParsList.setParent(this);
    }

    public DesignatorOption getDesignatorOption() {
        return DesignatorOption;
    }

    public void setDesignatorOption(DesignatorOption DesignatorOption) {
        this.DesignatorOption=DesignatorOption;
    }

    public ActParsList getActParsList() {
        return ActParsList;
    }

    public void setActParsList(ActParsList ActParsList) {
        this.ActParsList=ActParsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorOption!=null) DesignatorOption.accept(visitor);
        if(ActParsList!=null) ActParsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorOption!=null) DesignatorOption.traverseTopDown(visitor);
        if(ActParsList!=null) ActParsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorOption!=null) DesignatorOption.traverseBottomUp(visitor);
        if(ActParsList!=null) ActParsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatement_params(\n");

        if(DesignatorOption!=null)
            buffer.append(DesignatorOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParsList!=null)
            buffer.append(ActParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatement_params]");
        return buffer.toString();
    }
}
