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

    // Get Methods
    public String getItemName() {
        return this.CompanyName;
    }

    //Set Methods
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

}
