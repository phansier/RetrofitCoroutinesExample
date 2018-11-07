package ru.beryukhov.retrofitcoroutinesexample

class TrainModel {
    var segments: List<Segment>? = null
    override fun toString(): String {
        return "TrainModel(segments=$segments)"
    }


}

class Segment {
    var arrival: String? = null
        get() = field?.substringAfter("T")?.substringBefore(":00+")
    var departure: String? = null
        get() = field?.substringAfter("T")?.substringBefore(":00+")
    var thread: Thread? = null
    override fun toString(): String {
        return "Segment(arrival=$arrival, departure=$departure, thread=$thread)"
    }

}

class Thread {
    var title: String? = null//from-to name
    var transport_subtype: TrSubtype? = null
    var express_type: String? = null //null if not express
    override fun toString(): String {
        return "Thread(title=$title, transport_subtype=$transport_subtype, express_type=$express_type)"
    }

}

class TrSubtype {
    var title: String? = null//standart plus or ...
    override fun toString(): String {
        return "TrSubtype(title=$title)"
    }

}
