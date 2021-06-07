package com.gmail.hitoridevelop.nothingtodo.api


data class BoredApiResponse(val activity: String?, val accessibility: Double?,
    val type: String?, val participants: Int?, val price: Double?, val key: Int?, val link: String?) {

    override fun toString(): String = "\nactivity:  $activity" +
            "\naccessibility: $accessibility" +
            "\ntype: $type" +
            "\nparticipants: $participants" +
            "\nprice: $price" +
            "\nkey: $key" +
            "\nlink: $link"

}


