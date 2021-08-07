package Event.Texts.Tweet;

public class NewTweetEvent {
    private final String text;
    private final String image;

    public NewTweetEvent(String text,String image){
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
