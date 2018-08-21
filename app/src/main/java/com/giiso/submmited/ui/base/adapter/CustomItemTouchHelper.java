package com.giiso.submmited.ui.base.adapter;

import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Administrator on 2017/12/28.
 */

public class CustomItemTouchHelper extends ItemTouchHelper {

    public CustomItemTouchHelper(CustomItemTouchHelperCallback.OnItemTouchCallbackListener onItemTouchCallbackListener) {
        //直接把回调交给父类处理就好
        super(new CustomItemTouchHelperCallback(onItemTouchCallbackListener));
    }
}