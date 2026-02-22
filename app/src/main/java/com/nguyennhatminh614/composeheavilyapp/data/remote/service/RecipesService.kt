package com.nguyennhatminh614.composeheavilyapp.data.remote.service

import com.nguyennhatminh614.composeheavilyapp.data.remote.dto.recipes.RecipesItem
import retrofit2.Response
import retrofit2.http.GET



interface RecipesService {
    @GET("recipes.json")
    suspend fun fetchRecipes(): Response<List<RecipesItem>>
}
