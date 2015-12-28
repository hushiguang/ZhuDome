package com.example;

import java.io.File;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {

    public static void main(String[] arg) throws Exception {


        Schema schema = new Schema(1, "");


        addColect(schema);
//        addNode(schema);
//        addStorePrise(schema);
//        addCommentPrise(schema);



        new DaoGenerator().generateAll(schema, getPath());
    }


    /**
     * 获取程序的根目录
     *
     * @return
     */
    private static String getPath() {
/***
 * java\come\zhu\com\hushiguang\zhudome
 */
        String path = new StringBuilder()
                .append("app")
                .append(File.separator)
                .append("src")
                .append(File.separator)
                .append("main")
                .append(File.separator)
                .append("java")
                .append(File.separator)
                .append("come")
                .append(File.separator)
                .append("zhu")
                .append(File.separator)
                .append("com")
                .append(File.separator)
                .append("hushiguang")
                .append(File.separator)
                .append("zhudome")
                .append(File.separator)
                .append("DB")
                .append(File.separator).toString();
        return new File(path).getAbsolutePath();
    }


    /***
     *
     *
     * @param schema
     */
    static void addNode(Schema schema) {
        Entity node = schema.addEntity("Themes");
        node.addIdProperty().primaryKey();
        node.addStringProperty("deviceId");
        node.addStringProperty("themesId");
    }

    static void addColect(Schema schema) {
        Entity node = schema.addEntity("Colect");
        node.addIdProperty().primaryKey();
        node.addStringProperty("deviceId");
        node.addStringProperty("newsId");
        node.addStringProperty("image");
        node.addStringProperty("title");

    }

    static void addStorePrise(Schema schema) {
        Entity node = schema.addEntity("StorePrise");
        node.addIdProperty().primaryKey();
        node.addStringProperty("deviceId");
        node.addStringProperty("newsId");
    }

    static void addCommentPrise(Schema schema) {
        Entity node = schema.addEntity("CommentPrise");
        node.addIdProperty().primaryKey();
        node.addStringProperty("deviceId");
        node.addStringProperty("commentId");
    }

}
