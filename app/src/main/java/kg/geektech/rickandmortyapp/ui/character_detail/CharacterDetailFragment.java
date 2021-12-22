package kg.geektech.rickandmortyapp.ui.character_detail;

import android.graphics.Bitmap;
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

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import dagger.hilt.android.AndroidEntryPoint;
import kg.geektech.rickandmortyapp.R;
import kg.geektech.rickandmortyapp.common.Resource;
import kg.geektech.rickandmortyapp.data.models.Character;
import kg.geektech.rickandmortyapp.databinding.FragmentCharacterDetailBinding;

@AndroidEntryPoint
public class CharacterDetailFragment extends Fragment {
    private FragmentCharacterDetailBinding binding;
    private CharacterDetailFragmentArgs args;
    private CharacterDetailViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CharacterDetailViewModel.class);
        args = CharacterDetailFragmentArgs.fromBundle(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false);
        viewModel.getCharacterById(args.getCharacterId());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.liveData.observe(getViewLifecycleOwner(), new Observer<Resource<Character>>() {
            @Override
            public void onChanged(Resource<Character> characterResource) {
                switch (characterResource.status){
                    case SUCCESS:{
                        Character character = characterResource.data;
                        binding.characterName.setText(character.getName());
                        binding.characterStatus.setText(character.getStatus());
                        Glide.with(requireContext()).load(character.getImage()).into(binding.ivCharacter);
                        onSuccessState();
                        generateQr(args.getCharacterId());
                        break;
                    }
                    case ERROR:{
                        break;
                    }
                    case LOADING:{
                        onLoadingState();
                        break;
                    }
                }
            }
        });
    }

    private void onLoadingState(){
        binding.characterStatus.setVisibility(View.GONE);
        binding.characterName.setVisibility(View.GONE);
        binding.ivCharacter.setVisibility(View.GONE);
    }

    private void onSuccessState(){
        binding.characterStatus.setVisibility(View.VISIBLE);
        binding.characterName.setVisibility(View.VISIBLE);
        binding.ivCharacter.setVisibility(View.VISIBLE);
    }

    private void generateQr(int id){
        try {
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap qrBitmap = encoder.encodeBitmap(String.valueOf(id),
                    BarcodeFormat.QR_CODE,
                    200,
                    200);
            binding.ivQr.setImageBitmap(qrBitmap);
        } catch (Exception e){
            Log.e("TAG", "generateQR: " + e.getLocalizedMessage());
        }
    }
}