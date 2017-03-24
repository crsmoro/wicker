package com.shuffle.wicker;

public class WickerFactory {
	
	private String webClient;
	
	public WickerFactory(String webClient) {
		this.webClient = webClient;
	}
	
	public Wicker newInstance(String seedboxUrl, String username, String password) {
		if ("RuTorrent".equalsIgnoreCase(webClient)) {
			return new RuTorrentClient(seedboxUrl, username, password);
		}
		throw new IllegalArgumentException("Web client not yet implemented");
	}
}
