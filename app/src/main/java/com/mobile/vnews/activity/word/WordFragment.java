package com.mobile.vnews.activity.word;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchHistoryTable;
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;
import com.mobile.vnews.R;
import com.mobile.vnews.activity.word.detail.WordDetailActivity;
import com.mobile.vnews.module.bean.Word;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class WordFragment extends Fragment implements WordContract.View {

    @BindView(R.id.fragment_word_searchView)
    SearchView mSearchView;
    Unbinder unbinder;
    private WordContract.Presenter presenter;
    private SearchAdapter searchAdapter;
    private List<SearchItem> suggestionsList;
    public static WordFragment getInstance() {
        return new WordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void setPresenter(WordContract.Presenter mPresenter) {
        this.presenter = checkNotNull(mPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSearchView != null) {
            mSearchView.setVersionMargins(SearchView.VersionMargins.TOOLBAR_SMALL);
            mSearchView.setHint(R.string.word_search);
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mSearchView.close(false);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    presenter.search(newText);
                    return false;
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showResult(@NonNull List<String> words) {
        if (suggestionsList == null) {
            suggestionsList = new ArrayList<>();
        } else {
            suggestionsList.clear();
        }

        if (searchAdapter == null) {
            SearchHistoryTable mHistoryDatabase = new SearchHistoryTable(getContext());
            searchAdapter = new SearchAdapter(getContext(), suggestionsList);
            searchAdapter.setOnSearchItemClickListener((view, position, text) -> {
                this.showDetail(text);
                try {
                    mHistoryDatabase.addItem(new SearchItem(text));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mSearchView.close(false);
            });
            mSearchView.setAdapter(searchAdapter);
        }

        for (String word : words) {
            suggestionsList.add(new SearchItem(word));
        }
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDetail(String word) {
        Intent intent = new Intent(getContext(), WordDetailActivity.class);
        intent.putExtra("word", word);
        startActivity(intent);
    }
}
