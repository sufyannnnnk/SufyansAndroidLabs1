package algonquin.cst2335.khan0494;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**This page contains password validation function and Onclick listener for toast messages.
 * @author Sufyan Khan
 * @param <btn>
 * @version 1.0
 */
public class MainActivity<btn> extends AppCompatActivity {
    /**
     * This functions checks for special character
     *
     * @param c
     * @return
     */
    boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '?':
            case '*':
                return true;
            default:
                return false;
        }
    }

    /**
     * This function checks the complexity of password
     *
     * @param pw The string object that we are checking
     * @return Returns True
     */
    boolean checkPasswordComplexity(String pw) {
        /*Holds the value of Text View*/
        TextView tv = findViewById(R.id.textView);
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);
            if (Character.isDigit(c)) {
                foundNumber = true;
            } else if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else if (isSpecialCharacter(c)) {
                foundSpecial = true;
            }
        }

        if (!foundUpperCase) {
            tv.setText("You shall not pass!");
            Toast.makeText(getApplicationContext(), "Upper case letter is missing", Toast.LENGTH_SHORT).show();// Say that they are missing an upper case letter;
            return false;
        } else if (!foundLowerCase) {
            tv.setText("You shall not pass!");
            Toast.makeText(getApplicationContext(), "Lower case letter is missing", Toast.LENGTH_SHORT).show(); // Say that they are missing a lower case letter;
            return false;
        } else if (!foundNumber) {
            tv.setText("You shall not pass!");
            Toast.makeText(getApplicationContext(), "Number is missing", Toast.LENGTH_SHORT).show(); // Say that they are missing a number;
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(getApplicationContext(), "Special character is missing", Toast.LENGTH_SHORT).show(); // Say that they are missing a character;
            tv.setText("You shall not pass!");
            return false;
        } else
            tv.setText("Your password is Complex Enough");
        return true; //only get here if they're all true
    }

    /**
     * Variables are declared in this function and methods are
     * called to check the complexity of password.
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /**This holds the value for button*/
        Button btn = findViewById(R.id.button);
      /**This holds the value for Edit Text*/
        EditText et = findViewById(R.id.editTextTextPersonName3);
        btn.setOnClickListener(clk -> {
            String password = et.getText().toString();
            checkPasswordComplexity(password);
        });
    }
}



