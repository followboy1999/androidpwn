package com.example.launchanywherebyevilparcel1999;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getBundleExtra("data");
        String command = bundle.getString("mismatch");//真正bundle反序列化

        if (bundle != null) {
            Parcel parcel = Parcel.obtain();
            bundle.writeToParcel(parcel, 0);

            try {
                byte[] raw = parcel.marshall();//序列化为byte[]
                FileOutputStream fos = null;
                fos  = openFileOutput("second.pcl", Context.MODE_PRIVATE);
                fos.write(raw);
                fos.close();
            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
