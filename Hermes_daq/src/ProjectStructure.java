import java.io.Serializable;

public class ProjectStructure implements Serializable {
    public ProjectStructure(){

    }

    public ProjectStructure(String code, String ui, String projectName) {
        codeFile = code;
        uiFile = ui;
        this.projectName = projectName;
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
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
