package com.jf.crawl.connect;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public enum EventLoopGroupFactory {
	INS;

	private static final EventLoopGroup BOSS_GROUP = new NioEventLoopGroup();
	private static final EventLoopGroup WORK_GROUP = new NioEventLoopGroup();

	public EventLoopGroup getBoss() {
		return BOSS_GROUP;
	}

	public EventLoopGroup getWorker() {
		return WORK_GROUP;
	}

}
