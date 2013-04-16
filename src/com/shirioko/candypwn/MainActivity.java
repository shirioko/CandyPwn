package com.shirioko.candypwn;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        ActivityManager servMng = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = servMng.getRunningAppProcesses();
        String command = "";
        if(list != null){
        	for(int i=0;i<list.size();++i){
        		ActivityManager.RunningAppProcessInfo item = list.get(i);
        		if("com.king.candycrushsaga".matches(item.processName)){
        			command += "kill -9 " + item.pid + ";";
        		}
        	}
        }
        execSu(command + "rm -f /data/data/com.king.candycrushsaga/app_storage/save*.dat");

        finish();
        System.exit(0);
    }
}
