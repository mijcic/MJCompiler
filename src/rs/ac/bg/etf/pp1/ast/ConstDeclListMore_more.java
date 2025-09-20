// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclListMore_more extends ConstDeclListMore {

    private ConstDeclElem ConstDeclElem;
    private ConstDeclListMore ConstDeclListMore;

    public ConstDeclListMore_more (ConstDeclElem ConstDeclElem, ConstDeclListMore ConstDeclListMore) {
        this.ConstDeclElem=ConstDeclElem;
        if(ConstDeclElem!=null) ConstDeclElem.setParent(this);
        this.ConstDeclListMore=ConstDeclListMore;
        if(ConstDeclListMore!=null) ConstDeclListMore.setParent(this);
    }

    public ConstDeclElem getConstDeclElem() {
        return ConstDeclElem;
    }

    public void setConstDeclElem(ConstDeclElem ConstDeclElem) {
        this.ConstDeclElem=ConstDeclElem;
    }

    public ConstDeclListMore getConstDeclListMore() {
        return ConstDeclListMore;
    }

    public void setConstDeclListMore(ConstDeclListMore ConstDeclListMore) {
        this.ConstDeclListMore=ConstDeclListMore;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclElem!=null) ConstDeclElem.accept(visitor);
        if(ConstDeclListMore!=null) ConstDeclListMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclElem!=null) ConstDeclElem.traverseTopDown(visitor);
        if(ConstDeclListMore!=null) ConstDeclListMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclElem!=null) ConstDeclElem.traverseBottomUp(visitor);
        if(ConstDeclListMore!=null) ConstDeclListMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclListMore_more(\n");

        if(ConstDeclElem!=null)
            buffer.append(ConstDeclElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclListMore!=null)
            buffer.append(ConstDeclListMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclListMore_more]");
        return buffer.toString();
    }
}
