package info.xudshen.jandan.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;

import info.xudshen.jandan.JandanApp;
import info.xudshen.jandan.R;
import info.xudshen.jandan.internal.di.HasComponent;
import info.xudshen.jandan.internal.di.HasComponents;
import info.xudshen.jandan.navigation.Navigator;
import info.xudshen.jandan.view.activity.BaseActivity;
import info.xudshen.jandan.view.activity.HasDrawer;
import info.xudshen.jandan.view.activity.TransitionHelper;

/**
 * Created by xudshen on 16/1/7.
 */
public abstract class BaseFragment extends Fragment implements TransitionHelper.Listener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        TransitionHelper.of(getActivity()).addListener(this);
        super.onCreate(savedInstanceState);

    }

    /**
     * inject dependencies
     * normally called in {@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * or {@link Fragment#onActivityCreated(Bundle)}
     */
    protected abstract void inject();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TransitionHelper.of(getActivity()).onViewCreated();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JandanApp.getRefWatcher(getActivity()).watch(this);
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

    protected Navigator getNavigator() {
        return ((BaseActivity) getActivity()).getNavigator();
    }

    /**
     * get components from parent activity
     */
    protected <C> C getComponent(Class<C> componentType) {
        if (HasComponent.class.isInstance(getActivity())) {
            return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
        } else if (HasComponents.class.isInstance(getActivity())) {
            return ((HasComponents) getActivity()).getComponent(componentType);
        } else {
            throw new IllegalStateException("activity=" + getActivity().getClass().getSimpleName()
                    + " not implement HasComponent or HasComponents");
        }
    }

    /**
     * get drawer from parent activity
     * WARN: we do not use @Inject from Drawer, since each activity should only has one drawer
     */
    protected Drawer getDrawer() {
        if (HasDrawer.class.isInstance(getActivity())) {
            return ((HasDrawer) getActivity()).getDrawer();
        }
        throw new IllegalStateException("activity=" + getActivity().getClass().getSimpleName()
                + " not implement HasDrawer");
    }

    /**
     * bind toolbar in this fragment with drawer
     */
    protected void setActionBarDrawerToggle(Toolbar toolbar) {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(getActivity(), getDrawer().getDrawerLayout(),
                toolbar,
                R.string.drawer_open, R.string.drawer_close);
        getDrawer().setActionBarDrawerToggle(drawerToggle);
        drawerToggle.syncState();
        drawerToggle.setDrawerIndicatorEnabled(true);
//        drawerToggle.setHomeAsUpIndicator(new IconicsDrawable(activity, GoogleMaterial.Icon.gmd_arrow_back).sizeDp(16).color(Color.WHITE));
//        drawerToggle.setToolbarNavigationClickListener(v -> activity.onBackPressed());
    }

    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
