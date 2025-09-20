// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class MethodsVarDeclList_list extends MethodsVarDeclList {

    private LocalVarDeclList LocalVarDeclList;
    private MethodsVarDeclList MethodsVarDeclList;

    public MethodsVarDeclList_list (LocalVarDeclList LocalVarDeclList, MethodsVarDeclList MethodsVarDeclList) {
        this.LocalVarDeclList=LocalVarDeclList;
        if(LocalVarDeclList!=null) LocalVarDeclList.setParent(this);
        this.MethodsVarDeclList=MethodsVarDeclList;
        if(MethodsVarDeclList!=null) MethodsVarDeclList.setParent(this);
    }

    public LocalVarDeclList getLocalVarDeclList() {
        return LocalVarDeclList;
    }

    public void setLocalVarDeclList(LocalVarDeclList LocalVarDeclList) {
        this.LocalVarDeclList=LocalVarDeclList;
    }

    public MethodsVarDeclList getMethodsVarDeclList() {
        return MethodsVarDeclList;
    }

    public void setMethodsVarDeclList(MethodsVarDeclList MethodsVarDeclList) {
        this.MethodsVarDeclList=MethodsVarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LocalVarDeclList!=null) LocalVarDeclList.accept(visitor);
        if(MethodsVarDeclList!=null) MethodsVarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LocalVarDeclList!=null) LocalVarDeclList.traverseTopDown(visitor);
        if(MethodsVarDeclList!=null) MethodsVarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LocalVarDeclList!=null) LocalVarDeclList.traverseBottomUp(visitor);
        if(MethodsVarDeclList!=null) MethodsVarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodsVarDeclList_list(\n");

        if(LocalVarDeclList!=null)
            buffer.append(LocalVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodsVarDeclList!=null)
            buffer.append(MethodsVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodsVarDeclList_list]");
        return buffer.toString();
    }
}
