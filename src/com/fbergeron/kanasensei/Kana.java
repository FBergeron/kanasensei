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

package com.fbergeron.kanasensei;

import java.awt.*;
import java.io.*;
import java.util.*;

import com.fbergeron.util.*;

/**
 * Class implementing the representation of a Kana.
 */
public class Kana {

    public String romaji;
    public Image katakana;
    public Image hiragana;

    /**
     * Accessor for a Kana.
     *
     * @param name Name of the desired kana (using Romaji sound).
     * @return The corresponding Kana instance.
     */
    public static Kana get( String name ) {
        if( kana == null )
            buildKana();
        return( (Kana)( kana.get( name ) ) );
    }

    /**
     * Shows the string representation of a Kana.
     *
     * @return The string representation of a Kana.
     */
    public String toString() {
        return( romaji );
    }

    /**
     * Constructor of a Kana.  This constructor is private.  All kanas
     * will be created when the class is loaded.  It's not possible to
     * create other kanas.
     *
     * @param romaji Romaji sound
     * @param kataFilename Filename of the image of katakana representation of the romaji sound.
     * @param hiraFilename Filename of the image of hiragana representation of the romaji sound.
     */
    private Kana( String romaji, String kataFilename, String hiraFilename ) {
        this.romaji = romaji;
        this.katakana = Util.getImageResourceFile( kataFilename + ".gif", Kana.class );
        this.hiragana = Util.getImageResourceFile( hiraFilename + ".gif", Kana.class );
        if( tracker != null ) {
            tracker.addImage( this.katakana, 0 );
            tracker.addImage( this.hiragana, 0 );
        }
    }

