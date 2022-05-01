package com.example.firebaselogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {


    lateinit var email: EditText
    lateinit var pwd: EditText
    lateinit var name: EditText
    lateinit var number: EditText
    lateinit var btn: Button
    private lateinit var firebaseDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (FirebaseAuth.getInstance().currentUser!=null){
            val intent = Intent(this, Login_screen::class.java)
            startActivity(intent)
            finish()
        }




        email = findViewById(R.id.email)
        pwd = findViewById(R.id.password)
        btn = findViewById(R.id.create_btn)
        number = findViewById(R.id.number)
        name = findViewById(R.id.Name)
        firebaseDatabase= FirebaseDatabase.getInstance().reference

        btn.setOnClickListener {
            val email_txt = email.text.toString().trim()
            val pwd_txt = pwd.text.toString().trim()
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_txt,pwd_txt).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"logged in",Toast.LENGTH_LONG).show()
                    val hashMap= hashMapOf<String,String>()
                    hashMap["email"]=email.text.toString().trim()
                    hashMap["password"]=pwd.text.toString().trim()
                    hashMap["number"]=number.text.toString().trim()
                    hashMap["name"]=name.text.toString().trim()

                    firebaseDatabase.child("user_list").child(firebaseDatabase.push().key.toString()).setValue(hashMap)


                    val intent = Intent(this, Login_screen::class.java)
                    startActivity(intent)
                    finish()


                } else {
                    Toast.makeText(this, "Unsuccessful try again ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }



    }




}