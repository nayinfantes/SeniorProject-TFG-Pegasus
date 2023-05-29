package com.example.app_pegasus.activities.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.ViewHolderChildrenData>{

    @NonNull
    @Override
    public ChildrenAdapter.ViewHolderChildrenData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChildrenAdapter.ViewHolderChildrenData holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderChildrenData extends RecyclerView.ViewHolder{

        public ViewHolderChildrenData(@NonNull View itemView) {
            super(itemView);
        }
    }
}
