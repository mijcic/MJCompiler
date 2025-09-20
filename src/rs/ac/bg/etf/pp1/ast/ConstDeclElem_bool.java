// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclElem_bool extends ConstDeclElem {

    private String identName;
    private Integer value;

    public ConstDeclElem_bool (String identName, Integer value) {
        this.identName=identName;
        this.value=value;
    }

    public String getIdentName() {
        return identName;
    }

    public void setIdentName(String identName) {
        this.identName=identName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value=value;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclElem_bool(\n");

        buffer.append(" "+tab+identName);
        buffer.append("\n");

        buffer.append(" "+tab+value);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclElem_bool]");
        return buffer.toString();
    }
}
