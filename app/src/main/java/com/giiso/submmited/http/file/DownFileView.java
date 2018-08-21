package com.giiso.submmited.http.file;


import com.giiso.submmited.http.presenter.BaseView;

import java.io.File;

public interface DownFileView extends BaseView {

    void onSuccess(File file);

    void onError(String msg);

}
