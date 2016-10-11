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

package com.dono.android;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.dono.android.core.PersistableLabels;

public class LabelAdapter extends BaseSwipeAdapter
{

    private Context mContext;
    private String[] labels;

    public LabelAdapter(Context mContext, String[] labels)
    {
        this.mContext = mContext;
        this.labels = labels;
    }

    @Override
    public int getSwipeLayoutResourceId(int position)
    {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent)
    {
        View v = LayoutInflater.from(mContext).inflate(R.layout.label_row, null);

        v.findViewById(R.id.trash).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                PersistableLabels persistableLabels = new PersistableLabels(mContext);
                persistableLabels.deleteAt(position);

                View parent = (View) view.getParent().getParent().getParent();
                ListView labelsListView = (ListView)parent.findViewById(R.id.labelsListView);

                ListAdapter listAdapter = new LabelAdapter(view.getContext(), persistableLabels.getAll());
                labelsListView.setAdapter(listAdapter);
            }
        });

        v.setBackgroundColor(Color.WHITE);

        return v;
    }

    @Override
    public void fillValues(int position, View convertView)
    {
        TextView t = (TextView)convertView.findViewById(R.id.labelText);
        t.setText(this.labels[position]);
    }

    @Override
    public int getCount()
    {
        return labels.length;
    }

    @Override
    public Object getItem(int position)
    {
        return labels[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }
}
