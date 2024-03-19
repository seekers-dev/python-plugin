package org.seekers.python

import org.seekers.plugin.LanguageLoader
import org.seekers.plugin.SeekersExtension

class PythonExtension: SeekersExtension {
    override fun addLanguageLoaders(loaders: MutableList<LanguageLoader>) {
        loaders.add(PythonLoader())
    }
}