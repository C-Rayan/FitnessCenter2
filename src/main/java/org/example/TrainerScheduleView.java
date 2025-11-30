package org.example;

import javax.swing.*;
import  java.awt.*;
import java.time.LocalDateTime;

import com.mindfusion.common.DateTime;
import com.mindfusion.common.Duration;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.CalendarAdapter;
import com.mindfusion.scheduling.CalendarView;
import com.mindfusion.scheduling.ThemeType;
import com.mindfusion.scheduling.model.*;
import com.mindfusion.scheduling.standardforms.AppointmentForm;
import org.hibernate.Session;

public class TrainerScheduleView extends JFrame {
    private Calendar calender;
    //private  JPanel mainPanel;

    public  TrainerScheduleView(){
        setTitle("Schdule View");
        setSize(600, 600);

        //Intialize calender
        calender = new Calendar();
        getContentPane().add(calender, BorderLayout.CENTER);
        calender.beginInit();
        //Displays a timetable where each row represents a fixed arbitrary time interval and each column represents a day.
        calender.setCurrentView(CalendarView.WeekRange);
        calender.getWeekRangeSettings().setGroupWeekends(false); //Prevents weekends from being grouped
        calender.setTheme(ThemeType.Light);
        calender.setEnableDragCreate(true);
        calender.endInit();

        calender.addCalendarListener(new CalendarAdapter(){ //custom class
            private void showForm(Item item){
                AppointmentForm form = new AppointmentForm(calender.getSchedule());
                form.setAppointment((Appointment) item);
                form.setVisible(true);
            }

            public void itemCreated(ItemEvent e){
                calender.getSelection().reset();
                calender.getSelection().add(e.getItem().getStartTime(), e.getItem().getEndTime());
                showForm(e.getItem());
            }
        });
    }

    public static void main(String[] args) {
        //new TrainerScheduleView().setVisible(true);
        //TrainerScheduleController con = new TrainerScheduleController();
    }
}


