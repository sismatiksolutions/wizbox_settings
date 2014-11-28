package com.wizbox.settings;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;









import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements OnClickListener {
	TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9;

	LinearLayout ll;
	ImageView iv;
	EditText et1, et2;
	StringEntity se;
	SharedPreferences sp,response_sp;
	String json = "";
	String macAddress;
	String passWord,Appname="";
	Intent in;
	ListView lv;
	int appno=0;
	Button btn_activate;
	Intent intent;
	Context mContext=MainActivity.this;
	SharedPreferences appPreferences;
	boolean isAppInstalled = false;
	boolean isSuccess = false;
	TextView activation;
	
//	HttpClient httpclient;HttpPost httpPost; HttpResponse httpResponse;
 int listid;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;

	private ProgressDialog mProgressDialog;

	String[] values = new String[] {

	"Online Picture", "Picasa", "Local Photos App", "Pixlr App", "Flicker App",
			"Indian Movies", "International Movies", "International TV",
			"Movietube", "Filmon", "Local Videos App", "Big Flix App",
			"Box Tv App", "Spuul App", "Youtube Tv App", "Local Music App",
			"English Songs App", "Hindi Songs App", "Radio App",
			"Apps Organizer", "Mx Player App", "My Media App",
			"Network Test App","TeamViewer","WizBox","WizBox Setup","Apps Installer","Advertise App" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(android.os.Build.VERSION.SDK_INT>9 ){
			
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		response_sp = PreferenceManager.getDefaultSharedPreferences(this);
		isSuccess = response_sp.getBoolean("isSuccess",false);
		
		
		
		
		
		appPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		isAppInstalled = appPreferences.getBoolean("isAppInstalled",false);
		if(isAppInstalled==false){

		           // add shortcutIcon code here
			 addShortcutIcon(); 
		}
		// finally isAppInstalled should be true.
		SharedPreferences.Editor editor1 = appPreferences.edit();
		editor1.putBoolean("isAppInstalled", true);
		editor1.commit();
		
		
       
		 //httpclient = new DefaultHttpClient();
			
		 // 2. make POST request to the given URL
		//httpPost = new HttpPost("http://nebula.wizbox.in/mobileapi/address.php");
		mProgressDialog = new ProgressDialog(MainActivity.this);
		tv1 = (TextView) findViewById(R.id.network);
		tv2 = (TextView) findViewById(R.id.disp);
		tv3 = (TextView) findViewById(R.id.apps);
		tv4 = (TextView) findViewById(R.id.wacc);
		tv5 = (TextView) findViewById(R.id.gacc);
		tv6 = (TextView) findViewById(R.id.net_test);
		tv7 = (TextView) findViewById(R.id.vid);
		tv8 = (TextView) findViewById(R.id.ss);
		tv9 = (TextView) findViewById(R.id.remote_access);
		
		ll = (LinearLayout) findViewById(R.id.myll);
		in = new Intent(android.content.Intent.ACTION_VIEW);
		iv = (ImageView) findViewById(R.id.image_network);

		ll.removeAllViews();
		ll.setVisibility(View.VISIBLE);

		LayoutInflater inflatermain = getLayoutInflater();
		View mainview = inflatermain.inflate(R.layout.hometext, null);
		ll.addView(mainview);

		boolean conStatus = false;
		ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nf = cn.getActiveNetworkInfo();
		if (nf != null && nf.isConnected() == true) {
			iv.setImageResource(R.drawable.ok);
			// Toast.makeText(this, "Network Available",
			// Toast.LENGTH_LONG).show();

			conStatus = true;
		} else {
			iv.setImageResource(R.drawable.no);
			// Toast.makeText(this, "Network not Available",
			// Toast.LENGTH_LONG).show();
		}   

		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		tv4.setOnClickListener(this);
		tv5.setOnClickListener(this);
		tv6.setOnClickListener(this);
		tv7.setOnClickListener(this);
		tv8.setOnClickListener(this);
		tv9.setOnClickListener(this);
		

		

	}

	

	
	private void addShortcutIcon() {
		// TODO Auto-generated method stub
	
		  Intent shortcutIntent = new Intent(getApplicationContext(),
	                MainActivity.class);
	        
	        shortcutIntent.setAction(Intent.ACTION_MAIN);
	        //shortcutIntent is added with addIntent
	        Intent addIntent = new Intent();
	        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
	        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "WizBox Setup");
	        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
	            Intent.ShortcutIconResource.fromContext(getApplicationContext(),
	                        R.drawable.ic_launcher));

	        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
	        // finally broadcast the new Intent
	        getApplicationContext().sendBroadcast(addIntent);
	    
		
	}



	@SuppressWarnings("unused")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.network: // Choosing Connection Wifi or Ethernet
			
			listid=1;
			resetcolor(); // Reseting Color Of All the Button
			tv1.setTextColor(Color.parseColor("#3BB9FF"));

			ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);

			LayoutInflater inflatermain = getLayoutInflater();
			View mainview = inflatermain.inflate(R.layout.hometext, null);
			ll.addView(mainview);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					MainActivity.this);

			// Set title
			alertDialogBuilder.setTitle("Set Your Network Setting");

			// Set dialog message
			alertDialogBuilder
					.setMessage("Choose one Configuration")
					.setCancelable(true)
					.setPositiveButton("Wifi",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, close
									// current activity
									// MainActivity.this.finish();
									Intent wi = new Intent(Intent.ACTION_VIEW);
									wi.addCategory(Intent.CATEGORY_LAUNCHER);
									wi.setClassName("com.android.settings",
											"com.android.settings.wifi.WifiSettings");
									wi.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
									try {
										startActivity(wi);
									} catch (ActivityNotFoundException e) {

										// ShowAlert("Music");
									}
								}
							})
					.setNegativeButton("Ethernet",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									Intent et = new Intent(Intent.ACTION_VIEW);
									et.addCategory(Intent.CATEGORY_LAUNCHER);
									et.setClassName("com.android.settings",
											"com.android.settings.Settings");
									et.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
									try {
										startActivity(et);
									} catch (ActivityNotFoundException e) {

										// ShowAlert("Music");
									}
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
			break;

		case R.id.disp: // Setting Display Setting
			listid=2;
			resetcolor();
			tv2.setTextColor(Color.parseColor("#3BB9FF"));
			ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);

			LayoutInflater inflatermain1 = getLayoutInflater();
			View mainview1 = inflatermain1.inflate(R.layout.hometext, null);
			ll.addView(mainview1);
			Intent dis = new Intent(Intent.ACTION_VIEW);
			dis.addCategory(Intent.CATEGORY_LAUNCHER);
			dis.setClassName("com.android.settings",
					"com.android.settings.ScreenSettings");
			dis.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			try {
				startActivity(dis);
			} catch (ActivityNotFoundException e) {

				// ShowAlert("Music");
			}
			break;

		case R.id.apps: // Getting List Of Required Applicaton
			listid=3;
			resetcolor();
			tv3.setTextColor(Color.parseColor("#3BB9FF"));
			
			if(isSuccess==false){
				Account();
				try{
				startActivity(new Intent(MainActivity.this,Customdialog.class));

			}catch(Exception e){
				Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
			}
			}
			
			else {
			
			// ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);
			ll.removeAllViews();

			LayoutInflater inflater1 = getLayoutInflater();
			View myview2 = inflater1.inflate(R.layout.appinflate, null);
			ll.addView(myview2);
			lv = (ListView) myview2.findViewById(R.id.list);

			MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this,
					values);
			lv.setAdapter(adapter);
			
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View v,
						int position, long arg3) {
					// TODO Auto-generated method stub
					

					Intent i = new Intent(android.content.Intent.ACTION_VIEW);

					boolean installed = false;

					switch (position) {
					case 0:
						
						appno=1;
						
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.wizbox.onlinepictures");// Checking
																					// online
																					// picture
																					// is
																					// installed
																					// or
																					// not

						if (!installed) {
							
			                      
			                       String link="http://appstore.wizbox.in/installer/OnlinePictures.apk";
									 startDownload(link);
			                    
			                   
							
						} else {
							Toast.makeText(getApplicationContext(),
									"Online Picture App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					case 1:
						appno=2;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("larry.zou.colorfullife");// Checking
																					// Picasa
																					// is
																					// installed
																					// or
																					// not

						if (!installed)
						{
							
		                      
		                	   String link="http://appstore.wizbox.in/installer/PicasaTool.apk";
								 startDownload(link);
		                     
		                   
							
								
						} else {
							Toast.makeText(getApplicationContext(),
									"Picasa App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					

					case 2:
						appno=3;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.fstop.photo");// Checking
																			// fstop
																			// is
																			// installed
																			// or
																			// not

						if (!installed) {
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/F-Stop.apk";
									 startDownload(link);
			                      
						} else {
							Toast.makeText(getApplicationContext(),
									"Local Photos App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;

					case 3:
						appno=4;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.pixlr.express");// Checking
																			// pixlr
																			// is
																			// installed
																			// or
																			// not

						if (!installed) {
							
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/PixlrExpress.apk";
									 startDownload(link);
			                     
						} else {
							Toast.makeText(getApplicationContext(),
									"Pixlr App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;

					case 4:
						appno=5;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.yahoo.mobile.client.android.flickr");// Checking
																									// flicker
																									// is
																									// installed
																									// or
																									// not

						if (!installed) {
							 
									String link="http://appstore.wizbox.in/installer/Flickr.apk";
									 startDownload(link);

						} else {
							Toast.makeText(getApplicationContext(),
									"Flicker App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;

					
					case 5:
						appno=6;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.wizbox.indianmovies");// Checking
																					// indian
																					// movies
																					// is
																					// installed
																					// or
																					// not

						if (!installed) {
							
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/IndianMovies.apk";
									 startDownload(link);

			                     
						} else {
							Toast.makeText(getApplicationContext(),
									"Indian Movies App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					case 6:
						appno=7;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.wizbox.internationalmovies");// Checking
																							// online
																							// picture
																							// is
																							// installed
																							// or
																							// not

						if (!installed) {
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/InternationalMovies.apk";
									 startDownload(link);

			                     
						} else {
							Toast.makeText(
									getApplicationContext(),
									"International Movies App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					case 7:
						appno=8;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.wizbox.internationaltv");// Checking
																						// online
																						// picture
																						// is
																						// installed
																						// or
																						// not

						if (!installed) {
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/InternationalTv.apk";
									 startDownload(link);

			                     
						} else {
							Toast.makeText(getApplicationContext(),
									"Internationaltv App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					case 8:
						appno=9;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("topandroidapp.movietube");// Checking
																					// online
																					// picture
																					// is
																					// installed
																					// or
																					// not

						if (!installed) {
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/MovieTube.apk";
									 startDownload(link);

			                      
						} else {
							Toast.makeText(getApplicationContext(),
									"Movietube App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					case 9:
						appno=10;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.filmon.android.aff21136Yf");// Checking
																						// online
																						// picture
																						// is
																						// installed
																						// or
																						// not

						if (!installed) {
							
			                	   String link="http://appstore.wizbox.in/installer/WorldLiveTVChannels.apk";
									 startDownload(link);

			                      
						} else {
							Toast.makeText(getApplicationContext(),
									"Filmon App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					
					case 10:
						appno=11;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.androidcodemonkey.videos.free");// Checking
																							// androidcodemonkey
																							// video
																							// player
																							// is
																							// installed
																							// or
																							// not

						if (!installed) {
                         
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/Videos.apk";
									 startDownload(link);
			                      
						} else {
							Toast.makeText(getApplicationContext(),
									"Local Videos App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					case 11: 
						appno=12;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.bigflix.bigflixtablet");// Checking
																					// bigflix
																					// is
																					// installed
																					// or
																					// not

						if (!installed) {
                          
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/BIGFLIXforTablet.apk";
									 startDownload(link);
			                      
						} else {
							Toast.makeText(getApplicationContext(),
									"Big Flix App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;

					case 12:
						appno=13;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.til.boxtv.mobile");// Checking
																				// boxtv
																				// is
																				// installed
																				// or
																				// not

						if (!installed) {
							
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/BoxTV.apk";
									 startDownload(link);
			                     
						} else {
							Toast.makeText(getApplicationContext(),
									"Box Tv App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;

					case 13:
						appno=14;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.spuul.android");// Checking
																			// spuul
																			// is
																			// installed
																			// or
																			// not

						if (!installed) {
						
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/Spuul.apk";
									 startDownload(link);
			                      
						} else {
							Toast.makeText(getApplicationContext(),
									"Spuul App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;

					case 14:
						appno=15;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.google.android.youtube.googletv");// Checking
																								// Youtube
																								// Tv
																								// is
																								// installed
																								// or
																								// not

						if (!installed) {
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/YouTube.apk";
									 startDownload(link);
			                      
						} else {
							Toast.makeText(getApplicationContext(),
									"Youtube Tv App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;

					case 15:
						appno=16;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.google.android.music");// Checking
																					// Local
																					// Music
																					// is
																					// installed
																					// or
																					// not

						if (!installed) {
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/GooglePlayMusic.apk";
									 startDownload(link);
			                     
						} else {
							Toast.makeText(getApplicationContext(),
									"Local Music App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;

					case 16:
						appno=17;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.musicnetwork.vivepop");// Checking
																					// English
																					// Songs
																					// is
																					// installed
																					// or
																					// not

						if (!installed) {
							
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/MusicaPop.apk";
									 startDownload(link);
			                      
						} else {
							Toast.makeText(getApplicationContext(),
									"English Songs App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;

					case 17:
						appno=18;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.hungama.myplay.activity");// Checking
																						// Hindi
																						// Songs
																						// is
																						// installed
																						// or
																						// not

						if (!installed) {
							
							
			                	   String link="http://appstore.wizbox.in/installer/Hungama.apk";
									 startDownload(link);
			                      
						} else {
							Toast.makeText(getApplicationContext(),
									"Hindi Songs App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;

					case 18:
						appno=19;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.radio.fmradio");// Checking
																			// Radio
																			// app
																			// is
																			// installed
																			// or
																			// not

						if (!installed) {
							
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/RadioFM.apk";
									 startDownload(link);
			                      
						} else {
							Toast.makeText(getApplicationContext(),
									"Radio App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					case 19:
						appno=20;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.wixbox.appsorganizer");// Checking
																					// online
																					// picture
																					// is
																					// installed
																					// or
																					// not

						if (!installed) {
							
							
			                	   String link="http://appstore.wizbox.in/installer/WixBoxAppsOrganizer.apk";
									 startDownload(link);
			                      
						} else {
							Toast.makeText(getApplicationContext(),
									"Apps Organizer is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					case 20:
						appno=21;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.mxtech.videoplayer.ad");// Checking
																					// MX
																					// player
																					// is
																					// installed
																					// or
																					// not

						if (!installed) {

							
			                      
			                	   String link="http://appstore.wizbox.in/installer/MXPlayer.apk";
									 startDownload(link);
			                      
						} else {
							Toast.makeText(getApplicationContext(),
									"Mx Player App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					case 21:
						appno=22;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.softmedia.airshare");// Checking
																					// mymedia
																					// is
																					// installed
																					// or
																					// not

						if (!installed) {
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/AirShare.apk";
									 startDownload(link);
			                      
						} else {
							Toast.makeText(getApplicationContext(),
									"My Media App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					case 22:
						appno=23;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("org.zwanoo.android.speedtest");// Checking
																						// Radio
																						// app
																						// is
																						// installed
																						// or
																						// not

						if (!installed) {
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/Speedtest.apk";
									 startDownload(link);
			                     
						} else {
							Toast.makeText(getApplicationContext(),
									"Network Test App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					case 23:
						appno=24;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.teamviewer.quicksupport.market");// Checking
																						// TeamViewer
																						// app
																						// is
																						// installed
																						// or
																						// not

						if (!installed) {
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/TeamViewer.apk";
									 startDownload(link);
			                     
						} else {
							Toast.makeText(getApplicationContext(),
									"Team Viewer App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					case 24:
						appno=25;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("org.xbmc.xbmc");// Checking
																						// Wizbox
																						// app
																						// is
																						// installed
																						// or
																						// not

						if (!installed) {
							
			                      
			                	   String link="http://appstore.wizbox.in/installer/WizBox.apk";
									 startDownload(link);
			                     
						} else {
							Toast.makeText(getApplicationContext(),
									"WizBox App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					
					case 25:
						appno=26;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						
			                      
			                	 String link="http://appstore.wizbox.in/installer/wizboxsetup.apk";
									 startDownload(link);
			                     
						
						break;
					case 26:
						appno=27;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled( "com.apkinstaller.ApkInstaller");// Checking
																						// Wizbox
																						// app
																						// is
																						// installed
																						// or
																						// not

						if (!installed) {
							
			                      
			                	   String link1="http://appstore.wizbox.in/installer/APKInstaller.apk";
									 startDownload(link1);
			                     
						} else {
							Toast.makeText(getApplicationContext(),
									"WizBox App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					case 27:
						appno=28;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled(  "com.example.tablelayoutwithiandv_new");// Checking
																						// Wizbox
																						// app
																						// is
																						// installed
																						// or
																						// not

						if (!installed) {
							
			                      
			                	   String link1="http://appstore.wizbox.in/installer/Advertise.apk";
									 startDownload(link1);
			                     
						} else {
							Toast.makeText(getApplicationContext(),
									"Advertise App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					}
				}

				private void startDownload(String myurl) {
					
        String url = myurl;
        new DownloadFileAsync().execute(url);
    }
	

				private boolean isPackageInstalled(String packagename) {
					PackageManager pm = MainActivity.this.getPackageManager();
					try {
						pm.getPackageInfo(packagename,
								PackageManager.GET_ACTIVITIES);
						return true;
					} catch (NameNotFoundException e) {
						return false;
					}
				}

			});
			}
			break;

		case R.id.wacc: // Wizbox Account
			Account();
				
break;
 

			case R.id.gacc: // Google Account
			listid=5;
			resetcolor();
			tv5.setTextColor(Color.parseColor("#3BB9FF"));
			ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);

			LayoutInflater inflatermain2 = getLayoutInflater();
			View mainview2 = inflatermain2.inflate(R.layout.hometext, null);
			ll.addView(mainview2);
			Intent ga1 = new Intent(Intent.ACTION_VIEW);
			ga1.addCategory(Intent.CATEGORY_LAUNCHER);
			ga1.setClassName("com.android.settings",
					"com.android.settings.accounts.AddAccountSettings");
			ga1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			try {
				startActivity(ga1);
			} catch (ActivityNotFoundException e) {

				// ShowAlert("Music");
			}
			break;

		case R.id.net_test: // Checking internet Speed Using Ookla App
			listid=6;
			resetcolor();
			tv6.setTextColor(Color.parseColor("#3BB9FF"));
			ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);

			LayoutInflater inflatermain3 = getLayoutInflater();
			View mainview3 = inflatermain3.inflate(R.layout.hometext, null);
			ll.addView(mainview3);
			Intent nt;
			PackageManager manager = getPackageManager();
			try {
				nt = manager
						.getLaunchIntentForPackage("org.zwanoo.android.speedtest");
				if (nt == null)
					throw new PackageManager.NameNotFoundException();
				nt.addCategory(Intent.CATEGORY_LAUNCHER);
				startActivity(nt);
			} catch (Exception e) {

				// ShowAlert("Picture");
			}

			break;

		case R.id.vid: // Testing Video Playing
			listid=7;
			resetcolor();
			tv7.setTextColor(Color.parseColor("#3BB9FF"));
			ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);

			LayoutInflater inflatermain4 = getLayoutInflater();
			View mainview4 = inflatermain4.inflate(R.layout.hometext, null);
			ll.addView(mainview4);
			Intent i = new Intent(MainActivity.this, Videoplay.class);
			startActivity(i);

			break;

		case R.id.ss: // Calling System Setting
			listid=8;
			resetcolor();
			tv8.setTextColor(Color.parseColor("#3BB9FF"));
			ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);

			LayoutInflater inflatermain5 = getLayoutInflater();
			View mainview5 = inflatermain5.inflate(R.layout.hometext, null);
			ll.addView(mainview5);
			Intent ss = new Intent(Intent.ACTION_VIEW);
			ss.addCategory(Intent.CATEGORY_LAUNCHER);
			ss.setClassName("com.android.settings",
					"com.android.settings.Settings");
			ss.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			try {
				startActivity(ss);
			} catch (ActivityNotFoundException e) {

				// ShowAlert("Music");
			}

			break;
			
		case R.id.remote_access: //  Calling Remote Access
			listid=8;
			resetcolor();
			tv9.setTextColor(Color.parseColor("#3BB9FF"));
			ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);

			LayoutInflater inflatermain6 = getLayoutInflater();
			View mainview6 = inflatermain6.inflate(R.layout.hometext, null);
			ll.addView(mainview6);
			Intent ra;
			PackageManager manager1 = getPackageManager();
			try {
				ra = manager1
						.getLaunchIntentForPackage("com.teamviewer.quicksupport.market");
				if (ra == null)
					throw new PackageManager.NameNotFoundException();
				ra.addCategory(Intent.CATEGORY_LAUNCHER);
				startActivity(ra);
			} catch (Exception e) {

				// ShowAlert("Picture");
			}

			break;
		
		}

	}

	void Account() {
		// TODO Auto-generated method stub
		listid=4;
		resetcolor();
		tv4.setTextColor(Color.parseColor("#3BB9FF"));
		ll.setVisibility(View.VISIBLE);
		ll.removeAllViews();

		LayoutInflater inflater = getLayoutInflater();
		View myview = inflater.inflate(R.layout.accountinflate, null);
		ll.addView(myview);

		

		btn_activate = (Button) myview.findViewById(R.id.btn_send);
		et1 = (EditText) myview.findViewById(R.id.et_username);
		et2 = (EditText) myview.findViewById(R.id.et_password);
		activation = (TextView) myview.findViewById(R.id.tv_result);
		WifiManager wifimanager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo winfo = wifimanager.getConnectionInfo();
		macAddress = winfo.getMacAddress();

		

		if (macAddress == null) {

			Toast.makeText(getApplicationContext(), "wi-fi is disabled",
					Toast.LENGTH_LONG).show();
		} else {
			et1.setText(macAddress);
			et1.setEnabled(false);
		}

		sp = getSharedPreferences("mypref", 0);

		et1.setText(sp.getString("macid", macAddress));
		
		
        btn_activate.setOnClickListener( new OnClickListener() {

@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	
	passWord = et2.getText().toString();
	InputStream is = null;
	String result = "";
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

	try {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://nebula.wizbox.in/mobileapi/address.php");
		nameValuePairs.add(new BasicNameValuePair("mac",
				macAddress));
		nameValuePairs.add(new BasicNameValuePair("password",
				passWord));
		httppost.setEntity(new UrlEncodedFormEntity(
				nameValuePairs));
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

		is = entity.getContent();
	}

	catch (Exception e) {
		Log.e("log_tag",
				"Error in http connection " + e.toString());
	}
	
	// convert response to string
      try 
      {
           BufferedReader reader = new BufferedReader(new InputStreamReader( is, "iso-8859-1"), 8);
           StringBuilder sb = new StringBuilder();
           String line = null;

       while ((line = reader.readLine()) != null) 
       {
           sb.append(line + "\n");
       }

        is.close();
        result = sb.toString();
        
        String response = getResponse(result);
        String result1 = getFileName(result);
     
     String zero ="0";
     String one ="1";
      try{
    	  if(result1.equalsIgnoreCase(one)){
        	  
    		  activation.setText(response);
        	  activation.setTextColor(Color.GREEN);
    		  //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
    		  SharedPreferences.Editor editor2 = response_sp.edit();
    			editor2.putBoolean("isSuccess", true);
    			editor2.commit();
        	  
          }
      else if(result1.equalsIgnoreCase(zero)){
    	  if(isSuccess==false){
    		  activation.setText(response);
    		  activation.setTextColor(Color.RED);
    		  //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
    	  }
    	  else{
    		  activation.setText("Wrong Password.But You Are Already Register");
    		  activation.setTextColor(Color.YELLOW);
    		  //Toast.makeText(getApplicationContext(), "Wrong Password.But You Are Already Register", Toast.LENGTH_LONG).show();
    	  }
      }
      }catch(Exception e){
    	  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
      }
      

  } 
  catch (Exception e)
  {
	  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//   Log.v("log", "Error converting result " + e.toString());
  }


    return;

}


	public String getResponse(String newresult) {
		String name1=null;
	       int start,end;
	       start=newresult.lastIndexOf(':');
	       end=newresult.length();;     //lastIndexOf('.');
	       name1=newresult.substring((start+1),end);
	     //  name = "images/"+name;
	       //System.out.println("Start:"+start+"\t\tEnd:"+end+"\t\tName:"+name);
	       return name1;
}


	public String getFileName(String wholePath)
	   {
	       String name2=null;
	       int start,end;
	       start=wholePath.lastIndexOf('/');
	       end=wholePath.lastIndexOf(':');;     //lastIndexOf('.');
	       name2=wholePath.substring((start+1),end);
	     //  name = "images/"+name;
	       //System.out.println("Start:"+start+"\t\tEnd:"+end+"\t\tName:"+name);
	       return name2;
	
}
});
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		boolean conStatus = false;
		ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nf = cn.getActiveNetworkInfo();
		if (nf != null && nf.isConnected() == true) {
			iv.setImageResource(R.drawable.ok);
			// Toast.makeText(this, "Network Available",
			// Toast.LENGTH_LONG).show();

			conStatus = true;
		} else {
			iv.setImageResource(R.drawable.no);
			// Toast.makeText(this, "Network not Available",
			// Toast.LENGTH_LONG).show();
		}
		if(listid==3){
		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);
		lv.setAdapter(adapter);}
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("mypref", 0);

		SharedPreferences.Editor editor = sp.edit();

		editor.putString("macid", macAddress);
		editor.commit();
		
		
		super.onStop();

	}

	private void resetcolor() {
		// TODO Auto-generated method stub
		tv1.setTextColor(Color.parseColor("#FFFFFF"));
		tv2.setTextColor(Color.parseColor("#FFFFFF"));
		tv3.setTextColor(Color.parseColor("#FFFFFF"));
		tv4.setTextColor(Color.parseColor("#FFFFFF"));
		tv5.setTextColor(Color.parseColor("#FFFFFF"));
		tv6.setTextColor(Color.parseColor("#FFFFFF"));
		tv7.setTextColor(Color.parseColor("#FFFFFF"));
		tv8.setTextColor(Color.parseColor("#FFFFFF"));
		tv9.setTextColor(Color.parseColor("#FFFFFF"));
	}

	
	@Override
	protected Dialog onCreateDialog(int id) {
        switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:
			
			//mProgressDialog = new ProgressDialog(MainActivity.this);
			//mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			
			return mProgressDialog;
		default:
			return null;
        }
    }

class DownloadFileAsync extends AsyncTask<String, String, String> {
   
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showDialog(DIALOG_DOWNLOAD_PROGRESS);
	}

	@Override
	protected String doInBackground(String... aurl) {
		int count;
		
	try {

	URL url = new URL(aurl[0]);
	URLConnection conexion = url.openConnection();
	conexion.connect();

	int lenghtOfFile = conexion.getContentLength();
	Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
	
	File wizboxapps = new File("/sdcard/wizboxapps/");
	// have the object build the directory structure, if needed.
	wizboxapps.mkdirs();
	
	if(appno==1){
		Appname="OnlinePictures.apk";
	}
	else if(appno==2){
		Appname="PicasaTool.apk";
		
	}
	else if(appno==3){
		Appname="F-Stop.apk";
		
	}
	else if(appno==4){
		Appname="PixlrExpress.apk";
		
	}
	else if(appno==5){
		Appname="Flickr.apk";
		
	}
	else if(appno==6){
		Appname="IndianMovies.apk";
	}
	else if(appno==7){
		Appname="InternationalMovies.apk";
	}
	else if(appno==8){
		Appname="InternationalTv.apk";
	}
	else if(appno==9){
		Appname="MovieTube.apk";
	}
	else if(appno==10){
		Appname="WorldLiveTVChannels.apk";
	}
	else if(appno==11){
		Appname="Videos.apk";
	}
	else if(appno==12){
		Appname="BIGFLIXforTablet.apk";
	}
	else if(appno==13){
		Appname="BoxTV.apk";
	}
	else if(appno==14){
		Appname="Spuul.apk";
	}
	else if(appno==15){
		Appname="YouTube.apk";
	}
	else if(appno==16){
		Appname="GooglePlayMusic.apk";
	}
	else if(appno==17){
		Appname="MusicaPop.apk";
	}
	else if(appno==18){
		Appname="Hungama.apk";
	}
	else if(appno==19){
		Appname="RadioFM.apk";
	}
	else if(appno==20){
		Appname="WixBoxAppsOrganizer.apk";
	}
	else if(appno==21){
		Appname="MXPlayer.apk";
	}
	else if(appno==22){
		Appname="AirShare.apk";
	}
	else if(appno==23){
		Appname="Speedtest.apk";
	}
	else if(appno==24){
		Appname="TeamViewer.apk";
	}
	
	else if(appno==25){
		Appname="WizBox.apk";
	}
	else if(appno==26){
		Appname="wizboxsetup.apk";
	}
	else if(appno==27){
		Appname="APKInstaller.apk";
	}
	else if(appno==28){
		Appname="Advertise.apk";
	}
	InputStream input = new BufferedInputStream(url.openStream());
	OutputStream output = new FileOutputStream("/sdcard/wizboxapps/"+Appname);

	byte data[] = new byte[1024];

	long total = 0;

		while ((count = input.read(data)) != -1) {
			total += count;
			publishProgress(""+(int)((total*100)/lenghtOfFile));
			output.write(data, 0, count);
		}

		output.flush();
		output.close();
		input.close();
	} catch (Exception e) {}
	return null;

	}
	protected void onProgressUpdate(String... progress) {
		 Log.d("ANDRO_ASYNC",progress[0]);
		 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	}

	@Override
	protected void onPostExecute(String unused) {
		if(appno==1){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/OnlinePictures.apk")), "application/vnd.android.package-archive");
	}
		else if(appno==2){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/PicasaTool.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==3){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/F-Stop.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==4){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/PixlrExpress.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==5){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/Flickr.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==6){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/IndianMovies.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==7){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/InternationalMovies.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==8){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/InternationalTv.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==9){
		
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/MovieTube.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==10){
			
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/WorldLiveTVChannels.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==11){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/Videos.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==12){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/BIGFLIXforTablet.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==13){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/BoxTV.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==14){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/Spuul.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==15){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/YouTube.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==16){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/GooglePlayMusic.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==17){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/MusicaPop.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==18){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/Hungama.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==19){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/RadioFM.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==20){
		
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/WixBoxAppsOrganizer.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==21){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/MXPlayer.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==22){
			
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/AirShare.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==23){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/Speedtest.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==24){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/TeamViewer.apk")), "application/vnd.android.package-archive");
		}
		
		else if(appno==25){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/WizBox.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==26){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/wizboxsetup.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==27){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/APKInstaller.apk")), "application/vnd.android.package-archive");
		}
		else if(appno==28){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/Advertise.apk")), "application/vnd.android.package-archive");
		}
		dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
		
		
		startActivity(in);
	}
}


}
