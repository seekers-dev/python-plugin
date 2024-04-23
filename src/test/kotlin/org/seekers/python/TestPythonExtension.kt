/*
 * Copyright (C) 2024  Seekers Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.seekers.python

import org.ini4j.Ini
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.seekers.plugin.LanguageLoader

class TestPythonExtension {
    @Test
    fun testSetup() {
        Assertions.assertThrows(NullPointerException::class.java) {
            PythonExtension().setup(null)
        }
        Assertions.assertDoesNotThrow {
            val config = Ini(javaClass.getResourceAsStream("config.ini"))
            PythonExtension().setup(config["python-plugin"])
        }
    }

    @Test
    fun testAddLanguageLoaders() {
        Assertions.assertDoesNotThrow {
            val loaders = ArrayList<LanguageLoader>()
            PythonExtension().addLanguageLoaders(loaders)
            Assertions.assertFalse(loaders.isEmpty())
        }
    }
}
