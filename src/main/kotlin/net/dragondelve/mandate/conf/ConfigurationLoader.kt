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

package net.dragondelve.mandate.conf

import com.google.gson.Gson
import net.dragondelve.mandate.util.Report
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.charset.Charset

class ConfigurationLoader {
    private val gson = Gson()

    @Throws(IOException::class)
    fun loadConfiguration(fileName: String) {
        Report.main.info("Loading Configuration File: [ $fileName ]")
        try {
            val configFileContent = loadFile(fileName)
            Report.main.debug("Config: $configFileContent")

            Conf.config = gson.fromJson(configFileContent, Config::class.java)
            Report.main.info("Successfully Loaded Configuration File: [ $fileName ]")
        } catch (exception: IOException) {
            Report.main.warn("Configuration File Missing or Corrupt, Regenerating")
            provideSampleConfig(fileName)
            Report.main.info("Successfully regenerated a sample config file: [ $fileName ]")
            throw IOException("Invalid Configuration File")
        }
    }

    @Throws(IOException::class)
    private fun loadFile(fileName: String): String {
        val file = File(fileName)
        if (!file.exists() || file.isDirectory)
            throw FileNotFoundException("File not found: $fileName")

        return file.readText(Charset.forName("UTF-8"))
    }

    private fun provideSampleConfig(fileName: String) {
        val sample = generateSampleConfig()
        val sampleJson = gson.toJson(sample)

        saveFile(fileName, sampleJson)
    }

    private fun saveFile(fileName: String, content: String) {
        val file = File(fileName)
        file.writeText(content)
    }

    private fun generateSampleConfig(): Config {
        val connection = Connection("Sample", "https://dragondelve.net/api")
        val connections = ArrayList<Connection>()
        connections.add(connection)
        return Config(connections)
    }
}

object Conf {
    var config: Config? = null
}