package com.example.calendertest;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Date;
import java.util.Locale;

import b.Android;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddEvent extends AppCompatActivity  implements  TimePickerDialog.OnTimeSetListener {
    private View cv;
    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";
    private final java.util.Calendar calendar = java.util.Calendar.getInstance();
    private long dayClickedMill;
    private ActionBarView action_barview;
    private EditText addEventText;
    private final static int one[]=new int[]
            {Color.argb(255,169,68,65),
            Color.argb(255, 100, 68, 65),
            Color.argb(255, 70, 68, 65)};
    private java.util.Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        cv = getWindow().getDecorView();
        addEventText=(EditText)findViewById(R.id.EventContent);

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddEvent.this, calendar.get(java.util.Calendar.HOUR_OF_DAY), calendar.get(java.util.Calendar.MINUTE),false, false);
        timePickerDialog.setVibrate(true);
        timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);

        initView();
        dayClickedMill=getIntent().getLongExtra("date",0);

        if (savedInstanceState != null) {
            TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
            if (tpd != null) {
                tpd.setOnTimeSetListener(this);
            }
        }
    }
    private void initView() {
        action_barview = (ActionBarView) findViewById(R.id.action_barview);
        action_barview.setTitle("日程");
        action_barview.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddEvent.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        action_barview.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //--------------------save()中的用户名、组名。
                save("aa","1",one[0],cal.getTimeInMillis(),addEventText.getText().toString());
                Intent intent=new Intent(AddEvent.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void save(String username,String groupname,int color,Long mill,String content){
        Calendar calendar=new Calendar();
        calendar.setUserName(username);
        calendar.setGroupName(groupname);
        Integer co=new Integer(color);
        calendar.setColor(co);
        calendar.setTimeInMillis(mill);
        calendar.setData(content);
        calendar.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    String mObjectId=s;
                    Toast.makeText(BoomSQL.getContext(),"新增成功："+mObjectId,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(BoomSQL.getContext(),"新增失败！",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean getSetting(@IdRes int checkboxRes) {
        return ((CheckBox) findViewById(checkboxRes)).isChecked();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

        cal= java.util.Calendar.getInstance();
        cal.setTimeInMillis(dayClickedMill);
        cal.set(java.util.Calendar.HOUR_OF_DAY,hourOfDay);
        cal.set(java.util.Calendar.MINUTE,minute);

    }
}
