package com.example.launchanywherebyevilparcel1999;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class AuthenticatorService extends Service {
    public AuthenticatorService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        MyAuthenticator myAuthenticator=new MyAuthenticator(this);
        return myAuthenticator.getIBinder();
    }
}