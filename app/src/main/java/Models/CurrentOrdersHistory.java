
package Models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentOrdersHistory {

    @SerializedName("history")
    @Expose
    private List<History> history = new ArrayList<History>();

    /**
     * 
     * @return
     *     The history
     */
    public List<History> getHistory() {
        return history;
    }

    /**
     * 
     * @param history
     *     The history
     */
    public void setHistory(List<History> history) {
        this.history = history;
    }

}
