package com.wizbox.settings;



import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
  private final Context context;
  private final String[] values;
  private final String pkgName[]={  "com.wizbox.onlinepictures",
		                            "larry.zou.colorfullife",
		                            "com.fstop.photo",
		                            "com.pixlr.express",
		                            "com.yahoo.mobile.client.android.flickr",
		                            "com.wizbox.indianmovies",
		                            "com.wizbox.internationalmovies",
		                            "com.wizbox.internationaltv",
		                            "topandroidapp.movietube",
		                            "com.filmon.android.aff21136Yf",
		                            "com.androidcodemonkey.videos.free",
		                            "com.bigflix.bigflixtablet",
		                            "com.til.boxtv.mobile",
		                            "com.spuul.android",
		                            "com.google.android.youtube.googletv",
		                            "com.google.android.music",
		                            "com.musicnetwork.vivepop",
		                            "com.hungama.myplay.activity",
		                            "com.radio.fmradio",
		                            "com.wixbox.appsorganizer",
		                            "com.mxtech.videoplayer.ad",
		                            "com.softmedia.airshare",
		                            "org.zwanoo.android.speedtest",
		                            "com.teamviewer.quicksupport.market",
		                            "org.xbmc.xbmc",
		                            "com.wizbox.settings",
		                            "com.apkinstaller.ApkInstaller",
		                            "com.example.tablelayoutwithiandv_new"
                                  };
  
//  private final String pkgName[]={
//          "com.sismatik.wizboxhoteldsps",
//          "com.filmon.android.aff21136Yf",
//          "air.fyzb3",
//          "org.zwanoo.android.speedtest"
//        };

  public MySimpleArrayAdapter(Context context, String[] values) {
    super(context, R.layout.row_layout, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.row_layout, parent, false);
    TextView textView = (TextView) rowView.findViewById(R.id.label);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
 //  CheckBox cb = (CheckBox) rowView.findViewById(R.id.checkBox1);
    textView.setText(values[position]);
     
    
	
    // Change the icon for Windows and iPhone
   /* String s = values[position];
    if (s.startsWith("Windows7") || s.startsWith("iPhone")
        || s.startsWith("Solaris")) {
      imageView.setImageResource(R.drawable.no);
    } else {
      imageView.setImageResource(R.drawable.ok);
    }
*/
    boolean installed=isPackageInstalled(pkgName[position],context);
    if(installed) {
    	imageView.setImageResource(R.drawable.ok);
    }
    else {
    	imageView.setImageResource(R.drawable.no);
    }
    
    return rowView;
  }
  
  private boolean isPackageInstalled(String packagename, Context context) {
	    PackageManager pm = context.getPackageManager();
	    try {
	        pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
	        return true;
	    } catch (NameNotFoundException e) {
	        return false;
	    }
	}
} 
