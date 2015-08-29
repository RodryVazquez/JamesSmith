/**
 * Servicio de Sql para las casas
 * @author Rodrigo Vazquez
 * @version 1.0
 * @see com.example.rodrigovazquez.jamessmithproject.WebService.HomeService
 */

package com.example.rodrigovazquez.jamessmithproject.SqlService;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rodrigovazquez.jamessmithproject.Models.HomeModel;
import java.util.ArrayList;
import java.util.List;


public class HouseService {

    //Helper para la tabla de casas
    HouseSqlHelper houseSqlHelper;

    /**
     * Inicializa el servicio helper para las casas.
     * @param context contexto actual de la aplicacion.
     * @param name nombre en cadena para la base de datos.
     * @param factory valor nulo por defecto.
     * @param version version de la base de datos.
     */
    public HouseService(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        houseSqlHelper = new HouseSqlHelper(context, name, factory, version);
    }

    /**
     * Consulta un listado de casas disponibles
     * @return coleccion de objetos tipo HomeModel
     */
    public List<HomeModel> getAllHouse() {

        List<HomeModel> getHouses = new ArrayList<>();
        SQLiteDatabase db = houseSqlHelper.getReadableDatabase();
        if (db != null) {
            String[] args = new String[]{"1"};
            Cursor c = db.rawQuery("SELECT * FROM Houses WHERE Active=?", args);
            if (c.moveToFirst()) {

                do {

                    HomeModel model = new HomeModel();
                    int id = Integer.parseInt(c.getString(0));
                    model.setHomeId(id);
                    model.setDescription(c.getString(1));
                    model.setAddress(c.getString(2));
                    double price = Double.parseDouble(c.getString(3));
                    model.setPrice(price);
                    boolean state = Boolean.parseBoolean(c.getString(4));
                    model.setActive(state);

                    getHouses.add(model);

                } while (c.moveToNext());
            }

            db.close();
        }

        return getHouses;
    }

    /**
     * Consulta una casa en especifico
     * @param homeId id de la casa a consultar
     * @return un objeto tipo HomeModel
     */
    public HomeModel getHouseById(int homeId) {

        HomeModel model = new HomeModel();
        SQLiteDatabase db = houseSqlHelper.getReadableDatabase();
        if (db != null) {
            String[] args = new String[]{String.valueOf(homeId)};
            Cursor c = db.rawQuery("Select * FROM HOUSES WHERE HomeId=?", args);
            if (c.moveToFirst()) {

                int id = Integer.parseInt(c.getString(0));
                model.setHomeId(id);
                model.setDescription(c.getString(1));
                model.setAddress(c.getString(2));
                double price = Double.parseDouble(c.getString(3));
                model.setPrice(price);
                boolean state = Boolean.parseBoolean(c.getString(4));
                model.setActive(state);
            }
            else{
                return null;
            }
        }
        db.close();
        return model;
    }

    /**
     * Crea un nuevo registro tipo Casa
     * @param model
     * @return
     */
    public int createHouse(HomeModel model) {

        SQLiteDatabase db = houseSqlHelper.getWritableDatabase();
        if (db != null) {

            ContentValues contentValues = new ContentValues();

            contentValues.put("HomeId", model.getHomeId());
            contentValues.put("Description", model.getDescription());
            contentValues.put("Address", model.getAddress());
            contentValues.put("Price", model.getPrice());
            contentValues.put("Active",1);

            db.insert("Houses", null, contentValues);

        }
        db.close();

        return model.getHomeId();
    }

}
