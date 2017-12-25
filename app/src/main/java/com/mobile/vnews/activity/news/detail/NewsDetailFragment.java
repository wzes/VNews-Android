package com.mobile.vnews.activity.news.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mobile.vnews.R;
import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.bean.Comment;
import com.mobile.vnews.module.bean.Message;
import com.mobile.vnews.module.bean.News;
import com.mobile.vnews.module.bean.Word;
import com.mobile.vnews.util.TimeUtils;
import com.mobile.vnews.util.select.WordSelectedTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 12/21/17.
 */

public class NewsDetailFragment extends Fragment implements NewsDetailContract.View {

    @BindView(R.id.news_details_image)
    ImageView mNewsDetailsImage;
    @BindView(R.id.news_details_toolbar)
    Toolbar mNewsDetailsToolbar;
    @BindView(R.id.news_details_toolbar_layout)
    CollapsingToolbarLayout mNewsDetailsToolbarLayout;
    @BindView(R.id.news_details_app_bar)
    AppBarLayout mNewsDetailsAppBar;
    @BindView(R.id.news_detail_source)
    TextView mNewsDetailSource;
    @BindView(R.id.news_detail_source_layout)
    LinearLayout mNewsDetailSourceLayout;
    @BindView(R.id.news_detail_content)
    WordSelectedTextView mNewsDetailContent;
    @BindView(R.id.news_details_nested_scroll_view)
    NestedScrollView mNewsDetailsNestedScrollView;
    @BindView(R.id.news_details_coordinator_layout)
    CoordinatorLayout mNewsDetailsCoordinatorLayout;
    Unbinder unbinder;
    @BindView(R.id.news_detail_description)
    TextView mNewsDetailDescription;
    @BindView(R.id.news_detail_date)
    TextView mNewsDetailDate;
    @BindView(R.id.news_detail_comment_recycler_view)
    RecyclerView mNewsDetailCommentRecyclerView;
    @BindView(R.id.news_detail_comment)
    TextView mNewsDetailComment;
    @BindView(R.id.news_detail_date_layout)
    LinearLayout newsDetailDateLayout;
    @BindView(R.id.news_detail_comment_layout)
    LinearLayout mNewsDetailCommentLayout;
    private CommentAdapter mCommentAdapter;
    private List<Comment> mList;

    private AppCompatTextView mSheetWordTitle;
    private AppCompatTextView mSheetWordVoice;
    private AppCompatTextView mSheetWordDetail;
    private AppCompatImageButton mSheetWordVoiceStart;
    private AppCompatTextView mSheetWordMeans;
    private AppCompatButton mSheetWordCollect;
    private BottomSheetDialog mWordDialog;

    private BottomSheetDialog mCommentDialog;
    private TextInputEditText mCommentText;
    private AppCompatButton mCommentSend;


    private View mWordView;
    private View mCommentView;

    private NewsDetailContract.Presenter mPresenter;
    private static int newID;

