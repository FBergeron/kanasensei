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

/**
 * This class represents a boolean value that switches from true to false, from false to true.
 */

public class Toggle {

    /**
     * Contructs a toggle with initial value.
     * 
     * @param isOn Initial value of the toggle.
     */
    public Toggle( boolean isOn ) {
        _value = isOn;
    }

    /**
     * Toggles value from false to true or true to false.
     */
        
    public void toggle() {
        _value = !_value;
    }

    /**
     * Returns the boolean value of the toggle.
     * 
     * @return Boolean value of the toggle.
     */
        
    public boolean isOn() {
        return( _value );
    }

    /**
     * Returns the value of the toggle as a String.
     * 
     * @return Value of the toggle as a String.
     */
        
    public String toString() {
        return( _value + "" );
    }
        
    private boolean _value;
}
