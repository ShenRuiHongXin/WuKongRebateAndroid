package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.ScreenUtils;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

/**
 * Created by heikki on 2017/4/13.
 */

@EViewGroup(R.layout.invate_code_view)
public class InvateCodeView extends RelativeLayout {
    @ViewsById({R.id.tv_code_1,R.id.tv_code_2,R.id.tv_code_3,R.id.tv_code_4,R.id.tv_code_5})
    List<TextView> tvs;
    private Context context;
    public InvateCodeView(Context context) {
        super(context);
        this.context = context;
    }

    public InvateCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public InvateCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setInvateCode(String invateCode){
        int screenWidth = ScreenUtils.getScreenWidth(context);
        int tvWidth = (int) (screenWidth /4f);
        int marginSize = (int) (tvWidth * 0.3);

        for(int i=0; i<invateCode.length(); i++){
            RelativeLayout.LayoutParams params = (LayoutParams) tvs.get(i).getLayoutParams();
            if (i != 0){
                params.setMargins((tvWidth-marginSize)*i,0,0,0);
            }
            params.width = tvWidth;
            params.height = tvWidth;
            tvs.get(i).setText(invateCode.substring(i,i+1));
        }

    }
}
