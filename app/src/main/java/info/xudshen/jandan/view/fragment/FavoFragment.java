package info.xudshen.jandan.view.fragment;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.jandan.R;
import info.xudshen.jandan.internal.di.components.FavoComponent;
import info.xudshen.jandan.internal.di.components.PostComponent;

public class FavoFragment extends BaseFragment {
    public static FavoFragment newInstance() {
        Bundle args = new Bundle();
        FavoFragment fragment = new FavoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    @Named("favoListAdapter")
    DDBindableCursorLoaderRVHeaderAdapter favoListAdapter;

    @Override
    public void inject() {
        getComponent(FavoComponent.class).inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inject();

        View view = inflater.inflate(R.layout.fragment_favo, container, false);
        setActionBarDrawerToggle((Toolbar) view.findViewById(R.id.toolbar));

        return view;
    }
}