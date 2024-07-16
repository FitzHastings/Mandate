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
import io.ktor.client.request.*
import io.ktor.http.*
import net.dragondelve.mandate.util.Report

object RestClient {
    private val client = HttpClient {

    }
    private var token: String = ""
    var connectionUrl: String = ""

    suspend fun makeAuthRequest(email: String, password: String): Boolean {
        val response = client.post {
            url("${connectionUrl}/auth/login")
        }
        Report.main.info(response)
        return response.status.isSuccess()
    }
}