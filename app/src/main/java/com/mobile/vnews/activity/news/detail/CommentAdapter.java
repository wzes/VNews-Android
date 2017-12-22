package com.mobile.vnews.activity.news;

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

public class NewsAdapter extends BaseItemDraggableAdapter<News, BaseViewHolder> {

    public NewsAdapter(List<News> data) {
        super(data);
    }

    public NewsAdapter(int layoutResId, List<News> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        helper.setText(R.id.news_item_title, item.getTitle());

        // If the content length more than 30 chars, then show 30 chars
        helper.setText(R.id.news_item_brief, item.getContent());
        // more than 0 then visible
        if (item.getViewCount() > 0) {
            helper.setText(R.id.news_item_view, String.valueOf(item.getViewCount()));
            helper.getView(R.id.news_item_view_layout).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.news_item_view_layout).setVisibility(View.GONE);
        }
        // more than 0 then visible
        if (item.getLikeCount() > 0) {
            helper.setText(R.id.news_item_like, String.valueOf(item.getLikeCount()));
            helper.getView(R.id.news_item_like_layout).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.news_item_like_layout).setVisibility(View.GONE);
        }
        // more than 0 then visible
        if (item.getCommentCount() > 0) {
            helper.setText(R.id.news_item_comment, String.valueOf(item.getCommentCount()));
            helper.getView(R.id.news_item_comment_layout).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.news_item_comment_layout).setVisibility(View.GONE);
        }
        // level
        helper.setText(R.id.news_item_level, item.getLevel());
        // type
        helper.setText(R.id.news_item_category, item.getType());
        // image
        RequestOptions myOptions = new RequestOptions()
                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.placeholder);
        Glide.with(mContext).load(item.getImage()).apply(myOptions).into((ImageView) helper.getView(R.id.news_item_image));
    }
}