package oversecured.ovaa.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

public class IntentUtils {
    private IntentUtils() {
    }

    public static void protectActivityIntent(Context context, Intent intent) {
        for(ResolveInfo info : context.getPackageManager().queryIntentActivities(intent, 0)) {
            //查找系统内容所有的activity，如action="oversecured.ovaa.action.WEBVIEW"
            //设置这个activity相应的classname
            intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
            return;
        }
    }
}
