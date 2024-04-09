package com.example.findmyevent;
import android.os.AsyncTask
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection;
import java.net.URL;

class getUserData(private val listener : onDataFetchedListener):
    AsyncTask<Void, Void, List<userData>>(){

        interface onDataFetchedListener{
            fun onDataFetched(data: userData)
        }

    private val mongoDBApiUrl = "https://us-west-2.aws.data.mongodb-api.com/app/application-0-altha/endpoint/getUsers"
    override fun doInBackground(vararg params: Void?): List<userData> {
        try{
            var url: URL = URL(mongoDBApiUrl)
            var urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            var inputStreamReader = InputStreamReader(urlConnection.inputStream)
            var bufferedReader = BufferedReader(inputStreamReader)

            var stringBuilder = StringBuilder()
            var line: String?
            while(bufferedReader.readLine().also { line = it } != null){
                stringBuilder.append(line)
            }

            val JsonArray = JSONArray(stringBuilder.toString())
            val dataList = ArrayList<userData>()

            for(i in 0 until JsonArray.length()){
                val JsonObject = JsonArray.getJSONObject(i)
                val user_name = JsonObject.getString("userName")
                val email = JsonObject.getString("email")
                val password = JsonObject.getString("password")

                dataList.add(userData(user_name, email, password))
            }
            return dataList;
        }catch (e: Exception){
            e.printStackTrace()
        }
        return ArrayList()
    }


}
