package kg.geektech.rickandmortyapp.di;

import java.util.concurrent.TimeUnit;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import kg.geektech.rickandmortyapp.data.remote.RickAndMortyApi;
import kg.geektech.rickandmortyapp.data.remote.paging.CharactersDataSource;
import kg.geektech.rickandmortyapp.data.remote.paging.CharactersStorage;
import kg.geektech.rickandmortyapp.data.repository.MainRepository;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn({SingletonComponent.class})
public abstract class AppModule {

    @Provides
    public static MainRepository provideMainRepository(RickAndMortyApi api){
        return new MainRepository(api);
    }

    @Provides
    public static RickAndMortyApi provideApi(Retrofit retrofit) {
        return retrofit.create(RickAndMortyApi.class);
    }

    @Provides
    public static OkHttpClient provideOkHttpClient(Interceptor loggingInterceptor) {
        return new OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    public static Interceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    public static Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    public static CharactersStorage provideCharactersStorage(RickAndMortyApi api){
        return new CharactersStorage(api);
    }

    @Provides
    public static CharactersDataSource provideDataSource(CharactersStorage storage){
        return new CharactersDataSource(storage);
    }
}
