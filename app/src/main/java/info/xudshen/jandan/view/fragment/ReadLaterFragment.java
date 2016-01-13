package info.xudshen.jandan.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.xudshen.jandan.R;
import info.xudshen.jandan.internal.di.components.PostComponent;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadLaterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadLaterFragment extends BaseFragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static ReadLaterFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ReadLaterFragment fragment = new ReadLaterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getComponent(PostComponent.class).inject(this);

        View view = inflater.inflate(R.layout.fragment_read_later, container, false);
        setActionBarDrawerToggle((Toolbar) view.findViewById(R.id.toolbar));

        TextView textView = (TextView) view.findViewById(R.id.fragment_indicator_text);
        textView.setText("Fragment #" + mPage);
        return view;
    }
}
