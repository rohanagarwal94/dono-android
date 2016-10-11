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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dono.android.core.PersistableLabels;
import com.dono.android.R;

public class AddLabelFragment extends DonoFragment
{
    EditText newLabelTextField;

    ImageButton doneButton;

    LinearLayout keyboardToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.add_label_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        this.newLabelTextField = (EditText) view.findViewById(R.id.addLabelTextField);
        this.doneButton = (ImageButton) view.findViewById(R.id.doneButton);
        this.keyboardToolbar = (LinearLayout) view.findViewById(R.id.newlabel_kb_toolbar);

        // Focus on the new label text field
        this.focusOnInput(view);

        // Hide keyboard when text field loses focus
        this.newLabelTextField.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean hasFocus)
            {
                if (!hasFocus)
                {
                    if (view != null)
                    {
                        hideKeyboard(view);
                    }
                }
                else
                {
                    showToolbar();
                }
            }
        });

        this.doneButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addLabel(view);
            }
        });
    }

    public void addLabel(View view)
    {
        String label = newLabelTextField.getText().toString();

        if (!label.isEmpty())
        {
            PersistableLabels persistableLabels = new PersistableLabels(view.getContext());
            String labelAdded = persistableLabels.add(label);

            if (labelAdded != null)
            {
                donoToastFactory.makeInfoToast("Label " + labelAdded + " was added to your Labels!").show();
            }
            else
            {
                donoToastFactory.makeErrorToast("Label " + persistableLabels.canonicalize(label) + " is already added to your Labels!").show();
            }
        }

        this.clearInput();
        this.hideKeyboard(view);
    }

    private void focusOnInput(View view)
    {
        this.newLabelTextField.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(this.newLabelTextField, InputMethodManager.SHOW_IMPLICIT);

        this.showToolbar();
    }

    private void clearInput()
    {
        this.newLabelTextField.setText("");
    }

    private void hideKeyboard(View view)
    {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        this.hideToolbar();
        this.clearInput();
        this.newLabelTextField.clearFocus();
    }

    private void showToolbar()
    {
        this.keyboardToolbar.setVisibility(View.VISIBLE);
    }

    private void hideToolbar()
    {
        this.keyboardToolbar.setVisibility(View.GONE);
    }
}
