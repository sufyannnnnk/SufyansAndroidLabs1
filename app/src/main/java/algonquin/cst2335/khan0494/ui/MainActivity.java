package algonquin.cst2335.khan0494.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.khan0494.data.MainViewModel;
import algonquin.cst2335.khan0494.databinding.ActivityMainBinding;
//import data.MainViewModel;


public class MainActivity extends AppCompatActivity {
    //private MainViewModel model;
    private MainViewModel model;
    private ActivityMainBinding variableBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        model.isSelected.observe(this,selected ->{
            variableBinding.mySwitch.setChecked(selected);
            Context context = getApplicationContext();
            CharSequence text = "The value is now: " + selected;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            variableBinding.myCheckBox.setChecked(selected);
            variableBinding.myRadioButton.setChecked(selected);
        });

//        Context context = getApplicationContext();
//        String selected = null;
//        CharSequence text = "The value is now:" + selected;
//        int duration = Toast.LENGTH_SHORT;
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();


        Object isChecked;
//        CompoundButton.OnCheckedChangeListener btn;
//        variableBinding.mySwitch.setOnCheckedChangeListener(btn, isSelected); {
////            Toast
////                    .makeText(MainActivity.this,"This is the message on screen",Toast.LENGTH_LONG)
////                    .show();
//        });

        variableBinding.mySwitch.setOnCheckedChangeListener( (btn, isSelected) -> {
            model.isSelected.postValue(isSelected);
        });

        variableBinding.myCheckBox.setOnCheckedChangeListener( (btn, isSelected) -> {
            model.isSelected.postValue(isSelected);
        });

        variableBinding.myRadioButton.setOnCheckedChangeListener( (btn, isSelected) -> {
            model.isSelected.postValue(isSelected);
        });

        variableBinding.myimagebutton.setOnClickListener(imageBtn ->{
            int width = variableBinding.myimagebutton.getWidth();
            int height = variableBinding.myimagebutton.getHeight();
            Context context = getApplicationContext();
            CharSequence text = "The width = " + width + " and height = " + height;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });



        variableBinding.textview.setText(model.editString);
        variableBinding.mybutton.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v){
                model.editString= variableBinding.myedittext.getText().toString();
                variableBinding.textview.setText("Your edit text has:"+ model.editString);
                variableBinding.mybutton.setText("Your edit text has:"+model.editString);
                variableBinding.myedittext.setText("Your edit text has:"+ model.editString);
            }

        });
    }
}