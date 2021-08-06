package com.flower.myaudiomessanger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.flower.myaudiomessanger.R;
import com.flower.myaudiomessanger.model.languagecodemodel.Code;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    List<Code> languageCodeModels;

    public CustomAdapter(Context context, List<Code> languageCodeModels) {
        this.context = context;
        this.inflter = (LayoutInflater.from(context));;
        this.languageCodeModels = languageCodeModels;
    }

    @Override
    public int getCount() {
        return languageCodeModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.custom_spinner_items, null);
        TextView names = (TextView) convertView.findViewById(R.id.textView);
        names.setText(languageCodeModels.get(position).getLanguage());
        return convertView;
    }
}
