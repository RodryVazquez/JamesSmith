/**
 * Adapter para el listado de casas disponibles.
 * @author Rodrigo Vazquez.
 * @version 1.0.
 * @see
 */

package com.example.rodrigovazquez.jamessmithproject.Adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rodrigovazquez.jamessmithproject.Helpers.FontHelper;
import com.example.rodrigovazquez.jamessmithproject.Models.HomeModel;
import com.example.rodrigovazquez.jamessmithproject.R;

import java.util.List;


public class ListHouseAdapter extends ArrayAdapter<HomeModel> {

    private Activity context;
    private List<HomeModel> homeModelList;
    Typeface typeface;

    /**
     *
     * @param context contexto actual de la aplicacion.
     * @param objects coleccion de objetos tipo Homemodel.
     */
    public ListHouseAdapter(Activity context, List<HomeModel> objects) {
        super(context, R.layout.custom_list_house_adapter, objects);
        this.context = context;
        this.homeModelList = objects;
    }

    /**
     * Guarda la referencia de cada elemento de la vista.
     */
    static class ViewHolder {

        TextView getGetHouseDescription;
        TextView getHouseAddress;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        //llamamos a nuestra clase estatica
        ViewHolder holder;
        //Obtenemos el elemento atraves de su posicion
        HomeModel model = homeModelList.get(position);

        //Verificamos el sea la primera vez que carga el listView
        if (rowView == null) {

            //Inflamos el layout que se ha creado
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.custom_list_house_adapter, null, true);
            //Creamos un objeto de nuestra clase de referencia
            holder = new ViewHolder();
            holder.getGetHouseDescription = (TextView) rowView.findViewById(R.id.lblTitle);
            holder.getHouseAddress = (TextView) rowView.findViewById(R.id.lblSubtitle);
            //Guardamos el objeto view holder
            rowView.setTag(holder);
        } else {
            //Obtenenmos el objeto previamente guardado
            holder = (ViewHolder) rowView.getTag();
        }

        typeface = FontHelper.getTypeFace(typeface,context);
        holder.getGetHouseDescription.setText(model.getDescription());
        holder.getGetHouseDescription.setTypeface(typeface);
        holder.getHouseAddress.setText(model.getAddress());
        holder.getHouseAddress.setTypeface(typeface);

        return rowView;
    }


}
