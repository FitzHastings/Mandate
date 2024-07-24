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
import javafx.scene.control.*
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.scene.layout.BorderPane
import javafx.stage.Modality
import javafx.stage.Stage
import net.dragondelve.mandate.models.observable.Permission
import net.dragondelve.mandate.models.observable.Role
import net.dragondelve.mandate.util.StageBuilder

class MandateController : StageController {
    @FXML
    private lateinit var createTypeColumn: TableColumn<Permission, Boolean>

    @FXML
    private lateinit var deleteRoleButton: Button

    @FXML
    private lateinit var deleteTypeColumn: TableColumn<Permission, Boolean>

    @FXML
    private lateinit var editRoleButton: Button

    @FXML
    private lateinit var editTypesMenuItem: MenuItem

    @FXML
    private lateinit var loadLocalMenuItem: MenuItem

    @FXML
    private lateinit var permissionTypeNameColumn: TableColumn<Permission, String>

    @FXML
    private lateinit var readTypeColumn: TableColumn<Permission, Boolean>

    @FXML
    private lateinit var roleIdColumn: TableColumn<Role, Long>

    @FXML
    private lateinit var roleNameColumn: TableColumn<Role, String>

    @FXML
    private lateinit var roleTableView: TableView<Role>

    @FXML
    private lateinit var rootPane: BorderPane

    @FXML
    private lateinit var saveLocalMenuItem: MenuItem

    @FXML
    private lateinit var updateTypeColumn: TableColumn<Permission, Boolean>

    @FXML
    private lateinit var usersListView: ListView<Permission>

    @FXML
    private lateinit var addRoleButton: Button

    @FXML
    private lateinit var permissionsTable: TableView<Permission>

    private val roles = FXCollections.observableArrayList<Role>()
    private val selectedRole: Role? = null

    private lateinit var stage: Stage

    @FXML
    private fun initialize() {
        println("Initializing Main Window")

        println("Initializng Actions")
        initializeActions()

        println("Initializing Table")
        initializeTable()

        println("Initializing Permisisons Table")
        initializePermissionsTable()

        println("Main Window Initialized")
    }

    override fun passStage(stage: Stage): StageController {
        this.stage = stage
        return this
    }

    private fun initializeActions() {
        this.editTypesMenuItem.setOnAction {
            val stage = StageBuilder("permission-type.fxml", "Edit Permission Types")
                .controller(PermissionTypeController())
                .modality(Modality.WINDOW_MODAL)
                .build()
            stage.showAndWait()
        }

        this.addRoleButton.setOnAction {
            val controller = RoleFormController()
            val stage = StageBuilder("role-form.fxml", "Create Role")
                .controller(controller)
                .modality(Modality.WINDOW_MODAL)
                .build()

            controller.passStage(stage)
            stage.showAndWait()
        }
    }

    private fun initializeTable() {
        roleTableView.items = roles

        roleIdColumn.setCellValueFactory { it.value.idProperty.asObject() }
        roleNameColumn.setCellValueFactory { it.value.nameProperty }
    }

    private fun initializePermissionsTable() {
        permissionTypeNameColumn.setCellValueFactory { it.value.type.typeNameProperty }

        createTypeColumn.cellFactory = CheckBoxTableCell.forTableColumn(createTypeColumn)
        createTypeColumn.setCellValueFactory { it.value.createProperty }

        readTypeColumn.cellFactory = CheckBoxTableCell.forTableColumn(readTypeColumn)
        readTypeColumn.setCellValueFactory { it.value.readProperty }

        updateTypeColumn.cellFactory = CheckBoxTableCell.forTableColumn(updateTypeColumn)
        updateTypeColumn.setCellValueFactory { it.value.updateProperty }

        deleteTypeColumn.cellFactory = CheckBoxTableCell.forTableColumn(deleteTypeColumn)
        deleteTypeColumn.setCellValueFactory { it.value.deleteProperty }
    }
}
