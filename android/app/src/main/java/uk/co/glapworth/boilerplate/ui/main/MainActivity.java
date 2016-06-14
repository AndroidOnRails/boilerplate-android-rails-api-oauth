package uk.co.glapworth.boilerplate.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;


import javax.inject.Inject;

import uk.co.glapworth.boilerplate.BuildConfig;
import uk.co.glapworth.boilerplate.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import uk.co.glapworth.boilerplate.ui.base.BaseActivity;
import uk.co.glapworth.boilerplate.ui.base.MvpView;

/**
 * Created by glapworth on 02/06/16.
 */
public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject MainPresenter mMainPresenter;


    /**
     * Create an Intent for the main activity.
     * Set autoCheckInDisabled to true if you want to prevent this activity from
     * triggering any auto check-in related service on create.
     * This is useful for testing purposes.
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }


    //@BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.version_number) TextView mVersionText;

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        //TextView myVersionText = (TextView)findViewById(R.id.version_number);
        ButterKnife.bind(this);

        mMainPresenter.attachView(this);

        /**
         * The getVersion() function exists in the MainPresenter
         * We call it here to grab the version number and set the text of mVersionText field.
         */
        mVersionText.setText(mMainPresenter.getVersion());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    public void showError() {
        //DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_ribots))
        //        .show();
    }



}
