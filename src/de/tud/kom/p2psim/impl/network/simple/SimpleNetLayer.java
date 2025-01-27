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



package de.tud.kom.p2psim.impl.network.simple;

import org.apache.log4j.Logger;

import de.tud.kom.p2psim.api.common.Message;
import de.tud.kom.p2psim.api.common.Monitor.Reason;
import de.tud.kom.p2psim.api.network.Bandwidth;
import de.tud.kom.p2psim.api.network.NetID;
import de.tud.kom.p2psim.api.network.NetMessage;
import de.tud.kom.p2psim.api.network.NetPosition;
import de.tud.kom.p2psim.api.network.NetProtocol;
import de.tud.kom.p2psim.api.transport.TransProtocol;
import de.tud.kom.p2psim.impl.network.AbstractNetLayer;
import de.tud.kom.p2psim.impl.simengine.Simulator;
import de.tud.kom.p2psim.impl.transport.AbstractTransMessage;
import de.tud.kom.p2psim.impl.util.logging.SimLogger;

/**
 *
 * @author <peerfact@kom.tu-darmstadt.de>
 * @version 05/06/2011
 *
 */
public class SimpleNetLayer extends AbstractNetLayer {

	protected static Logger log = SimLogger.getLogger(SimpleNetLayer.class);

	private double currentDownBandwidth;

	private double currentUpBandwidth;

	private final SimpleSubnet subNet;

	public SimpleNetLayer(SimpleSubnet subNet, SimpleNetID netID,
			NetPosition netPosition, Bandwidth bandwidth) {
		super(bandwidth, netPosition);
		this.subNet = subNet;
		this.myID = netID;
		this.online = true;
		subNet.registerNetLayer(this);
	}

	public boolean isSupported(TransProtocol transProtocol) {
		if (transProtocol.equals(TransProtocol.UDP))
			return true;
		else
			return false;
	}

	public void send(Message msg, NetID receiver, NetProtocol netProtocol) {
		// outer if-else-block is used to avoid sending although the host is
		// offline
		if (this.isOnline()) {
			TransProtocol usedTransProtocol = ((AbstractTransMessage) msg)
					.getProtocol();
			if (this.isSupported(usedTransProtocol)) {
				NetMessage netMsg = new SimpleNetMessage(msg, receiver, myID,
						netProtocol);
				Simulator.getMonitor().netMsgEvent(netMsg, myID, Reason.SEND);
				subNet.send(netMsg);
			} else {
				throw new IllegalArgumentException("Transport protocol "
						+ usedTransProtocol
						+ " not supported by this NetLayer implementation.");
			}
		} else {
			int assignedMsgId = subNet.determineTransMsgNumber(msg);
			log.debug("During send: Assigning MsgId " + assignedMsgId
					+ " to dropped message");
			((AbstractTransMessage) msg).setCommId(assignedMsgId);
			NetMessage netMsg = new SimpleNetMessage(msg, receiver, myID,
					netProtocol);
			Simulator.getMonitor().netMsgEvent(netMsg, myID, Reason.DROP);
		}

	}

	@Override
	public String toString() {
		return "NetLayer(netID=" + myID + ", "
				+ (online ? "online" : "offline") + ")";
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(currentDownBandwidth);
		result = PRIME * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(currentUpBandwidth);
		result = PRIME * result + (int) (temp ^ (temp >>> 32));
		result = PRIME * result + ((subNet == null) ? 0 : subNet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SimpleNetLayer other = (SimpleNetLayer) obj;
		if (Double.doubleToLongBits(currentDownBandwidth) != Double
				.doubleToLongBits(other.currentDownBandwidth))
			return false;
		if (Double.doubleToLongBits(currentUpBandwidth) != Double
				.doubleToLongBits(other.currentUpBandwidth))
			return false;
		if (subNet == null) {
			if (other.subNet != null)
				return false;
		} else if (!subNet.equals(other.subNet))
			return false;
		return true;
	}

	public void cancelTransmission(int commId) {
		throw new UnsupportedOperationException();
	}

}
