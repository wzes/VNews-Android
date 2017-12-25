package com.mobile.vnews.activity.word.detail;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobile.vnews.R;
import com.mobile.vnews.module.bean.News;

import java.util.List;

/**
 * Created by xuantang on 12/20/17.
 */

public class WordDetailAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {

    public WordDetailAdapter(List<String> data) {
        super(data);
    }

    public WordDetailAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.word_detail_item_info, item);
    }
}