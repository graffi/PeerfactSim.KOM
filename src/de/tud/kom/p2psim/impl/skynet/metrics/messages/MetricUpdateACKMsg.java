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


package de.tud.kom.p2psim.impl.skynet.metrics.messages;

import de.tud.kom.p2psim.api.common.Message;
import de.tud.kom.p2psim.api.service.skynet.SkyNetConstants;
import de.tud.kom.p2psim.api.service.skynet.SkyNetNodeInfo;
import de.tud.kom.p2psim.impl.skynet.AbstractSkyNetMessage;
import de.tud.kom.p2psim.impl.skynet.DHTParamterManipulator;
import de.tud.kom.p2psim.impl.skynet.metrics.MetricsEntry;

/**
 * This class defines a SkyNet-message, which is used as ACK to a
 * <code>MetricUpdateMsg</code>. Since this ACK is used for piggybacking of
 * additional information, it comprises four further methods to access the data.
 * The data of this message comprises a <code>MetricsEntry</code>-object, which
 * holds the <i>system-statistics</i> of the P2P-system, generated by the root,
 * and a <code>DHTParamterManipulator</code>-object, which shall be used in
 * later versions of SkyNet to transport some behavioral rules.
 * 
 * @author Dominik Stingl <peerfact@kom.tu-darmstadt.de>
 * @version 1.0, 15.11.2008
 * 
 */
public class MetricUpdateACKMsg extends AbstractSkyNetMessage {

	private final MetricsEntry actualSystemStatistics;

	private final DHTParamterManipulator paraManipulator;

	private final long statisticsTimestamp;

	private final long manipulatorTimestamp;

	private final int hopsFromRoot;

	public MetricUpdateACKMsg(SkyNetNodeInfo senderNodeInfo,
			SkyNetNodeInfo receiverNodeInfo,
			MetricsEntry actualSystemStatistics,
			DHTParamterManipulator paraManipulator, long statisticsTimestamp,
			long manipulatorTimestamp, long skyNetMsgID, int hopsFromRoot) {
		super(senderNodeInfo, receiverNodeInfo, skyNetMsgID, true, false, false);
		this.actualSystemStatistics = actualSystemStatistics;
		this.paraManipulator = paraManipulator;
		this.statisticsTimestamp = statisticsTimestamp;
		this.manipulatorTimestamp = manipulatorTimestamp;
		this.hopsFromRoot = hopsFromRoot;
	}

	/**
	 * This method returns the actual <i>system-statistics</i>, which are
	 * generated by the root of a SkyNet-tree and express the current state of
	 * the P2P-system.
	 * 
	 * @return the current state of the P2P-stystem
	 */
	public MetricsEntry getActualSystemStatistics() {
		return actualSystemStatistics;
	}

	/**
	 * This method returns the actual behavioral rules, which are issued by the
	 * root of a SkyNet-tree to adapt all SkyNet-nodes to the current state of
	 * the P2P-system.
	 * 
	 * @return the behavioral rule, issued by the root
	 */
	public DHTParamterManipulator getParaManipulator() {
		return paraManipulator;
	}

	/**
	 * This method returns the point in time, when the corresponding
	 * <code>MetricsEntry</code>-object was created. By the timestamp, a node
	 * can distinguish old data from new data.
	 * 
	 * @return the timestamp of the data
	 */
	public long getStatisticsTimestamp() {
		return statisticsTimestamp;
	}

	/**
	 * This method returns the point in time, when the corresponding
	 * <code>DHTParamterManipulator</code>-object was created. By the timestamp,
	 * a node can distinguish old data from new data.
	 * 
	 * @return the timestamp of the data
	 */
	public long getManipulatorTimestamp() {
		return manipulatorTimestamp;
	}

	public int getHopsFromRoot() {
		return hopsFromRoot;
	}

	@Override
	public Message getPayload() {
		return null;
	}

	@Override
	public long getSize() {
		long sysSize = 0;
		long paraSize = 0;
		if (getActualSystemStatistics() != null) {
			sysSize = getActualSystemStatistics().getSize();
		}
		if (getParaManipulator() != null) {
			paraSize = getParaManipulator().getSize();
		}
		return 2 * SkyNetConstants.LONG_SIZE + super.getSize() + sysSize
				+ paraSize + SkyNetConstants.INT_SIZE;
	}

}
