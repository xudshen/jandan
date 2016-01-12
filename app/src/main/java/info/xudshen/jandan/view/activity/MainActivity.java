package info.xudshen.jandan.view.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.xudshen.jandan.R;
import info.xudshen.jandan.internal.di.HasComponents;
import info.xudshen.jandan.internal.di.components.DaggerPostComponent;
import info.xudshen.jandan.internal.di.components.PostComponent;
import info.xudshen.jandan.view.fragment.IndicatorFragment;
import info.xudshen.jandan.view.fragment.PostHubFragment;

public class MainActivity extends BaseActivity implements HasComponents {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sliding_tabs)
    TabLayout slidingTabs;

    Drawer drawer;

    private PostComponent postComponent;

    private void initDrawer() {
        IProfile profile = new ProfileDrawerItem()
                .withName("Java-Help")
                .withEmail("java-help@mail.ru")
                .withIcon(R.color.md_green_500);

        ProfileSettingDrawerItem profileSettingDrawerItem = new ProfileSettingDrawerItem()
                .withName(getString(R.string.drawer_profile_add))
                .withIcon(GoogleMaterial.Icon.gmd_add)
                .withIdentifier(-1);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.amber_500)
                .addProfiles(profile, profileSettingDrawerItem)
                .build();

        drawer = new DrawerBuilder().withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_posts).withIdentifier(R.id.nav_posts).withIcon(FontAwesome.Icon.faw_newspaper_o),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_pics).withIdentifier(R.id.nav_pics).withIcon(GoogleMaterial.Icon.gmd_photo),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_jokes).withIdentifier(R.id.nav_jokes).withIcon(GoogleMaterial.Icon.gmd_face),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_movies).withIdentifier(R.id.nav_movies).withIcon(GoogleMaterial.Icon.gmd_movie),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_preference).withIdentifier(R.id.nav_preference).withIcon(GoogleMaterial.Icon.gmd_settings),
                        new PrimaryDrawerItem().withName(R.string.drawer_about).withIdentifier(R.id.nav_about).withIcon(GoogleMaterial.Icon.gmd_info)
                )
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    switch (drawerItem.getIdentifier()) {
                        case R.id.nav_posts: {
                            slidingTabs.setVisibility(View.VISIBLE);
                            this.replaceFragment(R.id.activity_main_content, PostHubFragment.newInstance());
                            setTitle(R.string.drawer_item_posts);
                            break;
                        }
                        case R.id.nav_pics: {
                            slidingTabs.setVisibility(View.GONE);
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
                })
                .build();
//                menuItem.setChecked(true);
//                setTitle(menuItem.getTitle());
//                drawerLayout.closeDrawers();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        initDrawer();

        this.initializeInjector();
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
        this.postComponent = DaggerPostComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public <C> C getComponent(Class<C> componentType) {
        if (componentType.isInstance(this.postComponent)) {
            return (C) this.postComponent;
        }
        throw new IllegalStateException("componentType=" + componentType.getSimpleName() + " not found");
    }
    //</editor-fold>
}
