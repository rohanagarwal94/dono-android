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

package com.dono.psakkos.dono;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DonoToastFactory
{
    Resources resources;

    Context context;

    public DonoToastFactory(Context context, Resources resources)
    {
        this.resources = resources;

        this.context = context;
    }

    public Toast makeInfoToast(String info)
    {
        Toast toast = Toast.makeText(this.context, info, Toast.LENGTH_SHORT);
        View v = toast.getView();
        v.getBackground().setColorFilter(this.resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        TextView toastMessage = (TextView) v.findViewById(android.R.id.message);
        toastMessage.setGravity(Gravity.CENTER);

        return toast;
    }

    public Toast makeErrorToast(String error)
    {
        Toast toast = Toast.makeText(this.context, error, Toast.LENGTH_LONG);
        View v = toast.getView();
        v.getBackground().setColorFilter(this.resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
        TextView toastMessage = (TextView) v.findViewById(android.R.id.message);
        toastMessage.setGravity(Gravity.CENTER);

        return toast;
    }
}
