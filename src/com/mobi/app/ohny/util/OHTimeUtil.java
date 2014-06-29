package com.mobi.app.ohny.util;

import java.util.Date;
import java.util.HashMap;

import android.util.Log;

import com.mobi.app.ohny.util.OHEvent.EventType;

public class OHTimeUtil {
	private final String dayStartEpicTime = "1404002698";
	private final String dayEndEpicTime = "1404175497";
	private final int MILLISEC_IN_A_DAY = 1000 * 3600 * 24;
	private Date dayStart;
	private Date dayEnd;

	private static OHTimeUtil instance;

	private OHTimeUtil() {
		dayStart = new Date(Long.parseLong(dayStartEpicTime) * 1000);
		dayEnd = new Date(Long.parseLong(dayEndEpicTime) * 1000);
	}

	public static OHTimeUtil instance() {
		if (instance == null)
			instance = new OHTimeUtil();
		return instance;
	}

	public HashMap<EventType, Integer> getDateUpdate(Date date) {

		HashMap<EventType, Integer> ret = new HashMap<EventType, Integer>();
		Long dateDiff = (long) 0;
		if (date.before(dayStart)) {
			dateDiff = (date.getTime() - dayStart.getTime());
			dateDiff /= MILLISEC_IN_A_DAY;
			ret.put(EventType.EVENT_COMING, dateDiff.intValue());
		} else if (date.after(dayEnd)) {
			dateDiff = date.getTime() - dayEnd.getTime();
			dateDiff /= MILLISEC_IN_A_DAY;
			ret.put(EventType.EVENT_PASSED, dateDiff.intValue());
		} else if (date.getDate() == dayStart.getDate()) {
			dateDiff = date.getTime() - dayStart.getTime();
			dateDiff /= MILLISEC_IN_A_DAY; 
			dateDiff += 1;
			ret.put(EventType.EVENT_ON, dateDiff.intValue());
		}
		Log.d("", "the date diff is " + dateDiff);
		return ret;

	}

}
