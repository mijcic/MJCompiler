// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class ConditionListMore_more extends ConditionListMore {

    private CondTermList CondTermList;
    private ConditionListMore ConditionListMore;

    public ConditionListMore_more (CondTermList CondTermList, ConditionListMore ConditionListMore) {
        this.CondTermList=CondTermList;
        if(CondTermList!=null) CondTermList.setParent(this);
        this.ConditionListMore=ConditionListMore;
        if(ConditionListMore!=null) ConditionListMore.setParent(this);
    }

    public CondTermList getCondTermList() {
        return CondTermList;
    }

    public void setCondTermList(CondTermList CondTermList) {
        this.CondTermList=CondTermList;
    }

    public ConditionListMore getConditionListMore() {
        return ConditionListMore;
    }

    public void setConditionListMore(ConditionListMore ConditionListMore) {
        this.ConditionListMore=ConditionListMore;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondTermList!=null) CondTermList.accept(visitor);
        if(ConditionListMore!=null) ConditionListMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondTermList!=null) CondTermList.traverseTopDown(visitor);
        if(ConditionListMore!=null) ConditionListMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondTermList!=null) CondTermList.traverseBottomUp(visitor);
        if(ConditionListMore!=null) ConditionListMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionListMore_more(\n");

        if(CondTermList!=null)
            buffer.append(CondTermList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionListMore!=null)
            buffer.append(ConditionListMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionListMore_more]");
        return buffer.toString();
    }
}
