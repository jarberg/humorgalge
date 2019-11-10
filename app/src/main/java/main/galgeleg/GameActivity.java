package main.galgeleg;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements OnItemClickListener {
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
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        System.out.println("hello"+ id);

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
        System.out.println(ltr);
        v.setEnabled(false);
        v.setBackgroundResource(R.drawable.letter_down);
        spil.gætBogstav(String.valueOf(Character.toLowerCase(ltr.charAt(0))));
        spil.logStatus();

        if(spil.erSpilletSlut()){

            int score = (spil.getBrugteBogstaver().size()-spil.getAntalForkerteBogstaver())/(spil.getBrugteBogstaver().size());

            Intent i = new Intent(this, Screen_end.class);
            i.putExtra("gamestate", spil.erSpilletVundet());
            i.putExtra("score", score);
            startActivity(i);

        }


        for (int i = 0; i < spil.getAntalForkerteBogstaver() ; i++) {
            bodyParts[i].setVisibility(View.VISIBLE);
        }

        wordView.setAdapter(null);
        wordString();
        adapter2 = new ArrayAdapter<>(this, R.layout.word, R.id.textLetter, word);
        wordView.setAdapter(adapter2);
 
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
}
