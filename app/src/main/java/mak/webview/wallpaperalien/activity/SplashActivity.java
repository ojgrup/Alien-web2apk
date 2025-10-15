package mak.webview.wallpaperalien.activity;



import static mak.webview.wallpaperalien.activity.Settings.ADMOB_OPENADS;
import static mak.webview.wallpaperalien.activity.Settings.ON_OFF_ADS;
import static mak.webview.wallpaperalien.activity.Settings.URL_DATA;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.SparseLongArray;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aliendroid.alienads.AlienOpenAds;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mak.webview.wallpaperalien.R;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        if (ON_OFF_ADS.equals("1")) {
            if (checkConnectivity()) {
                loadUrlData();
            }
        }

        AlienOpenAds.LoadOpenAds(ADMOB_OPENADS);
        new CountDownTimer(10000, 1000) {
            @Override
            public void onFinish() {

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }

    private void loadUrlData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray contacts = jsonObj.getJSONArray("Ads");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        Settings.STATUS_APP = c.getString("status_app");
                        Settings.LINK_REDIRECT = c.getString("link_redirect");
                        Settings.SELECT_ADS = c.getString("select_main_ads");
                        Settings.SELECT_BACKUP_ADS = c.getString("select_backup_ads");
                        Settings.MAIN_ADS_BANNER = c.getString("main_ads_banner");
                        Settings.MAIN_ADS_INTER = c.getString("main_ads_intertitial");
                        Settings.BACKUP_ADS_BANNER = c.getString("backup_ads_banner");
                        Settings.BACKUP_ADS_INTER = c.getString("backup_ads_intertitial");
                        Settings.INITIALIZE_SDK = c.getString("initialize_sdk");
                        Settings.INITIALIZE_SDK_BACKUP_ADS = c.getString("initialize_sdk_backup_ads");
                        Settings.INTERVAL = c.getInt("interval_intertitial");
                        Settings.HIGH_PAYING_KEYWORD1 = c.getString("high_paying_keyword_1");
                        Settings.HIGH_PAYING_KEYWORD2 = c.getString("high_paying_keyword_2");
                        Settings.HIGH_PAYING_KEYWORD3 = c.getString("high_paying_keyword_3");
                        Settings.HIGH_PAYING_KEYWORD4 = c.getString("high_paying_keyword_4");
                        Settings.HIGH_PAYING_KEYWORD5 = c.getString("high_paying_keyword_5");
                        Settings.URL_WEBVIEW = c.getString("url_webiste");

                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SplashActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(SplashActivity.this);
        requestQueue.add(stringRequest);

    }


    private boolean checkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        // Toast.makeText(getApplicationContext(), "Sin conexión a Internet...", Toast.LENGTH_SHORT).show();
        return info != null && info.isConnected() && info.isAvailable();
    }
}
