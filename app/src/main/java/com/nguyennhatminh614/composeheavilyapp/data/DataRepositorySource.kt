package com.nguyennhatminh614.composeheavilyapp.data

import com.nguyennhatminh614.composeheavilyapp.data.remote.dto.recipes.Recipes
import com.nguyennhatminh614.composeheavilyapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow



interface DataRepositorySource {
    suspend fun requestRecipes(): Flow<Resource<Recipes>>

    suspend fun getUser(id: Int): Flow<Resource<UserEntity>>
    fun getUser2(id: Int)
}
