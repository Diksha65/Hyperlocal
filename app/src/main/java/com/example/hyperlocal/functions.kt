package com.example.hyperlocal

import com.example.hyperlocal.extensions.Firebase.auth
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class LoopingAtomicInteger(private val start : Int = 100, private val end : Int = 999) {
    private var atomicInteger = AtomicInteger(start)
    fun nextInt() : Int {
        atomicInteger.compareAndSet(end, start)
        return atomicInteger.incrementAndGet()
    }
}

fun timeStamp() = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())

val loopingAtomicInteger = LoopingAtomicInteger(0, 10000)

fun uniqueName() = "${timeStamp()}_${auth.currentUser?.uid.hashCode()}_${loopingAtomicInteger.nextInt()}.jpg"