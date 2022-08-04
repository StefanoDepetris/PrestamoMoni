package com.example.prestamomoni.data.model

enum class LoanStatus(index:Int) {
    APROVED(0),
    REJECTED(1),
    ERROR(2);

    companion object {
        fun create(x: Int?): LoanStatus {
            return when (x) {
                0 -> APROVED
                1 -> REJECTED
                2 -> ERROR
                else -> throw IllegalStateException()
            }
        }
    }

}