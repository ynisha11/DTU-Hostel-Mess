package Models;

import android.widget.Toast;

/**
 * Created by vivek on 09/05/16.
 */
public class FoodItem {
    public String mFoodId;
    public String mFoodName;
    public String mType;
    public String mCalories;
    public String mNutrition;
    public String mPicture;
    public double mCost;
    public double mQuantity;
    public double mAmount;


    public double getmCost() {
        return mCost;
    }

    public String getmFoodId() {
        return mFoodId;
    }

    public double getmQuantity() {
        return mQuantity;
    }


    public String getmFoodName() {
        return mFoodName;
    }


    public boolean ismIsSeparator() {
        return mIsSeparator;
    }

    public boolean mIsSeparator;

    public boolean ismIsSelected() {
        return mIsSelected;
    }

    public void setmIsSelected(boolean mIsSelected) {
        this.mIsSelected = mIsSelected;
    }

    public boolean mIsSelected;

    public FoodItem(FoodItemObject foodItemObject, boolean isSeparator) {
        mFoodId = foodItemObject.mFoodId;
        mFoodName = foodItemObject.mFoodName;
        mType= foodItemObject.mType;
        mCalories = foodItemObject.mCalories;
        mNutrition= foodItemObject.mNutrition;
        mPicture= foodItemObject.mPicture;
        mCost= foodItemObject.mCost;
        mIsSeparator= isSeparator;

    }

    public FoodItem(String id, String name,String type, boolean isSeparator) {
        mFoodId = id;
        mFoodName = name;
        mType= type;
        mIsSeparator= isSeparator;
    }

    public FoodItem(String type, boolean isSeparator) {
        mType= type;
        mIsSeparator= isSeparator;
    }

    public void setCost(double cost) {
        mCost = cost;
    }

    public void setName(String name) {
        mFoodName = name;
    }

    public void setQuantity(double quantity) {
        mQuantity = quantity;
    }

    public double getAmount() {
        mAmount= mQuantity * mCost;
        return mAmount;
    }

    public void setIsSection(boolean isSection) {
        mIsSeparator = isSection;
    }
}

