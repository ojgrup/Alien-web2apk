package mak.webview.wallpaperalien.activity;


public class Settings {
    /*
     Please upload guide_online.json to your hosting, archive.org, etc
     and fill URL_DATA with your link
      */
    public static final String URL_DATA = "https://aliendro.id/uploads/demo/webview/alien_webview.json";
    /*
    use file:///android_asset/namefile.html if set data offline from folder asset
    URL_WEBVIEW  = "file:///android_asset/namefile.html" or
    URL_WEBVIEW = "https://nameweb.id" for get data from internet
     */
    public static String URL_WEBVIEW = "https://aliendro.id";
    /*
   ON_OF_JSON="0" offline data more app, get data from json on folder asset
   ON_OF_JSON="1" online data more, get data from url json
   */
    public static String ON_OF_DATA = "1";
    /*
    ON_OFF_ADS ="0" offline ads, get data from Settings.java
    ON_OFF_ADS ="1" online ads, get data from url json
     */
    public static String ON_OFF_ADS = "1";

    /*
     SELECT_ADS="ADMOB"
     SELECT_ADS="APPLOVIN-M" for MAx and SELECT_ADS="APPLOVIN-D" for Discovery
     SELECT_ADS="STARTAPP"
     SELECT_ADS="MOPUB"
     SELECT_ADS="IRON"
    */
    public static String SELECT_ADS = "ADMOB";

    /*
      SELECT_ADS="APPLOVIN-M" for MAx and SELECT_ADS="APPLOVIN-D" for Discovery
      SELECT_BACKUP_ADS="STARTAPP"
      SELECT_BACKUP_ADS="MOPUB"
      SELECT_BACKUP_ADS="IRON"
      SELECT_BACKUP_ADS="ADMOB"
     */

    public static String SELECT_BACKUP_ADS = "APPLOVIN-D";


    /*Admob ID and Applovin SDK Key
    please check string.xml
    Admob = replace <string name="admobappid">ca-app-pub-3940256099942544~3347511713</string>
    Applovin = replace <string name="sdk_key_applovin">5UhA2fX7QnsiNAwdmPdJW-QTCSqDx1ssvQm1QC252VUsD0sLAG1-2hpDaqHKsM3ZwH0RlcTNcLKTn-gB_xBeBo</string>
    with your id
     */

    /*
    not recommended to use admob interstitial ads for Webview
     */

    public static String MAIN_ADS_INTER = "ca-app-pub-3940256099942544/1033173712";
    public static String MAIN_ADS_BANNER = "ca-app-pub-3940256099942544/6300978111";

    /*
    Backup Ads only for ADMOB Ads,
    fill qwerty1234 if not uses backup
     */
    public static String BACKUP_ADS_INTER = "qwerty1234";
    public static String BACKUP_ADS_BANNER = "b195f8dd8ded45fe847ad89ed1d016da";

    /*
    INITIALIZE_SDK  and INITIALIZE_SDK_BACKUP_ADS uses for MOBUP, STARTAPP and IRONSOURCE
    fill INITIALIZE_SDK or INITIALIZE_SDK_BACKUP_ADS with App Key for IRONSOURCE
    example : INITIALIZE_SDK ="107355779"

    fill INITIALIZE_SDK or INITIALIZE_SDK_BACKUP_ADS with Banner ID for MOPUB
    example : INITIALIZE_SDK ="b195f8dd8ded45fe847ad89ed1d016da"

    fill INITIALIZE_SDK or INITIALIZE_SDK_BACKUP_ADSwith App ID for STARTAPP
    example : INITIALIZE_SDK ="123456789"

    if use Admob and Applovin as main or backup ads please input INITIALIZE_SDK ="" or INITIALIZE_SDK_BACKUP_ADS =""
     */
    public static String INITIALIZE_SDK = ""; //Main Ads
    public static String INITIALIZE_SDK_BACKUP_ADS = ""; // Backup Ads

    /*
    OPEN Ads only for ADMOB
    can't load from json online
     */
    public static String ADMOB_OPENADS = "ca-app-pub-3940256099942544/3419835294";

    /*
    HPK only for Admob and Applovin-D (Discovery)
    please fill with HPK Adsense/Admob like finance, etc or you can ignore it

    uses HPK
    public static String HIGH_PAYING_KEYWORD1="finance";

    ignore HPK
    public static String HIGH_PAYING_KEYWORD1="";
     */
    public static String HIGH_PAYING_KEYWORD1 = "";
    public static String HIGH_PAYING_KEYWORD2 = "";
    public static String HIGH_PAYING_KEYWORD3 = "";
    public static String HIGH_PAYING_KEYWORD4 = "";
    public static String HIGH_PAYING_KEYWORD5 = "";

    /*
    STATUS_APP = "0" must set 0 if your app still live on playstore
    and STATUS_APP = "1" if your app "suspend"
    fill LINK_REDIRECT with new link app
    */
    public static String STATUS_APP = "0";
    public static String LINK_REDIRECT = "https://play.google.com/store/apps/details?id=com.ad.tesiqkepribadia&hl=en";

    /*
  Interval for Intertitial ads
   */
    public static int INTERVAL = 5;

    public static boolean CHILD_DIRECT_GDPR = true;
}

