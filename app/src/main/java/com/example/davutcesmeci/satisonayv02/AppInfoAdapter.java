package com.example.davutcesmeci.satisonayv02;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class AppInfoAdapter extends ArrayAdapter<AppInfo> implements CompoundButton.OnCheckedChangeListener {
    SparseBooleanArray mCheckStates;

    Context context;
    int layoutResourceId;
    ArrayList<AppInfo> data = null;

    public AppInfoAdapter(Context context, int layoutResourceId, ArrayList<AppInfo> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        mCheckStates = new SparseBooleanArray(data.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        AppInfoHolder holder = null;

        if (row == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new AppInfoHolder();

            //holder.imgIcon = (ImageView) row.findViewById(R.id.imageView1);
            holder.txtTitle = (TextView) row.findViewById(R.id.textView1);
            holder.chkSelect = (CheckBox) row.findViewById(R.id.checkBox1);

            row.setTag(holder);

        } else {
            holder = (AppInfoHolder) row.getTag();
        }


        AppInfo appinfo = data.get(position);
        holder.txtTitle.setText(appinfo.applicationName);
        //holder.imgIcon.setImageDrawable(appinfo.icon);
        // holder.chkSelect.setChecked(true);
        holder.chkSelect.setTag(position);
        holder.chkSelect.setChecked(mCheckStates.get(position, false));
        holder.chkSelect.setOnCheckedChangeListener(this);
        return row;

    }

    public boolean isChecked(int position) {
        return mCheckStates.get(position, false);
    }

    public void setChecked(int position, boolean isChecked) {
        mCheckStates.put(position, isChecked);

    }

    public void toggle(int position) {
        setChecked(position, !isChecked(position));

    }

    private int lastButtonTagFirst = NULL;
    private int lastButtonTagLast = NULL;
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

/**        if (lastButtonTagLast != NULL)
            lastButtonTagFirst = lastButtonTagLast;
        if (lastButtonTagFirst != NULL)
            mCheckStates.put(lastButtonTagFirst,false);
*/
        lastButtonTagLast = (Integer) buttonView.getTag();
        mCheckStates.put(lastButtonTagLast, isChecked);
    }

    static class AppInfoHolder {
        //ImageView imgIcon;
        TextView txtTitle;
        CheckBox chkSelect;

    }

}
