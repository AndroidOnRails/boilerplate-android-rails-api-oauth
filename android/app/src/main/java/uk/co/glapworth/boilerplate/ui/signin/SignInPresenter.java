package uk.co.glapworth.boilerplate.ui.signin;


import android.accounts.Account;
import android.content.Intent;

import com.google.android.gms.auth.UserRecoverableAuthException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import uk.co.glapworth.boilerplate.BuildConfig;
import uk.co.glapworth.boilerplate.data.DataManager;
import uk.co.glapworth.boilerplate.data.model.BoilerPlate;
import uk.co.glapworth.boilerplate.ui.base.Presenter;
import uk.co.glapworth.boilerplate.util.NetworkUtil;

/**
 * Created by glapworth on 06/06/16.
 */
public class SignInPresenter implements Presenter<SignInMvpView> {

    private final DataManager mDataManager;
    private SignInMvpView mMpvView;
    private Subscription mSubscription;

    @Inject
    public SignInPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SignInMvpView mpvView) {
        this.mMpvView = mpvView;
    }

    @Override
    public void detachView() {
        mMpvView = null;

        if(mSubscription != null) mSubscription.unsubscribe();
    }

    public void signInWithGoogle(final Account account) {
        Timber.i("Starting sign in with account " + account.name);
        mMpvView.showProgress(true);
        mMpvView.setSignInButtonEnabled(false);
        mSubscription = mDataManager.signIn(account)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BoilerPlate>() {
                    @Override
                    public void onCompleted() {
                        mMpvView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMpvView.showProgress(false);
                        Timber.w("Sign in has called onError " + e);
                        if(e instanceof UserRecoverableAuthException) {
                            Timber.w("UserRecoverableAuthException has happened");
                            Intent recover = ((UserRecoverableAuthException) e).getIntent();
                            mMpvView.onUserRecoverableAuthException(recover);
                        } else {
                            mMpvView.setSignInButtonEnabled(true);
                            if(NetworkUtil.isHttpStatusCode(e, 403)) {
                                /**
                                 * Google Auth was successful but the user does not have a profile set up
                                 */
                                mMpvView.showProfileNotFoundError(account.name);
                            } else {
                                mMpvView.showGeneralSignInError();
                            }
                        }
                    }

                    @Override
                    public void onNext(BoilerPlate bp) {
                        Timber.i("Sign in successful. Profile name " + bp.profile.name);
                        mMpvView.onSignInSuccessful(bp.profile);
                    }
                });
    }
}
