package com.example.launchanywherebyevilparcel1999;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getBundleExtra("data");//获取bundle对象
        String command = bundle.getString("mismatch");//真正bundle反序列化，解析bundle内容
        if (bundle != null) {
            Parcel parcel = Parcel.obtain();
            bundle.writeToParcel(parcel, 0);

            try {
                byte[] raw = parcel.marshall();//序列化为byte[]
                FileOutputStream fos = null;
                fos  = openFileOutput("first.pcl", Context.MODE_PRIVATE);
                fos.write(raw);
                fos.close();
            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent();
        intent.putExtra("data", bundle);
        intent.setClass(getApplicationContext(),SecondActivity.class);
        startActivity(intent);

        /*
        try {
            Process process = Runtime.getRuntime().exec("id");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            int exitCode = process.waitFor();
            String line;
            while ((line = reader.readLine()) != null) {
                // 处理命令输出
                Log.d(TAG,"Who am i:" + line);
            }
        }
        catch (IOException e) {
            // 处理异常
        }
        catch (InterruptedException e) {
            // 处理异常
        }

         */

    }
}
