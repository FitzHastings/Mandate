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
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import net.dragondelve.mandate.models.observable.Role
import net.dragondelve.mandate.models.observable.User
import net.dragondelve.mandate.util.Report

class AssignUsersController {
    @FXML
    private lateinit var assignedEmailColumn: TableColumn<User, String>

    @FXML
    private lateinit var assignedFirstNameColumn: TableColumn<User, String>

    @FXML
    private lateinit var assignedLastNameColumn: TableColumn<User, String>

    @FXML
    private lateinit var assignedOccupationColumn: TableColumn<User, String>

    @FXML
    private lateinit var assignedUsersTableView: TableView<User>

    @FXML
    private lateinit var rolesTable: TableView<Role>

    @FXML
    private lateinit var unassignedEmailColumn: TableColumn<User, String>

    @FXML
    private lateinit var unassignedFirstNameColumn: TableColumn<User, String>

    @FXML
    private lateinit var unassignedLastNameColumn: TableColumn<User, String>

    @FXML
    private lateinit var unassignedOccupationColumn: TableColumn<User, String>

    @FXML
    private lateinit var unassignedUsersTableView: TableView<User>

    @FXML
    private fun initialize() {
        Report.main.info("Assign Users Window Initialization")
    }
}
