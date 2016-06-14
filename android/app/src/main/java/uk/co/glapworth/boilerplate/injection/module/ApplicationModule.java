package uk.co.glapworth.boilerplate.injection.module;

import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import uk.co.glapworth.boilerplate.data.remote.BPService;
import uk.co.glapworth.boilerplate.injection.ApplicationContext;
/**
 * import uk.co.glapworth.boilerplate.data.remote.MyService
 */


/**
 * Created by glapworth on 02/06/16.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Bus provideEventBus() {
        return new Bus();
    }

    /**
     * Provide the service...
     * @Provides
     * @Singleton
     * MyService provideMyService() {
     *     return MyService.Creator.newMyService();
     * }
     */

    @Provides
    @Singleton
    BPService provideBPService() {
        return BPService.Factory.makeBPService(mApplication);
    }

    @Provides
    AccountManager provideAccountManager() {
        return AccountManager.get(mApplication);
    }
}
