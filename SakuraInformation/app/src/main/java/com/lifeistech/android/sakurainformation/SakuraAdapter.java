package com.lifeistech.android.sakurainformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBFile;
import com.nifty.cloud.mb.core.NCMBObject;

import java.util.List;

public class SakuraAdapter extends ArrayAdapter<NCMBObject> {

    static class ViewHolder {

        ImageView image;
        TextView name;
        ImageView like;
        TextView likeData;

    }

    public SakuraAdapter(Context context, int resource, List<NCMBObject> objects) {

        super(context, resource, objects);
    }

    private Bitmap getImage(String IMAGE_FILENAME) {

        final NCMBFile file = new NCMBFile(IMAGE_FILENAME);
        Bitmap btm = null;

        try {
            file.fetch();
            btm = BitmapFactory.decodeByteArray(file.getFileData(), 0, file.getFileData().length);

        } catch (NCMBException error) {
            NCMBError(error);
        }
        return btm;
    }

    private void addLike(String objectID, String likeNumber) {

        NCMB.initialize(getContext(), MainActivity.APPLICATION_KEY, MainActivity.CLIENT_KEY);
        NCMBObject obj = new NCMBObject("SakuraClass");

        obj.setObjectId(objectID);

        obj.put("likeData", likeNumber);

        try {
            obj.save();
        } catch (NCMBException error) {
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.like = (ImageView) convertView.findViewById(R.id.like);
            viewHolder.likeData = (TextView) convertView.findViewById(R.id.likeData);

        } else {

            viewHolder =(ViewHolder) convertView.getTag();
        }

final NCMBObject item =  getItem(position);

        viewHolder.image.setImageBitmap(getImage(item.getString("imageName")));
        viewHolder.name.setText(item.getString("name"));
        viewHolder.likeData.setText(item.getString("likeData"));

        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int Like = Integer.parseInt(viewHolder.likeData.getText().toString()) + 1;
                viewHolder.likeData.setText(String.valueOf(Like));

                addLike(getItem(position).getObjectId(), String.valueOf(Like));

                AlphaAnimation alpha = new AlphaAnimation(1,0);
                alpha.setDuration(500);
                viewHolder.like.startAnimation(alpha);
            }
        });

        return convertView;
    }




}
