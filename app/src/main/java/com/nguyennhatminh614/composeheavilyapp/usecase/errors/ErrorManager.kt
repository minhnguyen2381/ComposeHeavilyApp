package com.nguyennhatminh614.composeheavilyapp.usecase.errors

import com.nguyennhatminh614.composeheavilyapp.data.error.Error
import com.nguyennhatminh614.composeheavilyapp.data.error.mapper.ErrorMapper
import javax.inject.Inject



class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}
