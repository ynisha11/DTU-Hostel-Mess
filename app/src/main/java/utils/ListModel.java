package utils;

import android.support.v7.app.AppCompatActivity;

public class ListModel extends AppCompatActivity {
    private String CompanyName = "";
    private String id = "";
    private String Cost = "";

    private String Mess = "";
    private String Food = "";
    private String Total = "";
    private String DateTime = "";

    private String date = "";
    private String holiday = "";
    private int messOff;

    private String itemName = "";
    private String mealName = "";

    private String type = "";

    private String counterName = "";

    private String messOffDeduct ="";

    public String getMessOffDeduct(){
        return this.messOffDeduct;
    }

    public void setMessOffDeduct(String str){
        this.messOffDeduct = str;
    }

    public String getCounterName(){
        return this.counterName;
    }

    public void setCounterName(String str){
        this.counterName = str;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String str){
        this.type = str;
    }

    public String getItemName() {
        return this.CompanyName;
    }

    public void setItemName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getCost() {
        return this.Cost;
    }

    public void setCost(String Url) {
        this.Cost = Url;
    }

    public String getFoodId() {
        return this.id;
    }

    public void setFoodId(String id) {
        this.id = id;
    }

    public String getMess() {
        return this.Mess;
    }

    public void setMess(String mess) {
        this.Mess = mess;
    }

    public String getFood() {
        return this.Food;
    }

    public void setFood(String food) {
        this.Food = food;
    }

    public String getTotal() {
        return this.Total;
    }

    public void setTotal(String total) {
        this.Total = total;
    }

    public String getDateTime() {
        return this.DateTime;
    }

    public void setDateTime(String dateTime) {
        this.DateTime = dateTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId
            (String id) {
        this.id = id;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHoliday() {
        return this.holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public int getMessOff() {
        return this.messOff;
    }

    public void setMessOff(int messOff) {
        this.messOff = messOff;
    }

    public String getFoodName(){
        return this.itemName;
    }

    public void setFoodName(String Item){
        this.itemName = Item;
    }

    public String getMealName(){
        return this.mealName;
    }

    public void setMealName(String Item){
        this.mealName = Item;
    }


}
