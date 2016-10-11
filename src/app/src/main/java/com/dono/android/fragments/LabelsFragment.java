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

package com.dono.android.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dono.android.core.Dono;
import com.dono.android.LabelAdapter;
import com.dono.android.core.PersistableKey;
import com.dono.android.core.PersistableLabels;
import com.dono.android.R;

public class LabelsFragment extends DonoFragment
{
    // Milliseconds after which the row's background color will be restored
    public static int ROW_REFRESH_MILLISECONDS = 100;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.labels_fragment, container, false);

        String[] labels = new PersistableLabels(view.getContext()).getAll();
        final ListAdapter listAdapter = new LabelAdapter(view.getContext(), labels);
        final ListView labelsListView = (ListView)view.findViewById(R.id.labelsListView);
        labelsListView.setAdapter(listAdapter);

        labelsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        view.setBackgroundColor(getResources().getColor(R.color.secondary));

                        restoreCellColors();

                        PersistableKey persistableKey = new PersistableKey(view.getContext());
                        String key = persistableKey.getKey();

                        if (key == null)
                        {
                            donoToastFactory.makeErrorToast("You need to set your Key in order to derive passwords for your Labels!").show();
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
                            donoToastFactory.makeErrorToast("Oops! Failed to derive the password for Label " + label).show();
                            return;
                        }

                        ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboard.setPrimaryClip(ClipData.newPlainText(label, password));

                        donoToastFactory.makeInfoToast("Your password for " + label + " is ready to be pasted!").show();
                    }
                }
        );

        return view;
    }

    public void restoreCellColors() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                ListView list = (ListView) getView().findViewById(R.id.labelsListView);

                for (int i = 0; i < list.getChildCount(); i++)
                {
                    View child = list.getChildAt(i);
                    child.setBackgroundColor(Color.WHITE);
                }
            }
        }, LabelsFragment.ROW_REFRESH_MILLISECONDS);
    }
}
