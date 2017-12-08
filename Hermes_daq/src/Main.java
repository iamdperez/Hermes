
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;


public class Main extends Application {
    private final int windowsHeight = 768;
    private final int windowsWidth = 1366;
    private CodeEditor codeEditor;
    private final Map<String, String> icons;
    private String _currentProjectPath;
    private ProjectStructure _currentProjectStructure;
    private TreeItem<String> _rootTreeVItem;

    public Main() throws IOException {
        codeEditor = new CodeEditor();
        Gson gSon = new Gson();
        Type listType = new TypeToken<Map<String, String>>() {
        }.getType();
        icons = gSon.fromJson(getIconJson(), listType);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hermes DAQ");
        /*======= ui area ========*/
        BorderPane border = new BorderPane();
        border.setMinSize(windowsWidth, windowsHeight);
        border.setMaxSize(windowsWidth, windowsHeight);
        border.setTop(addAnchorPane(addMenuTop(primaryStage)));
        border.setBottom(addAnchorPane(addConsoleBottom()));
        border.setLeft(addAnchorPane(addTreeViewLeft()));
        border.setCenter(addAnchorPane(addWorkSpaceTabsCenter()));
        border.setRight(addAnchorPane(addUiElementsRight()));

        /*\====== ui area ========*/

        // Render screen
        Scene scene = new Scene(border);
        scene.getStylesheets().add(CodeEditor.class.getResource("/resources/uiStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        //        primaryStage.setMaximized(true);
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }


    private HBox addWorkSpaceTabsCenter() {
        HBox hb = new HBox();
        hb.setPadding(new Insets(10));
        hb.setSpacing(10);

        TabPane tabPane = new TabPane();
        Tab code = new Tab("Code");
        StackPane codeView = codeEditor.getEditorArea();
        HBox hbox = new HBox();
        hbox.getChildren().add(codeView);
        hbox.setAlignment(Pos.CENTER);
        HBox.setHgrow(codeView, Priority.ALWAYS);
        code.setContent(hbox);
        Tab design = new Tab("Design");

        tabPane.getTabs().addAll(code, design);

        hb.getChildren().addAll(tabPane);
        HBox.setHgrow(tabPane, Priority.ALWAYS);
        return hb;
    }

    private VBox addUiElementsRight() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);


        return vbox;
    }

    private VBox addTreeViewLeft() {
        _rootTreeVItem = new TreeItem<>("Project name",
                getSvgIcon("folder","orange","darkorange"));
        _rootTreeVItem.setExpanded(true);
        TreeView<String> treeView = new TreeView<>(_rootTreeVItem);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        vbox.getChildren().addAll(treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        return vbox;
    }

    private HBox addConsoleBottom() {
        TextArea textArea = new TextArea();

        HBox hb = new HBox();
        hb.setPadding(new Insets(10, 10, 10, 10));
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(10);
        hb.getChildren().addAll(textArea);
        HBox.setHgrow(textArea, Priority.ALWAYS);
        return hb;
    }

    private static SVGPath createPath(String d, String fill, String hoverFill) {
        SVGPath path = new SVGPath();
        path.getStyleClass().add("svg");
        path.setContent(d);
        path.setStyle("-fill:" + fill + ";-hover-fill:" + hoverFill + ';');
        return path;
    }

    private HBox addMenuTop(Stage stage) {
        MenuBar menuBar = new MenuBar();

        Menu menuFile = getMenuFile(stage);

        Menu menuBuild = new Menu("Build");

        menuBar.getMenus().addAll(menuFile, menuBuild);

        HBox hb = new HBox();
        hb.setPadding(new Insets(0));
        hb.setSpacing(10);
        hb.getChildren().addAll(menuBar);
        HBox.setHgrow(menuBar, Priority.ALWAYS);
        return hb;
    }

    private Menu getMenuFile(Stage stage) {
        Menu menuFile = new Menu("File");

        MenuItem openProject = new MenuItem("Open project",
                getSvgIcon("folder-open", "orange", "darkorange"));
        openProject.setOnAction(actionEvent -> {
            final FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser, "Open Project");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                openFile(file);
            }
        });

