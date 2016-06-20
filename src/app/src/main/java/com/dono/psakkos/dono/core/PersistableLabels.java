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

package com.dono.psakkos.dono.core;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class PersistableLabels
{
    private static String LABELS_FILE = ".labels";

    private static List<String> Labels = new ArrayList<String>();

    private Context context;

    public PersistableLabels(Context context)
    {
        this.context = context;
        this.loadLabels();
    }

    public String canonicalize(String label)
    {
        return label.toLowerCase().trim();
    }

    public String add(String label)
    {
        label = canonicalize(label);

        if (PersistableLabels.Labels.contains(label) || label.isEmpty())
        {
            return null;
        }

        PersistableLabels.Labels.add(label);

        this.saveLabels();

        return label;
    }

    public String[] getAll()
    {
        if (!PersistableLabels.Labels.isEmpty())
        {
            return PersistableLabels.Labels.toArray(new String[0]);
        }

        List<String> labels =  this.loadLabels();

        return labels.toArray(new String[0]);
    }

    public String getAt(int i)
    {
        return PersistableLabels.Labels.get(i);
    }

    public void deleteAt(int i)
    {
        PersistableLabels.Labels.remove(i);

        this.saveLabels();
    }

    private List<String> loadLabels()
    {
        try
        {
            PersistableLabels.Labels = new ArrayList<String>();

            BufferedReader br = new BufferedReader(new InputStreamReader(this.context.openFileInput(PersistableLabels.LABELS_FILE)));

            String label;
            while ((label = br.readLine()) != null)
            {
                PersistableLabels.Labels.add(label);
            }

            br.close();

            java.util.Collections.sort(PersistableLabels.Labels);
        }
        catch(Exception e)
        {
        }

        return PersistableLabels.Labels;
    }

    private void saveLabels()
    {
        this.context.deleteFile(PersistableLabels.LABELS_FILE);

        java.util.Collections.sort(PersistableLabels.Labels);

        String buffer = "";

        for (String label : PersistableLabels.Labels)
        {
            buffer += label + "\n";
        }

        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(PersistableLabels.LABELS_FILE, Context.MODE_PRIVATE)));
            bw.write(buffer);
            bw.close();
        }
        catch (Exception e)
        {
        }
    }
}
