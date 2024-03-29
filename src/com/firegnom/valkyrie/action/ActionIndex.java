/*******************************************************************************
 * Copyright (c) 2010 Maciej Kaniewski (mk@firegnom.com).
 * 
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 * 
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 * 
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software Foundation,
 *    Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 * 
 *    Contributors:
 *     Maciej Kaniewski (mk@firegnom.com) - initial API and implementation
 ******************************************************************************/
package com.firegnom.valkyrie.action;

import gnu.trove.TIntObjectHashMap;

import java.util.ArrayList;
import java.util.Properties;

import com.firegnom.valkyrie.util.IntProcedure;
import com.firegnom.valkyrie.util.Point;
import com.firegnom.valkyrie.util.Rectangle;
import com.firegnom.valkyrie.util.rtree.RTree;

public class ActionIndex implements IntProcedure {
	private RTree actiontree;
	private TIntObjectHashMap<ContextAction> map;
	private ArrayList<ContextAction> ret;
	private int retindex;
	public int maxItems = 20;
	public static final int MAX_NUMBER_OF_ACTIONS = 250;
	public boolean showeditor = false;
	private final static ContextAction editor = new ContextAction().setName(
			"script editor").setType(ContextAction.DEBUG_SCRIPT_EDITOR);

	public ActionIndex() {
		actiontree = new RTree();
		Properties p = new Properties();
		// p.setProperty("TreeVariant", "Linear");
		p.setProperty("MinNodeEntries", "2");
		p.setProperty("MaxNodeEntries", "20"); // reasonable values?
		actiontree.init(p);
		map = new TIntObjectHashMap<ContextAction>();

	}

	public void put(ContextAction ca) {
		map.put(ca.getId(), ca);
		actiontree.add(new Rectangle(ca.x, ca.y, ca.x + ca.width, ca.y
				+ ca.height), ca.getId());
	}

	public ArrayList<ContextAction> get(int x, int y, int radius) {
		ret = new ArrayList<ContextAction>();
		retindex = 0;
		actiontree.nearest(new Point(x, y), this, radius);
		if (showeditor) {
			ret.add(editor);
		}
		return ret;
	}

	@Override
	public boolean execute(int id) {
		ret.add(map.get(id));
		retindex++;
		return false;
	}

	public ArrayList<ContextAction> get(Rectangle r) {
		ret = new ArrayList<ContextAction>();
		retindex = 0;

		actiontree.intersects(r, this);
		if (showeditor) {
			ret.add(editor);
		}
		return ret;
	}
}
