package com.doo.study.rxjavapractice.retrofit;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.doo.study.rxjavapractice.R;
import com.doo.study.rxjavapractice.retrofit.model.Github;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Github> {

    private Context context;
    private List<Github> users;
    private int resource;


    public CustomListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        users = new ArrayList<>();
    }

    @Override
    public void add(Github object) {
        super.add(object);
        users.add(object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(resource, null);
            holder = new ViewHolder();
            holder.loginText = (TextView) convertView.findViewById(R.id.login);
            holder.blogText = (TextView) convertView.findViewById(R.id.blog);
            holder.reposText = (TextView) convertView.findViewById(R.id.repos);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Github user = getItem(position);
        holder.loginText.setText("user : " + user.getLogin());
        holder.blogText.setText("blog : " + user.getBlog());
        holder.reposText.setText("public_repos : " + user.getPublicRepos());

        return convertView;
    }


    static class ViewHolder {
        private TextView loginText;
        private TextView blogText;
        private TextView reposText;
    }
}
