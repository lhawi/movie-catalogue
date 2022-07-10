package com.hawi.lukman.cataloguemovieuiux;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

import com.hawi.lukman.cataloguemovieuiux.reminder.DailyReminder;
import com.hawi.lukman.cataloguemovieuiux.reminder.ReleaseReminder;
import com.hawi.lukman.cataloguemovieuiux.reminder.ReminderPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;


public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.dailyReminder)
    Switch dailyReminder;
    @BindView(R.id.relaseReminder)
    Switch relaseReminder;

    public static final String KEY_UPCOMING = "checkedUpcoming";
    public static final String KEY_DAILY= "checkedDaily";
    public static final String TYPE_REMINDER_RELEASE = "reminderRelase";
    public static final String TYPE_REMINDER_DAILY = "reminderDaily";
    public static final String HEADER_UPCOMING = "upcomingReminder";
    public static final String HEADER_DAILY = "upcomingDaily";

    public DailyReminder dailyReminderReceiver;
    public ReleaseReminder releaseReminderReceiver;
    public ReminderPreference schedulePreference;
    public SharedPreferences sReminderRelease, sReminderDaily;
    public SharedPreferences.Editor editorReminderRelease, editorReminderDaily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        getSupportActionBar();
        setTitle("Setting");

        dailyReminderReceiver = new DailyReminder();
        releaseReminderReceiver = new ReleaseReminder();
        schedulePreference = new ReminderPreference(this);
        setPreference();

    }


    @OnCheckedChanged(R.id.dailyReminder)
    public void setDailyReminder(boolean isChecked){
        editorReminderDaily = sReminderDaily.edit();
        if (isChecked) {
            editorReminderDaily.putBoolean(KEY_DAILY, true);
            editorReminderDaily.commit();
            dailyReminderOn();
        }else {
            editorReminderDaily.putBoolean(KEY_DAILY, false);
            editorReminderDaily.commit();
            dailyReminderOff();
        }
    }

    @OnCheckedChanged(R.id.relaseReminder)
    public void setRelaseReminder(boolean isChecked){
        editorReminderRelease = sReminderRelease.edit();
        if (isChecked) {
            editorReminderRelease.putBoolean(KEY_UPCOMING, true);
            editorReminderRelease.commit();
            releaseReminderOn();
        }else {
            editorReminderRelease.putBoolean(KEY_UPCOMING, false);
            editorReminderRelease.commit();
            releaseReminderOff();
        }
    }


    private void setPreference (){
        sReminderRelease = getSharedPreferences(HEADER_UPCOMING, MODE_PRIVATE);
        sReminderDaily = getSharedPreferences(HEADER_DAILY, MODE_PRIVATE);
        boolean checkSwRelease = sReminderRelease.getBoolean(KEY_UPCOMING, false);
        relaseReminder.setChecked(checkSwRelease);
        boolean checkSwDaily = sReminderDaily.getBoolean(KEY_DAILY, false);
        dailyReminder.setChecked(checkSwDaily);

    }

    private void releaseReminderOn(){
        String time = "08:00";
        String message = "Movie Release, lets see";
        schedulePreference.setReminderDailyTime(time);
        schedulePreference.setReminderDailyMessage(message);
        dailyReminderReceiver.setReminder(SettingActivity.this, TYPE_REMINDER_RELEASE, time, message );
    }
    private void releaseReminderOff(){
        dailyReminderReceiver.cancelReminder(SettingActivity.this);
    }

    private void dailyReminderOn(){
        String time = "07:00";
        String message = "Lets see the App Catalogue Movie";
        schedulePreference.setDailyTime(time);
        schedulePreference.setDailyMessage(message);
        dailyReminderReceiver.setReminder(SettingActivity.this, TYPE_REMINDER_DAILY, time, message );
    }
    private void dailyReminderOff(){
        dailyReminderReceiver.cancelReminder(SettingActivity.this);
    }
}
