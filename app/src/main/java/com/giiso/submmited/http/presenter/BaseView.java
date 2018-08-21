package com.giiso.submmited.http.presenter;

import com.giiso.submmited.http.ResultResponse;

/**
 * Created by jayce on 2017/11/30.
 */

public interface BaseView
{
    /**
     * function : 加载时,显示加载框
     * author : jayce
     * createTime : 2017/6/8 10:45
     */
    void showLoading();

    /**
     *function : 关闭加载框
     * author ：jayce
     * createTime : 2017/12/07 9:34
     */
    void closeLoading();

    /**
     * function : 加载成功,显示视图内容
     * author : jayce
     * createTime : 2017/6/8 10:45
     */
    void showContent();

    /**
     * function : 访问接口失败
     * author : jayce
     * create
     */
    void onErrorCode(ResultResponse resultResponse);

    /**
     * 显示下载文件dialog
     */

    void showLoadingFileDialog();

    /**
     * 隐藏下载文件dialog
     */

    void hideLoadingFileDialog();

    /**
     * 下载进度
     *
     * @param totalSize
     * @param downSize
     */

    void onProgress(long totalSize, long downSize);

}
