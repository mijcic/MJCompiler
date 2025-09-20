// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class MethodsStatementList_list extends MethodsStatementList {

    private Statement Statement;
    private MethodsStatementList MethodsStatementList;

    public MethodsStatementList_list (Statement Statement, MethodsStatementList MethodsStatementList) {
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.MethodsStatementList=MethodsStatementList;
        if(MethodsStatementList!=null) MethodsStatementList.setParent(this);
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public MethodsStatementList getMethodsStatementList() {
        return MethodsStatementList;
    }

    public void setMethodsStatementList(MethodsStatementList MethodsStatementList) {
        this.MethodsStatementList=MethodsStatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Statement!=null) Statement.accept(visitor);
        if(MethodsStatementList!=null) MethodsStatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(MethodsStatementList!=null) MethodsStatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(MethodsStatementList!=null) MethodsStatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodsStatementList_list(\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodsStatementList!=null)
            buffer.append(MethodsStatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodsStatementList_list]");
        return buffer.toString();
    }
}
