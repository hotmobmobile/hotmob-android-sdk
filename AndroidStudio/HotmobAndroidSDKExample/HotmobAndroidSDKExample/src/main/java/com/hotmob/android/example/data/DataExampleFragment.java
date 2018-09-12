package com.hotmob.android.example.data;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hotmob.android.example.R;
import com.hotmob.sdk.module.datacollection.DataCollection;
import com.hotmob.sdk.module.datacollection.DataSet;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataExampleFragment extends Fragment {

    private Button timedBtn;

    private DataSet timedEvent;

    public DataExampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_data_example, container, false);

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("param1", "ABC");
        dataMap.put("param2", "EFG");
        dataMap.put("param3", "123");
        HashMap<String, Object> param4 = new HashMap<>();
        param4.put("subParam1", "!@#");
        param4.put("subParam2", "%%%?");
        dataMap.put("param4", param4);
        DataCollection.captureEvent(getContext(), "test_event", dataMap);

        root.findViewById(R.id.click_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCollection.captureEvent(getContext(), "test_event_action", "clicked");
            }
        });
        root.findViewById(R.id.click_action_null).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xxx = null;
                DataCollection.captureEvent(getContext(), "null_event_action", xxx);
            }
        });
        timedBtn = root.findViewById(R.id.timed_event_btn);
        timedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timedEvent == null) {
                    timedEvent = new DataSet();
                    timedEvent.add("key1", "value1");
                    timedBtn.setText(R.string.timed_event_end);
                }else{
                    DataCollection.captureEvent(getContext(), "test_timed_event", timedEvent);
                    timedEvent = null;
                    timedBtn.setText(R.string.timed_event_start);
                }
            }
        });

        return root;
    }

}
