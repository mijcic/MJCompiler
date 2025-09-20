// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementAssignop_ERROR extends DesignatorStatementAssignop {

    public DesignatorStatementAssignop_ERROR () {
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
        buffer.append("DesignatorStatementAssignop_ERROR(\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementAssignop_ERROR]");
        return buffer.toString();
    }
}
