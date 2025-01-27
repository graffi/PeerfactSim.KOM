/*
 * Copyright (c) 2005-2011 KOM - Multimedia Communications Lab
 *
 * This file is part of PeerfactSim.KOM.
 * 
 * PeerfactSim.KOM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * PeerfactSim.KOM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with PeerfactSim.KOM.  If not, see <http://www.gnu.org/licenses/>.
 *
 */


package de.tud.kom.p2psim.impl.network.modular.st.trafCtrl;

import de.tud.kom.p2psim.impl.simengine.Simulator;
import de.tud.kom.p2psim.impl.util.BackToXMLWritable;

/**
 * 
 * A traffic queue that will drop packets, if they would have to wait longer than a certain
 * amount of time before they would BEGIN to be transferred.
 * 
 * Parameters: maxTimeSend(simulation time units), maxTimeReceive(simulation time units).
 * 
 * @author Leo Nobach <peerfact@kom.tu-darmstadt.de>
 *
 * @version 05/06/2011
 */
public class BoundedTrafficQueue extends InfiniteTrafficQueue implements BackToXMLWritable {

	long maxTimeSend = 3 * Simulator.SECOND_UNIT;
	long maxTimeReceive = 3 * Simulator.SECOND_UNIT;
	
	protected boolean isTooLongSend(long waitTime) {
		return waitTime > maxTimeSend;
	}
	
	protected boolean isTooLongRcv(long waitTime) {
		return waitTime > maxTimeReceive;
	}
	
	/**
	 * Sets the maximum time a message may (begin to) be
	 * sent after the send request attempt.
	 * @param maxTimeSend
	 */
	public void setMaxTimeSend(long maxTimeSend) {
		this.maxTimeSend = maxTimeSend;
	}
	
	/**
	 * Sets the maximum time a message may (begin to) be received
	 * after the receive request attempt.
	 * @param maxTimeReceive
	 */
	public void setMaxTimeReceive(long maxTimeReceive) {
		this.maxTimeReceive = maxTimeReceive;
	}
	
	@Override
	public void writeBackToXML(BackWriter bw) {
		bw.writeTime("maxTimeSend", maxTimeSend);
		bw.writeTime("maxTimeReceive", maxTimeReceive);
	}
	
}
