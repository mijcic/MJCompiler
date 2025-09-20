// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class Statement_do_while_condition extends Statement {

    private StartOfDoWhileLoop StartOfDoWhileLoop;
    private Statement Statement;
    private StartOfCondition StartOfCondition;
    private ConditionList ConditionList;
    private EndOfDoWhileLoop EndOfDoWhileLoop;

    public Statement_do_while_condition (StartOfDoWhileLoop StartOfDoWhileLoop, Statement Statement, StartOfCondition StartOfCondition, ConditionList ConditionList, EndOfDoWhileLoop EndOfDoWhileLoop) {
        this.StartOfDoWhileLoop=StartOfDoWhileLoop;
        if(StartOfDoWhileLoop!=null) StartOfDoWhileLoop.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.StartOfCondition=StartOfCondition;
        if(StartOfCondition!=null) StartOfCondition.setParent(this);
        this.ConditionList=ConditionList;
        if(ConditionList!=null) ConditionList.setParent(this);
        this.EndOfDoWhileLoop=EndOfDoWhileLoop;
        if(EndOfDoWhileLoop!=null) EndOfDoWhileLoop.setParent(this);
    }

    public StartOfDoWhileLoop getStartOfDoWhileLoop() {
        return StartOfDoWhileLoop;
    }

    public void setStartOfDoWhileLoop(StartOfDoWhileLoop StartOfDoWhileLoop) {
        this.StartOfDoWhileLoop=StartOfDoWhileLoop;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public StartOfCondition getStartOfCondition() {
        return StartOfCondition;
    }

    public void setStartOfCondition(StartOfCondition StartOfCondition) {
        this.StartOfCondition=StartOfCondition;
    }

    public ConditionList getConditionList() {
        return ConditionList;
    }

    public void setConditionList(ConditionList ConditionList) {
        this.ConditionList=ConditionList;
    }

    public EndOfDoWhileLoop getEndOfDoWhileLoop() {
        return EndOfDoWhileLoop;
    }

    public void setEndOfDoWhileLoop(EndOfDoWhileLoop EndOfDoWhileLoop) {
        this.EndOfDoWhileLoop=EndOfDoWhileLoop;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StartOfDoWhileLoop!=null) StartOfDoWhileLoop.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(StartOfCondition!=null) StartOfCondition.accept(visitor);
        if(ConditionList!=null) ConditionList.accept(visitor);
        if(EndOfDoWhileLoop!=null) EndOfDoWhileLoop.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StartOfDoWhileLoop!=null) StartOfDoWhileLoop.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(StartOfCondition!=null) StartOfCondition.traverseTopDown(visitor);
        if(ConditionList!=null) ConditionList.traverseTopDown(visitor);
        if(EndOfDoWhileLoop!=null) EndOfDoWhileLoop.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StartOfDoWhileLoop!=null) StartOfDoWhileLoop.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(StartOfCondition!=null) StartOfCondition.traverseBottomUp(visitor);
        if(ConditionList!=null) ConditionList.traverseBottomUp(visitor);
        if(EndOfDoWhileLoop!=null) EndOfDoWhileLoop.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_do_while_condition(\n");

        if(StartOfDoWhileLoop!=null)
            buffer.append(StartOfDoWhileLoop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StartOfCondition!=null)
            buffer.append(StartOfCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionList!=null)
            buffer.append(ConditionList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EndOfDoWhileLoop!=null)
            buffer.append(EndOfDoWhileLoop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_do_while_condition]");
        return buffer.toString();
    }
}
