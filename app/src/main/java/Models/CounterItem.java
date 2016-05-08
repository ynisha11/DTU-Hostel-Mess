package Models;

/**
 * Created by vivek on 09/05/16.
 */
public class CounterItem {
    public String mCounterId;
    public String mCounterName;
    public boolean mBoysCounter;
    public boolean mIsSeparator;

    public CounterItem(String counterId, String counterName, boolean boysCounter, boolean isSeparator) {
        mCounterId = counterId;
        mCounterName = counterName;
        mBoysCounter= boysCounter;
        mIsSeparator = isSeparator;
    }

    public void setCounterName(String name) {
        mCounterName = name;
    }

    public void setCounterId(String number) {
        mCounterId = number;
    }

    public void setIsBoysCounter(boolean boysCounter) {
        mBoysCounter = boysCounter;
    }

    public void setIsSection(boolean isSection) {
        mIsSeparator = isSection;
    }
}
