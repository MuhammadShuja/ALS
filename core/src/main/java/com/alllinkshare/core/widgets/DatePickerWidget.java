package com.alllinkshare.core.widgets;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.alllinkshare.core.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatePickerWidget implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final String dateFormat = "MMM dd, yyyy"; //In which you need put here

    private EditText editText;
    private TextView textView;
    private Calendar myCalendar;
    private Context mContext;
    private DatePickerDialog datePickerDialog;

    public DatePickerWidget(TextView view, Context context){
        this.mContext = context;
        this.textView = view;
        textView.setOnClickListener(this);
        myCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(mContext, this, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }



    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
        setDateInCalendar(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onClick(View view) {
        datePickerDialog.show();
    }

    public void setDate(String dateString){
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.US);
        try {
            Date parsedDate = format.parse(dateString);
            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
            String targetDate = targetFormat.format(parsedDate);

            String [] dateParts = targetDate.split("/");
            int year = Integer.parseInt(dateParts[0]);
            int monthOfYear = Integer.parseInt(dateParts[1]) - 1;
            int dayOfMonth = Integer.parseInt(dateParts[2]);

            datePickerDialog.updateDate(year, monthOfYear, dayOfMonth);
            setDateInCalendar(year, monthOfYear, dayOfMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setDateInCalendar(int year, int monthOfYear, int dayOfMonth){
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.US);
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        textView.setText(format.format(myCalendar.getTime()));
    }

}