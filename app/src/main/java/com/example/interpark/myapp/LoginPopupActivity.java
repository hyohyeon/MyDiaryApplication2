package com.example.interpark.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hyohyeon on 2018-02-12.
 */

public class LoginPopupActivity extends BaseActivity implements View.OnClickListener {


    EditText etName, etId, etPw, etCkeckPw, etPhone;
    Button btnSave, btnCancel;
    dbHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 타이틀바 제거
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 팝업이 올라오면 배경 블러처리
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        getWindow().setAttributes(layoutParams);
        // 레이아웃 설정
        setContentView(R.layout.join_popup);

        // 사이즈조절
        // 1. 디스플레이 화면 사이즈 구하기
        Display dp = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        // 2. 화면 비율 설정
        int width = (int) (dp.getWidth() * 0.9);
        int height = (int) (dp.getHeight() * 0.7);
        // 3. 현재 화면에 적용
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        // 액티비티 바깥화면이 클릭되어도 종료되지 않게 설정하기
        this.setFinishOnTouchOutside(false);


        //EditText etName, etId, etPw, etCkeckPw, etPhone;
        //Button btnSave, btnCancel;

        etName = (EditText)findViewById(R.id.etName);
        etId = (EditText)findViewById(R.id.etId);
        etPw = (EditText)findViewById(R.id.etPw);
        etCkeckPw = (EditText)findViewById(R.id.etCheckPw);
        etPhone = (EditText)findViewById(R.id.etPhone);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnCancel = (Button)findViewById(R.id.btnCancel);

        helper = new dbHelper(this);

        try {
            db = helper.getWritableDatabase();
            //데이터베이스 객체를 얻기 위하여 getWritableDatabse()를 호출

        } catch (SQLiteException e) {
            db = helper.getReadableDatabase();
        }
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                UserVO userInfo = new UserVO();

                String name = etName.getText().toString();
                String id = etId.getText().toString();
                String pw = etPw.getText().toString();
                String checkPw = etCkeckPw.getText().toString();
                String phone = etPhone.getText().toString();


                if(name.equals("") || id.equals("") || pw.equals("") || checkPw.equals("") || phone.equals("")){
                    Toast.makeText(getApplicationContext(),"공백없이 입력하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                userInfo.setName(name);
                userInfo.setId(id);
                userInfo.setPasswd(pw);
                userInfo.setPhone(phone);

                if (pw.equals(checkPw)){
                    Toast.makeText(this, "같습니다", Toast.LENGTH_SHORT).show();
                    StringBuffer sb = new StringBuffer();
                    sb.append("INSERT INTO user (");
                    sb.append(" id, name, passwd, phone )");
                    sb.append(" VALUES (?, ?, ?, ?)");

                    db.execSQL(sb.toString(), new Object[]{ userInfo.getId(), userInfo.getName(), userInfo.getPasswd(), userInfo.getPhone()});

                    db.close();
                    finish();

                    //id TEXT PRIMARY KEY, name TEXT, passwd TEXT, phone TEXT)

                } else {
                    finish();
                }
                break;
            case R.id.btnCancel:
                Intent intent = new Intent(LoginPopupActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}

