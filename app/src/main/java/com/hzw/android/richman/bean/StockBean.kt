package com.hzw.android.richman.bean

/**
 * class StockBean
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/18
 */
class StockBean(var name: String) {

    var oldPrice = 100
    var newPrice = 100
    var x = 10
    var number = 0

    init {
        randomX()
    }

    constructor(name: String, number:Int) : this(name) {
        this.name = name
        this.number = number
    }


    fun change() {
        oldPrice = newPrice
        newPrice = oldPrice + isAddOrReduce() * x
        if (newPrice < 0) {
            newPrice = 0
        }

    }

    private fun isAddOrReduce():Int {
        return if (Math.random() >= 0.5) 1 else -1
    }

    fun randomX() {
        x =  (Math.random() * 10 + 1).toInt()
    }
}