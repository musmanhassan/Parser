package au.com.redenergy.model;

/**
 * Created by Usman on 7/05/2017.
 */
public class LineDTO extends  BaseDTO{

    private String line;

    public LineDTO(String line) {
        this.line = line;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
