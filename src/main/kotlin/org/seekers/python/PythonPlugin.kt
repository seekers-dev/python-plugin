package org.seekers.python

import org.pf4j.Plugin

class PythonPlugin: Plugin() {
    companion object {
        val settings = PythonSettings()
        val installer = PythonInstaller(settings)
    }

    override fun start() {
        installer.workflow()
    }
}