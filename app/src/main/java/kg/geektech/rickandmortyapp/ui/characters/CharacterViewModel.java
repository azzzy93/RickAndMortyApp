package kg.geektech.rickandmortyapp.ui.characters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kg.geektech.rickandmortyapp.App;
import kg.geektech.rickandmortyapp.common.Resource;
import kg.geektech.rickandmortyapp.data.models.Character;
import kg.geektech.rickandmortyapp.data.models.RickAndMortyResponse;
import kg.geektech.rickandmortyapp.data.remote.paging.CharactersDataSourceFactory;
import kg.geektech.rickandmortyapp.data.remote.paging.CharactersStorage;
import kg.geektech.rickandmortyapp.data.repository.MainRepository;

@HiltViewModel
public class CharacterViewModel extends ViewModel {
    private MainRepository repository;
    private CharactersDataSourceFactory sourceFactory;
    public LiveData<Resource<RickAndMortyResponse<Character>>> liveData;
    public LiveData<PagedList<Character>> pagedLiveData;

    PagedList.Config config = (new PagedList.Config.Builder())
            .setEnablePlaceholders(true)
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .build();

    @Inject
    public CharacterViewModel(MainRepository repository, CharactersStorage storage) {
        sourceFactory = new CharactersDataSourceFactory(storage);
        this.repository = repository;
        pagedLiveData = new LivePagedListBuilder<>(sourceFactory, config).build();
    }

    public void getCharacters(){
        liveData = repository.getCharacters();
    }
}
