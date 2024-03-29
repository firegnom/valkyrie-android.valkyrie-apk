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
package com.firegnom.valkyrie.engine;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User extends Player {

	/**
	 * Instantiates a new user.
	 *
	 * @param name the name
	 */
	public User(String name) {
		super(name);
		startMoverThread();
	}

	/* (non-Javadoc)
	 * @see com.firegnom.valkyrie.engine.Player#startMoverThread()
	 */
	@Override
	public void startMoverThread() {
		moverThread = new MoverThread(this, true);
		moverThread.start();
	}

}
