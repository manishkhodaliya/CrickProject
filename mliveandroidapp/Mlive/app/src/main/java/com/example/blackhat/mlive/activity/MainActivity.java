package com.example.blackhat.mlive.activity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Html;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.blackhat.mlive.R;
import com.example.blackhat.mlive.model.RateAdapter;
import com.example.blackhat.mlive.model.RateModel;
import com.example.blackhat.mlive.pojo.ClientRequest;
import com.example.blackhat.mlive.pojo.Index;
import com.example.blackhat.mlive.pojo.ListComplexObject;
import com.example.blackhat.mlive.pojo.MkW;
import com.example.blackhat.mlive.pojo.Profile;
import com.example.blackhat.mlive.pojo.User;
import com.example.blackhat.mlive.util.AppConstant;
import com.example.blackhat.mlive.util.ApplicationUtils;
import com.example.blackhat.mlive.util.ComplexPreferences;
import com.example.blackhat.mlive.util.ConnectionAppication;
import com.example.blackhat.mlive.util.ConnectivityReceiver;
import com.example.blackhat.mlive.util.RecyclerItemClickListener;
import com.fasterxml.jackson.databind.util.BeanUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;


public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    //responsible variable for Market watch
    private LinkedList< MkW > listFirstData;
    private Map< String, Integer > scriptIndexMap;
    private WebSocketConnection mConnection;
    private ClientRequest clientRequest ;
    private List< RateModel > rateModelList;
    private RecyclerView recyclerView;
    private RateAdapter rateAdapter;
    private Context context;
    private ObjectAnimator textColorAnim, textColorAnim2, textColorAnim3, textColorAnim4, textColorAnim5, textColorAnim6;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private static final int blinkDuration = 1000;
    public static int size_of_items = 20;
    private Boolean skipUpdate = Boolean.FALSE;
    private static Integer PAGE_NO = 1;

    //Responsible variable for sensex
    private static boolean fIndex;
    private static boolean _skipUpdateIndex;
    private static ArrayList< Index > listFirstDataIndex;
    private static Map< Integer, String > scriptIndexMapIndex;
    private WebSocketConnection mIndexConnection;
    private List< Index > rateModelListIndex;

    private User user;
    private List< Profile > profileCommo;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder scriptOptions;
    private TextView selectCommodity;
    private String profile_id = null;
    private ProgressDialog mProgress;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ComplexPreferences complexPreferences;
    private ListComplexObject listComplexObject;


    private SeekBar sk;
    private TextView textViewFont;
    private TextView txtSensex, txtSensexChange, txtNifty, txtNiftyChange;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog( this );
        progressDialog.setMessage(AppConstant.MESSAGE_GETTING_DATA);
        progressDialog.setCancelable(Boolean.TRUE);
        progressDialog.setIndeterminate(Boolean.TRUE);

        recyclerView = ( RecyclerView ) findViewById( R.id.rateReacylerView );

        context = getApplicationContext();

        preferences = getApplicationContext().getSharedPreferences(AppConstant.PREF_LOGIN, 0);
        editor = preferences.edit();

        txtNifty = ( TextView ) findViewById( R.id.txtNifty );
        txtNiftyChange = ( TextView ) findViewById( R.id.txtNiftyChange );
        txtSensex = ( TextView ) findViewById( R.id.txtSensex );
        txtSensexChange = ( TextView ) findViewById( R.id.txtSensexChange );
        selectCommodity = ( TextView ) findViewById( R.id.dropdownCommodity );

        builder = new AlertDialog.Builder( this );
        scriptOptions = new AlertDialog.Builder( this );

        progressDialog.show();
        complexPreferences = ComplexPreferences.getComplexPreferences(this, AppConstant.PREF_PROFILE, MODE_PRIVATE);
        user = ComplexPreferences.getComplexPreferences( this, AppConstant.PREF_USER, MODE_PRIVATE ).
                getObject(AppConstant.PREF_USER, User.class);
        listComplexObject = complexPreferences.getObject(AppConstant.OBJ_PROFILE_LIST, ListComplexObject.class);

        profileCommo = listComplexObject.getProfileList();

        sk = ( SeekBar ) findViewById( R.id.seekbar );
        sk.setVisibility( View.INVISIBLE );
        textViewFont = ( TextView ) findViewById( R.id.textViewFont );

        textViewFont.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( sk.getVisibility() != View.VISIBLE ) {
                    sk.setVisibility( View.VISIBLE );
                } else {
                    sk.setVisibility( View.INVISIBLE );
                }
            }
        });

        mProgress = new ProgressDialog( this );
        mProgress.setIndeterminate( Boolean.TRUE );
        mProgress.setCancelable( Boolean.TRUE );
        mProgress.setMessage( AppConstant.MESSAGE_GETTING_DATA );

        final WindowManager.LayoutParams params = getWindow().getAttributes();
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );



        if ( profileCommo.size() > 0 ) {

            for ( int i = 0; i < profileCommo.size(); i++ ) {

                if ( profileCommo.get( i ).getProfile_type().equals( AppConstant.OBJ_DEAFAULT ) ) {
                    profile_id = profileCommo.get( i ).getProfileid();
                    loadScriptsFromLocal( profileCommo.get( i ) );
                }
            }
            setCommodity();
            start(profile_id);

        }
        startIndex( AppConstant.WS + AppConstant.INDEXURL + user.getUserid() + AppConstant.ANDROID_ID + "1" );

        selectCommodity.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                builder.show();
            }
        } );

        progressDialog.dismiss();

    }

    private void loadScriptsFromLocal( Profile profile ) {
        Map< Integer,String > scriptd = profile.getScripts();
System.out.println("prof ==> "+ApplicationUtils.generateJSONFromObject(profile));
        listFirstData = new LinkedList<>();
        scriptIndexMap = new HashMap<>();

        int k=0;
        for ( Map.Entry< Integer, String > entry:scriptd.entrySet() ){
            if ( k ==  user.getNumberofscriptperpage() ){
                break;
            }

            MkW m = new MkW();
            m.setSn(entry.getValue());

            listFirstData.add(m);

            scriptIndexMap.put(entry.getValue(), k);
System.out.println("script indrc size ==>"+scriptIndexMap.size());
            k++;
        }

System.out.println(recyclerView);
        rateAdapter = new RateAdapter( listFirstData );

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        rateAdapter.setHasStableIds(true);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(rateAdapter);

        //prepareRateDataForFirsttime( listFirstData );
        scriptd=null;

    }

    public void startIndex( final String url ) {

        mIndexConnection = new WebSocketConnection();

        _skipUpdateIndex = Boolean.TRUE;
        fIndex = Boolean.TRUE;
        listFirstDataIndex = new ArrayList< Index >();
        scriptIndexMapIndex = new HashMap<>();
        rateModelListIndex = new ArrayList<>();

        try {
            mIndexConnection.connect( url, new WebSocketHandler() {
                @Override
                public void onOpen() {
                    super.onOpen();

                }

                @Override
                public void onTextMessage( String message ) {

                    if ( fIndex  ) {
                        listFirstDataIndex = ( ArrayList< Index > ) ApplicationUtils.generateObjectFromJSONArray( message, Index.class );

                        prepareRateDataIndex( listFirstDataIndex );
                        fIndex = Boolean.FALSE;

                        try {
                            mIndexConnection.sendTextMessage( AppConstant.STARTINDEX );
                        } catch ( Exception e ) {
                        }
                    } else {
                        prepareRateDataIndex( ( ArrayList<Index> ) ApplicationUtils.generateObjectFromJSONArray( message, Index.class ) );
                    }
                    message = null;
                }

                @Override
                public void onClose( int code, String reason ) {
                    super.onClose( code, reason );
                }

                private void prepareRateDataIndex( ArrayList<Index> listFirstData ) {
                    int size;
                    if ( fIndex ) {

                        size = listFirstData.size();

                        if ( size > 0 ) {
                            for ( int i = 0; i < size; i++ ) {
                                Index index = ApplicationUtils.generateObjectFromJSON( ApplicationUtils.generateJSONFromObject( listFirstData.get( i ) ),
                                        Index.class );

                                Index rateModel = new Index();
                                if ( index.getScriptname() != null ) {

                                    rateModel.setScriptname( index.getScriptname() );
                                    rateModel.setItm_Value6( index.getItm_Value6() );
                                    rateModel.setItm_Value19( index.getItm_Value19() );

                                    scriptIndexMapIndex.put( i, index.getScriptname() );
                                }

                                rateModelListIndex.add( rateModel );

                            }

                            if ( rateModelListIndex.size() != 0 ) {

                                txtNifty.setText( rateModelListIndex.get( 0 ).getItm_Value6() );
                                txtSensex.setText( rateModelListIndex.get( 1 ).getItm_Value6() );

                                txtNiftyChange.setBackground( getColorBackground( "0", rateModelListIndex.get( 0 ).getItm_Value19() ) );
                                txtNiftyChange.setText( rateModelListIndex.get( 0 ).getItm_Value19() );
                                txtSensexChange.setBackground( getColorBackground( "0", rateModelListIndex.get( 1 ).getItm_Value19() ) );
                                txtSensexChange.setText( rateModelListIndex.get( 1 ).getItm_Value19() );
                            }
                        }

                        fIndex = Boolean.FALSE;
                        _skipUpdateIndex = Boolean.FALSE;

                    } else {

                        // check if _skipUpdate is enable we are not going to perform update operation. Because Recycler view is not ready for updation.
                        if ( !_skipUpdateIndex ) {

                            size = listFirstData.size();
                            for ( int i = 0; i < size; i++ ) {

                                Index indexNew = ApplicationUtils.generateObjectFromJSON( ApplicationUtils.generateJSONFromObject( listFirstData.get( i ) ), Index.class );

                                if ( indexNew.getItm_Value19() != null ) {
                                    if ( indexNew.getScriptname().equals( "NIFTY 11NOV2050" ) ) {

                                        if ( Float.parseFloat( indexNew.getItm_Value19() ) > 0 )
                                            txtNiftyChange.setText( "+" + indexNew.getItm_Value19() );
                                        else
                                            txtNiftyChange.setText( indexNew.getItm_Value19() );
                                    } else {

                                        if ( Float.parseFloat( indexNew.getItm_Value19() ) > 0 )
                                            txtSensexChange.setText( "+" + indexNew.getItm_Value19() );
                                        else
                                            txtSensexChange.setText( indexNew.getItm_Value19() );
                                    }
                                }


                            }
                        }
                    }
                    listFirstData= null;
                }

            } );


        } catch ( WebSocketException e ) {
        }



    }


    private Drawable getColorBackground( CharSequence old, String newValue ) {

        Float oldF = Float.parseFloat( old.toString() );
        Float newF = Float.parseFloat( newValue );

        if ( newF > oldF ) {
            return getResources().getDrawable( getResources()
                    .getIdentifier( AppConstant.OBJ_BUY_PRICE, AppConstant.OBJ_DRAWABLE, getPackageName() ) );
        } else {
            return getResources().getDrawable( getResources()
                    .getIdentifier( AppConstant.OBJ_SELL_PRICE, AppConstant.OBJ_DRAWABLE, getPackageName() ) );
        }

    }


    public void setCommodity() {

        List< String > listItems = new ArrayList< String >();

        for ( int i = 0; i < profileCommo.size(); i++ ) {
            if (  profileCommo.get( i ).getProfileid() != profile_id )
                listItems.add( profileCommo.get( i ).getProfilename() );
        }
        final CharSequence[] options = listItems.toArray( new CharSequence[ listItems.size() ] );

        builder.setCancelable( Boolean.TRUE );
        builder.setTitle(Html.fromHtml((AppConstant.PROFILE_LIST_TITLE ) ) );

        builder.setItems( options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {

                for ( int i = 0; i < profileCommo.size(); i++ ) {

                    if ( profileCommo.get( i ).getProfilename().equals( options[ which ] ) ) {

                        skipUpdate = Boolean.FALSE;

                        loadScriptsFromLocal( profileCommo.get( i ) );
                        selectCommodity.setText( options[ which ].toString() + " " );

                        profile_id = profileCommo.get( i ).getProfileid();

                        if ( mConnection != null ) {

                            ClientRequest clientRequesttoStop = new ClientRequest();

                            clientRequesttoStop.setUserId( user.getUserid() );
                            clientRequesttoStop.setIsFirstTime(Boolean.FALSE);
                            clientRequesttoStop.setRawSizeperPage(user.getNumberofscriptperpage());
                            clientRequesttoStop.setPageNo( PAGE_NO );
                            clientRequesttoStop.setProfileId(Integer.parseInt(profile_id));
                            clientRequesttoStop.setWantToStart(Boolean.FALSE);
                            clientRequesttoStop.setWantToStop(Boolean.TRUE);

                            mConnection.sendTextMessage( ApplicationUtils.generateJSONFromObject( clientRequesttoStop ) );
                            clientRequesttoStop = null;

                            ClientRequest clientRequestFirstTime = new ClientRequest();

                            clientRequestFirstTime.setUserId( user.getUserid() );
                            clientRequestFirstTime.setIsFirstTime(Boolean.TRUE);
                            clientRequestFirstTime.setRawSizeperPage(user.getNumberofscriptperpage());
                            clientRequestFirstTime.setPageNo( PAGE_NO );
                            clientRequestFirstTime.setProfileId(Integer.parseInt(profile_id));
                            clientRequestFirstTime.setWantToStart(Boolean.FALSE);
                            clientRequestFirstTime.setWantToStop(Boolean.FALSE);

                            mConnection.sendTextMessage(ApplicationUtils.generateJSONFromObject(clientRequestFirstTime));
                            clientRequestFirstTime = null;
                        }

                    }
                }
                setCommodity();
            }
        } );
    }

    public void selectOption( String script ) {
        final CharSequence options[] = new CharSequence[]{

                AppConstant.STR_MARKET_DEPTH, AppConstant.STR_HIGH_LOW, AppConstant.STR_NEXT_CONTRACT, AppConstant.STR_SET_ALERT, AppConstant.STR_DEFAULT,
                AppConstant.ST_REMOVE_SCRIPT,AppConstant.STR_CLOSE

        };

        scriptOptions.setCancelable( true );
        scriptOptions.setTitle(Html.fromHtml(("<font color='#FFAA00'>" + script + "</font>")));

        scriptOptions.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = null;

                for (int i = 0; i < options.length; i++) {

                    switch ("" + options[which]) {
                        case AppConstant.STR_MARKET_DEPTH:
                            intent = new Intent(MainActivity.this, MarketDepth.class);
                            //intent.putExtra( "User",user );
                            break;
                        case AppConstant.STR_HIGH_LOW:

                            break;
                        case AppConstant.STR_NEXT_CONTRACT:

                            break;
                        case AppConstant.STR_SET_ALERT:
                            break;
                        case AppConstant.STR_DEFAULT:

                            break;
                        case AppConstant.ST_REMOVE_SCRIPT:

                            break;
                        case AppConstant.STR_CLOSE:
                            dialog.dismiss();

                            break;
                    }
                }
                if (intent != null)
                    startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status liste
        ConnectionAppication.getInstance().setConnectivityListener( this );
    }

    @Override
    public void onNetworkConnectionChanged( boolean isConnected ) {
        if ( isConnected == true ) {
            start( profile_id );
        }
    }

    private  void start( final String profile_id ) {

        mConnection = new WebSocketConnection();

        final String wsuri = AppConstant.WS + AppConstant.WSURI  + user.getUserid() ;

        try {
            mConnection.connect( wsuri, new WebSocketHandler() {

                @Override
                public void onOpen() {

                    clientRequest = new ClientRequest();
                    clientRequest.setUserId( user.getUserid() );
                    clientRequest.setIsFirstTime( Boolean.TRUE );
                    clientRequest.setRawSizeperPage( user.getNumberofscriptperpage() );
                    clientRequest.setPageNo( PAGE_NO );
                    clientRequest.setProfileId( Integer.parseInt( profile_id ) );
                    clientRequest.setWantToStart( Boolean.FALSE );
                    clientRequest.setWantToStop(Boolean.FALSE);

                    mConnection.sendTextMessage(ApplicationUtils.generateJSONFromObject( clientRequest ) );
                    clientRequest = null;
                }

                @Override
                public void onTextMessage( String message ) {
                    System.out.println("f time "+message);
                    Object json=null;
                    try {
                        json = new JSONTokener( message ).nextValue();
                    } catch ( JSONException e ) {
                        e.printStackTrace();
                    }
                    if (  json != null &&  json  instanceof JSONObject ){

                        ClientRequest clientRequest = ApplicationUtils.generateObjectFromJSON( message, ClientRequest.class );

                        if ( clientRequest.getIsFirstTime() ){
                            skipUpdate = Boolean.TRUE;

                            ArrayList< MkW >listFirstDataFromSocket = ( ArrayList<MkW> ) ApplicationUtils.generateObjectFromJSONArray( ApplicationUtils.
                                    generateJSONFromObject(clientRequest.getData()), MkW.class );

                            if ( listFirstDataFromSocket !=null ){

                                if( listFirstDataFromSocket.size() > 0 ) {

                                    prepareRateDataForFirsttime(listFirstDataFromSocket);

                                    if ( scriptIndexMap !=null ){

                                        if( scriptIndexMap.size() > 0 ){

                                            ClientRequest clientRequestToStart = new ClientRequest();

                                            clientRequestToStart.setUserId( user.getUserid() );
                                            clientRequestToStart.setIsFirstTime( Boolean.FALSE );
                                            clientRequestToStart.setRawSizeperPage( user.getNumberofscriptperpage() );
                                            clientRequestToStart.setPageNo( PAGE_NO );
                                            clientRequestToStart.setProfileId( Integer.parseInt( profile_id ) );
                                            clientRequestToStart.setWantToStart( Boolean.TRUE );
                                            clientRequestToStart.setWantToStop( Boolean.FALSE );

                                            mConnection.sendTextMessage( ApplicationUtils.generateJSONFromObject( clientRequestToStart ) );
                                            clientRequestToStart =null;
                                        }

                                    }

                                }
                                else{
                                    mProgress.dismiss();
                                }
                                listFirstDataFromSocket = null;
                            }

                        }else if ( clientRequest.getWantToStop() ){

                        }else if ( clientRequest.getWantToStart() ){


                        }

                    }//End of if ( message instance of Clientrequest )

                    else {
                        if ( skipUpdate ) {
                      //      prepareRateData((ArrayList<MkW>) ApplicationUtils.generateObjectFromJSONArray(message, MkW.class));
                        }
                    }
                    recyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    // do whatever
                                    //Toast.makeText(MainActivity.this,"Script="+ rateModelList.get(position).getScriptName(),Toast.LENGTH_LONG).show();

                                    selectOption(rateModelList.get(position).getScriptName());
                                    scriptOptions.show();

                                }

                                @Override
                                public void onLongItemClick(View view, int position) {
                                    // do whatever
                                }

                            }));
                    json = null;
                    message = null;

                    sk.setMax( 20 );
                    sk.setProgress( size_of_items - 5 );
                    sk.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {

                        @Override
                        public void onProgressChanged( SeekBar seekBar, int i, boolean b ) {
                            i = i + 5;
                            size_of_items = i;
                            rateAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onStartTrackingTouch( SeekBar seekBar ) {

                        }

                        @Override
                        public void onStopTrackingTouch( SeekBar seekBar ) {

                            sk.setVisibility( View.INVISIBLE );
                        }
                    } );



                }


                @Override
                public void onClose( int code, String reason ) {

                }

            } );
        } catch ( WebSocketException e ) {
        }


    }

    private void prepareRateDataForFirsttime( ArrayList<MkW> listFd ) {


        int size = listFd.size()-1;

        if ( size > 0 ) {
            for ( int i = 0; i < size; i++ ) {
                MkW mkw = ApplicationUtils.generateObjectFromJSON( ApplicationUtils.generateJSONFromObject( listFd.get( i ) ),
                        MkW.class );
               MkW mk = listFirstData.get(scriptIndexMap.get(mkw.getSn()));
                mk.setD1 ( mkw.getD1() );
                mk.setD2 ( mkw.getD2() );
                mk.setD3 ( mkw.getD3() );
                mk.setD4 ( mkw.getD4() );
                mk.setD5 ( mkw.getD5() );
                mk.setD6 ( mkw.getD6() );
                mk.setN( mkw.getN() );

                rateAdapter.notifyDataSetChanged();
            }

        }

    }

    private void prepareRateData( LinkedList<MkW> listFirstData ) {

        int size = listFirstData.size();
        for ( int i = 0; i < size; i++ ) {

            MkW mkwNew = ApplicationUtils.generateObjectFromJSON( ApplicationUtils.generateJSONFromObject( listFirstData.get( i ) ), MkW.class );

            if ( rateModelList != null ){
                RateModel rateModel = rateModelList.get( scriptIndexMap.get( mkwNew.getSn() ) );

                TextView textView = null;

                if ( rateModel.getScriptName().equalsIgnoreCase( mkwNew.getSn() ) ) {

                    if ( mkwNew.getD1() != null ) {

                        rateModel.setRate1( mkwNew.getD1() );
                        if ( recyclerView.findViewHolderForAdapterPosition( scriptIndexMap.get( mkwNew.getSn() ) ) != null ) {

                            textView = ( TextView ) recyclerView.findViewHolderForAdapterPosition( scriptIndexMap.get( mkwNew.getSn() ) ).itemView.findViewById( R.id.txtRate1 );

                            textColorAnim = ObjectAnimator.ofInt( textView, AppConstant.BACKGROUND_CLOR_BLINK, getColorCode( textView.getText(), mkwNew.getD1() ), Color.TRANSPARENT );

                            textColorAnim.setDuration( blinkDuration );
                            textColorAnim.setEvaluator( argbEvaluator );
                            textColorAnim.setRepeatCount( 0 );
                            textColorAnim.start();
                            textView.setText( mkwNew.getD1() );

                        }
                    }

                    if ( mkwNew.getD2() != null ) {

                        rateModel.setRate2( mkwNew.getD2() );
                        if ( recyclerView.findViewHolderForAdapterPosition( scriptIndexMap.get( mkwNew.getSn() ) ) != null ) {

                            textView = ( TextView ) recyclerView.findViewHolderForAdapterPosition( scriptIndexMap.get( mkwNew.getSn() ) ).itemView.findViewById( R.id.txtRate2 );

                            textColorAnim2 = ObjectAnimator.ofInt( textView, AppConstant.BACKGROUND_CLOR_BLINK, getColorCode( textView.getText(), mkwNew.getD2() ), Color.TRANSPARENT );
                            textColorAnim2.setDuration( blinkDuration );
                            textColorAnim2.setEvaluator( argbEvaluator );
                            textColorAnim2.setRepeatCount( 0 );
                            textColorAnim2.start();
                            textView.setText( mkwNew.getD2() );

                        }
                    }
                    if ( mkwNew.getD3() != null ) {

                        rateModel.setRate3( mkwNew.getD3() );
                        if ( recyclerView.findViewHolderForAdapterPosition( scriptIndexMap.get( mkwNew.getSn() ) ) != null ) {

                            textView = ( TextView ) recyclerView.findViewHolderForAdapterPosition( scriptIndexMap.get( mkwNew.getSn() ) ).itemView.findViewById( R.id.txtRate3 );

                            textColorAnim3 = ObjectAnimator.ofInt( textView, AppConstant.BACKGROUND_CLOR_BLINK, getColorCode( textView.getText(), mkwNew.getD3() ), Color.TRANSPARENT );
                            textColorAnim3.setDuration( blinkDuration );
                            textColorAnim3.setEvaluator( argbEvaluator );
                            textColorAnim3.setRepeatCount( 0 );
                            textColorAnim3.start();

                            textView.setText( mkwNew.getD3() );


                        }
                    }
                    if ( mkwNew.getD4() != null ) {

                        rateModel.setRate4( mkwNew.getD4() );
                        if ( recyclerView.findViewHolderForAdapterPosition( scriptIndexMap.get( mkwNew.getSn() ) ) != null ) {

                            textView = ( TextView ) recyclerView.findViewHolderForAdapterPosition( scriptIndexMap.get( mkwNew.getSn() ) ).itemView.findViewById( R.id.txtRate4 );

                            textColorAnim4 = ObjectAnimator.ofInt( textView, AppConstant.BACKGROUND_CLOR_BLINK, getColorCode( textView.getText(), mkwNew.getD4() ), Color.TRANSPARENT );
                            textColorAnim4.setDuration( blinkDuration );
                            textColorAnim4.setEvaluator( argbEvaluator );
                            textColorAnim4.setRepeatCount( 0 );
                            textColorAnim4.start();
                            textView.setText( mkwNew.getD4() );


                        }
                    }
                    if ( mkwNew.getD5() != null ) {

                        rateModel.setRate5( mkwNew.getD5() );
                        if ( recyclerView.findViewHolderForAdapterPosition( scriptIndexMap.get( mkwNew.getSn() ) ) != null ) {

                            textView = ( TextView ) recyclerView.findViewHolderForAdapterPosition( scriptIndexMap.get( mkwNew.getSn() ) ).itemView.findViewById( R.id.txtRate5 );

                            textColorAnim5 = ObjectAnimator.ofInt( textView, AppConstant.BACKGROUND_CLOR_BLINK, getColorCode( textView.getText(), mkwNew.getD5() ), Color.TRANSPARENT );
                            textColorAnim5.setDuration( blinkDuration );
                            textColorAnim5.setEvaluator( argbEvaluator );
                            textColorAnim5.setRepeatCount( 0 );
                            textColorAnim5.start();

                            textView.setText( mkwNew.getD5() );

                        }
                    }
                    if ( mkwNew.getD6() != null ) {


                        rateModel.setRate6( mkwNew.getD6() );
                        if ( recyclerView.findViewHolderForAdapterPosition( scriptIndexMap.get( mkwNew.getSn() ) ) != null ) {
                            textView = ( TextView ) recyclerView.findViewHolderForAdapterPosition( scriptIndexMap.get( mkwNew.getSn() ) ).itemView.findViewById( R.id.txtRate6 );

                            //textColorAnim6 = ObjectAnimator.ofInt( textView, "backgroundColor", getColorCode( textView.getText(), mkwNew.getD5() ), Color.TRANSPARENT );
                            //textColorAnim6.setDuration( blinkDuration );
                            //textColorAnim6.setEvaluator( new ArgbEvaluator() );
                            //textColorAnim6.setRepeatCount( 0 );
                            //textColorAnim.setRepeatMode( ValueAnimator.REVERSE );
                            //textColorAnim6.start();

                            // Float oldF=Float.parseFloat( "0" );
                                /*Float newF=Float.parseFloat( mkwNew.getD6() );*/
                           /* if ( Float.parseFloat( mkwNew.getD6() ) > 0 ) {
                                //return Color.RED;
                                textView.setText( "+" + mkwNew.getD6() );
                            } else {

                           */
                            textView.setText( mkwNew.getD6() );
                            //}


                        }
                    }
                    rateAdapter.notifyItemChanged(scriptIndexMap.get(mkwNew.getSn()));
                }
                mkwNew = null;
            }


        }
        listFirstData=null;

    }


    private int getColorCode( CharSequence old, String newValue ) {
        Float oldF = Float.parseFloat( old.toString() );
        Float newF = Float.parseFloat( newValue );
        if ( oldF.compareTo( newF ) > 0 ) {
            return Color.RED;
        } else {
            return Color.BLUE;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // mConnection.sendTextMessage( AppConstant.ST_COMMAND );
        mConnection.disconnect();
        //  finish();
    }
}
