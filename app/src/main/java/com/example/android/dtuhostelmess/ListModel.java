package com.example.android.dtuhostelmess;

import android.support.v7.app.AppCompatActivity;

public class ListModel extends AppCompatActivity {
    private  String CompanyName="";
    private  String id="";
    private  String Cost="";

    private String Mess="";
    private String Food="";
    private String Total="";
    private String DateTime="";

   private String date="";
    private String holiday="";
    private int messOff;




    /*********** Set Methods ******************/
    public void setItemName(String CompanyName)
    {
        this.CompanyName = CompanyName;
        // Toast.makeText(ListModel.this, "name is "+ this.CompanyName, Toast.LENGTH_LONG).show();
    }

    public void setFoodId(String id)
    {
        this.id = id;
    }

    public void setCost(String Url)
    {
        this.Cost = Url;
    }


    public void setMess(String mess)
    {
        this.Mess = mess;
    }

    public void setFood(String food)
    {
        this.Food = food;
    }

    public void setTotal(String total)
    {
        this.Total = total;
    }

    public void setDateTime(String dateTime)
    {
        this.DateTime = dateTime;
    }


    public void setId
            (String id){
        this.id = id;
    }


    public void setDate(String date){
        this.date = date;
    }

    public void setHoliday(String holiday){
        this.holiday = holiday;
    }

    public void setMessOff(int messOff){
        this.messOff = messOff;
    }





    /*********** Get Methods ****************/
    public String getItemName()
    {
        return this.CompanyName;
    }


    public String getCost()
    {
        return this.Cost;
    }

    public String getFoodId()
    {
        return this.id;
    }


    public String getMess()
    {
        return this.Mess;
    }

    public String getFood()
    {
        return this.Food;
    }

    public String getTotal()
    {
       return this.Total;
    }

    public String getDateTime( )
    {
        return this.DateTime;
    }

    public String getId(){
        return this.id;
    }

    public String getDate(){
        return this.date;
    }

    public String getHoliday(){
        return this.holiday;
    }

    public int getMessOff(){
        return this.messOff;
    }




}
