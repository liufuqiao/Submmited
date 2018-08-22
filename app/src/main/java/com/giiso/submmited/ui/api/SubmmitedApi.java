package com.giiso.submmited.ui.api;

import com.giiso.submmited.http.HttpContext;
import com.giiso.submmited.http.ResultResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

import static com.giiso.submmited.base.Constants.ADD_SUBMMITED;
import static com.giiso.submmited.base.Constants.ADD_TASK;
import static com.giiso.submmited.base.Constants.CONFIRM_TASK;
import static com.giiso.submmited.base.Constants.DELETE_TASK;
import static com.giiso.submmited.base.Constants.EMPLOYEE_LIST;
import static com.giiso.submmited.base.Constants.LOGIN;
import static com.giiso.submmited.base.Constants.LOGIN_OUT;
import static com.giiso.submmited.base.Constants.MENU_LIST;
import static com.giiso.submmited.base.Constants.PROJECT_LIST;
import static com.giiso.submmited.base.Constants.SELECT_ALL_TASK;
import static com.giiso.submmited.base.Constants.SINGLE_PROJECT_LIST;
import static com.giiso.submmited.base.Constants.START_BREAK_TASK;
import static com.giiso.submmited.base.Constants.SUBMMITED_LIST;
import static com.giiso.submmited.base.Constants.TASK_DETAIL;
import static com.giiso.submmited.base.Constants.UNUSUSAL_LIST;
import static com.giiso.submmited.base.Constants.UPDATE_SUBMMITED;

/**
 * Created by LiuRiZhao on 2018/7/25.
 */

public interface SubmmitedApi {

    // 文件上传参数添加 @Part("password") RequestBody password
    @POST
    @Multipart
    Observable<ResultResponse> uploadImg(@Part MultipartBody.Part parts);

    /**
     * 大文件官方建议用 @Streaming 来进行注解，不然会出现IO异常，小文件可以忽略不注入
     */
    @Streaming
    @POST
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    /**
     * 登录
     * @return
     */
    @FormUrlEncoded
    @POST(LOGIN)
    Observable<ResultResponse> logins(@Field(HttpContext.USERNAME) String username,
                                               @Field(HttpContext.PWD) String password);

    /**
     * 登出
     * @return
     */
    @POST(LOGIN_OUT)
    Observable<ResultResponse> logins_out();

    /**
     * 获取首页菜单项
     */
    @POST(MENU_LIST)
    Observable<ResultResponse> getMenuList();

    @FormUrlEncoded
    @POST(PROJECT_LIST)
    Observable<ResultResponse> getProjectList(@Field(HttpContext.STARTTIME) String startTime,
                                        @Field(HttpContext.ENDTIME) String endTime,
                                        @Field(HttpContext.PROJECT_ID) String projectId,
                                        @Field(HttpContext.PAGENUM) int pageNum,
                                        @Field(HttpContext.PAGESIZE) int pageSize);

    /**
     * type	是	String	0 看个人任务 1看全部人任务
     typeStatus	是	String	1 计划 2 临时任务
     projectId	否	String	项目id
     userName	否	String	被指派人姓名
     createId	否	String	创建人id
     startTime	否	String	开始时间
     endTime	否	String	结束时间
     pageNum	否	int	记录开始读取行数 默认值：0
     pageSize	否	int	获取的数量 默认值：20
     */
    @FormUrlEncoded
    @POST(SELECT_ALL_TASK)
    Observable<ResultResponse> getAllList(@Field(HttpContext.TYPE) String type,
                                        @Field(HttpContext.TYPESTATUS) String typeStatus,
                                        @Field(HttpContext.USERNAME) String userName,
                                        @Field(HttpContext.CREATEID) String createId,
                                        @Field(HttpContext.STARTTIME) String startTime,
                                        @Field(HttpContext.ENDTIME) String endTime,
                                        @Field(HttpContext.PROJECT_ID) String projectId,
                                        @Field(HttpContext.PAGENUM) int pageNum,
                                        @Field(HttpContext.PAGESIZE) int pageSize);

    /**
     * 获取日报列表
     startTime	否	String	开始时间
     endTime	否	String	结束时间
     name	否	String	计划名称
     projectId	否	String	项目id
     pageNum	否	int	记录开始读取行数 默认值：0
     pageSize	否	int	获取的数量 默认值：20
     * @return
     */
    @FormUrlEncoded
    @POST(SUBMMITED_LIST)
    Observable<ResultResponse> getSubmmitedList(@Field(HttpContext.STARTTIME)String startTime,
                                                @Field(HttpContext.ENDTIME) String endTime,
                                                @Field(HttpContext.NAME) String name,
                                                @Field(HttpContext.PROJECT_ID) String projectId,
                                                @Field(HttpContext.PAGENUM) int pageNum,
                                                @Field(HttpContext.PAGESIZE) int pageSize);

