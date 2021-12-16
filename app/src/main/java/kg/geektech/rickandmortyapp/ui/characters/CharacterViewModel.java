package kg.geektech.rickandmortyapp.ui.characters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import kg.geektech.rickandmortyapp.App;
import kg.geektech.rickandmortyapp.common.Resource;
import kg.geektech.rickandmortyapp.data.models.Character;
import kg.geektech.rickandmortyapp.data.models.RickAndMortyResponse;

public class CharacterViewModel extends ViewModel {

    public LiveData<Resource<RickAndMortyResponse<Character>>> liveData;

    public CharacterViewModel() {
    }

    public void getCharacters(){
        liveData = App.repository.getCharacters();
    }
}
