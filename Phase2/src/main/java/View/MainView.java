package View;

import Controller.Pages.Login;
import Graphic.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Stack;

public class MainView{
    private Stage mainStage;
    private static MainView MV;
    private final Stack<String> all;
    static private final Logger logger = LogManager.getLogger(MainView.class);

    private MainView(){
        all = new Stack<>();
    }

    public static MainView getMV(){
        if(MV == null)MV = new MainView();
        return MV;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setScene(String scene) throws IOException {
        String name = "";
        for(int i = 0;i < scene.length();i++){
            if(scene.charAt(i) == '.' || scene.charAt(i) == '/')break;
            name += scene.charAt(i);
        }
        logger.info("Scene changed to " + name);
        all.push(scene);
        Parent pane = FXMLLoader.load(Main.class.getResource(scene));
        mainStage.setScene(new Scene(pane));
    }

    public void back() throws IOException{
        all.pop();
        Parent pane = FXMLLoader.load(Main.class.getResource(all.lastElement()));
        mainStage.setScene(new Scene(pane));
    }
}
