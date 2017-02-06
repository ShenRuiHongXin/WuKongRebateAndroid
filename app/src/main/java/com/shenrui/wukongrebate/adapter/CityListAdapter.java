package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.db.City;
import com.shenrui.wukongrebate.view.MyGridView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/4.
 */

public class CityListAdapter extends BaseAdapter {
    Context context;
    private String currentCity;
    private List<City> allCity;
    private List<String> hotCity;
    public HashMap<String,Integer> map;
    View.OnClickListener listener;

    public CityListAdapter(final Context context, String currentCity, final List<City> allCity, List<String> hotCity) {
        this.context = context;
        this.currentCity = currentCity;
        this.allCity = allCity;
        this.hotCity = hotCity;

        //保存每个条目所在的位置，方便滑动时获得位置
        map = new HashMap<>();
        map.put("定位",0);
        map.put("热门",1);
        for(int i=0;i<allCity.size();i++){
            if (map.get(allCity.get(i).getFirstPY())==null){
                map.put(allCity.get(i).getFirstPY(),i+2);
            }
        }

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Toast.makeText(context, allCity.get(position).getCity(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public int getCount() {
        return allCity.size()+2;
    }


    @Override
    public Object getItem(int position) {
        return allCity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case 0://当前定位城市
                convertView = View.inflate(context, R.layout.layout_item_current_city,null);
                TextView tvCurrentCity = (TextView) convertView.findViewById(R.id.tvCurrentCity);
                tvCurrentCity.setText(currentCity);
                break;
            case 1://热门城市
                convertView = View.inflate(context,R.layout.layout_item_hot_city,null);
                MyGridView gridView = (MyGridView) convertView.findViewById(R.id.gridView_hotCity);
                gridView.setAdapter(new HotCityAdapter());
                break;
            case 2://所有城市
                ViewHolder holder = null;
                if(convertView==null){
                    holder = new ViewHolder();
                    convertView = View.inflate(context,R.layout.layout_item_city,null);
                    holder.tvCityName = (TextView) convertView.findViewById(R.id.tvCity);
                    holder.tvFirstPy = (TextView) convertView.findViewById(R.id.tvCityFirstPY);
                    convertView.setTag(holder);
                }else{
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.tvCityName.setText(allCity.get(position-2).getCity());
                if(position==2 || !allCity.get(position-2).getFirstPY().equals(allCity.get(position-3).getFirstPY())){
                    holder.tvFirstPy.setVisibility(View.VISIBLE);
                    holder.tvFirstPy.setText(allCity.get(position-2).getFirstPY());
                }else{
                    holder.tvFirstPy.setVisibility(View.GONE);
                }
                holder.tvCityName.setTag(position-2);
                holder.tvCityName.setOnClickListener(listener);
                break;
        }
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position<2 ? position:2;
    }

    class ViewHolder{
        TextView tvCityName;
        TextView tvFirstPy;
    }



    class HotCityAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return hotCity.size();
        }

        @Override
        public Object getItem(int position) {
            return hotCity.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            HotCityHolder holder = null;
            if(convertView==null){
                holder = new HotCityHolder();
                convertView = View.inflate(context, R.layout.layout_hot_city_item, null);
                holder.tvHotCity = (TextView) convertView.findViewById(R.id.tvHotCity);
                convertView.setTag(holder);
            }else{
                holder = (HotCityHolder) convertView.getTag();
            }
            holder.tvHotCity.setText(hotCity.get(position));
            holder.tvHotCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, hotCity.get(position), Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
    }
    class HotCityHolder{
        TextView tvHotCity;
    }
}
