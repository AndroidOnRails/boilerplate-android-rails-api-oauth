package uk.co.glapworth.boilerplate.injection.component;

import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import com.squareup.otto.Bus;

import dagger.Component;
import uk.co.glapworth.boilerplate.BoilerplateApplication;
import uk.co.glapworth.boilerplate.data.DataManager;
import uk.co.glapworth.boilerplate.data.local.PreferencesHelper;
import uk.co.glapworth.boilerplate.data.remote.BPService;
import uk.co.glapworth.boilerplate.data.remote.GoogleAuthHelper;
import uk.co.glapworth.boilerplate.data.remote.UnauthorisedInterceptor;
import uk.co.glapworth.boilerplate.injection.ApplicationContext;
import uk.co.glapworth.boilerplate.injection.module.ApplicationModule;


/**
 * Created by glapworth on 02/06/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    //void inject(s)
    void inject(BoilerplateApplication boilerplateApplication);
    void inject(UnauthorisedInterceptor unauthorisedInterceptor);

    @ApplicationContext Context context();
    Application application();
    DataManager dataManager();
    BPService bpService();
    GoogleAuthHelper googleAuthHelper();
    PreferencesHelper preferencesHelper();
    Bus eventBus();
    AccountManager accountManager();
}