    @Override
    public void onResume() {
        super.onResume();

        // TODO
        // get username
        mPresenter.view(AppPreferences.getLoginUserID(), newID);
        mPresenter.load(newID);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        NewsDetailActivity activity = (NewsDetailActivity) getActivity();
        activity.setSupportActionBar(mNewsDetailsToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        mNewsDetailsToolbar.setOnClickListener(v -> mNewsDetailsNestedScrollView.smoothScrollTo(0, 0));
        mNewsDetailCommentLayout.setVisibility(View.GONE);
        setHasOptionsMenu(true);
        return view;
    }

    public static NewsDetailFragment newInstance(int newID) {
        NewsDetailFragment.newID = newID;
        return new NewsDetailFragment();
    }

    @Override
    public void setPresenter(NewsDetailContract.Presenter mPresenter) {
        this.mPresenter = checkNotNull(mPresenter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
        }
        return true;
    }

    public void setTitle(@NonNull String title) {
        setCollapsingToolbarLayoutTitle(title);
    }

    // to change the title's font size of toolbar layout
    private void setCollapsingToolbarLayoutTitle(String title) {
        mNewsDetailsToolbarLayout.setTitle(title);
        mNewsDetailsToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mNewsDetailsToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mNewsDetailsToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        mNewsDetailsToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }

    @Override
    public void showResults(@NonNull News news) {
        setTitle(news.getTitle());
        mNewsDetailContent.setText(news.getContent(), TextView.BufferType.SPANNABLE);
        mNewsDetailSource.setText(news.getSource());
        mNewsDetailDescription.setText(news.getDescription());
        mNewsDetailDate.setText(TimeUtils.millis2String(news.getPublishedAt()));

        Glide.with(getActivity()).load(news.getImage()).into(mNewsDetailsImage);
        mNewsDetailCommentLayout.setVisibility(View.VISIBLE);

        // send text
        mNewsDetailComment.setOnClickListener(view -> {
            if (!AppPreferences.getLoginState()){
                Toast.makeText(getActivity(), "Please Login", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mCommentDialog == null) {
                mCommentDialog = new BottomSheetDialog(getActivity());
                mCommentView = getActivity().getLayoutInflater().inflate(R.layout.sheet_comment, null);
                mCommentText = mCommentView.findViewById(R.id.sheet_comment_text);
                mCommentSend = mCommentView.findViewById(R.id.sheet_comment_send);
                mCommentDialog.setContentView(mCommentView);
                mCommentSend.setOnClickListener(view1 -> {
                    if (!TextUtils.isEmpty(mCommentText.getText())) {
                        Message message = new Message();
                        message.setNewsID(String.valueOf(news.getID()));
                        message.setFromID(AppPreferences.getLoginUserID());
                        message.setFromImage(AppPreferences.getLoginUserImage());
                        message.setFromUsername(AppPreferences.getLoginUsername());
                        message.setTitle(news.getTitle());
                        message.setContent(mCommentText.getText().toString());
                        mPresenter.comment(message);
                    }
                });
            }
            mCommentDialog.show();
        });
        mNewsDetailContent.setOnWordSelectedClickListener(word -> mPresenter.search(word));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showWord(@NonNull Word word) {
        if (mWordDialog == null) {
            mWordDialog = new BottomSheetDialog(getActivity());
            mWordView = getActivity().getLayoutInflater().inflate(R.layout.sheet_word_details, null);
            mSheetWordTitle = mWordView.findViewById(R.id.sheet_word_title);
            mSheetWordCollect = mWordView.findViewById(R.id.sheet_word_collect);
            mSheetWordMeans = mWordView.findViewById(R.id.sheet_word_means);
            mSheetWordVoice = mWordView.findViewById(R.id.sheet_word_voice);
            mSheetWordDetail = mWordView.findViewById(R.id.sheet_word_detail);
            mSheetWordVoiceStart = mWordView.findViewById(R.id.sheet_word_voice_start);
            mWordDialog.setContentView(mWordView);
            mWordDialog.setOnDismissListener(dialogInterface -> mNewsDetailContent.dismissSelected());
        }

        mSheetWordTitle.setText(word.getWord());
        if (word.getVoiceList() != null && word.getVoiceList().size() > 0) {
            mSheetWordVoice.setText(word.getVoiceList().get(0).getPhonogram());
        }
        if (word.getPosList() != null && word.getPosList().size() > 0) {
            mSheetWordMeans.setText(word.getPosList().get(0).getSymbol() + word.getPosList().get(0).getMeans());
        }
        mSheetWordCollect.setOnClickListener(view1 -> Toast.makeText(getActivity(), "Collect", Toast.LENGTH_SHORT).show());
        mSheetWordVoiceStart.setOnClickListener(view12 -> Toast.makeText(getActivity(), "Speak", Toast.LENGTH_SHORT).show());
        mSheetWordDetail.setOnClickListener(view -> Toast.makeText(getActivity(), "Detail", Toast.LENGTH_SHORT).show());
        mWordDialog.show();
    }

    @Override
    public void onShowFail() {

    }

    @Override
    public void onSearchFail() {
        // Toast.makeText(getActivity(), "没找到～～", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLikeSuccess() {

    }

    @Override
    public void onLikeFail() {

    }

    @Override
    public void showComments(@NonNull List<Comment> comments) {
        if (mCommentAdapter == null) {
            mList = comments;
            mCommentAdapter = new CommentAdapter(R.layout.comment_item, mList);

            // showDetail
            mCommentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Toast.makeText(getActivity(), "ItemClick item " + position, Toast.LENGTH_SHORT).show();
                }
            });

            // Like and reply
            mCommentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                switch (view.getId()) {
                    case R.id.comment_item_like:
                        break;
                    case R.id.comment_item_reply:
                        break;
                }
                Toast.makeText(getActivity(), "ItemChildClick item " + position, Toast.LENGTH_SHORT).show();
            });
            mNewsDetailCommentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mNewsDetailCommentRecyclerView.setAdapter(mCommentAdapter);
        } else {
            mList.clear();
            mList.addAll(comments);
            // load more
            mCommentAdapter.loadMoreComplete();
            mCommentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCommentFail() {
        Toast.makeText(getActivity(), "Server is error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCommentSuccess(Message message) {
        Comment comment = new Comment();
        comment.setID(message.getID());
        comment.setContent(message.getContent());
        comment.setFromID(message.getFromID());
        comment.setFromImage(message.getFromImage());
        comment.setFromUsername(message.getFromUsername());
        comment.setTimestamp(message.getTimestamp());
        mCommentAdapter.addData(comment);
        mCommentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLogin() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
