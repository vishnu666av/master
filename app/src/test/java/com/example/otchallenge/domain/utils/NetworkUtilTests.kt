package com.example.otchallenge.domain.utils

import com.example.otchallenge.domain.errors.AppError
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException

class NetworkUtilTests {

    @Test
    fun `test IllegalStateException returns network error`() = runTest {
        val result = secureNetworkRequest {
            val test: () -> Response<String> = {
                error("expected error")
            }
            test()
        }
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(AppError.Network::class.java)
    }

    @Test
    fun `test non 200 returns network error`() = runTest {
        val result = secureNetworkRequest {
            Response.error<String>(404, "Unknown error".toResponseBody())
        }
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(AppError.Http::class.java)
        assertThat((result.exceptionOrNull() as AppError.Http).httpCode).isEqualTo(404)
    }

    @Test
    fun `test empty body returns empty body error`() = runTest {
        val result = secureNetworkRequest {
            Response.success<String>(200, null)
        }
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(AppError.Http::class.java)
        assertThat((result.exceptionOrNull() as AppError.Http).httpCode).isEqualTo(200)
        assertThat((result.exceptionOrNull() as AppError.Http).message).isEqualTo("Empty body")
    }


    @Test(expected = CancellationException::class)
    fun `test cancellationException is rethrow`() = runTest {
        secureNetworkRequest {
            val test: () -> Response<String> = {
                throw CancellationException("test")
            }
            test()
        }
    }

    @Test
    fun `test success returns expected data`() = runTest {
        val result = secureNetworkRequest {
            Response.success("My data")
        }
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo("My data")
    }
}
