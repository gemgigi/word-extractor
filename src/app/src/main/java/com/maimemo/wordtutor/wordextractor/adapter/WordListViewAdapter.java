package com.maimemo.wordtutor.wordextractor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.maimemo.wordtutor.wordextractor.R;
import com.maimemo.wordtutor.wordextractor.model.Word;

import java.util.List;

/**
 * Created by jason on 3/21/16.
 */
public class WordListViewAdapter extends ArrayAdapter {

    private Context context;
    private int resourceId;
    private List<Word> list;

    public WordListViewAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceId = resource;
        this.list = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.word_tv);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(list.get(position).getName());
        return view;
    }

}

class ViewHolder {
    TextView textView;
}
