// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class LocalVarDeclListMore_more extends LocalVarDeclListMore {

    private LocalVarDeclElem LocalVarDeclElem;
    private LocalVarDeclListMore LocalVarDeclListMore;

    public LocalVarDeclListMore_more (LocalVarDeclElem LocalVarDeclElem, LocalVarDeclListMore LocalVarDeclListMore) {
        this.LocalVarDeclElem=LocalVarDeclElem;
        if(LocalVarDeclElem!=null) LocalVarDeclElem.setParent(this);
        this.LocalVarDeclListMore=LocalVarDeclListMore;
        if(LocalVarDeclListMore!=null) LocalVarDeclListMore.setParent(this);
    }

    public LocalVarDeclElem getLocalVarDeclElem() {
        return LocalVarDeclElem;
    }

    public void setLocalVarDeclElem(LocalVarDeclElem LocalVarDeclElem) {
        this.LocalVarDeclElem=LocalVarDeclElem;
    }

    public LocalVarDeclListMore getLocalVarDeclListMore() {
        return LocalVarDeclListMore;
    }

    public void setLocalVarDeclListMore(LocalVarDeclListMore LocalVarDeclListMore) {
        this.LocalVarDeclListMore=LocalVarDeclListMore;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LocalVarDeclElem!=null) LocalVarDeclElem.accept(visitor);
        if(LocalVarDeclListMore!=null) LocalVarDeclListMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LocalVarDeclElem!=null) LocalVarDeclElem.traverseTopDown(visitor);
        if(LocalVarDeclListMore!=null) LocalVarDeclListMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LocalVarDeclElem!=null) LocalVarDeclElem.traverseBottomUp(visitor);
        if(LocalVarDeclListMore!=null) LocalVarDeclListMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LocalVarDeclListMore_more(\n");

        if(LocalVarDeclElem!=null)
            buffer.append(LocalVarDeclElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(LocalVarDeclListMore!=null)
            buffer.append(LocalVarDeclListMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LocalVarDeclListMore_more]");
        return buffer.toString();
    }
}
