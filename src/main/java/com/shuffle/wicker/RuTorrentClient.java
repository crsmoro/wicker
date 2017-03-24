package com.shuffle.wicker;

import com.shuffle.wicker.webui.RuTorrent;
import com.shuffle.wicker.webui.WebClient;

public class RuTorrentClient extends AbstractWicker {

	private final String seedboxUrl;

	private final WebClient webClient = new RuTorrent();

	public RuTorrentClient(String seedboxUrl, String username, String password) {
		this.seedboxUrl = seedboxUrl;
		init(username, password);
	}

	@Override
	public String getUrl() {
		return seedboxUrl;
	}

	@Override
	public WebClient getWebClient() {
		return webClient;
	}

}
