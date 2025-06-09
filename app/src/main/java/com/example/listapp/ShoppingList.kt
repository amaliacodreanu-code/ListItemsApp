package com.example.listapp
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.listapp.ui.theme.ShoppingItem
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListApp()
{  //remember -> tine minte variabila chair daca UI-ul se redeseneaza
    //mutableStateOf creează o valoare pe care Compose o „urmărește”. Dacă valoarea se schimbă, Compose redesenează automat ce trebuie
    var sItems by remember{ mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }//text introdus de utilizator pentru numele produsului
    var itemQuant by remember { mutableStateOf(" ") } // text introdus pentru cantitate
    

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    )
    {
        Button(
            onClick = {showDialog = true},
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(top = 100.dp)
                .fillMaxWidth().padding(4.dp)

        ) {
            Text (
                text = "Add Items"
            )
        }
        //datorita acestei coloane care a ocupat intreg spatiu butonul s-a dus sus
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(16.dp)
        ){
            //items este o functie speciala din LazyColumn
            //Functia items ia o lista si creeaza automat un element UI (un card ) pentru fiecare element din aceea lista
            items(sItems)
            {
                ShoppingListItems(
                    it, {}, {}
                )
            }
        }
    }

    if(showDialog)
    {
        AlertDialog(onDismissRequest = {showDialog = false},
                   confirmButton = {
                       Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
                           horizontalArrangement =  Arrangement.SpaceBetween){
                           Button(onClick = {
                               //isNotBlank() = functie Kotlin care verifica daca un sir de caractere string nu este null, nu este gol, si nu contine spatii albe
                               if(itemName.isNotBlank()) {
                                   val newItem = ShoppingItem(
                                       id = sItems.size + 1,
                                       name = itemName,
                                       quantity = itemQuant.toIntOrNull(),
                                       change = true
                                   )
                                   sItems += newItem
                                   showDialog = false
                                   itemName = " "
                                   itemQuant = " "

                               }
                           }) {
                               Text("Add")
                           }
                           Button(onClick = {showDialog = false}) {
                               Text("Cancel")
                           }
                       }
                   },
                   title = { Text("Add shopping items")},
                   text =
                   {
                       Column {
                           //OutlineTextField() este o casuta de tip text in Jetpack Compose
                           //it este noul text scris de catre utilizator, contextual il folosim pentru a actualiza variabila itemName
                           //nu are bloc {} nu poate contine alte elemente
                           OutlinedTextField(value = itemName,
                               onValueChange = {itemName =it},
                               singleLine = true,
                               modifier = Modifier.fillMaxWidth().padding(8.dp))

                           OutlinedTextField(value = itemQuant,
                               onValueChange = {itemQuant =it},
                               singleLine = true,
                               modifier = Modifier.fillMaxWidth().padding(8.dp)
                           )
                       }
                   }
        )
    }
}

//BorderStroke este o clasa care defineste o bordura
//Sintaxa: BorderStroke(width: Dp, color: Color)
//width: cât de groasă să fie marginea (ex: 2.dp)
//color: ce culoare să aibă marginea (Color.Red, Color(0xFFD6CEC3), etc)

@Composable
fun ShoppingListItems(
    item:ShoppingItem,//un obiect de tipul Shoppingitem
    onEditClick:() -> Unit,
    onDeleteClick:()->Unit,
)
{
  Row(
      modifier = Modifier.padding(8.dp)
                         .fillMaxWidth()
                         .border(
                             BorderStroke(2.dp, RandomColor()),
                             shape = RoundedCornerShape(20.dp)
                         )
  )
  {  Text(text  = item.name,
          modifier = Modifier.padding(8.dp))
      Text(text  = "Quantity: ${item.quantity}",
          modifier = Modifier.padding(8.dp))
      //creez randul unde vor fi butoanele
      Row( modifier = Modifier.padding(end  = 1.dp)){
          //buton special in Jetpack Compose care este folosit pentru a afisa o pictograma, iconita in loc de text
          IconButton(onClick = onEditClick)
          {
              //Icons.Default.Edit selecteaza o imagine(iconita) din Material Design, unde Material Design este sistemul de Design facut de Google care vine cu sute de iconite gratuite
              Icon(imageVector = Icons.Default.Edit, contentDescription = "butonul ce editeaza continutul" )
          }
          IconButton(onClick = onDeleteClick)
          {
              //Icons.Default.Edit selecteaza o imagine(iconita) din Material Design, unde Material Design este sistemul de Design facut de Google care vine cu sute de iconite gratuite
              Icon(imageVector = Icons.Default.Delete, contentDescription = "butonul ce sterge" )
          }
      }
  }

}

fun RandomColor():Color
{
     val red  = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)

    return  Color(red,green,blue)
 }