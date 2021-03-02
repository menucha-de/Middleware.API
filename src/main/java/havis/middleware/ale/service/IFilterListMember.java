package havis.middleware.ale.service;

import java.util.List;

public interface IFilterListMember {

	List<String> getPatList();

	IFieldSpec getIFieldSpec();

	String getIncludeExclude();
}