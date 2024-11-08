package com.example.prm392.domain.model.ClothingProduct.request

data class ClothingProductFilterParam(
    val searchValue:String?,
    val categoryId:Int?
)

fun ClothingProductFilterParam.buildFilterMap(): Map<String, String> {
    val filterMap = mutableMapOf<String, String>()

    searchValue?.let { value ->
        if(value.isNotEmpty())
            filterMap["ProductName"] = value
    }
    categoryId?.let { id -> filterMap["CategoryID"] = id.toString() }

    return filterMap
}
