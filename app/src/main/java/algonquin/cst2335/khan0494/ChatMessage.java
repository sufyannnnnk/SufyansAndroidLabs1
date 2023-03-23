package algonquin.cst2335.khan0494;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;

import java.util.ArrayList;

public class ChatMessage extends ViewModel {

    private String message;
    private String timeSent;
    private boolean isSentButton;

    public MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData<ArrayList<ChatMessage>>();

    public ChatMessage() {
    }

    public ChatMessage(String m, String t, boolean sent) {
        message = m;
        timeSent = t;
        isSentButton = sent;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSentButton() {
        return isSentButton;
    }
}