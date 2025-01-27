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


package de.tud.kom.p2psim.impl.overlay.gnutella04.vis;

import java.awt.Color;

import de.tud.kom.p2psim.impl.vis.api.metrics.overlay.OverlayNodeMetric;
import de.tud.kom.p2psim.impl.vis.model.overlay.VisOverlayNode;

/**
 * Anzahl Documents, die der Host verwaltet
 * @author Leo Nobach <peerfact@kom.tu-darmstadt.de>
 * @version 3.0, 10.11.2008
 *
 */
public class DocCountM extends OverlayNodeMetric {

	public DocCountM() {
		this.setColor(new Color (200, 0, 255));
	}

	@Override
	public String getValue(VisOverlayNode node){
		Object attr = node.getAttribute("doc_count");
		
		if (attr != null) return attr.toString();
		else return null;
	}

	@Override
	public String getName() {
		return "Anzahl Documents";
	}

	@Override
	public String getUnit() {
		return null;
	}

	@Override
	public boolean isNumeric() {
		return true;
	}

}
