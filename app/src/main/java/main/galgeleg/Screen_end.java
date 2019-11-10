package main.galgeleg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Screen_end extends AppCompatActivity implements View.OnClickListener {

        Button btn1Win;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        Boolean yourDataObject = null;

        if (getIntent().hasExtra("gamestate")) {
            yourDataObject = getIntent().getBooleanExtra("gamestate", false);
            if (yourDataObject) {
                setContentView(R.layout.layout_screen_winner);
                btn1Win = findViewById(R.id.accept);
                btn1Win.setOnClickListener(this);

                EditText tester= findViewById(R.id.nameview);
                showKeyboard(tester, this);

            } else {
                setContentView(R.layout.layout_dialog_loser);
            }
        } else {
            throw new IllegalArgumentException("Activity cannot find  extras " + "gamestate");
        }





    }


    public static void showKeyboard(EditText mEtSearch, Context context) {
        mEtSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideSoftKeyboard(EditText mEtSearch, Context context) {
        mEtSearch.clearFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0);


    }

    @Override
    public void onClick(View v) {
        if(v == btn1Win){
            EditText tester= findViewById(R.id.nameview);
            hideSoftKeyboard(tester, this);
            int score = getIntent().getExtras().getInt("score");
            Intent i = new Intent(this, HighScore.class);
            i.putExtra("name",tester.getText()+"");
            i.putExtra("score",score);
            startActivity(i);
        }
    }
}

