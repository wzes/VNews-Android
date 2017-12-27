package com.mobile.vnews.activity.word.collect;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobile.vnews.R;
import com.mobile.vnews.module.bean.Word;
import com.mobile.vnews.module.bean.WordCollect;

import java.util.List;

/**
 * Created by xuantang on 12/20/17.
 */

public class WordCollectAdapter extends BaseItemDraggableAdapter<WordCollect, BaseViewHolder> {

    public WordCollectAdapter(List<WordCollect> data) {
        super(data);
    }

    public WordCollectAdapter(int layoutResId, List<WordCollect> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WordCollect item) {
        helper.setText(R.id.word_item_title, item.getWord());
        helper.setText(R.id.word_item_means, item.getMeans());
        helper.addOnClickListener(R.id.word_item_like);
    }
}