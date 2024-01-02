package com.example.a3strokemanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.a3strokemanagement.databinding.ActivitySigninPageBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import retrofit2.Response

class SignInPage : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivitySigninPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else {
            binding.txtSignUp.setOnClickListener {
                val intent = Intent(this, RegisterPage::class.java)
                startActivity(intent)
            }
            firebaseAuth = FirebaseAuth.getInstance()
            binding.btnSignIn.setOnClickListener {

                val mailSignIn = binding.mailSignIn.text.toString()
                val passwordSignIn = binding.passwordSignIn.text.toString()


                if (mailSignIn.isNotEmpty() && passwordSignIn.isNotEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(mailSignIn, passwordSignIn)
                        .addOnCompleteListener() {
                            if (it.isSuccessful) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Wrong user or password!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                } else {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}