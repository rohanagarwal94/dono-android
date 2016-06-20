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

import com.dono.psakkos.dono.core.Dono;

import org.junit.Test;

import static org.junit.Assert.*;

public class DonoCoreKeyDerivationUnitTests
{
    @Test
    public void requireThatDonoKeyDerivationIsCorrectForKeyLength17() throws Exception
    {
        Dono dono = new Dono();

        String password = dono.computePassword("helloworldofpassw", "ords");

        assertEquals("9e06ac4eb49d2d5ee29398a9045b237af60abe794bea8b129d430e90be23a6db", password);
    }

    @Test
    public void requireThatDonoKeyDerivationIsCorrectForKeyLength18() throws Exception
    {
        Dono dono = new Dono();

        String password = dono.computePassword("helloworldofpasswo", "rds");

        assertEquals("c54f93f0f9393a3c6b5aab984f3363264ab33ea47f350fb1b89ace7a5419f321", password);
    }

    @Test
    public void requireThatDonoKeyDerivationIsCorrectForKeyLength19() throws Exception
    {
        Dono dono = new Dono();

        String password = dono.computePassword("helloworldofpasswor", "ds");

        assertEquals("afffd6a73ec4a0a162f9977aa086ac8bd7b3e314e2a175d820f46940eb91afb3", password);
    }

    @Test
    public void requireThatDonoKeyDerivationIsCorrectForKeyLength20() throws Exception
    {
        Dono dono = new Dono();

        String password = dono.computePassword("helloworldofpassword", "s");

        assertEquals("bbe639f58065f3dc36598850febbf4e1e4067ecd5cec2ab373573138630903ab", password);
    }

    @Test
    public void requireThatDonoKeyDerivationIsCorrectForKeyLength21() throws Exception
    {
        Dono dono = new Dono();

        String password = dono.computePassword("helloworldofpasswords", "test");

        assertEquals("15698afa02c9628690d18d5cfe456f1a6e1f2181f029be3b431ec84a28d2f646", password);
    }
}