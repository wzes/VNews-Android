package com.mobile.vnews.activity.message;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.mobile.vnews.R;
import com.mobile.vnews.activity.news.NewsAdapter;
import com.mobile.vnews.activity.news.detail.NewsDetailActivity;
import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.bean.Message;
import com.mobile.vnews.module.bean.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class MessageFragment extends Fragment implements MessageContract.View {

    @BindView(R.id.fragment_message_toolbar)
    Toolbar mFragmentMessageToolbar;
    @BindView(R.id.fragment_message_app_bar)
    AppBarLayout mFragmentMessageAppBar;
    @BindView(R.id.fragment_message_recycler_view)
    RecyclerView mFragmentMessageRecyclerView;
    @BindView(R.id.fragment_message_empty_view)
    LinearLayout mFragmentMessageEmptyView;
    @BindView(R.id.fragment_message_fresh_layout)
    SwipeRefreshLayout mFragmentMessageFreshLayout;
    Unbinder unbinder;
    private MessageContract.Presenter mPresenter;

    private MessageAdapter mMessageAdapter;
    private List<Message> mList;

    private MessageReceiver messageReceiver;

    public static MessageFragment getInstance() {
        return new MessageFragment();
    }

    // receive message
    public class MessageReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            // get msg
            refresh(intent.getStringExtra("message"));
        }
    }
    private NotificationManager notifyManager;

    /**
     *
     * @param message
     */
    public void refresh(String message) {
        // get msg
        JSONObject jsonObject = JSON.parseObject(message);
        Message msg = JSON.toJavaObject(jsonObject, Message.class);
        mList.add(0, msg);
        mMessageAdapter.notifyDataSetChanged();

        // check if notification is opened
        if (AppPreferences.getNotification()) {
            sendNotification();
        }
    }
    /*
     * Notice
     */
    private void sendNotification() {
        // vibrates
        long[] vibrates = new long[]{0, 500, 1000, 0};
        notifyManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        notifyManager.notify(1, new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("来自VNews的新消息")
                .setVibrate(vibrates)
                .build());
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
    public void setPresenter(MessageContract.Presenter mPresenter) {
        this.mPresenter = checkNotNull(mPresenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppPreferences.getLoginState()) {
            mPresenter.load(AppPreferences.getLoginUserID());
            // register receiver
            if (messageReceiver == null) {
                messageReceiver = new MessageReceiver();
                IntentFilter intentFilter = new IntentFilter("com.mobile.vnews.message");
                getActivity().registerReceiver(messageReceiver, intentFilter);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showResults(@NonNull List<Message> list) {
        if (mFragmentMessageFreshLayout != null) {
            mFragmentMessageFreshLayout.setRefreshing(false);
        }
        if (list.size() == 0) {
            mFragmentMessageEmptyView.setVisibility(View.VISIBLE);
        } else {
            mFragmentMessageEmptyView.setVisibility(View.GONE);
        }
        //
        if (mMessageAdapter == null) {
            mList = list;
            mMessageAdapter = new MessageAdapter(R.layout.message_item, mList);
            // on click
            mFragmentMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mFragmentMessageRecyclerView.setAdapter(mMessageAdapter);

            mFragmentMessageFreshLayout.setOnRefreshListener(() -> {
                if (AppPreferences.getLoginState()) {
                    mPresenter.load(AppPreferences.getLoginUserID());
                } else {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            });
            ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mMessageAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
            itemTouchHelper.attachToRecyclerView(mFragmentMessageRecyclerView);


            // swipe
            mMessageAdapter.enableSwipeItem();
            mMessageAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
                @Override
                public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                    Toast.makeText(getActivity(), "滑动删除", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

                }

                @Override
                public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                    mPresenter.removeMessage(list.get(pos));
                }

                @Override
                public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

                }
            });

        } else {
            mList.clear();
            mList.addAll(list);
            // load more
            mMessageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onShowFail() {
        mFragmentMessageEmptyView.setVisibility(View.VISIBLE);
        mFragmentMessageFreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (AppPreferences.getLoginState()) {
            getActivity().unregisterReceiver(messageReceiver);
        }
    }
}
