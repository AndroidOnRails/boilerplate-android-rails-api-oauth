package uk.co.glapworth.boilerplate.ui;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import timber.log.Timber;
import uk.co.glapworth.boilerplate.data.DataManager;
import uk.co.glapworth.boilerplate.ui.base.BaseActivity;
import uk.co.glapworth.boilerplate.ui.main.MainActivity;
import uk.co.glapworth.boilerplate.ui.signin.SignInActivity;


/**
 * Created by glapworth on 10/06/16.
 */
public class LauncherActivity extends BaseActivity {

    @Inject
    DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);

        Intent intent;
        if (mDataManager.getPreferencesHelper().getAccessToken() != null) {
            Timber.w("Found an access token.");
            intent = MainActivity.getStartIntent(this);
        } else {
            intent = SignInActivity.getStartIntent(this, false);
        }
        startActivity(intent);
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        finish();
    }
}
