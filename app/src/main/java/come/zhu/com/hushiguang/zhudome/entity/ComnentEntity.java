package come.zhu.com.hushiguang.zhudome.entity;

import java.util.ArrayList;

/**
 * Created by office on 2015/12/8.
 */
public class ComnentEntity {

    /**
     * author : 每一天都在混水摸鱼
     * content : 钱会让它变的好吃
     * avatar : http://pic3.zhimg.com/0ecf2216c2612b04592126adc16affa2_im.jpg
     * time : 1413987020
     * id : 556780
     * likes : 0
     */

    private ArrayList<CommentsEntity> comments;

    public void setComments(ArrayList<CommentsEntity> comments) {
        this.comments = comments;
    }

    public ArrayList<CommentsEntity> getComments() {
        return comments;
    }


    public static class CommentsEntity {

        private String author;
        private String content;
        private String avatar;
        private int time;
        private int id;
        private int likes;
        /**
         * content : 我每次都不假思索选了牛肉，然后就深深的后悔没有试过一次鸡肉，到下一次又情不自禁选了牛肉，周而复始循环往复-_-#
         * status : 0
         * id : 551969
         * author : 怒放的腋毛
         */

        private ReplyToEntity reply_to;


        public void setAuthor(String author) {
            this.author = author;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public String getAvatar() {
            return avatar;
        }

        public int getTime() {
            return time;
        }

        public int getId() {
            return id;
        }

        public int getLikes() {
            return likes;
        }



        public void setReply_to(ReplyToEntity reply_to) {
            this.reply_to = reply_to;
        }

        public ReplyToEntity getReply_to() {
            return reply_to;
        }

        public static class ReplyToEntity {
            private String content;
            private int status;
            private int id;
            private String author;
            private String error_msg;

            public void setError_msg(String error_msg) {
                this.error_msg = error_msg;
            }

            public String getError_msg() {
                return error_msg;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getContent() {
                return content;
            }

            public int getStatus() {
                return status;
            }

            public int getId() {
                return id;
            }

            public String getAuthor() {
                return author;
            }

            @Override
            public String toString() {
                return "ReplyToEntity{" +
                        "content='" + content + '\'' +
                        ", status=" + status +
                        ", id=" + id +
                        ", author='" + author + '\'' +
                        ", error_msg='" + error_msg + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "CommentsEntity{" +
                    "author='" + author + '\'' +
                    ", content='" + content + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", time=" + time +
                    ", id=" + id +
                    ", likes=" + likes +
                    ", reply_to=" + reply_to +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ComnentEntity{" +
                "comments=" + comments +
                '}';
    }
}
