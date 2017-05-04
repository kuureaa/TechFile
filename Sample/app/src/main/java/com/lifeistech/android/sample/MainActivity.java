package com.lifeistech.android.sample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import de.timroes.android.listview.EnhancedListView;

public class MainActivity extends AppCompatActivity {

    // リストビュー
    private EnhancedListView mListView;

    // リストビューに設定するリストとアダプター
    private LinkedList<String> mItemList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // リストビュー
        mListView = (EnhancedListView) findViewById(R.id.listview1);

        // リストビューにアイテム追加
        mItemList = new LinkedList<String>();

        //Buttonオブジェクト取得
        Button btn=(Button)findViewById(R.id.btn);

        //クリックイベントの通知先指定
        btn.setOnClickListener(new View.OnClickListener() {

            //クリックイベント
            @Override
            public void onClick(View v) {
                //要素追加
                addStringData();
            }
        });


        mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.text, mItemList);
        mListView.setAdapter(mAdapter);

        // スワイプで消す設定
        mListView.setDismissCallback(new de.timroes.android.listview.EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {

                final String item = (String) mAdapter.getItem(position);
                /// 消す処理
                mItemList.remove(position);
                mAdapter.notifyDataSetChanged();
                return new EnhancedListView.Undoable() {
                    @Override
                    public void undo() {
                        // 元に戻す処理
                        mItemList.add(position, item);
                        mAdapter.notifyDataSetChanged();
                    }
                };
            }
        });
        mListView.enableSwipeToDismiss();

    }

    //要素追加処理
    private void addStringData(){

        //EditTextオブジェクト取得
        EditText edit=(EditText)findViewById(R.id.edit_text);

        //EditText(テキスト)を取得し、アダプタに追加
        String msg =new SimpleDateFormat( "yyyy/MM/dd HH:mm\n" ).format( new Date() );

        Spinner item = (Spinner) findViewById(R.id.spinner);
        // 選択したアイテムを取得
        String selected = (String) item.getSelectedItem();

        mAdapter.add(msg + edit.getText().toString() + "\n\n" + "アンガーポイント：" + selected + "\n");
        edit.getEditableText().clear();
    }

    public void info(View v) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

}












