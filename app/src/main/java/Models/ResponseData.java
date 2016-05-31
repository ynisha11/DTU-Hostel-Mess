
package Models;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {

    @SerializedName("responseType")
    @Expose
    private String responseType;
    @SerializedName("payload")
    @Expose
    private JsonElement payload;

    /**
     * 
     * @return
     *     The responseType
     */
    public String getResponseType() {
        return responseType;
    }

    /**
     * 
     * @param responseType
     *     The responseType
     */
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    /**
     * 
     * @return
     *     The payload
     */
    public JsonElement getPayload() {
        return payload;
    }

    /**
     * 
     * @param payload
     *     The payload
     */
    public void setPayload(JsonElement payload) {
        this.payload = payload;
    }

}
