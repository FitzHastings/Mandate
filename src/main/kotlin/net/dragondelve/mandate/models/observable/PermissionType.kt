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
import net.dragondelve.mandate.models.CreatePermissionTypeDto
import net.dragondelve.mandate.models.PermissionTypeDto

class PermissionType(dto: PermissionTypeDto) {
    val idProperty = SimpleLongProperty(dto.id)
    val typeNameProperty = SimpleStringProperty(dto.typeName)
    val systemNameProperty = SimpleStringProperty(dto.systemName)
    val descriptionProperty = SimpleStringProperty(dto.description)

    fun toDto(): PermissionTypeDto {
        return PermissionTypeDto().apply {
            id = idProperty.get()
            typeName = typeNameProperty.get()
            systemName = systemNameProperty.get()
            description = descriptionProperty.get()
        }
    }

    fun toCreateDto(): CreatePermissionTypeDto {
        return CreatePermissionTypeDto().apply {
            typeName = typeNameProperty.get()
            systemName = systemNameProperty.get()
            description = descriptionProperty.get()
        }
    }
}
