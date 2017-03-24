package com.shuffle.wicker.webui;

import java.util.List;
import java.util.Map;

import com.shuffle.wicker.Torrent;

public interface WebClient {

	String mainUrl();

	String addUrl();

	String labelField();

	String torrentFileField();
	
	Map<String, String> listParams();
	
	String httpMethodList();
	
	Map<String, String> configParams();

	String httpMethodConfig();
	
	Map<String, String> setLabelParams();
	
	String httpMethodSetlabel();
	
	String setLabelHashField();
	
	String setLabelLabelField();
	
	List<Torrent> getTorrents(String httpResponse);
	
	String getDownloadFolder(String httpResponse);
}