
package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {

    @SerializedName("counter_name")
    @Expose
    private String counterName;
    @SerializedName("food_item")
    @Expose
    private String foodItem;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    /**
     * 
     * @return
     *     The counterName
     */
    public String getCounterName() {
        return counterName;
    }

    /**
     * 
     * @param counterName
     *     The counter_name
     */
    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    /**
     * 
     * @return
     *     The foodItem
     */
    public String getFoodItem() {
        return foodItem;
    }

    /**
     * 
     * @param foodItem
     *     The food_item
     */
    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    /**
     * 
     * @return
     *     The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     *     The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return
     *     The timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * 
     * @param timestamp
     *     The timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
