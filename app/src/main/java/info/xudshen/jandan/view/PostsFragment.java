package info.xudshen.jandan.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.TimeUnit;

import info.xudshen.droiddata.adapter.impl.DDViewBindingCursorLoaderAdapter;
import info.xudshen.jandan.JandanApp;
import info.xudshen.jandan.R;
import info.xudshen.jandan.adapter.RecyclerViewAdapterFactory;
import info.xudshen.jandan.databinding.FragmentPostsBinding;
import info.xudshen.jandan.model.Article;
import rx.Observable;

public class PostsFragment extends Fragment {
    private Article article = new Article("123");

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static PostsFragment newInstance(String param1, String param2) {
        PostsFragment fragment = new PostsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Observable.empty().subscribe((T) -> {
        }, (E) -> {
        }, () -> {
            if (JandanApp.daoSession.getArticleDao().count() < 100) {
                for (int i = 0; i < 10000; i = i + 1) {
                    Article newArticle = new Article(i + "");
                    JandanApp.daoSession.getArticleDao().insert(newArticle);
                }
            }
        });
        Observable.interval(1, TimeUnit.SECONDS).subscribe(aLong -> {
            article.setContent(aLong.toString());
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentPostsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts, container, false);
        binding.setArticle(article);
        binding.myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DDViewBindingCursorLoaderAdapter viewAdapter = RecyclerViewAdapterFactory.getArticleListAdapter(getActivity());
        binding.myRecyclerView.setAdapter(viewAdapter);
        getLoaderManager().initLoader(0, null, viewAdapter);

        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        //Called when the fragment has been associated with the activity
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

}
