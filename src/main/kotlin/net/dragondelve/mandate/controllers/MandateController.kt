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
import javafx.scene.layout.BorderPane
import javafx.stage.Modality
import net.dragondelve.mandate.util.StageBuilder

class MandateController {
    @FXML
    private lateinit var createTypeColumn: TableColumn<Any, Any>

    @FXML
    private lateinit var deleteRoleButton: Button

    @FXML
    private lateinit var deleteTypeColumn: TableColumn<Any, Any>

    @FXML
    private lateinit var editRoleButton: Button

    @FXML
    private lateinit var editTypesMenuItem: MenuItem

    @FXML
    private lateinit var loadLocalMenuItem: MenuItem

    @FXML
    private lateinit var permissionTypeNameColumn: TableColumn<Any, Any>

    @FXML
    private lateinit var readTypeColumn: TableColumn<Any, Any>

    @FXML
    private lateinit var roleIdColumn: TableColumn<Any, Any>

    @FXML
    private lateinit var roleNameColumn: TableColumn<Any, Any>

    @FXML
    private lateinit var roleTableView: TableView<Any>

    @FXML
    private lateinit var rootPane: BorderPane

    @FXML
    private lateinit var saveLocalMenuItem: MenuItem

    @FXML
    private lateinit var updateTypeColumn: TableColumn<Any, Any>

    @FXML
    private lateinit var usersListView: ListView<Any>

    @FXML
    private fun initialize() {
        println("Initialization Complete")
        this.editTypesMenuItem.setOnAction {
            val stage = StageBuilder("permission-type.fxml", "Edit Permission Types")
                .controller(PermissionTypeController())
                .modality(Modality.WINDOW_MODAL)
                .build()
            stage.showAndWait()
        }
    }
}
