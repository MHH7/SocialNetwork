package Listener.Texts;

import Model.Request;

public class RequestListener {
    private final Request request;

    public RequestListener(Request request){
        this.request = request;
    }

    public void accept(){
        request.accept();
    }

    public void reject(){
        request.reject();
    }

    public void anonynousReject(){
        request.anonymousReject();
    }
}
