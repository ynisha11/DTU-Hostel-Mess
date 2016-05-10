package Models;

/**
 * Created by vivek on 09/05/16.
 */
public class FoodItemObject {
    public String mFoodId;
    public String mFoodName;
    public String mType;
    public String mCalories;
    public String mNutrition;
    public String mPicture;
    public double mCost;

    public FoodItemObject(String id, String name, String type, String calories, String nutrition, String picture, double cost) {
        mFoodId = id;
        mFoodName = name;
        mType= type;
        mCalories = calories;
        mNutrition= nutrition;
        mPicture= picture;
        mCost= cost;
    }

    public String getType() {
        return mType;
    }
}

