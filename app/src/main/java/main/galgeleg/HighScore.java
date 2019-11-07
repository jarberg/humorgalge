package main.galgeleg;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HighScore extends AppCompatActivity implements View.OnClickListener {

    private static final String SHARED_PREF_NAME = "highscore";
    private static final String KEY_NAME = "key_username";
    private static final String KEY_SCORE = "key_score";
    int i =1;
    private List<String> nameList;
    public List<Integer> scoreList;
    private DeviceAdapter mAdapter;

    public HighScore(){
        nameList = new ArrayList<>();
        scoreList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_screen_highscore);

        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.clear().commit();

        loadSet();

        nameList.add("test");
        scoreList.add(3);

        RecyclerView recyclerView = this.findViewById(R.id.devicerecycler);
        mAdapter = new DeviceAdapter(nameList, scoreList, this);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {
        enter_player("testy"+i, (int) (Math.random()*60+1));
        i++;
        System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
        mAdapter.notifyDataSetChanged();
    }

    public void loadSet(){
        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
       try{
           String[] temp = sp.getString(KEY_NAME, null).replaceAll("\\W+"," ").trim().split(" ");
           if(temp[0] != ""){
               List<String> nameTemp = Arrays.asList(temp);
               for (String s: nameTemp ) {
                   nameList.add(s);
               }
           }
           temp = sp.getString(KEY_SCORE, null).replaceAll("\\W+"," ").trim().split(" ");
           if(temp[0] != "") {
               List<Integer> tempint = new ArrayList<>();
               if (temp.length != 0) {
                   for (String s : temp) {
                       tempint.add(Integer.valueOf(s));
                   }
                   scoreList = tempint;
               }
           }
       }
       catch (NullPointerException e){
       }
    }

    public void saveSet() {

        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.clear().commit();

        editor.putString(KEY_NAME, nameList.toString());
        editor.commit();
        editor.putString(KEY_SCORE, scoreList.toString());
        editor.commit();
        nameList.clear();
        scoreList.clear();

      }

      public void enter_player(String name, int score){
          for (int i = 0; i < nameList.size(); i++) {
              if(scoreList.get(i) < score ){
                  shift(i,nameList.size()-1);
                  nameList.set(i, name);
                  scoreList.set(i,score);
                  break;
              }
          }
      }

      public void shift(int index, int end){
        int i = end;
        while(i >= index){
            if( end != 19 && i == end){
                nameList.add(nameList.get(i));
                scoreList.add(scoreList.get(i));
            }
            else if( end == 19 && i == end){

            }
            else{
                nameList.set((i+1), nameList.get(i));
                scoreList.set((i+1), scoreList.get(i));
            }
            i--;
        }
      }
}

