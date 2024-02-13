package com.example.petfinder.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.petfinder.repository.PetsMediator
import com.example.petfinder.storage.database.AnimalDao
import com.example.petfinder.storage.database.EntityAnimal
import com.example.petfinder.ui.navigation.DetailsDirection
import com.example.petfinder.ui.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel
@Inject constructor(
    private val animalDao: AnimalDao,
    private val petsMediator: PetsMediator,
    private val navigationManager: NavigationManager
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    private val pager = Pager(
        config = PagingConfig(20),
        initialKey = 1,
        remoteMediator = petsMediator,
        pagingSourceFactory = { animalDao.pagingSource() }
    )

    val screenModel = MutableStateFlow(ListScreenModel(pager))

    fun onPetClick(pet: EntityAnimal) {
        viewModelScope.launch {
            navigationManager.navigate(DetailsDirection(pet.id))
        }
    }

}