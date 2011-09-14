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

package de.tud.kom.p2psim.impl.overlay.ido.cs.operations;

import de.tud.kom.p2psim.impl.common.AbstractOperation;
import de.tud.kom.p2psim.impl.overlay.ido.cs.ClientNode;
import de.tud.kom.p2psim.impl.overlay.ido.cs.messages.LeaveMessage;
import de.tud.kom.p2psim.impl.overlay.ido.cs.util.CSConfiguration;

/**
 * Leave Operation of a client. It sends to the server a {@link LeaveMessage}.
 * 
 * @author Christoph Muenker <peerfact@kom.tu-darmstadt.de>
 * @version 01/06/2011
 * 
 */
public class LeaveOperation extends AbstractOperation<ClientNode, Object> {

	ClientNode node;

	public LeaveOperation(ClientNode node) {
		super(node);
		this.node = node;
	}

	@Override
	protected void execute() {
		LeaveMessage msg = new LeaveMessage(node.getOverlayID());

		node.getTransLayer().send(msg, node.getServerTransInfo(),
				node.getPort(), CSConfiguration.TRANSPORT_PROTOCOL);
		operationFinished(true);
	}

	@Override
	public Object getResult() {
		// Nothing to get back.
		return null;
	}

}
