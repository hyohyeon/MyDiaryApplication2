package com.example.interpark.myapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hyohyeon on 2018-02-09.
 */

public class DiaryReadActivity extends BaseActivity implements View.OnClickListener {

    private Uri mImageCaptureUri;
    private ImageView ivPic;
    private int id_view;
    private String no, date, dimgpath, content, id;
    private String absoultePath;
    private TextView tvDate, tvRead, tvNo;
    private Button btPre, btHome, btModify, btNext, btDelete;
    public static final int UPDATESIGN = 3;


    dbHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        /* 타이틀바 설정 title.setText만 변경해주면됨*/
        View textView = (View) findViewById(R.id.icHeader);
        TextView title = (TextView) textView.findViewById(R.id.tvHeader);
        ImageButton ibMenu = (ImageButton) textView.findViewById(R.id.ibMenu);
        ImageButton ibLogout = (ImageButton) textView.findViewById(R.id.ibLogout);
        title.setText("일기읽기");


        tvDate = (TextView) findViewById(R.id.tvDate);
        ivPic = (ImageView) findViewById(R.id.ivPic);
        tvNo = (TextView) findViewById(R.id.tvNo);

        btHome = (Button) findViewById(R.id.btHome);
        tvRead = (TextView) findViewById(R.id.tvRead);
        btPre = (Button) findViewById(R.id.btPre);
        btNext = (Button) findViewById(R.id.btNext);
        btModify = (Button) findViewById(R.id.btModify);
        btDelete = (Button) findViewById(R.id.btDelete);

        Intent intent = getIntent();
        no = intent.getExtras().getString("no");
        date = intent.getExtras().getString("date");
        dimgpath = intent.getExtras().getString("dimgpath");
        content = intent.getExtras().getString("content");
        id = intent.getExtras().getString("id");


        // setImage, setText
        // Intent로 받아온 데이터 뿌려주는 작업
        // 이미지 uri는 string으로 받아오기 때문에 parse작업이 꼭 필요함
        ivPic.setImageURI(Uri.parse(dimgpath));
        tvRead.setText(content);
        tvDate.setText(date);
        tvNo.setText(no);

        btHome.setOnClickListener(this);
        btNext.setOnClickListener(this);
        btPre.setOnClickListener(this);
        btModify.setOnClickListener(this);
        btDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btHome: {
                finish();
                break;
            }
            case R.id.btPre: {
                DiaryVO diary = new DiaryVO();
                diary.setId(id);
                diary.setDdate(tvDate.getText().toString());
                /* 이전버튼 = P, 다음버튼 = N */
                String flag = "P";
                changeDiaryData(diary, flag);
                break;
            }
            case R.id.btNext: {
                DiaryVO diary = new DiaryVO();
                diary.setId(id);
                diary.setDdate(tvDate.getText().toString());
                String flag = "N";
                changeDiaryData(diary, flag);
                break;
            }
            case R.id.btModify: {
                DiaryVO diary = new DiaryVO();
                diary.setId(id);
                diary.setNo(tvNo.getText().toString());
                modifyDiaryData(diary);

                //Intent intent = new Intent(DiaryReadActivity.this, DiaryWriteActivity.class);
                //intent.putExtra("diary")

            }
            case R.id.btDelete: {
                DiaryVO diary = new DiaryVO();
                diary.setNo(no);
                deleteDiaryData(diary);
                break;
            }
        }
    }

    private void deleteDiaryData(final DiaryVO diary) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DiaryReadActivity.this);

        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("정말 삭제하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            helper = new dbHelper(DiaryReadActivity.this);
                            db = helper.getWritableDatabase();
                        } catch (SQLiteException e) {
                        }

                        StringBuffer sb = new StringBuffer();
                        sb.append("DELETE FROM DIARY WHERE dno = #no#");
                        String query = sb.toString();
                        query = query.replace("#no#", diary.getNo());
                        db.execSQL(query);
                        db.close();
                        finish();
                    }
                })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show
        alertDialog.show();






    }

    private void changeDiaryData(DiaryVO diary, String flag) {

        try {
            helper = new dbHelper(DiaryReadActivity.this);
            db = helper.getReadableDatabase();
        } catch (SQLiteException e) {
        }

        StringBuffer sb = new StringBuffer();
        if (flag.equals("N")) {
            sb.append("SELECT * FROM DIARY WHERE id = #id# AND ddate > #date# ORDER BY ddate ASC LIMIT 1");
        } else if (flag.equals("P")) {
            sb.append("SELECT * FROM DIARY WHERE id = #id# AND ddate < #date# ORDER BY ddate DESC LIMIT 1");
        }
        String query = sb.toString();
        query = query.replace("#id#", "'" + diary.getId() + "'");
        query = query.replace("#date#", "'" + diary.getDdate() + "'");

        Cursor cursor;
        cursor = db.rawQuery(query, null);

        if (cursor.moveToNext()) {
            String no = cursor.getString(0);
            String date = cursor.getString(1);
            String dimgpath = cursor.getString(3);
            String content = cursor.getString(4);
            String id = cursor.getString(5);

            if (no != null) {
                Toast.makeText(getApplicationContext(), no + date + dimgpath + content + id, Toast.LENGTH_SHORT).show();
            }

            cursor.close();
            db.close();

            diary.setNo(no);
            diary.setDdate(date);
            diary.setDimgpath(dimgpath);
            diary.setDcontent(content);
            diary.setId(id);

            ivPic.setImageURI(Uri.parse(dimgpath));
            tvRead.setText(content);
            tvDate.setText(date);

        } else {
            Toast.makeText(getApplicationContext(), "일기가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void modifyDiaryData(DiaryVO diary) {
        try {
            helper = new dbHelper(DiaryReadActivity.this);
            db = helper.getReadableDatabase();
        } catch (SQLiteException e) {
        }

        StringBuffer sb = new StringBuffer();

        sb.append("SELECT * FROM DIARY WHERE id = #id# AND dno = #no#");

        String query = sb.toString();
        query = query.replace("#id#", "'" + diary.getId() + "'");
        query = query.replace("#no#", diary.getNo());


        Cursor cursor;
        cursor = db.rawQuery(query, null);

        if (cursor.moveToNext()) {
            String no = cursor.getString(0);
            String date = cursor.getString(1);
            String dimgpath = cursor.getString(3);
            String content = cursor.getString(4);
            String id = cursor.getString(5);

            cursor.close();
            db.close();

            Intent updatIntent = new Intent(DiaryReadActivity.this, DiaryWriteActivity.class);
            updatIntent.putExtra("no", no);
            updatIntent.putExtra("date", date);
            updatIntent.putExtra("dimgpath", dimgpath);
            updatIntent.putExtra("content", content);
            updatIntent.putExtra("id", id);
            updatIntent.putExtra("FLAG", "UPDATESIGN");
            MainpageActivity.UPDATEIS = "O";
            startActivityForResult(updatIntent, UPDATESIGN);

        }


    }


}
