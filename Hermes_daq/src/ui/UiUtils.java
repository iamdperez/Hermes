package ui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.shape.SVGPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

public class UiUtils {
    private static UiUtils instance;
    private Gson gSon;
    private Map<String, String> icons;
    public static synchronized UiUtils getInstance() throws IOException {
        if (instance == null) {
            instance =  new UiUtils();
            return instance;
        }
        return instance;
    }
    private UiUtils() throws IOException {
        gSon = new Gson();
        loadIcons();
    }

    private void loadIcons() throws IOException {
        Type listType = new TypeToken<Map<String, String>>() {
        }.getType();
        icons = gSon.fromJson(getIconJson(), listType);
    }

    public String getIconJson() throws IOException {
        return loadResource("/icons.json");
    }

    public String loadResource(String filePath) throws IOException {
        InputStream res = UiUtils.class.getResourceAsStream(filePath);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(res));
        StringBuffer  sb = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }

    public Button getSaveIcon(){
        Group svg = new Group(
                createPath(icons.get("save-1"),"#324d5b","#324d5b"),
                createPath(icons.get("save-2"),"#ccd0d2","#ccd0d2"),
                createPath(icons.get("save-3"),"#e4e7e7","#e4e7e7"),
                createPath(icons.get("save-4"),"#2b414d","#2b414d"),
                createPath(icons.get("save-5"),"#ccd0d2","#ccd0d2")
        );
        Bounds bounds = svg.getBoundsInParent();
        double scale = Math.min(10 / bounds.getWidth(), 10 / bounds.getHeight());
        svg.setScaleX(scale);
        svg.setScaleY(scale);

        Button btn = new Button();
        btn.setGraphic(svg);
        btn.setMaxSize(10, 10);
        btn.setMinSize(10, 10);
        btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        return btn;
    }

    public Group getSwitchOff(){
        Group svg = new Group(
                createPath(icons.get("switch-off-1"),"#c7cac7","#c7cac7"),
                createPath(icons.get("switch-off-2"),"#ffffff","#ffffff"),
                createPath(icons.get("switch-off-3"),"",""),
                createPath(icons.get("switch-off-4"),"",""),
                createPath(icons.get("switch-off-5"),"","")
        );
        Bounds bounds = svg.getBoundsInParent();
        double scale = Math.min(60 / bounds.getWidth(), 60 / bounds.getHeight());
        svg.setScaleX(scale);
        svg.setScaleY(scale);
        return svg;
    }

    public Group getSwitchOn(){
        Group svg = new Group(
                createPath(icons.get("switch-on-1"),"#61b872","#61b872"),
                createPath(icons.get("switch-on-2"),"#ffffff","#ffffff"),
                createPath(icons.get("switch-on-3"),"",""),
                createPath(icons.get("switch-on-4"),"",""),
                createPath(icons.get("switch-on-5"),"","")
        );
        Bounds bounds = svg.getBoundsInParent();
        double scale = Math.min(60 / bounds.getWidth(), 60 / bounds.getHeight());
        svg.setScaleX(scale);
        svg.setScaleY(scale);
        return svg;
    }

    public SVGPath createPath(String d, String fill, String hoverFill) {
        SVGPath path = new SVGPath();
        path.getStyleClass().add("svg");
        path.setContent(d);
        String style = "";
        style += "-fill:" + (fill.isEmpty() ? "#ff000000" : fill) +";";
        style += "-hover-fill:" + (hoverFill.isEmpty() ? "#ff000000" : hoverFill) + ';';
        path.setStyle(style);
        return path;
    }

    public String getIcon(String name){
        return icons.get(name);
    }

    public Button getSvgIcon(String iconName, String color, String hoverColor) throws IOException {
        Group svg = new Group(
                UiUtils.getInstance().createPath(UiUtils.getInstance().getIcon(iconName), color, hoverColor)
        );
        Bounds bounds = svg.getBoundsInParent();
        double scale = Math.min(10 / bounds.getWidth(), 10 / bounds.getHeight());
        svg.setScaleX(scale);
        svg.setScaleY(scale);

        Button btn = new Button();
        btn.setGraphic(svg);
        btn.setMaxSize(10, 10);
        btn.setMinSize(10, 10);
        btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        return btn;
    }
}
