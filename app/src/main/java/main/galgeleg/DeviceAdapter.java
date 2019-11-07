package main.galgeleg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.MyViewHolder> {

    private final List<String> nameList;
    private final List<Integer> scoreList;
    private final OnClickListener onClick;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView number;
        private TextView name;
        private TextView score;

        public MyViewHolder(@NonNull View view) {
            super(view);
            number = view.findViewById(R.id.number);
            name = view.findViewById(R.id.rækkenavn);
            score = view.findViewById(R.id.turrække);
        }

    }

    public DeviceAdapter(@NonNull List<String> nameList, @NonNull List<Integer> scoreList, @Nullable OnClickListener onClick) {
        this.nameList = nameList;
        this.scoreList = scoreList;
        this.onClick = onClick;
    }

    @Override @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_highscore, parent, false);
        itemView.setOnClickListener(onClick);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(nameList != null && scoreList != null){
            String device = nameList.get(position);
            int score = scoreList.get(position);

            holder.number.setText(""+(position+1));
            holder.name.setText(device+"");
            holder.score.setText(score+"");

        }
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

}
