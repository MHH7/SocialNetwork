package Event.Texts.Message;

public class NewMessageEvent {
    private final String text;
    private final String image;

    public NewMessageEvent(String text,String image){
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }
}
