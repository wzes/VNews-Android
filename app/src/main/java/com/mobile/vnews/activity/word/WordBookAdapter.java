package com.mobile.vnews.activity.word;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobile.vnews.R;

import java.util.List;

/**
 * Created by xuantang on 12/20/17.
 */

public class WordBookAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {

    public WordBookAdapter(List<String> data) {
        super(data);
    }

    public WordBookAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.word_book_item_title, getString(item));
    }

    /**
     *
     * @param source
     * @return
     */
    public int getString(String source) {
        switch (source) {
            case "收藏":
                return R.string.word_book_title;
            case "生词":
                return R.string.word_unknown;
            case "熟记":
                return R.string.word_know;
            default:
                return R.string.word_book_title;
        }
    }
}