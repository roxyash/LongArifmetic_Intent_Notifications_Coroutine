package com.example.longarifmetic

class Calculating{
    fun calculate(): Int {
        var n = 12
        var fact: Int = 1
        while(n>1){
            fact*=n
            n -= 2
        }
        return fact
    }
}