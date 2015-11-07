package com.mdstudios.quickweatherinannarbor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String sourceUrl = "http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=426834b575ad9e957330144da9940740";


    TextView mDisplayView;
    UpdateTask mUpdateTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDisplayView = (TextView) findViewById(R.id.weatherText);
    }

    public void updateClick(View v) {
        if(mUpdateTask != null) {
            mUpdateTask.cancel(true);
        }

        mUpdateTask = new UpdateTask();
        mUpdateTask.execute();
    }

    private class UpdateTask extends AsyncTask<Void, Void, Void> {
        String mResult = "";


        @Override
        protected Void doInBackground(Void... voids) {
            // Create service handler class instance to handle HTTP
            ServiceHandler sh = new ServiceHandler();

            // Make a request to url and get response
            String jsonStr = sh.makeServiceCall(sourceUrl, ServiceHandler.GET);
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                mResult = "Temperature: " +
                        (jsonObj.getJSONObject("main").getDouble("temp")*9/5-459.67);
            } catch (JSONException e) {
                e.printStackTrace();
                mResult = "Failed to get the temp";
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mDisplayView.setText(mResult);
        }
    }
}
