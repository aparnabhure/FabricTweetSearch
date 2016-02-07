package fabricsearch.android.com.fabricsearch;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterSession;

import io.fabric.sdk.android.Fabric;


public class Fabric extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "key";
    private static final String TWITTER_SECRET = "secret_key";

    private final String LOGIN_FRAG_TAG = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_fabric);
        if (savedInstanceState == null) {
            TwitterSession appSession = MainApp.getAppInstance().appSession;
            Fragment fragment = null;

            if(appSession == null || appSession.getAuthToken().isExpired()){
                fragment = new LoginFragment();
            }else{
                fragment = new UserTimelineFragment();
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, LOGIN_FRAG_TAG)
                    .commit();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the fragment, which will then pass the result to the login
        // button.
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(LOGIN_FRAG_TAG);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
