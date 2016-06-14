package uk.co.glapworth.boilerplate.data.remote;

import android.accounts.Account;
import android.content.Context;


import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;


import java.io.IOException;

import javax.inject.Inject;

import timber.log.Timber;
import uk.co.glapworth.boilerplate.BuildConfig;
import rx.Observable;
import rx.Subscriber;
import uk.co.glapworth.boilerplate.injection.ApplicationContext;

/**
 * Created by glapworth on 06/06/16.
 */
public class GoogleAuthHelper {

    private static final String SCOPE = String.format(
            "oauth2:server:client_id:%s:api_scope:https://www.googleapis.com/auth/userinfo.profile"
                    + " https://www.googleapis.com/auth/userinfo.email",
            BuildConfig.GOOGLE_API_SERVER_CLIENT_ID);


    private final Context mContext;

    @Inject
    public GoogleAuthHelper(@ApplicationContext Context context) {
        mContext = context;
    }

    public String retrieveAuthToken(Account account) throws GoogleAuthException, IOException {
        String token = GoogleAuthUtil.getToken(mContext, account, SCOPE);
        //GoogleAuthUtil.get
        Timber.w("token is " + token);
        /**
         * The token needs to be clear, so we make sure next time we get a brand new one.  Otherwise
         * this may return a token which has already been used.  Since tokens are one time, the auth
         * won't work.
         */
        GoogleAuthUtil.clearToken(mContext,token);
        return token;
    }

    public Observable<String> retrieveAuthTokenAsObservable(final Account account) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(retrieveAuthToken(account));
                    subscriber.onCompleted();
                } catch (Throwable error) {
                    subscriber.onError(error);
                }
            }
        });
    }

}
