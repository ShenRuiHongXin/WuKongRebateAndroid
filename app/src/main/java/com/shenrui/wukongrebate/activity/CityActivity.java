package com.shenrui.wukongrebate.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.CityListAdapter;
import com.shenrui.wukongrebate.contents.MyApplication;
import com.shenrui.wukongrebate.db.City;
import com.shenrui.wukongrebate.db.MyCityDBHelper;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;
import com.shenrui.wukongrebate.view.MyLetterView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_city)
public class CityActivity extends BaseActivity {
    @ViewById(R.id.toolbar_left_image)
    ImageView toolbar_left_image;
    @ViewById(R.id.toolbar_left_text)
    TextView toolbar_left_text;
    @ViewById(R.id.toolbar_title)
    TextView toolbar_title;
    @ViewById(R.id.toolbar_right_image)
    ImageView toolbar_right_image;
    @ViewById(R.id.tvDialog)
    TextView tvDialog;
    @ViewById(R.id.myLetterView)
    MyLetterView myLetterView;
    @ViewById(R.id.etCity)
    EditText etCity;
    @ViewById(R.id.ivFindCity)
    ImageView ivFindCity;
    @ViewById(R.id.listView)
    ListView listView;
    @ViewById(R.id.result_listView)
    ListView resultListView;
    @ViewById(R.id.noResult_textView)
    TextView noResultTextView;

    String currentCity = SharedPreferenceUtils.getInstance(this).getCurrentCity();
    List<City> allCity;
    List<String> hotCity;
    CityListAdapter cityListAdapter;
    Context context;
    @AfterViews
    void initView(){
        context = this;
        toolbar_left_image.setImageResource(R.drawable.common_btn_back_n);
        toolbar_left_text.setVisibility(View.GONE);
        toolbar_title.setText("切换城市");
        toolbar_right_image.setVisibility(View.GONE);

        getAllCity();
        hotCity = new ArrayList<String>(){
            {add("北京");add("上海");add("广州");add("深圳");
             add("杭州");add("天津");add("成都");add("青岛");
            }
        };

        cityListAdapter = new CityListAdapter(this, currentCity, allCity, hotCity);
        listView.setAdapter(cityListAdapter);

        setListener();

    }

    private void setListener() {
        //对右边字母栏设置滑动监听
        myLetterView.setTextView(tvDialog);
        myLetterView.setOnSlidingListener(new MyLetterView.OnSlidingListener() {
            @Override
            public void sliding(String str) {
                tvDialog.setText(str);
                int position = cityListAdapter.map.get(str);
                listView.setSelection(position);
            }
        });

        //对搜索框设置监听
        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString()==null||s.toString().equals("")){
                    listView.setVisibility(View.VISIBLE);
                    myLetterView.setVisibility(View.VISIBLE);
                    resultListView.setVisibility(View.GONE);
                    noResultTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getAllCity() {
        MyCityDBHelper dbHelper = new MyCityDBHelper(this);
        allCity = dbHelper.getCityDB().getAllCity();
        Log.e("DeDiWang",allCity.toString());
    }

    @Click({R.id.toolbar_left_image,R.id.ivFindCity})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_image:
                MFGT.finish(this);
                break;
            case R.id.ivFindCity:
                String city = etCity.getText().toString();
                if(!city.isEmpty()){
                    listView.setVisibility(View.GONE);
                    myLetterView.setVisibility(View.GONE);
                    //显示搜索结果
                    showResult(city);
                }else{
                    listView.setVisibility(View.VISIBLE);
                    myLetterView.setVisibility(View.VISIBLE);
                    resultListView.setVisibility(View.GONE);
                    noResultTextView.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void showResult(String city) {
        final List<City> result = new ArrayList<>();
        for(int i=0;i<allCity.size();i++){
            if(allCity.get(i).getCity().contains(city) || allCity.get(i).getAllFristPY().contains(city)){
                result.add(allCity.get(i));
            }
        }
        if(result.size()==0){
            noResultTextView.setVisibility(View.VISIBLE);
            resultListView.setVisibility(View.GONE);
        }else{
            resultListView.setVisibility(View.VISIBLE);
            noResultTextView.setVisibility(View.GONE);

            resultListView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return result.size();
                }

                @Override
                public Object getItem(int position) {
                    return result.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {
                    convertView = View.inflate(context,R.layout.layout_item_city,null);
                    TextView tvCity = (TextView) convertView.findViewById(R.id.tvCity);
                    tvCity.setText(result.get(position).getCity());
                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, result.get(position).getCity(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    return convertView;
                }
            });
        }

    }

}
