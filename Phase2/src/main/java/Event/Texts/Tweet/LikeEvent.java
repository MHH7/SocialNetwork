package Event.Texts.Tweet;

import Model.Tweet;

public class LikeEvent {
    private final Tweet tweet;

    public LikeEvent(Tweet tweet){
        this.tweet = tweet;
    }

    public Tweet getTweet() {
        return tweet;
    }
}
