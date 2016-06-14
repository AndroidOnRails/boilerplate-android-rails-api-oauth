package uk.co.glapworth.boilerplate;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;

import timber.log.Timber;

import uk.co.glapworth.boilerplate.data.BusEvent;
import uk.co.glapworth.boilerplate.data.DataManager;
import uk.co.glapworth.boilerplate.injection.component.ApplicationComponent;
import uk.co.glapworth.boilerplate.injection.component.DaggerApplicationComponent;
import uk.co.glapworth.boilerplate.injection.module.ApplicationModule;



/**
 * Created by glapworth on 02/06/16.
 */
public class BoilerplateApplication extends Application {

    @Inject Bus mEventBus;
    @Inject DataManager mDataManager;
    ApplicationComponent mApplicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Fabric.with(this, new Crashlytics());
        }

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
        mEventBus.register(this);
    }


    public static BoilerplateApplication get(Context context) {
        return (BoilerplateApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }

        return mApplicationComponent;
    }

    // For testing to replace the component with a test specific one..
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    @Subscribe
    public void onAuthenticationError(BusEvent.AuthenticationError event) {
     /*   mDataManager.signOut()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        startSignInActivity();
                    }
                });*/
    }
}