        MenuItem newProject = new MenuItem("New Project",
                getSvgIcon("folder-add", "orange", "darkorange"));
        newProject.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser, "Save project");
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                try {
                    file.mkdir();
                    ProjectStructure ps = new ProjectStructure("code", "ui", file.getName());
                    writeFileObject(file, ".hdaq", ps);

                    writeCodeFile(file.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        MenuItem saveProject = new MenuItem("Save Project",
                getSvgIcon("floppy-disk", "blue", "darkblue"));
        saveProject.setOnAction(actionEvent -> {
            saveCode();
        });

        menuFile.getItems().addAll(openProject, newProject, saveProject);
        return menuFile;
    }

    private void saveCode() {
        try {

            String content = codeEditor.getText();
            File file = new File(_currentProjectPath
                    +"/"+_currentProjectStructure.getCodeFile()+".hc");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeCodeFile(String filePath) throws IOException {
        String content = readFile("src/resources/projectFormat.txt");
        Files.write(Paths.get(filePath + "/code.hc"), content.getBytes());
    }

    private void writeFileObject(File file, String extension, Object object) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(
                file.getAbsolutePath() + "/" + file.getName() + extension);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(object);
        out.close();
        fileOut.close();
    }

    private void configureFileChooser(final FileChooser fileChooser, String title) {
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        if (!title.equals("Save project"))
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Hermes_Project", "*.hdaq")
            );
    }

    private void openFile(File file) {
        try {
            FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
            String path = file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(file.getName()));
            _currentProjectPath = path;
            ObjectInputStream in = new ObjectInputStream(fileIn);
            _currentProjectStructure = (ProjectStructure) in.readObject();

            _rootTreeVItem.setValue(_currentProjectStructure.getProjectName());
            TreeItem<String> codeItem = new TreeItem<>(_currentProjectStructure.getCodeFile(),
                    getSvgIcon("file","blue","darkblue"));
            _rootTreeVItem.getChildren().addAll(codeItem);

            _rootTreeVItem.setValue(_currentProjectStructure.getProjectName());
            TreeItem<String> uiItem = new TreeItem<>(_currentProjectStructure.getUiFile(),
                    getSvgIcon("uiApp","red","darkred"));
            _rootTreeVItem.getChildren().addAll(uiItem);

            String code = readFile(_currentProjectPath+"/"
                    +_currentProjectStructure.getCodeFile()+".hc",true);
            codeEditor.setText(code);
            in.close();
            fileIn.close();
        } catch (Exception ex) {

        }
    }

    private Button getSvgIcon(String iconName, String color, String hoverColor) {
        Group svg = new Group(
                createPath(icons.get(iconName), color, hoverColor)
        );
        Bounds bounds = svg.getBoundsInParent();
        double scale = Math.min(20 / bounds.getWidth(), 20 / bounds.getHeight());
        svg.setScaleX(scale);
        svg.setScaleY(scale);

        Button btn = new Button();
        btn.setGraphic(svg);
        btn.setMaxSize(20, 20);
        btn.setMinSize(20, 20);
        btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        return btn;
    }

    private AnchorPane addAnchorPane(Node uiElement) {
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.getChildren().addAll(uiElement);
        AnchorPane.setBottomAnchor(uiElement, 10.0);
        AnchorPane.setRightAnchor(uiElement, 10.0);
        AnchorPane.setLeftAnchor(uiElement, 10.0);
        AnchorPane.setTopAnchor(uiElement, 10.0);
        return anchorpane;
    }

    public String getIconJson() throws IOException {
        return readFile("src/resources/icons.json");
    }

    private String readFile(String filePath, boolean eof) throws IOException {
        StringBuffer sb = new StringBuffer();
        Files.readAllLines(Paths.get(filePath)).forEach(s -> sb.append(eof ? s +"\r\n" : s));
        return sb.toString();
    }

    private String readFile(String filePath) throws IOException {
        return readFile(filePath,false);
    }
}
