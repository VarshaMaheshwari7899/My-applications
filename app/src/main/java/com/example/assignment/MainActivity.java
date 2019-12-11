package com.example.assignment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Timestamp;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView UserName,UserLocation,TotalRides,TotalFreeRides,TotalCredit;
    ImageView UserProfile;
    private RequestQueue mRequestQueue;
    RequestOptions options;

    RecyclerView TripListView;
    List<tripinfo> tripinfoList;
    tripinfoadapter tripinfo_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Profile Objects
        UserName = findViewById(R.id.UserName);
        UserLocation = findViewById(R.id.UserLocation);
        UserProfile = findViewById(R.id.UserProfileImage);

        //Ride Objects
        TotalRides = findViewById(R.id.TotalRides);
        TotalFreeRides = findViewById(R.id.TotalFreeRides);
        TotalCredit = findViewById(R.id.TotalCredit);

        //Library Queue
        mRequestQueue = Volley.newRequestQueue(this);

        //Glider library option if image not parsed
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.boy)
                .error(R.drawable.boy);

        //Trip List
        tripinfoList = new ArrayList<tripinfo>();
        TripListView = findViewById(R.id.TripsRecyclerView);
        TripListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LoadData();



    }

    void LoadData()
    {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "https://gist.githubusercontent.com/iranjith4/522d5b466560e91b8ebab54743f2d0fc/raw/7b108e4aaac287c6c3fdf93c3343dd1c62d24faf/radius-mobile-intern.json", null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = response.getString("message");
                            if(result.equals("Success"))
                            {
                                JSONObject data = response.getJSONObject("data");

                                //Profile Data
                                JSONObject Profile = data.getJSONObject("profile");
                                String FName = Profile.getString("first_name");
                                String LName = Profile.getString("last_name");
                                String City = Profile.getString("city");
                                String Country = Profile.getString("Country");
                                String Image = Profile.getString("middle_name");


                                //Set Profile Data
                                UserName.setText(FName + " " + LName);
                                UserLocation.setText(City + ", " + Country);
                                Glide.with(getApplicationContext()).load(Image).apply(options).into(UserProfile);

                                //Ride Data
                                JSONObject Stats = data.getJSONObject("stats");
                                String Rides = Stats.getString("rides");
                                String FreeRides = Stats.getString("free_rides");
                                JSONObject Credits = Stats.getJSONObject("credits");
                                String Value = Credits.getString("value");
                                String Currency = Credits.getString("currency");
                                String Currency_Symbol = Credits.getString("currency_symbol");

                                //Set Ride Data
                                TotalRides.setText(Rides);
                                TotalFreeRides.setText(FreeRides);
                                TotalCredit.setText(Currency_Symbol + "" + Value);

                                //Trip Array
                                JSONArray Trips = data.getJSONArray("trips");

                                JSONObject jsonObject = null;

                                for(int i=0;i<Trips.length();i++)
                                {
                                    //Getting Trip Data
                                    jsonObject = Trips.getJSONObject(i);
                                    String from = jsonObject.getString("from");
                                    String to = jsonObject.getString("to");
                                    long from_time = jsonObject.getLong("from_time");
                                    long to_time = jsonObject.getLong("to_time");
                                    JSONObject cost = jsonObject.getJSONObject("cost");
                                    String value  = cost.getString("value");
                                    String currency  = cost.getString("currency");
                                    String currency_symbol  = cost.getString("currency_symbol");
                                    Integer trip_duration = jsonObject.getInt("trip_duration_in_mins");

                                    Date from_time_D = new Date(from_time );
                                    Date to_time_D = new Date(to_time );

                                    String f_to_D = String.valueOf(from_time_D);
                                    String t_to_D = String.valueOf(to_time_D);

                                    //Convert timeinto hr and mins
                                    int hours = trip_duration/ 60; //since both are ints, you get an int
                                    int minutes = trip_duration % 60;
                                    String tripTime = "";
                                    if(hours==0)
                                    {
                                         tripTime =  minutes + "min";
                                    }
                                    else
                                    {
                                         tripTime = hours + "h " + minutes + "min";
                                    }

                                    //Convert Date To Proper format
                                    SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM HH:mm");
                                    f_to_D = formatter.format(from_time_D);
                                    t_to_D = formatter.format(to_time_D);

                                    //Creating a trip object
                                    tripinfoList.add(new tripinfo(from,f_to_D,to,t_to_D,currency_symbol + " " + value,tripTime,""));



                                }

                                //Initialising Trip Adapter
                                tripinfo_adapter = new tripinfoadapter(getApplicationContext(),tripinfoList);
                                //Adding it to recycler
                                TripListView.setAdapter(tripinfo_adapter);
                                tripinfo_adapter.notifyDataSetChanged();
                            }
                            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);

    }
}
