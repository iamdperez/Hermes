package parser.tree;

public class Location {
    private final int line;
    private final int column;

    public Location(int line, int column){
        this.line = line;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }
}
