package come.zhu.com.hushiguang.zhudome.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by office on 2015/12/5.
 */
public class HomeMsgEntity implements Serializable{
    public static final long serialVersionUID = 1L;


    /**
     * date : 20151205
     * stories : [{"images":["http://pic2.zhimg.com/9150140fa81d2d741c173a3ad454a865.jpg"],"type":0,"id":7541213,"ga_prefix":"120522","title":"深夜惊奇 · 活在段子里"},{"title":"看完这个真实的故事，不禁让人质疑世界","ga_prefix":"120521","images":["http://pic3.zhimg.com/9b6ee656ea66161a86bc5789f2aaeec2.jpg"],"multipic":true,"type":0,"id":7551388},{"images":["http://pic2.zhimg.com/e771948867496e05a1bc2a5774d0aef9.jpg"],"type":0,"id":7552036,"ga_prefix":"120520","title":"没有这些酒壶，还怎么在宫斗剧里害人"},{"images":["http://pic3.zhimg.com/e4cf25b48d521777daba275127154e7e.jpg"],"type":0,"id":7548156,"ga_prefix":"120519","title":"写手多、派系多、读者多，日本的推理小说就是特别火"},{"images":["http://pic2.zhimg.com/5435dbcceab771626fe8b25c3cfd1fa9.jpg"],"type":0,"id":7547531,"ga_prefix":"120518","title":"三国系列数不清的人物头像，是我和我的小伙伴们画出来的"},{"images":["http://pic4.zhimg.com/d0fd2047a03c9f41a123973e76fc0a4f.jpg"],"type":0,"id":7551666,"ga_prefix":"120517","title":"小脚一踩，电吉他的音色就变了"},{"title":"除了炒蛋，番茄的吃法可多了","ga_prefix":"120516","images":["http://pic4.zhimg.com/739a376d48ee72e734463fd1abc501a7.jpg"],"multipic":true,"type":0,"id":7517685},{"images":["http://pic4.zhimg.com/cad16c10625da29ad6afba2064192a17.jpg"],"type":0,"id":7549468,"ga_prefix":"120515","title":"最早的古龙水被用来漱口擦脸擦太阳穴\u2026\u2026"},{"images":["http://pic2.zhimg.com/15215c19a7d3b40a124aae25ac997111.jpg"],"type":0,"id":7547294,"ga_prefix":"120514","title":"秒针明明是钟表的第三根针，为什么「秒」却叫「second」？"},{"images":["http://pic1.zhimg.com/ed200e7b58632f9c14f198e35a17c658.jpg"],"type":0,"id":7527706,"ga_prefix":"120513","title":"家门口开间面包店，要挣钱哪有那么简单"},{"images":["http://pic3.zhimg.com/8ce100f008670ba236578ab24efd53da.jpg"],"type":0,"id":7539804,"ga_prefix":"120512","title":"你以为可以满身红血深藏功与名，谁知道对方刷出暴击了"},{"images":["http://pic2.zhimg.com/88e3d582f7b6b847e1359a44d290dd55.jpg"],"type":0,"id":7541271,"ga_prefix":"120511","title":"把一排猫与琴键连接，就能弹出音阶"},{"title":"这样摆盘和张照，晒出来的甜点才诱人","ga_prefix":"120510","images":["http://pic3.zhimg.com/bdb2ec59dfc17e806c3e07c2af5a1142.jpg"],"multipic":true,"type":0,"id":7524791},{"images":["http://pic2.zhimg.com/606478384b11137b4c663b2acc6202ad.jpg"],"type":0,"id":7542803,"ga_prefix":"120509","title":"「在网上想骂谁就骂谁，反正也没人认识我」"},{"images":["http://pic2.zhimg.com/16cc8551dfd6a046a53cda0520d5f3a9.jpg"],"type":0,"id":7537787,"ga_prefix":"120508","title":"不是我不爱洗碗，是这碗怎么洗都油油的"},{"images":["http://pic3.zhimg.com/b48d690f8710054ed31452eadcdc98de.jpg"],"type":0,"id":7540788,"ga_prefix":"120507","title":"想要节食减肥，先饿个两三天吧"},{"images":["http://pic4.zhimg.com/e804894cead6c50caa7b60e1719329ef.jpg"],"type":0,"id":7533981,"ga_prefix":"120507","title":"老王用一碗牛肉面教会你记账基础知识"},{"images":["http://pic2.zhimg.com/074564b76250c3db0123fcda2779f7b5.jpg"],"type":0,"id":7545948,"ga_prefix":"120507","title":"明明动的一样快，电影里面就是跑，游戏里面却像放幻灯片"},{"images":["http://pic1.zhimg.com/27bd9dc892f65b46f508d6db10143938.jpg"],"type":0,"id":7547933,"ga_prefix":"120506","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic2.zhimg.com/b4aaeb48d12f87bc394119ca8d04e1fd.jpg","type":0,"id":7547531,"ga_prefix":"120518","title":"三国系列数不清的人物头像，是我和我的小伙伴们画出来的"},{"image":"http://pic4.zhimg.com/27c48a499d8dad644888fb7967c8f387.jpg","type":0,"id":7547294,"ga_prefix":"120514","title":"秒针明明是钟表的第三根针，为什么「秒」却叫「second」？"},{"image":"http://pic3.zhimg.com/5132e22f3eb60be508643932bed5c28a.jpg","type":0,"id":7541271,"ga_prefix":"120511","title":"把一排猫与琴键连接，就能弹出音阶"},{"image":"http://pic2.zhimg.com/cc732554c093bd07ce0284ebe0311465.jpg","type":0,"id":7542803,"ga_prefix":"120509","title":"「在网上想骂谁就骂谁，反正也没人认识我」"},{"image":"http://pic2.zhimg.com/17b2b888f2e96ffa45d56255062e0205.jpg","type":0,"id":7533981,"ga_prefix":"120507","title":"老王用一碗牛肉面教会你记账基础知识"}]
     */

