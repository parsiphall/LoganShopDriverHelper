package com.parsiphal.loganshopdriverhelper.data

enum class Cars(val i: Int, val id: String) { Largus(0,"Largus"), Sandero(1,"Sandero"), XRay(2,"X-Ray") }

enum class WorkType(val i: Int) { Delivery(0), Move(1), Task(2), Expense(3), Other(4), Salary(5) }

enum class SalaryType(val i: Int) { Prepay(0), Holiday(1), Extra(2), Penalty(3), Quality(4) }

enum class DeliveryType(val i: Int) { Logan(0), Vesta(1) }

enum class PayType(val i: Int) { Cash(0), Card(1) }

enum class Shops(val i: Int) { Other(-1), Zhukova(0), Kulturi(1), Sedova(2), Himikov(3), Planernaya(4), Veteranov(5) }

enum class Expenses(val i: Int) { Fuel(0), Wash(1), Other(2) }

enum class Districts(val ruName: String) {North("Север"), Center("Центр"), South("Юг")}