    /**
     * Builds all kanas and put them in a data structure in memory.
     */
    private static void buildKana() {
        Canvas dummyCanvas = new Canvas();
        tracker = new MediaTracker( dummyCanvas );

        kana = new Hashtable();
        kana.put( "a", new Kana( "a", "a_k", "a" ) );
        kana.put( "ba", new Kana( "ba", "ba_k", "ba" ) );
        kana.put( "be", new Kana( "be", "be_k", "be" ) );
        kana.put( "bi", new Kana( "bi", "bi_k", "bi" ) );
        kana.put( "bo", new Kana( "bo", "bo_k", "bo" ) );
        kana.put( "bu", new Kana( "bu", "bu_k", "bu" ) );
        kana.put( "da", new Kana( "da", "da_k", "da" ) );
        kana.put( "de", new Kana( "de", "de_k", "de" ) );
        kana.put( "ji*", new Kana( "ji*", "di_k", "di" ) );
        kana.put( "do", new Kana( "do", "do_k", "do" ) );
        kana.put( "zu*", new Kana( "zu*", "du_k", "du" ) );
        kana.put( "e", new Kana( "e", "e_k", "e" ) );
        kana.put( "ga", new Kana( "ga", "ga_k", "ga" ) );
        kana.put( "ge", new Kana( "ge", "ge_k", "ge" ) );
        kana.put( "gi", new Kana( "gi", "gi_k", "gi" ) );
        kana.put( "go", new Kana( "go", "go_k", "go" ) );
        kana.put( "gu", new Kana( "gu", "gu_k", "gu" ) );
        kana.put( "ha", new Kana( "ha", "ha_k", "ha" ) );
        kana.put( "he", new Kana( "he", "he_k", "he" ) );
        kana.put( "hi", new Kana( "hi", "hi_k", "hi" ) );
        kana.put( "ho", new Kana( "ho", "ho_k", "ho" ) );
        kana.put( "fu", new Kana( "fu", "hu_k", "hu" ) );
        kana.put( "i", new Kana( "i", "i_k", "i" ) );
        kana.put( "ka", new Kana( "ka", "ka_k", "ka" ) );
        kana.put( "ke", new Kana( "ke", "ke_k", "ke" ) );
        kana.put( "ki", new Kana( "ki", "ki_k", "ki" ) );
        kana.put( "ko", new Kana( "ko", "ko_k", "ko" ) );
        kana.put( "ku", new Kana( "ku", "ku_k", "ku" ) );
        kana.put( "ma", new Kana( "ma", "ma_k", "ma" ) );
        kana.put( "me", new Kana( "me", "me_k", "me" ) );
        kana.put( "mi", new Kana( "mi", "mi_k", "mi" ) );
        kana.put( "mo", new Kana( "mo", "mo_k", "mo" ) );
        kana.put( "mu", new Kana( "mu", "mu_k", "mu" ) );
        kana.put( "n", new Kana( "n", "n_k", "n" ) );
        kana.put( "na", new Kana( "na", "na_k", "na" ) );
        kana.put( "ne", new Kana( "ne", "ne_k", "ne" ) );
        kana.put( "ni", new Kana( "ni", "ni_k", "ni" ) );
        kana.put( "no", new Kana( "no", "no_k", "no" ) );
        kana.put( "nu", new Kana( "nu", "nu_k", "nu" ) );
        kana.put( "o", new Kana( "o", "o_k", "o" ) );
        kana.put( "pa", new Kana( "pa", "pa_k", "pa" ) );
        kana.put( "pe", new Kana( "pe", "pe_k", "pe" ) );
        kana.put( "pi", new Kana( "pi", "pi_k", "pi" ) );
        kana.put( "po", new Kana( "po", "po_k", "po" ) );
        kana.put( "pu", new Kana( "pu", "pu_k", "pu" ) );
        kana.put( "ra", new Kana( "ra", "ra_k", "ra" ) );
        kana.put( "re", new Kana( "re", "re_k", "re" ) );
        kana.put( "ri", new Kana( "ri", "ri_k", "ri" ) );
        kana.put( "ro", new Kana( "ro", "ro_k", "ro" ) );
        kana.put( "ru", new Kana( "ru", "ru_k", "ru" ) );
        kana.put( "sa", new Kana( "sa", "sa_k", "sa" ) );
        kana.put( "se", new Kana( "se", "se_k", "se" ) );
        kana.put( "shi", new Kana( "shi", "si_k", "si" ) );
        kana.put( "so", new Kana( "so", "so_k", "so" ) );
        kana.put( "su", new Kana( "su", "su_k", "su" ) );
        kana.put( "ta", new Kana( "ta", "ta_k", "ta" ) );
        kana.put( "te", new Kana( "te", "te_k", "te" ) );
        kana.put( "ti", new Kana( "chi", "ti_k", "ti" ) );
        kana.put( "to", new Kana( "to", "to_k", "to" ) );
        kana.put( "tsu", new Kana( "tsu", "tu_k", "tu" ) );
        kana.put( "u", new Kana( "u", "u_k", "u" ) );
        kana.put( "wa", new Kana( "wa", "wa_k", "wa" ) );
        //kana.put( "we", new Kana( "we", "we_k", "we" ) );
        //kana.put( "wi", new Kana( "wi", "wi_k", "wi" ) );
        kana.put( "o*", new Kana( "o*", "wo_k", "wo" ) );
        kana.put( "ya", new Kana( "ya", "ya_k", "ya" ) );
        kana.put( "yo", new Kana( "yo", "yo_k", "yo" ) );
        kana.put( "yu", new Kana( "yu", "yu_k", "yu" ) );
        kana.put( "za", new Kana( "za", "za_k", "za" ) );
        kana.put( "ze", new Kana( "ze", "ze_k", "ze" ) );
        kana.put( "ji", new Kana( "ji", "zi_k", "zi" ) );
        kana.put( "zo", new Kana( "zo", "zo_k", "zo" ) );
        kana.put( "zu", new Kana( "zu", "zu_k", "zu" ) );

        try {
            tracker.waitForID( 0 );
        } catch( InterruptedException e ) { }
    }

    protected static Hashtable kana = null;

    private static MediaTracker tracker = null;
}
