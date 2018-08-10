package influx.com.demo.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import influx.com.demo.R;
import influx.com.demo.model.Fnblist;

public class TabFragment extends Fragment {

    RecyclerView recyclerView;
    private TabAdapter tabAdapter;
    TextView item_name,item_amount, item_summary_name, item_summary_amount;
    ImageView item_icon_up, item_icon_down;
    LinearLayout pay_layout, hide_layout, layout_up;

    List<Fnblist> fnblists;
    ArrayList<String> subitem_array;
    String currency;
    Animation slideUpAnimation, slideDownAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        fnblists = (List<Fnblist>) bundle.getSerializable("fnblist");

        subitem_array = new ArrayList<String>();

        currency = bundle.getString("currency");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, null);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        pay_layout = (LinearLayout)view.findViewById(R.id.pay_layout);
        layout_up = (LinearLayout)view.findViewById(R.id.layout_up);
        hide_layout = (LinearLayout)view.findViewById(R.id.hide_layout);

        item_name = (TextView)view.findViewById(R.id.item_name);
        item_amount = (TextView)view.findViewById(R.id.item_amount);
        item_summary_name = (TextView)view.findViewById(R.id.item_summary_name);
        item_summary_amount = (TextView)view.findViewById(R.id.item_summary_amount);
        item_icon_up = (ImageView)view.findViewById(R.id.item_icon_up);
        item_icon_down = (ImageView)view.findViewById(R.id.item_icon_down);

        item_name.setText(String.valueOf(currency));

        //item_summary_name.setText();

        tabAdapter = new TabAdapter(fnblists, currency, getContext(),view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.getRecycledViewPool().clear();
        recyclerView.setAdapter(tabAdapter);



        slideUpAnimation = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_up_animation);

        slideDownAnimation = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_down_animation);

        layout_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(item_icon_up.getVisibility()==View.VISIBLE){
                    item_icon_up.setVisibility(View.GONE);
                    item_icon_down.setVisibility(View.VISIBLE);

                    hide_layout.setVisibility(View.VISIBLE);
                    hide_layout.startAnimation(slideUpAnimation);

                }else {
                    item_icon_up.setVisibility(View.VISIBLE);
                    item_icon_down.setVisibility(View.GONE);

                    hide_layout.setVisibility(View.GONE);
                    hide_layout.startAnimation(slideDownAnimation);
                }
            }
        });

        //view.setBackgroundResource(color);
        return view;
    }
}
