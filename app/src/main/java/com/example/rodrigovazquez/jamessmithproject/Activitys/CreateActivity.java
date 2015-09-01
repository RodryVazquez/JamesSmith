/**
 * @author Rodrigo vazquez.
 * @version 1.0.
 * @see com.example.rodrigovazquez.jamessmithproject.MainActivity
 * @see com.example.rodrigovazquez.jamessmithproject.Models.HomeModel
 */


package com.example.rodrigovazquez.jamessmithproject.Activitys;

import android.content.ClipData;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rodrigovazquez.jamessmithproject.Adapter.CustomizedSpinnerAdapter;
import com.example.rodrigovazquez.jamessmithproject.Helpers.FontHelper;
import com.example.rodrigovazquez.jamessmithproject.Models.HomeModel;
import com.example.rodrigovazquez.jamessmithproject.R;
import com.example.rodrigovazquez.jamessmithproject.WebService.HomeService;

public class CreateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Typeface typeface;
    EditText houseDescription;
    EditText houseAddress;
    EditText housePrice;
    Spinner houseStatus;
    private HomeService homeService;
    private HomeModel homeModel;
    TextView titleDescription;
    TextView titleAddress;
    TextView titlePrice;
    TextView titleStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);

        houseDescription = (EditText)findViewById(R.id.edtDescription);
        houseAddress = (EditText)findViewById(R.id.edtAddress);
        housePrice = (EditText)findViewById(R.id.edtPrice);
        houseStatus = (Spinner)findViewById(R.id.spinner);

        titleDescription = (TextView)findViewById(R.id.txtCreateDescription);
        titleAddress = (TextView)findViewById(R.id.txtCreateAddress);
        titlePrice = (TextView)findViewById(R.id.txtCreatePrice);
        titleStatus = (TextView)findViewById(R.id.txtCreateStatus);

        typeface = FontHelper.getTypeFace(typeface, CreateActivity.this);

        houseDescription.setTypeface(typeface);
        houseAddress.setTypeface(typeface);
        housePrice.setTypeface(typeface);

        titleDescription.setTypeface(typeface);
        titleAddress.setTypeface(typeface);
        titlePrice.setTypeface(typeface);
        titleStatus.setTypeface(typeface);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final String[] data = getResources().getStringArray(R.array.spinnerContent);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new CustomizedSpinnerAdapter(CreateActivity.this, android.R.layout.simple_spinner_item,data);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_save:
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Necesario para el spinner.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         parent.getItemAtPosition(position);
    }

    /**
     * Necesario para el spinner.
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
