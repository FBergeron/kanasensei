/*
 * Copyright (C) 2001  Frédéric Bergeron (fbergeron@users.sourceforge.net)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.fbergeron.util;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import com.sixlegs.image.png.PngImage;

public class Util {
    public static Image getImageResourceFile( String strResourceFilename, Class srcClass ) {
        PngImage pngImage = null;
        Image image = null;
        BufferedInputStream in = new BufferedInputStream(
            srcClass.getResourceAsStream( strResourceFilename ) );
        if( in == null ) {
            System.err.println( "Image not found:" + strResourceFilename );
            return null;
        }
        pngImage = new PngImage( in );
        image = Toolkit.getDefaultToolkit().createImage( pngImage );
	    return( image );
	}
}
