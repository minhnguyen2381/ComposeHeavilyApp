package com.nguyennhatminh614.composeheavilyapp.data.local.dao

import androidx.room.*
import com.nguyennhatminh614.composeheavilyapp.data.local.entity.UserEntity


@Dao
interface UserDao {

    @Query("SELECT * FROM user where user.id=:id")
    fun getUser(id:Int): UserEntity

}
