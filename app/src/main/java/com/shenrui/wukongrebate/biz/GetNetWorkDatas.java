package com.shenrui.wukongrebate.biz;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.CatsItemLocal;
import com.shenrui.wukongrebate.entities.TbkFavorite;
import com.shenrui.wukongrebate.entities.TbkItem;
import com.shenrui.wukongrebate.entities.TenGoodsData;
import com.shenrui.wukongrebate.entities.UatmTbkItem;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.TaobaoReqUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by heikki on 2017/1/6.
 */

public class GetNetWorkDatas {

    private static OkHttpClient okHttpClient;

    public static List getTenNewGoods(){
        Map map = new HashMap<String, String>();
        map.put("fields", "num_iid,pict_url,title,zk_final_price");
        map.put("cat", "16,50006843,30,50011740");
        map.put("page_size", "10");

        String url = "http://gw.api.taobao.com/router/rest?" + TaobaoReqUtil.GenerateTaobaoReqStr("taobao.tbk.item.get", map);
        LogUtil.i("main url: " + url);


        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        List<TenGoodsData> tenGoodsDataList = null;
        try {
            Response response = okHttpClient.newCall(request).execute();
            String responseJson = response.body().string();
            LogUtil.i(responseJson);
            JSONObject jsonObject = (JSONObject) JSON.parse(responseJson);
            JSONObject jsonObject1 = jsonObject.getJSONObject("tbk_item_get_response");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("results");
            JSONArray jsonArrayItems = jsonObject2.getJSONArray("n_tbk_item");
            tenGoodsDataList = JSON.parseArray(jsonArrayItems.toString(), TenGoodsData.class);
            LogUtil.i("goods size: " + tenGoodsDataList.size());
            LogUtil.i("list : " + tenGoodsDataList.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){

        }
        return tenGoodsDataList;
    }

    /**
     * 根据分类获取商品
     * @return
     */
    public static List getCatsGoodsFromTaobao(CatsItemLocal catsItemLocal){
        Map map = new HashMap<String, String>();
        map.put("fields", "num_iid,pict_url,title,zk_final_price");
        String cidString = "";
        for (int integer : catsItemLocal.getCids()){
            if(cidString.equals("")){
                cidString += integer;
            }else{
            cidString = cidString+","+integer;

            }
        }
        LogUtil.i("cidString: " + cidString);
        map.put("cat", cidString);
        String url = "http://gw.api.taobao.com/router/rest?" + TaobaoReqUtil.GenerateTaobaoReqStr("taobao.tbk.item.get", map);
        LogUtil.i("main url: " + url);


        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        List<TenGoodsData> goodsDataList = null;
        try {
            Response response = okHttpClient.newCall(request).execute();
            String responseJson = response.body().string();
            LogUtil.i(responseJson);
            JSONObject jsonObject = (JSONObject) JSON.parse(responseJson);
            JSONObject jsonObject1 = jsonObject.getJSONObject("tbk_item_get_response");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("results");
            JSONArray jsonArrayItems = jsonObject2.getJSONArray("n_tbk_item");
            goodsDataList = JSON.parseArray(jsonArrayItems.toString(), TenGoodsData.class);
            LogUtil.i("goods size: " + goodsDataList.size());
            LogUtil.i("list : " + goodsDataList.toString());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return goodsDataList;
    }
    /**
     * 关键字查询商品
     * @param q
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static Map<String,Object> getSearchGoods(String q,int pageNo,int pageSize){
        DecimalFormat df = new DecimalFormat("#####0");
        Map<String,String> map = new HashMap<>();
        map.put("fields", "num_iid,title,pict_url,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        map.put("page_no",String.valueOf(pageNo));
        map.put("q",q);
        map.put("page_size",String.valueOf(pageSize));
        map.put("start_price","1");
        map.put("end_price","1000000");
        map.put("start_tk_rate","3000");//淘宝客佣金比率10%-100%
        map.put("end_tk_rate","10000");
        map.put("sort","total_sales_des");

        String url = "http://gw.api.taobao.com/router/rest?" + TaobaoReqUtil.GenerateTaobaoReqStr("taobao.tbk.item.get", map);
        LogUtil.d("Request url:"+url);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        HashMap<String, Object> result = new HashMap<>();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String responseJson = response.body().string();
            LogUtil.i(responseJson);
            JSONObject jsonObject = (JSONObject) JSON.parse(responseJson);
            JSONObject jsonObject1 = jsonObject.getJSONObject("tbk_item_get_response");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("results");
            Double results = jsonObject1.getDouble("total_results");
            JSONArray jsonArrayItems = jsonObject2.getJSONArray("n_tbk_item");
            List<TbkItem> goodsList = JSON.parseArray(jsonArrayItems.toString(), TbkItem.class);
            LogUtil.d("getSearchGoods goodsList:"+goodsList.toString());
            LogUtil.d("getSearchGoods results:"+String.valueOf(results));
            result.put("goodsList",goodsList);
            result.put("totals",df.format(results));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 关键字查询商品 # 重载
     * @param q
     * @param pageNo
     * @return
     */
    public static Map<String,Object> getSearchGoods(String q,int pageNo){
        return getSearchGoods(q,pageNo,20);
    }

    /**
     * 九块九商品
     */
    public static Map<String,Object> getNineGoods(String q,int page_no){
        Map<String,String> map = new HashMap<>();
        DecimalFormat df = new DecimalFormat("#####0");
        map.put("fields", "num_iid,title,pict_url,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        map.put("page_no",String.valueOf(page_no));
        map.put("q",q);
        map.put("page_size","20");
        map.put("start_price","1");
        map.put("end_price","49");
        map.put("start_tk_rate","3000");//淘宝客佣金比率30%-90%
        map.put("end_tk_rate","9000");

        String url = "http://gw.api.taobao.com/router/rest?" + TaobaoReqUtil.GenerateTaobaoReqStr("taobao.tbk.item.get", map);
        Log.e("DeDiWang nineGoods url",url);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Map<String,Object> result = new HashMap<>();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String responseJson = response.body().string();
            LogUtil.i(responseJson);
            JSONObject jsonObject = (JSONObject) JSON.parse(responseJson);
            JSONObject jsonObject1 = jsonObject.getJSONObject("tbk_item_get_response");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("results");
            Double results = jsonObject1.getDouble("total_results");
            JSONArray jsonArrayItems = jsonObject2.getJSONArray("n_tbk_item");
            List<TbkItem> goodsList = JSON.parseArray(jsonArrayItems.toString(), TbkItem.class);
            Log.e("DeDiWang",goodsList.toString());
            Log.e("DeDiWang",String.valueOf(results));
            result.put("goodsList",goodsList);
            result.put("totals",df.format(results));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){

        }
        return result;
    }

    /**
     * 获取九块九榜单10个商品
     */
    public static List<TbkItem> getNineRankGoods(String q){
        Map<String,String> map = new HashMap<>();
        map.put("fields", "num_iid,title,pict_url,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        map.put("page_no","1");
        map.put("q",q);
        map.put("page_size","10");
        map.put("start_price","1");
        map.put("end_price","49");
        map.put("start_tk_rate","2000");//淘宝客佣金比率20%-90%
        map.put("end_tk_rate","9000");
        map.put("sort","total_sales_des");//销量降序排列

        String url = "http://gw.api.taobao.com/router/rest?" + TaobaoReqUtil.GenerateTaobaoReqStr("taobao.tbk.item.get", map);
        Log.e("DeDiWang nineRank url",url);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        List<TbkItem> goodsList = null;
        try {
            Response response = okHttpClient.newCall(request).execute();
            String responseJson = response.body().string();
            LogUtil.i(responseJson);
            JSONObject jsonObject = (JSONObject) JSON.parse(responseJson);
            JSONObject jsonObject1 = jsonObject.getJSONObject("tbk_item_get_response");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("results");
            JSONArray jsonArrayItems = jsonObject2.getJSONArray("n_tbk_item");
            goodsList = JSON.parseArray(jsonArrayItems.toString(), TbkItem.class);
            Log.e("DeDiWang",goodsList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){

        }
        return goodsList;
    }

    /**
     * 超级返商品
     */
    public static Map<String,Object> getSuperGoods(String q,int page_no){
        DecimalFormat df = new DecimalFormat("#####0");
        Map<String,String> map = new HashMap<>();
        map.put("fields", "num_iid,title,pict_url,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        map.put("page_no",String.valueOf(page_no));
        map.put("q",q);
        map.put("page_size","50");
        map.put("start_price","50");
        map.put("end_price","100000");
        map.put("start_tk_rate","3500");//淘宝客佣金比率35%-95%
        map.put("end_tk_rate","9500");

        String url = "http://gw.api.taobao.com/router/rest?" + TaobaoReqUtil.GenerateTaobaoReqStr("taobao.tbk.item.get", map);
        Log.e("DeDiWang url",url);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String responseJson = response.body().string();
            LogUtil.i(responseJson);
            JSONObject jsonObject = (JSONObject) JSON.parse(responseJson);
            JSONObject jsonObject1 = jsonObject.getJSONObject("tbk_item_get_response");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("results");
            Double results = jsonObject1.getDouble("total_results");
            JSONArray jsonArrayItems = jsonObject2.getJSONArray("n_tbk_item");
            List<TbkItem> goodsList = JSON.parseArray(jsonArrayItems.toString(), TbkItem.class);
            Log.e("DeDiWang",goodsList.toString());
            Log.e("DeDiWang",String.valueOf(results));
            result.put(Constants.GOODS,goodsList);
            result.put(Constants.TOTALS,df.format(results));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){

        }
        return result;
    }

    /**
     * 获取选品库列表
     */
    public static List<TbkFavorite> getFavorites(int pageNo){
        HashMap<String, String> map = new HashMap<>();
        map.put("page_no",String.valueOf(pageNo));
        map.put("page_size",String.valueOf(20));
        map.put("fields","favorites_title,favorites_id,type");
        map.put("type",String.valueOf(-1));

        String url = "http://gw.api.taobao.com/router/rest?" + TaobaoReqUtil.GenerateTaobaoReqStr("taobao.tbk.uatm.favorites.get", map);
        Log.e("DeDiWang url",url);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        List<TbkFavorite> favorites = null;
        try {
            Response response = client.newCall(request).execute();
            String responseJson = response.body().string();
            JSONObject jsonObject = (JSONObject) JSON.parse(responseJson);
            JSONObject jsonObject1 = jsonObject.getJSONObject("tbk_uatm_favorites_get_response");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("results");
            Double results = jsonObject1.getDouble("total_results");
            JSONArray jsonArrayItems = jsonObject2.getJSONArray("tbk_favorites");
            favorites = JSON.parseArray(jsonArrayItems.toString(), TbkFavorite.class);
            Log.e("DeDiWang favorites",favorites.toString());
            Log.e("DeDiWang results",String.valueOf(results));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return favorites;
    }

    /**
     * 获取指定选品库商品
     */
    public static Map<String,Object> getFavoritesGoods(int favorites_id,int pageNo,int pageSize){
        HashMap<String, String> map = new HashMap<>();
        map.put("page_no",String.valueOf(pageNo));
        map.put("page_size",String.valueOf(pageSize));
        map.put("adzone_id",String.valueOf(73328735));
        map.put("favorites_id",String.valueOf(favorites_id));
        map.put("fields","num_iid,title,pict_url,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type");

        String url = "http://gw.api.taobao.com/router/rest?" + TaobaoReqUtil.GenerateTaobaoReqStr("taobao.tbk.uatm.favorites.item.get", map);
        Log.e("DeDiWang url",url);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Map<String,Object> result = new HashMap<>();
        try {
            Response response = client.newCall(request).execute();
            String responseJson = response.body().string();
            JSONObject jsonObject = (JSONObject) JSON.parse(responseJson);
            JSONObject jsonObject1 = jsonObject.getJSONObject("tbk_uatm_favorites_item_get_response");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("results");
            int results = jsonObject1.getInteger("total_results");
            JSONArray jsonArrayItems = jsonObject2.getJSONArray("uatm_tbk_item");
            List<UatmTbkItem> favorites = JSON.parseArray(jsonArrayItems.toString(), UatmTbkItem.class);
            Log.e("DeDiWang favorites",favorites.toString());
            Log.e("DeDiWang results",String.valueOf(results));
            result.put(Constants.GOODS,favorites);
            result.put(Constants.TOTALS,results);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
