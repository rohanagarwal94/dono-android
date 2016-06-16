// Dono Android - Password Derivation Tool
// Copyright (C) 2016  Panos Sakkos
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.dono.psakkos.dono;

import org.junit.Test;

import static org.junit.Assert.*;

public class DonoCoreConstantsTests
{
    @Test
    public void requireThatMinKeyLengthIs17() throws Exception
    {
        assertEquals(17, Dono.MIN_KEY_LENGTH);
    }

    @Test
    public void requireThatMaxDkLenIs32() throws Exception
    {
        assertEquals(32, Dono.MAX_DK_LEN);
    }

}
