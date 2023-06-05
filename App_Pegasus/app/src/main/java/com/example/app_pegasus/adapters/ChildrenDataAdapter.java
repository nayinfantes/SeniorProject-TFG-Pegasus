package com.example.app_pegasus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_pegasus.R;
import com.example.app_pegasus.models.Children;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ChildrenDataAdapter extends FirebaseRecyclerAdapter<Children, ChildrenDataAdapter.ViewHolder > {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private Context mContext;
    public ChildrenDataAdapter(FirebaseRecyclerOptions<Children> options, Context context) {
        super(options);
        mContext = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull Children childrenData) {
        holder.mTextChildrenName.setText(childrenData.getName());
        holder.mTextChildrenEmail.setText(childrenData.getEmail());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_children_information, parent, false);
        return new ViewHolder(view) ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextChildrenName, mTextChildrenEmail;

        public ViewHolder( View view){
            super (view);

            mTextChildrenName = view.findViewById(R.id.showChildrenNameParentProfile);
            mTextChildrenEmail = view.findViewById(R.id.showChildrenEmailParentProfile);
        }
    }
}
