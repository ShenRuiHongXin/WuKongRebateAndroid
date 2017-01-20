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

    //淘宝客app key
    public static final String APP_KEY = "23585348";
    public static final String APP_SECRET = "ac4a7853f5ae6b37642358c8ba8c7aef";

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


    public static final CatsItemLocal[] Itemcats = {
            new CatsItemLocal("悟空推荐",new int[]{}),
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
            new CatsItemLocal("3C产品",new int[]{PHONE,PAD,LAPTOP,CAMERA}),
            new CatsItemLocal("鞋包配饰",new int[]{MAN_SHOSE,WOMEN_SHOSE,BAG,CLOTHES_PARTS}),
            new CatsItemLocal("家居饰品",new int[]{HOME_DECOR,HOME_FABRIC,HOME_DAILY,home_Arrange}),
            new CatsItemLocal("化妆品",new int[]{COSMETITICS}),
            new CatsItemLocal("流行首饰",new int[]{ORNAMENT}),
            new CatsItemLocal("床上用品",new int[]{BEDDING})
    };

    //服务器根地址
    public static final String SERVICE_URL = "http://192.168.0.4:8080/WukongServer/";
    //Host
    public static final String HOST = "http://192.168.0.4:8080/";
    //注册/登录 成功的结果码
    public static final int CODE_SUCCESS = 200;
    //手机号已被注册
    public static final int REGISTER_PHONE_HAVE_USED = 401;
    //手机号还未注册
    public static final int LOGIN_PHONE_NO_REGISTER = 402;
    //密码错误
    public static final int LOGIN_PASSWORD_ERROR = 403;
}
