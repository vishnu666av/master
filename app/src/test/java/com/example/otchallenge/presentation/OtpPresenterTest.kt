package com.example.otchallenge.presentation

import com.example.otchallenge.domain.BookDomain
import com.example.otchallenge.domain.RepoResponse
import com.example.otchallenge.domain.fakes.FakeBooksRepo
import com.example.otchallenge.presentation.fakes.FakeOtpView
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class OtpPresenterTest {

    lateinit var sut: OtpPresenter
    private val fakeOtpView = FakeOtpView()
    private val fakeRepo = FakeBooksRepo(
        RepoResponse.Success(
            listOf(BookDomain("title1", "description1", "url1"))
        )
    )
    private val job = Job()

    @Before
    fun setUp() {
        sut = OtpPresenter(fakeOtpView, fakeRepo, job,  UnconfinedTestDispatcher())
    }

    @Test
    fun onStartWhenRepositoryCallSuccessfullDisplaySpinnerAndThenResults() {
        runTest {
            //when
            sut.onStart()

            // then
            assertTrue(fakeOtpView.listOfOperations[0] is OtpContract.Model.Loading)
            assertTrue(fakeOtpView.listOfOperations[1] is OtpContract.Model.Success)

            assertEquals(2, fakeOtpView.listOfOperations.size)
        }
    }

    @Test
    fun onStartWhenRepositoryCallUnSuccessfullDisplaySpinnerAndError() {
        runTest {
            //given
            fakeRepo.response =  RepoResponse.Error("some error",
                listOf(BookDomain("title1", "description1", "url1"))
            )

            //when
            sut.onStart()

            // then
            assertTrue(fakeOtpView.listOfOperations[0] is OtpContract.Model.Loading)
            assertTrue(fakeOtpView.listOfOperations[1] is OtpContract.Model.Error)

            assertEquals(2, fakeOtpView.listOfOperations.size)
        }
    }

    @Test
    fun onStop() {
        //given
        sut.onStop()

        //then
        assertTrue(job.isCancelled)
    }
}