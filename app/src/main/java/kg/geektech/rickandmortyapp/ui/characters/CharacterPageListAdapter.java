package kg.geektech.rickandmortyapp.ui.characters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import kg.geektech.rickandmortyapp.data.models.Character;
import kg.geektech.rickandmortyapp.databinding.ItemCharacterBinding;

public class CharacterPageListAdapter extends PagedListAdapter<Character, CharacterPageListAdapter.ViewHolder> {


    protected CharacterPageListAdapter(@NonNull DiffUtil.ItemCallback<Character> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCharacterBinding binding = ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }








    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCharacterBinding binding;

        public ViewHolder(@NonNull ItemCharacterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Character item) {
            binding.tvName.setText(item.getName());
            binding.tvStatus.setText(item.getStatus());
            Glide.with(binding.ivCharacter).load(item.getImage())
                    .centerCrop()
                    .into(binding.ivCharacter);
        }
    }
}
