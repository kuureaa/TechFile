package com.example.username.myscheduler;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by user.name on 2017/03/04.
 */


public class ScheduleAdapter extends RealmBaseAdapter<Schedule> {

    private static class ViewHolder {
        TextView title;
        TextView ap;
    }

    public ScheduleAdapter(@Nullable
                                   OrderedRealmCollection<Schedule> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent,
                            false);
            viewHolder = new ViewHolder();
            viewHolder.ap =
                    (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.title =
                    (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Schedule schedule = adapterData.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String formatDate = sdf.format(schedule.getDate());
        viewHolder.title.setText(schedule.getTitle()+ "\n\n" + formatDate + "\n\n");
        viewHolder.ap.setText("アンガーポイント：" + schedule.getDetail() + "\n\n");
        return convertView;
    }
}
