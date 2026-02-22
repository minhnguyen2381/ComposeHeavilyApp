package com.nguyennhatminh614.composeheavilyapp.usecase.errors

import com.nguyennhatminh614.composeheavilyapp.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}

