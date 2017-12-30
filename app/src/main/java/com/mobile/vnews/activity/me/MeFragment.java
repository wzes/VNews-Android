package com.mobile.vnews.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mobile.vnews.R;
import com.mobile.vnews.activity.login.LoginActivity;
import com.mobile.vnews.activity.me.comment.MeCommentActivity;
import com.mobile.vnews.activity.me.news.NewsMeActivity;
import com.mobile.vnews.activity.me.setting.SettingActivity;
import com.mobile.vnews.application.AppPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class MeFragment extends Fragment implements MeContract.View {

    @BindView(R.id.fragment_me_toolbar)
    Toolbar mFragmentMeToolbar;
    @BindView(R.id.fragment_me_app_bar)
    AppBarLayout mFragmentMeAppBar;
    @BindView(R.id.my_image)
    CircleImageView mMyImage;
    @BindView(R.id.fragment_me_username)
    TextView mFragmentMeUsername;
    @BindView(R.id.fragment_me_motto)
    TextView mFragmentMeMotto;
    @BindView(R.id.fragment_me_login_in_layout)
    RelativeLayout mFragmentMeLoginInLayout;
    @BindView(R.id.fragment_me_login_out_layout)
    RelativeLayout mFragmentMeLoginOutLayout;
    @BindView(R.id.fragment_me_login_layout)
    LinearLayout mFragmentMeLoginLayout;
    @BindView(R.id.me_news_collect_num)
    TextView mMeNewCollectNum;
    @BindView(R.id.me_news_collect_layout)
    LinearLayout mMeNewsCollectLayout;
    @BindView(R.id.me_comment_num)
    TextView mMeCommentNum;
    @BindView(R.id.me_comment_layout)
    LinearLayout mMeCommentLayout;
    @BindView(R.id.me_settings_layout)
    LinearLayout mMeSettingsLayout;
    @BindView(R.id.fragment_me_login)
    Button mFragmentMeLogin;
    Unbinder unbinder;
    @BindView(R.id.me_news_track_num)
    TextView mMeNewsTrackNum;
    @BindView(R.id.me_news_track_layout)
    LinearLayout mMeNewsTrackLayout;

    private MeContract.Presenter mPresenter;

    public static MeFragment getInstance() {
        return new MeFragment();
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
    public void setPresenter(MeContract.Presenter mPresenter) {
        this.mPresenter = checkNotNull(mPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.load();
    }

    @OnClick({R.id.fragment_me_login_in_layout, R.id.me_news_collect_layout, R.id.me_comment_layout,
            R.id.me_settings_layout, R.id.fragment_me_login, R.id.me_news_track_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_me_login_in_layout:
                break;
            case R.id.me_news_collect_layout:
                if (AppPreferences.getLoginState()) {
                    Intent intentLike = new Intent(getContext(), NewsMeActivity.class);
                    intentLike.putExtra("title", "like");
                    startActivity(intentLike);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.me_news_track_layout:
                if (AppPreferences.getLoginState()) {
                    Intent intentTrack = new Intent(getContext(), NewsMeActivity.class);
                    intentTrack.putExtra("title", "view");
                    startActivity(intentTrack);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.me_comment_layout:
                if (AppPreferences.getLoginState()) {
                    Intent intentComment = new Intent(getContext(), MeCommentActivity.class);
                    startActivity(intentComment);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.me_settings_layout:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.fragment_me_login:
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }

    @Override
    public void show(boolean loginState) {
        if (loginState) {
            mFragmentMeLoginInLayout.setVisibility(View.VISIBLE);
            mFragmentMeLoginOutLayout.setVisibility(View.GONE);
            mFragmentMeUsername.setText(AppPreferences.getLoginUsername());
            mFragmentMeMotto.setText(AppPreferences.getLoginUserMotto());
            RequestOptions myOptions = new RequestOptions()
                    .fitCenter()
                    .centerCrop()
                    .placeholder(R.drawable.placeholder);
            Glide.with(getActivity().getApplicationContext()).load(AppPreferences.getLoginUserImage())
                    .apply(myOptions).into(mMyImage);
        } else {
            mFragmentMeLoginInLayout.setVisibility(View.GONE);
            mFragmentMeLoginOutLayout.setVisibility(View.VISIBLE);
        }
    }

}
