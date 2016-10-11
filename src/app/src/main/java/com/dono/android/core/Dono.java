// Dono Android - Password Derivation Tool
// Copyright (C) 2016  Dono - Password Derivation Tool
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

package com.dono.android.core;

import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;

import java.math.BigInteger;

import java.nio.charset.StandardCharsets;

public class Dono
{
    public static final int MIN_KEY_LENGTH = 17;

    public static final int MAX_DK_LEN = 64;

    private static BigInteger[] Iterations =
            {
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

    private static final String MagicSalt =
            "4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b";

    private static char MagicSymbol = '!';

    private static char MagicCapital = 'A';

    private static int SHA256_digestBits = 256;

    public String computePassword(String k, String l) throws Exception
    {
        return this.computePassword(k, l, Dono.MAX_DK_LEN, false, false);
    }

    private String computePassword(String k, String l, int dkLen, boolean addFixedSymbol, boolean addFixedCapital)
            throws Exception
    {
        if (k.length() < Dono.MIN_KEY_LENGTH)
        {
            throw new Exception("k.length < MIN_KEY_LENGTH");
        }

        if (dkLen > Dono.MAX_DK_LEN)
        {
            throw new Exception("dkLen > MAX_DK_LEN");
        }

        l = l.toLowerCase().trim();

        int c = this.getIterations(k);

        dkLen = addFixedSymbol ? dkLen - 1 : dkLen;
        dkLen = addFixedCapital ? dkLen - 1 : dkLen;

        String d = this.derivePassword(k, l, c, dkLen);

        if (addFixedSymbol)
        {
            d += Dono.MagicSymbol;
        }

        if (addFixedCapital)
        {
            d += Dono.MagicCapital;
        }

        return d;
    }

    private int getIterations(String k)
    {
        if (k.length() >= Dono.Iterations.length)
        {
            return Dono.Iterations[Dono.Iterations.length - 1].intValue();
        }
        else
        {
            return Dono.Iterations[k.length()].intValue();
        }
    }

    private String derivePassword(String k, String l, int c, int dkLen)
    {
        String s = this.SHA256(k + l + this.MagicSalt);

        String d = this.PBKDF2_SHA256(k, s, c);

        return d.substring(0, dkLen);
    }

    private String SHA256(String message)
    {
        SHA256Digest sha256 = new SHA256Digest();

        byte []messageBytes = message.getBytes(StandardCharsets.UTF_8);
        sha256.update(messageBytes, 0, messageBytes.length);
        byte []digestBytes = new byte[sha256.getDigestSize()];
        sha256.doFinal(digestBytes, 0);

        return this.toHex(digestBytes);
    }

    private String PBKDF2_SHA256(String key, String salt, int c)
    {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);
        PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA256Digest());
        gen.init(keyBytes, saltBytes, c);
        byte []dk = ((KeyParameter) gen.generateDerivedParameters(Dono.SHA256_digestBits)).getKey();

        return this.toHex(dk);
    }

    private String toHex(byte[] data)
    {
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data)).toLowerCase();
    }
}
