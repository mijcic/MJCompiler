// generated with ast extension for cup
// version 0.8
// 19/7/2025 14:22:29


package rs.ac.bg.etf.pp1.ast;

public class FormParsList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private FormParsElem FormParsElem;
    private FormParsListMore FormParsListMore;

    public FormParsList (FormParsElem FormParsElem, FormParsListMore FormParsListMore) {
        this.FormParsElem=FormParsElem;
        if(FormParsElem!=null) FormParsElem.setParent(this);
        this.FormParsListMore=FormParsListMore;
        if(FormParsListMore!=null) FormParsListMore.setParent(this);
    }

    public FormParsElem getFormParsElem() {
        return FormParsElem;
    }

    public void setFormParsElem(FormParsElem FormParsElem) {
        this.FormParsElem=FormParsElem;
    }

    public FormParsListMore getFormParsListMore() {
        return FormParsListMore;
    }

    public void setFormParsListMore(FormParsListMore FormParsListMore) {
        this.FormParsListMore=FormParsListMore;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParsElem!=null) FormParsElem.accept(visitor);
        if(FormParsListMore!=null) FormParsListMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParsElem!=null) FormParsElem.traverseTopDown(visitor);
        if(FormParsListMore!=null) FormParsListMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParsElem!=null) FormParsElem.traverseBottomUp(visitor);
        if(FormParsListMore!=null) FormParsListMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsList(\n");

        if(FormParsElem!=null)
            buffer.append(FormParsElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsListMore!=null)
            buffer.append(FormParsListMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsList]");
        return buffer.toString();
    }
}
