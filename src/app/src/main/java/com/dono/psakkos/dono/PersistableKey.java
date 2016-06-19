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

import android.content.Context;
import android.provider.Settings;

import com.scottyab.aescrypt.AESCrypt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PersistableKey
{
    private static String KEY_FILE = ".key";

    public static String getKey(Context context)
    {
        String key = null;

        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(PersistableKey.KEY_FILE)));

            String password = PersistableKey.getAndroidId(context);
            String encryptedKey = br.readLine();

            key = AESCrypt.decrypt(password, encryptedKey);

            br.close();
        }
        catch(Exception e)
        {
        }

        return key;
    }

    public static void setKey(Context context, String key)
    {
        context.deleteFile(PersistableKey.KEY_FILE);

        try
        {
            String password = PersistableKey.getAndroidId(context);
            String encryptedMsg = AESCrypt.encrypt(password, key);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(PersistableKey.KEY_FILE, Context.MODE_PRIVATE)));
            bw.write(encryptedMsg);
            bw.close();
        }
        catch (Exception e)
        {
        }
    }

    private static String getAndroidId(Context context)
    {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
