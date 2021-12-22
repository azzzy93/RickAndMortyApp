package kg.geektech.rickandmortyapp.data.remote.paging;

import javax.inject.Inject;

import kg.geektech.rickandmortyapp.data.models.Character;
import kg.geektech.rickandmortyapp.data.models.RickAndMortyResponse;
import kg.geektech.rickandmortyapp.data.remote.RickAndMortyApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharactersStorage {
    private RickAndMortyApi api;

    @Inject
    public CharactersStorage(RickAndMortyApi api) {
        this.api = api;
    }

    public void getCharactersByPage(int page, OnCharactersReadyCallback callback){
        api.getCharacterByPage(page).enqueue(new Callback<RickAndMortyResponse<Character>>() {
            @Override
            public void onResponse(Call<RickAndMortyResponse<Character>> call, Response<RickAndMortyResponse<Character>> response) {
                if (response.isSuccessful() && response.body() != null){
                    callback.onReady(response.body());
                }
            }

            @Override
            public void onFailure(Call<RickAndMortyResponse<Character>> call, Throwable t) {

            }
        });
    }

    interface OnCharactersReadyCallback{
        void onReady(RickAndMortyResponse<Character> response);
    }
}
