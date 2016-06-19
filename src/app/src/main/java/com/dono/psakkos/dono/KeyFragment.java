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

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.key_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        view = getView();
        EditText editText= (EditText) view.findViewById(R.id.passwordField);
        editText.requestFocus();

        String key = PersistableKey.getKey(view.getContext());
        editText.setText(key);

        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        view.findViewById(R.id.passwordField);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus)
                {
                    if (view != null)
                    {
                        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    EditText passwordField = (EditText)view.findViewById(R.id.passwordField);
                    String key = passwordField.getText().toString();

                    if (key.length() < Dono.MIN_KEY_LENGTH)
                    {
                        MainActivity.showError("Your Key has to be longer than 16 characters long");
                        passwordField.setText(PersistableKey.getKey(view.getContext()));
                    }
                    else
                    {
                        PersistableKey.setKey(view.getContext(), key);
                        MainActivity.showInfo("Your Key was set!");
                    }
                }
            }});
    }
}
