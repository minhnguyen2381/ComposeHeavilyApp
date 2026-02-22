package com.nguyennhatminh614.composeheavilyapp.data

import com.nguyennhatminh614.composeheavilyapp.data.remote.dto.recipes.Recipes
import com.nguyennhatminh614.composeheavilyapp.data.local.UserLocalData
import com.nguyennhatminh614.composeheavilyapp.data.local.entity.UserEntity
import com.nguyennhatminh614.composeheavilyapp.data.remote.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class DataRepository @Inject constructor(private val remoteRepository: RemoteData, private val userLocalData: UserLocalData,
                                         private val ioDispatcher: CoroutineContext) : DataRepositorySource {

    /**
     * Get list recipes from remote
     */
    override suspend fun requestRecipes(): Flow<Resource<Recipes>> {
        return flow {
            emit(remoteRepository.requestRecipes())
        }.flowOn(ioDispatcher)
    }

    /**
     * Get User from local database
     */
    override suspend fun getUser(id: Int): Flow<Resource<UserEntity>> {
        return flow {
            emit(Resource.Success(userLocalData.getUser(id)))
        }.flowOn(ioDispatcher)
    }

    override fun getUser2(id: Int) {
        userLocalData.getUser(id)
    }


}
