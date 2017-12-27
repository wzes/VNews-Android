package com.mobile.vnews.activity.news.detail.comment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mobile.vnews.R;
import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.bean.Comment;
import com.mobile.vnews.module.bean.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 12/21/17.
 */

public class NewsCommentFragment extends Fragment implements NewsCommentContract.View {

    @BindView(R.id.fragment_news_comment_toolbar)
    Toolbar mFragmentNewsCommentToolbar;
    @BindView(R.id.fragment_news_comment_app_bar)
    AppBarLayout mFragmentNewsCommentAppBar;
    @BindView(R.id.fragment_news_comment_recycler_view)
    RecyclerView mFragmentNewsCommentRecyclerView;
    private NewsCommentAdapter mNewsCommentAdapter;
    private List<Comment> mList;

    private BottomSheetDialog mCommentDialog;
    private View mCommentView;
    private TextInputEditText mCommentText;
    private AppCompatButton mCommentSend;

    Unbinder unbinder;
    private NewsCommentContract.Presenter mPresenter;
    private static int newsID;
    private static int floor;
    private static String username;
    @Override
    public void onResume() {
        super.onResume();
        // TODO
        // get username
        mPresenter.load(newsID, floor);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_comment, container, false);
        unbinder = ButterKnife.bind(this, view);
        NewsCommentActivity activity = (NewsCommentActivity) getActivity();
        mFragmentNewsCommentToolbar.setTitle("回复" + username);
        activity.setSupportActionBar(mFragmentNewsCommentToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        setHasOptionsMenu(true);
        return view;
    }

    public static NewsCommentFragment newInstance(int newsID, int floor, String username) {
        NewsCommentFragment.newsID = newsID;
        NewsCommentFragment.floor = floor;
        NewsCommentFragment.username = username;
        return new NewsCommentFragment();
    }

    @Override
    public void setPresenter(NewsCommentContract.Presenter mPresenter) {
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

    @Override
    public void onLikeSuccess() {

    }

    @Override
    public void onLikeFail() {

    }

    @Override
    public void showComments(@NonNull List<Comment> comments) {
        if (mNewsCommentAdapter == null) {
            mList = comments;
            mNewsCommentAdapter = new NewsCommentAdapter(R.layout.comment_item, mList);

            // showDetail
            mNewsCommentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Toast.makeText(getActivity(), "ItemClick item " + position, Toast.LENGTH_SHORT).show();
                }
            });

            // Like and reply
            mNewsCommentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                switch (view.getId()) {
                    case R.id.comment_item_like:
                        try {
                            mPresenter.likeComment(AppPreferences.getLoginUserID(),
                                    mList.get(position).getId());
                            mList.get(position).setLikeCount(mList.get(position).getLikeCount() + 1);
                            mNewsCommentAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.comment_item_reply:
                        if (mCommentDialog == null) {
                            mCommentDialog = new BottomSheetDialog(getActivity());
                            mCommentView = getActivity().getLayoutInflater().inflate(R.layout.sheet_comment, null);
                            mCommentText = mCommentView.findViewById(R.id.sheet_comment_text);
                            mCommentSend = mCommentView.findViewById(R.id.sheet_comment_send);
                            mCommentDialog.setContentView(mCommentView);
                            mCommentSend.setOnClickListener(view1 -> {
                                if (!TextUtils.isEmpty(mCommentText.getText())) {
                                    Message message = new Message();
                                    message.setNewsID(String.valueOf(newsID));
                                    message.setFromID(AppPreferences.getLoginUserID());
                                    message.setFromImage(AppPreferences.getLoginUserImage());
                                    message.setFromUsername(AppPreferences.getLoginUsername());
                                    message.setToID(mList.get(position).getToID());
                                    message.setContent(mCommentText.getText().toString());
                                    mPresenter.comment(message);
                                }
                            });
                        }
                        mCommentText.setHint("回复" + mList.get(position).getFromUsername());
                        mCommentDialog.show();
                        break;
                }
            });
            mFragmentNewsCommentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mFragmentNewsCommentRecyclerView.setAdapter(mNewsCommentAdapter);
        } else {
            mList.clear();
            mList.addAll(comments);
            // load more
            mNewsCommentAdapter.loadMoreComplete();
            mNewsCommentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCommentFail() {
        Toast.makeText(getActivity(), "Server is error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCommentSuccess(Message message) {
        Comment comment = new Comment();
        comment.setId(message.getID());
        comment.setContent(message.getContent());
        comment.setFromID(message.getFromID());
        comment.setFromImage(message.getFromImage());
        comment.setFromUsername(message.getFromUsername());
        comment.setTimestamp(message.getTimestamp());
        mNewsCommentAdapter.addData(comment);
        mNewsCommentAdapter.notifyDataSetChanged();
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
