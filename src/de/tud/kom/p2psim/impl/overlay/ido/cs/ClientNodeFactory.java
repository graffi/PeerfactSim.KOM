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

package de.tud.kom.p2psim.impl.overlay.ido.cs;

import de.tud.kom.p2psim.api.common.Component;
import de.tud.kom.p2psim.api.common.ComponentFactory;
import de.tud.kom.p2psim.api.common.Host;
import de.tud.kom.p2psim.impl.overlay.BootstrapManager;
import de.tud.kom.p2psim.impl.overlay.ido.cs.util.CSConfiguration;

/**
 * The factory of the client node.
 * 
 * @author Christoph Muenker <peerfact@kom.tu-darmstadt.de>
 * @version 06/01/2011
 */
public class ClientNodeFactory implements ComponentFactory {

	/**
	 * The port for the node.
	 */
	private short port;

	@Override
	public Component createComponent(Host host) {
		BootstrapManager bootstrap = CSBootstrapManager.getInstance();
		ClientNode node = new ClientNode(host.getTransLayer(), port,
				CSConfiguration.AOI, bootstrap);
		return node;
	}

	public void setPort(short port) {
		this.port = port;
	}

}
