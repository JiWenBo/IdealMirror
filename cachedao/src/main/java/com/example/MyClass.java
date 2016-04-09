package com.example;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

import de.greenrobot.daogenerator.DaoGenerator;

public class MyClass {
    public static void main(String[] args){
       Schema schema = new Schema(1,"com.example.dllo.idealmirror.mirrordao") ;
/**/
        Entity entity = schema.addEntity("AllMirrorCache");//AllMirrorCache 的表
        entity.addIdProperty().primaryKey().autoincrement();//添加一个主键
        entity.addStringProperty("imgurl");
        entity.addStringProperty("goodname");
        entity.addStringProperty("productarea");
        entity.addStringProperty("brand");
        entity.addStringProperty("goodprice");


/*GoodList*/
        Entity goodentity = schema.addEntity("GoodListCache");//GoodListDao 的表
        goodentity.addIdProperty().primaryKey().autoincrement();//添加一个主键
        goodentity.addStringProperty("title");
        goodentity.addStringProperty("type");
        goodentity.addStringProperty("store");
        goodentity.addStringProperty("info_data");
        goodentity.addStringProperty("topColor");
        goodentity.addStringProperty("buttomColor");

/*平光镜数据库  */
            Entity plainmirror = schema.addEntity("PlainMirror");//GoodListDao 的表
            plainmirror.addIdProperty().primaryKey().autoincrement();//添加一个主键
            plainmirror.addStringProperty("goodsimg");
            plainmirror.addStringProperty("goodsname");
            plainmirror.addStringProperty("wholestorge");
            plainmirror.addStringProperty("product");
            plainmirror.addStringProperty("goodsprice");
            plainmirror.addStringProperty("brand");
            plainmirror.addStringProperty("discount");

        /*专题分享表*/
        Entity storymirror = schema.addEntity("StoryMirror");
        storymirror.addIdProperty().primaryKey().autoincrement();
        storymirror.addStringProperty("picimg");
        storymirror.addStringProperty("title");
        storymirror.addStringProperty("type");



        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
