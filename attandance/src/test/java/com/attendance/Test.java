package com.attendance;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class Test {

    public static void main(String[] args){
        Integer f1=100,f2=100,f3=150,f4=150;
        System.out.println(f1 ==f2);
        System.out.println(f3 ==f4);


    }

    //LocalDate
    public static void test1(){
        LocalDate today = LocalDate.now();
        System.out.println(today);

        // 第一天
        LocalDate first_2014 = LocalDate.of(2014, Month.JANUARY,1);
        System.out.println(first_2014);

        LocalDate todayKolKata = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        System.out.println(todayKolKata);

        LocalDate dateFormat = LocalDate.ofEpochDay(365);
        System.out.println(dateFormat);

        LocalDate hundredDay2014 = LocalDate.ofYearDay(2014,100);
        System.out.println(hundredDay2014);
    }

    //LocalTime
    public static void test2(){
        LocalTime time = LocalTime.now();
        System.out.println("Current Time = "+time);

        LocalTime specificTime = LocalTime.of(12,20,25,40);
        System.out.println(specificTime);

        LocalTime timeKolkata = LocalTime.now(ZoneId.of(("Asia/Kolkata")));
        System.out.println(timeKolkata);

        LocalTime specificSecondTime = LocalTime.ofSecondOfDay(1000);
        System.out.println("10000th second time = "+specificSecondTime);
    }

    // LocalDateTime
    public static void test3(){
        LocalDateTime today = LocalDateTime.now();
        System.out.println(today);

        today = LocalDateTime.of(LocalDate.now(),LocalTime.now());
        System.out.println(today);

        LocalDateTime specificDate = LocalDateTime.of(2014,Month.JANUARY,1,10,10,30);
        System.out.println(specificDate);

        LocalDateTime todayKolkata = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println(todayKolkata);

        LocalDateTime dateFormat = LocalDateTime.ofEpochSecond(1000,0,ZoneOffset.UTC);
        System.out.println(dateFormat);
    }

    // instant
    public static void test4(){
        Instant timeStamp = Instant.now();
        System.out.println(timeStamp);

        Instant specificTime = Instant.ofEpochMilli(timeStamp.toEpochMilli());
        System.out.println(specificTime);

        Duration thirtyDay = Duration.ofDays(30);
        System.out.println(thirtyDay);
    }

    public static void test5(){
        LocalDate today = LocalDate.now();
        Calendar toda = Calendar.getInstance();
        System.out.println("year :"+today.getYear()+" "+today.isLeapYear());

        System.out.println("Today is before 01/01/2015? "+today.isBefore(LocalDate.of(2015,1,1)));
        System.out.println("Current Time = "+today.atTime(LocalTime.now()));

        // 增加或减少
        System.out.println("10 days today will be "+today.plusDays(10));
        System.out.println("3 weeks after today will be "+today.plusWeeks(3));
        System.out.println("20 months before today will be "+today.minusMonths(20));

        System.out.println("First date of this month "+today.with(TemporalAdjusters.firstDayOfMonth()));
        LocalDate lastDayOfYear = today.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(lastDayOfYear);

        Period period = today.until(lastDayOfYear);
        System.out.println(period);
        System.out.println("Months remaining in the year "+period.getMonths());
    }

    public static void test6(){
        LocalDate date = LocalDate.now();
        System.out.println(date);
        System.out.println(date.format(DateTimeFormatter.ofPattern("d::MMM::uuuu")));

        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);
        System.out.println(dateTime.format(DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss")));
        System.out.println(dateTime.format(DateTimeFormatter.BASIC_ISO_DATE));

        Instant timeStamp = Instant.now();
        System.out.println(timeStamp);
    }

    public static void test7(){
        Instant timeStamp = new Date().toInstant();
        LocalDateTime date =
                LocalDateTime.ofInstant(timeStamp,ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
        System.out.println(date);
        Instant time = Calendar.getInstance().toInstant();
        System.out.println(time);
        ZoneId defaultZone = TimeZone.getDefault().toZoneId();
        System.out.println(defaultZone);

        Date dt = Date.from(Instant.now());
        System.out.println(dt);
        TimeZone tz = TimeZone.getTimeZone(defaultZone);
        System.out.println(tz);
    }

    public static void test8(){
        Clock c1 = Clock.systemUTC();
        System.out.println(c1.millis());
        Clock c2 = Clock.systemDefaultZone();
        Clock c3 = Clock.system(ZoneId.of("Europe/Paris")); // 巴黎时区
        System.out.println(c3.millis());

        Clock css = Clock.system(ZoneId.of("Asia/Shanghai"));   // 上海时区
        System.out.println(css.millis()); // 每次调用将返回当前的瞬时时间

        Clock c4 = Clock.fixed(Instant.now(),ZoneId.of("Asia/Shanghai"));
        System.out.println(c4.millis());
    }

    public static void test9(){
        ZonedDateTime now1 = ZonedDateTime.now();
        System.out.println(now1);

        ZonedDateTime now2 = ZonedDateTime.now(ZoneId.of("Europe/London"));
        System.out.println(now2);

        ZonedDateTime z1 = ZonedDateTime.parse("2013-12-12T23:59:59Z[Europe/Paris]");
        System.out.println(z1);

        // 表示两个瞬时时间的时间段
        Duration d1 = Duration.between(Instant.ofEpochMilli(System.currentTimeMillis() - 12323123),
                Instant.now());
        System.out.println(d1);

        // 得到时差
        System.out.println(d1.toDays());
        System.out.println(d1.toHours());
    }
}
