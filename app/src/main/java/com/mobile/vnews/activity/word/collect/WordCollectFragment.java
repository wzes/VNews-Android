package com.mobile.vnews.activity.word.collect;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.mobile.vnews.R;
import com.mobile.vnews.activity.word.detail.WordDetailActivity;
import com.mobile.vnews.activity.word.detail.WordDetailAdapter;
import com.mobile.vnews.activity.word.recite.WordReciteActivity;
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

public class WordCollectFragment extends Fragment implements WordCollectContract.View {

    @BindView(R.id.fragment_word_collect_toolbar)
    Toolbar mFragmentWordCollectToolbar;
    @BindView(R.id.fragment_word_collect_app_bar)
    AppBarLayout mFragmentWordCollectAppBar;
    @BindView(R.id.fragment_word_collect_recycler_view)
    RecyclerView mFragmentWordCollectRecyclerView;
    Unbinder unbinder;
    private WordCollectContract.Presenter mPresenter;

    private Word mWord;
    private static String word;
    private List<WordCollect> mList;
    private WordCollectAdapter mWordCollectAdapter;

    public static WordCollectFragment newInstance(String word) {
        WordCollectFragment.word = word;
        return new WordCollectFragment();
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
    public void setPresenter(WordCollectContract.Presenter mPresenter) {
        this.mPresenter = checkNotNull(mPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_collect, container, false);
        unbinder = ButterKnife.bind(this, view);
        WordCollectActivity activity = (WordCollectActivity) getActivity();
        activity.setSupportActionBar(mFragmentWordCollectToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        setHasOptionsMenu(true);
        mFragmentWordCollectToolbar.setTitle(getString(word));
        mFragmentWordCollectToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_open_recite:
                    Intent intent = new Intent(getActivity(), WordReciteActivity.class);
                    intent.putExtra("tag", word);
                    startActivity(intent);
                    break;
            }
            return true;
        });
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
    /**
     *
     * @param source
     * @return
     */
    public int getString(String source) {
        switch (source) {
            case "收藏":
                return R.string.word_book_title;
            case "生词":
                return R.string.word_unknown;
            case "熟记":
                return R.string.word_know;
            default:
                return R.string.word_book_title;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.word_recite_menu, menu);
    }

    public void onResume() {
        super.onResume();
        mPresenter.load(word);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void showResults(List<WordCollect> list) {
        mList = list;
        //
        //
        if (mWordCollectAdapter == null) {
            //mList = list;
            mWordCollectAdapter = new WordCollectAdapter(R.layout.word_item, mList);
            mWordCollectAdapter.setOnItemClickListener((adapter, view, position) -> {
                Intent intent = new Intent(getContext(), WordDetailActivity.class);
                intent.putExtra("word", mList.get(position).getWord());
                startActivity(intent);
            });
            // on click
            mWordCollectAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                switch (view.getId()) {
                    case R.id.word_item_like:
                        WordCollect wordCollect = new WordCollect();
                        wordCollect.setId(mList.get(position).getId());
                        wordCollect.setMeans(mList.get(position).getMeans());
                        wordCollect.setTag("收藏");
                        wordCollect.setWord(mList.get(position).getWord());
                        wordCollect.setTimestamp(System.currentTimeMillis());
                        try {
                            mPresenter.addCollect(wordCollect);
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Collect Fail", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getContext(), "Collect Success", Toast.LENGTH_SHORT).show();
                        break;
                }

            });
            mFragmentWordCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mFragmentWordCollectRecyclerView.setAdapter(mWordCollectAdapter);

            ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mWordCollectAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
            itemTouchHelper.attachToRecyclerView(mFragmentWordCollectRecyclerView);


            // swipe
            mWordCollectAdapter.enableSwipeItem();
            mWordCollectAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
                @Override
                public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                    Toast.makeText(getActivity(), "滑动删除", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

                }

                @Override
                public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                    mPresenter.removeCollect(mList.get(pos));
                }

                @Override
                public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

                }
            });
        }
    }

    @Override
    public void onShowFail() {
        Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
    }
}
