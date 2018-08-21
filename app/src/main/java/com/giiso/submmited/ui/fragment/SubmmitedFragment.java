package com.giiso.submmited.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.giiso.submmited.R;
import com.giiso.submmited.http.rxbus.RxBusMessage;
import com.giiso.submmited.bean.Menus;
import com.giiso.submmited.http.rxbus.RxBus;
import com.giiso.submmited.ui.LoaderActivity;
import com.giiso.submmited.ui.base.fragment.BaseFragment;
import com.giiso.submmited.ui.fragment.adapter.SitesAdapter;
import com.giiso.submmited.ui.fragment.child.TaskFragment;
import com.giiso.submmited.ui.fragment.presenter.MenuPresenter;
import com.giiso.submmited.ui.fragment.presenter.MenuView;
import com.giiso.submmited.ui.widget.tablayout.SlidingTabLayout;
import com.giiso.submmited.utils.DialogUtil;
import com.giiso.submmited.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 报工管理
 * Created by LiuRiZhao on 2018/8/9.
 */

public class SubmmitedFragment extends BaseFragment<MenuPresenter> implements MenuView {

    @BindView(R.id.layout_tab)
    SlidingTabLayout layoutTab;
    @BindView(R.id.ll_no_network)
    LinearLayout llNoNetwork;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private FragmentPagerAdapter mFragmentAdapter;
    private List<String> tabs;

    public static SubmmitedFragment newInstance() {
        return new SubmmitedFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected MenuPresenter getPresenter() {
        return new MenuPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();

        tabs = new ArrayList<>();
        tabs.add("任务管理");
        tabs.add("日报列表");
        tabs.add("异常任务");
        if (NetworkUtils.isConnected()) {
            yesNetwork();
        } else {
            noNetwork();
        }
        initViewPager();
        mPresenter.getMenuList();
        RxBus.getInstance().subscribe(String.class, new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
                if(o.equals(RxBusMessage.MENU)){
                    mPresenter.getMenuList();
                }
            }
        });
    }

    private void initViewPager() {
        mFragmentAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return TaskFragment.newInstance();
            }

            @Override
            public int getCount() {
                return tabs.isEmpty() ? 0 : tabs.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                int index = 0;
                if (tabs.size() != 0 && position != 0) {
                    index = position % tabs.size();
                }
                return tabs.get(index);
            }

            @Override
            public int getItemPosition(Object object) {
                return PagerAdapter.POSITION_NONE;
            }
        };
        viewPager.setAdapter(mFragmentAdapter);
        layoutTab.setIndicatorColorId(R.color.colorPrimary);
        layoutTab.setTextSelectColorId(R.color.colorPrimary);
        layoutTab.setTextUnselectColorId(R.color.color_9b9b9b);
        layoutTab.setViewPager(viewPager);
        layoutTab.setmCurrentTab(0);
        layoutTab.onPageSelected(0);
        layoutTab.setSmoothScrollingEnabled(true);
    }

    @Override
    public void noNetwork() {
        super.noNetwork();
        llNoNetwork.setVisibility(View.VISIBLE);
    }

    @Override
    public void yesNetwork() {
        super.yesNetwork();
        llNoNetwork.setVisibility(View.GONE);
    }

    private SitesAdapter sitesAdapter;
    private List<Object> items = new ArrayList<>();
    private void initRecyclerView(List<Menus> menusList){
        if(menusList.isEmpty()){
            return;
        }
        //数据获取之后  将数据循环遍历，放进items集合中，至于服务器返回什么格式的数据，我想看下实体类就应该明白了
        for (int i=0; i < menusList.size(); i++){
            items.add(menusList.get(i));
            items.addAll(menusList.get(i).getChildMenus());
        }
        //实例化适配器将遍历好的数据放进适配器中
        sitesAdapter = new SitesAdapter(getActivity() ,items);
        //new一个布局管理器，这里是用GridLayoutManager，要区分3列
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity() , 3);//多少列，如果数据项只需要1列，这里写1，下面return 也返回1即可实现
        //下面这个方法很重要，根据position获取当前这条数据是标题还是数据项，来设置他的跨列
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //适配器中有这么一个方法，根据position获取当前这条数据是标题还是数据项，来设置他的跨列
                switch (sitesAdapter.getItemViewType(position)){
                    case SitesAdapter.TITLE://标题的话跨多少列 这个值要跟整个列数相等 如果大于会出错，小于布局会乱
                        return 3;
                    case SitesAdapter.ITEM://数据项
                        return 1;//不跨列，就是分成三列显示
                    default:
                        return -1;
                }
            }
        });
        recyclerview.setLayoutManager(gridLayoutManager);
        recyclerview.setAdapter(sitesAdapter);
        //item的点击事件，这里实现，进行具体的操作
        sitesAdapter.setOnItemClickListener(new SitesAdapter.OnItemClickListener() {
            @Override
            public void onClick(View itemview, int position) {
                switch (sitesAdapter.getItemViewType(position)){
                    case SitesAdapter.ITEM:
                        Menus.MenuItem item = (Menus.MenuItem) items.get(position);
                        LoaderActivity.show(mContext, item);
                        break;
                    case SitesAdapter.TITLE:
                        break;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubcribe();
    }

    @Override
    public void showLoading() {
        super.showLoading();
        DialogUtil.showProgressDialog(mContext);
    }

    @Override
    public void closeLoading() {
        super.closeLoading();
        DialogUtil.dismissDialog();
    }

    @Override
    public void showResult(List<Menus> menus) {
        initRecyclerView(menus);
    }
}
