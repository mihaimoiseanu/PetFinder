package com.example.petfinder

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.petfinder.repository.PetsMediator
import com.example.petfinder.storage.database.AnimalDatabase
import com.example.petfinder.storage.database.EntityAnimal
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalPagingApi::class)
@RunWith(AndroidJUnit4::class)
class PageKeyedRemoteMediatorTest {

    private val petFactory = PetFactory()
    private val mockDb = AnimalDatabase.create(
        context = ApplicationProvider.getApplicationContext(),
        useInMemory = true
    )
    private val mockPets = listOf(
        petFactory.createPet(),
        petFactory.createPet(),
        petFactory.createPet()
    )
    private val mockApi = MockPetFinderApi()


    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() {

        // Add mock results for the API to return.
        for (pet in mockPets) {
            mockApi.addPet(pet)
        }
        val remoteMediator = PetsMediator(
            mockDb,
            mockApi,
        )
        val pagingState: PagingState<Int, EntityAnimal> = PagingState(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        remoteMediator.loadSingle(LoadType.REFRESH, pagingState)
            .test()
            .await()
            .assertValueCount(1)
            .assertValue { value ->
                value is RemoteMediator.MediatorResult.Success && value.endOfPaginationReached
            }
    }

    @After
    fun tearDown() {
        mockDb.clearAllTables()
        // Clear out failure message to default to the successful response.
        mockApi.failureMsg = null
        // Clear out posts after each test run.
        mockApi.clearPets()
    }
}