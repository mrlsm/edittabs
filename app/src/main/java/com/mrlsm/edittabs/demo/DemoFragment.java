package com.mrlsm.edittabs.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrlsm.edittabs.R;

/**
 * @author Mrlsm
 * @since 2019/5/29
 */
public class DemoFragment extends Fragment {

    private String demoText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View frView = inflater.inflate(R.layout.fragment_demo, null);
        TextView textView = frView.findViewById(R.id.fg_demo_text);
        textView.setText(demoText);
        return frView;
    }

    public DemoFragment setText(String text) {
        demoText = text;
        return this;
    }
}
