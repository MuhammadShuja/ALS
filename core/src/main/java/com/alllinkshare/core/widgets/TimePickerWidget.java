package com.alllinkshare.core.widgets;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimePickerWidget implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private static final String timeFormat = "h:mm a"; //In which you need put here

    private TextView textView;
    private Calendar myCalendar;
    private Context mContext;
    private TimePickerDialog timePickerDialog;

    public TimePickerWidget(TextView view, Context context){
        this.mContext = context;
        this.textView = view;
        textView.setOnClickListener(this);
        myCalendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(mContext, this, myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE), true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        textView.setText(String.format("%02d:%02d", hourOfDay, minute));
    }

    @Override
    public void onClick(View v) {
        timePickerDialog.show();
    }

    private String getTime(int hr, int min) {
        Time time = new Time(hr, min, 0);//seconds by default set to zero
        Format formatter = new SimpleDateFormat(timeFormat);
        return formatter.format(time);
    }
}