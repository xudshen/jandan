package info.xudshen.jandan.internal.di.modules;

import android.app.Activity;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dagger.Module;
import dagger.Provides;
import info.xudshen.jandan.R;
import info.xudshen.jandan.internal.di.PerActivity;

@Module
public class ActivityModule {
    private final Activity activity;
    private static final Logger logger = LoggerFactory.getLogger(ActivityModule.class);

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    Drawer provideDrawer() {
        logger.info("provideDrawer");
        //TODO: read profile from local db
        IProfile profile = new ProfileDrawerItem()
                .withName("Java-Help")
                .withEmail("java-help@mail.ru")
                .withIcon(R.color.md_green_500);

        ProfileSettingDrawerItem profileSettingDrawerItem = new ProfileSettingDrawerItem()
                .withName(this.activity.getString(R.string.drawer_profile_add))
                .withIcon(GoogleMaterial.Icon.gmd_add)
                .withIdentifier(-1);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this.activity)
                .withHeaderBackground(R.color.colorPrimaryDark)
                .addProfiles(profile, profileSettingDrawerItem)
                .build();

        return new DrawerBuilder().withActivity(this.activity)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_posts)
                                .withIdentifier(R.id.nav_posts).withIcon(FontAwesome.Icon.faw_newspaper_o),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_pics)
                                .withIdentifier(R.id.nav_pics).withIcon(GoogleMaterial.Icon.gmd_photo),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_jokes)
                                .withIdentifier(R.id.nav_jokes).withIcon(GoogleMaterial.Icon.gmd_face),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_movies)
                                .withIdentifier(R.id.nav_movies).withIcon(GoogleMaterial.Icon.gmd_movie),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_preference)
                                .withIdentifier(R.id.nav_preference).withIcon(GoogleMaterial.Icon.gmd_settings),
                        new PrimaryDrawerItem().withName(R.string.drawer_about)
                                .withIdentifier(R.id.nav_about).withIcon(GoogleMaterial.Icon.gmd_info)
                )
                .build();
    }
}
