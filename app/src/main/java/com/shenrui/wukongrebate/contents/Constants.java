package com.shenrui.wukongrebate.contents;

import com.shenrui.wukongrebate.entities.CatsItemLocal;

/**
 * Created by heikki on 2017/1/3.
 */

public class Constants {
    public static final String SIGN_METHOD_MD5 = "md5";
    public static final String SIGN_METHOD_HMAC = "hmac";
    public static final String CHARSET_UTF8 = "utf-8";

    public static final boolean IS_DEBUG = true;
    //正式环境
    public static final String TAOBAO_API_ROOT_URL = "http://gw.api.taobao.com/router/rest?";
    //淘宝客app key
    //public static final String APP_KEY = "23585348";
    public static final String APP_KEY = "23665752";
    //public static final String APP_SECRET = "ac4a7853f5ae6b37642358c8ba8c7aef";
    public static final String APP_SECRET = "efcb1281a2de520742aa08f7bcfe46f3";

    //支付宝APP_ID
    public static final String ALIPAY_APP_ID = "2017042506957781";
    //支付宝私钥
    public static final String ALIPAY_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCP13UAL2brucFn6NTow82ootkOBl2i9qBX6xv2NPg+R3L/kQKbqOM2EWoBnYjxC22nLbmoxnfaLAdevNRbYZmbIpvGDf0YP6ztZg6uFDTYbdLkSfnLpNL07Rm3djQhSVRIAqt+OW2l6voLfRVWFwPA/1lJpgSvRzmm64C3OkcaSVeotJPb0KehT0dxuTdX7ObwTbkuJanPvzOhUD9lAvTTXqgFiTu3B4eeRko+/WJ8B6TemmVbZY+f0Qpg/FztjFtK5naYD8gSy/n8v73b/FWPZm9O3+RvmAlhfNnpxTIDd37YO4hU+k2FKrclFfFrNtcNP8CvEjMmPVEP4G1FWE3bAgMBAAECggEAHFXHGIg98AWhkcSN3GRl6OhbdaXPYEDRCUCpO3DnzezpPGrmZp94UrhPd43+4ezsZthdEGA4T+QId6Me+flZ9vN0WCl9R/+TIxjNn4Eff9ryhkIEURdg7xqdzEQetMfrMFxGSBEsBlkpdIXpZBIYjTY/8oAfwB95veX6bx2t0AGA5itBGI0nwcgMFwH4uQea8UbEiZnolRODi9YJmWV/N+dR8hHIkL2h6d1KwA66IsD4b7gWcvnMzSCKoHcs7mb+06ouZBG+4gVIaQa3iu4B82uz+Bb6Xu/X8udVzpCgmYC9rb4EsW7JXn62sm+s+360V5MFc3J/xNp7GKOFKmxy8QKBgQDzIqw22g/yH5nIoY0dKyBHwFvhNcmP8opz8CM4KtZt42slVkVNJPSOMmglivuVNuuvOUTfSZgND6zTtLdew/iFbC59NHt0uVqCtchW2IDAadRG0L1syqh66bmbju1FIZDewleYIkNwkukXHQJlkJdx3f9r3kSuFz5tGnc/M3FdhwKBgQCXc9N4+G2JoAMwtA19ntAcxTpD1fdnGcOCbno3WFU0iZSHnU4cmsFsC95ef92W1ATatB3WwUACrvg8hcOtG3/7QWkNImCDBjwny0loAxhWfyDTCALFQkyUUpOZS5sQpwUjtGoEb2c2J3AK2b8+DyM5wJM9AsY87m42+5XhNiiCDQKBgDpcQUAtWZSBt1UsSgYNWwM+Dg/h7E7sJgWsR5fs2GmqJAbjTWE2FKmpOcyBLuBjPXFi6sA6FxBTieWLRFSv75B/Ji2RkmyzLSGg5sTJ1SXagUS6SC27+3L9dTr8tMFaTD7cIVB4MP3tlZuhr/iXG3MOmYfX415yX96qcqMuAvEzAoGAcoTXa8pVcVI391S+OrWiYY7UnsSknSYDCpYk/cJiMyaw09ajYMe3584Vp9kBofHRddnqEh/wGddZxtHSgwufO0pbd939ojVNuT1i1S458fWQyWFuTt/x7dl+19QufWn9XeEmpRHjtjQd6WZ60B88IIg4DVMSuLIFTrfvvepouOUCgYEAqYerHcF0y7qZUc90qhvLheBXfT5lvT8Y2W5thjYQK0P1HvBWDEfywcUcfncD2lGsNAifNzRGuJ05M+Aq2uyMd0GxsDqkQ41KcInM3pSTkNAk3YyV7LjXJ5sjb/OX0qISoFfwbVz/CA9ufAH4lprF4UAoS2pXniWkQ4IHI4JuGPs=";
    //支付宝商户ID
    public static final String ALIPAY_PID = "2088521511776698";

