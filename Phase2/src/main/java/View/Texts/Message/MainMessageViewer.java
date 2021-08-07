package View.Texts.Message;

import Controller.DataBase.DataBase;
import Controller.Pages.Settings;
import Model.Message;
import View.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMessageViewer implements Initializable {
    private Message message;

    @FXML
    private ImageView imagebox;

    @FXML
    private Label label;

    @FXML
    private TextArea newTextBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        message = DataBase.getDB().getCurrentMessage();
        label.setText(message.show());
        if(!(message.getImage() == null)){
            try {
                imagebox.setImage(new Image(message.getImagePhoto()));
            } catch (MalformedURLException e) {
            }
        }
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }

    @FXML
    void delete() throws IOException{
        message.delete();
        Return();
    }

    @FXML
    void edit() {
        message.edit(newTextBox.getText());
    }

    @FXML
    void save() {
        message.save();
    }
}
