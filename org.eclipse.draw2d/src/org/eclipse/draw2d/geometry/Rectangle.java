package org.eclipse.draw2d.geometry;
/*
 * Licensed Material - Property of IBM
 * (C) Copyright IBM Corp. 2001, 2002 - All Rights Reserved.
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 */

import org.eclipse.draw2d.PositionConstants;

/**
 * Represents a Rectangle (x, y, width, height). This class provides various methods
 * for manipulating this Rectangle or creating new derived geometrical Objects.
 */
public class Rectangle
	implements Cloneable, java.io.Serializable, Translatable
{
/** the X value */
public int x;
/** the Y value */
public int y;
/** the width*/
public int width;
/** the height */
public int height;

/**A singleton for use in short calculations.  Use to avoid newing unnecessary objects.*/
public static final Rectangle SINGLETON = new Rectangle();

static final long serialVersionUID = 1;

/**
 * Constructs a Rectangle at the origin with zero 
 * width and height.
 * @since 2.0
 */
public Rectangle () { }

/**
 * Constructs a Rectangle given a location and size.
 * @param p  the location
 * @param size the size
 * @since 2.0
 */
public Rectangle(Point p, Dimension size) {
	this(p.x, p.y, size.width, size.height);
}

/**
 * Constructs a copy of the provided Rectangle.
 * @param rect Rectangle supplying the initial values
 * @since 2.0
 */
public Rectangle (Rectangle rect) {
	this(rect.x, rect.y, rect.width, rect.height);
}

/**
 * Constructs a copy of the provided SWT {@link org.eclipse.swt.graphics.Rectangle Rectangle}.
 * @param rect The SWT Rectangle being copied
 * @since 2.0
 */
public Rectangle (org.eclipse.swt.graphics.Rectangle rect) {
	this(rect.x, rect.y, rect.width, rect.height);
}

/**
 * Constructs a Rectangle with the provided values.
 * @param x  X location
 * @param y  Y location
 * @param width  Width of the rectangle
 * @param height Height of the rectangle
 * @since 2.0
 */
public Rectangle(int x, int y, int width, int height) {
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
}

/**
 * Constructs the smallest Rectangle that contains the specified Points.
 * @param p1  Upper left hand corner
 * @param p2  Lower right hand corner
 * @since 2.0
 */
public Rectangle(Point p1, Point p2) {
	setLocation(p1);
	union(p2);
}

/**
 * Returns the y-coordinate of the bottom of this Rectangle
 * @return  The Y coordinate of the bottom
 * @since 2.0
 */
public int bottom() {
	return y + height;
}

/**
 * Returns whether the given point is within the 
 * boundaries of this Rectangle.  The boundaries are inclusive of the top and left edges,
 * but exclusive of the bottom and right edges.
 * 
 * @param pt  Point being tested for containment
 * @return true if the Point is within this Rectangle
 * @since 2.0
 */
public boolean contains (Point pt) {
	return contains(pt.x, pt.y);
}

/**
 * Returns whether the given coordinates are within the 
 * boundaries of this Rectangle.  The boundaries are inclusive of the top and left edges,
 * but exclusive of the bottom and right edges.
 *
 * @param x X value
 * @param y Y value
 * @return true if the coordinates are within this Rectangle
 * @since 2.0
 */
public boolean contains (int x, int y) {
	return x >= this.x
		&& y >= this.y
		&& x < this.x + this.width
		&& y < this.y + this.height;
}

/**
 * Crops this rectangle by the amount specified in <code>insets</code>.
 *
 * @param insets  Insets to be removed from the Rectangle
 * @return  <code>this</code> for convenience
 * @since 2.0
 */
public Rectangle crop(Insets insets) {
	if (insets == null) return this;
	x += insets.left;
	y += insets.top;
	width -= (insets.getWidth());
	height-= (insets.getHeight());
	return this;
}

/**
 * Returns whether the input object is equal to this
 * Rectangle or not.
 * Rectangles are equivalent if their x, y, height, and 
 * width values are the same.
 *
 * @param o  Object being tested for equality.
 * @return  Returns the result of the equality test.
 * @since 2.0
 */
public boolean equals(Object o) {
	if (this == o) return true;
	if (o instanceof Rectangle) {
		Rectangle r = (Rectangle)o;
		return (x == r.x) &&
			 (y == r.y) &&
			 (width == r.width) &&
			 (height== r.height);
	}
	return false;
}

/**
 * Expands the horizontal and vertical sides of this
 * Rectangle with the values provided as input, and returns
 * this for convenience. The location of its center is 
 * kept constant.
 * 
 * @param h  Horizontal increment.
 * @param v  Vertical increment.
 * @return  <code>this</code> for convenience
 * @since 2.0
 */
public Rectangle expand(int h, int v) {return shrink(-h,-v);}

/**
 * Expands the horizontal and vertical sides of this
 * Rectangle by the width and height of the given Insets, 
 * and returns this for convenience.
 * @return  <code>this</code> for convenience
 * @since 2.0
 */
public Rectangle expand(Insets insets) {
	x -= insets.left;
	y -= insets.top;
	height += insets.getHeight();
	width  += insets.getWidth();
	return this;
}

/**
 * Returns a new Point representing the middle point of the bottom side of this
 * Rectangle.
 *
 * @return  Point at the bottom of the Rectangle.
 * @since 2.0
 */
public Point getBottom() {return new Point(x+width/2,bottom());}

/**
 * Returns a new Point representing the bottom left point of this Rectangle.
 *
 * @return  Point at the bottom left of the rectangle.
 * @since 2.0
 */
public Point getBottomLeft() {return new Point(x, y + height);}

/**
 * Returns a new Point representing the bottom right point of this Rectangle.
 *
 * @return  Point at the bottom right of the rectangle.
 * @since 2.0
 */
public Point getBottomRight() {return new Point(x + width, y + height);}

/**
 * Returns a new point representing the center of this
 * Rectangle.
 *
 * @return  Point at the center of the rectangle.
 */
public Point getCenter() {return new Point(x+width/2, y+height/2);}

/**
 * Returns a new Rectangle which has the exact same parameters
 * as this Rectangle.
 *
 * @return  Copy of this Rectangle.
 * @since 2.0
 */
public Rectangle getCopy() {
	try{
		return (Rectangle)clone();
	} catch (CloneNotSupportedException exc) {
		return new Rectangle(this);
	}
}

/**
 * Returns a new Rectangle with the specified insets cropped.
 *
 * @param insets  Insets being cropped from the Rectangle.
 * @return  Cropped new Rectangle.
 */
public Rectangle getCropped(Insets insets) {
	Rectangle r = new Rectangle(this);
	r.crop(insets);
	return r;
}

/**
 * Returns a new incremented Rectangle, where the sides are
 * expanded by the horizonatal and vertical values provided.
 * The center of the Rectangle is maintained constant.
 *
 * @param h  Horizontal increment.
 * @param v  Vertical Inrement.
 * @return  A new expanded Rectangle.
 * @since 2.0
 */
public Rectangle getExpanded(int h, int v) { return new Rectangle(this).expand(h,v);}

/**
 * Creates and returns a new Rectangle with the bounds of
 * <code>this</code> Rectangle, expanded by the given Insets.
 *
 * @see #expand(Insets)
 * @since 2.0
 */
public Rectangle getExpanded(Insets insets) {
	return new Rectangle(this).expand(insets);
}


/**
 * Returns a new Rectangle which has the intersection of this
 * Rectangle and the rectangle provided as input. Returns an
 * empty Rectangle if there is no interection.
 *
 * @param rect  Rectangle provided to test for intersection.
 * @return  A new Rectangle representing the intersection.
 * @since 2.0
 */
public Rectangle getIntersection(Rectangle rect) {
	int x1 = Math.max(x, rect.x);
	int x2 = Math.min(x + width, rect.x + rect.width);
	int y1 = Math.max(y, rect.y);
	int y2 = Math.min(y + height, rect.y + rect.height);
	if (((x2 - x1) < 0) || ((y2 - y1) < 0))
		return new Rectangle(0,0,0,0);	// No intersection
	else
		return new Rectangle(x1, y1, x2 - x1, y2 - y1);
}

/**
 * Returns a new Rectangle representing the middle point of the 
 * left hand side of this Rectangle.
 *
 * @return  Point requested.
 */
public Point getLeft() {
	return new Point(x,y+height/2);
}

/**
 * Returns the upper left hand corner of the rectangle.
 *
 * @return  Location of the rectangle.
 * @see  #setLocation(Point)
 */
public Point getLocation() {
	return new Point(x,y);
}



/**
 * Returns a new rectangle with its width and height augmented.
 */
public Rectangle getResized(int w, int h) {
	return new Rectangle(this).resize(w, h);
}

/**
 * Returns a new Rectangle which is shifted by the position
 * of the input Point supplied.
 * 
 * @param pt  Point providing the amount of shift along each axis.
 * @return  The new translated Rectangle.
 * @since 2.0
 */
public Rectangle getTranslated(Point pt) {
	return new Rectangle(this).translate(pt);
}

/**
 * Returns a new Rectangle which is shifted along each axis
 * by the passed values.
 *
 * @param dx  Displacement along X axis.
 * @param dy  Displacement along Y axis.
 * @return  The translated rectangle.
 * @since 2.0
 */
public Rectangle getTranslated(int dx, int dy) {
	return new Rectangle(this).translate(dx,dy);
}

/**
 * Returns a new rectangle whose width and height have
 * been interchanged. This can be useful in orientation
 * changes.
 *
 * @return  The transposed rectangle.
 * @since 2.0
 */
public Rectangle getTransposed() {
	Rectangle r = new Rectangle(this);
	r.transpose();
	return r;
}

/**
 * Returns an integer which represents the position of the
 * given point with respect to this rectangle. Possible return
 * values are WEST, EAST, NORTH, and SOUTH as found in 
 * {@link org.eclipse.draw2d.PositionConstants PositionConstants}  
 * 
 * Returns PositionConstant.NONE if the given point is in this Rectangle.
 *
 * @param	pt	The Point whose position has to be determined
 * @return	An int which is a PositionConstant
 * @see org.eclipse.draw2d.PositionConstants
 * @since 2.0
 */
public int getPosition(Point pt) {
	int result = PositionConstants.NONE;
		
	if(contains(pt))
		return result;
		
	if(pt.x < x)
		result = PositionConstants.WEST;
	else if(pt.x > (x + width))
		result = PositionConstants.EAST;
		
	if(pt.y < y)
		result = result | PositionConstants.NORTH;
	else if(pt.y > (y + height))
		result = result | PositionConstants.SOUTH;
		
	return result;
}	 

/**
 * Returns a new Rectangle which is equivalent to this Rectangle 
 * with its dimensions modified by the passed Dimension <i>d</i>.
 *
 * @param d  Dimensions by which the rectangle's size
 *            should be modified.
 * @return   The new rectangle with the modified dimensions. 
 * @since 2.0
 */
public Rectangle getResized(Dimension d) {
	return new Rectangle(this).resize(d);
}

/**
 * Returns a new Point which represents the middle point of the 
 * right hand side of this Rectangle.
 *
 * @return  The point requested.
 * @since 2.0
 */
public Point	getRight()	 {return new Point(right(), y+height/2);}

/**
 * Retuns the dimensions of this Rectangle.
 *
 * @return  Size of this Rectangle as a Dimension.
 * @see  #setSize(Dimension)
 * @see  #setSize(int,int)
 * @since 2.0
 */
public Dimension	getSize()	 {return new Dimension(width,height);}

/**
 * Returns a new Point which represents the middle point of the top side 
 * of this Rectangle.
 *
 * @return  The point requested.
 * @since 2.0
 */
public Point	getTop()	 {return new Point(x+width/2,y);}

/**
 * Returns a new Point which represents the top left hand corner of 
 * this Rectangle.
 *
 * @return The requested point.
 * @since 2.0
 */
public Point	getTopLeft() {return new Point(x,y);}

/**
 * Returns a new Point which represents the top right hand corner 
 * of this Rectangle.
 *
 * @return The requested point.
 * @since 2.0
 */
public Point	getTopRight() {return new Point (x+width,y);}

/**
 * Returns the rectangular area which contains both this Rectangle
 * and the Rectangle supplied as input.
 *
 * @param rect  Rectangle for calculating union.
 * @return  Rectangular union region.
 * @since 2.0
 */
public Rectangle getUnion(Rectangle rect) {
	if (rect == null || rect.isEmpty())
		return new Rectangle(this);
	Rectangle union = new Rectangle(
		Math.min(x, rect.x),
		Math.min(y, rect.y),0,0);
	union.width = Math.max(x+width, rect.x+rect.width) - union.x;
	union.height= Math.max(y+height,rect.y+rect.height)- union.y;
	return union;
}

/**
 * Sets the size of this Rectangle to the intersection region
 * with the Rectangle supplied as input, and returns this for
 * convenience. The location and dimensions are set to zero if
 * there is no intersection with the input Rectangle.
 *
 * @param rect  Rectangle for the calculating intersection.
 * @return  <code>this</code> for convenience
 * @see  #intersects(Rectangle)
 * @since 2.0
 */
public Rectangle intersect(Rectangle rect) {
	int x1 = Math.max(x, rect.x);
	int x2 = Math.min(x + width, rect.x + rect.width);
	int y1 = Math.max(y, rect.y);
	int y2 = Math.min(y + height, rect.y + rect.height);
	if (((x2 - x1) < 0) || ((y2 - y1) < 0))
		x = y = width = height = 0;	// No intersection
	else {
		x = x1;
		y = y1;
		width = x2 - x1;
		height = y2 - y1;
    }
    return this;
}    

/**
 * Returns if the input Rectangle intersects this Rectangle.
 *
 * @param rect  Rectangle for the intersetion test.
 * @return  Result of the intersection test.
 * @see  #intersects(Rectangle)
 * @since 2.0
 */
public boolean intersects(Rectangle rect) {
	return rect.x < x + width &&
		 rect.y < y + height &&
		 rect.x + rect.width > x &&
		 rect.y + rect.height > y;
}

/**
 * Returns whether this Rectangle has any dimensions.
 * 
 * @return  Result of the emptiness test.
 * @since 2.0
 */
public boolean isEmpty() {
	return width <= 0 || height <= 0;
}

public void performScale(float factor) {
	scale(factor);
}

/**
 * Translates <code>this</code> Rectangle by (dx,dy).
 *
 * @see #translate(int, int)
 * @since 2.0
 */
public void performTranslate(int dx, int dy) {
	translate(dx, dy);
}

/**
 * Resizes this Rectangle by the dimensions provided as input, 
 * and returns this for convenience. This Rectange's width
 * will become this.width += sizeDelta.width. Likewise
 * for height.
 *
 * @param sizeDelta  Resize data as a Dimension. 
 * @return  <code>this</code> for convenience
 * @see  #resize(int,int)
 * @since 2.0
 */
public Rectangle resize(Dimension sizeDelta) {
	width += sizeDelta.width;
	height+= sizeDelta.height;
	return this;
}

/**
 * Resizes this Rectangle by the dimension values supplied as input, 
 * and returns this for convenience. This Rectangle's width will
 * become this.width += dw. This Rectangle's height will become
 * this.height += dh.
 *
 * @param dw  Amount by which width is to be resized.
 * @param dh  Amount by which height is to be resized.
 * @return  <code>this</code> for convenience
 * @see #resize(Dimension)
 * @since 2.0
 */
public Rectangle resize(int dw, int dh) {
	width += dw;
	height+= dh;
	return this;
}

/**
 * Returns the right end of the rectangle along the X axis.
 *
 * @return  The requestion location.
 * @since 2.0
 */
public int right() {return x+width;}

/**
 * Scales the location and size of this Rectangle by 
 * the given scale.  Returns itself for convenience.
 * 
 * @param	scaleFactor	The factor by which this rectangle 
 *				        will be scaled
 * @return  <code>this</code> for convenience
 * @since 2.0
 */
final public Rectangle scale(float scaleFactor) {
	return scale (scaleFactor, scaleFactor);
}

/**
 * Scales the location and size of this Rectangle by
 * the given scales.  Returns itself for convenience.
 *
 * @param	scaleX	the factor by which the X
 *					dimension has to be scaled
 * @param	scaleY	the factor by which the Y
 *					dimension has to be scaled
 * @return  <code>this</code> for convenience
 * @since 2.0
 */
public Rectangle scale(float scaleX, float scaleY) {
	x = (int)(x * scaleX + 0.5);
	y = (int)(y * scaleY + 0.5);
	width = (int)(width * scaleX + 0.5);
	height = (int)(height * scaleY + 0.5);
	return this;
}

/**
 * Sets the parameters of this Rectangle from the 
 * Rectangle passed in.
 * @return  <code>this</code> for convenience
 * @param rect Rectangle providing the bounding values.
 * @since 2.0
 */
public Rectangle setBounds(Rectangle rect) {
	x = rect.x;
	y = rect.y;
	width = rect.width;
	height= rect.height;
	return this;
}

/**
 * Sets the location of this Rectangle to the point 
 * given as input.
 * @return  <code>this</code> for convenience
 * @param p  New position of this Rectangle.
 * @since 2.0
 */
public Rectangle setLocation(Point p) {
	x = p.x;
	y = p.y;
	return this;
}

/**
 * Sets the location of this Rectangle to the coordinates
 * given as input.
 *
 * @param x1  Position of rectangle along X axis.
 * @param y1  Position of rectangle along Y axis.
 * @return  <code>this</code> for convenience
 * @see  #getLocation()
 * @since 2.0
 */
public Rectangle setLocation(int x1,int y1) {
	x = x1;
	y = y1;
	return this;
}

/**
 * Sets the dimensions of this Rectangle to the 
 * input Dimension values.
 *
 * @param d  New Dimensions.
 * @return  <code>this</code> for convenience
 * @see  #setSize(int,int)
 * @see  #getSize()
 * @since 2.0
 */
public Rectangle setSize(Dimension d) {
	width=d.width;
	height=d.height;
	return this;
}

/**
 * Sets the dimensions of this Rectangle to the
 * values given as input.
 *
 * @return  <code>this</code> for convenience
 * @param w  New width.
 * @param h  New height.
 * @see  #setSize(Dimension)
 * @see  #getSize()
 * @since 2.0
 */
public Rectangle setSize(int w, int h) {
	width=w;
	height=h;
	return this;}

/**
 * Shrinks the sides of this Rectangle by the 
 * horizontal and vertical values provided as input, and
 * returns this Rectangle for convenience. The center of this Rectangle 
 * is kept constant.
 *
 * @param h  Horizontal reduction amount.
 * @param v  Vertical reduction amount.
 * @return  <code>this</code> for convenience
 * @since 2.0
 */
public Rectangle shrink(int h, int v) {
	x += h; width -= (h+h);
	y += v; height-= (v+v);
	return this;
}

/**
 * Returns the description of this Rectangle.
 *
 * @return String containing the description.
 * @since 2.0
 */
public String toString() {
	return 	"Rectangle("+x+", "+y+", "+ //$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
			width+", "+height+")";//$NON-NLS-2$//$NON-NLS-1$
}

/**
 * Returns whether the input Rectangle touches this 
 * Rectangle.
 *
 * @param rect  Rectangle being checked for contact.
 * @return  Result of the contact test.
 * @since 2.0
 */
public boolean touches(Rectangle rect) {
	return rect.x <= x + width &&
		 rect.y <= y + height &&
		 rect.x + rect.width >= x &&
		 rect.y + rect.height >= y;
}

/**
 * Returns this Rectangle after moving the center of the
 * coordinate system Point <i>p</i>.
 *
 * @param p  Point which provides translation information.
 * @return  <code>this</code> for convenience
 * @see  #translate(int,int)
 */
public Rectangle translate (Point p) {
	x += p.x;
	y += p.y;
	return this;
}

/**
 * Returns this Rectangle after moving the center of the
 * coordinate system by the values specified along each axis.
 *
 * @param dx  Shift along X axis.
 * @param dy  Shift along Y axis.
 * @return  <code>this</code> for convenience
 * @see  #translate(Point)
 * @since 2.0
 */
public Rectangle translate (int dx, int dy) {x+=dx; y+=dy; return this;}

/**
 * Switches the location and the dimension of this
 * Rectangle. Useful for orientation changes.
 * @return  <code>this</code> for convenience
 * @since 2.0
 */
public Rectangle transpose() {
	int temp = x;
	x = y;
	y = temp;
	temp = width;
	width = height;
	height = temp;
	return this;
}

/**
 * Unions this Rectangle's <code>width</code> and <code>height</code>with the
 * specified Dimension.
 * @param d Dimension being unioned
 * @return  <code>this</code> for convenience
 * @see  #union(int,int)
 * @see  #union(Point)
 * @see  #union(Rectangle)
 * @since 2.0
 */
public Rectangle union (Dimension d) {
	width = Math.max(width, d.width);
	height = Math.max(height, d.height);
	return this;
}

/**
 * Updates this Rectangle's bounds to the 
 * minimum size which can hold both this Rectangle
 * and the specified point at (x, y).
 * @return  <code>this</code> for convenience
 * @param x1 point's X value
 * @param y1  point's Y value
 * @see  #union(Dimension)
 * @see  #union(Point)
 * @see  #union(Rectangle)
 * @since 2.0
 */
public Rectangle union(int x1, int y1) {
	if (x1 < x) {
		width += (x-x1);
		x = x1;
	} else {
		int right = x+width;
		if (x1 >= right) {
			right = x1 + 1;
			width = right - x;
		}
	}
	if (y1 < y) {
		height += (y-y1);
		y = y1;
	} else {
		int bottom = y+height;
		if (y1 >= bottom) {
			bottom = y1+1;
			height= bottom - y;
		}
	}
	return this;
}

/**
 * Updates this Rectangle's dimensions to the 
 * minimum size which can hold both this Rectangle
 * and the supplied Point.
 *
 * @param p  Point against which union is to be performed.
 * @return  <code>this</code> for convenience
 * @see  #union(int,int)
 * @see  #union(Dimension)
 * @see  #union(Rectangle)
 * @since 2.0
 */
final public void union(Point p) {
	union(p.x, p.y);
}

/**
 * Updates this Rectangle's dimensions to the 
 * minimum size which can hold both this Rectangle
 * and the passed Rectangle <i>rect</i>.
 * @return  <code>this</code> for convenience
 * @param rect  Rectangle against which union is performed.
 * @see  #union(int,int)
 * @see  #union(Point)
 * @see  #union(Dimension)
 * @since 2.0
 */
final public Rectangle union (Rectangle rect) {
	if (rect == null)
		return this;
	return union (rect.x, rect.y, rect.width, rect.height);
}

/**
 * Updates this Rectangle's dimensions to the 
 * minimum size which can hold both this Rectangle
 * and the passed parameters.
 *
 * @param _x X coordiante of desired union.
 * @param _y Y coordiante of desired union.
 * @param _w Width of desired union.
 * @param _h Height of desired union.
 * @return  <code>this</code> for convenience
 * @see  #union(int,int)
 * @see  #union(Point)
 * @see  #union(Dimension)
 * @since 2.0
 */
public Rectangle union(int x_, int y_, int w, int h) {
	int right = Math.max(this.x+width, x_+w);
	int bottom= Math.max(this.y+height, y_ + h);
	this.x = Math.min(this.x, x_);
	this.y = Math.min(this.y, y_);
	this.width = right - this.x;
	this.height= bottom- this.y;
	return this;
}

}