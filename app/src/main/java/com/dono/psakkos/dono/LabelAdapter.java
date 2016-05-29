package com.dono.psakkos.dono;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;

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
