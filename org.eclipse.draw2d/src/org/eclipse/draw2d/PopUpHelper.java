/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.draw2d;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * Provides abstract support for classes that manage popups. Popups in Draw2d consist of a
 * LightweightSystem object with an SWT shell as its Control. Desired popup behavior is 
 * attained by adding appropriate listeners to this shell. 
 */
public abstract class PopUpHelper {

private Shell shell;
private LightweightSystem lws;
private boolean tipShowing;
/** The Control this PopUpHelper's tooltip will belong to. */
protected Control control;

/**
 * Constructs a PopUpHelper to assist with popups on Control c.
 * 
 * @param c the Control
 * @since 2.0
 */
protected PopUpHelper(Control c) {
	control = c;
}

/**
 * Creates and returns the LightweightSystem object used by PopUpHelper to draw upon.
 * 
 * @return the newly created LightweightSystem
 * @since 2.0
 */
protected LightweightSystem createLightweightSystem() {
	return new LightweightSystem();
}

/**
 * Creates a new Shell object with the parameters SWT.NO_TRIM | SWT.NO_FOCUS | SWT.ON_TOP.
 * 
 * @return the newly created Shell
 * @since 2.0
 */
protected Shell createShell() {
	return new Shell(control.getShell(), SWT.NO_TRIM | SWT.NO_FOCUS | SWT.ON_TOP);
}

/**
 * Dispose of this PopUpHelper object.
 * 
 * @since 2.0
 */
public void dispose() {
	if (isShowing())
		hide();
	if (shell != null && !shell.isDisposed())
		shell.dispose();
}

/**
 * Returns this PopUpHelper's shell. If no shell exists for this PopUpHelper, a new shell 
 * is created and hookShellListeners() is called.
 * 
 * @return the Shell
 * @since 2.0
 */
protected Shell getShell() {
	if (shell == null) {
		shell = createShell();
		hookShellListeners();
	}		
	return shell;
}

/**
 * Returns this PopUpHelper's LightweightSystem. If no LightweightSystem exists for this 
 * PopUpHelper, a new LightweightSystem is created with this PopUpHelper's Shell as its 
 * Control.
 * 
 * @return the LightweightSystem
 * @since 2.0
 */
protected LightweightSystem getLightweightSystem() {
	if (lws == null) {
		lws = createLightweightSystem();
		lws.setControl(getShell());
	}
	return lws;
}

/**
 * Hides this PopUpHelper's Shell.
 * 
 * @since 2.0
 */
protected void hide() {
	if (shell != null && !shell.isDisposed())
		shell.setVisible(false);
	tipShowing = false;
}

/** 
 * Desired popup helper behavior is achieved by writing listeners that manipulate the 
 * behavior of the PopUpHelper's Shell. Override this method and add these listeners here.
 * 
 * @since 2.0
 */
protected abstract void hookShellListeners();

/**
 * Returns <code>true</code> if this PopUpHelper's Shell is visible, <code>false</code> 
 * otherwise.
 * 
 * @return <code>true</code> if this PopUpHelper's Shell is visible
 * @since 2.0
 */
public boolean isShowing() {
	return tipShowing;
}

/**
 * Sets the background color of this PopUpHelper's Shell.
 * 
 * @param c the new background color
 * @since 2.0
 */
public void setBackgroundColor(Color c) {
	getShell().setBackground(c);
}

/**
 * Sets the foreground color of this PopUpHelper's Shell.
 * 
 * @param c the new foreground color
 * @since 2.0
 */
public void setForegroundColor(Color c) {
	getShell().setForeground(c);
}	

/**
 * Sets the bounds on this PopUpHelper's Shell.
 * 
 * @param x the x coordinate
 * @param y the y coordinate
 * @param width the width
 * @param height the height
 * @since 2.0
 */
protected void setShellBounds(int x, int y, int width, int height) {
	getShell().setBounds(x, y, width, height);
}

/**
 * Displays this PopUpHelper's Shell.
 * 
 * @since 2.0
 */
protected void show() {
	getShell().setVisible(true);
	tipShowing = true;
}	

}