// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class Factor_designator_fun_no_params extends Factor {

    private DesignatorOption DesignatorOption;
    private NoActParsList NoActParsList;

    public Factor_designator_fun_no_params (DesignatorOption DesignatorOption, NoActParsList NoActParsList) {
        this.DesignatorOption=DesignatorOption;
        if(DesignatorOption!=null) DesignatorOption.setParent(this);
        this.NoActParsList=NoActParsList;
        if(NoActParsList!=null) NoActParsList.setParent(this);
    }

    public DesignatorOption getDesignatorOption() {
        return DesignatorOption;
    }

    public void setDesignatorOption(DesignatorOption DesignatorOption) {
        this.DesignatorOption=DesignatorOption;
    }

    public NoActParsList getNoActParsList() {
        return NoActParsList;
    }

    public void setNoActParsList(NoActParsList NoActParsList) {
        this.NoActParsList=NoActParsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorOption!=null) DesignatorOption.accept(visitor);
        if(NoActParsList!=null) NoActParsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorOption!=null) DesignatorOption.traverseTopDown(visitor);
        if(NoActParsList!=null) NoActParsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorOption!=null) DesignatorOption.traverseBottomUp(visitor);
        if(NoActParsList!=null) NoActParsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Factor_designator_fun_no_params(\n");

        if(DesignatorOption!=null)
            buffer.append(DesignatorOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NoActParsList!=null)
            buffer.append(NoActParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Factor_designator_fun_no_params]");
        return buffer.toString();
    }
}
