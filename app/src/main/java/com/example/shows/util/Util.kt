package com.example.shows.util

import org.jsoup.Jsoup

fun String.removeHtml(): String{
    return Jsoup.parse(this).text()
}