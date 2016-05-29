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
