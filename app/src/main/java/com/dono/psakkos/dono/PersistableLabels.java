package com.dono.psakkos.dono;

import android.content.Context;

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

    public String add(String label)
    {
        label = label.toLowerCase().trim();

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
