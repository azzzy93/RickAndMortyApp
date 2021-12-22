package kg.geektech.rickandmortyapp.data.remote.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import javax.inject.Inject;

import kg.geektech.rickandmortyapp.data.models.Character;
import kg.geektech.rickandmortyapp.data.models.RickAndMortyResponse;

public class CharactersDataSource extends PageKeyedDataSource<Integer, Character> {
    private CharactersStorage storage;

    @Inject
    public CharactersDataSource(CharactersStorage storage) {
        this.storage = storage;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Character> callback) {
        storage.getCharactersByPage(0, response -> {
            if (response != null){
                List<Character> result = response.getResults();
                String[] splitNextPageUrl = response.getInfo().getNext().split("=");
                Integer nextPage = Integer.parseInt(splitNextPageUrl[1]);
                callback.onResult(result, null, nextPage);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Character> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Character> callback) {
        storage.getCharactersByPage(params.key, response -> {
            List<Character> result = response.getResults();
            String[] splitNextPageUrl = response.getInfo().getNext().split("=");
            Integer nextPage = Integer.parseInt(splitNextPageUrl[1]);
            callback.onResult(result, nextPage);
        });
    }
}
