package com.glob.mytrips.domain.usecases.places

import com.glob.mytrips.domain.executors.PostExecutorThread
import com.glob.mytrips.domain.repositories.PlacesRepository
import com.glob.mytrips.domain.usecases.mocks.MyTripsMocks
import com.glob.mytrips.domain.usecases.ImmediateExecutorThread
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class GetPlacesByUserUseCaseTest : TestCase() {

    @Mock
    lateinit var placesRepository: PlacesRepository

    @Mock
    lateinit var postExecutorThread: PostExecutorThread

    private val placeByUserUseCase: GetPlacesByUserUseCase by lazy {
        GetPlacesByUserUseCase(placesRepository,
            ImmediateExecutorThread(), postExecutorThread)
    }

    @Before
    fun setup() {
        Mockito.`when`(postExecutorThread.getScheduler()).thenReturn(Schedulers.trampoline())
    }

    @Test
    fun validatePlaceSuccess() {
        val params = GetPlacesByUserUseCase.Params(1)
        Mockito.`when`(placesRepository.getPlacesByUser(1)).thenReturn(Single.just(listOf(MyTripsMocks().placeMock)))
        placeByUserUseCase.execute(params)
            .map {
                it[0]
            }
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                it.favorite == MyTripsMocks().placeMock.favorite
            }
            .assertValue {
                it.id == MyTripsMocks().placeMock.id
            }
            .assertValue {
                it.description == MyTripsMocks().placeMock.description
            }
    }

    @Test
    fun validatePlaceError() {
        val params = GetPlacesByUserUseCase.Params(2)
        val message = "Item not found"
        Mockito.`when`(placesRepository.getPlacesByUser(2))
            .thenReturn(Single.error(Throwable(message)))
        placeByUserUseCase.execute(params)
            .test()
            .assertNotComplete()
            .assertError {
                it.message == message
            }
    }

    @Test
    fun validatePlaceFail() {
        val message = "Invalid Arguments"
        placeByUserUseCase.execute(null)
            .test()
            .assertNotComplete()
            .assertError {
                it.message == message
            }
    }

}