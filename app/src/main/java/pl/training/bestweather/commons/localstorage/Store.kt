package pl.training.bestweather.commons.localstorage

interface Store {

    fun set(key: String, value: String)

    fun get(key: String, defaultValue: String = ""): String

}