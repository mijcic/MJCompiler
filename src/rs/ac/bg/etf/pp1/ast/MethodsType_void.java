// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class MethodsType_void extends MethodsType {

    public MethodsType_void () {
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
        buffer.append("MethodsType_void(\n");

        buffer.append(tab);
        buffer.append(") [MethodsType_void]");
        return buffer.toString();
    }
}
