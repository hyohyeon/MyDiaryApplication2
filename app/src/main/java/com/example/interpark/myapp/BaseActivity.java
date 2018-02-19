package com.example.interpark.myapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hyohyeon on 2018-02-08.
 */

public class BaseActivity extends Activity implements View.OnClickListener{

    private static Typeface typeface;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if(typeface == null) {
            typeface = Typeface.createFromAsset(this.getAssets(), "BMHANNA_11yrs_ttf.ttf");
        }
        setGlobalFont(getWindow().getDecorView());
    }

    private void setGlobalFont(View view) {
        if(view != null) {
            if(view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup)view;
                int vgCnt = viewGroup.getChildCount();
                for(int i = 0; i<vgCnt; i++) {
                    View v = viewGroup.getChildAt(i);
                    if(v instanceof TextView) {
                        ((TextView) v).setTypeface(typeface);
                    }
                    setGlobalFont(v);
                }
            }
        }
    }
    /* 메뉴버튼과 로그아웃버튼에 대한 이벤트를 처리하는 함수임 */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibMenu :
                Toast.makeText(getApplicationContext(), "메뉴버튼",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ibLogout :
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

}