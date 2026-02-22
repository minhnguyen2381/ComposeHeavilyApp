package com.nguyennhatminh614.composeheavilyapp.data.local

import com.nguyennhatminh614.composeheavilyapp.data.local.dao.UserDao
import javax.inject.Inject

class UserLocalData @Inject constructor(private val userDao: UserDao) {

    fun getUser(id:Int) = userDao.getUser(id)
}