package info.xudshen.jandan.view.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by xudshen on 16/1/7.
 */
public abstract class BaseFragment extends Fragment {
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
