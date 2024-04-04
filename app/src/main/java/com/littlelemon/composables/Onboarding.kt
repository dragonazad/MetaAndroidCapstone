package com.littlelemon.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.littlelemon.R
import com.littlelemon.helpers.rememberImeState
import com.littlelemon.helpers.validateRegData
import com.littlelemon.navigation.Home
import com.littlelemon.navigation.Onboarding
import com.littlelemon.ui.theme.PrimaryGreen
import com.littlelemon.ui.theme.PrimaryYellow


@Composable
fun Onboarding(context: Context, navHostController: NavHostController) {
    val  sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    val firstName = remember {
        mutableStateOf("")
    }

    val lastName = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    
    
    LaunchedEffect(key1 = imeState.value){
        if (imeState.value){
            scrollState.scrollTo(scrollState.maxValue)
        }
    }
    Column(){
        HeaderWithOutNav()
    HeroPanel()
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)) {


        Text(text = "Personal Information",
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.h3)
        OutlinedTextField(
            value = firstName.value,
            onValueChange ={
                           firstName.value = it
            },
            label = { Text(text = "First Name")},
            singleLine = true,
            placeholder = { Text(text = "John")},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            ),
            modifier = Modifier.fillMaxWidth())

        OutlinedTextField(
            value = lastName.value,
            onValueChange ={
                           lastName.value = it
            },
            label = { Text(text = "Last Name")},
            singleLine = true,
            placeholder = { Text(text = "Doe")},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            ),
            modifier = Modifier.fillMaxWidth())

        OutlinedTextField(
            value = email.value,
            onValueChange ={
                           email.value = it
            },
            label = { Text(text = "Email")},
            singleLine = true,
            placeholder = { Text(text = "johndoe@gmail.com")},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            ),
            modifier = Modifier.fillMaxWidth())
        
        Spacer(modifier = Modifier.size(40.dp))
        
        Button(onClick = {
                if (validateRegData(
                        firstName.value,
                        lastName.value,
                        email.value)){
                    sharedPreferences.edit()
                        .putString("firstName", firstName.value)
                        .putString("lastName", lastName.value)
                        .putString("email", email.value)
                        .putBoolean("userRegistered", true)
                        .apply()

                    Toast.makeText(context,
                        "Registration Successful",
                        Toast.LENGTH_SHORT)
                        .show()


                    navHostController.navigate(Home.route){
                        popUpTo(Onboarding.route){inclusive = true}
                        launchSingleTop = true
                    }

                }
                else{
                    Toast.makeText(context,
                        "Invalid Details, Please try again",
                        Toast.LENGTH_SHORT)
                        .show()
                }

        },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)) {
            Text(text = "Register")
        }
    }
    }
}

@Composable
fun HeroPanel() {

    Column(modifier = Modifier
        .background(PrimaryGreen)
        .padding(horizontal = 20.dp, vertical = 10.dp)) {
        Text(text = "Little Lemon", style = MaterialTheme.typography.h1, color = PrimaryYellow)
        Text(text = "New York", style = MaterialTheme.typography.h3, color = Color.White)
        Row(Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with  a modern twist. Turkish, Italian, Indian and chinese recipes are our speciality.",
                modifier = Modifier.fillMaxWidth(0.7f),
                color = Color.White,
                style = MaterialTheme.typography.body1)
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        Spacer(modifier = Modifier.size(10.dp))

    }

}
@Composable
fun HeaderWithOutNav(){
    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth(0.65f))

    }
}

