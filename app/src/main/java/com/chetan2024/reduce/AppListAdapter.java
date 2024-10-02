package com.chetan2024.reduce;

import android.content.pm.ApplicationInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppViewHolder> {

    private final List<ApplicationInfo> appList;
    private final List<ApplicationInfo> selectedApps = new ArrayList<>();

    public AppListAdapter(List<ApplicationInfo> appList) {
        this.appList = appList;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        ApplicationInfo appInfo = appList.get(position);
        holder.appName.setText(appInfo.loadLabel(holder.itemView.getContext().getPackageManager()));
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedApps.add(appInfo);
            } else {
                selectedApps.remove(appInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public List<ApplicationInfo> getSelectedApps() {
        return selectedApps;
    }

    static class AppViewHolder extends RecyclerView.ViewHolder {
        TextView appName;
        CheckBox checkBox;

        AppViewHolder(View itemView) {
            super(itemView);
            appName = itemView.findViewById(R.id.app_name);
            checkBox = itemView.findViewById(R.id.app_checkbox);
        }
    }
}
