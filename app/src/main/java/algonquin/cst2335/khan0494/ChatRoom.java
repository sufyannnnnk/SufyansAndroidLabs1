package algonquin.cst2335.khan0494;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.khan0494.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.khan0494.databinding.RecieveMessageBinding;
import algonquin.cst2335.khan0494.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {
    ArrayList<ChatMessage> messages;
    ActivityChatRoomBinding binding;
    ChatViewModel chatModel;
    private RecyclerView.Adapter myAdapter;
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
    String currentDateAndTime = sdf.format(new Date());
    ChatMessage chat = new ChatMessage("", "", false);
    ChatMessageDAO mDAO;
    int position;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TextView messageText;
        messageText = binding.recycleView.findViewById(R.id.messageText);
        switch( item.getItemId() )
        {
            case R.id.item_1:

                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setTitle("Question:")
                        .setMessage("Do you want to delete the message: " + messageText.getText())
                        .setNegativeButton("No", (dialog, cl)-> {})
                        .setPositiveButton("Yes", (dialog, cl) -> {
                            Executor thread = Executors.newSingleThreadExecutor();
                            ChatMessage m = messages.get(position);
                            thread.execute(() -> {
                                mDAO.deleteMessage(m);
                            });

                            messages.remove(position);
                            myAdapter.notifyItemRemoved(position);
                            Snackbar.make(messageText,"You deleted message #"+ position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo",click ->{
                                        messages.add(position, m);
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();
                        })
                        .create().show();
                break;
            case R.id.about:
                Toast.makeText(this, "Version 1.0, created by Sufyan", Toast.LENGTH_LONG).show();
                break;
        }

        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        chatModel = new ViewModelProvider(this).get(ChatViewModel.class);
        messages = chatModel.messages.getValue();
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();
        chatModel.selectedMessage.observe(this, (newMessageValue) -> {
            MessageDetailsFragment.MessageDetailedFragment chatFragment = new MessageDetailsFragment.MessageDetailedFragment(newMessageValue);
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            tx.add(R.id.fragmentlocation, chatFragment).addToBackStack("null");
            tx.replace(R.id.fragmentlocation, chatFragment);
            tx.commit();
        });

        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);
        if (messages == null) {

            chatModel.messages.setValue(messages = new ArrayList<ChatMessage>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDAO.insertMessage(chat);
            });
        }
        binding.sendButton.setOnClickListener(click -> {
            String message = binding.textInput.getText().toString();
            boolean sentButton = true;
            chat = new ChatMessage(message, currentDateAndTime, sentButton);
            messages.add(chat);
            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.textInput.setText("");
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDAO.insertMessage(chat);
            });

        });
        binding.recievebutton.setOnClickListener(click -> {
            String message = binding.textInput.getText().toString();
            boolean sentButton = false;
            chat = new ChatMessage(message, currentDateAndTime, sentButton);
            messages.add(chat);
            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.textInput.setText("");
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDAO.insertMessage(chat);
            });

        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    View root = binding.getRoot();
                    return new MyRowHolder(root);
                } else {
                    RecieveMessageBinding binding = RecieveMessageBinding.inflate(getLayoutInflater(),
                            parent, false
                    );
                    View root = binding.getRoot();
                    return new MyRowHolder(root);
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage chatMessage = messages.get(position);
                holder.messageText.setText(chatMessage.getMessage());
                holder.timeText.setText(chatMessage.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage chatMessage = messages.get(position);
                if (chatMessage.isSentButton()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk ->  {

                position = getAbsoluteAdapterPosition();

                ChatMessage selected = messages.get(position);

                chatModel.selectedMessage.postValue(selected);


            });

            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);
        }
    }
}