    //返给用户的佣金比率
    public static final double RATE_FAN = 0.15;

    //分类(从淘宝API：taobao.itemcats.get获取到分类根目录下的所有父目录，只选取热门分类作为APP展示目录)
    public static final int COSMETITICS = 1801;         //化妆品

    public static final int WOMEN_CLOTHES = 16;         //女装
    public static final int WOMEN_SHOSE = 50006843;     //女鞋
    public static final int MEN_CLOTHES = 30;           //男装
    public static final int MAN_SHOSE = 50011740;       //流行男鞋

    public static final int PHONE = 1512;               //手机
    public static final int CAMERA = 14;                //数码相机/单反相机/摄像机
    public static final int MP3 = 1201;                 //MP3/MP4/iPod/录音笔
    public static final int LAPTOP = 1101;              //笔记本电脑
    public static final int PAD = 50019780;             //平板电脑/MID
    public static final int DIY_PC = 50018222;          //DIY电脑
    public static final int PC_PARTS = 11;              //电脑硬件/显示器/电脑周边
    public static final int NET_PARTS =50018264;        //网络设备/网络相关
    public static final int PARTS_3C = 50008090;        //3C数码配件
    public static final int HARD_DISK = 50012164;       //闪存卡/U盘/存储/移动硬盘

    public static final int MILK_POWDER = 35;           //奶粉/辅食/营养品/零食
    public static final int DIAPERS = 50014812;         //尿片/洗护/喂哺/推车床
    public static final int MATERNITY_DRESS = 50022517; //孕妇装/孕产妇用品/营养
    public static final int BABY_DRESS = 50008165;      //童装/婴儿装/亲子装
    public static final int BABY_SHOSE = 122650005;     //童鞋/婴儿鞋/亲子鞋

    public static final int BEDDING = 50008163;         //床上用品
    public static final int BAG = 50006842;             //箱包皮具/热销女包/男包
    public static final int UNDERWEAR = 1625;           //女士内衣/男士内衣/家居服
    public static final int CLOTHES_PARTS = 50010404;   //服饰配件/皮带/帽子/围巾

    public static final int HOME_DECOR = 50020808;      //家居饰品
    public static final int HOME_FABRIC = 122852001;    //居家布艺
    public static final int HOME_DAILY = 21;            //居家日用
    public static final int home_Arrange = 122928002;   //收纳整理

    public static final int ORNAMENT = 50013864;        //饰品/流行首饰/时尚饰品新

    //超级返分类条目
    public static final CatsItemLocal[] ItemSuperCats = {
            new CatsItemLocal("全部",new int[]{}),
            new CatsItemLocal("女装",new int[]{WOMEN_CLOTHES}),
            new CatsItemLocal("男装",new int[]{MEN_CLOTHES}),
            new CatsItemLocal("化妆品",new int[]{COSMETITICS}),
            new CatsItemLocal("母婴童装",new int[]{BABY_DRESS,BABY_SHOSE}),
            new CatsItemLocal("3C产品",new int[]{PHONE,PAD,LAPTOP,CAMERA}),
            new CatsItemLocal("鞋包配饰",new int[]{MAN_SHOSE,WOMEN_SHOSE,BAG,CLOTHES_PARTS}),
            new CatsItemLocal("家居饰品",new int[]{HOME_DECOR,HOME_FABRIC,HOME_DAILY,home_Arrange}),
            new CatsItemLocal("床上用品",new int[]{BEDDING})
    };

