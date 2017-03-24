package com.shuffle.wicker;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.entity.ContentType;

import com.shuffle.sfhttprequest.SfHttpRequest;

public abstract class AbstractWicker implements Wicker {
	
	private static final transient Log log = LogFactory.getLog(AbstractWicker.class);

	private SfHttpRequest httpRequestBuilder;

	protected void init(String username, String password) {
		if (username == null || password == null) {
			throw new RuntimeException("No seedbox information set");
		}
		httpRequestBuilder = new SfHttpRequest(getUrl() + getWebClient().mainUrl()).addCredentials(username, password);
	}

	@Override
	public List<Torrent> getTorrents() {
		httpRequestBuilder.setHttpMethod(getWebClient().httpMethodList());
		getWebClient().listParams().forEach(httpRequestBuilder::addParameter);
		String listResponse = httpRequestBuilder.request().getStringResponse();
		httpRequestBuilder.clearParameters();
		return getWebClient().getTorrents(listResponse);
	}

	@Override
	public boolean exists(String hash) {
		return getTorrents().stream().filter(t -> t.getHash().equalsIgnoreCase(hash)).count() > 0;
	}

	@Override
	public Torrent getTorrent(String hash) {
		return getTorrents().stream().filter(t -> t.getHash().equalsIgnoreCase(hash)).findFirst().orElse(null);
	}

	@Override
	public String getDownloadFolder() {
		httpRequestBuilder.setHttpMethod(getWebClient().httpMethodConfig());
		getWebClient().configParams().forEach(httpRequestBuilder::addParameter);
		String configurationResponse = httpRequestBuilder.request().getStringResponse();
		httpRequestBuilder.clearParameters();
		return getWebClient().getDownloadFolder(configurationResponse);
	}

	@Override
	public void uploadTorrent(InputStream inputStream) {
		uploadTorrent(inputStream, null);
	}

	@Override
	public void uploadTorrent(InputStream inputStream, String label) {
		httpRequestBuilder.setHttpMethod("POST");
		httpRequestBuilder.setUrl(getUrl() + getWebClient().addUrl());
		if (label != null) {
			httpRequestBuilder.addParameter(getWebClient().labelField(), label);			
		}
		httpRequestBuilder.addParameter(getWebClient().torrentFileField(), inputStream, ContentType.APPLICATION_OCTET_STREAM, "vnttorrent.torrent");
		httpRequestBuilder.request();
		log.debug(httpRequestBuilder.getStatusLine());
		log.debug(httpRequestBuilder.getStringResponse());
		httpRequestBuilder.clearParameters();
	}

	@Override
	public void setLabel(String hash, String label) {
		httpRequestBuilder.setHttpMethod(getWebClient().httpMethodSetlabel());
		httpRequestBuilder.setUrl(getUrl() + getWebClient().mainUrl());
		getWebClient().setLabelParams().forEach(httpRequestBuilder::addParameter);
		httpRequestBuilder.addParameter(getWebClient().setLabelLabelField(), label);
		httpRequestBuilder.addParameter(getWebClient().setLabelHashField(), hash);
		httpRequestBuilder.request();
		log.debug(httpRequestBuilder.getStatusLine());
		log.debug(httpRequestBuilder.getStringResponse());
		httpRequestBuilder.clearParameters();
		
	}
}
