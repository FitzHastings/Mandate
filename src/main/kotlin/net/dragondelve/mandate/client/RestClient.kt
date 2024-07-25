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
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import kotlinx.serialization.json.Json
import net.dragondelve.mandate.models.*
import net.dragondelve.mandate.models.observable.Permission
import net.dragondelve.mandate.models.observable.PermissionType
import net.dragondelve.mandate.models.observable.Role
import net.dragondelve.mandate.util.Report

object RestClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
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
        val body: AuthTokenDto = response.body()

        val token = body.access_token
        Report.main.info("Token Received: $token")
        if (token != "") {
            this.token = token
            return true
        }

        return false
    }

    suspend fun loadStubPermissions(): ObservableList<Permission> {
        val response = loadTypes()
        val observable = FXCollections.observableArrayList<Permission>()
        for (type in response) {
            val permissionDto = PermissionDto()
            permissionDto.create = false
            permissionDto.read = false
            permissionDto.update = false
            permissionDto.delete = false
            permissionDto.type = type.toDto()
            observable.add(Permission(permissionDto))
        }

        return observable
    }

    suspend fun loadTypes(): ObservableList<PermissionType> {
        val response = client.get("$connectionUrl/permission-type/") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }

        Report.main.info(response.status.value)
        val body: List<PermissionTypeDto> = response.body()

        val observable = FXCollections.observableArrayList(
            *(body.map { PermissionType(it) }.toTypedArray())
        )

        return observable
    }

    suspend fun saveType(type: PermissionType): PermissionType {
        val response: HttpResponse = if (type.idProperty.get() == -1L) {
            val dto = type.toCreateDto()
            client.post("$connectionUrl/permission-type/") {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(dto)
            }
        } else {
            val dto = type.toDto()
            client.patch("$connectionUrl/permission-type/${type.idProperty.get()}") {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(dto)
            }
        }

        Report.main.info(response.status.value)
        val body: PermissionTypeDto = response.body()
        return PermissionType(body)
    }

    suspend fun deleteType(id: Long): Boolean {
        if (id == -1L) return true
        val response = client.delete("$connectionUrl/permission-type/$id") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }
        return response.status == HttpStatusCode.OK
    }

    suspend fun updateRole(role: Role): Role {
        val response: HttpResponse = if (role.idProperty.get() == -1L) {
            val dto = role.toCreateDto()
            client.post("$connectionUrl/role/") {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(dto)
            }
        } else {
            val dto = role.toDto()
            client.patch("$connectionUrl/role/${role.idProperty.get()}") {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(dto)
            }
        }

        Report.main.info(response.status.value)
        val body: RoleDto = response.body()
        return Role(body)
    }
}