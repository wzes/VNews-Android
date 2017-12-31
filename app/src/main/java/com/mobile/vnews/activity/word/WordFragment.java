package com.mobile.vnews.activity.word;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchHistoryTable;
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;
import com.mobile.vnews.R;
import com.mobile.vnews.activity.word.collect.WordCollectActivity;
import com.mobile.vnews.activity.word.detail.WordDetailActivity;
import com.mobile.vnews.activity.word.search.WordSearchActivity;
import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.bean.WordCollect;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class WordFragment extends Fragment implements WordContract.View {

    Unbinder unbinder;
    @BindView(R.id.fragment_word_recycler_view)
    RecyclerView mFragmentWordRecyclerView;
    @BindView(R.id.fragment_word_search_layout)
    FrameLayout fragmentWordSearchLayout;
    private WordContract.Presenter presenter;

    private WordBookAdapter mWordBookAdapter;
    private List<String> mBooks;

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
        // check
        if (AppPreferences.getLoginState()) {
            presenter.load(AppPreferences.getLoginUserID());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showBooks(List<String> books) {
        if (mBooks == null) {
            mBooks = new ArrayList<>();
        }
        mBooks.clear();
        mBooks.addAll(books);
        if (mWordBookAdapter == null) {
            mWordBookAdapter = new WordBookAdapter(R.layout.word_book_item, mBooks);
            mFragmentWordRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            mFragmentWordRecyclerView.setAdapter(mWordBookAdapter);
            mWordBookAdapter.setOnItemClickListener((adapter, view, position) -> {
                Intent intent = new Intent(getActivity(), WordCollectActivity.class);
                intent.putExtra("collect", mBooks.get(position));
                startActivity(intent);
            });
        } else {
            mWordBookAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.fragment_word_search_layout)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), WordSearchActivity.class));
    }
}
