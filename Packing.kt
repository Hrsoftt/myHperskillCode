const val MASSAGE = "Sorry, a parking lot has not been created."

class ParkingSpot {
    private val freeSpots = mutableListOf<Int>()
    var sortedFreeSpots = freeSpots.sorted()
    val parkedCars = mutableListOf<String>()
    var start = false

    private fun addFreeSpot(spot: Int) {
        for (i in 1..spot) {
            freeSpots.add(i)
        }
        sortedFreeSpots = freeSpots.sorted().toMutableList()
    }

    fun create(spot: Int) {
        if (spot > 0) {
            freeSpots.clear()
            parkedCars.clear()

            addFreeSpot(spot)
            println("Created a parking lot with $spot spots.")
            start = true
        }
    }

    fun parkCar(color: String, registration: String) {
        if (start) {
            if (parkedCars.size < sortedFreeSpots.size) {
                var num = 0
                val count = freeSpots.sorted()
                parkedCars.add("${count[0]} $registration $color")
                freeSpots.remove(count[0])

                for (i in 1..parkedCars.size) {
                    num = i
                }
                println("$color car parked in spot $num.")
            } else {
                println("parking lot is full.")
            }
        } else {
            println(MASSAGE)
        }
    }

    fun leaveSpot(spot: Int) {
        if (parkedCars.isNotEmpty()) {
            freeSpots.add(spot)
            parkedCars.removeAt(spot - 1)
            println("Spot $spot is free.")
        } else {
            println(MASSAGE)
        }
    }

    fun status() {
        if (start) {
            if (parkedCars.isNotEmpty()) {
                println(parkedCars.joinToString("\n"))
            } else {
                println("Parking lot is empty.")
            }
        } else {
            println(MASSAGE)
        }
    }

    fun regByColor(color: String) {
        val newColor = color.lowercase()
        val regColor = mutableListOf<String>()
        if (parkedCars.isEmpty()) {
            println(MASSAGE)
        } else {
            for (i in parkedCars.indices) {
                if (parkedCars[i].split(" ")[2].lowercase() == newColor) {
                    regColor.add(parkedCars[i].split(" ")[1])
                }
            }
            if (regColor.isEmpty()) println("No cars with color $color were found.") else println(
                regColor.joinToString(
                    ", "
                )
            )
            regColor.clear()
        }
    }

    fun spotByColor(color: String) {
        val newColor = color.lowercase()
        val spotColor = mutableListOf<String>()
        if (parkedCars.isEmpty()) {
            println(MASSAGE)
        } else {
            for (i in parkedCars.indices) {
                if (parkedCars[i].split(" ")[2].lowercase() == newColor) {
                    spotColor.add(parkedCars[i].split(" ")[0])
                }
            }
            if (spotColor.isEmpty()) println("No cars with color $color were found.") else println(
                spotColor.joinToString(
                    ", "
                )
            )
            spotColor.clear()
        }
    }

    fun spotByReg(registration: String) {
        val spotReg = mutableListOf<String>()
        if (parkedCars.isEmpty()) {
            println(MASSAGE)
        } else {
            for (i in parkedCars.indices) {
                if (parkedCars[i].split(" ")[1] == registration) {
                    val num = i + 1
                    spotReg.add(num.toString())
                }
            }
            if (spotReg.isEmpty()) println("No cars with registration number $registration were found.") else println(
                spotReg.joinToString(
                    ", "
                )
            )
        }
    }
}

fun main() {


    val parkingSpot = ParkingSpot()
    val book = "13"
    val list = listOf("A♦", "78","4♣")
    val list2 = mutableListOf<String>()
    list2.add(readln())
    println(list.first().contains(list2[0].last()))

//
//    while (true) {
//        val input = readln().split(" ")
//
//        when (input[0]) {
//            "create" -> {
//                parkingSpot.create(input[1].toInt())
//            }
//            "park" -> {
//                parkingSpot.parkCar(input[2], input[1])
//            }
//            "leave" -> {
//                parkingSpot.leaveSpot(input[1].toInt())
//            }
//            "status" -> {
//                parkingSpot.status()
//            }
//            "reg_by_color" -> {
//                parkingSpot.regByColor(input[1])
//            }
//            "spot_by_color" -> {
//                parkingSpot.spotByColor(input[1])
//            }
//            "spot_by_reg" -> {
//                parkingSpot.spotByReg(input[1])
//            }
//            "exit" -> {
//                break
//            }
//        }
//
//    }
}