package com.example.foodapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.fragments.homeFragment
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var youtubeLink:String
    private  lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm:MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

         mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        getMealIformationFromIntent()

        setInformatiomInViews(

        )
        loadingCase()
        mealMvvm.getMealDetial(mealId)

        observerMealDetialsLiveData()
        onYoutubeImageClick()

    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observerMealDetialsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this
        ) { t ->
            onResponseCase()
            val meal = t
            binding.tvCategoryInfo.text = "Category :${meal!!.strMeal}"
            binding.tvAreaInfo.text = "Area :${meal!!.strArea}"
            binding.tvInstructions.text = "Instruction :${meal!!.strInstructions}"
            youtubeLink = meal.strYoutube
        }
    }

    private fun setInformatiomInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealIformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(homeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(homeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(homeFragment.MEAL_THUMB)!!
    }
    private fun loadingCase(){
        binding.progressBar.visibility=View.VISIBLE
        binding.btnSave.visibility= View.INVISIBLE
        binding.tvInstructions.visibility= View.INVISIBLE
        binding.tvCategoryInfo.visibility= View.INVISIBLE
        binding.tvAreaInfo.visibility= View.INVISIBLE
        binding.imgYoutube.visibility= View.INVISIBLE
    }
    private fun onResponseCase(){
        binding.progressBar.visibility=View.INVISIBLE
        binding.btnSave.visibility= View.VISIBLE
        binding.tvInstructions.visibility= View.VISIBLE
        binding.tvCategoryInfo.visibility= View.VISIBLE
        binding.tvAreaInfo.visibility= View.VISIBLE
        binding.imgYoutube.visibility= View.VISIBLE
    }
}