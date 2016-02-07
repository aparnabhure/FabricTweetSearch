package fabricsearch.android.com.fabricsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * Created by aparna_bhure on 06/02/16.
 */
public class UserTimelineFragment extends Fragment {
    private ListView mTimeLineTweetsList = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
        mTimeLineTweetsList = (ListView)rootView.findViewById(R.id.timelinelist);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        TwitterSession appSession = MainApp.getAppInstance().appSession;

        if (appSession != null && appSession.getUserName() != null) {

            final UserTimeline userTimeline = new UserTimeline.Builder()
                    .screenName(appSession.getUserName())
                    .build();
            final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                    .setTimeline(userTimeline)
                    .build();
            mTimeLineTweetsList.setAdapter(adapter);
        }else{
            //Login again
            login();
        }
    }

    private void login(){
        LoginFragment loginFragment = new LoginFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, loginFragment)
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fabric_search, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));
        if(searchView != null){
            searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {}
            });

            searchView.setOnCloseListener(new SearchView.OnCloseListener(){
                @Override
                public boolean onClose() {
                    updateTimeLine();
                    return false;
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query){
                    searchTweetWithHashTag(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText){
                    return true;
                }
            });
        }
    }

    private void updateTimeLine(){
        TwitterSession appSession = MainApp.getAppInstance().appSession;
        if (appSession != null && appSession.getUserName() != null) {
            final UserTimeline userTimeline = new UserTimeline.Builder()
                    .screenName(appSession.getUserName())
                    .build();
            final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                    .setTimeline(userTimeline)
                    .build();
            mTimeLineTweetsList.setAdapter(adapter);
        }
    }

    private void searchTweetWithHashTag(String tag){
        if (tag != null && tag.trim().length() > 0){
            final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query((tag.startsWith("#")?tag:("#"+tag)))
                .build();
            final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                    .setTimeline(searchTimeline)
                    .build();
            mTimeLineTweetsList.setAdapter(adapter);
        }
    }
}
