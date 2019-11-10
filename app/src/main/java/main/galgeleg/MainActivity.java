package main.galgeleg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1;
    Button btn2;
    GaleLogik spil = GaleLogik.get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spil.hentordfradrViaThread();
        try {
            spil.t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        btn1 = findViewById(R.id.playBtn);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.helpbtn);
        btn2.setOnClickListener(this);

    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        Dialog editNameDialogFragment = Dialog.newInstance("Some Title", "Dette er hangman spillet\n Tryk på start for at spille\n du skal gætte ordet der er på skærmen\n tryk på det bogstav du mener er i ordet før du løber tør for legmsdele", new GameActivity());
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void onClick(View v) {
        if( v == btn1 ){
            Intent i = new Intent(this, GameActivity.class);
            spil.nulstil();
            startActivity(i);
        }
        else if( v == btn2 ){
            showEditDialog();
        }
    }
}
