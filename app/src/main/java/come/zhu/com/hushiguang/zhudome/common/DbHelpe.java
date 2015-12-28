package come.zhu.com.hushiguang.zhudome.common;

import java.util.List;

import come.zhu.com.hushiguang.zhudome.DB.Colect;
import come.zhu.com.hushiguang.zhudome.DB.ColectDao;
import come.zhu.com.hushiguang.zhudome.DB.CommentPrise;
import come.zhu.com.hushiguang.zhudome.DB.CommentPriseDao;
import come.zhu.com.hushiguang.zhudome.DB.StorePrise;
import come.zhu.com.hushiguang.zhudome.DB.StorePriseDao;
import come.zhu.com.hushiguang.zhudome.DB.Themes;
import come.zhu.com.hushiguang.zhudome.DB.ThemesDao;
import come.zhu.com.hushiguang.zhudome.MainActivity;
import come.zhu.com.hushiguang.zhudome.ui.StoriesActivity;
import de.greenrobot.dao.query.Query;

/**
 * Created by office on 2015/12/9.
 */
public class DbHelpe {

    /**
     * 存在为true
     * 不存在为false
     */
   public static List<Themes> getDBWhereId(MainActivity mainActivity , String themesId) {
        Query query = mainActivity.themesDao.queryBuilder().where(ThemesDao.Properties.ThemesId.eq(themesId)).build();
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return query.list();
        }
    }


    /**
     * 存在为true
     * 不存在为false
     */
    public static List<Colect> getNewsFromDb(StoriesActivity storiesActivity , String NewsId) {
        Query query = storiesActivity.colectDao.queryBuilder().where(ColectDao.Properties.NewsId.eq(NewsId)).build();
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return query.list();
        }
    }


    /**
     * 存在为true
     * 不存在为false
     */
    public static List<StorePrise> getNewsPrise(StoriesActivity storiesActivity , String NewsId) {
        Query query = storiesActivity.storePriseDao.queryBuilder().where(StorePriseDao.Properties.NewsId.eq(NewsId)).build();
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return query.list();
        }
    }

    /**
     * 存在为true
     * 不存在为false
     */
    public static List<CommentPrise> getCommentPrise(StoriesActivity storiesActivity , String commentId) {
        Query query = storiesActivity.commentPriseDao.queryBuilder().where(CommentPriseDao.Properties.CommentId.eq(commentId)).build();
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return query.list();
        }
    }
}
