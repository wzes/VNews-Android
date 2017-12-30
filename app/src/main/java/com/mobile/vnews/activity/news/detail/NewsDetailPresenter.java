package com.mobile.vnews.activity.news.detail;

import android.annotation.SuppressLint;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.Comment;
import com.mobile.vnews.module.bean.Message;
import com.mobile.vnews.module.bean.News;
import com.mobile.vnews.module.bean.Word;
import com.mobile.vnews.module.bean.WordCollect;
import com.mobile.vnews.module.dao.MessageDao;
import com.mobile.vnews.module.dao.WordDao;
import com.mobile.vnews.module.database.AppDatabase;
import com.mobile.vnews.module.parse.WordParser;
import com.mobile.vnews.service.Api;
import com.mobile.vnews.service.DefaultObserver;
import com.mobile.vnews.service.client.MessageService;
import com.mobile.vnews.util.Utils;
import com.mobile.vnews.util.word.WordLemma;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by xuantang on 12/21/17.
 */

public class NewsDetailPresenter implements NewsDetailContract.Presenter {

    private static final String TAG = NewsDetailPresenter.class.getSimpleName();

    private NewsDetailFragment mFragment;

    private Word mWord;

    public NewsDetailPresenter(NewsDetailFragment mFragment) {
        this.mFragment = mFragment;
        mFragment.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void load(int newsID) {
        // Get news
        if (AppPreferences.getLoginState()) {
            Api.getApiService().getNewsDetailByUserID(AppPreferences.getLoginUserID(), newsID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<BasicResponse<News>>(mFragment.getActivity(), true) {
                        @Override
                        public void onSuccess(BasicResponse<News> response) {
                            mFragment.showResults(response.getContent());
                        }

                        @Override
                        public void onFail(BasicResponse<News> response) {
                            mFragment.onShowFail();
                        }
                    });
            // Get comments
            Api.getApiService().getNewsCommentsByUserID(AppPreferences.getLoginUserID(), String.valueOf(newsID))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<BasicResponse<List<Comment>>>(mFragment.getActivity(), true) {
                        @Override
                        public void onSuccess(BasicResponse<List<Comment>> response) {
                            mFragment.showComments(response.getContent());
                        }

                        @Override
                        public void onFail(BasicResponse<List<Comment>> response) {
                            mFragment.onShowFail();
                        }
                    });
        } else {
            Api.getApiService().getNewsDetail(newsID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<BasicResponse<News>>(mFragment.getActivity(), true) {
                        @Override
                        public void onSuccess(BasicResponse<News> response) {
                            mFragment.showResults(response.getContent());
                        }

                        @Override
                        public void onFail(BasicResponse<News> response) {
                            mFragment.onShowFail();
                        }
                    });
            // Get comments
            Api.getApiService().getNewsComments(String.valueOf(newsID))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<BasicResponse<List<Comment>>>(mFragment.getActivity(), true) {
                        @Override
                        public void onSuccess(BasicResponse<List<Comment>> response) {
                            mFragment.showComments(response.getContent());
                        }

                        @Override
                        public void onFail(BasicResponse<List<Comment>> response) {
                            mFragment.onShowFail();
                        }
                    });
        }
    }

    @Override
    public void search(String word) {
        new Thread(() -> {
            AppDatabase appDatabase = AppDatabase.getDatabase(Utils.getContext());
            WordDao wordDao = appDatabase.getWordDao();



            List<Word> words = wordDao.getWordsByName(WordLemma.get(word));

            if (words.size() == 0) {
                words = wordDao.getWordsByName(word.toLowerCase());
                if (words.size() == 0) {
                    words = wordDao.getWordsByName(WordLemma.get(word));
                }
            }

            if (words.size() != 0) {
                try {
//                    Log.i(TAG, "search: ---------------"  + word + " " + words.size() + "\n"
//                            + JSON.toJSONString(words.get(0)) + "\n" +
//                    words.get(0).getVoice());
                    WordParser wordParser = new WordParser();
                    mWord = wordParser.parse(words);
                    Log.i(TAG, "search: " + JSON.toJSONString(mWord));
                } catch (Exception e) {
                    handler.sendEmptyMessage(1);
                    return;
                }
                if (mWord != null) {
                    handler.sendEmptyMessage(0);
                } else {
                    handler.sendEmptyMessage(1);
                }
            } else {
                mFragment.onSearchFail();
            }

        }).start();
    }

    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    mFragment.showWord(mWord);
                    break;
                case 1:
                    mFragment.onSearchFail();
                    break;
            }
        }
    };


    @Override
    public void like(String userID, int newID) {
        // json
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", userID);
        map.put("news_id", String.valueOf(newID));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(map));

        Api.getApiService().likeNews(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(mFragment.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        // TODO
                        Log.i(TAG, "onSuccess: ");
                    }
                });
    }

    @Override
    public void likeComment(String userID, int comment_id) {
        Api.getApiService().likeComment(userID, comment_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(mFragment.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        // TODO
                        Log.i(TAG, "onSuccess: ");
                    }
                });
    }

    @Override
    public void dislikeComment(String userID, int comment_id) {
        Api.getApiService().cancelLikeComment(userID, comment_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(mFragment.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        // TODO
                        Log.i(TAG, "onSuccess: ");
                    }
                });
    }

    @Override
    public void dislikeNews(String userID, int news_id) {
        Api.getApiService().cancelLikeNews(userID, news_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(mFragment.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        // TODO
                        Log.i(TAG, "onSuccess: ");
                    }
                });
    }

    @Override
    public void comment(Message message) {
        try {
            MessageService.getMessageClient().sendMessage(message);
            new Thread(() -> {
                AppDatabase appDatabase = AppDatabase.getDatabase(Utils.getContext());
                MessageDao messageDao = appDatabase.getMessageDao();
                int size = messageDao.getSize();
                message.setId(size + 1);
                messageDao.addMessage(message);
            }).start();
            mFragment.onCommentSuccess(message);
        } catch (Exception e) {
            e.printStackTrace();
            mFragment.onCommentFail();
        }
    }

    @Override
    public void view(String userID, int newID) {
        // json
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", userID);
        map.put("news_id", String.valueOf(newID));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(map));
        Api.getApiService().viewNews(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(mFragment.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        Log.i(TAG, "onSuccess: ");
                    }
                });
    }

    @Override
    public void addWordCollect(WordCollect wordCollect) {
        new Thread(() -> {
            AppDatabase appDatabase = AppDatabase.getDatabase(Utils.getContext());
            WordDao wordDao = appDatabase.getWordDao();
            wordDao.addWordCollect(wordCollect);
        }).start();
    }
}
