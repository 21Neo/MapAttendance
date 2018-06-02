package com.attendance.util;

import com.attendance.pojo.UserAttendance;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortUtils implements Comparator<UserAttendance>{

    public static void sortwith(List<UserAttendance> userAttendance){
        Collections.sort(userAttendance,new SortUtils());
    }

    @Override
    public int compare(UserAttendance o1, UserAttendance o2) {
        if(o1.getAuserName().equals(o2.getAuserName())){
            return 1;
        }
        return -1;
    }
}
