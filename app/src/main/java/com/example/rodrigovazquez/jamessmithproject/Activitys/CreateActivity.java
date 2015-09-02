/**
 * @author Rodrigo vazquez.
 * @version 1.0.
 * @see com.example.rodrigovazquez.jamessmithproject.MainActivity
 * @see com.example.rodrigovazquez.jamessmithproject.Models.HomeModel
 */


package com.example.rodrigovazquez.jamessmithproject.Activitys;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    Spinner spinner;

    /**
     * Iniciamos el servicio.
     */
    public CreateActivity() {
        homeService = new HomeService(CreateActivity.this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);

        houseDescription = (EditText) findViewById(R.id.edtDescription);
        houseAddress = (EditText) findViewById(R.id.edtAddress);
        housePrice = (EditText) findViewById(R.id.edtPrice);
        houseStatus = (Spinner) findViewById(R.id.spinner);

        titleDescription = (TextView) findViewById(R.id.txtCreateDescription);
        titleAddress = (TextView) findViewById(R.id.txtCreateAddress);
        titlePrice = (TextView) findViewById(R.id.txtCreatePrice);
        titleStatus = (TextView) findViewById(R.id.txtCreateStatus);

        typeface = FontHelper.getTypeFace(typeface, CreateActivity.this);

        houseDescription.setTypeface(typeface);
        houseAddress.setTypeface(typeface);
        housePrice.setTypeface(typeface);

        titleDescription.setTypeface(typeface);
        titleAddress.setTypeface(typeface);
        titlePrice.setTypeface(typeface);
        titleStatus.setTypeface(typeface);

        //Custom spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        final String[] data = getResources().getStringArray(R.array.spinnerContent);
        //Custom adapter
        ArrayAdapter<String> adapter = new CustomizedSpinnerAdapter(CreateActivity.this, android.R.layout.simple_spinner_item, data);
        //Aplicamos el adapter al spinner
        spinner.setAdapter(adapter);


    }

    /**
     * Inflamos el menu.
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
     * Guardamos el formulario.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_save:

                final String description = houseDescription.getText().toString();
                final String address = houseAddress.getText().toString();
                final String price = housePrice.getText().toString();

                HomeModel model = new HomeModel();

                if (description.matches("") || address.matches("") || price.matches("")) {

                    new AlertDialog.Builder(this)
                            .setMessage("Please complete all fields.")
                            .setTitle("House Portal")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                } else {

                    boolean status = false;
                    int value = spinner.getSelectedItemPosition();
                    if (value == 0) {

                        status = true;
                    }
                    model.setDescription(description);
                    model.setAddress(address);
                    model.setPrice(Double.parseDouble(price));
                    model.setActive(status);
                    homeService.createNewHouse(model);

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Necesario para el spinner.
     *
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
     *
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }

}
