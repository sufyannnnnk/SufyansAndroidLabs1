package algonquin.cst2335.khan0494;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel {

        public MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData<>();
    public static MutableLiveData<ChatMessage> selectedMessage = new MutableLiveData< >();
    }
