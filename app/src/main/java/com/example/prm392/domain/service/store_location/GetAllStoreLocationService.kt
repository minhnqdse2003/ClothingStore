package com.example.prm392.domain.service.store_location

import com.example.prm392.data.dto.store_location.get_all.GetAllStoreLocationResponseModel
import com.example.prm392.data.dto.store_location.get_all.StoreLocation
import com.example.prm392.domain.repository.IStoreLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllStoreLocationService @Inject constructor(
    private val repository: IStoreLocationRepository
) {
    suspend operator fun invoke(): Flow<GetAllStoreLocationResponseModel> = flow {
        // Uncomment the line below to use the real repository when ready
        // emit(repository.getAllStoreLocation())

        val mockedLocations = listOf(
            StoreLocation(1, "366 Đ. Phan Văn Trị, Phường 5, Gò Vấp, Hồ Chí Minh, Vietnam", 10.826480, 106.690610),
            StoreLocation(2, "527 Đ. Phan Văn Trị, Phường 5, Gò Vấp, Hồ Chí Minh 700000, Vietnam", 10.824790, 106.690640),
            StoreLocation(3, "262 Đ. Nguyễn Thái Sơn, Phường 4, Gò Vấp, Hồ Chí Minh 70000, Vietnam", 10.821510, 106.684440),
            StoreLocation(4, "84/1 Đ. Số 1, Phường 4, Gò Vấp, Hồ Chí Minh, Vietnam", 10.8212573, 106.6852947),
            StoreLocation(5, "Hẻm 600 Lê Quang Định, Phường 1, Gò Vấp, Hồ Chí Minh, Vietnam", 10.8179705, 106.6901928)
        )

        // Create a response model with mocked data
        val responseModel = GetAllStoreLocationResponseModel(
            status = 200, // Assuming 200 means success
            message = "Success",
            data = mockedLocations
        )

        emit(responseModel)
    }
}
