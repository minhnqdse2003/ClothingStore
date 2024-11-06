package com.example.prm392.di

import android.content.Context
import com.example.prm392.data.IClothingProductApi
import com.example.prm392.data.IMessageApi
import com.example.prm392.data.IProductApi
import com.example.prm392.data.IUserApi
import com.example.prm392.data.repository.ClothingProductRepository
import com.example.prm392.data.repository.MessageRepository
import com.example.prm392.data.repository.ProductRepository
import com.example.prm392.data.repository.UserRepository
import com.example.prm392.domain.repository.IMessageRepository
import com.example.prm392.domain.service.ClothingProduct.ClothingProductService
import com.example.prm392.domain.service.ClothingProduct.GetAllClothing
import com.example.prm392.domain.service.GetProductDataService
import com.example.prm392.domain.service.GetSearchProductDataService
import com.example.prm392.domain.service.MessageService.GetMessageById
import com.example.prm392.domain.service.MessageService.MessageService
import com.example.prm392.domain.service.MessageService.SendMessage
import com.example.prm392.domain.service.Services
import com.example.prm392.domain.service.User.GetAuthUserService
import com.example.prm392.domain.service.User.LoginService
import com.example.prm392.domain.service.User.RegisterService
import com.example.prm392.domain.service.User.UserService
import com.example.prm392.presentation.navigation.Navigator
import com.example.prm392.utils.Constants
import com.example.prm392.utils.HeaderProcessing
import com.example.prm392.utils.TokenSlice
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
            .readTimeout(10000L, TimeUnit.MILLISECONDS)
            .writeTimeout(10000L, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi,okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideMessageRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_2)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    @Provides
    @Singleton
    fun provideHeaderProcessing(tokenSlice: TokenSlice): HeaderProcessing {
        return HeaderProcessing(tokenSlice)
    }

    @Provides
    @Singleton
    fun provideTokenSlice(@ApplicationContext context: Context): TokenSlice {
        return TokenSlice(context)
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): IProductApi {
        return retrofit.create(IProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(api: IProductApi): ProductRepository {
        return ProductRepository(api)
    }

    @Provides
    @Singleton
    fun provideNavigation() : Navigator = Navigator()

//    @Provides
//    @Singleton
//    fun provideServices(repository: ProductRepository): Services {
//        return Services(
//            getProductDataService = GetProductDataService(repository),
//            getSearchProductDataService = GetSearchProductDataService(repository)
//        )
//    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): IUserApi {
        return retrofit.create(IUserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: IUserApi, headerProcessing: HeaderProcessing): UserRepository {
        return UserRepository(api, headerProcessing)
    }

    @Provides
    @Singleton
    fun provideUserServices(repository: UserRepository): UserService {
        return UserService(
            loginService = LoginService(repository),
            registerService = RegisterService(repository),
            getAuthUserService = GetAuthUserService(repository)
        )
    }

    @Provides
    @Singleton
    fun provideClothingProductApi(retrofit: Retrofit) : IClothingProductApi {
        return retrofit.create(IClothingProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideClothingProductRepository(api: IClothingProductApi, headerProcessing: HeaderProcessing): ClothingProductRepository {
        return ClothingProductRepository(api, headerProcessing)
    }

    @Provides
    @Singleton
    fun provideClothingProductServices(repository: ClothingProductRepository): ClothingProductService {
        return ClothingProductService(
           getAllClothing = GetAllClothing(repository)
        )
    }
    @Provides
    @Singleton
    fun provideMessageApi(messageRetrofit: Retrofit): IMessageApi {
        return messageRetrofit.create(IMessageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMessageRepository(api: IMessageApi, headerProcessing: HeaderProcessing): MessageRepository {
        return MessageRepository(api, headerProcessing)
    }

    @Provides
    @Singleton
    fun provideMessageService(repository: MessageRepository): MessageService {
        return MessageService(
            getMessageById = GetMessageById(repository),
            sendMessage = SendMessage(repository),
        )
    }

//    @Provides
//    @Singleton
//    fun provideSignalRService(): SignalRService {
//        return SignalRService()
//    }
//
//    @Provides
//    @Singleton
//    fun provideSendMessageUseCase(signalRService: SignalRService?): SendMessageUseCase {
//        return SendMessageUseCase(signalRService)
//    }
//
//    @Provides
//    @Singleton
//    fun provideReceiveMessageUseCase(signalRService: SignalRService?): ReceiveMessageUseCase {
//        return ReceiveMessageUseCase(signalRService)
//    }
}
