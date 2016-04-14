package com.example.dllo.idealmirror.bean;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by dllo on 16/3/29.
 * 分类
 */
public class GoodsListBean{
    /**
     * result : 1
     * msg :
     * data : {"pagination":{"first_time":"1452078872","last_time":"1452078872","has_more":"2"},"list":[{"goods_id":"28JeX1452078872","goods_pic":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo0102549e4bee5442391fa715e7d33f6864c3.jpg","goods_img":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo017fc27aa328f2d4592b23966bc0d1a1b1.jpg","goods_name":"SEE CONCEPT","model":"See-o-01|玳瑁復古花紋閱讀鏡","last_storge":"0","whole_storge":"2","height":"","ordain":"1","product_area":"法國巴黎","goods_price":"450","discount_price":"","brand":"玳瑁復古花紋閱讀鏡","info_des":"思念那么长，愿意为你撑伞走过所有雨天。你送我的see concept的玳瑁圆框阅读镜，斑驳之间弥漫着无法言语的轻柔，耳边的轻巧是雨天咖啡屋的喃喃细语。我戴着它，从雨季来临，到一人看过曾经一起注目的风景。","goods_data":[{"introContent":"复古花纹玳瑁色一直备受追捧，see concept的玳瑁圆框阅读镜，在追求时尚感的同时也不忘执着质感，采用轻柔树脂材质，打造出舒适的镜腿弧度，有效防治眼镜滑落，镜框本身却不可思议的有鼻翼能承受之轻。","cellHeight":"590","name":"","location":"來自時尚之都法國","country":"巴黎","english":"The frame comes from France"},{"introContent":"see concept不仅是色彩时尚的先锋，也极度注重佩戴舒适感。灵活并可自由伸缩的铰链，免去脸部宽窄的担忧。镜腿处除了印有see的精致小标，再无多余缀饰。建议搭配温暖的白衬衫，和午后提神的咖啡。","cellHeight":"590","name":"SEE CONCEPT","location":"","country":"","english":""},{"introContent":"完美经典的圆框眼镜能为造型增加不少书卷气，这款see concept不仅营造了轻盈感又保持眼镜了的质感，整个镜框的线条流畅精致，丝毫没有被超低的售价所影响。戴在脸上、挂在口袋或衣领，立马提升造型感。","cellHeight":"590","name":"SEE CONCEPT","location":"","country":"","english":""}],"design_des":[{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo010150d3b15510a8be1bc28f9a15a9d6c680.jpg","cellHeight":"480","type":"1"},{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo01035026a1557ce8029799b2f06361716b79.jpg","cellHeight":"480","type":"1"},{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo0102269dc1969932cf3a47a9f97ab2062653.jpg","cellHeight":"480","type":"1"},{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/184713835e17631786215c4d4b34099b1f8fd35.jpg","cellHeight":"540","type":"2"},{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/shouhouxuzhi17565df5d0d392b70ab0e8ef19fff444a.jpg","cellHeight":"828","type":"3"}],"wear_video":[{"type":"9","data":"http://7xprhi.com2.z0.glb.qiniucdn.com/void4ea287289b3c6c2e145633889a74fc277.jpg"},{"type":"1","data":"http://7xprhi.com2.z0.glb.qiniucdn.com/edie106279882ec82e31d29e092f31c3f35c43a94b1.jpg"},{"type":"4","data":"http://7xprhi.com2.z0.glb.qiniucdn.com/IMG_802714a2502f05fc38749df9f76db121f19bf.jpg"},{"type":"5","data":"http://7xprhi.com2.z0.glb.qiniucdn.com/IMG_8009e48f77a4f8f148e24c805ed8f8c9c3da.jpg"},{"type":"8","data":"http://7xlvms.com2.z0.glb.qiniucdn.com/See%20Concept%2C%20Deuxi.mp4"}],"goods_share":"http://api101.test.mirroreye.cn/index.php/goodweb/info?id=28JeX1452078872"}]}
     */

