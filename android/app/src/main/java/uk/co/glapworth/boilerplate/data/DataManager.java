package uk.co.glapworth.boilerplate.data;

import android.accounts.Account;

import rx.Observable;

import javax.inject.Inject;

import rx.functions.Func1;
import uk.co.glapworth.boilerplate.data.local.PreferencesHelper;
import uk.co.glapworth.boilerplate.data.model.BoilerPlate;
import uk.co.glapworth.boilerplate.data.remote.BPService;
import uk.co.glapworth.boilerplate.data.remote.BPService.SignInRequest;
import uk.co.glapworth.boilerplate.data.remote.BPService.SignInResponse;

import uk.co.glapworth.boilerplate.data.remote.GoogleAuthHelper;


/**
 * Created by glapworth on 06/06/16.
 */
public class DataManager {

    private final BPService mBPService;
    private final PreferencesHelper mPreferencesHelper;
    private final GoogleAuthHelper mGoogleAuthHelper;

    @Inject
    public DataManager(BPService bpService,
                       PreferencesHelper preferencesHelper,
                       GoogleAuthHelper googleAuthHelper) {
        mBPService = bpService;
        mPreferencesHelper = preferencesHelper;
        mGoogleAuthHelper = googleAuthHelper;

    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    /**
     * Sign in with a Google account.
     * 1. Retrieve an google auth code for the given account
     * 2. Sends code and account to API
     * 3. If success, saves boilerplate.profile and API access token in preferences
     */
    public Observable<BoilerPlate> signIn(Account account) {
        return mGoogleAuthHelper.retrieveAuthTokenAsObservable(account)
                .concatMap(new Func1<String, Observable<SignInResponse>>() {
                    @Override
                    public Observable<SignInResponse> call(String googleAccessToken) {
                        SignInRequest requestData = new SignInRequest(googleAccessToken);
                        return mBPService.signInGoogleOAuth(
                                requestData.code
                        );
                    }
                })
                .map(new Func1<SignInResponse, BoilerPlate>() {
                    @Override
                    public BoilerPlate call(SignInResponse signInResponse) {
                        mPreferencesHelper.putAccessToken(signInResponse.accessToken);
                        mPreferencesHelper.putSignedInBoilerPlate(signInResponse.boilerPlate);
                        return signInResponse.boilerPlate;
                    }
                });


    }

}
