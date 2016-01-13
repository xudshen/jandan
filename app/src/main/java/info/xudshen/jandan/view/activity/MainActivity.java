package info.xudshen.jandan.view.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.mikepenz.materialdrawer.Drawer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import info.xudshen.jandan.R;
import info.xudshen.jandan.internal.di.HasComponents;
import info.xudshen.jandan.internal.di.components.ActivityComponent;
import info.xudshen.jandan.internal.di.components.DaggerActivityComponent;
import info.xudshen.jandan.internal.di.components.DaggerPostComponent;
import info.xudshen.jandan.internal.di.components.PostComponent;
import info.xudshen.jandan.internal.di.modules.ActivityModule;
import info.xudshen.jandan.view.fragment.IndicatorFragment;
import info.xudshen.jandan.view.fragment.PostHubFragment;

public class MainActivity extends BaseActivity implements HasComponents, HasDrawer {
    private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);
    @Inject
    Drawer drawer;

    private PostComponent postComponent;
    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeInjector();
        //set drawer click listener
        drawer.setOnDrawerItemClickListener((view, position, drawerItem) -> {
            logger.info("onClick, {}", drawerItem.getIdentifier());
            switch (drawerItem.getIdentifier()) {
                case R.id.nav_posts: {
                    this.replaceFragment(R.id.activity_main_content, PostHubFragment.newInstance());
                    setTitle(R.string.drawer_item_posts);
                    break;
                }
                case R.id.nav_pics: {
                    this.replaceFragment(R.id.activity_main_content, IndicatorFragment.newInstance(90));
                    setTitle(R.string.drawer_item_pics);
                    break;
                }
                default: {
                    return false;
                }
            }
            drawer.closeDrawer();
            return true;
        });
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

    //<editor-fold desc="Drawer">
    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
    //</editor-fold>

    //<editor-fold desc="HasComponents">
    private void initializeInjector() {
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
    }

    @Override
    public <C> C getComponent(Class<C> componentType) {
        if (componentType.isInstance(this.activityComponent)) {
            return (C) this.activityComponent;
        }
        if (componentType.isInstance(this.postComponent)) {
            return (C) this.postComponent;
        }
        throw new IllegalStateException("componentType=" + componentType.getSimpleName() + " not found");
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    //</editor-fold>
}
