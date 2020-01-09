package main.galgeleg;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.media.MediaPlayer;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import main.galgeleg.animation.AnimBtnUtil;

public class GameActivity extends Activity implements OnItemClickListener, View.OnClickListener {
    //body part images
    private ImageView[] bodyParts;
    //number of body parts
    private int numParts=6;
    private String[] letters;
    GaleLogik spil = GaleLogik.get();
    String word[];
    char temp[];
    GridView wordView;
    ArrayAdapter<String> adapter2;
    Button btnGues;
    EditText guessview;
    int gætforsoeg=0;
    MediaPlayer mp;

    public GameActivity(){
        letters =new String[29];
        for (int a = 0; a < letters.length; a++) {
            letters[a] = String.valueOf((char)(a+'A'));
        }
        letters[26] = "Æ";
        letters[27] = "Ø";
        letters[28] = "Å";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mp= MediaPlayer.create(this, R.raw.click);

        setContentView(R.layout.activity_game);
        createbodylinks();
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.letter, R.id.test, letters);
        GridView gridView = findViewById(R.id.letters);
        gridView.setAdapter(adapter);
        spil.logStatus();
        wordString();
        adapter2 = new ArrayAdapter<>(this, R.layout.word, R.id.textLetter, word);
        wordView = findViewById(R.id.textView);
        wordView.setNumColumns(temp.length);
        wordView.setAdapter(adapter2);
        guessview = findViewById(R.id.guesstext);
        guessview.setOnClickListener(this);
        btnGues = findViewById(R.id.guessbtn);
        btnGues.setOnClickListener(this);
        showKeyboard(guessview, this);
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        System.out.println("hello"+ id);

    }

    @Override
    public void onClick(View v) {

        if(v.equals(btnGues)){
            guessword(guessview.getText().toString());
            AnimBtnUtil.bounce(v, this);
            mp.start();
        }
        else if(v == guessview){
        }
        else{

        }

    }
    public void guessword(String guess){
        if(spil.getOrdet().equals(guess)){
            float main = spil.getBrugteBogstaver().size();
            float sec = spil.getAntalForkerteBogstaver();
            float temp = ((main-sec)/main)*100;
            int score = (int) temp;

            Intent i = new Intent(this, Screen_end.class);
            i.putExtra("gamestate", true);
            i.putExtra("addname", true);
            i.putExtra("score", score);
            i.putExtra("ord", spil.getOrdet());
            i.putExtra("attempts", spil.getBrugteBogstaver().size()+gætforsoeg);
            startActivity(i);
            finish();
        }
        else{
            guessview.setText("");
            gætforsoeg++;
            Toast.makeText(this, "FORKERT!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void wordString(){
        temp = spil.getSynligtOrd().toCharArray();
        word =new String[temp.length];
        for (int i = 0; i < temp.length ; i++) {
            word[i] = String.valueOf(temp[i]);
        }
    }

    public void letterPressed(View v){
        String ltr=((TextView)v).getText().toString();

            v.setEnabled(false);
            v.setBackgroundResource(R.drawable.letter_down);
            spil.gætBogstav(String.valueOf(Character.toLowerCase(ltr.charAt(0))));
            spil.logStatus();
            AnimBtnUtil.bounce(v, this);
            if(spil.erSpilletSlut()){
                float main = spil.getBrugteBogstaver().size();
                float sec = spil.getAntalForkerteBogstaver();
                float temp = ((main-sec)/main)*100;
                int score = (int) temp;

                Intent i = new Intent(this, Screen_end.class);
                i.putExtra("gamestate", spil.erSpilletVundet());
                i.putExtra("score", score);
                i.putExtra("ord", spil.getOrdet());
                i.putExtra("attempts", spil.getBrugteBogstaver().size()+gætforsoeg);
                i.putExtra("addname", true);
                startActivity(i);
                finish();
            }
            for (int i = 0; i < spil.getAntalForkerteBogstaver()%7 ; i++) {
                bodyParts[i].setVisibility(View.VISIBLE);
            }
            adapter2.notifyDataSetChanged();
            wordView.setAdapter(null);
            wordString();
            adapter2 = new ArrayAdapter<>(this, R.layout.word, R.id.textLetter, word);
            wordView.setAdapter(adapter2);
            mp.start();
        }


    public void createbodylinks(){
        bodyParts = new ImageView[numParts];
        bodyParts[0] = findViewById(R.id.head);
        bodyParts[1] = findViewById(R.id.torso);
        bodyParts[2] = findViewById(R.id.leftArm);
        bodyParts[3] = findViewById(R.id.rightArm);
        bodyParts[4] = findViewById(R.id.leftLeg);
        bodyParts[5] = findViewById(R.id.rightLeg);
        for(int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);
            System.out.println(bodyParts[p].getId()+" is now "+bodyParts[p].getVisibility());
        }
    }
    public void showKeyboard(EditText mEtSearch, Context context) {
        mEtSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        hideSoftKeyboard(mEtSearch, context);
    }

    public void hideSoftKeyboard(EditText mEtSearch, Context context) {
        mEtSearch.clearFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0);
    }
}
