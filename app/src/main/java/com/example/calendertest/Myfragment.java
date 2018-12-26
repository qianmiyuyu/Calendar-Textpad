package com.example.calendertest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.melnykov.fab.FloatingActionButton;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static android.provider.Contacts.Settings.getSetting;
import static android.support.constraint.Constraints.TAG;

public class Myfragment extends Fragment{
    private static final String TAG = "MainActivity";
    private static final String LAYOUT_RES = "LAYOUT_RES";
    private int layoutRes;
    private ActionBar toolbar;
    private TextView dayshoww;
    private TextView DaySelect;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForDay = new SimpleDateFormat("M月-d日", Locale.getDefault());
    private List<String> mutableBookings;
    private CompactCalendarView compactCalendarView;
    private RecyclerView textlist;
    private TextView textdate;
    private FloatingActionButton Fab;
    private static Map<Event,String> showlist = new HashMap<Event,String>();
    private View view;
    private Date sent_dateClicked=new Date();
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyFragment.
     */
    public class DataFragment {
        String date;
        List<String> mu;

        DataFragment(String d, List<String> m) {
            date = d;
            mu = m;
        }
    }

    public static Myfragment newInstance(int layoutRes) {
        Myfragment fragment = new Myfragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES, layoutRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            layoutRes = getArguments().getInt(LAYOUT_RES, R.layout.fragment_calendar);

        mutableBookings = new ArrayList<>();
        EventBus.getDefault().register(this);
        initData("1");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(layoutRes, container, false);
        Fab = (FloatingActionButton) view.findViewById(R.id.floating);
        switch (layoutRes) {
            case R.layout.fragment_calendar: {

                dayshoww = (TextView) view.findViewById(R.id.dayshow);

                compactCalendarView = (CompactCalendarView) view.findViewById(R.id.calender);
                compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
                compactCalendarView.setUseThreeLetterAbbreviation(true);

                dayshoww.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));





                //日历点击事件
                compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                    @Override
                    public void onDayClick(final Date dateClicked) {
                        sent_dateClicked=dateClicked;
                        dayshoww.setText(dateFormatForMonth.format(dateClicked));
                        List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                        Log.d(TAG, "inside onclick " + dateFormatForDisplaying.format(dateClicked));
                        if (bookingsFromMap != null) {
                            Log.d(TAG, bookingsFromMap.toString());
                            mutableBookings.clear();
                            for (int a = 0; a < bookingsFromMap.size(); a++) {
                                mutableBookings.add((String) bookingsFromMap.get(a).getData());
                            }
                            Log.d(TAG, "inside onclick " + bookingsFromMap.size());
                            DataFragment EventAndDate = new DataFragment(dateFormatForDay.format(dateClicked), mutableBookings);
                            EventBus.getDefault().post(EventAndDate);


                        }
                        //---------------浮动按钮添加点击事件-----------------
                        Fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                     Intent intent=new Intent(getActivity(),AddEvent.class);
                                     intent.putExtra("date",dateClicked.getTime());
                                     startActivity(intent);
                                     getActivity().finish();

                            }
                        });

                    }


                    @Override
                    public void onMonthScroll(Date firstDayOfNewMonth) {
                        dayshoww.setText(dateFormatForMonth.format(firstDayOfNewMonth));
                    }

                });
                //日历点击事件


            }
            break;
            case R.layout.fragment_textpad: {
                Log.d(TAG, "inside onclick " + "text");
                textlist = (RecyclerView) view.findViewById(R.id.textlist);
                textdate = (TextView) view.findViewById(R.id.textDay);

            }
            break;
            default:
                break;
        }


        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doThis(DataFragment e) {
        if (layoutRes == R.layout.fragment_textpad) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            textlist.setLayoutManager(layoutManager);
            TextAdapter adapter = new TextAdapter(e.mu);
            textlist.setAdapter(adapter);
            textdate.setText(e.date);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void initData(String groupname){
        BmobQuery<com.example.calendertest.Calendar> query = new BmobQuery<com.example.calendertest.Calendar>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("GroupName",groupname);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<com.example.calendertest.Calendar>(){
            @Override
            public void done(List<com.example.calendertest.Calendar> list, BmobException e) {
                if(e==null) {
                    //Toast.makeText(BoomSQL.getContext(), "查询成功：共" + list.size() + "条数据。", Toast.LENGTH_SHORT).show();
                    for (com.example.calendertest.Calendar gameScore : list) {

                        Event event = new Event(gameScore.getcolor(), gameScore.getTimeInMillis(), gameScore.getData());
                        showlist.put(event, gameScore.getUserName());
                    }
                    //初始化事件
                    Set<Event> ks = showlist.keySet();
                    Iterator<Event> it = ks.iterator();
                    while (it.hasNext()) {
                        Event key = it.next();
                        compactCalendarView.addEvent(key);

                    }
                }
            }
        });
    }



}
