/**
 * Servicio HTTP para las casas.
 * @author Rodrigo Vazquez.
 * @version 1.0.
 * @see com.example.rodrigovazquez.jamessmithproject.MainActivity
 */

package com.example.rodrigovazquez.jamessmithproject.WebService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rodrigovazquez.jamessmithproject.Adapter.ListHouseAdapter;
import com.example.rodrigovazquez.jamessmithproject.Enums.StatusEnum;
import com.example.rodrigovazquez.jamessmithproject.Models.HomeModel;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Servicio general para HomeController
public class HomeService {

    //Ruta relativa para consultar las casas disponibles
    private String relativeUrl = "api/home";
    private Context context;

    /**
     * Inicializa el contexto.
     * @param context
     */
    public HomeService(Context context) {

        this.context = context;
    }

    /**
     * Consulta todas las casas disponibles
     * @return colecion de casas disponibles
     */
    public void GetAllHouses(final ListView listView) {

        final List<HomeModel> homeModelList = new ArrayList<>();
        ApiService.get(relativeUrl, null, new JsonHttpResponseHandler() {

            ProgressDialog dialog;

            @Override
            public void onStart() {
                super.onStart();
                dialog = new ProgressDialog(context);
                dialog.setMessage("Getting house...");
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                if (statusCode == 200) {

                    int sizeData = response.length();
                    for (int i = 0; i < sizeData; i++) {

                        try {
                            JSONObject object = (JSONObject) response.get(i);
                            HomeModel model = new HomeModel(object.getInt("HomeId"), object.getString("HomeDescription"), object.getString("Address"), object.getDouble("Price"), object.getBoolean("Active"));
                            model.save();
                            homeModelList.add(model);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    ListHouseAdapter adapter = new ListHouseAdapter((Activity) context, homeModelList);
                    listView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (dialog != null && dialog.isShowing()) {

                    dialog.dismiss();
                    dialog = null;
                }
            }

        });
    }

    /**
     * Consulta un casa en especifico.
     * @param objects
     * @param homeId
     */
    public void getHomeById(final Object[] objects, final int homeId) {

        String finalUrl = relativeUrl + "/" + homeId;

        ApiService.get(finalUrl, null, new JsonHttpResponseHandler() {

            ProgressDialog dialog;

            @Override
            public void onStart() {
                super.onStart();
                dialog = new ProgressDialog(context);
                dialog.setMessage("Getting house...");
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                if (statusCode == 200) {

                    try {

                        HomeModel homeModel = new HomeModel(response.getInt("HomeId"),
                                              response.getString("HomeDescription"), response.getString("Address"),
                                              response.getDouble("Price"), response.getBoolean("Active"));
                        homeModel.save();

                        //Id de la casa
                        TextView houseId = (TextView) objects[0];
                        String id = String.valueOf(homeModel.getHomeId());
                        houseId.setText(id);
                        //Descripcion de la casa
                        TextView houseDescription = (TextView) objects[1];
                        houseDescription.setText(homeModel.getDescription());
                        //Direccion de la casa
                        TextView houseAddress = (TextView) objects[2];
                        houseAddress.setText(homeModel.getAddress());
                        //Precio de la casa
                        String price = String.valueOf(homeModel.getPrice());
                        TextView housePrice = (TextView) objects[3];
                        housePrice.setText(price);
                        //Estado de la casa

                        TextView houseState = (TextView) objects[4];
                        if (homeModel.getActive()) {

                            houseState.setText(StatusEnum.Disponible.toString());
                        } else {

                            houseState.setText(StatusEnum.Vendida.toString());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (dialog != null && dialog.isShowing()) {

                    dialog.dismiss();
                    dialog = null;
                }
            }
        });
    }
}
