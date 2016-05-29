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

public class AddLabelFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.new_label_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        view = getView();
        EditText editText= (EditText) view.findViewById(R.id.addLabelTextField);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        view.findViewById(R.id.passwordField);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    EditText newLabelTextField = (EditText)view.findViewById(R.id.addLabelTextField);
                    String label = newLabelTextField.getText().toString();

                    String labelAdded = new PersistableLabels(view.getContext()).add(label);

                    if (labelAdded != null)
                    {
                        MainActivity.showInfo("Label " + labelAdded + " was added to your Labels!");
                        newLabelTextField.setText("");
                    }
                }
            }});
    }
}
