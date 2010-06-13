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
package com.firegnom.valkyrie.graphics;

import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.firegnom.valkyrie.common.Dir;
import com.firegnom.valkyrie.map.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class DirectionalAnimation.
 */
public class DirectionalAnimation implements DrawableFeature {

	/** The dir frames. */
	private final ArrayList<ArrayList<Image>> dirFrames;
	
	/** The position. */
	private short position = 0;
	
	/** The direction. */
	private short direction = 0;
	
	/** The num of frames. */
	private short numOfFrames = 0;
	
	/** The width. */
	private final int width;
	
	/** The height. */
	private final int height;
	
	/** The speed. */
	private final int speed = 80;
	
	/** The name. */
	private String name;
	
	/** The pos. */
	private final Position pos = new Position();

	/** The forward. */
	private boolean forward = true;

	/** The animation task. */
	AnimationTask animationTask;

	/** The submit. */
	Future<?> submit;

	/**
	 * Instantiates a new directional animation.
	 *
	 * @param name the name
	 * @param numOfFrames the num of frames
	 * @param width the width
	 * @param height the height
	 * @param xOffset the x offset
	 * @param yOffset the y offset
	 */
	public DirectionalAnimation(final String name, final short numOfFrames,
			final int width, final int height, final int xOffset,
			final int yOffset) {
		this.setName(name);
		this.setNumOfFrames(numOfFrames);
		this.width = width;
		this.height = height;

		dirFrames = new ArrayList<ArrayList<Image>>();
		for (int i = 0; i < Dir.dirs; i++) {
			final ArrayList<Image> frames = new ArrayList<Image>();
			for (int j = 0; j < numOfFrames; j++) {
				frames.add(new Image(name + "," + j + "," + i + ".png", width,
						height, xOffset, yOffset));
			}
			dirFrames.add(i, frames);
		}
	}

	/**
	 * Use directions from class Dir in commons.
	 *
	 * @param dir the dir
	 * @see Dir
	 */
	public void changeDir(final short dir) {
		direction = dir;
	}

	/* (non-Javadoc)
	 * @see com.firegnom.valkyrie.graphics.DrawableFeature#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(final Canvas canvas) {
		getBitmap().setX(pos.x).setY(pos.y).draw(canvas);
	}

	/**
	 * Gets the bitmap.
	 *
	 * @return the bitmap
	 */
	public Image getBitmap() {
		return dirFrames.get(direction).get(position);
	}

	/* (non-Javadoc)
	 * @see com.firegnom.valkyrie.graphics.DrawableFeature#getBounds()
	 */
	@Override
	public Rect getBounds() {
		// TODO Auto-generated method stub

		return null;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the num of frames.
	 *
	 * @return the numOfFrames
	 */
	public short getNumOfFrames() {
		return numOfFrames;
	}

	/**
	 * Next frame.
	 *
	 * @return the short
	 */
	public short nextFrame() {
		position++;
		if (position >= getNumOfFrames()) {
			forward = false;
			position = 0;
		}
		return position;
	}

	/**
	 * Reset frame.
	 */
	public void resetFrame() {
		position = 0;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Sets the num of frames.
	 *
	 * @param numOfFrames the numOfFrames to set
	 */
	public void setNumOfFrames(final short numOfFrames) {
		this.numOfFrames = numOfFrames;
	}

	/**
	 * Sets the position.
	 *
	 * @param pos the new position
	 */
	public void setPosition(final short pos) {
		position = pos;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the x
	 * @return the directional animation
	 */
	public DirectionalAnimation setX(final int x) {
		pos.x = x;
		return this;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the y
	 * @return the directional animation
	 */
	public DirectionalAnimation setY(final int y) {
		pos.y = y;
		return this;
	}

	/**
	 * Start.
	 *
	 * @param executor the executor
	 */
	public void start(final ScheduledThreadPoolExecutor executor) {
		// TODO Auto-generated method stub
		if (animationTask == null) {
			animationTask = new AnimationTask(this);
			submit = executor.scheduleAtFixedRate(animationTask, 0, speed,
					TimeUnit.MILLISECONDS);

		}

	}

	/**
	 * Stop.
	 */
	public void stop() {
		// there was a null pointer here it happend when in fight mode you click
		// on
		// your self or somewhere where you can't move
		if (submit != null) {
			submit.cancel(true);
		}
		animationTask = null;
		// resetFrame();
	}

}
