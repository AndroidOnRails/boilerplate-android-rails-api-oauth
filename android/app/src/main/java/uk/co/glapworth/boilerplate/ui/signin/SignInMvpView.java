package uk.co.glapworth.boilerplate.ui.signin;

import android.content.Intent;

import uk.co.glapworth.boilerplate.data.model.Profile;
import uk.co.glapworth.boilerplate.ui.base.MvpView;

/**
 * Created by glapworth on 06/06/16.
 */
public interface SignInMvpView extends MvpView {

    void onSignInSuccessful(Profile signedInProfile);

    void showProgress(boolean show);

    void onUserRecoverableAuthException(Intent recoverIntent);

    void setSignInButtonEnabled(boolean enabled);

    void showProfileNotFoundError(String accountName);

    void showGeneralSignInError();
}
