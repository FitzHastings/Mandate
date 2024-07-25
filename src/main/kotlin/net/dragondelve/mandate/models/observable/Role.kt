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

package net.dragondelve.mandate.models.observable

import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import net.dragondelve.mandate.models.CreateRoleDto
import net.dragondelve.mandate.models.RoleDto

class Role(roleDto: RoleDto) {
    val idProperty = SimpleLongProperty(roleDto.id)
    val nameProperty = SimpleStringProperty(roleDto.name)
    val descriptionProperty = SimpleStringProperty(roleDto.description)
    val permissions: ObservableList<Permission> = FXCollections.observableArrayList(
        roleDto.permissions.map { permissionDto ->
            Permission(permissionDto)
        }
    )

    fun toDto(): RoleDto {
        val what = permissions
        return RoleDto().apply {
            id = idProperty.get()
            name = nameProperty.get()
            description = descriptionProperty.get()
            permissions = what.map { it.toDto() }
        }
    }

    fun toCreateDto(): CreateRoleDto {
        val what = permissions
        return CreateRoleDto().apply {
            name = nameProperty.get()
            description = descriptionProperty.get()
            permissions = what.map { it.toCreateDto() }
        }
    }
}