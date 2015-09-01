package com.example.rodrigovazquez.jamessmithproject.Adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rodrigovazquez.jamessmithproject.Helpers.FontHelper;
import com.example.rodrigovazquez.jamessmithproject.R;

import java.lang.reflect.Type;

/**
 * Created by rodrigo.vazquez on 31/08/2015.
 */
public class CustomizedSpinnerAdapter extends ArrayAdapter<String> {
    private Activity context;
    String[] data = null;
    Typeface typeface;

    public CustomizedSpinnerAdapter(Activity context, int resource, String[] data2)
    {
        super(context, resource, data2);
        this.context = context;
        this.data = data2;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if(row == null)
        {
            //inflate your customlayout for the textview
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_layout_adapter, parent, false);
        }
        //put the data in it
        String item = data[position];
        if(item != null)
        {
            TextView title = (TextView) row.findViewById(R.id.txtTitleSpinner);
            TextView subtitle = (TextView) row.findViewById(R.id.txtTitleSpinner);
            typeface = FontHelper.getTypeFace(typeface, context);
            title.setTypeface(typeface);
            title.setText(item);

            subtitle.setTypeface(typeface);
        }

        return row;
    }

}
