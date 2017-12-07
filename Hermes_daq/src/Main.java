
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private final int windowsHeight = 768;
    private final int windowsWidth = 1366;
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hermes DAQ");

        /*======= ui area ========*/
        BorderPane border = new BorderPane();
        border.setMinSize(windowsWidth,windowsHeight);
        border.setMaxSize(windowsWidth,windowsHeight);
        border.setTop(addAnchorPane(addMenuTop()));
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
        primaryStage.show();
    }

    private static final String sampleCode = "module patito;\n" +
            "initial\n" +
            "    device 'Due';\n" +
            "    a, b pin 10, 12;\n" +
            "    a, b istype output;\n" +
            "    c = [a,b];\n" +
            "endinitial\n" +
            "\n" +
            "main\n" +
            "    var x;\n" +
            "    x = 0;\n" +
            "    a = high;\n" +
            "    b = high;\n" +
            "    print a + \" \" + b;\n" +
            "    wait 5000;\n" +
            "    a = low;\n" +
            "    b = low;\n" +
            "    print a + \" \" + b;\n" +
            "    wait 2000;\n" +
            "    c = high;\n" +
            "    print a + \" \"+ b;\n" +
            "    wait 2000;\n" +
            "    c = low;\n" +
            "    wait 2000;\n" +
            "    print \"Terminado\";\n" +
            "endmain\n" +
            "\n" +
            "function terminado()\n" +
            "    var x;\n" +
            "    for(x = 0; x < 11; x++)\n" +
            "        if(x%2==0)\n" +
            "            print \"x es par valor= \" + x;\n" +
            "        endif\n" +
            "    endfor\n" +
            "    print \"llamando terminado 2\";\n" +
            "    terminado2(10);\n" +
            "endfunction\n" +
            "\n" +
            "function terminado2(a)\n" +
            "    var x;\n" +
            "    x =0;\n" +
            "    while (x < a)\n" +
            "        if(x == 5)\n" +
            "            print \"x llego a 5\";\n" +
            "            break;\n" +
            "        endif\n" +
            "        print x;\n" +
            "        x++;\n" +
            "    endwhile\n" +
            "    print \"2 es mayo que 5 = \"+(terminado2(2,5) ? 2: 5);\n" +
            "endfunction\n" +
            "\n" +
            "function terminado2(a,b)\n" +
            "    return a > b;\n" +
            "endfunction \n" +
            "\n" +
            "endmodule";
    private HBox addWorkSpaceTabsCenter() {
        HBox hb = new HBox();
        hb.setPadding(new Insets(10));
        hb.setSpacing(10);

        TabPane tabPane = new TabPane();
        Tab code = new Tab("Code");

        CodeEditor codeEditor = new CodeEditor();
        StackPane codeView = codeEditor.getEditorArea();
        codeEditor.setText(sampleCode);
        HBox hbox = new HBox();
        hbox.getChildren().add(codeView);
        hbox.setAlignment(Pos.CENTER);
        HBox.setHgrow(codeView,Priority.ALWAYS);
        code.setContent(hbox);
        Tab design = new Tab("Design");

        tabPane.getTabs().addAll(code,design);

        hb.getChildren().addAll(tabPane);
        HBox.setHgrow(tabPane,Priority.ALWAYS);
        return hb;
    }

    private VBox addUiElementsRight() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);


        return vbox;
    }

    private VBox addTreeViewLeft() {
        TreeItem<String> rootItem = new TreeItem<>("Project name");
        rootItem.getChildren().addAll(new TreeItem<>("Test"));

        rootItem.setExpanded(true);
        TreeView<String> treeView = new TreeView<>(rootItem);


        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        vbox.getChildren().addAll(treeView);
        VBox.setVgrow(treeView,Priority.ALWAYS);
        return vbox;
    }

    private HBox addConsoleBottom() {
        TextArea textArea = new TextArea();

        HBox hb = new HBox();
        hb.setPadding(new Insets(10, 10, 10, 10));
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(10);
        hb.getChildren().addAll(textArea);
        HBox.setHgrow(textArea,Priority.ALWAYS);
        return hb;
    }

    private HBox addMenuTop() {
        MenuBar menuBar = new MenuBar();

        Menu menuFile = new Menu("File");
        MenuItem openProject = new MenuItem("Open project");
        openProject.setOnAction(actionEvent -> {
            // openFolder code
        });

        menuFile.getItems().addAll(openProject);

        Menu menuBuild = new Menu("Build");

        menuBar.getMenus().addAll(menuFile, menuBuild);

        HBox hb = new HBox();
        hb.setPadding(new Insets(0));
        hb.setSpacing(10);
        hb.getChildren().addAll(menuBar);
        HBox.setHgrow(menuBar,Priority.ALWAYS);
        return hb;
    }

    private AnchorPane addAnchorPane(Node uiElement) {
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.getChildren().addAll(uiElement);
        AnchorPane.setBottomAnchor(uiElement, 10.0);
        AnchorPane.setRightAnchor(uiElement, 10.0);
        AnchorPane.setLeftAnchor(uiElement,10.0);
        AnchorPane.setTopAnchor(uiElement, 10.0);
        return anchorpane;
    }
}
