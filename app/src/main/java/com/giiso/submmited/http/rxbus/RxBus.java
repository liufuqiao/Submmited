package com.giiso.submmited.http.rxbus;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * RxBus 取代EventBus 异步更新 基于RxAndroid
 * Created by Administrator on 2018/7/9.
 */

public class RxBus {

    private static volatile RxBus mInstance;
    private final Subject<Object> subject = PublishSubject.create().toSerialized();
    private Disposable dispoable;

    private RxBus() {
    }

    public static RxBus getInstance() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                if (mInstance == null) {
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }


    /**
     * 发送事件
     *
     * @param object
     */
    public void send(Object object) {
        subject.onNext(object);
    }


    /**
     * @param classType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservale(Class<T> classType) {
        return subject.ofType(classType);
    }


    /**
     * 订阅
     *
     * @param bean
     * @param consumer
     */
    public void subscribe(Class bean, Consumer consumer) {
        dispoable = toObservale(bean).subscribe(consumer);
    }

    /**
     * 取消订阅
     */
    public void unSubcribe() {
        if (dispoable != null && dispoable.isDisposed()) {
            dispoable.dispose();
        }

    }

    //监听事件
//    RxBus.getInstance().subscribe(String.class, new Consumer<String>() {
//        @Override
//        public void accept(String path) throws Exception {
//
//        }
//    });
    //发送监听事件：
    // RxBus.getInstance().send("发送事件");
    //在退出Activity(Fragment)时,取消监听:
    // RxBus.getInstance().unSubcribe();

}
