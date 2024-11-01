package com.example.prm392.domain.model.ClothingProduct.request

data class ClothingProductFilterParam(
    val searchValue:String?,
    val categoryId:Int?
)

fun ClothingProductFilterParam.buildFilterMap(): Map<String, Any?> {
    val filterMap = mutableMapOf<String, Any?>()

    searchValue?.let { value ->
        if(value.isNotEmpty())
            filterMap["ProductName"] = value
    }
    categoryId?.let { id -> filterMap["CategoryID"] = id }

    return filterMap
}
