package kg.geektech.rickandmortyapp.data.remote.paging;

import androidx.paging.DataSource;

import kg.geektech.rickandmortyapp.data.models.Character;

public class CharactersDataSourceFactory extends DataSource.Factory<Integer, Character> {
    private CharactersStorage storage;

    public CharactersDataSourceFactory(CharactersStorage storage) {
        this.storage = storage;
    }

    @Override
    public DataSource<Integer, Character> create() {
        return new CharactersDataSource(storage);
    }
}
