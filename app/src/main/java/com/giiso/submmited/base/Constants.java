package com.giiso.submmited.base;

import com.giiso.submmited.BuildConfig;

/**
 * 应用常量类
 * Created by LiuRiZhao on 2018/7/9.
 */

public class Constants {

    // 服务端返回错误吗
    public static final String RESPONSE_SUCCESS_CODE = "0";
    public static final String RESPONSE_FAILRE_CODE = "1";
    public static final String RESPONSE_CODE_SERVICE_EXCEPTION = "";

    public static final String USERNAME = "userName";
    public static final String PASSWORD = "password";
    public static final String USER_ID = "userId";
    public static final String USRE = "user";
    public static final String COOKIE = "cookie";

    // 请求服务地址
    private static final String DEVELOP_HTTP_URL = "http://172.16.21.56:8080/";
    private static final String RELEASE_HTTP_URL = "http://172.16.21.56:8080/";

    //处理请求地址切换,打包选择决定请求地址 (开发中直接安装为默认DEBUG模式)
    public static final String REQUEST_URL = BuildConfig.DEBUG ? DEVELOP_HTTP_URL : RELEASE_HTTP_URL;

    public static final String LOGIN = "/tms/manage/user/login";
    public static final String LOGIN_OUT = "/tms/manage/user/logout";
    //菜单列表
    public static final String MENU_LIST = "/tms/user/menu/list";
    //选择项目列表
    public static final String PROJECT_LIST = "/tms/owner/select_project_list";
    //个人项目列表
    public static final String SINGLE_PROJECT_LIST = "/tms/owner/project_list";
    //全部项目处理列表
    public static final String SELECT_ALL_TASK = "/tms/employee/select_all_task";
    //任务列表启动、暂停按钮
    public static final String START_BREAK_TASK = "/tms/employee/start_break_task";
    //任务列表启动按钮
    public static final String CONFIRM_TASK = "/tms/employee/confirm_task";
    //添加任务
    public static final String ADD_TASK = "/tms/employee/add_all_task";
    //修改任务
    public static final String UPDATE_TASK = "/tms/employee/update_all_task";
    //添加日报
    public static final String ADD_SUBMMITED = "/tms/employee/add_task";
    //修改日报
    public static final String UPDATE_SUBMMITED = "/tms/employee/update_task";
    //删除任务
    public static final String DELETE_TASK = "/tms/employee/del_task";
    //获取指派人列表
    public static final String EMPLOYEE_LIST = "/tms/employee/employee_list";
    //报工列表
    public static final String SUBMMITED_LIST = "/tms/employee/select_task_list";
    //异常任务列表
    public static final String UNUSUSAL_LIST = "/tms/employee/select_unususal_task";

    public static final String REGISTER = "/ac/reg/by_mobile";

    public static final int PAGE_SIZE = 15;// 默认分页大小
    public static final String TOPIC_CACHE= "topic_cache";

}
