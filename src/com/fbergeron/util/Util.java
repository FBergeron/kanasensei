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

public class Util {
    public static Image getImageResourceFile( String strResourceFilename, Class srcClass ) {
        Image m_image = null;
        try {
            BufferedInputStream in = new BufferedInputStream(
            	srcClass.getResourceAsStream( strResourceFilename ) );
            if( in == null ) {
                System.err.println( "Image not found:" + strResourceFilename );
                return null;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            copy( in, out );
        	m_image = Toolkit.getDefaultToolkit().createImage( out.toByteArray() );
        }
        catch( java.io.IOException e ) {
            System.err.println( "Unable to read image." );
            e.printStackTrace();
        }
	    return( m_image );
	}

    public static void copy( InputStream in, OutputStream out ) throws IOException
    {
        // do not allow other threads to read from the
        // input or write to the output while copying is
        // taking place
        synchronized( in ) {
            synchronized( out ) {
                byte[] buffer = new byte[ 1024 ];
                while( true ) {
                    int bytesRead = in.read( buffer );
                    if( bytesRead == -1 )
                    	break;
                    out.write( buffer, 0, bytesRead );
                }
            }
        }
    }
}
