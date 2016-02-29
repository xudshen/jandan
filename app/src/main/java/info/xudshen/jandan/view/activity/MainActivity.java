package info.xudshen.jandan.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.LayoutInflaterCompat;

import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mikepenz.materialdrawer.Drawer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import info.xudshen.jandan.R;
import info.xudshen.jandan.internal.di.HasComponents;
import info.xudshen.jandan.internal.di.components.ActivityComponent;
import info.xudshen.jandan.internal.di.components.DaggerActivityComponent;
import info.xudshen.jandan.internal.di.components.DaggerFavoComponent;
import info.xudshen.jandan.internal.di.components.DaggerJokeComponent;
import info.xudshen.jandan.internal.di.components.DaggerPicComponent;
import info.xudshen.jandan.internal.di.components.DaggerPostComponent;
import info.xudshen.jandan.internal.di.components.DaggerVideoComponent;
import info.xudshen.jandan.internal.di.components.FavoComponent;
import info.xudshen.jandan.internal.di.components.JokeComponent;
import info.xudshen.jandan.internal.di.components.PicComponent;
import info.xudshen.jandan.internal.di.components.PostComponent;
import info.xudshen.jandan.internal.di.components.VideoComponent;
import info.xudshen.jandan.internal.di.modules.ActivityModule;
import info.xudshen.jandan.view.fragment.FavoFragment;
import info.xudshen.jandan.view.fragment.HomeFragment;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements HasComponents, HasDrawer {
    private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);
    @Inject
    Drawer drawer;

    private PostComponent postComponent;
    private PicComponent picComponent;
    private JokeComponent jokeComponent;
    private VideoComponent videoComponent;
    private FavoComponent favoComponent;
    private ActivityComponent activityComponent;

    private long previousSelection = -999;
    private boolean needRefreshAfterSetting = false;

    @Override
    protected void initializeInjector() {
        ActivityModule activityModule = getActivityModule();
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(activityModule)
                .build();
        activityComponent.inject(this);

        postComponent = DaggerPostComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(activityModule)
                .build();

        picComponent = DaggerPicComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(activityModule)
                .build();

        jokeComponent = DaggerJokeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(activityModule)
                .build();

        videoComponent = DaggerVideoComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(activityModule)
                .build();

        favoComponent = DaggerFavoComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(activityModule)
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeInjector();

        //set drawer click listener
        drawer.setOnDrawerItemClickListener((view, position, drawerItem) -> {
            logger.info("onClick, {}", drawerItem.getIdentifier());
            switch ((int) drawerItem.getIdentifier()) {
                case R.id.drawer_home: {
                    if (previousSelection != drawerItem.getIdentifier()) {
                        previousSelection = drawerItem.getIdentifier();
                        MainActivity.this.replaceFragment(R.id.activity_main_content, HomeFragment.newInstance());
                        setTitle(R.string.drawer_home);
                    }
                    break;
                }
                case R.id.drawer_favorites: {
                    if (previousSelection != drawerItem.getIdentifier()) {
                        previousSelection = drawerItem.getIdentifier();
                        MainActivity.this.replaceFragment(R.id.activity_main_content, FavoFragment.newInstance());
                        setTitle(R.string.drawer_favorites);
                    }
                    break;
                }
                case R.id.drawer_preference: {
                    MainActivity.this.getNavigator().launchSetting(MainActivity.this);
                    break;
                }
                case R.id.drawer_about: {
                    break;
                }
                default: {
                    break;
                }
            }
            drawer.closeDrawer();
            return true;
        });

        if (savedInstanceState == null) {
            drawer.setSelection(R.id.drawer_home);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //[foreground lifetime]visible & taking user focus
        if (needRefreshAfterSetting) {
            needRefreshAfterSetting = false;
            if (previousSelection == R.id.drawer_home) {
                MainActivity.this.replaceFragment(R.id.activity_main_content, HomeFragment.newInstance());
            } else if (previousSelection == R.id.drawer_favorites) {
                MainActivity.this.replaceFragment(R.id.activity_main_content, FavoFragment.newInstance());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //[foreground lifetime]visible & not taking user focus
        //called when device goes to sleep OR dialog appears
        //TODO: write crucial persistent data
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //TODO: the transient state of the activity (the state of the UI)
        //just test it with rotate the device
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == JandanSettingActivity.REQUEST_CODE) {
            if (resultCode == JandanSettingActivity.RESULT_FILTER_CHANGED) {
                logger.info("refresh from result");
                needRefreshAfterSetting = true;
            }
        }
    }

    //<editor-fold desc="Other Action">
    private int backPressedCount = 0;

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            if (backPressedCount == 0) {
                showToast(getString(R.string.press_again_exit));
                backPressedCount = 1;
                Observable.timer(5, TimeUnit.SECONDS).subscribeOn(Schedulers.immediate())
                        .subscribe(aLong -> {
                            backPressedCount = 0;
                        }, throwable -> {
                        });
            } else {
                super.onBackPressed();
                backPressedCount = 0;
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="HasComponents HasDrawer">
    @Override
    public <C> C getComponent(Class<C> componentType) {
        if (componentType.isInstance(this.activityComponent)) {
            return (C) this.activityComponent;
        }
        if (componentType.isInstance(this.postComponent)) {
            return (C) this.postComponent;
        }
        if (componentType.isInstance(this.picComponent)) {
            return (C) this.picComponent;
        }
        if (componentType.isInstance(this.jokeComponent)) {
            return (C) this.jokeComponent;
        }
        if (componentType.isInstance(this.videoComponent)) {
            return (C) this.videoComponent;
        }
        if (componentType.isInstance(this.favoComponent)) {
            return (C) this.favoComponent;
        }
        throw new IllegalStateException("componentType=" + componentType.getSimpleName() + " not found");
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }
    //</editor-fold>
}
