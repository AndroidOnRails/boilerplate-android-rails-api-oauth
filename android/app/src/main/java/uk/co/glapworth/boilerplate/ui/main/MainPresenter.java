package uk.co.glapworth.boilerplate.ui.main;

import javax.inject.Inject;
import timber.log.Timber;

import uk.co.glapworth.boilerplate.BuildConfig;
import uk.co.glapworth.boilerplate.ui.base.BasePresenter;

/**
 * Created by glapworth on 02/06/16.
 */
public class MainPresenter extends BasePresenter<MainMvpView> {

    @Inject
    public MainPresenter() {
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public String getVersion() {
        return BuildConfig.VERSION_NAME;
    }

}
