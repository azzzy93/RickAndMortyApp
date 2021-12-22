package kg.geektech.rickandmortyapp.ui.characters;

import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

import dagger.hilt.android.AndroidEntryPoint;
import kg.geektech.rickandmortyapp.R;
import kg.geektech.rickandmortyapp.data.models.Character;
import kg.geektech.rickandmortyapp.databinding.FragmentCharactersBinding;

@AndroidEntryPoint
public class CharactersFragment extends Fragment implements OnItemClick {
    private FragmentCharactersBinding binding;
    private CharacterViewModel viewModel;
    private CharactersAdapter adapter;
    private CharacterPageListAdapter pagedAdapter;
    private NavController navController;
    private ActivityResultLauncher<ScanOptions> barcodeResult = registerForActivityResult(
            new ScanContract(), result -> {
                if (result.getContents() != null) {
                    navController.navigate(CharactersFragmentDirections
                            .actionCharactersFragmentToCharacterDetailFragment(Integer.parseInt(result.getContents())));
                }
            }
    );


    private DiffUtil.ItemCallback<Character> getDiffCallback(){
        return new DiffUtil.ItemCallback<Character>() {
            @Override
            public boolean areItemsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
                return oldItem.equals(newItem);
            }
        };
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new CharactersAdapter();

        pagedAdapter = new CharacterPageListAdapter(getDiffCallback());
        adapter.setOnItemClickListener(this);
        viewModel = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);
        viewModel.getCharacters();

        NavHostFragment hostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager().findFragmentById(R.id.nav_host);
        navController = hostFragment.getNavController();
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


        binding.rvCharacters.setAdapter(pagedAdapter);

        viewModel.pagedLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<Character>>() {
            @Override
            public void onChanged(PagedList<Character> characters) {
                pagedAdapter.submitList(characters);
            }
        });
        viewModel.liveData.observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case SUCCESS: {
                    binding.rvCharacters.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                    adapter.setCharacters(resource.data.getResults());
                    break;
                }
                case ERROR: {
                    binding.rvCharacters.setVisibility(View.GONE);
                    binding.progressBar.setVisibility(View.GONE);
                    Log.e("TAG", "onChanged: " + resource.message);
                    break;
                }
                case LOADING: {
                    binding.rvCharacters.setVisibility(View.GONE);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    break;
                }
            }
        });

        binding.btnQr.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            options.setPrompt("Отсканируйте QR пресонажа");
            options.setBeepEnabled(true);
            options.setCameraId(0);
            barcodeResult.launch(options);
        });
    }

    @Override
    public void onClick(Character character) {
        navController.navigate(CharactersFragmentDirections
                .actionCharactersFragmentToCharacterDetailFragment(character.getId()));
    }
}