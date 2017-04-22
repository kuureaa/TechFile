package com.lifeistech.android.sakurainformation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SakuraAdapter adapter;
    ListView listView;

    static final String APPLICATION_KEY = "b57c5a322a3db53bdfa6cb2d596273e6c21c8d1c0b72053ee5106d1c9fc4dca1";
    static final String CLIENT_KEY = "43b11ad631a119856d06f1f478bbdc4e32c4df2ffcadda226d45b2a5416ef353";
    static final String ORIGINAL_TEXT = "jieopbzwrajdibjlpeuf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NCMB.initialize(this, APPLICATION_KEY, CLIENT_KEY);
        NCMBQuery<NCMBObject> query = new NCMBQuery<>("SakuraClass");

        query.whereContainedInArray("data", Arrays.asList("sakura",ORIGINAL_TEXT));

        try {
            List<NCMBObject> NCMBObjects = query.find();
            adapter = new SakuraAdapter(this, R.layout.custom_list_layout, NCMBObjects);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

        }catch (NCMBException error) {
            NCMBError(error);
        }
    }

    public void NCMBError(NCMBException error) {

        StringBuilder sb = new StringBuilder("【Failure】\n");
        if (error.getCode() != null) {
            sb.append("StatusCode :").append(error.getCode()).append("\n");
        }
        if (error.getMessage() != null) {
            sb.append("Message :").append(error.getMessage()).append("\n");
        }
        Log.e("error", sb.toString());
    }





}
