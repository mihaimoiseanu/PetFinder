package com.example.petfinder.ui.screens.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petfinder.networking.AuthInterceptor
import com.example.petfinder.networking.PetFinderAPI
import com.example.petfinder.ui.navigation.ListDirection
import com.example.petfinder.ui.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val petFinderAPI: PetFinderAPI,
    private val authInterceptor: AuthInterceptor,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    init {
        getAuthToken()
    }

    fun getAuthToken() {
        disposables += petFinderAPI.getAccessToken(
            clientId = "8FvB92COL3loJkRHBozGPLOVKZTG4CgXal6Dou6EjsH5lj2SXB",
            clientSecret = "zcYSA3CrhG6yW1dc539o8rAVgj7ecwLUaYHTSe3s"
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { accessToken ->
                    authInterceptor.authToken = accessToken.accessToken
                    viewModelScope.launch { navigationManager.navigate(ListDirection) }
                },
                {
                    Timber.e(it)
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}