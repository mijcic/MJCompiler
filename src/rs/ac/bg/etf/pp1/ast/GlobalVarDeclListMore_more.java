// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarDeclListMore_more extends GlobalVarDeclListMore {

    private GlobalVarDeclElem GlobalVarDeclElem;
    private GlobalVarDeclListMore GlobalVarDeclListMore;

    public GlobalVarDeclListMore_more (GlobalVarDeclElem GlobalVarDeclElem, GlobalVarDeclListMore GlobalVarDeclListMore) {
        this.GlobalVarDeclElem=GlobalVarDeclElem;
        if(GlobalVarDeclElem!=null) GlobalVarDeclElem.setParent(this);
        this.GlobalVarDeclListMore=GlobalVarDeclListMore;
        if(GlobalVarDeclListMore!=null) GlobalVarDeclListMore.setParent(this);
    }

    public GlobalVarDeclElem getGlobalVarDeclElem() {
        return GlobalVarDeclElem;
    }

    public void setGlobalVarDeclElem(GlobalVarDeclElem GlobalVarDeclElem) {
        this.GlobalVarDeclElem=GlobalVarDeclElem;
    }

    public GlobalVarDeclListMore getGlobalVarDeclListMore() {
        return GlobalVarDeclListMore;
    }

    public void setGlobalVarDeclListMore(GlobalVarDeclListMore GlobalVarDeclListMore) {
        this.GlobalVarDeclListMore=GlobalVarDeclListMore;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobalVarDeclElem!=null) GlobalVarDeclElem.accept(visitor);
        if(GlobalVarDeclListMore!=null) GlobalVarDeclListMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobalVarDeclElem!=null) GlobalVarDeclElem.traverseTopDown(visitor);
        if(GlobalVarDeclListMore!=null) GlobalVarDeclListMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobalVarDeclElem!=null) GlobalVarDeclElem.traverseBottomUp(visitor);
        if(GlobalVarDeclListMore!=null) GlobalVarDeclListMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarDeclListMore_more(\n");

        if(GlobalVarDeclElem!=null)
            buffer.append(GlobalVarDeclElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobalVarDeclListMore!=null)
            buffer.append(GlobalVarDeclListMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarDeclListMore_more]");
        return buffer.toString();
    }
}
