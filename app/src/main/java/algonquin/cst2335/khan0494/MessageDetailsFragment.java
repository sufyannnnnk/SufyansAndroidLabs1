package algonquin.cst2335.khan0494;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.khan0494.ChatMessage;
import algonquin.cst2335.khan0494.databinding.DetailsLayoutBinding;


public class MessageDetailsFragment {
    public static class MessageDetailedFragment extends Fragment {

        ChatMessage selected;

        public MessageDetailedFragment(ChatMessage m){
            selected = m;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);


            binding.messageText.setText(selected.message);
            binding.timeText.setText(selected.getTimeSent());
            binding.databaseText.setText("Id " + selected.id );

            return binding.getRoot();
        }
    }
}