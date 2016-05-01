package utils;

import android.app.Application;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class WebService extends Application {

    private String _apiUrl;

    public void setApiUrl(String _apiUrl) {
        this._apiUrl = _apiUrl;
    }
    public WebService(String url) {
        setApiUrl(url);
    }

    public String executePost(String json) {

        String tokenResponse=null;
        HttpPost httpPost=null;
        try
        {
            httpPost = new HttpPost(_apiUrl);
            //System.out.println("API_URL "+_apiUrl);
            StringEntity entity = new StringEntity(json, HTTP.UTF_8);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httpPost);
            //System.out.println("Response to out for delivery");
            tokenResponse=EntityUtils.toString(response.getEntity());
            //System.out.println(tokenResponse);
        }
        catch(Exception e)
        {
            System.out.println("Exception "+e);
        }
        finally
        {
            if(httpPost!=null)
                httpPost.abort();
        }

        return tokenResponse;
    }

}