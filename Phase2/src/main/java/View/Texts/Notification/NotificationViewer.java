package View.Texts.Notification;

import Model.Notification;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class NotificationViewer {
    private final Notification notification;
    private Label label;
    private Pane pane;

    public NotificationViewer(Notification notification){
        this.notification = notification;
        startLabel();
    }

    private void startLabel(){
        label = new Label();
        label.setAlignment(Pos.TOP_LEFT);
        label.setLayoutX(100);
        label.setLayoutY(0);
        label.setText(notification.show());
        label.setVisible(true);
    }



    public Pane getPane(){
        pane = new Pane();
        pane.getChildren().add(label);
        pane.setVisible(true);
        return pane;
    }
}
