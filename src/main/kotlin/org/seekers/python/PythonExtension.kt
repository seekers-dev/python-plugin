package org.seekers.python

import org.pf4j.Extension
import org.seekers.plugin.LanguageLoader
import org.seekers.plugin.SeekersExtension

@Extension
class PythonExtension: SeekersExtension {
    override fun addLanguageLoaders(loaders: MutableList<LanguageLoader>) {
        loaders.add(PythonLoader())
    }
}