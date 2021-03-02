package havis.middleware.ale.service;

import java.util.Date;

public interface ITimestampStat extends IStat {

	void setFirstSightingTime(Date date);

	void setLastSightingTime(Date date);
}