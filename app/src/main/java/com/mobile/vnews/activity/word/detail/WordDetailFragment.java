package com.mobile.vnews.activity.word.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobile.vnews.R;
import com.mobile.vnews.module.bean.Word;

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

public class WordDetailFragment extends Fragment implements WordDetailContract.View {
    @BindView(R.id.fragment_word_detail_toolbar)
    Toolbar mFragmentWordDetailToolbar;
    @BindView(R.id.fragment_word_detail_app_bar)
    AppBarLayout mFragmentWordDetailAppBar;
    @BindView(R.id.fragment_word_detail_title)
    AppCompatTextView mFragmentWordDetailTitle;
    @BindView(R.id.fragment_word_detail_voice_en_start)
    AppCompatImageButton mFragmentWordDetailVoiceEnStart;
    @BindView(R.id.fragment_word_detail_en_voice)
    AppCompatTextView mFragmentWordDetailEnVoice;
    @BindView(R.id.fragment_word_detail_voice_am_start)
    AppCompatImageButton mFragmentWordDetailVoiceAmStart;
    @BindView(R.id.fragment_word_detail_am_voice)
    AppCompatTextView mFragmentWordDetailAmVoice;
    @BindView(R.id.fragment_word_detail_recycler_view)
    RecyclerView mFragmentWordDetailRecyclerView;
    @BindView(R.id.fragment_word_detail_collect)
    AppCompatButton mFragmentWordDetailCollect;
    @BindView(R.id.fragment_word_detail_layout)
    LinearLayout mFragmentWordDetailLayout;
    Unbinder unbinder;
    private WordDetailContract.Presenter mPresenter;

    private Word mWord;
    private static String word;
    private List<String> mList;
    private WordDetailAdapter mWordDetailAdapter;
    public static WordDetailFragment newInstance(String word) {
        WordDetailFragment.word = word;
        return new WordDetailFragment();
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
    public void setPresenter(WordDetailContract.Presenter mPresenter) {
        this.mPresenter = checkNotNull(mPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        mFragmentWordDetailTitle.setText(word);
        WordDetailActivity activity = (WordDetailActivity) getActivity();
        activity.setSupportActionBar(mFragmentWordDetailToolbar);
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
        mPresenter.load(word);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showResult(@NonNull Word word) {
        mWord = word;
        //
        if (word.getVoiceList() != null && word.getVoiceList().get(0) != null) {
            mFragmentWordDetailEnVoice.setText(word.getVoiceList().get(0).getPhonogram());
        }
        if (word.getVoiceList() != null && word.getVoiceList().get(1) != null) {
            mFragmentWordDetailAmVoice.setText(word.getVoiceList().get(1).getPhonogram());
        }
        //
        if (mWordDetailAdapter == null) {
            if (mList == null) {
                mList = new ArrayList<>();
                for (Word.Pos pos : word.getPosList()) {
                    mList.add(pos.getSymbol() + " " + pos.getMeans());
                }
                for (Word.Exchange exchange : word.getExchanges()) {
                    mList.add(exchange.getName() + ": " + exchange.getContent());
                }
            }
            //mList = list;
            mWordDetailAdapter = new WordDetailAdapter(R.layout.word_detail_item, mList);
            // on click
//            mWordDetailAdapter.setOnItemClickListener((adapter, view, position) -> {
////                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
////                intent.putExtra("newsID", mList.get(position).getID());
////                startActivity(intent);
//            });
            mFragmentWordDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mFragmentWordDetailRecyclerView.setAdapter(mWordDetailAdapter);
        }
    }

    @Override
    public void onSearchFail() {

    }


    @OnClick({R.id.fragment_word_detail_voice_en_start, R.id.fragment_word_detail_voice_am_start, R.id.fragment_word_detail_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_word_detail_voice_en_start:
                if (mWord.getVoiceList() != null && mWord.getVoiceList().get(0) != null) {
                    Toast.makeText(getContext(), "Speak", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fragment_word_detail_voice_am_start:
                if (mWord.getVoiceList() != null && mWord.getVoiceList().get(1) != null) {
                    Toast.makeText(getContext(), "Speak", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fragment_word_detail_collect:
                Toast.makeText(getContext(), "Collect", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
