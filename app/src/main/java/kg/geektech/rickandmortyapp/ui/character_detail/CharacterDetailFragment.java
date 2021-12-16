package kg.geektech.rickandmortyapp.ui.character_detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.geektech.rickandmortyapp.R;
import kg.geektech.rickandmortyapp.databinding.FragmentCharacterDetailBinding;

public class CharacterDetailFragment extends Fragment {
    private FragmentCharacterDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}