    private String date;
    /**
     * images : ["http://pic2.zhimg.com/9150140fa81d2d741c173a3ad454a865.jpg"]
     * type : 0
     * id : 7541213
     * ga_prefix : 120522
     * title : 深夜惊奇 · 活在段子里
     */

    private ArrayList<StoriesEntity> stories;
    /**
     * image : http://pic2.zhimg.com/b4aaeb48d12f87bc394119ca8d04e1fd.jpg
     * type : 0
     * id : 7547531
     * ga_prefix : 120518
     * title : 三国系列数不清的人物头像，是我和我的小伙伴们画出来的
     */

    private ArrayList<TopStoriesEntity> top_stories;

    public void setDate(String date) {
        this.date = date;
    }

    public void setStories(ArrayList<StoriesEntity> stories) {
        this.stories = stories;
    }

    public void setTop_stories(ArrayList<TopStoriesEntity> top_stories) {
        this.top_stories = top_stories;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<StoriesEntity> getStories() {
        return stories;
    }

    public ArrayList<TopStoriesEntity> getTop_stories() {
        return top_stories;
    }

    public static class StoriesEntity {
        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private ArrayList<String> images;

        public void setType(int type) {
            this.type = type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }

        public int getType() {
            return type;
        }

        public int getId() {
            return id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public ArrayList<String> getImages() {
            return images;
        }

        @Override
        public String toString() {
            return "StoriesEntity{" +
                    "type=" + type +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    ", images=" + images +
                    '}';
        }
    }

    public static class TopStoriesEntity {
        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public void setImage(String image) {
            this.image = image;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public int getType() {
            return type;
        }

        public int getId() {
            return id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return "TopStoriesEntity{" +
                    "image='" + image + '\'' +
                    ", type=" + type +
                    ", id=" + id +
                    ", ga_prefix='" + ga_prefix + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HomeMsgEntity{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }



}
