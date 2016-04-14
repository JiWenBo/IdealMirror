package com.example.dllo.idealmirror.tool;

/**
 * Created by nan on 16/3/30.
 * 网址的接口
 */
public interface Url {
    /**
     * 网址拼接头
     */
    String URL = "http://api101.test.mirroreye.cn/";
    /**
     * 获得的凭证
     */
    String TOKEN = "1bb26b25b32ccb55890611b8fb9d552f";
    String BROIDCAST = "com.example.dllo.day14broadcast.MY_BROADACAST";

    /**
     * 菜单数据
     */
    String MENU_LIST = URL + "index.php/index/menu_list";
    /**
     * 故事列表
     */
    String STORY_LIST = URL + "index.php/story/story_list";
    /**
     * 故事详情
     */
    String STORY_INFO = URL + "index.php/story/info";
    /**
     * 用户注册
     */
    String USER_REG = URL + "index.php/user/reg";
    /**
     * 登陆
     */
    String USER_LOGIN = URL + "index.php/user/login";
    /**
     * 绑定账号
     */
    String USER_BUNDLING = URL + "index.php/user/bundling";
    /**
     * 商品列表
     */
    String GOODS_LIST = URL + "index.php/products/goods_list";
    /**
     * 获取验证码
     */
    String USER_SEND_CODE = URL + "index.php/user/send_code";
    /**
     * 商品详情
     */
    String GOODS_INFO = URL + "index.php/products/goods_info";
    /**
     * 商品分类列表
     */
    String CATEGORY_LIST = URL + "index.php/products/category_list";
    /**
     * 个人中心
     */
    String USER_INFO = URL + "index.php/user/user_info";
    /**
     * 加入购物车
     */
    String JOIN_SHOPPING_CART = URL + "index.php/order/join_shopping_cart";
    /**
     * 订单列表
     */
    String ORDER_LIST = URL + "index.php/order/order_list";
    /**
     * 购物车列表
     */
    String SHOPPING_CART_LIST = URL + "index.php/order/shopping_cart_list";
    /**
     * 我的收获地址列表
     */
    String USER_ADDRESS_LIST = URL + "index.php/user/address_list";
    /**
     * 添加收获地址
     */
    String USER_ADD_ADDRESS = URL + "index.php/user/add_address";
    /**
     * 编辑收获地址
     */
    String USER_EDIT_ADDRESS = URL + "index.php/user/edit_address";
    /**
     * 删除收获地址
     */
    String USER_DEL_ADDRESS = URL + "index.php/user/del_address";
    /**
     * 设置默认收获地址
     */
    String USER_MR_ADDRESS = URL + "index.php/user/mr_address";
    /**
     * 下订单
     */
    String ORDER_SUB = URL + "index.php/order/sub";
    /**
     * 支付宝支付
     */
    String PAY_ALI = URL + "index.php/pay/ali_pay_sub";
    /**
     * 微信支付
     */
    String PAY_WECHAT = URL + "index.php/pay/wx_pay_sub";
    /**
     * 获取微信支付结果
     */
    String PAY_WECHAT_ORDERQUERY = URL + "index.php/pay/wx_orderquery";
    /**
     * 我的订单详情
     */
    String ORDER_INFO = URL + "index.php/order/info";
    /**
     * 我的优惠劵列表
     */
    String USER_DISCOUNT_LIST = URL + "index.php/user/discount_list";
    /**
     * 测试 故事列表
     */
    String TEST_STORY_LIST = URL + "index.php/story_test/story_list";
    /**
     * 测试 故事详情
     */
    String TEST_STORY_INFO = URL + "index.php/story_test/info";
    /**
     * 测试 商品列表
     */
    String TEST_GOODS_LIST = URL + "index.php/products_test/goods_list";
    /**
     * 测试 商品详情
     */
    String TEST_GOODS_INFO = URL + "index.php/products_test/goods_info";
    /**
     * 每日推荐列表
     */
    String INDEX_MRTJ = URL + "index.php/index/mrtj";
    /**
     * 启动图
     */
    String INDEX_STARTED_IMG = URL + "index.php/index/started_img";
    /**
     * 分享开关
     */
    String INDEX_SHARE_SWITCH = URL + "index.php/index/share_switch";
}
