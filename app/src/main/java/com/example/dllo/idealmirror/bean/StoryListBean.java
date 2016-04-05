package com.example.dllo.idealmirror.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by dllo on 16/4/1.
 */
public class StoryListBean {



    private String result;
    private String msg;


    private DataEntity data;

    public static StoryListBean objectFromData(String str) {

        return new Gson().fromJson(str, StoryListBean.class);
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


        private PaginationEntity pagination;
        

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
            private String story_title;
            private String story_des;
            private String story_img;
            private String story_id;
            private String story_url;
            private String if_original;
            private String original_url;
            private String from;


            private StoryDataEntity story_data;

            public static ListEntity objectFromData(String str) {

                return new Gson().fromJson(str, ListEntity.class);
            }

            public void setStory_title(String story_title) {
                this.story_title = story_title;
            }

            public void setStory_des(String story_des) {
                this.story_des = story_des;
            }

            public void setStory_img(String story_img) {
                this.story_img = story_img;
            }

            public void setStory_id(String story_id) {
                this.story_id = story_id;
            }

            public void setStory_url(String story_url) {
                this.story_url = story_url;
            }

            public void setIf_original(String if_original) {
                this.if_original = if_original;
            }

            public void setOriginal_url(String original_url) {
                this.original_url = original_url;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public void setStory_data(StoryDataEntity story_data) {
                this.story_data = story_data;
            }

            public String getStory_title() {
                return story_title;
            }

            public String getStory_des() {
                return story_des;
            }

            public String getStory_img() {
                return story_img;
            }

            public String getStory_id() {
                return story_id;
            }

            public String getStory_url() {
                return story_url;
            }

            public String getIf_original() {
                return if_original;
            }

            public String getOriginal_url() {
                return original_url;
            }

            public String getFrom() {
                return from;
            }

            public StoryDataEntity getStory_data() {
                return story_data;
            }

            public static class StoryDataEntity {
                private String story_date_type;
                private String story_date_url;
                private String title;
                private String subtitle;
                private String head_img;
                private String if_suggest;
                /**
                 * goods_id : 96Psa1455524521
                 * goods_name : GENTLE MONSTER
                 * goods_pic : http://7xprhi.com2.z0.glb.qiniucdn.com/gmsliver155cbc9a39c911ee63efda10378130330.jpg
                 * goods_price : 1750
                 * goods_des :
                 */

                private List<GoodsDataEntity> goods_data;

                public static StoryDataEntity objectFromData(String str) {

                    return new Gson().fromJson(str, StoryDataEntity.class);
                }

                public void setStory_date_type(String story_date_type) {
                    this.story_date_type = story_date_type;
                }

                public void setStory_date_url(String story_date_url) {
                    this.story_date_url = story_date_url;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setSubtitle(String subtitle) {
                    this.subtitle = subtitle;
                }

                public void setHead_img(String head_img) {
                    this.head_img = head_img;
                }

                public void setIf_suggest(String if_suggest) {
                    this.if_suggest = if_suggest;
                }

                public void setGoods_data(List<GoodsDataEntity> goods_data) {
                    this.goods_data = goods_data;
                }

                public String getStory_date_type() {
                    return story_date_type;
                }

                public String getStory_date_url() {
                    return story_date_url;
                }

                public String getTitle() {
                    return title;
                }

                public String getSubtitle() {
                    return subtitle;
                }

                public String getHead_img() {
                    return head_img;
                }

                public String getIf_suggest() {
                    return if_suggest;
                }

                public List<GoodsDataEntity> getGoods_data() {
                    return goods_data;
                }

                public static class GoodsDataEntity {
                    private String goods_id;
                    private String goods_name;
                    private String goods_pic;
                    private String goods_price;
                    private String goods_des;

                    public static GoodsDataEntity objectFromData(String str) {

                        return new Gson().fromJson(str, GoodsDataEntity.class);
                    }

                    public void setGoods_id(String goods_id) {
                        this.goods_id = goods_id;
                    }

                    public void setGoods_name(String goods_name) {
                        this.goods_name = goods_name;
                    }

                    public void setGoods_pic(String goods_pic) {
                        this.goods_pic = goods_pic;
                    }

                    public void setGoods_price(String goods_price) {
                        this.goods_price = goods_price;
                    }

                    public void setGoods_des(String goods_des) {
                        this.goods_des = goods_des;
                    }

                    public String getGoods_id() {
                        return goods_id;
                    }

                    public String getGoods_name() {
                        return goods_name;
                    }

                    public String getGoods_pic() {
                        return goods_pic;
                    }

                    public String getGoods_price() {
                        return goods_price;
                    }

                    public String getGoods_des() {
                        return goods_des;
                    }
                }
            }
        }
    }
}
