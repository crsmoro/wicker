package com.shuffle.wicker.webui;

import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shuffle.wicker.Torrent;
import com.shuffle.wicker.WickerException;

public class RuTorrent implements WebClient {

	private final String mainUrl = "plugins/httprpc/action.php";

	private final String addUrl = "php/addtorrent.php";

	private final String labelField = "label";

	private final String torrentFileField = "torrent_file";

	private final Map<String, String> listParams = Collections.unmodifiableMap(Stream.of(new AbstractMap.SimpleEntry<String, String>("mode", "list")).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)));

	private final String httpMethodList = "POST";

	private final Map<String, String> configParams = Collections.unmodifiableMap(Stream.of(new AbstractMap.SimpleEntry<String, String>("mode", "stg")).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)));

	private final String httpMethodConfig = "POST";
	
	private final Map<String, String> setLabelParams = Collections.unmodifiableMap(Stream.of(new AbstractMap.SimpleEntry<String, String>("mode", "setlabel"), new AbstractMap.SimpleEntry<String, String>("s", "label")).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)));
	
	private final String httpMethodSetLabel = "POST";
	
	private final String setLabelLabelField = "v";
	
	private final String setLabelHashField = "hash";

	private final JsonParser jsonParser = new JsonParser();

	@Override
	public String mainUrl() {
		return mainUrl;
	}

	@Override
	public String addUrl() {
		return addUrl;
	}

	@Override
	public String labelField() {
		return labelField;
	}

	@Override
	public String torrentFileField() {
		return torrentFileField;
	}

	@Override
	public Map<String, String> listParams() {
		return listParams;
	}

	@Override
	public String httpMethodList() {
		return httpMethodList;
	}

	@Override
	public Map<String, String> configParams() {
		return configParams;
	}

	@Override
	public String httpMethodConfig() {
		return httpMethodConfig;
	}

	@Override
	public Map<String, String> setLabelParams() {
		return setLabelParams;
	}

	@Override
	public String httpMethodSetlabel() {
		return httpMethodSetLabel;
	}

	@Override
	public String setLabelLabelField() {
		return setLabelLabelField;
	}

	@Override
	public String setLabelHashField() {
		return setLabelHashField;
	}

	@Override
	public List<Torrent> getTorrents(String httpResponse) {
		return getTorrentJsonProperties(getTorrentsJsonObject(httpResponse));
	}

	@Override
	public String getDownloadFolder(String httpResponse) {
		return Optional.ofNullable(jsonParser.parse(httpResponse)).map(JsonElement::getAsJsonArray).map(j -> j.get(4)).map(JsonElement::getAsString).orElseThrow(() -> new WickerException("Download Folder unavailable"));
	}

	private JsonObject getTorrentsJsonObject(String listResponse) {
		JsonElement jsonElement = jsonParser.parse(listResponse);
		return Optional.ofNullable(jsonElement).map(JsonElement::getAsJsonObject).map(j -> j.get("t")).map(JsonElement::getAsJsonObject).orElseThrow(() -> new WickerException("Error getting torrent json object"));
	}

	private Torrent buildTorrent(String id, JsonArray jsonArrayProperties) {
		Torrent torrent = new Torrent();
		torrent.setId(id);
		torrent.setName(Optional.ofNullable(jsonArrayProperties.get(4)).map(JsonElement::getAsString).orElse(""));
		torrent.setLabel(Optional.ofNullable(jsonArrayProperties.get(14)).map(JsonElement::getAsString).orElse(""));
		torrent.setFinished(Optional.ofNullable(jsonArrayProperties.get(19)).map(JsonElement::getAsBigInteger).orElse(BigInteger.TEN).compareTo(BigInteger.ZERO) <= 0);
		torrent.setPath(Optional.ofNullable(jsonArrayProperties.get(25)).map(JsonElement::getAsString).orElse(""));
		return torrent;
	}

	private List<Torrent> getTorrentJsonProperties(JsonObject torrentsJsonObect) {
		List<Torrent> torrents = new ArrayList<>();
		Optional.ofNullable(torrentsJsonObect).map(JsonObject::entrySet).orElseGet(HashSet::new).forEach(e -> {
			torrents.add(buildTorrent(e.getKey(), Optional.ofNullable(e.getValue()).map(JsonElement::getAsJsonArray).orElseGet(JsonArray::new)));
		});
		return torrents;
	}
}
