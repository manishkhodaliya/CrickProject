package com.example.blackhat.mlive.util;

/**
 * Created by BlackHat on 17/03/2017.
 */
public final class AppConstant {

    //public static final String WSURI="ws://mlive.bcommo.in:8888/WebSocketsServer2.ashx?type=Watch&userid=1956&profileid=2400&rowsize=20&android_id=1&currentpage=1";
    // public static final String WSURI="://mlive.bcommo.in:8888/WebSocketsServer2.ashx?type=Watch";
    public static final String USERID = "&userid=";
    public static final String PROFILEID = "&profileid=";
    public static final String ROWSIZE = "&rowsize=";
    public static final String ANDROID_ID = "&android_id=";
    public static final String CURRENTPAGE = "&currentpage=";
    public static final String SCRIPTNAME = "&scriptname=";
    //public static final String INDEXURL="://d1.user11.com:3030/MliveAppSocket/index/";
    public static final String INDEXURL = "://mlive.bcommo.in:8888/WebSocketsServerIndex.ashx?userid=";

    public static final String WSURI = "://mlive.bcommo.in:3030/MliveAppSocket/watch/";
    //  public static final String S_COMMAND="StartWatch";
    // public static final String ST_COMMAND="StopWatch";
    public static final String S_COMMAND = "S";
    public static final String ST_COMMAND = "ST";
    public static final String SLASH = "/";

    public static final String STARTINDEX = "StartWatch";

    public static final String WS = "ws";

    public static final String MARKETDEPTHURI = "://mlive.bcommo.in:8888/WebSocketsServerIndex.ashx?userid=";
    public static final String SETDEFAULT="://mlive.bcommo.in:76/appmediator/index.php/Home/setDefault";
    public static final String GETUSERPROFILE="://mlive.bcommo.in:76/appmediator/index.php/welcome/getResult";
    public static final String PROFILE_URI="://mlive.bcommo.in:188/Profile.asmx/getUserProfile";
    public static final String GETSCRIPT="://mlive.bcommo.in:76/appmediator/index.php/Home/getUserProfilesItems";
    public static final String ADDPROFILE="://mlive.bcommo.in:188/Profile.asmx/add_profile";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_X_FORM_URL = "application/x-www-form-urlencoded";

    public static final String HTTP = "http";
    public static final String HTTP_PORT = ":188";
    public static final String HTTP_JAVA_PORT = ":3030";

    //String constant
    public static final int MAXFONTSIZE = 20;
    public static final String BACKGROUND_CLOR_BLINK = "backgroundColor";
    public static final String PROFILE_LIST_TITLE = "<font color='#FFAA00'>Select your Profile</font>";
    public static final String STR_MARKET_DEPTH = "Market Depth", STR_HIGH_LOW = "High Low", STR_NEXT_CONTRACT = "Next Contract", STR_SET_ALERT = "Set Alert",
    STR_DEFAULT = "Set Default", ST_REMOVE_SCRIPT = "Remove Script", STR_CLOSE = "Close";

    // Api mapping
    private static final String API_CONTEXT = "/MliveApi/rest";
    public static final String LOGIN_MAPPING = API_CONTEXT + "/user/login";
    public static final String PROFILE_URI_MAPPING = API_CONTEXT + "/profile/getall/byuserid";

    //Objetc name to pass or use as aprameter
    public static final String OBJ_PROFILE = "p";
    public static final String OBJ_PROFILE_LIST = "profileList";
    public static final String OBJ_DEAFAULT = "Default";
    public static final String OBJ_BUY_PRICE = "buyprice";
    public static final String OBJ_SELL_PRICE = "sellprice";
    public static final String OBJ_DRAWABLE= "drawable";
    public static final String OBJ_U = "u";

    //Preferences Object
    public static final String PREF_LOGIN = "LoginPref";
    public static final String PREF_PROFILE = "profile";
    public static final String PREF_LOGIN_USER_NAME = "LoginUserName";
    public static final String PREF_LOGIN_PASSWORD = "LoginPassword";
    public static final String PREF_SERVER = "Server";
    public static final String PREF_CURRENT_USER_JSON = "CurrentUser";
    public static final String PREF_USER_ID = "userId";
    public static final String PREF_USER = "user";

    //Messages
    public static final String MESSAGE_GETTING_DATA = "Getting Data...";
    public static final String MESSAGE_GETTING_PROFILES = "Getting Profiles...";
    public static final String MESSAGE_BLANK_USERNAME_PASSWORD = "Enter Username and Password";
    public static final String MESSAGE_SERVER_CONNECTION_PROBLEM = "Server connection problem !";
    public static final String MESSAGE_USER_NOT_FOUND = "User Not Found";
    public static final String MESSAGE_AUTHENTICATING = "Authanticating...";



}
