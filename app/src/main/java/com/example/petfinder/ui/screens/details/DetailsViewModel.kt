package com.example.petfinder.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petfinder.storage.database.AnimalDao
import com.example.petfinder.ui.navigation.DetailsDirection
import com.example.petfinder.ui.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel
@Inject constructor(
    private val animalDao: AnimalDao,
    private val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val petId = DetailsDirection.getPetId(savedStateHandle)
    private val disposables = CompositeDisposable()

    val screenModel = MutableStateFlow(DetailsScreenModel())

    init {
        disposables += animalDao.getPetById(petId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { pet -> screenModel.update { it.copy(animal = pet) } },
                { Timber.e(it) }
            )
    }

    fun navigateBack() {
        viewModelScope.launch { navigationManager.navigateBack() }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}