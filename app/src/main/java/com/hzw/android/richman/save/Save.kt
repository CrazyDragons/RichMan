package com.hzw.android.richman.save

/**
 * class Save
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/10
 */
class Save : BaseSave() {

    companion object {

        val INSTANCE: Save by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Save()
        }

        private const val MAP = "map"
    }

    fun saveMap(json: String) {
        putStringData(MAP, json)
    }

    fun loadMap(): String {
        return getStringData(MAP, "")
    }

    fun clean() {
        saveMap("")
    }
}
