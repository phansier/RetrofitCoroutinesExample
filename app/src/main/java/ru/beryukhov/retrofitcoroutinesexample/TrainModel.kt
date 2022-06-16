package ru.beryukhov.retrofitcoroutinesexample

data class TrainModel(
    val segments: List<Segment>? = null
)

data class Segment(
    private val arrival: String? = null,

    private val departure: String? = null,

    val thread: Thread? = null
) {
    val arrivalFormatted get() = arrival?.substringAfter("T")?.substringBefore(":00+").orEmpty()
    val departureFormatted get() = departure?.substringAfter("T")?.substringBefore(":00+").orEmpty()
}

data class Thread(
    val title: String? = null,//from-to name
    val transport_subtype: TrSubtype? = null,
    val express_type: String? = null //null if not express

)

data class TrSubtype(
    val title: String? = null//standart plus or ...
)
