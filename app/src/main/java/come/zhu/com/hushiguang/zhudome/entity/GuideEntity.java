package come.zhu.com.hushiguang.zhudome.entity;

import java.io.Serializable;

/**
 * Created by office on 2015/12/5.
 */
public class GuideEntity implements Serializable{
    public static final long serialVersionUID = 1L;


    /**
     * text : © Fido Dido
     * img : http://p2.zhimg.com/10/7b/107bb4894b46d75a892da6fa80ef504a.jpg
     */

    private String text;
    private String img;

    public void setText(String text) {
        this.text = text;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public String getImg() {
        return img;
    }


    @Override
    public String toString() {
        return "GuideEntity{" +
                "text='" + text + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
