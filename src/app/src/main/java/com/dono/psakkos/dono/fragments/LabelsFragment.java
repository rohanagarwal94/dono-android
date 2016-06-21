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

package com.dono.psakkos.dono.fragments;

import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dono.psakkos.dono.core.Dono;
import com.dono.psakkos.dono.LabelAdapter;
import com.dono.psakkos.dono.MainActivity;
import com.dono.psakkos.dono.core.PersistableKey;
import com.dono.psakkos.dono.core.PersistableLabels;
import com.dono.psakkos.dono.R;

public class LabelsFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.labels_fragment, container, false);

        String[] labels = new PersistableLabels(view.getContext()).getAll();
        ListAdapter listAdapter = new LabelAdapter(view.getContext(), labels);
        ListView labelsListView = (ListView)view.findViewById(R.id.labelsListView);
        labelsListView.setAdapter(listAdapter);

        labelsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        PersistableKey persistableKey = new PersistableKey(view.getContext());
                        String key = persistableKey.getKey();

                        if (key == null)
                        {
                            MainActivity.showError("You need to set your Key in order to derive passwords for your Labels!");
                            return;
                        }

                        String label = String.valueOf(parent.getItemAtPosition(position));

                        String password;

                        try
                        {
                            password = new Dono().computePassword(key, label);
                        }
                        catch (Exception e)
                        {
                            MainActivity.showError("Oops! Failed to derive the password for Label " + label);
                            return;
                        }

                        ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboard.setPrimaryClip(ClipData.newPlainText(label, password));

                        MainActivity.showInfo("Your password for " + label + " is ready to be pasted!");
                    }
                }
        );

        return view;
    }
}
