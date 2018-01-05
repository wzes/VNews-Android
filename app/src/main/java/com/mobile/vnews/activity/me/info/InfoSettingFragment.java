package com.mobile.vnews.activity.me.info;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
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

    private static User mUser;

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

        AppPreferences.saveLoginUserImage("http://118.89.111.157/vnews/users/013cc199af6c4d56b9d9.jpg");
        refreshPhoto(AppPreferences.getLoginUserImage());
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

    @OnClick({R.id.fragment_me_setting_app_bar, R.id.me_setting_info_photo_layout,
            R.id.me_setting_info_id_layout, R.id.me_setting_info_username_layout,
            R.id.me_setting_info_phone_layout, R.id.me_setting_info_email_layout,
            R.id.me_setting_info_sex_layout, R.id.me_setting_info_birthday_layout,
            R.id.me_setting_info_motto_layout, R.id.me_setting_info_brief_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_me_setting_app_bar:
                break;
            case R.id.me_setting_info_photo_layout:
                requestPermission();
                break;
            case R.id.me_setting_info_id_layout:
                Toast.makeText(getActivity(), "不能更改ID", Toast.LENGTH_SHORT).show();
                break;
            case R.id.me_setting_info_username_layout:
                updateUser("username");
                break;
            case R.id.me_setting_info_phone_layout:
                updateUser("phone");
                break;
            case R.id.me_setting_info_email_layout:
                updateUser("email");
                break;
            case R.id.me_setting_info_sex_layout:
                updateUser("sex");
                break;
            case R.id.me_setting_info_birthday_layout:
                updateUser("birthday");
                break;
            case R.id.me_setting_info_motto_layout:
                updateUser("motto");
                break;
            case R.id.me_setting_info_brief_layout:
                updateUser("brief");
                break;
        }
    }

    @Override
    public void showResults(@NonNull User user) {
        refreshUser(user);
    }

    @Override
    public void onFail() {
        Toast.makeText(getActivity(), "Server error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadSuccess(String filename) {
        AppPreferences.saveLoginUserImage(filename);
        refreshPhoto(filename);
        Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();

    }

    /**
     *
     */
    private void refreshPhoto(String filename) {

        RequestOptions myOptions = new RequestOptions()
                .fitCenter()
                .centerCrop();
        Glide.with(getActivity().getApplicationContext()).load(filename)
                .apply(myOptions).into(mMeSettingInfoPhoto);
    }

    @Override
    public void onUploadFail() {
        Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateSuccess(@NonNull User user) {
        refreshUser(user);
    }

    private void refreshUser(User user) {
        mUser = user;
        meSettingInfoEmail.setText(mUser.getEmail());
        meSettingInfoPhone.setText(mUser.getTelephone());
        mMeSettingInfoBirthday.setText(mUser.getBirthday());
        mMeSettingInfoBrief.setText(mUser.getInfo());
        mMeSettingInfoMotto.setText(mUser.getMotto());
        mMeSettingInfoSex.setText(mUser.getSex());
        // save
        AppPreferences.saveLoginUserID(mUser.getId());
        AppPreferences.saveLoginUsername(mUser.getUsername());
        AppPreferences.saveLoginUserImage(mUser.getImage());
        AppPreferences.saveLoginUserMotto(mUser.getMotto());
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

    /**
     *
     */
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

    private void updateUser(String info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText edit = new EditText(getActivity());
        builder.setView(edit);
        if (info.equals("email")) {
            builder.setTitle("请输入邮箱");
            if (!TextUtils.isEmpty(mUser.getEmail())) {
                edit.setText(mUser.getEmail());
                edit.setSelection(mUser.getEmail().length());
            }
        } else if (info.equals("phone")) {
            builder.setTitle("请输入电话");
            if (!TextUtils.isEmpty(mUser.getTelephone())) {
                edit.setText(mUser.getTelephone());
                edit.setSelection(mUser.getTelephone().length());
            }

        } else if (info.equals("birthday")) {
            builder.setTitle("请输入生日");
            if (!TextUtils.isEmpty(mUser.getBirthday())) {
                edit.setText(mUser.getBirthday());
                edit.setSelection(mUser.getBirthday().length());
            }
        } else if (info.equals("motto")) {
            builder.setTitle("请输入个性签名");
            if (!TextUtils.isEmpty(mUser.getMotto())) {
                edit.setText(mUser.getMotto());
                edit.setSelection(mUser.getMotto().length());
            }
        } else if (info.equals("brief")) {
            builder.setTitle("请输入简介");
            if (!TextUtils.isEmpty(mUser.getInfo())) {
                edit.setText(mUser.getInfo());
                edit.setSelection(mUser.getInfo().length());
            }
        } else if (info.equals("sex")) {
            builder.setTitle("请输入性别");
            if (!TextUtils.isEmpty(mUser.getSex())) {
                edit.setText(mUser.getSex());
                edit.setSelection(mUser.getSex().length());
            }
        } else if (info.equals("username")) {
            builder.setTitle("请输入用户名");
            if (!TextUtils.isEmpty(mUser.getUsername())) {
                edit.setText(mUser.getUsername());
                edit.setSelection(mUser.getUsername().length());
            }
        }
        builder.setPositiveButton("确认", (dialog, which) -> {
            if (info.equals("email")) {
                mUser.setEmail(edit.getText().toString());
            } else if (info.equals("phone")) {
                mUser.setTelephone(edit.getText().toString());
            } else if (info.equals("birthday")) {
                mUser.setBirthday(edit.getText().toString());
            } else if (info.equals("motto")) {
                mUser.setMotto(edit.getText().toString());
            } else if (info.equals("brief")) {
                mUser.setInfo(edit.getText().toString());
            } else if (info.equals("sex")) {
                mUser.setSex(edit.getText().toString());
            } else if (info.equals("username")) {
                mUser.setUsername(edit.getText().toString());
            }
            if (TextUtils.isEmpty(mUser.getEmail())) {
                mUser.setEmail("");
            }
            if (TextUtils.isEmpty(mUser.getSex())) {
                mUser.setSex("");
            }
            if (TextUtils.isEmpty(mUser.getBirthday())) {
                mUser.setBirthday("");
            }
            if (TextUtils.isEmpty(mUser.getInfo())) {
                mUser.setInfo("");
            }
            if (TextUtils.isEmpty(mUser.getPassword())) {
                mUser.setPassword("");
            }
            mPresenter.update(mUser);
        });
        builder.setNegativeButton("取消", (dialog, which) -> {

        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
