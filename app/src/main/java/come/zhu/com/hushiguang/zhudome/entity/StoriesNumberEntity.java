package come.zhu.com.hushiguang.zhudome.entity;

import java.io.Serializable;

/**
 * Created by office on 2015/12/7.
 */
public class StoriesNumberEntity implements Serializable{

    /**
     * post_reasons : 0
     * long_comments : 1
     * popularity : 12
     * normal_comments : 154
     * comments : 154
     * short_comments : 153
     */

    private int post_reasons;
    private int long_comments;
    private int popularity;
    private int normal_comments;
    private int comments;
    private int short_comments;

    public void setPost_reasons(int post_reasons) {
        this.post_reasons = post_reasons;
    }

    public void setLong_comments(int long_comments) {
        this.long_comments = long_comments;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setNormal_comments(int normal_comments) {
        this.normal_comments = normal_comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void setShort_comments(int short_comments) {
        this.short_comments = short_comments;
    }

    public int getPost_reasons() {
        return post_reasons;
    }

    public int getLong_comments() {
        return long_comments;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getNormal_comments() {
        return normal_comments;
    }

    public int getComments() {
        return comments;
    }

    public int getShort_comments() {
        return short_comments;
    }

    @Override
    public String toString() {
        return "StoriesNumberEntity{" +
                "post_reasons=" + post_reasons +
                ", long_comments=" + long_comments +
                ", popularity=" + popularity +
                ", normal_comments=" + normal_comments +
                ", comments=" + comments +
                ", short_comments=" + short_comments +
                '}';
    }
}
