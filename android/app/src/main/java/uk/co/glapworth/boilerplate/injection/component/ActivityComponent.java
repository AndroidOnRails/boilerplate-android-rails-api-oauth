package uk.co.glapworth.boilerplate.injection.component;

import dagger.Component;

import uk.co.glapworth.boilerplate.injection.PerActivity;
import uk.co.glapworth.boilerplate.injection.module.ActivityModule;
import uk.co.glapworth.boilerplate.ui.LauncherActivity;
import uk.co.glapworth.boilerplate.ui.main.MainActivity;
import uk.co.glapworth.boilerplate.ui.signin.SignInActivity;

/**
 * Created by glapworth on 02/06/16.
 * This component injects dependencies to all Activities across the application
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(SignInActivity signInActivity);
    void inject(LauncherActivity launcherActivity);

}