    /**
     * 获取异常任务列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST(UNUSUSAL_LIST)
    Observable<ResultResponse> getUnususalList(@Field(HttpContext.PAGENUM) int pageNum, @Field(HttpContext.PAGESIZE) int pageSize);

    /**
     * 获取个人项目列表
     * @return
     */
    @POST(SINGLE_PROJECT_LIST)
    Observable<ResultResponse> getSingleProjectList();


    /**
     * 获取指派人列表
     * @return
     */
    @FormUrlEncoded
    @POST(EMPLOYEE_LIST)
    Observable<ResultResponse> getEmployeeList(@Field(HttpContext.PROJECT_ID) int projecyId);

    /**
     * 启动或暂停任务
     * @param id
     * @param status 0 启动 3 暂停
     * @return Observable<ResultResponse>
     */
    @FormUrlEncoded
    @POST(START_BREAK_TASK)
    Observable<ResultResponse> startOrStopTask(@Field(HttpContext.ID) int id,
                                              @Field(HttpContext.STATUS) String status);

    /**
     * 删除 任务 或 日报
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(DELETE_TASK)
    Observable<ResultResponse> deleteTask(@Field(HttpContext.ID) int id);

    /**
     * 获取任务详情  日报详情
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(TASK_DETAIL)
    Observable<ResultResponse> getTaskDetail(@Field(HttpContext.ID) int id);

    /**
     * 确认任务
     * @param id
     * @return Observable<ResultResponse>
     */
    @FormUrlEncoded
    @POST(CONFIRM_TASK)
    Observable<ResultResponse> confirmTask(@Field(HttpContext.ID) int id);

    /**
     * 添加项目任务
     * @param expectStartTime
     * @param expectFinishTime
     * @param projectId
     * @param name
     * @param type
     * @param userId
     * @param taskTime
     * @param percentComplete
     * @return
     */
    @FormUrlEncoded
    @POST(ADD_TASK)
    Observable<ResultResponse> addTask(@Field(HttpContext.EXPECTSTARTTIME) String expectStartTime,
                                       @Field(HttpContext.EXPECTFINISHTIME) String expectFinishTime,
                                       @Field(HttpContext.PROJECT_ID) int projectId,
                                       @Field(HttpContext.NAME) String name,
                                       @Field(HttpContext.TYPE) String type,
                                       @Field(HttpContext.USERID) int userId,
                                       @Field(HttpContext.TASKTIME) String taskTime,
                                       @Field(HttpContext.PERCENTCOMPLETE) String percentComplete);

    /**
     * 修改项目任务
     * @param expectStartTime
     * @param expectFinishTime
     * @param projectId
     * @param name 任务描述
     * @param type 任务类型
     * @param id   任务ID
     * @param percentComplete
     * @return
     */
    @FormUrlEncoded
    @POST(ADD_TASK)
    Observable<ResultResponse> updateTask( @Field(HttpContext.ID) int id,
                                       @Field(HttpContext.NAME) String name,
                                       @Field(HttpContext.TYPE) String type,
                                       @Field(HttpContext.EXPECTSTARTTIME) String expectStartTime,
                                       @Field(HttpContext.EXPECTFINISHTIME) String expectFinishTime,
                                       @Field(HttpContext.PROJECT_ID) int projectId,
                                       @Field(HttpContext.PERCENTCOMPLETE) String percentComplete);


    /**
     * 添加日报
     * @param id
     * @param realFinishTime 日报时间
     * @param taskTime 日报工时
     * @param status 状态 0进行中 1已完成 2延期
     * @param percentComplete 任务完成百分比
     * @param description 任务备注
     * @return Observable<ResultResponse>
     */
    @FormUrlEncoded
    @POST(ADD_SUBMMITED)
    Observable<ResultResponse> addSubmmited(@Field(HttpContext.ID) int id,
                                       @Field(HttpContext.REALFINISHTIME) String realFinishTime,
                                       @Field(HttpContext.TASKTIME) String taskTime,
                                       @Field(HttpContext.STATUS) String status,
                                       @Field(HttpContext.PERCENTCOMPLETE) String percentComplete,
                                       @Field(HttpContext.DESCRIPTION) String description);

    /**
     * 修改日报
     * @param id
     * @param percentComplete
     * @param taskTime
     * @param description
     * @param realFinishTime
     * @return
     */
    @FormUrlEncoded
    @POST(UPDATE_SUBMMITED)
    Observable<ResultResponse> updateSubmmited(@Field(HttpContext.ID) int id,
                                          @Field(HttpContext.PERCENTCOMPLETE) String percentComplete,
                                          @Field(HttpContext.TASKTIME) String taskTime,
                                          @Field(HttpContext.DESCRIPTION) String description,
                                          @Field(HttpContext.REALFINISHTIME) String realFinishTime);


    @FormUrlEncoded
    @POST()
    Observable<ResultResponse> checkUpdate(@Field(HttpContext.VERSION) String version,
                                           @Field(HttpContext.APP_NAME) String app_name,
                                           @Field(HttpContext.T_ID) String t_id,
                                           @Field(HttpContext.MARKETID) String marketid);
    @FormUrlEncoded
    @POST()
    Observable<ResultResponse> uploadLog(@FieldMap Map<String, Object> pramsMap);


}