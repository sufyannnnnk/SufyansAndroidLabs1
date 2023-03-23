package algonquin.cst2335.khan0494;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import algonquin.cst2335.khan0494.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.khan0494.databinding.RecieveMessageBinding;
import algonquin.cst2335.khan0494.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {
    private ArrayList<ChatMessage> messageList;
    ActivityChatRoomBinding binding;
    private RecyclerView.Adapter myAdapter;
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
    String currentDateAndTime = sdf.format(new Date());
    ChatMessage chat = new ChatMessage("","",false);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        chat = new ViewModelProvider(this).get(ChatMessage.class);
        messageList = chat.messages.getValue();
        setContentView(binding.getRoot());
        if(messageList == null)
        {
            chat.messages.postValue( messageList = new ArrayList<ChatMessage>());
        }
        binding.sendButton.setOnClickListener( click ->{
            String message = binding.textInput.getText().toString();
            boolean sentButton = true;
            chat = new ChatMessage(message,currentDateAndTime, sentButton );
            messageList.add(chat);
            myAdapter.notifyItemInserted(messageList.size()-1);
            binding.textInput.setText("");

        });
        binding.button.setOnClickListener( click ->{
            String message = binding.textInput.getText().toString();
            boolean sentButton = false;
            chat = new ChatMessage(message,currentDateAndTime, sentButton );
            messageList.add(chat);
            myAdapter.notifyItemInserted(messageList.size()-1);
            binding.textInput.setText("");

        });

        binding.theRecycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if(viewType==0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(),
                            parent, false);
                    View root = binding.getRoot();
                    return new MyRowHolder(root);
                }
                else{
                    RecieveMessageBinding binding = RecieveMessageBinding.inflate(getLayoutInflater(),
                            parent, false
                    );
                    View root = binding.getRoot();
                    return new MyRowHolder(root);
                }
            }
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage chatMessage = messageList.get(position);
                holder.messageText.setText(chatMessage.getMessage());
                holder.timeText.setText(chatMessage.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messageList.size();
            }
            @Override
            public int getItemViewType(int position) {
                ChatMessage chatMessage = messageList.get(position);
                if (chatMessage.isSentButton()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        binding.theRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }
    class MyRowHolder extends RecyclerView.ViewHolder {
        public TextView messageText;
        public TextView timeText;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            timeText =itemView.findViewById(R.id.timeText);
        }
    }
}