    private String result;
    private String msg;
    /**
     * pagination : {"first_time":"1452078872","last_time":"1452078872","has_more":"2"}
     * list : [{"goods_id":"28JeX1452078872","goods_pic":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo0102549e4bee5442391fa715e7d33f6864c3.jpg","goods_img":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo017fc27aa328f2d4592b23966bc0d1a1b1.jpg","goods_name":"SEE CONCEPT","model":"See-o-01|玳瑁復古花紋閱讀鏡","last_storge":"0","whole_storge":"2","height":"","ordain":"1","product_area":"法國巴黎","goods_price":"450","discount_price":"","brand":"玳瑁復古花紋閱讀鏡","info_des":"思念那么长，愿意为你撑伞走过所有雨天。你送我的see concept的玳瑁圆框阅读镜，斑驳之间弥漫着无法言语的轻柔，耳边的轻巧是雨天咖啡屋的喃喃细语。我戴着它，从雨季来临，到一人看过曾经一起注目的风景。","goods_data":[{"introContent":"复古花纹玳瑁色一直备受追捧，see concept的玳瑁圆框阅读镜，在追求时尚感的同时也不忘执着质感，采用轻柔树脂材质，打造出舒适的镜腿弧度，有效防治眼镜滑落，镜框本身却不可思议的有鼻翼能承受之轻。","cellHeight":"590","name":"","location":"來自時尚之都法國","country":"巴黎","english":"The frame comes from France"},{"introContent":"see concept不仅是色彩时尚的先锋，也极度注重佩戴舒适感。灵活并可自由伸缩的铰链，免去脸部宽窄的担忧。镜腿处除了印有see的精致小标，再无多余缀饰。建议搭配温暖的白衬衫，和午后提神的咖啡。","cellHeight":"590","name":"SEE CONCEPT","location":"","country":"","english":""},{"introContent":"完美经典的圆框眼镜能为造型增加不少书卷气，这款see concept不仅营造了轻盈感又保持眼镜了的质感，整个镜框的线条流畅精致，丝毫没有被超低的售价所影响。戴在脸上、挂在口袋或衣领，立马提升造型感。","cellHeight":"590","name":"SEE CONCEPT","location":"","country":"","english":""}],"design_des":[{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo010150d3b15510a8be1bc28f9a15a9d6c680.jpg","cellHeight":"480","type":"1"},{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo01035026a1557ce8029799b2f06361716b79.jpg","cellHeight":"480","type":"1"},{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo0102269dc1969932cf3a47a9f97ab2062653.jpg","cellHeight":"480","type":"1"},{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/184713835e17631786215c4d4b34099b1f8fd35.jpg","cellHeight":"540","type":"2"},{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/shouhouxuzhi17565df5d0d392b70ab0e8ef19fff444a.jpg","cellHeight":"828","type":"3"}],"wear_video":[{"type":"9","data":"http://7xprhi.com2.z0.glb.qiniucdn.com/void4ea287289b3c6c2e145633889a74fc277.jpg"},{"type":"1","data":"http://7xprhi.com2.z0.glb.qiniucdn.com/edie106279882ec82e31d29e092f31c3f35c43a94b1.jpg"},{"type":"4","data":"http://7xprhi.com2.z0.glb.qiniucdn.com/IMG_802714a2502f05fc38749df9f76db121f19bf.jpg"},{"type":"5","data":"http://7xprhi.com2.z0.glb.qiniucdn.com/IMG_8009e48f77a4f8f148e24c805ed8f8c9c3da.jpg"},{"type":"8","data":"http://7xlvms.com2.z0.glb.qiniucdn.com/See%20Concept%2C%20Deuxi.mp4"}],"goods_share":"http://api101.test.mirroreye.cn/index.php/goodweb/info?id=28JeX1452078872"}]
     */

    private DataEntity data;

