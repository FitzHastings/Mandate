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
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldTableCell
import javafx.util.StringConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.dragondelve.mandate.client.RestClient
import net.dragondelve.mandate.models.PermissionTypeDto
import net.dragondelve.mandate.models.observable.PermissionType
import net.dragondelve.mandate.util.Report

class PermissionTypeController {
    @FXML
    private lateinit var addTypeButton: Button

    @FXML
    private lateinit var descriptionColumn: TableColumn<PermissionType, String>

    @FXML
    private lateinit var idColumn: TableColumn<PermissionType, Long>

    @FXML
    private lateinit var removeTypeButton: Button

    @FXML
    private lateinit var systemNameColumn: TableColumn<PermissionType, String>

    @FXML
    private lateinit var typeNameColumn: TableColumn<PermissionType, String>

    @FXML
    private lateinit var permissionTypeTable: TableView<PermissionType>

    private var permissionTypes: ObservableList<PermissionType> = FXCollections.observableArrayList()

    @FXML
    private fun initialize() {
        Report.main.info("Permission Type Window Initialization")

        Report.main.info("Initializing Actions")
        initializeActions()

        Report.main.info("Mapping the Table")
        initializeTable()

        Report.main.info("Loading Data")
        loadData()

        Report.main.info("Permission Type Window Initialized")
    }

    private fun initializeActions() {
        addTypeButton.setOnAction {
            permissionTypes.add(PermissionType(PermissionTypeDto()))
        }
    }

    private fun initializeTable() {
        val stringConverter = object : StringConverter<Long>() {
            override fun toString(obj: Long?): String {
                return obj.toString()
            }

            override fun fromString(str: String): Long {
                return str.toLong()
            }
        }

        idColumn.cellFactory = TextFieldTableCell.forTableColumn(stringConverter)
        idColumn.cellValueFactory = PropertyValueFactory("id")

        typeNameColumn.cellFactory = TextFieldTableCell.forTableColumn()
        typeNameColumn.cellValueFactory = PropertyValueFactory("typeName")

        systemNameColumn.cellFactory = TextFieldTableCell.forTableColumn()
        systemNameColumn.cellValueFactory = PropertyValueFactory("systemName")

        descriptionColumn.cellFactory = TextFieldTableCell.forTableColumn()
        descriptionColumn.cellValueFactory = PropertyValueFactory("description")

        permissionTypeTable.items = permissionTypes
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = RestClient.loadTypes()
            permissionTypes.addAll(result)
            Report.main.info("Data Loaded")
        }
    }

}
