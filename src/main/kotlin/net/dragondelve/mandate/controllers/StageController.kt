package net.dragondelve.mandate.controllers

import javafx.stage.Stage

interface StageController {
    fun passStage(stage: Stage): StageController
}