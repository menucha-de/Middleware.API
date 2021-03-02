package havis.middleware.ale.service;

import java.util.List;

public interface IStat {

	String getProfile();

	void setProfile(String profile);

	List<ECReaderStat> getStatBlockList();
	
	void setStatBlockList(List<ECReaderStat> list);
}