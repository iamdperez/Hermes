import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import parser.exeptions.SemanticException;
import parser.parserSettings.ParserSettings;
import parser.tree.statements.ProgramNode;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import serialCommunication.SerialCommException;
import ui.*;
import ui.Console;
import ui.ElectronicElement.*;
import ui.ElectronicElement.ToggleButton;


public class Main extends Application {
    private final int windowsHeight = 768;
    private final int windowsWidth = 1366;
    private CodeEditor codeEditor;

    private String _currentProjectPath;
    private ProjectStructure _currentProjectStructure;
    private TreeItem<String> _rootTreeVItem;
    private Console _consoleArea;
    private ParserSettings _parserSettings;
    private ParserCode parserCode;
    private Pane _canvas;
    private ArrayList<ElectronicElement> electronicElements;
    private Gson gSon;
    public Main() throws IOException {
        codeEditor = new CodeEditor();
        gSon = new Gson();
        _canvas = new Pane();
        electronicElements = new ArrayList<>();

        Type parserSettingsType = new TypeToken<ParserSettings>() {
        }.getType();
        _parserSettings = gSon.fromJson(
                UiUtils.getInstance().loadResource("/parserSettings.json"), parserSettingsType);
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
        scene.getStylesheets().add(Main.class.getResource("/uiStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        //        primaryStage.setMaximized(true);
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    private HBox addWorkSpaceTabsCenter() throws IOException {
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
        BorderPane content = new BorderPane();

//        _canvas = new Pane();
        _canvas.getStyleClass().add("canvas");
        content.setCenter(_canvas);
        design.setContent(content);

        tabPane.getTabs().addAll(code, design);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        hb.getChildren().addAll(tabPane);
        HBox.setHgrow(tabPane, Priority.ALWAYS);
        return hb;
    }

    private VBox addUiElementsRight() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        Label cbl = new Label("Element");
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
                "Led", "Switch", "Push Button")
        );

        Label nl = new Label("Name");
        TextField name = new TextField();

        Button button = new Button("Add");
        button.setOnAction(e -> {
            String item = (String) cb.getSelectionModel().getSelectedItem();
            if (item == null)
                return;
            try {
                ElectronicElement ee = null;
                switch (item) {
                    case "Led":
                        ee = new Led(name.getText(), o -> removeElectronicElement(o));
                        break;
                    case "Switch":
                        ee = new ToggleSwitch(name.getText(), o -> removeElectronicElement(o));
                        break;
                    case "Push Button":
                        ee = new ToggleButton(name.getText(), o -> removeElectronicElement(o));
                        break;
                }
                _canvas.getChildren().add(ee.drawElement());
                electronicElements.add(ee);
                codeEditor.appendText(ee.getEventsFunctions());

            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        });
        vbox.getChildren().addAll(cbl, cb, nl, name, button);


        return vbox;
    }

    private void addElementToCanvas(ElementSerialized element){
        try {
            ElectronicElement ee = null;
            switch (element.getType()) {
                case "Led":
                    ee = new Led(element.getName(),s -> removeElectronicElement(s),element.getX(),element.getY());
                    break;
                case "Switch":
                    ee = new ToggleSwitch(element.getName(),s -> removeElectronicElement(s),element.getX(),element.getY());
                    break;
                case "Button":
                    ee = new ToggleButton(element.getName(),s -> removeElectronicElement(s),element.getX(),element.getY());
                    break;
            }
            _canvas.getChildren().add(ee.drawElement());
            electronicElements.add(ee);

        } catch (IOException e1) {
            System.out.println(e1.getMessage());
        }
    }

    public boolean removeElectronicElement(String name){

        Optional<ElectronicElement> element = electronicElements.stream().filter(o -> o.getName().equals(name)).findFirst();
        if(!element.isPresent())
            return false;
        ElectronicElement mainNode = element.get();
        _canvas.getChildren().remove(mainNode.drawElement());
        electronicElements.remove(mainNode);
        return true;
    }

    private VBox addTreeViewLeft() throws IOException {
        _rootTreeVItem = new TreeItem<>("Project name",
                UiUtils.getInstance().getSvgIcon("folder", "orange", "darkorange"));
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
        HBox hb = new HBox();
        TextArea console = new TextArea();
        _consoleArea = new Console(hb, console);
        return hb;
    }


    private HBox addMenuTop(Stage stage) throws IOException {
        MenuBar menuBar = new MenuBar();

        Menu menuFile = getMenuFile(stage);

        Menu menuBuild = getMenuBuild();

        menuBar.getMenus().addAll(menuFile, menuBuild);

        HBox hb = new HBox();
        hb.setPadding(new Insets(0));
        hb.setSpacing(10);
        hb.getChildren().addAll(menuBar);
        HBox.setHgrow(menuBar, Priority.ALWAYS);
        return hb;
    }

    private Menu getMenuBuild() throws IOException {
        Menu menuBuild = new Menu("Build");
        MenuItem run = new MenuItem("Run",
                UiUtils.getInstance().getSvgIcon("play", "green", "darkgreen"));
        run.setOnAction(actionEvent -> {
            try {
                new Thread(() -> {
                    _consoleArea.clear();
                    System.out.println("Compiling " + LocalDateTime.now());
                    saveCode();
                    saveUi();
                    ProgramNode program = null;
                    try {
                        program = parserCode.getAST();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("program is running");
                    try {
                        program.interpretCode();
                    } catch (SemanticException e) {
                        e.printStackTrace();
                    } catch (SerialCommException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Program finished...");
                }).start();

            } catch (Exception e) {
                System.out.println(e.toString());
                //TODO: show error
            }
        });

        MenuItem stop = new MenuItem("Stop",
                UiUtils.getInstance().getSvgIcon("stop", "#D46354", "#D46354"));
        stop.setOnAction(actionEvent -> {
            //TODO stop button
        });

        menuBuild.getItems().addAll(run, stop);
        return menuBuild;
    }

    private Menu getMenuFile(Stage stage) throws IOException {
        Menu menuFile = new Menu("File");

        MenuItem openProject = new MenuItem("Open project",
                UiUtils.getInstance().getSvgIcon("folder-open", "orange", "darkorange"));
        openProject.setOnAction(actionEvent -> {
            final FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser, "Open Project");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                openFile(file);
            }
        });

        MenuItem newProject = new MenuItem("New Project",
                UiUtils.getInstance().getSvgIcon("folder-add", "orange", "darkorange"));
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
                    writeUiFile(file.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        MenuItem saveProject = new MenuItem("Save Project",
                UiUtils.getInstance().getSaveIcon());
        saveProject.setOnAction(actionEvent -> {
            saveCode();
            saveUi();
        });

        menuFile.getItems().addAll(openProject, newProject, saveProject);
        return menuFile;
    }


    private void saveCode() {
        try {

            String content = codeEditor.getText();
            File file = new File(_currentProjectPath
                    + "/" + _currentProjectStructure.getCodeFile() + ".hc");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void saveUi(){
        try {

            String content = getElectronicElementsJson();
            File file = new File(_currentProjectPath
                    + "/" + _currentProjectStructure.getUiFile() + ".hu");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private void writeCodeFile(String filePath) throws IOException {
        String content = UiUtils.getInstance().loadResource("/projectFormat.txt", true);
        String p = filePath + "/code.hc";
        Files.write(Paths.get(filePath + "/code.hc"), content.getBytes());
    }

    private void writeUiFile(String filePath) throws IOException{
        String content = getElectronicElementsJson();
        String p = filePath + "/ui.hu";
        writeFile(p,content);
    }

    private String getElectronicElementsJson(){
        ArrayList<ElementSerialized> elements = new ArrayList<>();
        electronicElements.stream().forEach( o ->
                elements.add(new ElementSerialized(o.getName(), o.getX(),o.getY(), o.getType())));
        return gSon.toJson(elements);
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
                    UiUtils.getInstance().getSvgIcon("file", "blue", "darkblue"));
            _rootTreeVItem.getChildren().addAll(codeItem);

            _rootTreeVItem.setValue(_currentProjectStructure.getProjectName());
            TreeItem<String> uiItem = new TreeItem<>(_currentProjectStructure.getUiFile(),
                    UiUtils.getInstance().getSvgIcon("uiApp", "red", "darkred"));
            _rootTreeVItem.getChildren().addAll(uiItem);

            String code = readFile(_currentProjectPath + "/"
                    + _currentProjectStructure.getCodeFile() + ".hc", true);
            codeEditor.setText(code);

            String electronicJson = readFile(_currentProjectPath + "/"
                    + _currentProjectStructure.getUiFile() + ".hu", true);

            Type listElements = new TypeToken<ArrayList<ElementSerialized>>(){}.getType();
            ArrayList<ElementSerialized> es = gSon.fromJson(electronicJson,listElements);
            es.forEach( o -> addElementToCanvas(o));

            parserCode = new ParserCode(Paths.get(_currentProjectPath + "\\"
                    + _currentProjectStructure.getCodeFile() + ".hc").toString(), _parserSettings);
            in.close();
            fileIn.close();

         } catch (Exception ex) {
            System.out.println(ex);
        }
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

    private String readFile(String filePath, boolean eof) throws IOException {
        StringBuffer sb = new StringBuffer();
        Files.readAllLines(Paths.get(filePath)).forEach(s -> sb.append(eof ? s + "\r\n" : s));
        return sb.toString();
    }

    private void writeFile(String path, String content) throws IOException{
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }
}
