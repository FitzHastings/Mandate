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

package net.dragondelve.mandate.controllers

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.dragondelve.mandate.client.RestClient
import net.dragondelve.mandate.conf.Conf
import net.dragondelve.mandate.conf.Connection
import net.dragondelve.mandate.util.Report
import net.dragondelve.mandate.util.StageBuilder

class LoginController: StageController {
    @FXML
    private lateinit var emailTextField: TextField

    @FXML
    private lateinit var environmentChoiceBox: ChoiceBox<Connection>

    @FXML
    private lateinit var loginButton: Button

    @FXML
    private lateinit var passwordField: PasswordField

    private lateinit var stage: Stage

    @FXML
    private fun initialize() {
        Report.main.info("Initializing Login Screen")

        Report.main.info("Initializing Environment Selector")
        initializeConfigChoiceBox()

        Report.main.info("Initializing Actions")
        initializeActions()

        Report.main.info("Initialization Complete")
    }

    private fun initializeConfigChoiceBox() {
        Conf.config?.let {
            environmentChoiceBox.items.addAll(it.connections)
            environmentChoiceBox.selectionModel.select(0)
        }
    }

    private fun initializeActions() {
        val myStage = this.stage
        loginButton.setOnAction {
            CoroutineScope(Dispatchers.Main).launch {
                RestClient.connectionUrl = environmentChoiceBox.selectionModel.selectedItem.url
                val result = RestClient.makeAuthRequest(emailTextField.text, passwordField.text)
                Report.main.info("Trying to authorize:")
                if (result) {
                    val stage = StageBuilder("mandate-main.fxml", "Mandate v0.1.0")
                        .controller(PermissionTypeController())
                        .build()

                    stage.show()
                    myStage.close()
                }
            }
        }
    }

    override fun passStage(stage: Stage): StageController {
        this.stage = stage
        return this
    }
}