package com.giiso.submmited.http.file;


import com.giiso.submmited.base.Constants;
import com.giiso.submmited.http.presenter.BasePresenter;
import com.giiso.submmited.http.retrofit.RetrofitServiceManager;
import com.giiso.submmited.ui.api.SubmmitedApi;

import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 作者： LiuRiZhao
 * 时间： 2018/07/27.13:56
 * 描述： 文件上传、下载
 * 来源：
 */


public class FilePresenter extends BasePresenter<DownFileView> {
    private SubmmitedApi apiServer;
    public FilePresenter(DownFileView baseView) {
        super(baseView);
        apiServer = RetrofitServiceManager.getInstance().create(SubmmitedApi.class);
    }

    public void uploadFile(String picPath) {
        File file = new File(picPath);
        //  图片参数
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);
        //  字符参数
        //RequestBody phoneBody = RequestBody.create(MediaType.parse("multipart/form-data"), phone);

    }

    public void downFile(final String url, final String path) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        return response.newBuilder().body(new ProgressResponseBody(response.body(),
                                new ProgressResponseBody.ProgressListener() {
                                    @Override
                                    public void onProgress(long totalSize, long downSize) {
                                        mView.onProgress(totalSize, downSize);
                                    }
                                })).build();
                    }
                }).build();

        Retrofit retrofit = new Retrofit.Builder().client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.REQUEST_URL).build();

        apiServer = retrofit.create(SubmmitedApi.class);
//        apiServer.downloadFile(url)
//                .map(new Function<ResponseBody, String>() {
//                    @Override
//                    public String apply(ResponseBody responseBody) throws Exception {
//                        File file = FileUtil.writeFile(path, responseBody.byteStream());
//                        return file != null ? file.getPath() : null;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new FileObserver(mView) {
//                    @Override
//                    public void onSuccess(File file) {
//                        mView.onSuccess(file);
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        mView.onError(msg);
//                    }
//                });

    }


}
