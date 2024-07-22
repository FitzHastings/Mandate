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
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.stage.Stage
import net.dragondelve.mandate.util.Report

class RoleFormController: StageController {
    @FXML
    lateinit var updateTypeColumn: TableColumn<*, *>

    @FXML
    lateinit var cancelButton: Button

    @FXML
    lateinit var createTypeColumn: TableColumn<*, *>

    @FXML
    lateinit var permissionsTable: TableView<*>

    @FXML
    lateinit var permissionTypeNameColumn: TableColumn<*, *>

    @FXML
    lateinit var deleteTypeColumn: TableColumn<*, *>

    @FXML
    lateinit var confirmButton: Button

    @FXML
    lateinit var roleTitleTextField: TextField

    @FXML
    lateinit var roleDescriptionTextField: TextField

    @FXML
    lateinit var readTypeColumn: TableColumn<*, *>

    private lateinit var stage: Stage

    @FXML
    private fun initialize() {
        Report.main.info("Role Form Window Initialization")

        Report.main.info("Initializing Actions")
        initializeActions()

        Report.main.info("Role Form Window Initialized")
    }

    override fun passStage(stage: Stage): StageController {
        this.stage = stage
        return this
    }

    private fun initializeActions() {
        cancelButton.setOnAction {
            stage.close()
        }

        confirmButton.setOnAction {
            stage.close()
        }
    }
}