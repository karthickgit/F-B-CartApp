package influx.com.demo.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import influx.com.demo.model.BasePojoClass;
import influx.com.demo.model.FoodList;
import influx.com.demo.retrofitapi.AppClient;
import influx.com.demo.retrofitapi.AppInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {

    List tabFragmentList = new ArrayList();
    ActionBar actionBar;
    BasePojoClass basePojoClass;
    List<FoodList> foodLists;
    RelativeLayout.LayoutParams layoutparams;
    //List<Fnblist> fnblists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // TODO: Remove the redundant calls to getSupportActionBar()
        //       and use variable actionBar instead
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        GetData();
    }

    private void GetData() {

        AppInterface appInterface = AppClient.getClient().create(AppInterface.class);
        Call<BasePojoClass> call = appInterface.RetriveData();

        call.enqueue(new Callback<BasePojoClass>() {
            @Override
            public void onResponse(Call<BasePojoClass> call, Response<BasePojoClass> response) {
                if (response.body() != null) {

                    try {
                        basePojoClass = response.body();
                        foodLists = new ArrayList<FoodList>();
                        //fnblists = new ArrayList<Fnblist>();
                        foodLists = basePojoClass.getFoodList();

                        System.out.println("Size : "+foodLists.size());

                        for (int j=0; j < foodLists.size(); j++) {
                            ActionBar.Tab tab = actionBar.newTab();
                            tab.setText(foodLists.get(j).getTabName());
                            tab.setTabListener(MainActivity.this);
                            actionBar.addTab(tab);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BasePojoClass> call, Throwable t) {

            }
        });
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        Fragment fragment = null;
        TabFragment tabFragment = null;


        if (tabFragmentList.size() > tab.getPosition()) {
            fragment = (Fragment) tabFragmentList.get(tab.getPosition());
        }

        if (fragment == null) {
            tabFragment = new TabFragment();
            Bundle bundle = new Bundle();

            bundle.putSerializable("fnblist", (Serializable) foodLists.get(tab.getPosition()).getFnblist());
            bundle.putString("currency", basePojoClass.getCurrency());
            tabFragment.setArguments(bundle);
            tabFragmentList.add(tabFragment);
        }
        else {
            tabFragment = (TabFragment) fragment;
        }
        ft.replace(android.R.id.content, tabFragment);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        if (tabFragmentList.size() > tab.getPosition()) {
            ft.remove((Fragment) tabFragmentList.get(tab.getPosition()));
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }
}
