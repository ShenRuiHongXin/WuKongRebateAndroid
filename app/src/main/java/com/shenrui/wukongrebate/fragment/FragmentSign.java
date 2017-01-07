package com.shenrui.wukongrebate.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.SignContentRecyAdapter;
import com.shenrui.wukongrebate.entities.SignRecyItemData;
import com.shenrui.wukongrebate.entities.SplitlineItem;
import com.shenrui.wukongrebate.entities.TaskItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by heikki on 2017/1/3.
 */

@EFragment(R.layout.fragment_sign)
public class FragmentSign extends BaseFragment {

    @ViewById(R.id.rv_sign_content)
    RecyclerView rvSignContent;

    private List<SignRecyItemData> listData;


    @AfterViews
    void init(){
        listData = new ArrayList<>();
        rvSignContent.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listData.add(new SignRecyItemData("user",SignRecyItemData.ITEM_USER));

        listData.add(new SignRecyItemData(new SplitlineItem(SplitlineItem.OFFICIAL_TASK_LINE),SignRecyItemData.ITEM_SPLITLINE));
        listData.add(new SignRecyItemData(new TaskItem(TaskItem.OFFICIAL_TASK),SignRecyItemData.ITEM_TASK));
        listData.add(new SignRecyItemData(new TaskItem(TaskItem.OFFICIAL_TASK),SignRecyItemData.ITEM_TASK));

        listData.add(new SignRecyItemData(new SplitlineItem(SplitlineItem.SHOP_TASK_LINE),SignRecyItemData.ITEM_SPLITLINE));
        listData.add(new SignRecyItemData(new TaskItem(TaskItem.SHOP_TASK),SignRecyItemData.ITEM_TASK));
        listData.add(new SignRecyItemData(new TaskItem(TaskItem.SHOP_TASK),SignRecyItemData.ITEM_TASK));
        listData.add(new SignRecyItemData(new TaskItem(TaskItem.SHOP_TASK),SignRecyItemData.ITEM_TASK));
        listData.add(new SignRecyItemData(new TaskItem(TaskItem.SHOP_TASK),SignRecyItemData.ITEM_TASK));
        listData.add(new SignRecyItemData(new TaskItem(TaskItem.SHOP_TASK),SignRecyItemData.ITEM_TASK));

        listData.add(new SignRecyItemData(new SplitlineItem(SplitlineItem.CENTER_LINE),SignRecyItemData.ITEM_SPLITLINE));
        listData.add(new SignRecyItemData(new TaskItem(TaskItem.SHOP_TASK),SignRecyItemData.ITEM_TASK));

        listData.add(new SignRecyItemData(new SplitlineItem(SplitlineItem.END_LINE),SignRecyItemData.ITEM_SPLITLINE));


        rvSignContent.setAdapter(new SignContentRecyAdapter(getActivity(),listData));
    }

}
