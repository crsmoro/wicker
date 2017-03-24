package com.shuffle.wicker;

import java.io.InputStream;
import java.util.List;

import com.shuffle.wicker.webui.WebClient;

public interface Wicker {
	
	String getUrl();
	
	WebClient getWebClient();
	
	List<Torrent> getTorrents();
	
	boolean exists(String hash);
	
	Torrent getTorrent(String hash);
	
	String getDownloadFolder();
	
	void uploadTorrent(InputStream inputStream);
	
	void uploadTorrent(InputStream inputStream, String label);
	
	void setLabel(String hash, String label);
}
