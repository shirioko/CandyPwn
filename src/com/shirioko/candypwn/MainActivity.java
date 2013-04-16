package com.shirioko.candypwn;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	String crushname = "com.king.candycrushsaga";
	
	static String execSu(String command)
	{
		try
		{
			Process root = Runtime.getRuntime().exec("su");
			DataOutputStream dos = new DataOutputStream(root.getOutputStream());
			dos.writeBytes(command);
			dos.flush();
			dos.close();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(root.getInputStream()));
			char[] buffer = new char[1];
			StringBuffer output = new StringBuffer();
			root.waitFor();
			while(reader.read(buffer) != -1)
			{
				output.append(buffer);
			}
			reader.close();

			return output.toString();
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	boolean CandyCrushInstalled()
	{
		try
		{
			ApplicationInfo info = getPackageManager().getApplicationInfo(this.crushname, 0);
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        if(this.CandyCrushInstalled())
        {
	        ActivityManager servMng = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
	        List<ActivityManager.RunningAppProcessInfo> list = servMng.getRunningAppProcesses();
	        String command = "";
	        if(list != null){
	        	for(int i=0;i<list.size();++i){
	        		ActivityManager.RunningAppProcessInfo item = list.get(i);
	        		if(this.crushname.matches(item.processName)){
	        			command += "kill -9 " + item.pid + ";";
	        		}
	        	}
	        }
	        execSu(command + "rm -f /data/data/com.king.candycrushsaga/app_storage/save*.dat");
	
	        //start candy crush saga
	        Intent intent = getPackageManager().getLaunchIntentForPackage(this.crushname);
	        if(intent != null)
	        {
	        	startActivity(intent);
	        }
        }
        else
        {
        	Toast.makeText(getApplicationContext(), "Candy Crush Saga not installed", Toast.LENGTH_LONG).show();
        	try
        	{
        		//open play store
        		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + this.crushname)));
        	} catch(Exception ex)
        	{}
        }
        
        finish();
        System.exit(0);
    }
}
