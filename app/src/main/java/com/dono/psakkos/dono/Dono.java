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

import java.math.BigInteger;
import java.security.MessageDigest;

public class Dono
{
    public static final int MIN_KEY_LENGTH = 17;

    private static BigInteger[] rs = {
            new BigInteger("21688899074207999999999999999"),
            new BigInteger("834188425931076923076923075"),
            new BigInteger("32084170228118343195266271"),
            new BigInteger("1234006547235320892125624"),
            new BigInteger("47461790278281572774061"),
            new BigInteger("1825453472241598952847"),
            new BigInteger("70209748932369190493"),
            new BigInteger("2700374958937276556"),
            new BigInteger("103860575343741405"),
            new BigInteger("3994637513220822"),
            new BigInteger("153639904354646"),
            new BigInteger("5909227090562"),
            new BigInteger("227277965020"),
            new BigInteger("8741460192"),
            new BigInteger("336210006"),
            new BigInteger("12931153"),
            new BigInteger("497351"),
            new BigInteger("19127"),
            new BigInteger("734"),
            new BigInteger("27"),
            new BigInteger("0"),
    };

    private static String EVALUATOR_CHEAT= "!A";

    public static String computePassword(String k, String st)
    {
        st = st.toLowerCase().trim();

        String s = "4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b";
        s = SHA_256(k + st + s);
        String d = SHA_256(k + st + s);

        BigInteger rs = decideRounds(k);

        for (BigInteger i = BigInteger.ZERO; i.compareTo(rs) < 0; i.add(BigInteger.ONE))
        {
            s = SHA_256(d + s);
            d = SHA_256(d + s);
        }

        d = d + Dono.EVALUATOR_CHEAT;

        return d;
    }

    private static BigInteger decideRounds(String k)
    {
        if (k.length() >= rs.length)
        {
            return rs[rs.length - 1];
        }
        else
        {
            return rs[k.length()];
        }
    }

    private static String SHA_256(String data)
    {
        try
        {
            MessageDigest h = MessageDigest.getInstance("SHA-256");
            h.reset();

            return bin2hex(h.digest((data).getBytes()));
        }
        catch(Exception e)
        {
        }

        return null;
    }

    private static String bin2hex(byte[] data)
    {
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data)).toLowerCase();
    }
}
