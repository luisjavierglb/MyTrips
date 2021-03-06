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
class GetPlaceByIdUseCaseTest : TestCase() {

    @Mock
    lateinit var placesRepository: PlacesRepository

    @Mock
    lateinit var postExecutorThread: PostExecutorThread

    private val placeByIdUseCase: GetPlaceByIdUseCase by lazy {
        GetPlaceByIdUseCase(placesRepository,
            ImmediateExecutorThread(), postExecutorThread)
    }

    @Before
    fun setup() {
        Mockito.`when`(postExecutorThread.getScheduler()).thenReturn(Schedulers.trampoline())
    }

    @Test
    fun validatePlaceSuccess() {
        val params = GetPlaceByIdUseCase.Params(1)
        Mockito.`when`(placesRepository.getPlaceById(1)).thenReturn(Single.just(MyTripsMocks().placeMock))
        placeByIdUseCase.execute(params)
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
    val params = GetPlaceByIdUseCase.Params(3)
        val message = "Place not found"
        Mockito.`when`(placesRepository.getPlaceById(3))
            .thenReturn(Single.error(Throwable(message)))
        placeByIdUseCase.execute(params)
            .test()
            .assertNotComplete()
            .assertError {
                it.message == message
            }
    }

    @Test
    fun validatePlaceFail() {
        val message = "Place not found"
        placeByIdUseCase.execute(null)
            .test()
            .assertNotComplete()
            .assertError {
                it.message == message
            }
    }

}