    public static GoodsListBean objectFromData(String str) {

        return new Gson().fromJson(str, GoodsListBean.class);
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * first_time : 1452078872
         * last_time : 1452078872
         * has_more : 2
         */

        private PaginationEntity pagination;
        /**
         * goods_id : 28JeX1452078872
         * goods_pic : http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo0102549e4bee5442391fa715e7d33f6864c3.jpg
         * goods_img : http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo017fc27aa328f2d4592b23966bc0d1a1b1.jpg
         * goods_name : SEE CONCEPT
         * model : See-o-01|玳瑁復古花紋閱讀鏡
         * last_storge : 0
         * whole_storge : 2
         * height :
         * ordain : 1
         * product_area : 法國巴黎
         * goods_price : 450
         * discount_price :
         * brand : 玳瑁復古花紋閱讀鏡
         * info_des : 思念那么长，愿意为你撑伞走过所有雨天。你送我的see concept的玳瑁圆框阅读镜，斑驳之间弥漫着无法言语的轻柔，耳边的轻巧是雨天咖啡屋的喃喃细语。我戴着它，从雨季来临，到一人看过曾经一起注目的风景。
         * goods_data : [{"introContent":"复古花纹玳瑁色一直备受追捧，see concept的玳瑁圆框阅读镜，在追求时尚感的同时也不忘执着质感，采用轻柔树脂材质，打造出舒适的镜腿弧度，有效防治眼镜滑落，镜框本身却不可思议的有鼻翼能承受之轻。","cellHeight":"590","name":"","location":"來自時尚之都法國","country":"巴黎","english":"The frame comes from France"},{"introContent":"see concept不仅是色彩时尚的先锋，也极度注重佩戴舒适感。灵活并可自由伸缩的铰链，免去脸部宽窄的担忧。镜腿处除了印有see的精致小标，再无多余缀饰。建议搭配温暖的白衬衫，和午后提神的咖啡。","cellHeight":"590","name":"SEE CONCEPT","location":"","country":"","english":""},{"introContent":"完美经典的圆框眼镜能为造型增加不少书卷气，这款see concept不仅营造了轻盈感又保持眼镜了的质感，整个镜框的线条流畅精致，丝毫没有被超低的售价所影响。戴在脸上、挂在口袋或衣领，立马提升造型感。","cellHeight":"590","name":"SEE CONCEPT","location":"","country":"","english":""}]
         * design_des : [{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo010150d3b15510a8be1bc28f9a15a9d6c680.jpg","cellHeight":"480","type":"1"},{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo01035026a1557ce8029799b2f06361716b79.jpg","cellHeight":"480","type":"1"},{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo0102269dc1969932cf3a47a9f97ab2062653.jpg","cellHeight":"480","type":"1"},{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/184713835e17631786215c4d4b34099b1f8fd35.jpg","cellHeight":"540","type":"2"},{"img":"http://7xprhi.com2.z0.glb.qiniucdn.com/shouhouxuzhi17565df5d0d392b70ab0e8ef19fff444a.jpg","cellHeight":"828","type":"3"}]
         * wear_video : [{"type":"9","data":"http://7xprhi.com2.z0.glb.qiniucdn.com/void4ea287289b3c6c2e145633889a74fc277.jpg"},{"type":"1","data":"http://7xprhi.com2.z0.glb.qiniucdn.com/edie106279882ec82e31d29e092f31c3f35c43a94b1.jpg"},{"type":"4","data":"http://7xprhi.com2.z0.glb.qiniucdn.com/IMG_802714a2502f05fc38749df9f76db121f19bf.jpg"},{"type":"5","data":"http://7xprhi.com2.z0.glb.qiniucdn.com/IMG_8009e48f77a4f8f148e24c805ed8f8c9c3da.jpg"},{"type":"8","data":"http://7xlvms.com2.z0.glb.qiniucdn.com/See%20Concept%2C%20Deuxi.mp4"}]
         * goods_share : http://api101.test.mirroreye.cn/index.php/goodweb/info?id=28JeX1452078872
         */

        private List<ListEntity> list;

        public static DataEntity objectFromData(String str) {

            return new Gson().fromJson(str, DataEntity.class);
        }

        public void setPagination(PaginationEntity pagination) {
            this.pagination = pagination;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public PaginationEntity getPagination() {
            return pagination;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class PaginationEntity {
            private String first_time;
            private String last_time;
            private String has_more;

            public static PaginationEntity objectFromData(String str) {

                return new Gson().fromJson(str, PaginationEntity.class);
            }

            public void setFirst_time(String first_time) {
                this.first_time = first_time;
            }

            public void setLast_time(String last_time) {
                this.last_time = last_time;
            }

            public void setHas_more(String has_more) {
                this.has_more = has_more;
            }

            public String getFirst_time() {
                return first_time;
            }

            public String getLast_time() {
                return last_time;
            }

            public String getHas_more() {
                return has_more;
            }
        }

        public static class ListEntity {
            private String goods_id;
            private String goods_pic;
            private String goods_img;
            private String goods_name;
            private String model;
            private String last_storge;
            private String whole_storge;
            private String height;
            private String ordain;
            private String product_area;
            private String goods_price;
            private String discount_price;
            private String brand;
            private String info_des;
            private String goods_share;
            /**
             * introContent : 复古花纹玳瑁色一直备受追捧，see concept的玳瑁圆框阅读镜，在追求时尚感的同时也不忘执着质感，采用轻柔树脂材质，打造出舒适的镜腿弧度，有效防治眼镜滑落，镜框本身却不可思议的有鼻翼能承受之轻。
             * cellHeight : 590
             * name :
             * location : 來自時尚之都法國
             * country : 巴黎
             * english : The frame comes from France
             */

            private List<GoodsDataEntity> goods_data;
            /**
             * img : http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo010150d3b15510a8be1bc28f9a15a9d6c680.jpg
             * cellHeight : 480
             * type : 1
             */

            private List<DesignDesEntity> design_des;
            /**
             * type : 9
             * data : http://7xprhi.com2.z0.glb.qiniucdn.com/void4ea287289b3c6c2e145633889a74fc277.jpg
             */

            private List<WearVideoEntity> wear_video;

            public static ListEntity objectFromData(String str) {

                return new Gson().fromJson(str, ListEntity.class);
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public void setGoods_pic(String goods_pic) {
                this.goods_pic = goods_pic;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public void setLast_storge(String last_storge) {
                this.last_storge = last_storge;
            }

            public void setWhole_storge(String whole_storge) {
                this.whole_storge = whole_storge;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public void setOrdain(String ordain) {
                this.ordain = ordain;
            }

            public void setProduct_area(String product_area) {
                this.product_area = product_area;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public void setDiscount_price(String discount_price) {
                this.discount_price = discount_price;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public void setInfo_des(String info_des) {
                this.info_des = info_des;
            }

            public void setGoods_share(String goods_share) {
                this.goods_share = goods_share;
            }

            public void setGoods_data(List<GoodsDataEntity> goods_data) {
                this.goods_data = goods_data;
            }

            public void setDesign_des(List<DesignDesEntity> design_des) {
                this.design_des = design_des;
            }

            public void setWear_video(List<WearVideoEntity> wear_video) {
                this.wear_video = wear_video;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public String getGoods_pic() {
                return goods_pic;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public String getModel() {
                return model;
            }

            public String getLast_storge() {
                return last_storge;
            }

            public String getWhole_storge() {
                return whole_storge;
            }

            public String getHeight() {
                return height;
            }

            public String getOrdain() {
                return ordain;
            }

            public String getProduct_area() {
                return product_area;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public String getDiscount_price() {
                return discount_price;
            }

            public String getBrand() {
                return brand;
            }

            public String getInfo_des() {
                return info_des;
            }

            public String getGoods_share() {
                return goods_share;
            }

            public List<GoodsDataEntity> getGoods_data() {
                return goods_data;
            }

            public List<DesignDesEntity> getDesign_des() {
                return design_des;
            }

            public List<WearVideoEntity> getWear_video() {
                return wear_video;
            }

            public static class GoodsDataEntity {
                private String introContent;
                private String cellHeight;
                private String name;
                private String location;
                private String country;
                private String english;

                public static GoodsDataEntity objectFromData(String str) {

                    return new Gson().fromJson(str, GoodsDataEntity.class);
                }

                public void setIntroContent(String introContent) {
                    this.introContent = introContent;
                }

                public void setCellHeight(String cellHeight) {
                    this.cellHeight = cellHeight;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public void setCountry(String country) {
                    this.country = country;
                }

                public void setEnglish(String english) {
                    this.english = english;
                }

                public String getIntroContent() {
                    return introContent;
                }

                public String getCellHeight() {
                    return cellHeight;
                }

                public String getName() {
                    return name;
                }

                public String getLocation() {
                    return location;
                }

                public String getCountry() {
                    return country;
                }

                public String getEnglish() {
                    return english;
                }
            }

            public static class DesignDesEntity {
                private String img;
                private String cellHeight;
                private String type;

                public static DesignDesEntity objectFromData(String str) {

                    return new Gson().fromJson(str, DesignDesEntity.class);
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public void setCellHeight(String cellHeight) {
                    this.cellHeight = cellHeight;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getImg() {
                    return img;
                }

                public String getCellHeight() {
                    return cellHeight;
                }

                public String getType() {
                    return type;
                }
            }

            public static class WearVideoEntity {
                private String type;
                private String data;

                public static WearVideoEntity objectFromData(String str) {

                    return new Gson().fromJson(str, WearVideoEntity.class);
                }

                public void setType(String type) {
                    this.type = type;
                }

                public void setData(String data) {
                    this.data = data;
                }

                public String getType() {
                    return type;
                }

                public String getData() {
                    return data;
                }
            }
        }
    }
}
