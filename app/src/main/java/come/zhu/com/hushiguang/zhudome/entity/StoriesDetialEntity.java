package come.zhu.com.hushiguang.zhudome.entity;

import java.util.List;

/**
 * Created by office on 2015/12/7.
 */
public class StoriesDetialEntity {

    /**
     * body :
     * image_source : MIKI Yoshihito / CC BY
     * title : 对于古典钢琴这种大师都很难不出错的事情，为什么不用机器人来完成？
     * image : http://pic4.zhimg.com/3983fd646ef618cdc7c5ea3cc794523f.jpg
     * recommenders : [{"avatar":"http://pic1.zhimg.com/7c0252b19accff6cf0de8ec789508b68_m.jpg"}]
     * share_url : http://daily.zhihu.com/story/7482771
     * js : []
     * theme : {"thumbnail":"http://pic4.zhimg.com/eac535117ed895983bd2721f35d6e8dc.jpg","id":7,"name":"音乐日报"}
     * ga_prefix : 112313
     * type : 0
     * id : 7482771
     * css : ["http://news.at.zhihu.com/css/news_qa.auto.css?v=77778"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    /**
     * thumbnail : http://pic4.zhimg.com/eac535117ed895983bd2721f35d6e8dc.jpg
     * id : 7
     * name : 音乐日报
     */

    private ThemeEntity theme;
    private String ga_prefix;
    private int type;
    private int id;
    /**
     * avatar : http://pic1.zhimg.com/7c0252b19accff6cf0de8ec789508b68_m.jpg
     */

    private List<RecommendersEntity> recommenders;
    private List<?> js;
    private List<String> css;

    public void setBody(String body) {
        this.body = body;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public void setTheme(ThemeEntity theme) {
        this.theme = theme;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRecommenders(List<RecommendersEntity> recommenders) {
        this.recommenders = recommenders;
    }

    public void setJs(List<?> js) {
        this.js = js;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public String getBody() {
        return body;
    }

    public String getImage_source() {
        return image_source;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getShare_url() {
        return share_url;
    }

    public ThemeEntity getTheme() {
        return theme;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public List<RecommendersEntity> getRecommenders() {
        return recommenders;
    }

    public List<?> getJs() {
        return js;
    }

    public List<String> getCss() {
        return css;
    }

    public static class ThemeEntity {
        private String thumbnail;
        private int id;
        private String name;

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "ThemeEntity{" +
                    "thumbnail='" + thumbnail + '\'' +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class RecommendersEntity {
        private String avatar;

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatar() {
            return avatar;
        }

        @Override
        public String toString() {
            return "RecommendersEntity{" +
                    "avatar='" + avatar + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StoriesDetialEntity{" +
                "body='" + body + '\'' +
                ", image_source='" + image_source + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", share_url='" + share_url + '\'' +
                ", theme=" + theme +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", type=" + type +
                ", id=" + id +
                ", recommenders=" + recommenders +
                ", js=" + js +
                ", css=" + css +
                '}';
    }
}
