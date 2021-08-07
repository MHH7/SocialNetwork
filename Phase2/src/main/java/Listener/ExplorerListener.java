package Listener;

import Controller.DataBase.DataBase;
import Controller.Pages.Explorer;
import Controller.Pages.Login;
import Model.User;
import View.ExplorerViewer;
import View.Profile.MiniProfileViewer;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;

public class ExplorerListener {
    private final Explorer explorer;
    private final ExplorerViewer explorerViewer;
    static private final Logger logger = LogManager.getLogger(ExplorerListener.class);

    public ExplorerListener(ExplorerViewer explorerViewer){
        explorer = new Explorer();
        this.explorerViewer = explorerViewer;
    }

    public void search(String user){
        User found = explorer.search(user);
        if(found == null){
            explorerViewer.getLabel().setVisible(true);
            explorerViewer.getLabel().setText("User you looking for doesn't exist!");
            explorerViewer.getPane().setVisible(false);
        }
        else{
            explorerViewer.getLabel().setVisible(false);
            Pane pane = explorerViewer.getPane();
            pane.getChildren().clear();
            pane.setVisible(true);
            MiniProfileViewer miniProfileViewer = new MiniProfileViewer(found);
            pane.getChildren().add(miniProfileViewer.getImageView());
            pane.getChildren().add(miniProfileViewer.getLabel());
            pane.getChildren().add(miniProfileViewer.getButton());
            try {
                if(!(found.getProfilePath() == null))miniProfileViewer.getImageView().setImage(new Image(found.getProfilePhoto()));
            }
            catch (MalformedURLException e){
                logger.info("Profile image file did not found!");
            }
        }
    }
}
