package dev.hienlt.graph

data class Mien(
    var name: String? = null,
    var tinh: List<Tinh>? = null
)

data class Tinh(
    var name: String? = null,
    var huyen: List<Huyen>? = null
)

data class Huyen(
    var name: String? = null
)