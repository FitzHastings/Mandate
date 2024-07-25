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

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleLongProperty
import net.dragondelve.mandate.models.CreatePermissionDto
import net.dragondelve.mandate.models.PermissionDto

class Permission(permissionDto: PermissionDto) {
    val idProperty = SimpleLongProperty(permissionDto.id)
    val type: PermissionType = PermissionType(permissionDto.type)
    val createProperty = SimpleBooleanProperty(permissionDto.create)
    val readProperty = SimpleBooleanProperty(permissionDto.read)
    val updateProperty = SimpleBooleanProperty(permissionDto.update)
    val deleteProperty = SimpleBooleanProperty(permissionDto.delete)

    fun toDto(): PermissionDto {
        return PermissionDto().apply {
            id = idProperty.get()
            create = createProperty.get()
            read = readProperty.get()
            update = updateProperty.get()
            delete = deleteProperty.get()
        }
    }

    fun toCreateDto(): CreatePermissionDto {
        return CreatePermissionDto().apply {
            create = createProperty.get()
            read = readProperty.get()
            update = updateProperty.get()
            delete = deleteProperty.get()
            type_id = type.idProperty.get()
        }
    }
}