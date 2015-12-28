package come.zhu.com.hushiguang.zhudome.entity;

/**
 * Created by office on 2015/12/11.
 */
public class CommentPriseEntity {

    /**
     * count : 1
     */

    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Count{" +
                "count=" + count +
                '}';
    }

}
