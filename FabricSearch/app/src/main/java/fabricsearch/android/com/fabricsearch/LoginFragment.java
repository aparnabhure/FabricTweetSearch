package fabricsearch.android.com.fabricsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by aparna_bhure on 07/02/16.
 */
public class LoginFragment extends Fragment {
    private TwitterLoginButton mLoginButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        mLoginButton = (TwitterLoginButton) rootView.findViewById(R.id.login_button);
        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                MainApp.getAppInstance().appSession  = result.data;
                startUserTimeLine();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getActivity(), "Login failed: " + exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the login button.
        if(mLoginButton != null)
            mLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void startUserTimeLine(){
        UserTimelineFragment timelineFragment = new UserTimelineFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, timelineFragment)
                .commit();
    }
}
