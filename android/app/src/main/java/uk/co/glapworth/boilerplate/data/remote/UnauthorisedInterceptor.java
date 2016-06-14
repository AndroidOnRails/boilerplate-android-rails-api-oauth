package uk.co.glapworth.boilerplate.data.remote;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

import java.io.IOException;

import javax.inject.Inject;

import uk.co.glapworth.boilerplate.BoilerplateApplication;
import uk.co.glapworth.boilerplate.data.BusEvent;

import okhttp3.Interceptor;
import okhttp3.Response;
/**
 * Created by glapworth on 07/06/16.
 */
public class UnauthorisedInterceptor implements Interceptor {

    @Inject
    Bus eventBus;

    public UnauthorisedInterceptor(Context context) {
        BoilerplateApplication.get(context).getComponent().inject(this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() == 401) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    eventBus.post(new BusEvent.AuthenticationError());
                }
            });
        }
        return response;
    }

}
