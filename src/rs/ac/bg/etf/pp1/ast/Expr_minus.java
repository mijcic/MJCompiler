// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class Expr_minus extends Expr {

    private MinusTerm MinusTerm;
    private ExprMore ExprMore;

    public Expr_minus (MinusTerm MinusTerm, ExprMore ExprMore) {
        this.MinusTerm=MinusTerm;
        if(MinusTerm!=null) MinusTerm.setParent(this);
        this.ExprMore=ExprMore;
        if(ExprMore!=null) ExprMore.setParent(this);
    }

    public MinusTerm getMinusTerm() {
        return MinusTerm;
    }

    public void setMinusTerm(MinusTerm MinusTerm) {
        this.MinusTerm=MinusTerm;
    }

    public ExprMore getExprMore() {
        return ExprMore;
    }

    public void setExprMore(ExprMore ExprMore) {
        this.ExprMore=ExprMore;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MinusTerm!=null) MinusTerm.accept(visitor);
        if(ExprMore!=null) ExprMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MinusTerm!=null) MinusTerm.traverseTopDown(visitor);
        if(ExprMore!=null) ExprMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MinusTerm!=null) MinusTerm.traverseBottomUp(visitor);
        if(ExprMore!=null) ExprMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Expr_minus(\n");

        if(MinusTerm!=null)
            buffer.append(MinusTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprMore!=null)
            buffer.append(ExprMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Expr_minus]");
        return buffer.toString();
    }
}
