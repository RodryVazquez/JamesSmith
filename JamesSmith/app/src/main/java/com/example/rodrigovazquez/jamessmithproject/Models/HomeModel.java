/**
 * Representa a una casa.
 * @author Rodrigo Vazquez.
 * @version 1.0.
 * @see com.example.rodrigovazquez.jamessmithproject.WebService.HomeService
 */

package com.example.rodrigovazquez.jamessmithproject.Models;

import com.orm.SugarRecord;

import java.io.Serializable;

//Representa a un domicilio
public class HomeModel  extends SugarRecord<HomeModel> implements Serializable {

    //Id del domicilio
    int HomeId;
    //Descripcion del domicilio
    String Description;
    //Direccion del domicilio
    String Address;
    //Costo del domicilio
    double Price;
    //Estado del domicilio
    Boolean Active;

    /**
     * Constructor por defecto.
     */
    public HomeModel(){

    }

    /**
     * Inicializa los valores.
     * @param HomeId
     * @param Description
     * @param Address
     * @param Price
     * @param Active
     */
    public HomeModel(int HomeId,String Description,String Address,double Price,Boolean Active){

        this.HomeId = HomeId;
        this.Description = Description;
        this.Address = Address;
        this.Price = Price;
        this.Active = Active;
    }

    public int getHomeId() {
        return HomeId;
    }

    public void setHomeId(int homeId) {
        HomeId = homeId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean active) {
        Active = active;
    }
}
