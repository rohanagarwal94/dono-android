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

import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Dono
{
    private static final String MAGIC_SALT =
            "4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b";

    private static char MAGIC_SYMBOL= '!';

    private static char MAGIC_CAPITAL= 'A';

    public static final int MIN_KEY_LENGTH = 17;

    private static BigInteger[] rs = {
            new BigInteger("56641855831775999999999999999"),
            new BigInteger("2178532916606769230769230768"),
            new BigInteger("83789727561798816568047336"),
            new BigInteger("3222681829299954483386435"),
            new BigInteger("123949301126921326284092"),
            new BigInteger("4767280812573897164771"),
            new BigInteger("183356954329765275567"),
            new BigInteger("7052190551144818290"),
            new BigInteger("271238098120954548"),
            new BigInteger("10432234543113635"),
            new BigInteger("401239790119754"),
            new BigInteger("15432299619989"),
            new BigInteger("593549985383"),
            new BigInteger("22828845590"),
            new BigInteger("878032521"),
            new BigInteger("33770480"),
            new BigInteger("1298863"),
            new BigInteger("49955"),
            new BigInteger("1920"),
            new BigInteger("72"),
            new BigInteger("1"),
    };

    public String computePassword(String k, String l)
    {
        if (k.length() < Dono.MIN_KEY_LENGTH)
        {
            return ""; // TODO: fix me
        }

        l = l.toLowerCase().trim();

        int c = this.getIterations(k);

        String d = this.derivePassword(k, l, c, 32);

        char[] tmp = d.toCharArray();

        tmp[tmp.length - 2] = Dono.MAGIC_SYMBOL;
        tmp[tmp.length - 1] = Dono.MAGIC_CAPITAL;

        return new String(tmp);
    }

    private int getIterations(String k)
    {
        if (k.length() >= rs.length)
        {
            return rs[rs.length - 1].intValue();
        }
        else
        {
            return rs[k.length()].intValue();
        }
    }

    public String derivePassword(String k, String l, int c, int dkLen)
    {
        String s = this.SHA256(k + l + this.MAGIC_SALT);

        String d = this.PBKDF2(k, s, c, dkLen);

        return d;
    }

    private String PBKDF2(String pass, String salt, int c, int dkLen)
    {
        String d = null;

        try
        {
            byte[] dk = null;
            PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA256Digest());
            gen.init(pass.getBytes(), salt.getBytes(), c);
            dk = ((KeyParameter) gen.generateDerivedParameters(dkLen * 8)).getKey();

            d = this.bin2hex(dk);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return d;
    }

    private String SHA256(String data)
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

    private String bin2hex(byte[] data)
    {
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data)).toLowerCase();
    }
}
