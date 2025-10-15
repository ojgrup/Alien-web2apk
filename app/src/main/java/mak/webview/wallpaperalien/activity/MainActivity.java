package mak.webview.wallpaperalien.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.aliendroid.alienads.AlienGDPR;
import com.aliendroid.alienads.AlienOpenAds;
import com.aliendroid.alienads.AliendroidBanner;
import com.aliendroid.alienads.AliendroidInitialize;
import com.aliendroid.alienads.AliendroidIntertitial;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mak.webview.wallpaperalien.BuildConfig;
import mak.webview.wallpaperalien.R;
import mak.webview.wallpaperalien.activity.More.MoreAdapter;
import mak.webview.wallpaperalien.activity.More.MoreList;
import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;
import static com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE;
import static mak.webview.wallpaperalien.activity.Settings.BACKUP_ADS_BANNER;
import static mak.webview.wallpaperalien.activity.Settings.BACKUP_ADS_INTER;
import static mak.webview.wallpaperalien.activity.Settings.CHILD_DIRECT_GDPR;
import static mak.webview.wallpaperalien.activity.Settings.HIGH_PAYING_KEYWORD1;
import static mak.webview.wallpaperalien.activity.Settings.HIGH_PAYING_KEYWORD2;
import static mak.webview.wallpaperalien.activity.Settings.HIGH_PAYING_KEYWORD3;
import static mak.webview.wallpaperalien.activity.Settings.HIGH_PAYING_KEYWORD4;
import static mak.webview.wallpaperalien.activity.Settings.HIGH_PAYING_KEYWORD5;
import static mak.webview.wallpaperalien.activity.Settings.INITIALIZE_SDK;
import static mak.webview.wallpaperalien.activity.Settings.INITIALIZE_SDK_BACKUP_ADS;
import static mak.webview.wallpaperalien.activity.Settings.INTERVAL;
import static mak.webview.wallpaperalien.activity.Settings.MAIN_ADS_BANNER;
import static mak.webview.wallpaperalien.activity.Settings.MAIN_ADS_INTER;
import static mak.webview.wallpaperalien.activity.Settings.ON_OF_DATA;
import static mak.webview.wallpaperalien.activity.Settings.SELECT_ADS;
import static mak.webview.wallpaperalien.activity.Settings.SELECT_BACKUP_ADS;
import static mak.webview.wallpaperalien.activity.Settings.URL_WEBVIEW;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int MY_REQUEST_CODE = 17326;
    ReviewInfo reviewInfo;
    ReviewManager manager;

    protected static final String TAG = MainActivity.class.getSimpleName();
    private WebView mWebView;
    private RecyclerView recyclerView;
    private MoreAdapter adapter;
    List<MoreList> webLists;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    boolean redirect = false;
    RelativeLayout fragma2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setVisibility(View.GONE);
        Review();
        webLists = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar);

        if (ON_OF_DATA.equals("1")){
            if (checkConnectivity()){
                loadUrlData();
            } else {
                ambildata();
            }
        } else {
            ambildata();
        }

        setSupportActionBar(toolbar);
        fragma2 = findViewById(R.id.fragma2);

        mWebView = findViewById(R.id.activity_main_webview);
        mWebView.setWebViewClient(new MyBrowser ());
        mWebView.setWebChromeClient(new ChromeClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings .setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);

        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength){
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                request.setDescription("Download file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
            }
        });

        mWebView.loadUrl(URL_WEBVIEW);
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        RelativeLayout layAdsbanner = findViewById(R.id.mainLayout);
        AlienGDPR.loadGdpr(MainActivity.this, SELECT_ADS, CHILD_DIRECT_GDPR);
        switch (SELECT_ADS) {
            case "ADMOB":
                AliendroidInitialize.SelectAdsAdmob(this, SELECT_BACKUP_ADS, INITIALIZE_SDK_BACKUP_ADS);
                break;
            case "APPLOVIN-D":
                AliendroidInitialize.SelectAdsApplovinDis(this, SELECT_BACKUP_ADS, INITIALIZE_SDK_BACKUP_ADS);
                break;
            case "APPLOVIN-M":
                AliendroidInitialize.SelectAdsApplovinMax(this, SELECT_BACKUP_ADS, INITIALIZE_SDK_BACKUP_ADS);
                break;
            case "MOPUB":
                AliendroidInitialize.SelectAdsMopub(this, SELECT_BACKUP_ADS, INITIALIZE_SDK, INITIALIZE_SDK_BACKUP_ADS);
                break;
            case "IRON":
                AliendroidInitialize.SelectAdsIron(this, SELECT_BACKUP_ADS, INITIALIZE_SDK, INITIALIZE_SDK_BACKUP_ADS);
                break;
            case "STARTAPP":
                AliendroidInitialize.SelectAdsStartApp(this, SELECT_BACKUP_ADS, INITIALIZE_SDK, INITIALIZE_SDK_BACKUP_ADS);
                break;
        }
        switch (SELECT_ADS) {
            case "ADMOB":
                AliendroidBanner.SmallBannerAdmob(MainActivity.this, layAdsbanner, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER, HIGH_PAYING_KEYWORD1,
                        HIGH_PAYING_KEYWORD2, HIGH_PAYING_KEYWORD3, HIGH_PAYING_KEYWORD4, HIGH_PAYING_KEYWORD5);
                AliendroidIntertitial.LoadIntertitialAdmob(MainActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTER, BACKUP_ADS_INTER, HIGH_PAYING_KEYWORD1,
                        HIGH_PAYING_KEYWORD2, HIGH_PAYING_KEYWORD3, HIGH_PAYING_KEYWORD4, HIGH_PAYING_KEYWORD5);
                AlienOpenAds.ShowOpen(MainActivity.this);
                break;
            case "APPLOVIN-M":
                AliendroidBanner.SmallBannerApplovinMax(MainActivity.this, layAdsbanner, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER);
                AliendroidIntertitial.LoadIntertitialApplovinMax(MainActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTER, BACKUP_ADS_INTER);
                break;
            case "APPLOVIN-D":
                AliendroidBanner.SmallBannerApplovinDis(MainActivity.this, layAdsbanner, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER);
                AliendroidIntertitial.LoadIntertitialApplovinDis(MainActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTER, BACKUP_ADS_INTER);
                break;
            case "MOPUB":
                AliendroidBanner.SmallBannerMopub(MainActivity.this, layAdsbanner, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER);
                AliendroidIntertitial.LoadIntertitialMopub(MainActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTER, BACKUP_ADS_INTER);
                break;
            case "STARTAPP":
                AliendroidBanner.SmallBannerStartApp(MainActivity.this, layAdsbanner, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER);
                AliendroidIntertitial.LoadIntertitialStartApp(MainActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTER, BACKUP_ADS_INTER);
                break;
            case "IRON":
                AliendroidBanner.SmallBannerIron(MainActivity.this, layAdsbanner, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER);
                AliendroidIntertitial.LoadIntertitialIron(MainActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTER, BACKUP_ADS_INTER);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            exitapp();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_more) {
            fragma2.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_about) {
            mWebView.loadUrl("file:///android_asset/about.html");
            fragma2.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        }else if (id == R.id.nav_share) {

            String shareLink = "https://play.google.com/store/apps/details?id="
                    + BuildConfig.APPLICATION_ID;
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    getResources().getString(R.string.shareit)
                            + shareLink);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_beranda) {
            mWebView.loadUrl(URL_WEBVIEW);
            fragma2.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

    }else if (id == R.id.nav_pri) {
            mWebView.loadUrl("file:///android_asset/privacy_police.html");
            fragma2.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else if (id == R.id.nav_rate) {
            String str = "https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID;
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(str)));
        }else if (id == R.id.nav_update) {
            checkUpdate();

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        exitapp();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true; // in both cases we handle the link manually

        }

        public void onReceivedError(WebView view, int errorCode, String description, String
                failingUrl) {
            Toast.makeText(MainActivity.this, "please activate internet !! ", Toast.LENGTH_SHORT).show();

            view.loadUrl("about:blank");
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            if (!redirect) {
                switch (SELECT_ADS) {
                    case "ADMOB":
                        AliendroidIntertitial.ShowIntertitialAdmob(MainActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTER, BACKUP_ADS_INTER, INTERVAL,
                                HIGH_PAYING_KEYWORD1, HIGH_PAYING_KEYWORD2, HIGH_PAYING_KEYWORD3, HIGH_PAYING_KEYWORD4, HIGH_PAYING_KEYWORD5);
                        break;
                    case "APPLOVIN-D":
                        AliendroidIntertitial.ShowIntertitialApplovinDis(MainActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTER, BACKUP_ADS_INTER, INTERVAL);
                        break;
                    case "APPLOVIN-M":
                        AliendroidIntertitial.ShowIntertitialApplovinMax(MainActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTER, BACKUP_ADS_INTER, INTERVAL);
                        break;
                    case "IRON":
                        AliendroidIntertitial.ShowIntertitialIron(MainActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTER, BACKUP_ADS_INTER, INTERVAL);
                        break;
                    case "MOPUB":
                        AliendroidIntertitial.ShowIntertitialMopub(MainActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTER, BACKUP_ADS_INTER, INTERVAL);
                        break;
                    case "STARTAPP":
                        AliendroidIntertitial.ShowIntertitialSartApp(MainActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTER, BACKUP_ADS_INTER, INTERVAL);
                        break;
                }
            } else {
                redirect = false;
            }
        }
    }

    private void exitapp() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Exit App");
        builder.setMessage("Are you sure you want to leave the application?");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which){
                finish();

            }
        });

        builder.setNegativeButton("No",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }


    private class ChromeClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        ChromeClient() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    private void loadUrlData() {

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
               Settings.URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("More");
                    for (int i = 0; i < array.length(); i++){
                        JSONObject jo = array.getJSONObject(i);
                        MoreList developers = new MoreList(jo.getString("title"),
                                jo.getString("image"), jo.getString("link"));
                        webLists.add(developers);
                    }
                    adapter = new MoreAdapter(webLists, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }



    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = MainActivity.this.getAssets().open("moreapp.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public void ambildata(){
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("More");
            // Extract data from json and store into ArrayList as class objects
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                MoreList dataUrl = new MoreList();
                dataUrl.html_url = jsonData.getString("title");
                dataUrl.avatar_url = jsonData.getString("image");
                dataUrl.psl_url = jsonData.getString("link");
                webLists.add(dataUrl);

            }

            adapter = new MoreAdapter(webLists, MainActivity.this);
            recyclerView.setAdapter(adapter);

        } catch (JSONException e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkConnectivity() {
        boolean enabled = true;

        ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if ((info == null || !info.isConnected() || !info.isAvailable())) {
            // Toast.makeText(getApplicationContext(), "Sin conexión a Internet...", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /*
   In App Update
    */
    AppUpdateManager appUpdateManager;
    com.google.android.play.core.tasks.Task<AppUpdateInfo> appUpdateInfoTask;

    private void checkUpdate(){
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager.registerListener(listener);

        appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                Log.d("appUpdateInfo :", "packageName :"+appUpdateInfo.packageName()+ ", "+ "availableVersionCode :"+ appUpdateInfo.availableVersionCode() +", "+"updateAvailability :"+ appUpdateInfo.updateAvailability() +", "+ "installStatus :" + appUpdateInfo.installStatus() );

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(FLEXIBLE)){
                    requestUpdate(appUpdateInfo);
                    Log.d("UpdateAvailable","update is there ");
                }
                else if (appUpdateInfo.updateAvailability() == 3){
                    Log.d("Update","3");
                    notifyUser();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No Update Available", Toast.LENGTH_SHORT).show();
                    Log.d("NoUpdateAvailable","update is not there ");
                }
            }
        });
    }
    private void requestUpdate(AppUpdateInfo appUpdateInfo){
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, MainActivity.this,MY_REQUEST_CODE);
            resume();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE){
            switch (resultCode){
                case Activity.RESULT_OK:
                    if(resultCode != RESULT_OK){
                        Toast.makeText(this,"RESULT_OK" +resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_OK  :",""+resultCode);
                    }
                    break;
                case Activity.RESULT_CANCELED:

                    if (resultCode != RESULT_CANCELED){
                        Toast.makeText(this,"RESULT_CANCELED" +resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_CANCELED  :",""+resultCode);
                    }
                    break;
                case RESULT_IN_APP_UPDATE_FAILED:

                    if (resultCode != RESULT_IN_APP_UPDATE_FAILED){

                        Toast.makeText(this,"RESULT_IN_APP_UPDATE_FAILED" +resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_IN_APP_FAILED:",""+resultCode);
                    }
            }

            if (resultCode == Activity.RESULT_OK) {
                Uri treeUri = data.getData();
                int takeFlags = data.getFlags();
                takeFlags &= (Intent.FLAG_GRANT_READ_URI_PERMISSION |
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                    Log.i("TAG", "takePersistableUriPermission: " + treeUri);
                    this.getContentResolver().takePersistableUriPermission(treeUri, takeFlags);

                }

            }
        }
    }

    InstallStateUpdatedListener listener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState installState) {
            if (installState.installStatus() == InstallStatus.DOWNLOADED){
                Log.d("InstallDownloded","InstallStatus sucsses");
                notifyUser();
            }
        }
    };

    private void notifyUser() {
        Snackbar snackbar =
                Snackbar.make(findViewById(R.id.frame_container),
                        "An update has just been downloaded.",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RESTART", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(
                getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    private void resume(){
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
                    notifyUser();

                }

            }
        });
    }

    /*
    In app review
     */

    private void Review(){
        manager = ReviewManagerFactory.create(this);
        manager.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            @Override
            public void onComplete(@NonNull Task<ReviewInfo> task) {
                if(task.isSuccessful()){
                    reviewInfo = task.getResult();
                    manager.launchReviewFlow(MainActivity.this, reviewInfo).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            //Toast.makeText(MainActivity.this, "Rating Failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Toast.makeText(MainActivity.this, "Review Completed, Thank You!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                //Toast.makeText(MainActivity.this, "In-App Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
