package com.matheus.popcodeentrevista.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matheus.popcodeentrevista.R;
import com.matheus.popcodeentrevista.models.Result;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolderCharacter> {
    private List<Result> results;


    @NonNull
    @Override
    public ViewHolderCharacter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_character, parent, false);
        return new ViewHolderCharacter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCharacter holder, int position) {
        Result result = results.get(position);

        holder.textViewCharacterName.setText(result.getName());
        holder.textViewCharacterMass.setText(result.getMass());
        holder.textViewCharacterGender.setText(result.getGender());
        holder.textViewCharacterHeight.setText(result.getHeight());
    }

    @Override
    public int getItemCount() {
       return results == null ? 0 : results.size();

    }

    public void update(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public class ViewHolderCharacter extends RecyclerView.ViewHolder {
        private TextView textViewCharacterName, textViewCharacterGender, textViewCharacterHeight, textViewCharacterMass;

        public ViewHolderCharacter(@NonNull View itemView) {
            super(itemView);
            textViewCharacterName = itemView.findViewById(R.id.textView_character_name);
            textViewCharacterHeight = itemView.findViewById(R.id.textView_character_height);
            textViewCharacterGender = itemView.findViewById(R.id.textView_character_gender);
            textViewCharacterMass = itemView.findViewById(R.id.textView_character_mass);
        }

    }
}
