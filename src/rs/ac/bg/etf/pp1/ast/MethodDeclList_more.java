// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclList_more extends MethodDeclList {

    private MethodDeclElem MethodDeclElem;
    private MethodDeclList MethodDeclList;

    public MethodDeclList_more (MethodDeclElem MethodDeclElem, MethodDeclList MethodDeclList) {
        this.MethodDeclElem=MethodDeclElem;
        if(MethodDeclElem!=null) MethodDeclElem.setParent(this);
        this.MethodDeclList=MethodDeclList;
        if(MethodDeclList!=null) MethodDeclList.setParent(this);
    }

    public MethodDeclElem getMethodDeclElem() {
        return MethodDeclElem;
    }

    public void setMethodDeclElem(MethodDeclElem MethodDeclElem) {
        this.MethodDeclElem=MethodDeclElem;
    }

    public MethodDeclList getMethodDeclList() {
        return MethodDeclList;
    }

    public void setMethodDeclList(MethodDeclList MethodDeclList) {
        this.MethodDeclList=MethodDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclElem!=null) MethodDeclElem.accept(visitor);
        if(MethodDeclList!=null) MethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclElem!=null) MethodDeclElem.traverseTopDown(visitor);
        if(MethodDeclList!=null) MethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclElem!=null) MethodDeclElem.traverseBottomUp(visitor);
        if(MethodDeclList!=null) MethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclList_more(\n");

        if(MethodDeclElem!=null)
            buffer.append(MethodDeclElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclList!=null)
            buffer.append(MethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclList_more]");
        return buffer.toString();
    }
}
