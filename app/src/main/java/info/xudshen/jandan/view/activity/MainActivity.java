package info.xudshen.jandan.view.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.LayoutInflaterCompat;

import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mikepenz.materialdrawer.Drawer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                    this.replaceFragment(R.id.activity_main_content, HomeFragment.newInstance());
                    setTitle(R.string.drawer_home);
                    break;
                }
                case R.id.drawer_favorites: {
                    this.replaceFragment(R.id.activity_main_content, FavoFragment.newInstance());
                    setTitle(R.string.drawer_favorites);
                    break;
                }
                case R.id.drawer_preference: {
                    break;
                }
                case R.id.drawer_about: {
                    break;
                }
                default: {
                    return false;
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
