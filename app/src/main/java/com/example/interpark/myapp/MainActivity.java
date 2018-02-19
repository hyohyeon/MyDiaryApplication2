package com.example.interpark.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends BaseActivity implements TextView.OnEditorActionListener {

    EditText etId, etPw;
    TextView tvBtLogin, tvBtChangePw, tvBtJoin;
    CheckBox ckSaveId;
    ImageView ivPic;
    String saveId = "";

    dbHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 타이틀바 설정 title.setText만 변경해주면됨*/
        View textView = (View) findViewById(R.id.icHeader);
        TextView title = (TextView) textView.findViewById(R.id.tvHeader);
        ImageButton ibMenu = (ImageButton) textView.findViewById(R.id.ibMenu);
        ImageButton ibLogout = (ImageButton) textView.findViewById(R.id.ibLogout);
        ibMenu.setVisibility(View.INVISIBLE);
        ibLogout.setVisibility(View.INVISIBLE);
        title.setText("로그인");


        etId = (EditText) findViewById(R.id.etId);
        etPw = (EditText) findViewById(R.id.etPw);
        tvBtLogin = (TextView) findViewById(R.id.tvBtLogin);
        tvBtChangePw = (TextView) findViewById(R.id.tvBtChangePw);
        ckSaveId = (CheckBox) findViewById(R.id.ckSaveId);
        tvBtJoin = (TextView) findViewById(R.id.tvBtJoin);

        PasswordTransformationMethod PTM = new PasswordTransformationMethod();
        etPw.setTransformationMethod(PTM);
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);


        saveId = auto.getString("inputId", null);

        etPw.setOnEditorActionListener(this);
        if (saveId != null) {
            etId.setText(saveId);
        }
        tvBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    helper = new dbHelper(MainActivity.this);
                    db = helper.getReadableDatabase();
                } catch (SQLiteException e) {
                }

                String id = etId.getText().toString();
                String pw = etPw.getText().toString();

                UserVO userInfo = new UserVO();
                userInfo.setId(id);
                userInfo.setPasswd(pw);

                StringBuffer sb = new StringBuffer();
                sb.append("SELECT * FROM user WHERE id = #id# AND passwd = #passwd#");

                String query = sb.toString();
                query = query.replace("#id#", "'" + userInfo.getId() + "'");
                query = query.replace("#passwd#", "'" + userInfo.getPasswd() + "'");

                //

                Cursor cursor;
                cursor = db.rawQuery(query, null);


                if (cursor.moveToNext()) {
                    String strId = cursor.getString(0);
                    String strName = cursor.getString(1);
                    String strPhone = cursor.getString(3);
                    MApp appDel = (MApp) getApplication();
                    appDel.setUserId(strId);
                    appDel.setUserName(strName);
                    appDel.setUserPhone(strPhone);
                    if (strId != null) {
                        if (ckSaveId.isChecked() == true) {
                            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor autoLogin = auto.edit();
                            autoLogin.putString("inputId", strId);
                            autoLogin.commit();
                            startMain(strId, strName, strPhone);
                        } else {
                            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor autoLogin = auto.edit();
                            autoLogin.putString("inputId", null);
                            autoLogin.commit();
                            Toast.makeText(getApplicationContext(), "로그인 완료", Toast.LENGTH_SHORT).show();
                            startMain(strId, strName, strPhone);
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "입력정보를 확인하세요!", Toast.LENGTH_SHORT).show();
                    return;
                }


                cursor.close();
                db.close();

/*                if (id.equals("aa") && pw.equals("bb")) {
                    if (ckSaveId.isChecked() == true) {
                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor autoLogin = auto.edit();
                        autoLogin.putString("inputId", id);
                        autoLogin.commit();
                        Toast.makeText(getApplicationContext(), "로그인성공했어요저장도했어요", Toast.LENGTH_SHORT).show();
                        startMain(id);
                    } else if (ckSaveId.isChecked() == false) {
                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor autoLogin = auto.edit();
                        autoLogin.putString("inputId", null);
                        autoLogin.commit();
                        Toast.makeText(getApplicationContext(), "로그인 완료", Toast.LENGTH_SHORT).show();
                        startMain(id);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "아이디 패스워드를 확인하세요", Toast.LENGTH_SHORT).show();
                }*/

            }
        });

        tvBtJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginPopupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startMain(String id, String name, String phone) {

        Intent intent = new Intent(MainActivity.this, MainpageActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        startActivity(intent);

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        /* 키보드에서 다음 or 완료버튼을 눌렀을때 이벤트
        *  - id에서는 pw로 포커스 이동
        *  - pw에서는 로그인버튼 클릭
        */
        if ((actionId == EditorInfo.IME_ACTION_DONE) || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            View nextFocus = v.focusSearch(View.FOCUS_DOWN);
            if (nextFocus != null && nextFocus instanceof EditText) {
                nextFocus.requestFocus();
            } else {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                ((TextView) findViewById(R.id.tvBtLogin)).performClick();
            }
            return true;
        }
        return false;
    }
}
