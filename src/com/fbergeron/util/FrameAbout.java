/*
 * Copyright (C) 1999  Frédéric Bergeron (fbergeron@users.sourceforge.net)
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
import java.text.*;
import java.util.*;

/**
 * Simple frame that displays information about KanaSensei application.
 */
public class FrameAbout extends Frame
{
    /**
     * Constructor for FrameAbout.
     */
    public FrameAbout()
    {
        setLayout( new BorderLayout() );
        setBackground( Color.white );
        
        Image imgLogo = Util.getImageResourceFile( "logo.jpg", getClass() );
        Util.loadImages( new Image[] { imgLogo }, this );
        _panelPicture = new ImagePanel( imgLogo );

        _panelAuthor = new Panel( new GridLayout( 0, 1 ) );
        _labelVersion = new Label( "", Label.RIGHT );
        _labelAuthor = new Label( "", Label.RIGHT );
        _labelEmail = new Label( "", Label.RIGHT );
        _labelWebSite = new Label( "", Label.RIGHT );
        _labelDate = new Label( "", Label.RIGHT );
        _panelAuthor.add( _labelVersion );
        _panelAuthor.add( _labelAuthor );
        _panelAuthor.add( _labelEmail );
        _panelAuthor.add( _labelWebSite );
        _panelAuthor.add( _labelDate );
        
        add( _panelPicture, BorderLayout.CENTER );
        add( _panelAuthor, BorderLayout.SOUTH );

        addWindowListener( new WindowManager( this, WindowManager.HIDE_ON_CLOSE ) );
        pack();
    }

    public Insets getInsets() {
        Insets insets = super.getInsets();
        int top = insets.top + 10;
        int left = insets.left + 10;
        int bottom = insets.bottom + 10;
        int right = insets.right + 10;
        return( new Insets( top, left, bottom, right ) );
    }

    /** 
     * Sets the locale of the Frame.
     * @param locale Locale of the frame.
     */
    public void setLocale( Locale locale ) {
        super.setLocale( locale );
        ResourceBundle resBundle = ResourceBundle.getBundle( getClass().getName() + "Ress", locale ); 
        
        DateFormat df = DateFormat.getDateInstance( DateFormat.LONG, locale );
        Calendar cal = Calendar.getInstance();
        cal.set( 2002, 4, 10 );

        _labelVersion.setText( (String)resBundle.getString( "Version" ) + " " + 
            (String)resBundle.getString( "VersionNumber" ) );
        _labelAuthor.setText( (String)resBundle.getString( "By" ) +" : " + (String)resBundle.getString( "Author" ) );
        _labelDate.setText( "© " + df.format( cal.getTime() ) );
        _labelEmail.setText( resBundle.getString( "Email" ) );
        _labelWebSite.setText( resBundle.getString( "WebSite" ) );

        setTitle( resBundle.getString( "About" ) + " " + resBundle.getString( "KanaSensei" ) );
    }

    private Label       _labelVersion;
    private ImagePanel  _panelPicture;
    private Panel       _panelAuthor;
    private Label       _labelDate;
    private Label       _labelAuthor;
    private Label       _labelEmail;
    private Label       _labelWebSite;
    
}
