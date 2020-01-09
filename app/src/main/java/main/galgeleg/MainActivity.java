package main.galgeleg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1,btn2,btn3;
    GaleLogik spil = GaleLogik.get();
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.playBtn);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.helpbtn);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.btn_highscore);
        btn3.setOnClickListener(this);
        mp= MediaPlayer.create(getApplicationContext(), R.raw.click);
        mp.setVolume(2, 2);

    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        Dialog editNameDialogFragment = Dialog.newInstance("Some Title", "Dette er hangman spillet\n Tryk på start for at spille\n du skal gætte ordet der er på skærmen\n tryk på det bogstav du mener er i ordet før du løber tør for legmsdele", new GameActivity());
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void onClick(View v) {
        mp.start();
        if( v == btn1 ){
            Intent i = new Intent(this, GameActivity.class);
            try {
                if(spil.t.isAlive()){
                    Toast.makeText(this, "Henter ord fra dr", Toast.LENGTH_SHORT).show();
                }
                spil.t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spil.nulstil();
            startActivity(i);
        }
        else if( v == btn2 ){
            showEditDialog();
        }
        else if(v == btn3){
            Intent i = new Intent(this, HighScore.class);
            i.putExtra("addname", false);
            spil.nulstil();
            startActivity(i);
        }
    }
}
