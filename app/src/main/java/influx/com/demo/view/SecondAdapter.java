package influx.com.demo.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import influx.com.demo.R;

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.MyViewHolder> {
    private ArrayList<String> foodLists;
    private ArrayList<String> subitem_array;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_summary_name, item_summary_amount;

        public MyViewHolder(View view) {
            super(view);
            item_summary_name = (TextView) view.findViewById(R.id.item_summary_name);
            item_summary_amount = (TextView) view.findViewById(R.id.item_summary_amount);
        }
    }

    public SecondAdapter(ArrayList<String> foodLists, ArrayList<String> subitem_array, Context context) {
        this.foodLists = foodLists;
        this.subitem_array = subitem_array;
        this.context = context;
    }

    @Override
    public SecondAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.second_list, parent, false);

        return new SecondAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SecondAdapter.MyViewHolder holder, final int position) {

        holder.item_summary_name.setText(String.valueOf(foodLists.get(position)));
        holder.item_summary_amount.setText(String.valueOf(subitem_array.get(position)));

    }

    @Override
    public int getItemCount() {
        return foodLists.size() == 0 ? 0 : foodLists.size();
        //return foodLists.size();
    }



}