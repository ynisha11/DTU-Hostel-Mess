package utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<String,Void,Boolean> {

    String request;
    String url;
    Context context;
    private static String response="NA";

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;

    public MyAsyncTask(Context context,String request,String url,AsyncResponse delegate) {
        this.request=request;
        this.url=url;
        this.context=context;
        this.delegate=delegate;
        //System.out.println("Request Recieved by MyAsynctask "+request);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        delegate.processFinish(response);
        if(response==null)
        {
            Toast.makeText(context, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Boolean doInBackground(String... arg0) {

        try{
            response = new WebService(url).executePost(request);
            System.out.println("Response in Async Task "+response);
        }
        catch(Exception e)
        {
            System.out.println("Exception is " + e);
        }
        return true;
    }

}