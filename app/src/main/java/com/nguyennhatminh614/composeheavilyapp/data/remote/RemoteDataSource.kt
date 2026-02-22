package com.nguyennhatminh614.composeheavilyapp.data.remote

import com.nguyennhatminh614.composeheavilyapp.data.Resource
import com.nguyennhatminh614.composeheavilyapp.data.remote.dto.recipes.Recipes


internal interface RemoteDataSource {
    suspend fun requestRecipes(): Resource<Recipes>
}
