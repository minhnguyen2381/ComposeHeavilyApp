package com.nguyennhatminh614.composeheavilyapp.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nguyennhatminh614.composeheavilyapp.data.Resource
import com.nguyennhatminh614.composeheavilyapp.usecase.errors.ErrorManager
import com.nguyennhatminh614.composeheavilyapp.util.SOMETHING_WENT_WRONG
import kotlinx.coroutines.CoroutineExceptionHandler

import javax.inject.Inject



abstract class BaseViewModel : ViewModel() {
    /**Inject Singleton ErrorManager
     * Use this errorManager to get the Errors
     */
    @Inject
    lateinit var errorManager: ErrorManager

    val showDialogLoadingPrivate = MutableLiveData(false)

    val showMessageDialog = MutableLiveData<Resource<String>>()

    private val onErrorDialogDismissPrivate = MutableLiveData<Resource<Boolean>>()
    val onErrorDialogDismiss: LiveData<Resource<Boolean>> get() = onErrorDialogDismissPrivate

    protected val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        hideLoading()
        showMessageDialog(Resource.DataError(SOMETHING_WENT_WRONG))

    }


    fun isLoading(): Boolean {
        return showDialogLoadingPrivate.value!!
    }


    fun showLoading() {
        if (!showDialogLoadingPrivate.value!!) {
            showDialogLoadingPrivate.value = true
        }

    }

    fun hideLoading() {
        if (showDialogLoadingPrivate.value!!) {
            showDialogLoadingPrivate.value = false
        }
    }




    fun showMessageDialog(dataError: Resource.DataError<String>) {
        showMessageDialog.value = dataError
    }

    fun showPostMessageDialog(dataError: Resource.DataError<String>) {
        showMessageDialog.value = dataError
    }

    fun hideMessageDialog(success: Resource.Success<String>) {
        showMessageDialog.value = success

    }

}
