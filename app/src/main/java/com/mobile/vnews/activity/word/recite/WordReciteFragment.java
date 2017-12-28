package com.mobile.vnews.activity.word.recite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mobile.vnews.R;
import com.mobile.vnews.module.bean.Word;
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

public class WordReciteFragment extends Fragment implements WordReciteContract.View {

    @BindView(R.id.fragment_word_recite_toolbar)
    Toolbar mFragmentWordReciteToolbar;
    @BindView(R.id.fragment_word_recite_app_bar)
    AppBarLayout mFragmentWordReciteAppBar;
    @BindView(R.id.fragment_word_recite_title)
    AppCompatTextView mFragmentWordReciteTitle;
    @BindView(R.id.fragment_word_recite_expand)
    AppCompatImageButton mFragmentWordReciteExpand;
    @BindView(R.id.fragment_word_recite_means)
    AppCompatTextView mFragmentWordReciteMeans;
    @BindView(R.id.fragment_word_recite_unknown)
    AppCompatButton mFragmentWordReciteUnknown;
    @BindView(R.id.fragment_word_recite_collect)
    AppCompatButton mFragmentWordReciteCollect;
    @BindView(R.id.fragment_word_recite_known)
    AppCompatButton mFragmentWordReciteKnown;
    @BindView(R.id.fragment_word_recite_back)
    AppCompatImageButton mFragmentWordReciteBack;
    @BindView(R.id.fragment_word_recite_process)
    AppCompatTextView mFragmentWordReciteProcess;
    @BindView(R.id.fragment_word_recite_forward)
    AppCompatImageButton mFragmentWordReciteForward;
    @BindView(R.id.fragment_word_recite_layout)
    RelativeLayout mFragmentWordReciteLayout;
    private WordReciteContract.Presenter mPresenter;

    Unbinder unbinder;
    private static String tag;
    private List<WordCollect> mList;

    public static WordReciteFragment newInstance(String tag) {
        WordReciteFragment.tag = tag;
        return new WordReciteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_recite, container, false);
        unbinder = ButterKnife.bind(this, view);
        WordReciteActivity activity = (WordReciteActivity) getActivity();
        activity.setSupportActionBar(mFragmentWordReciteToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private WordCollect newWordCollect(String tag, int position) {
        WordCollect wordCollect = new WordCollect();
        wordCollect.setId(mList.get(position).getId());
        wordCollect.setMeans(mList.get(position).getMeans());
        wordCollect.setTag(tag);
        wordCollect.setWord(mList.get(position).getWord());
        wordCollect.setTimestamp(System.currentTimeMillis());
        return wordCollect;
    }

    @OnClick({R.id.fragment_word_recite_expand, R.id.fragment_word_recite_unknown, R.id.fragment_word_recite_collect, R.id.fragment_word_recite_known, R.id.fragment_word_recite_forward})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_word_recite_expand:
                break;
            case R.id.fragment_word_recite_unknown:
                break;
            case R.id.fragment_word_recite_collect:
                break;
            case R.id.fragment_word_recite_known:
                break;
            case R.id.fragment_word_recite_forward:
                break;
        }
    }

    @Override
    public void setPresenter(WordReciteContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void showResults(@NonNull List<WordCollect> list) {
        mList = list;
    }

    @Override
    public void nextWord(int position) {

    }

    @Override
    public void onShowFail() {

    }
}
