package sg.edu.rp.c346.id21018157.demoweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import cz.msebera.android.httpclient.entity.mime.Header;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ListView lvWeather;
    AsyncHttpClient client;

    ArrayList<Weather> alWeather;
    ArrayAdapter<Weather> aaWeather;


    @Override
    protected void onResume() {
        super.onResume();

        alWeather = new ArrayList<Weather>(); //create arraylist to store objects
        //aaWeather = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alWeather); //create arrayadapter to bind
        //implicit. change context "this" - android can tell it is mainActivity.java

        client.get("https://api.data.gov.sg/v1/environment/2-hour-weather-forecast", new JsonHttpResponseHandler() {

            String area;
            String forecast;


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArrItems = response.getJSONArray("items"); //response is the JSONObject gotten from the URL, jsonArrItems = items (getJSONArray)
                    JSONObject firstObj = jsonArrItems.getJSONObject(0); //in JSON viewer: {} 0. firstObj = 0 (getJSONObject)
                    JSONArray jsonArrForecasts = firstObj.getJSONArray("forecasts"); //in JSON viewer: [] forecast. jsonArrForecasts = forecast (getJSONArray)
                    for(int i = 0; i < jsonArrForecasts.length(); i++) {
                        JSONObject jsonObjForecast = jsonArrForecasts.getJSONObject(i);
                        area = jsonObjForecast.getString("area"); //get String and provide the key. "" is the key
                        forecast = jsonObjForecast.getString("forecast");
                        Weather weather = new Weather(area, forecast); //create object
                        alWeather.add(weather); //add weather obj into arraylist
                    }
                }
                catch(JSONException e){

                }

                //POINT X – Code to display List View
                aaWeather = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, alWeather);
                //explicit. change context: "this" to "MainActivity.this"
                lvWeather.setAdapter(aaWeather);


            }//end onSuccess
        });
    }//end onResume



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvWeather = findViewById(R.id.lv);
        client = new AsyncHttpClient(); //declaring variable 'client' as new AsyncHttpClient obj



    }
}