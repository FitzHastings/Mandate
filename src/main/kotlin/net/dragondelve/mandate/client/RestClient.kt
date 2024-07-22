/* Copyright 2024 Prokhor Kalinin

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package net.dragondelve.mandate.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dragondelve.mandate.models.AuthTokenDto
import net.dragondelve.mandate.models.LoginDto
import net.dragondelve.mandate.models.PermissionTypeDto
import net.dragondelve.mandate.models.observable.PermissionType
import net.dragondelve.mandate.util.Report
import net.dragondelve.mandate.util.readFileFromResources

object RestClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
    private var token: String = ""
    var connectionUrl: String = ""

    suspend fun makeAuthRequest(credentials: LoginDto): Boolean {
        Report.main.info("Making an Authentication Request")
        val response = client.post("$connectionUrl/auth/login") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(credentials)
        }

        Report.main.info(response.status.value)
        val body: AuthTokenDto  = response.body()

        val token = body.access_token
        Report.main.info("Token Received: $token")
        if (token != "") {
            this.token = token
            return true
        }

        return false
    }

    suspend fun loadTypes(): ObservableList<PermissionType> {
        val json = Json { ignoreUnknownKeys = true }
        val response = json.decodeFromString<Array<PermissionTypeDto>>(readFileFromResources("permissionTypeMocks.json"))
        val observable = FXCollections.observableArrayList(
            *(response.map { PermissionType(it) }.toTypedArray())
        )

        return observable
    }

    suspend fun saveType(type: PermissionType) {
        val dto = type.toDto();
        val json = Json { ignoreUnknownKeys = true }
        println(json.encodeToString(dto))
    }
}