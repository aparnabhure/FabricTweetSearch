package fabricsearch.android.com.fabricsearch;

import android.app.Application;
import android.content.Context;

import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by aparna_bhure on 06/02/16.
 */
public class MainApp extends Application {
    private static Context mContext;
    private static MainApp mainApp;
    public TwitterSession appSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mainApp = this;
    }

    public static Context getContext(){
        return mContext;
    }

    public static MainApp getAppInstance(){
        return mainApp;
    }
}
