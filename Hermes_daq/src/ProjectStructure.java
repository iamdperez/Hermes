import java.io.Serializable;

public class ProjectStructure implements Serializable {
    public ProjectStructure(){

    }

    public ProjectStructure(String code, String ui) {
        codeFile = code;
        uiFile = ui;
    }

    public String getUiFile() {
        return uiFile;
    }

    public void setUiFile(String uiFile) {
        this.uiFile = uiFile;
    }

    public String getCodeFile() {
        return codeFile;
    }

    public void setCodeFile(String codeFile) {
        this.codeFile = codeFile;
    }

    private String uiFile;
    private String codeFile;

}
