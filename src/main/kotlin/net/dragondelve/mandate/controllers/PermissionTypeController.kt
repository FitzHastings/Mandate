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
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.TextFieldTableCell
import javafx.stage.Stage
import javafx.util.StringConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.dragondelve.mandate.client.RestClient
import net.dragondelve.mandate.models.PermissionTypeDto
import net.dragondelve.mandate.models.observable.PermissionType
import net.dragondelve.mandate.util.Report

class PermissionTypeController: StageController {
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

    @FXML
    private lateinit var actionColumn: TableColumn<PermissionType, String>

    private var permissionTypes: ObservableList<PermissionType> = FXCollections.observableArrayList()

    private lateinit var stage: Stage

    @FXML
    private fun initialize() {
        Report.main.info("Permission Type Window Initialization")

        initializeActions()

        Report.main.info("Initializing Actions")
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

        removeTypeButton.setOnAction {
            removeTypeAction()
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
        idColumn.setCellValueFactory { it.value.idProperty.asObject() }

        typeNameColumn.cellFactory = TextFieldTableCell.forTableColumn()
        typeNameColumn.setCellValueFactory { it.value.typeNameProperty }

        systemNameColumn.cellFactory = TextFieldTableCell.forTableColumn()
        systemNameColumn.setCellValueFactory { it.value.systemNameProperty }

        descriptionColumn.cellFactory = TextFieldTableCell.forTableColumn()
        descriptionColumn.setCellValueFactory { it.value.descriptionProperty }

        actionColumn.setCellFactory { _: TableColumn<PermissionType, String>? ->
            object : TableCell<PermissionType, String>() {
                private val saveButton = Button("Save")

                init {
                    saveButton.setOnAction {
                        if (permissionTypeTable.items.get(index) != null) {
                            CoroutineScope(Dispatchers.Main).launch {
                                RestClient.saveType(permissionTypeTable.items.get(index))
                            }
                        }
                    }
                }

                override fun updateItem(item: String?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (empty) graphic = null
                    else graphic = saveButton
                }
            }
        }
        permissionTypeTable.items = permissionTypes
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = RestClient.loadTypes()
            permissionTypes.addAll(result)
            Report.main.info("Data Loaded")
        }
    }

    private fun removeTypeAction() {
        val selectedIndex = permissionTypeTable.selectionModel.selectedIndex
        removeTypeButton.isDisable = true

        if (selectedIndex != -1) {
            val selectedType = permissionTypeTable.items[selectedIndex]

            CoroutineScope(Dispatchers.IO).launch {
                // Call deleteType and store result
                val result = RestClient.deleteType(selectedType.idProperty.get())

                withContext(Dispatchers.Main) {
                    if (result) {
                        permissionTypeTable.items.removeAt(selectedIndex)

                        val newSelectedIndex =
                            if (selectedIndex == permissionTypeTable.items.size)
                                selectedIndex - 1
                            else
                                selectedIndex

                        if (newSelectedIndex != -1)
                            permissionTypeTable.selectionModel.select(newSelectedIndex)
                    }

                    removeTypeButton.isDisable = false
                }
            }
        }
    }

    override fun passStage(stage: Stage): StageController {
        this.stage = stage
        return this
    }
}
