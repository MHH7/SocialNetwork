package Event.Texts.Tweet;

import Model.Tweet;

public class SaveEvent {
    private final Tweet tweet;

    public SaveEvent(Tweet tweet){
        this.tweet = tweet;
    }

    public Tweet getTweet() {
        return tweet;
    }
}
