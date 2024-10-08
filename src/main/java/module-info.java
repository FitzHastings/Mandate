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

module net.dragondelve.mandate {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires org.apache.logging.log4j;
    requires io.ktor.client.core;
    requires io.ktor.http;
    requires kotlinx.coroutines.core;
    requires io.ktor.client.content.negotiation;
    requires io.ktor.client.cio;
    requires kotlinx.serialization.core;
    requires kotlinx.serialization.json;
    requires io.ktor.serialization.kotlinx.json;

    opens net.dragondelve.mandate.controllers to javafx.fxml;
    opens net.dragondelve.mandate.models to kotlinx.serialization.core;
    opens net.dragondelve.mandate.models.observable to javafx.base;
    exports net.dragondelve.mandate;
}