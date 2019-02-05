package com.unitedcreation.latticeinnovations.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.unitedcreation.latticeinnovations.R;
import com.unitedcreation.latticeinnovations.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder> {

    private final myOnClickListener myOnClickListener;
    private List<User> userList;

    public interface myOnClickListener {
        void onClickListener(int key, String name, String address, String email, String phone);
    }

    public UserRecyclerViewAdapter(myOnClickListener myOnClickListener, List<User> userList) {
        this.myOnClickListener = myOnClickListener;
        this.userList = userList;
    }

    public void updateList (List<User> userList) {

        this.userList.clear();
        this.userList = userList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(root);

    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        String name = userList.get(position).getName();

        holder.name.setText(name);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(userList.get(position).getKey());

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(name.substring(0, 1), color);

        holder.avatar.setImageDrawable(drawable);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.avatar_iv)
        ImageView avatar;

        @BindView(R.id.name_tv)
        TextView name;

        UserViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            myOnClickListener.onClickListener(userList.get(getAdapterPosition()).getKey(),
                    userList.get(getAdapterPosition()).getName(),
                    userList.get(getAdapterPosition()).getAddress(),
                    userList.get(getAdapterPosition()).getEmail(),
                    userList.get(getAdapterPosition()).getPhone());
        }
    }
}
