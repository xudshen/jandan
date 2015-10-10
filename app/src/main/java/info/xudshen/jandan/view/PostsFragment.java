package info.xudshen.jandan.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import info.xudshen.droiddata.adapter.impl.DDViewBindingCursorLoaderAdapter;
import info.xudshen.jandan.JandanApp;
import info.xudshen.jandan.R;
import info.xudshen.jandan.adapter.RecyclerViewAdapterFactory;
import info.xudshen.jandan.databinding.FragmentPostsBinding;
import info.xudshen.jandan.model.Article;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
                List<Article> articles = new ArrayList<Article>();
                for (int i = 0; i < 10000; i = i + 1) {
                    Article newArticle = new Article(i + "");
                    newArticle.setArticleId(Long.valueOf(i));
                    articles.add(newArticle);
                }
                JandanApp.daoSession.getArticleDao().insertInTx(articles);
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

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        DDViewBindingCursorLoaderAdapter viewAdapter = RecyclerViewAdapterFactory.getArticleListAdapter(getActivity());
        viewAdapter.onItemClick((itemView, position) -> {
            Snackbar.make(itemView, position + "clicked", Snackbar.LENGTH_LONG).show();
            Article itemArticle = JandanApp.daoSession.getArticleDao().readEntity(viewAdapter.getItemCursor(position), 0);
            article.setArticleId(itemArticle.getArticleId());
            article.setTitle(itemArticle.getTitle());
            article.setId(itemArticle.getId());
        });

        binding.myRecyclerView.setLayoutManager(linearLayoutManager);
        binding.myRecyclerView.setAdapter(viewAdapter);

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.amber_700, R.color.deep_purple_a200);
        binding.swipeRefreshLayout.setOnRefreshListener((direction) -> {
            switch (direction) {
                case TOP: {
                    Observable.range(1, 10).subscribeOn(Schedulers.io())
                            .doOnNext(integer -> {
                                long offset = JandanApp.daoSession.getArticleDao().count();
                                Article newArticle = new Article(offset + "");
                                newArticle.setArticleId(offset);
                                JandanApp.daoSession.getArticleDao().insert(newArticle);
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((T) -> {
                            }, (E) -> {
                            }, () -> {
                                binding.swipeRefreshLayout.setRefreshing(false);
                            });
                    break;
                }
                case BOTTOM: {
                    Observable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(aLong -> {
                                binding.swipeRefreshLayout.setRefreshing(false);
                            });
                    break;
                }
            }
        });

        binding.fab.setOnClickListener(v ->
                binding.myRecyclerView.smoothScrollToPosition(linearLayoutManager.getItemCount() - 1));
        binding.insertBtn.setOnClickListener(v -> {
//                Snackbar.make(v, article.getTitle(), Snackbar.LENGTH_SHORT).show()
                    article.setTitle(binding.inputTitle.getText().toString());
                    JandanApp.daoSession.getArticleDao().update(article);
                }
        );
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
