package info.xudshen.jandan.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.xudshen.jandan.R;

public class BlankFragment extends BaseFragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static BlankFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        BlankFragment fragment = new BlankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void inject() {
        
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        TextView textView = (TextView) view.findViewById(R.id.fragment_blank_text);
        textView.setText("Fragment $" + mPage);
        return view;
    }
}
