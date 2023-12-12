package pl.training.bestweather.commons.localstorage

interface PropertiesMap {

    fun set(key: String, value: String)

    fun get(key: String, defaultValue: String = ""): String

}