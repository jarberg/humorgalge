package main.galgeleg;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;


public class Dialog extends DialogFragment implements View.OnClickListener {

    Button btn1;
    Button btn2;
    Object test;

    public Dialog() {
    }

    public static Dialog newInstance(String title, String text, Object test) {
        Dialog frag = new Dialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("text", text);
        frag.setArguments(args);
        frag.test = test;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setCancelable(false);
        this.getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return inflater.inflate(R.layout.layout_dialog_loser, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        view.findViewById(R.id.cancel).setOnClickListener(this);
        btn1 = view.findViewById(R.id.accept);
        btn1.setOnClickListener(this);
        //btn1.setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
        btn2 = view.findViewById(R.id.cancel);
        btn2.setOnClickListener(this);
        //btn2.setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
        TextView titel = view.findViewById(R.id.Titel);
        titel.setText("Hello");
        TextView tekst = view.findViewById(R.id.Text);
        tekst.setText(getArguments().getString("text", "forklaring" ));
        view.setFadingEdgeLength(5);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), test.getClass());
        if(v==btn1){
            startActivity(intent);
            Toast.makeText(this.getActivity(), "accept", Toast.LENGTH_SHORT).show();
        }
        else if(v==btn2){

            Toast.makeText(this.getActivity(), "cancel", Toast.LENGTH_SHORT).show();
            this.dismiss();
        }
    }
}