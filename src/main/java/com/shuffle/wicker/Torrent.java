package com.shuffle.wicker;

public class Torrent {

	private String hash;

	private String name;

	private String label;

	private boolean finished;

	private String path;

	public String getHash() {
		return hash;
	}

	public void setId(String hash) {
		this.hash = hash;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Torrent [hash=" + hash + ", name=" + name + ", label=" + label + ", finished=" + finished + ", path=" + path + "]";
	}
}
