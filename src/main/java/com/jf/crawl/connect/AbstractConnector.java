package com.jf.crawl.connect;

public abstract class AbstractConnector implements Connector {

	private String host;
	
	private Integer port;
	
	private DecoderType decoderType;
	
	public AbstractConnector host(String host) {
		this.host = host;
		return this;
	}
	
	public AbstractConnector port(Integer port) {
		this.port = port;
		return this;
	}
	
	public AbstractConnector decoderType(DecoderType decoderType) {
		this.decoderType = decoderType;
		return this;
	}

	public String getHost() {
		return host;
	}

	public Integer getPort() {
		return port;
	}

	public DecoderType getDecoderType() {
		return decoderType;
	}

}
