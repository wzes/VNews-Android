package com.mobile.vnews.activity.intro;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.mobile.vnews.R;
import com.mobile.vnews.activity.main.MainActivity;
import com.mobile.vnews.application.AppPreferences;


public class ActivityIntro extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // not first
        if (!AppPreferences.getLaunchInfo()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            AppPreferences.saveLaunchInfo(false);
        }
        addSlide(AppIntroFragment.newInstance(createSliderPage("title1", "desc1",
                R.drawable.ic_public_black_24dp, getResources().getColor(R.color.colorPrimary))));
        addSlide(AppIntroFragment.newInstance(createSliderPage("title2", "desc2",
                R.drawable.ic_public_black_24dp, getResources().getColor(R.color.colorPrimary))));
        addSlide(AppIntroFragment.newInstance(createSliderPage("title3", "desc3",
                R.drawable.ic_public_black_24dp, getResources().getColor(R.color.colorPrimary))));
        addSlide(AppIntroFragment.newInstance(createSliderPage("title4", "desc4",
                R.drawable.ic_public_black_24dp, getResources().getColor(R.color.colorPrimary))));
        addSlide(AppIntroFragment.newInstance(createSliderPage("title5", "desc5",
                R.drawable.ic_public_black_24dp, getResources().getColor(R.color.colorPrimary))));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }


    /**
     * TODO
     * @param title
     * @param description
     * @param imageDrawable
     * @param bgColor
     * @return
     */
    private SliderPage createSliderPage(String title,
                                        String description,
                                        int imageDrawable,
                                        int bgColor) {
        SliderPage sliderPage = new SliderPage();
        sliderPage.setTitle(title);
        sliderPage.setDescription(description);
        sliderPage.setImageDrawable(imageDrawable);
        sliderPage.setBgColor(bgColor);

        return sliderPage;
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        // TODO: Judge whether we should go to ActivateActivity or MainActivity
//        startActivity(new Intent(this, ActivateActivity.class));
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

}
