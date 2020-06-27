package org.tain.queue;

import java.util.LinkedList;

import org.tain.object.Packet;

public class PacketQueue {

	private LinkedList<Packet> queue = new LinkedList<>();
	
	public synchronized void set(Packet packet) {
		this.queue.addLast(packet);
		this.notifyAll();
	}
	
	public synchronized Packet get() {
		while (this.queue.size() <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		
		return this.queue.removeFirst();
	}
}
