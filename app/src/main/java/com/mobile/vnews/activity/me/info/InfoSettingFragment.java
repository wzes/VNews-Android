package com.mobile.vnews.activity.me.info;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mobile.vnews.R;
import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.bean.User;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yancy.gallerypick.inter.ImageLoader;
import com.yancy.gallerypick.widget.GalleryImageView;

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

public class InfoSettingFragment extends Fragment implements InfoSettingContract.View {

    @BindView(R.id.fragment_me_setting_toolbar)
    Toolbar mFragmentMeSettingToolbar;
    @BindView(R.id.fragment_me_setting_app_bar)
    AppBarLayout mFragmentMeSettingAppBar;
    @BindView(R.id.me_setting_info_photo)
    ImageView mMeSettingInfoPhoto;
    @BindView(R.id.me_setting_info_photo_layout)
    LinearLayout mMeSettingInfoPhotoLayout;
    @BindView(R.id.me_setting_info_id)
    TextView mMeSettingInfoId;
    @BindView(R.id.me_setting_info_id_layout)
    LinearLayout mMeSettingInfoIdLayout;
    @BindView(R.id.me_setting_info_username)
    TextView meSettingInfoUsername;
    @BindView(R.id.me_setting_info_username_layout)
    LinearLayout mMeSettingInfoUsernameLayout;
    @BindView(R.id.me_setting_info_phone)
    TextView meSettingInfoPhone;
    @BindView(R.id.me_setting_info_phone_layout)
    LinearLayout mMeSettingInfoPhoneLayout;
    @BindView(R.id.me_setting_info_email)
    TextView meSettingInfoEmail;
    @BindView(R.id.me_setting_info_email_layout)
    LinearLayout mMeSettingInfoEmailLayout;
    @BindView(R.id.me_setting_info_sex)
    TextView mMeSettingInfoSex;
    @BindView(R.id.me_setting_info_sex_layout)
    LinearLayout mMeSettingInfoSexLayout;
    @BindView(R.id.me_setting_info_birthday)
    TextView mMeSettingInfoBirthday;
    @BindView(R.id.me_setting_info_birthday_layout)
    LinearLayout mMeSettingInfoBirthdayLayout;
    @BindView(R.id.me_setting_info_motto)
    TextView mMeSettingInfoMotto;
    @BindView(R.id.me_setting_info_motto_layout)
    LinearLayout mMeSettingInfoMottoLayout;
    @BindView(R.id.me_setting_info_brief)
    TextView mMeSettingInfoBrief;
    @BindView(R.id.me_setting_info_brief_layout)
    LinearLayout mMeSettingInfoBriefLayout;
    Unbinder unbinder;
    private InfoSettingContract.Presenter mPresenter;

    private User user;

    public static InfoSettingFragment newInstance() {
        return new InfoSettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_me_info_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        InfoSettingActivity activity = (InfoSettingActivity) getActivity();
        activity.setSupportActionBar(mFragmentMeSettingToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        setHasOptionsMenu(true);
        meSettingInfoUsername.setText(AppPreferences.getLoginUsername());
        mMeSettingInfoId.setText(AppPreferences.getLoginUserID());
        mMeSettingInfoMotto.setText(AppPreferences.getLoginUserMotto());
        RequestOptions myOptions = new RequestOptions()
                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.placeholder);
        Glide.with(getActivity().getApplicationContext()).load(AppPreferences.getLoginUserImage())
                .apply(myOptions).into(mMeSettingInfoPhoto);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.load(AppPreferences.getLoginUserID());
    }


    @Override
    public void setPresenter(InfoSettingContract.Presenter mPresenter) {
        this.mPresenter = checkNotNull(mPresenter);
    }

    @OnClick({R.id.fragment_me_setting_app_bar, R.id.me_setting_info_photo_layout, R.id.me_setting_info_id_layout, R.id.me_setting_info_username_layout, R.id.me_setting_info_phone_layout, R.id.me_setting_info_email_layout, R.id.me_setting_info_sex_layout, R.id.me_setting_info_birthday_layout, R.id.me_setting_info_motto_layout, R.id.me_setting_info_brief_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_me_setting_app_bar:
                break;
            case R.id.me_setting_info_photo_layout:
                requestPermission();
                break;
            case R.id.me_setting_info_id_layout:
                break;
            case R.id.me_setting_info_username_layout:
                break;
            case R.id.me_setting_info_phone_layout:
                break;
            case R.id.me_setting_info_email_layout:
                break;
            case R.id.me_setting_info_sex_layout:
                break;
            case R.id.me_setting_info_birthday_layout:
                break;
            case R.id.me_setting_info_motto_layout:
                break;
            case R.id.me_setting_info_brief_layout:
                break;
        }
    }

    @Override
    public void showResults(@NonNull User user) {
        this.user = user;
        meSettingInfoEmail.setText(user.getEmail());
        meSettingInfoPhone.setText(user.getTelephone());
        mMeSettingInfoBirthday.setText(user.getBirthday());
        mMeSettingInfoBrief.setText(user.getInfo());
        mMeSettingInfoMotto.setText(user.getMotto());
        mMeSettingInfoSex.setText(user.getSex());
    }

    @Override
    public void onFail() {
        Toast.makeText(getActivity(), "Server error", Toast.LENGTH_SHORT).show();
    }

    /**
     * Open
     */
    private void openGallery() {
        // Open Gallery
        GalleryPick.getInstance()
                .setGalleryConfig(new GalleryConfig.Builder()
                .imageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Activity activity, Context context, String path, GalleryImageView galleryImageView, int width, int height) {
                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.centerCrop();
                        Glide.with(context).load(path).apply(requestOptions).into(galleryImageView);
                    }

                    @Override
                    public void clearMemoryCache() {

                    }
                })
                .iHandlerCallBack(new IHandlerCallBack() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(List<String> photoList) {
                        if (photoList.size() > 0) {
                            mPresenter.updatePhoto(photoList.get(0));
                        }
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onError() {

                    }
                })
                .provider("com.yancy.gallerypickdemo.fileprovider")
                .pathList(new ArrayList<>())
                .multiSelect(false)
                .maxSize(9)
                .crop(true)
                .isShowCamera(true)
                .filePath("/Gallery/Pictures")
                .build())
                .open(getActivity());
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            openGallery();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE")) {
            Toast.makeText(getActivity(), "请在 设置-应用管理 中开启此应用的储存授权。", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 2);
        }
    }
}
