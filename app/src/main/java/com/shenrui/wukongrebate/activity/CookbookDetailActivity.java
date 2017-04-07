package com.shenrui.wukongrebate.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.CookBookBean;
import com.shenrui.wukongrebate.entities.CookbookBeanMaterial;
import com.shenrui.wukongrebate.entities.CookbookBeanProcess;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by heikki on 2017/3/31.
 */

@EActivity(R.layout.activity_cookbook_detail)
public class CookbookDetailActivity extends BaseActivity {
    //菜谱主图
    @ViewById(R.id.iv_cookbook_detail_icon)
    ImageView ivBookIcon;
    //菜谱名称
    @ViewById(R.id.tv_cookbook_detail_title)
    TextView tvBookTitle;
    //菜谱介绍
    @ViewById(R.id.tv_cookbook_detail_content)
    TextView tvBookContent;
    //菜谱材料
    @ViewById(R.id.rv_cookbook_detail_material)
    RecyclerView rvBookMaterial;
    //菜谱工序
    @ViewById(R.id.rv_cookbook_detail_process)
    RecyclerView rvBookProcess;

    private CookbookAdapter materialAdapter;
    private CookbookAdapter processAdapter;
    private LinearLayoutManager materialLayoutManager;
    private LinearLayoutManager processLayoutManager;

    @AfterViews
    void init(){
        Bundle bundle = getIntent().getExtras();
        CookBookBean cookBookBean = (CookBookBean) bundle.getSerializable("cookBookBean");
        initData(cookBookBean);
    }

    void initData(CookBookBean cookBookBean){
        materialLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        processLayoutManager  = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        materialAdapter = new CookbookAdapter(this,cookBookBean.getMaterial());
        processAdapter  = new CookbookAdapter(this,cookBookBean.getProcess());

        rvBookMaterial.setAdapter(materialAdapter);
        rvBookMaterial.setLayoutManager(materialLayoutManager);
        rvBookProcess.setAdapter(processAdapter);
        rvBookProcess.setLayoutManager(processLayoutManager);
        tvBookTitle.setText(cookBookBean.getName());
        tvBookContent.setText("\u3000"+ cookBookBean.getContent().replace("<br />","\n\u3000"));
        Glide.with(this)
                .load(cookBookBean.getPic())
                .into(ivBookIcon);
    }

    @Click({R.id.iv_cookbook_detail_back,R.id.iv_cookbook_detail_share})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_cookbook_detail_back:
                MFGT.finish(this);
                break;
            case R.id.iv_cookbook_detail_share:
                break;
        }
    }

    private class CookbookAdapter extends RecyclerView.Adapter{
        private static final int dataTypeMaterial = 1;
        private static final int dataTypeProcess  = 2;
        private Context context;
        private List<CookbookBeanMaterial> dataMaterial;
        private List<CookbookBeanProcess> dataProcess;
        private int dataType = -1;

        public CookbookAdapter(Context context,List<?> data) {
            this.context = context;
            if (data.get(0) instanceof CookbookBeanMaterial){
                this.dataMaterial = (List<CookbookBeanMaterial>) data;
                dataType = dataTypeMaterial;
            }else if(data.get(0) instanceof CookbookBeanProcess){
                this.dataProcess = (List<CookbookBeanProcess>) data;
                dataType = dataTypeProcess;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view;
            RecyclerView.ViewHolder holder = null;
            switch (dataType){
                case dataTypeMaterial:
                    view = inflater.inflate(R.layout.cookbook_detail_material,parent,false);
                    holder = new MaterialViewHolder(view);
                    break;
                case dataTypeProcess:
                    view = inflater.inflate(R.layout.cookbook_detail_process,parent,false);
                    holder = new ProcessViewHolder(view);
                    break;
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (dataType){
                case dataTypeMaterial:
                    MaterialViewHolder materialViewHolder = (MaterialViewHolder) holder;
                    materialViewHolder.tvMaterialName.setText(dataMaterial.get(position).getMname());
                    materialViewHolder.tvMaterialCount.setText(dataMaterial.get(position).getAmount());
                    break;
                case dataTypeProcess:
                    ProcessViewHolder processViewHolder = (ProcessViewHolder) holder;
                    processViewHolder.tvProcessNum.setText("步骤"+(position+1));
                    Glide.with(context)
                            .load(dataProcess.get(position).getPic())
                            .into(processViewHolder.ivProcessImg);
                    processViewHolder.tvProcessContent.setText(dataProcess.get(position).getPcontent());
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return dataMaterial == null ? dataProcess.size() : dataMaterial.size();
        }

        class MaterialViewHolder extends RecyclerView.ViewHolder{
            private TextView tvMaterialName;
            private TextView tvMaterialCount;

            public MaterialViewHolder(View itemView) {
                super(itemView);
                tvMaterialName = (TextView) itemView.findViewById(R.id.tv_cookbook_material_name);
                tvMaterialCount = (TextView) itemView.findViewById(R.id.tv_cookbook_material_count);
            }
        }
        class ProcessViewHolder extends RecyclerView.ViewHolder{
            private TextView tvProcessNum;
            private ImageView ivProcessImg;
            private TextView tvProcessContent;

            public ProcessViewHolder(View itemView) {
                super(itemView);
                tvProcessNum = (TextView) itemView.findViewById(R.id.tv_cookbook_process_num);
                ivProcessImg = (ImageView) itemView.findViewById(R.id.iv_cookbook_process_img);
                tvProcessContent = (TextView) itemView.findViewById(R.id.tv_cookbook_process_content);
            }
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
