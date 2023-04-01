package com.example.chooseownapi_codepathunit5project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    var imgUrl=""
    var pokemon="charizard"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("petImageURL", "pet image URL set")
        var button=findViewById<Button>(R.id.button)
        var image=findViewById<ImageView>(R.id.pic)

        getNextImage(button,image)
    }




    private fun getNextImage(button: Button, imageView: ImageView) {
        button.setOnClickListener {
            getPokemon()

            Glide.with(this)
                .load(imgUrl)
                .fitCenter()
                .into(imageView)
        }
    }

    private fun getPokemon() {
        var search:EditText=findViewById<EditText>(R.id.search)
        var species:TextView=findViewById<TextView>(R.id.species)
        var weight:TextView=findViewById<TextView>(R.id.weight)
        val client = AsyncHttpClient()
        var speciesString=""
        var weightString=""
        pokemon=search.getText().toString()

        client["https://pokeapi.co/api/v2/pokemon/${pokemon}", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                // Access a JSON array response with `json.jsonArray`
                //Log.d("DEBUG ARRAY", json.jsonArray.toString())
                // Access a JSON object response with `json.jsonObject`

                Log.d("Dog", "response successful")
                Log.d("DEBUG OBJECT", json.jsonObject.toString())
                imgUrl=json.jsonObject.getJSONObject("sprites").getString("back_default")
                speciesString=json.jsonObject.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name")
                weightString=json.jsonObject.getString("weight")
                species.setText("Type: "+speciesString)
                weight.setText("Weight: "+weightString)

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String,
                throwable: Throwable?
            ) {
            }
        }]
    }


}