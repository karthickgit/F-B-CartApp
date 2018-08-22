package influx.com.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;
import java.util.List;

import influx.com.demo.R;
import influx.com.demo.model.Fnblist;

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.MyViewHolder> {
    private List<Fnblist> foodLists;
    private LinearLayout my_linear_layout;
    private Context context;
    String currency;

    RecyclerView second_recyclerview;

    private SecondAdapter secondAdapter;

    ArrayList<String> subitem_array = new ArrayList<String>();
    ArrayList<String> price_array = new ArrayList<String>();
    TextView item_amount, item_summary_name, item_summary_amount;
    View view;

    private double _cartprice = 0;
    int[] _counter = new int[10];

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, item_sub, item_count;
        public ImageView list_image, list_icon, item_minus, item_plus;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.item_name);
            item_sub = (TextView) view.findViewById(R.id.item_sub);
            my_linear_layout = (LinearLayout) view.findViewById(R.id.insert_point);
            list_image = (ImageView) view.findViewById(R.id.list_image);
            list_icon = (ImageView) view.findViewById(R.id.list_icon);
            item_count = (TextView) view.findViewById(R.id.item_count);
            item_minus = (ImageView) view.findViewById(R.id.item_minus);
            item_plus = (ImageView) view.findViewById(R.id.item_plus);
            //item_count.setText(String.valueOf(_counter));
        }
    }

    public TabAdapter(List<Fnblist> foodLists, String currency, Context context, View view) {
        this.foodLists = foodLists;
        this.context = context;
        this.currency = currency;
        this.view = view;

        item_amount = (TextView) view.findViewById(R.id.item_amount);
        second_recyclerview = (RecyclerView) view.findViewById(R.id.recycler_view_sub);

        secondAdapter = new SecondAdapter(subitem_array, price_array, context);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(context.getApplicationContext());
        second_recyclerview.setLayoutManager(mLayoutManager2);
        second_recyclerview.setItemAnimator(new DefaultItemAnimator());
        second_recyclerview.getRecycledViewPool().clear();
        second_recyclerview.setAdapter(secondAdapter);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tab_item_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Fnblist fnblist = foodLists.get(position);

        holder.title.setText(String.valueOf(fnblist.getName()));

        if (fnblist.getSubitems() != null) {
            holder.item_sub.setText(String.valueOf(currency));
        }

        UrlImageViewHelper.setUrlDrawable(holder.list_image, fnblist.getImgUrl(), R.drawable.photo_not_available, 60000);

        UrlImageViewHelper.setUrlDrawable(holder.list_icon, fnblist.getVegClass(), null, 60000);

        if (fnblist.getSubitems() != null) {

            final ArrayList<LinearLayout> viewList = new ArrayList<>();

            for (int i = 0; i < fnblist.getSubitems().size(); i++) {

                View view = LayoutInflater.from(context).inflate(R.layout.layout, null);

                final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout);

                final TextView textView = (TextView) view.findViewById(R.id.layout_text);

                textView.setText(String.valueOf(fnblist.getSubitems().get(i).getName()));


                linearLayout.setId(Integer.parseInt(fnblist.getSubitems().get(i).getVistaSubFoodItemId()));

                linearLayout.setTag(i);

                if (i == 0) {

                    linearLayout.setBackground(context.getResources().getDrawable(R.drawable.yellow_bg));

                    textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));

                    holder.item_sub.setText(String.valueOf(currency) + " " + String.valueOf(fnblist.getSubitems().get(i).getSubitemPrice()));
                }

                final int finalI = i;

                viewList.add(linearLayout);

                linearLayout.setOnClickListener(new View.OnClickListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {

                        for (int i = 0; i < viewList.size(); i++) {

                            viewList.get(i).setBackground(context.getResources().getDrawable(R.drawable.border));

                            TextView view1 = viewList.get(i).getChildAt(0).findViewById(R.id.layout_text);

                            view1.setTextColor(context.getResources().getColor(R.color.white));
                        }

                        linearLayout.setBackground(context.getResources().getDrawable(R.drawable.yellow_bg));
                        textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));

                        holder.item_sub.setText(String.valueOf(currency) + " " + String.valueOf(fnblist.getSubitems().get(finalI).getSubitemPrice()));
                    }
                });

                my_linear_layout.addView(view);

                my_linear_layout.setVisibility(View.VISIBLE);
            }
        } else {
            my_linear_layout.setVisibility(View.GONE);
        }

        holder.item_minus.setId(position);
        holder.item_plus.setId(position);

        _counter[position] = 0;

        holder.item_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _counter[v.getId()]++;

                holder.item_count.setText(String.valueOf(_counter[v.getId()]));

                String total_price;

                if (fnblist.getSubitems().size() > 0) {
                    total_price = fnblist.getSubitems().get(position).getSubitemPrice();
                } else {
                    total_price = fnblist.getItemPrice();
                }

                _cartprice = Float.valueOf(total_price) * _counter[v.getId()];

                System.out.println("Cart price : " + String.valueOf(_cartprice));

                item_amount.setText(String.valueOf(_cartprice));

                subitem_array.add(position, fnblist.getName());
                price_array.add(position, total_price);


                secondAdapter.notifyItemInserted(position);

               /* item_summary_name.setText(String.valueOf(fnblist.getName()) + " (" + holder.item_count.getText().toString() + ")");

                item_summary_amount.setText(String.valueOf(_cartprice));*/

            }
        });

        holder.item_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _counter[v.getId()]--;

                if (_counter[v.getId()] >= 0) {
                    holder.item_count.setText(String.valueOf(_counter[v.getId()]));
                    String total_price;

                    if (fnblist.getSubitems().size() > 0) {
                        total_price = fnblist.getSubitems().get(position).getSubitemPrice();
                    } else {
                        total_price = fnblist.getItemPrice();
                    }

                    _cartprice = Float.valueOf(total_price) * _counter[v.getId()];


                    System.out.println("_cartprice" + _cartprice);

                    item_amount.setText(String.valueOf(_cartprice));

                    subitem_array.remove(position);
                    price_array.remove(position);

                    secondAdapter.notifyItemRemoved(position);

                    secondAdapter.notifyItemRangeChanged(position, subitem_array.size());
                    secondAdapter.notifyItemRangeChanged(position, price_array.size());

                  /* item_summary_name.setText(String.valueOf(fnblist.getName()) + " (" + holder.item_count.getText().toString() + ")");

                    item_summary_amount.setText(String.valueOf(_cartprice));*/
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodLists.size() == 0 ? 0 : foodLists.size();
        //return foodLists.size();
    }

    private double getTotalAmount() {

        if (item_amount != null && item_amount.getText().toString().trim().length() > 0) {

            return Double.parseDouble(item_amount.getText().toString());
        }
        else {
            return 0;
        }
    }
}