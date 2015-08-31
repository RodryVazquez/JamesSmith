/**
 * Actividad principal.
 * @author Rodrigo Vazquez.
 * @version 1.0.
 * @see com.example.rodrigovazquez.jamessmithproject.WebService.HomeService
 * @see com.example.rodrigovazquez.jamessmithproject.Models.HomeModel
 * @see com.example.rodrigovazquez.jamessmithproject.Activitys.DetailHouseActivity
 */

package com.example.rodrigovazquez.jamessmithproject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rodrigovazquez.jamessmithproject.Adapter.ListHouseAdapter;
import com.example.rodrigovazquez.jamessmithproject.Activitys.DetailHouseActivity;
import com.example.rodrigovazquez.jamessmithproject.Models.HomeModel;
import com.example.rodrigovazquez.jamessmithproject.WebService.HomeService;

import java.util.List;

//Actividad principal
public class MainActivity extends AppCompatActivity {

    //Servicio de domicilios
    private HomeService homeService;
    ListView houseList;
    ListHouseAdapter houseAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    List<HomeModel> houses;

    /**
     * Inicializa el servicio
     */
    public MainActivity() {

        homeService = new HomeService(MainActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        houseList = (ListView) findViewById(R.id.homeListView);


        houses = HomeModel.listAll(HomeModel.class);
        if (houses.size() > 0) {

            houseAdapter = new ListHouseAdapter(MainActivity.this, houses);
            houseList.setAdapter(houseAdapter);
        } else {

            homeService.GetAllHouses(houseList);
        }

        /**
         * Habilitamos el evento en el listView
         */
        houseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeModel model = houses.get(position);
                Intent intent = new Intent(MainActivity.this,DetailHouseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("keyHome",model);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        /**
         * Refrescamos.
         */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                HomeModel.deleteAll(HomeModel.class);
                homeService.GetAllHouses(houseList);
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
       return true;
    }

    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actionNewItem) {

            Intent intent = new Intent(MainActivity.this,DetailHouseActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
