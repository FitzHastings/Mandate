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

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.stage.Stage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.dragondelve.mandate.client.RestClient
import net.dragondelve.mandate.models.observable.Permission
import net.dragondelve.mandate.util.Report

class RoleFormController : StageController {
    @FXML
    lateinit var updateTypeColumn: TableColumn<Permission, Boolean>

    @FXML
    lateinit var cancelButton: Button

    @FXML
    lateinit var createTypeColumn: TableColumn<Permission, Boolean>

    @FXML
    lateinit var permissionsTable: TableView<Permission>

    @FXML
    lateinit var permissionTypeNameColumn: TableColumn<Permission, String>

    @FXML
    lateinit var deleteTypeColumn: TableColumn<Permission, Boolean>

    @FXML
    lateinit var confirmButton: Button

    @FXML
    lateinit var roleTitleTextField: TextField

    @FXML
    lateinit var roleDescriptionTextField: TextField

    @FXML
    lateinit var readTypeColumn: TableColumn<Permission, Boolean>

    private lateinit var stage: Stage

    private val permissions = FXCollections.observableArrayList<Permission>()

    @FXML
    private fun initialize() {
        Report.main.info("Role Form Window Initialization")

        Report.main.info("Initializing Actions")
        initializeActions()

        Report.main.info("Initializing Permissions Table")
        initializeTable()

        Report.main.info("Loading Data")
        loadData()

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

    private fun initializeTable() {
        permissionsTable.items = permissions

        permissionsTable.editableProperty().set(true)

        permissionTypeNameColumn.setCellValueFactory { it.value.type.typeNameProperty }

        createTypeColumn.cellFactory = CheckBoxTableCell.forTableColumn(createTypeColumn)
        createTypeColumn.editableProperty().set(true)
        createTypeColumn.setCellValueFactory { it.value.createProperty }

        readTypeColumn.cellFactory = CheckBoxTableCell.forTableColumn(readTypeColumn)
        readTypeColumn.editableProperty().set(true)
        readTypeColumn.setCellValueFactory { it.value.readProperty }

        updateTypeColumn.cellFactory = CheckBoxTableCell.forTableColumn(updateTypeColumn)
        updateTypeColumn.editableProperty().set(true)
        updateTypeColumn.setCellValueFactory { it.value.updateProperty }

        deleteTypeColumn.cellFactory = CheckBoxTableCell.forTableColumn(deleteTypeColumn)
        deleteTypeColumn.editableProperty().set(true)
        deleteTypeColumn.setCellValueFactory { it.value.deleteProperty }
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = RestClient.loadStubPermissions()
            permissions.addAll(result)
            Report.main.info("Data Loaded")
        }
    }
}