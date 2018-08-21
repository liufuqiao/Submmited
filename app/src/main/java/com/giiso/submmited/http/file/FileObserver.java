package com.giiso.submmited.http.file;

import com.giiso.submmited.http.presenter.BaseObserver;
import com.giiso.submmited.http.presenter.BaseView;

import java.io.File;

public abstract class FileObserver extends BaseObserver<String> {

    public FileObserver(BaseView view) {
        super(view);

    }

    @Override
    protected void onStart() {
        if (view != null) {
            //view.showLoadingFileDialog();
        }
    }

    @Override
    public void onComplete() {
        if (view != null) {
            //view.hideLoadingFileDialog();
        }
    }

    @Override
    public void onNext(String path) {
        File file = new File(path);
        if (file != null && file.exists()) {
            onSuccess(file);
        } else {
            onError("file is null or a file does not exist");
        }
    }

    @Override
    public void onSuccess(String o) {

    }


    public abstract void onSuccess(File file);

    public abstract void onError(String msg);

}
