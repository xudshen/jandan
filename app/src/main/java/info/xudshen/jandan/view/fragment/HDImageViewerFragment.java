package info.xudshen.jandan.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.xudshen.jandan.R;

public class HDImageViewerFragment extends BaseFragment {
    private static final String ARG_URL = "ARG_URL";

    public static HDImageViewerFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);

        HDImageViewerFragment fragment = new HDImageViewerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public HDImageViewerFragment() {
    }

    private String url;

    @Override
    protected void inject() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inject();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hdimage_viewer, container, false);

        ((TextView) view.findViewById(R.id.textUrl)).setText(url);
        return view;
    }
}
