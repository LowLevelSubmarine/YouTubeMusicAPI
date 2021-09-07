package com.lowlevelsubmarine.ytma.http

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DefaultHttpInterface : HttpInterface {

    companion object {
        val INSTANCE = DefaultHttpInterface()
    }

    private fun URL.buildConnection(): HttpURLConnection {
        return this.openConnection() as HttpURLConnection
    }

    private fun HttpURLConnection.applyCookies(cookies: Map<String, String>) {
        for (cookie in cookies) {
            this.setRequestProperty(cookie.key, cookie.value)
        }
    }

    private fun InputStream.collectString(): String {
        val reader = BufferedReader(InputStreamReader(this))
        val strBuffer = StringBuffer()
        var line = reader.readLine()
        while (line != null) {
            strBuffer.append(line)
            line = reader.readLine()
        }
        reader.close()
        this.close()
        return strBuffer.toString()
    }

    private fun HttpURLConnection.writeString(string: String) {
        val dataOutStream = DataOutputStream(this.outputStream)
        dataOutStream.write(string.toByteArray())
    }

    override fun get(url: URL, cookies: Map<String, String>): String {
        val con = url.buildConnection()
        con.requestMethod = "GET"
        con.applyCookies(cookies)
        return con.inputStream.collectString()
    }

    override fun post(url: URL, cookies: Map<String, String>, content: String): String {
        val con = url.buildConnection()
        con.requestMethod = "POST"
        con.applyCookies(cookies)
        con.doOutput = true
        con.writeString(content)
        con.connect()
        return if (con.responseCode == HttpURLConnection.HTTP_OK) {
            con.inputStream
        } else {
            con.errorStream
        }.collectString()
    }

}