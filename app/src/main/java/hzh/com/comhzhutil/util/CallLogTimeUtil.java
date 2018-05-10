package hzh.com.comhzhutil.util;

import android.content.Context;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;

import hzh.com.comhzhutil.R;

/**
 * Created by hzh on 2017/10/20.
 * time util
 *
 */

public class CallLogTimeUtil {
    private static final Logger LOGGER  =   Logger.getLogger("CallLogTimeUtil");

    //        test:
//        long saveTime=1508497530000L;//2017/10/20 19:5:30//昨天
//        long saveTime1=1508411130000L;//2017/10/19 19:5:30 //星期四
//        long saveTime2=1508118934000L;//2017/10/16 19:5:30//星期一
//        long saveTime3=1476788730000L;//2016/10/18 19:5:30
//        long saveTime4=1495105530000L;//2017/5/18 19:5:30
//
//
//        String s1=CallLogTimeUtil.compareTime(System.currentTimeMillis(),saveTime,callLogView.getContext());
//        String s2=CallLogTimeUtil.compareTime(System.currentTimeMillis(),saveTime1,callLogView.getContext());
//        String s3=CallLogTimeUtil.compareTime(System.currentTimeMillis(),saveTime2,callLogView.getContext());
//        String s4=CallLogTimeUtil.compareTime(System.currentTimeMillis(),saveTime3,callLogView.getContext());
//        String s5=CallLogTimeUtil.compareTime(System.currentTimeMillis(),saveTime4,callLogView.getContext());


//        LOGGER.info("s1::"+s1);
//        LOGGER.info("s2::"+s2);
//        LOGGER.info("s3::"+s3);
//        LOGGER.info("s4::"+s4);
//        LOGGER.info("s5::"+s5);



    public static void getTime() {
        Calendar cal= Calendar.getInstance();
        cal.setTimeInMillis(1540025538000L);
        int i=cal.get(Calendar.YEAR);

        TimeZone tz = TimeZone.getTimeZone("GMT");
        cal.setTimeZone(tz);
        LOGGER.info("year:: "+i);
        Calendar cal1= Calendar.getInstance();
        cal1.setTimeInMillis(System.currentTimeMillis());
        int i1=cal1.get(Calendar.YEAR);
        LOGGER.info("year:: i1"+i1);


        LOGGER.info("year:: i"+i);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);

        Date date=new Date(System.currentTimeMillis());

        LOGGER.info("date:::"+simpleDateFormat.format(date));

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm",Locale.CHINA);

        String format= dateFormat.format(date);
        LOGGER.info("data format::::"+format);



    }


    public static String compareTime(long currentTimestamp, long saveTimestamp, Context context){
        Calendar currentTimeCal= Calendar.getInstance();
        currentTimeCal.setTimeInMillis(currentTimestamp);
        Calendar saveTimeCal= Calendar.getInstance();
        saveTimeCal.setTimeInMillis(saveTimestamp);

        int currentYear=currentTimeCal.get(Calendar.YEAR);
        int saveYear=saveTimeCal.get(Calendar.YEAR);
        int currentWeek=currentTimeCal.get(Calendar.WEEK_OF_YEAR);
        int saveWeek=saveTimeCal.get(Calendar.WEEK_OF_YEAR);

        int currentDay=currentTimeCal.get(Calendar.DAY_OF_YEAR);
        int saveDay=saveTimeCal.get(Calendar.DAY_OF_YEAR);
         Date date=new Date(saveTimestamp);
        if(currentYear==saveYear){

            if(currentDay==saveDay){
                //同一日 显示几点
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
                return dateFormat.format(date);

            }else if(currentDay-saveDay==1) {
                //相差一日 显示昨天

                return context.getString(R.string.yesterday);

            }else if(currentWeek==saveWeek){
                //显示星期几

              int dayOfWeek=  saveTimeCal.get(Calendar.DAY_OF_WEEK);
                LOGGER.info("dayofweek"+ String.valueOf(dayOfWeek));

              return judgeDayOfWeek(dayOfWeek,context);


            }else {
              //显示月份
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd",Locale.CHINA);

                return dateFormat.format(date);
            }

        }else {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
            return simpleDateFormat.format(date);

        }







    }


    private static String judgeDayOfWeek(int day, Context context){
        String judgeDay ="";
        if(day== Calendar.MONDAY){
            judgeDay= context.getString(R.string.monday);
        }else if(day== Calendar.TUESDAY){

            judgeDay= context.getString(R.string.tuesday);
        }else if(day== Calendar.WEDNESDAY){

            judgeDay= context.getString(R.string.wednesday);

        }else if(day== Calendar.THURSDAY){

            judgeDay= context.getString(R.string.thursday);

        }else if(day== Calendar.FRIDAY){
            judgeDay= context.getString(R.string.friday);


        }else if(day== Calendar.SATURDAY){

            judgeDay= context.getString(R.string.saturday);

        }else if(day== Calendar.SUNDAY){

            judgeDay= context.getString(R.string.sunday);

        }



        return judgeDay;



    }



}
