package com.giiso.submmited.ui.widget.swipRecycleView;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface ItemTouchHelperAdapter {

    //数据交换
    void onItemMove(int fromPosition, int toPosition);
    //数据删除
    void onItemDissmiss(int position);
}
