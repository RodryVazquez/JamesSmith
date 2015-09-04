/**
 * @author Rodrigo vazquez.
 * @version 1.0.
 * @see com.example.rodrigovazquez.jamessmithproject.MainActivity
 * @see com.example.rodrigovazquez.jamessmithproject.Models.HomeModel
 */

package com.example.rodrigovazquez.jamessmithproject.Activitys;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.rodrigovazquez.jamessmithproject.Enums.StatusEnum;
import com.example.rodrigovazquez.jamessmithproject.Helpers.FontHelper;
import com.example.rodrigovazquez.jamessmithproject.Models.HomeModel;
import com.example.rodrigovazquez.jamessmithproject.R;
import com.example.rodrigovazquez.jamessmithproject.WebService.HomeService;

import org.w3c.dom.Text;


public class DetailHouseActivity extends AppCompatActivity {

    Typeface typeface;
    TextView houseId;
    TextView houseDescription;
    TextView houseAddress;
    TextView housePrice;
    TextView houseStatus;
    private HomeService homeService;

    private HomeModel homeModel;

    TextView title;
    TextView titleDescription;
    TextView titleAddress;
    TextView titlePrice;
    TextView titleStatus;
    TextView titleId;
    /**
     * Iniciamos el servicio.
     */
    public DetailHouseActivity(){

        homeService = new HomeService(DetailHouseActivity.this);
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_listview_house_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);

        //Campos editables
        houseId = (TextView)findViewById(R.id.txtHouseIdentity);
        houseDescription = (TextView)findViewById(R.id.txtHouseDescription);
        houseAddress = (TextView)findViewById(R.id.txtHouseAddress);
        housePrice = (TextView)findViewById(R.id.txtHousePrice);
        houseStatus = (TextView)findViewById(R.id.txtHouseStatus);

        title = (TextView)findViewById(R.id.TitleHistoric);
        titleDescription = (TextView)findViewById(R.id.txtNameDetailHistoric);
        titleAddress = (TextView)findViewById(R.id.txtPhoneDetailHistoric);
        titlePrice = (TextView)findViewById(R.id.txtDirectionDetailHistoric);
        titleStatus = (TextView)findViewById(R.id.txtZoneDetailHistoric);
        titleId = (TextView)findViewById(R.id.txtSubZoneDetailHistoric);


        typeface = FontHelper.getTypeFace(typeface, DetailHouseActivity.this);
        houseId.setTypeface(typeface);
        houseDescription.setTypeface(typeface);
        houseAddress.setTypeface(typeface);
        housePrice.setTypeface(typeface);
        houseStatus.setTypeface(typeface);

        title.setTypeface(typeface);
        titleDescription.setTypeface(typeface);
        titleAddress.setTypeface(typeface);
        titlePrice.setTypeface(typeface);
        titleStatus.setTypeface(typeface);
        titleId.setTypeface(typeface);

        if(homeModel == null){

            Bundle bundle = getIntent().getExtras();
            if(bundle != null){
                homeModel = (HomeModel) bundle.getSerializable("keyHome");
                houseId.setText(String.valueOf(homeModel.getHomeId()));
                houseDescription.setText(homeModel.getDescription());
                houseAddress.setText(homeModel.getAddress());
                housePrice.setText(String.valueOf(homeModel.getPrice()));
                if(homeModel.getActive()){

                    houseStatus.setText(StatusEnum.Disponible.toString());
                }else{
                    houseStatus.setText(StatusEnum.Vendida.toString());
                }
            }
        }
    }

    /**
     * Inflamos el menu.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Obtenemos la referencia dependiendo si queremos editar o borrar el elemento.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case R.id.editItem:
                return true;
            case R.id.deleteItem:
                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }
}
