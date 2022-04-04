package com.example.proyectoparcial2

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.lang.Exception

class ParseApp {
    val apps = ArrayList<Entry>()

    fun parse(xmlData: String): Boolean{
        var status = true
        var tagInEntry = false
        var textValue = ""

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val pullParser = factory.newPullParser()

            pullParser.setInput(xmlData.reader())
            var eventType = pullParser.eventType
            var currentRecord = Entry()
            while (eventType != XmlPullParser.END_DOCUMENT){
                val tagName = pullParser.name?.lowercase()
                when(eventType){
                    XmlPullParser.START_TAG ->{
                        if(tagName == "item"){
                            tagInEntry = true
                        }
                    }
                    XmlPullParser.TEXT -> {
                        textValue = pullParser.text
                    }
                    XmlPullParser.END_TAG ->{
                        if(tagInEntry){
                            when(tagName){
                                "item" -> {
                                    apps.add(currentRecord)
                                    tagInEntry = false
                                    currentRecord = Entry()
                                }
                                "title" -> currentRecord.title = textValue
                                "link" -> currentRecord.link = textValue
                                "description" -> currentRecord.description = textValue
                                "author" -> currentRecord.author = textValue
                                "pubDate" -> currentRecord.pubDate = textValue
                            }
                        }
                    }
                }
                eventType = pullParser.next()
            }
        } catch (e: Exception){
            e.printStackTrace()
            status = false
        }

        return true
    }
}