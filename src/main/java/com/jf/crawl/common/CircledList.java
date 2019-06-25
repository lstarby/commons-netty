package com.jf.crawl.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 循环列表，用于实现轮循算法
 */
public class CircledList<T> {

	private List<T> list = new ArrayList<T>();
	private AtomicLong index = new AtomicLong();

	public synchronized T get() {
		int size = list.size();
		if (size == 0) {
			return null;
		}

		int idx = (int) getNextAtomicValue();
		T ret = list.get(idx % size);
		return ret;
	}

	public synchronized boolean add(T t) {
		return list.add(t);
	}

	public synchronized boolean remove(T t) {
		return list.remove(t);
	}

	private long getNextAtomicValue() {
		long next = index.getAndIncrement();
		if (next > 65535) {
			index.set(0);
			return 0;
		} else {
			return next;
		}
	}
}