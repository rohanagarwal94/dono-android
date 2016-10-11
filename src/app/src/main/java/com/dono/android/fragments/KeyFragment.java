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
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dono.android.core.Dono;
import com.dono.android.core.PersistableKey;
import com.dono.android.R;

public class KeyFragment extends DonoFragment
{
    EditText keyTextField;

    ImageButton setKeyButton;

    ImageButton revealButton;

    LinearLayout keyboardToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.key_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        this.keyTextField = (EditText) view.findViewById(R.id.keyField);
        this.setKeyButton = (ImageButton) view.findViewById(R.id.setKeyButton);
        this.revealButton = (ImageButton) view.findViewById(R.id.revealButton);
        this.keyboardToolbar = (LinearLayout) view.findViewById(R.id.key_kb_toolbar);

        // Focus on the key text field
        this.focusOnInput(view);

        this.restoreInput(view);

        this.keyTextField.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean hasFocus)
            {
                handleFocus(view, hasFocus);
            }

        });

        this.setKeyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                changeKey(view);
            }
        });

        this.revealButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                handleKeyVisibility(view);
            }
        });

    }

    private void handleFocus(View view, boolean hasFocus)
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

    private void changeKey(View view)
    {
        String key = this.keyTextField.getText().toString();
        PersistableKey persistableKey = new PersistableKey(view.getContext());

        this.hideKeyboard(view);

        if (key.equals(persistableKey.getKey()))
        {
            return;
        }

        if (key.length() < Dono.MIN_KEY_LENGTH)
        {
            donoToastFactory.makeErrorToast("Your Key has to be longer than " + (Dono.MIN_KEY_LENGTH - 1) + " characters!").show();
            this.restoreInput(view);
        }
        else
        {
            persistableKey.setKey(key);
            donoToastFactory.makeInfoToast("Your Key was set!").show();
        }
    }

    private void handleKeyVisibility(View view)
    {
        boolean shouldReveal = this.keyTextField.getTransformationMethod() == PasswordTransformationMethod.getInstance();

        if (shouldReveal)
        {
            this.revealKey();
            this.revealButton.setImageResource(R.drawable.eyeoff);
        }
        else
        {
            this.hideKey();
            this.revealButton.setImageResource(R.drawable.eye);
        }
    }

    private void revealKey()
    {
        this.keyTextField.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    private void hideKey()
    {
        this.keyTextField.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    private void focusOnInput(View view)
    {
        this.keyTextField.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(this.keyTextField, InputMethodManager.SHOW_IMPLICIT);

        this.showToolbar();
    }

    private void restoreInput(View view)
    {
        PersistableKey key = new PersistableKey(view.getContext());
        this.keyTextField.setText(key.getKey());
    }

    private void hideKeyboard(View view)
    {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        this.hideToolbar();
        this.keyTextField.clearFocus();
        this.hideKey();
    }

    private void showToolbar()
    {
        this.keyboardToolbar.setVisibility(View.VISIBLE);
    }

    private void hideToolbar()
    {
        this.keyboardToolbar.setVisibility(View.GONE);
        this.revealButton.setImageResource(R.drawable.eye);
    }
}
