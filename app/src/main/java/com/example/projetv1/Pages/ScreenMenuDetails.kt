package com.example.projetv1.Pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projetv1.Data.restaurantList

@Composable
fun ScreenMenuDetail(restaurantId:Int , menuId: Int, navController: NavController) {

    val restaurant = remember { restaurantList.find { it.restId == restaurantId } }
    val menu = remember { restaurant?.restMenuList?.find { it.menuId == menuId } }
    var quantity by remember { mutableStateOf(1) }  // Quantité initiale
    if (menu != null) {
        val totalPrice = remember(quantity) { menu.menuPrice * quantity }  // Calcul du prix total

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // Bouton retour et titre
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {navController.popBackStack()}) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Options", style = MaterialTheme.typography.headlineSmall, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Informations sur l'article
            Card(
                modifier = Modifier.fillMaxWidth().shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(15.dp),
                    true,
                    Color.DarkGray,
                    Color.Gray
                ),
                colors = CardColors(
                    containerColor = Color.White,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified
                ),
                //elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            menu.menuName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "$totalPrice DZD",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = Color(
                                    0xFFFF5722
                                )
                            ),
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        // "Eggs, 100G Meat, Lettuce, Onion, American Cheese.",
                        text = menu.menuDescription,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    //Spacer(modifier = Modifier.height(8.dp))

                }
            }

            Spacer(modifier = Modifier.height(25.dp))


            Card(

                colors = CardColors(
                    containerColor = Color.White,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified
                ),
                modifier = Modifier.shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(15.dp),
                    true,
                    Color.DarkGray,
                    Color.Gray
                )
            ) {


                // Section pour la quantité et le prix total
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quantity",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    // Sélecteur de quantité
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(5.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(50))
                            .background(color = Color(0xffECECE3), shape = RoundedCornerShape(50))
                    ) {
                        Button(
                            onClick = { if (quantity > 1) quantity-- },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Black,

                                )
                        )
                        {
                            Text("—", fontSize = 25.sp)
                        }
                        Text(
                            text = "$quantity",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            onClick = { quantity++ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Black,
                            )
                        )
                        {
                            Text("+", fontSize = 25.sp)
                        }
                    }
                }
            }



            Spacer(modifier = Modifier.height(25.dp))
            Column() {
                var note by remember { mutableStateOf("") }
                // Notes
                Text(
                    "Notes", style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 20.dp), fontWeight = FontWeight.Bold
                )
                TextField(
                    value = note,
                    onValueChange = {note =it},
                    placeholder = { Text("Do you have something to say to the restaurant?") },
                    modifier = Modifier
                        .fillMaxWidth().padding(top = 8.dp).shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(15.dp),
                            true,
                            Color.DarkGray,
                            Color.Gray
                        )
                        .height(170.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(15.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bouton "Add to Cart"
            Button(
                onClick = { /* Ajouter au panier */ },
                modifier = Modifier
                    .fillMaxWidth().height(80.dp)
                    .padding(vertical = 16.dp, horizontal = 30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5722)
                )
            ) {
                Text(
                    "Add to Cart",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }else{

        Text(text = "No Additional information about this menu")
    }
}
