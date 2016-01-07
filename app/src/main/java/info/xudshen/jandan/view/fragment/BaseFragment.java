package info.xudshen.jandan.view.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import info.xudshen.jandan.internal.di.HasComponent;
import info.xudshen.jandan.internal.di.HasComponents;

/**
 * Created by xudshen on 16/1/7.
 */
public abstract class BaseFragment extends Fragment {
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

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
}
