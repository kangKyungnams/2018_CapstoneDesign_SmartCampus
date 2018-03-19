package com.capston.iceamericano.smartcampus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by cksdn on 2018-03-18.
 */

public class NoticeListAdapter extends BaseAdapter{
    private Context context;
    private List<Notice> noticeList;

    public NoticeListAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int i) {
        return noticeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.notice, null);
        TextView notice_Text = (TextView) v.findViewById(R.id.notice_text);
        TextView name_Text = (TextView) v.findViewById(R.id.text_name);
        TextView date_Text = (TextView) v.findViewById(R.id.text_date);


        notice_Text.setText(noticeList.get(i).getNotice());
        name_Text.setText(noticeList.get(i).getName());
        date_Text.setText(noticeList.get(i).getDate());

        v.setTag(noticeList.get(i).getNotice());
        return v;
    }




}
