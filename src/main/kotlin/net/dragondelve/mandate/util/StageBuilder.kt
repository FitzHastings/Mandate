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

package net.dragondelve.mandate.util

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import net.dragondelve.mandate.MandateApplication

class StageData {
    var controller: Any? = null
    var width: Double? = null
    var height: Double? = null
    var parentStage: Stage? = null
}

class StageBuilder(private val resource: String, private val title: String) {
    private val data = StageData()

    fun controller(value: Any) = apply { data.controller = value }
    fun width(value: Double) = apply { data.width = value }
    fun height(value: Double) = apply { data.height = value }
    fun parentStage(value: Stage) = apply { data.parentStage = value }
    fun modality(value: Modality) = apply { data.modality = value }

    fun build(): Stage {
        if (data.controller == null) {
            throw IllegalStateException("Controller must be set")
        }

        val stage = Stage()

        val fxmlLoader = FXMLLoader(MandateApplication::class.java.getResource(resource))
        fxmlLoader.setController(data.controller)
        val root = fxmlLoader.load<Parent>()
        val scene = data.width?.let { width ->
            data.height?.let { height ->
                Scene(root, width, height)
            } ?: Scene(root)
        } ?: Scene(root)

        data.parentStage?.let {
            stage.initOwner(it)
            stage.initModality(data.modality)
        }

        stage.title = title
        stage.scene = scene
        return stage
    }

    private inner class StageData {
        var controller: Any? = null
        var width: Double? = null
        var height: Double? = null
        var parentStage: Stage? = null
        var modality: Modality? = Modality.NONE
    }
}