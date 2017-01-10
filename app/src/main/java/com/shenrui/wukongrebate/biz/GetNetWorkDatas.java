package com.shenrui.wukongrebate.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.shenrui.wukongrebate.entities.CatsItemLocal;
import com.shenrui.wukongrebate.entities.TenGoodsData;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.TaobaoReqUtil;

import java.io.IOException;
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

}
