package havis.middleware.ale.service;

import java.util.List;

public interface IStatBlocks {

	List<ECReaderStat> getStatBlockList();
	
	void setStatBlockList(List<ECReaderStat> list);
}