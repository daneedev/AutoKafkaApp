package dev.daneeskripter.cernyrobin

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class APIMethods {
    suspend fun getRequest(url: String) : HttpResponse {
        return withContext(Dispatchers.IO) {
            val client = HttpClient(CIO)
            try {
                val response: HttpResponse = client.get(url)
                response
            } finally {
                client.close()
            }
        }
    }
}