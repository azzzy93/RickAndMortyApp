package kg.geektech.rickandmortyapp.ui.characters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.geektech.rickandmortyapp.R;
import kg.geektech.rickandmortyapp.common.Resource;
import kg.geektech.rickandmortyapp.data.models.Character;
import kg.geektech.rickandmortyapp.data.models.RickAndMortyResponse;
import kg.geektech.rickandmortyapp.databinding.FragmentCharactersBinding;

public class CharactersFragment extends Fragment {
    private FragmentCharactersBinding binding;
    private CharacterViewModel viewModel;
    private CharactersAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new CharactersAdapter();
        viewModel = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);
        viewModel.getCharacters();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCharactersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvCharacters.setAdapter(adapter);
        viewModel.liveData.observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status){
                case SUCCESS:{
                    binding.rvCharacters.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                    adapter.setCharacters(resource.data.getResults());
                    break;
                }
                case ERROR:{
                    binding.rvCharacters.setVisibility(View.GONE);
                    binding.progressBar.setVisibility(View.GONE);
                    Log.e("TAG", "onChanged: " + resource.message);
                    break;
                }
                case LOADING:{
                    binding.rvCharacters.setVisibility(View.GONE);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    break;
                }
            }
        });
    }
}