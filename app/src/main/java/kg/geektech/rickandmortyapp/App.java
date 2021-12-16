package kg.geektech.rickandmortyapp;

import android.app.Application;

import kg.geektech.rickandmortyapp.data.remote.RetrofitClient;
import kg.geektech.rickandmortyapp.data.remote.RickAndMortyApi;
import kg.geektech.rickandmortyapp.data.repository.MainRepository;

public class App extends Application {
    public static RickAndMortyApi api;
    public static MainRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitClient client = new RetrofitClient();
        api = client.provideApi();
        repository = new MainRepository();
    }
}
