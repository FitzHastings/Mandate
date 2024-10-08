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

package net.dragondelve.mandate

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import net.dragondelve.mandate.conf.ConfigurationLoader
import net.dragondelve.mandate.controllers.LoginController

class MandateApplication : Application() {
    override fun start(stage: Stage) {
        ConfigurationLoader().loadConfiguration("mandate.conf.json")
        val fxmlLoader = FXMLLoader(MandateApplication::class.java.getResource("mandate-login.fxml"))
        fxmlLoader.setController(LoginController().passStage(stage))
        val scene = Scene(fxmlLoader.load())
        stage.isResizable = false
        stage.title = "Login - Mandate"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(MandateApplication::class.java)
}