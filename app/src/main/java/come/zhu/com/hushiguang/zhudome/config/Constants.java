package come.zhu.com.hushiguang.zhudome.config;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import come.zhu.com.hushiguang.zhudome.entity.CommentPriseEntity;
import come.zhu.com.hushiguang.zhudome.entity.ComnentEntity;
import come.zhu.com.hushiguang.zhudome.entity.GuideEntity;
import come.zhu.com.hushiguang.zhudome.entity.HomeMsgEntity;
import come.zhu.com.hushiguang.zhudome.entity.StoriesDetialEntity;
import come.zhu.com.hushiguang.zhudome.entity.StoriesNumberEntity;
import come.zhu.com.hushiguang.zhudome.entity.ThemesEntity;
import come.zhu.com.hushiguang.zhudome.entity.ThemesNewsMsgEntity;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/***
 * 接口列表 所有请求全部以get请求去访问数据
 *
 * @author office
 */
public interface Constants {

    /* 用户的标识认证 **/
    public static String Authorization = "Bearer VkEYGuLsQnSR3SCmsyompA";

    /* 获取启动页的图片 **/
    @GET("/api/4/start-image/{size}")
    Call<GuideEntity> getGuideImage(@Path("size") String size);

    /* 获取日报主题列表 **/
    @GET("/api/4/themes")
    Call<ThemesEntity> getThemes();


    /* 获取主页信息 **/
    //http://news-at.zhihu.com/api/4/news/latest
    @GET("/api/4/news/latest")
    Call<HomeMsgEntity> getHomeMsg();

    /* 获取过往信息 **/
    //http://news-at.zhihu.com/api/4/before/20131119
    @GET("/api/4/news/before/{date}")
    Call<HomeMsgEntity> getHistoryMsg(@Path("date") String date);


    /* 获取过往信息 **/
    //http://news-at.zhihu.com/api/4/theme/11
    @GET("/api/4/theme/{id}")
    Call<ThemesNewsMsgEntity> getThemesMsgList(@Path("id") String id);

    /* 获取新闻的点赞数 **/
    //http://news-at.zhihu.com/api/4/story-extra/#{id}
    @GET("/api/4/story-extra/{id}")
    Call<StoriesNumberEntity> getNewsNumber(@Path("id") String id);

    /* 获取新闻的内容信息 **/
    //http://news-at.zhihu.com/api/4/news/3892357
    @GET("/api/4/news/{id}")
    Call<StoriesDetialEntity> getNewsDetial(@Path("id") String id);

    /* 获取新闻的短评论 **/
    //http://news-at.zhihu.com/api/4/story/4232852/short-comments
    @GET("/api/4/story/{id}/short-comments")
    Call<ComnentEntity> getNewsShortComments(@Path("id") String id);

    /* 获取新闻的长评论 **/
    //http://news-at.zhihu.com/api/4/story/4232852/long-comments
    @GET("/api/4/story/{id}/long-comments")
    Call<ComnentEntity> getNewsLongComments(@Path("id") String id);


    /* 评论的点赞 **/
    //http://news-at.zhihu.com/api/4/story/4232852/long-comments
    @POST("/api/4/vote/comment/{id}")
    Call<CommentPriseEntity> commentPrise(@Header("Authorization") String Authorization , @Path("id") String id);

    /* 评论的取消点赞 **/
    @DELETE("/api/4/vote/comment/{id}")
    Call<CommentPriseEntity> commentExit(@Header("Authorization") String Authorization , @Path("id") String id);

    /* 文章的点赞与取消 **/
    @POST("/api/4/vote/story/{id}")
    Call<ResponseBody> storePrise(@Header("Authorization") String Authorization , @Body() RequestBody data , @Path("id") String id);

}