    //9块9分类条目
    public static final CatsItemLocal[] ItemNineCats = {
            new CatsItemLocal("全部",new int[]{}),
            new CatsItemLocal("女装",new int[]{WOMEN_CLOTHES}),
            new CatsItemLocal("男装",new int[]{MEN_CLOTHES}),
            new CatsItemLocal("3C产品",new int[]{PARTS_3C,PHONE,PAD,LAPTOP,CAMERA}),
            new CatsItemLocal("鞋包配饰",new int[]{MAN_SHOSE,WOMEN_SHOSE,BAG,CLOTHES_PARTS}),
            new CatsItemLocal("家居饰品",new int[]{HOME_DECOR,HOME_FABRIC,HOME_DAILY,home_Arrange}),
            new CatsItemLocal("化妆品",new int[]{COSMETITICS}),
            new CatsItemLocal("流行首饰",new int[]{ORNAMENT,MAN_SHOSE,WOMEN_SHOSE,BAG,CLOTHES_PARTS}),
            new CatsItemLocal("床上用品",new int[]{BEDDING})
    };

    //服务器根地址    正式环境
    public static final String SERVICE_ONLINE_URL = "http://106.14.156.4/WukongServer/";
    public static final String SERVICE_ONLINE_HOST = "http://106.14.156.4/";
    //服务器根地址 测试
    public static final String SERVICE_DEBUG_URL = "http://192.168.0.6:8080/WukongServer/";
    public static final String SERVICE_DEBUG_HOST = "http://192.168.0.6:8080/";
    //Host
    public static final String HOST = "http://192.168.0.4:8080/";
    //数据库路径
    public static final String DATABASE_PATH = "/data/data/com.shenrui.wukongrebate/databases/common_info.db";
    //菜谱API host
    public static final String FOODMENU_HOST = "http://jisusrecipe.market.alicloudapi.com";
    //菜谱分类API path
    public static final String FOODMENU_CATS = "/recipe/class";
    //菜谱搜索API path
    public static final String FOODMENU_SEARCH = "/recipe/search";
    //菜谱详情API path
    public static final String FOODMENU_DETAIL = "/recipe/detail";
    //菜谱分类检索API path
    public static final String FOODMENU_BY_CATS = "/recipe/byclass";
    //菜谱API AppCode
    public static final String FOODMENU_APPCODE = "9c5b9f4618c4425eb871638160b9237c";



    //注册/登录 成功的结果码
    public static final int CODE_SUCCESS = 200;
    //手机号已被注册
    public static final int REGISTER_PHONE_HAVE_USED = 401;
    //手机号还未注册
    public static final int LOGIN_PHONE_NO_REGISTER = 402;
    //密码错误
    public static final int LOGIN_PASSWORD_ERROR = 403;

    //价格从低到高排列
    public static final int SORT_PRICE_ASC = 123;
    //价格从高到低排列
    public static final int SORT_PRICE_DESC = 321;
    //销量从低到高排列
    public static final int SORT_VOLUME_ASC = 456;
    //销量从高到低排列
    public static final int SORT_VOLUME_DESC = 654;
    //综合排序
    public static final int SORT_NO = 111;

    //三方登录
    public static final String SINA = "sina";
    public static final String QQ = "qq";
    public static final String WECHAT = "wechat";
    public static final String TAOBAO = "taobao";
    public static final String TYPE_PHONE = "phone";

    //商品和商品数量
    public static final String GOODS = "goodsList";
    public static final String TOTALS = "totals";
    //默认每页加载的商品数量
    public static final int PAGE_SIZE = 20;

    //跳到值得买界面
    public static final String GOTOZHI = "go_to_zhi";

    //某品牌选品库id
    public static final String FAVORITES_ID = "favorites_id";

    //周刊数据
    public static final String FOOD_ARTICLE = "article";
    //视频数据
    public static final String FOOD_VIDEO = "video";
}
