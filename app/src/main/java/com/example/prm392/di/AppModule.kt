package com.example.prm392.di

import android.app.Application
import android.content.Context
import com.example.prm392.data.ICartApi
import com.example.prm392.data.ICategoryApi
import com.example.prm392.data.IClothingProductApi
import com.example.prm392.data.IMessageApi
import com.example.prm392.data.INotifyAPI
import com.example.prm392.data.IProductApi
import com.example.prm392.data.IStoreLocationApi
import com.example.prm392.data.IUserApi
import com.example.prm392.data.repository.CartRepository
import com.example.prm392.data.repository.CategoryRepository
import com.example.prm392.data.repository.ClothingProductRepository
import com.example.prm392.data.repository.MessageRepository
import com.example.prm392.data.repository.NotifyRepository
import com.example.prm392.data.repository.ProductRepository
import com.example.prm392.data.repository.StoreLocationRepository
import com.example.prm392.data.repository.UserRepository
import com.example.prm392.domain.service.Cart.AddUserCartService
import com.example.prm392.domain.service.Cart.CartService
import com.example.prm392.domain.service.Cart.GetUserCartService
import com.example.prm392.domain.service.Cart.RemoveUserCartService
import com.example.prm392.domain.service.Cart.UpdateUserCartItemQuantityService
import com.example.prm392.domain.service.Category.CategoryService
import com.example.prm392.domain.service.Category.GetAllCategoryService
import com.example.prm392.domain.repository.IMessageRepository
import com.example.prm392.domain.service.ClothingProduct.ClothingProductService
import com.example.prm392.domain.service.ClothingProduct.GetAllClothing
import com.example.prm392.domain.service.ClothingProduct.GetProductById
import com.example.prm392.domain.service.GetProductDataService
import com.example.prm392.domain.service.GetSearchProductDataService
import com.example.prm392.domain.service.MessageService.GetListChat
import com.example.prm392.domain.service.MessageService.GetMessageById
import com.example.prm392.domain.service.MessageService.MessageService
import com.example.prm392.domain.service.MessageService.SendMessage
import com.example.prm392.domain.service.NotifyService.GetNotify
import com.example.prm392.domain.service.NotifyService.NotifyService
import com.example.prm392.domain.service.NotifyService.UpdateStatus
import com.example.prm392.domain.service.Services
import com.example.prm392.domain.service.User.GetAuthUserService
import com.example.prm392.domain.service.User.LoginService
import com.example.prm392.domain.service.User.RegisterService
import com.example.prm392.domain.service.User.UserService
import com.example.prm392.domain.service.store_location.GetAllStoreLocationService
import com.example.prm392.domain.service.store_location.StoreLocationService
import com.example.prm392.presentation.navigation.Navigator
import com.example.prm392.utils.Constants
import com.example.prm392.utils.HeaderProcessing
import com.example.prm392.utils.TokenSlice
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application)
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
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
    fun provideNavigation(): Navigator = Navigator()

    @Provides
    @Singleton
    fun provideServices(repository: ProductRepository, messageRepository: MessageRepository, notifyRepository: NotifyRepository): Services {
        return Services(
            getProductDataService = GetProductDataService(repository),
            getSearchProductDataService = GetSearchProductDataService(repository),
            updateStatus = UpdateStatus(notifyRepository),
            getNotifyService = GetNotify(notifyRepository),
            getMessageService = GetMessageById(messageRepository),
            sendMessageService = SendMessage(messageRepository),
            getListChat = GetListChat(messageRepository)
        )
    }

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
    fun provideClothingProductApi(retrofit: Retrofit): IClothingProductApi {
        return retrofit.create(IClothingProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideClothingProductRepository(
        api: IClothingProductApi,
        headerProcessing: HeaderProcessing
    ): ClothingProductRepository {
        return ClothingProductRepository(api, headerProcessing)
    }

    @Provides
    @Singleton
    fun provideClothingProductServices(repository: ClothingProductRepository): ClothingProductService {
        return ClothingProductService(
            getAllClothing = GetAllClothing(repository),
            getProductById = GetProductById(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCategoryApi(retrofit: Retrofit): ICategoryApi {
        return retrofit.create(ICategoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        api: ICategoryApi,
        headerProcessing: HeaderProcessing
    ): CategoryRepository {
        return CategoryRepository(api, headerProcessing)
    }

    @Provides
    @Singleton
    fun provideCategoryServices(repository: CategoryRepository): CategoryService {
        return CategoryService(
            getAllCategoryService = GetAllCategoryService(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCartApi(retrofit: Retrofit): ICartApi {
        return retrofit.create(ICartApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        api: ICartApi,
        headerProcessing: HeaderProcessing
    ): CartRepository {
        return CartRepository(api, headerProcessing)
    }

    @Provides
    @Singleton
    fun provideCartServices(repository: CartRepository): CartService {
        return CartService(
            getUserCartService = GetUserCartService(repository),
            addUserCartService = AddUserCartService(repository),
            removeUserCartService = RemoveUserCartService(repository),
            updateUserCartItemQuantityService = UpdateUserCartItemQuantityService(repository)
        )
    }

    @Provides
    @Singleton
    fun provideStoreLocationApi(retrofit: Retrofit): IStoreLocationApi {
        return retrofit.create(IStoreLocationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStoreLocationRepository(api: IStoreLocationApi): StoreLocationRepository {
        return StoreLocationRepository(api)
    }

    @Provides
    @Singleton
    fun provideStoreLocationService(repository: StoreLocationRepository): StoreLocationService {
        return StoreLocationService(
            getAllStoreLocation = GetAllStoreLocationService(repository)
        )
    }
    @Provides
    @Singleton
    fun provideMessageApi(retrofit: Retrofit): IMessageApi {
        return retrofit.create(IMessageApi::class.java)
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
            getListChat = GetListChat(repository)
        )
    }

    @Provides
    @Singleton
    fun providerNotifyApi(retrofit: Retrofit): INotifyAPI {
        return retrofit.create(INotifyAPI::class.java)
    }

    @Provides
    @Singleton
    fun providerNotifyRepository(api: INotifyAPI, headerProcessing: HeaderProcessing): NotifyRepository {
        return NotifyRepository(api, headerProcessing)
    }

    @Provides
    @Singleton
    fun providerNotifyService(repository: NotifyRepository): NotifyService {
        return NotifyService(
            getNotify = GetNotify(repository),
            updateStatus = UpdateStatus(repository)
        )
    }
}
