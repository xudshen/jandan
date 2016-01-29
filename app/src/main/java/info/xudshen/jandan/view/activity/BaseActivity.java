package info.xudshen.jandan.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import info.xudshen.jandan.JandanApp;
import info.xudshen.jandan.internal.di.components.ApplicationComponent;
import info.xudshen.jandan.internal.di.modules.ActivityModule;
import info.xudshen.jandan.navigation.Navigator;

/**
 * Created by xudshen on 16/1/6.
 */
public abstract class BaseActivity extends AppCompatActivity implements TransitionHelper.Source, TransitionHelper.Listener {
    @Inject
    Navigator navigator;
    TransitionHelper transitionHelper;

    @Override
    public TransitionHelper getTransitionHelper() {
        return transitionHelper;
    }

    @Override
    public void setTransitionHelper(TransitionHelper transitionHelper) {
        this.transitionHelper = transitionHelper;
    }

    /**
     * called in {@link #onCreate(Bundle)}
     */
    protected abstract void initializeInjector();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TransitionHelper.init(this, savedInstanceState);
        TransitionHelper.of(this).addListener(this);
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        TransitionHelper.of(this).onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        TransitionHelper.of(this).onResume();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        TransitionHelper.of(this).onBackPressed();
    }

    //<editor-fold desc="TransitionHelper.Listener">
    @Override
    public void onBeforeViewShows(View contentView) {
    }

    @Override
    public void onBeforeEnter(View contentView) {
    }

    @Override
    public void onAfterEnter() {
    }

    @Override
    public boolean onBeforeBack() {
        return false;
    }

    @Override
    public void onBeforeReturn() {
    }
    //</editor-fold>

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link info.xudshen.jandan.internal.di.components.ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((JandanApp) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    public Navigator getNavigator() {
        return navigator;
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment        The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(containerViewId, fragment)
                .commit();
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment        The fragment to be added.
     */
    protected void replaceFragment(int containerViewId, Fragment fragment) {
        //Insert the fragment by replacing any existing fragment
        getSupportFragmentManager().beginTransaction()
                .replace(containerViewId, fragment)
//if you do call addToBackStack() when removing a fragment,
//then the fragment is stopped and will be resumed if the user navigates back
//                .addToBackStack(null)
                .commit();
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
