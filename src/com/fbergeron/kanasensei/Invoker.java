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

package com.fbergeron.kanasensei;

import java.awt.*;
import java.util.*;

import com.fbergeron.kanasensei.*;

/**
 * Applet from which KanaSensei frame can be invoked.
 */
public class Invoker extends java.applet.Applet {

	/**
	 * Constructs and shows the KanaSensei frame in the specified language.
	 */
	public void showKanaSensei( String language ) {
        Locale locale = null;
	    if( "fr".equals( language ) )
	        locale = Locale.FRENCH;
	    else if( "es".equals( language )  )
	        locale = new Locale( "es", "" );
	    else if( "de".equals( language ) )
	        locale = Locale.GERMAN;
	    else if( "it".equals( language ) )
	        locale = Locale.ITALIAN;
	    else
	        locale = Locale.ENGLISH;
		KanaSensei kanaSensei = new KanaSensei( this, "Sensei", locale );
	}
        
}
