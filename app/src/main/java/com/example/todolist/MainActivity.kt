package com.example.todolist

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.ui.theme.ToDoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListTheme {
                Main()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Main() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var isScrollTopApp by remember { mutableStateOf(false) }
    val isIconVisibility = remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Мои дела",
                            fontSize = if (!isScrollTopApp) 32.sp else 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (!isScrollTopApp) TopShowTodoItems(isIconVisibility)
                    }
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    if (isScrollTopApp)
                        VisibilityIcon(isIconVisibility, Modifier.padding(end = 16.dp))
                }
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TaskList(Modifier.padding(start = 16.dp, end = 16.dp))
        }

    }

    LaunchedEffect(scrollBehavior.state.collapsedFraction) {
        isScrollTopApp = scrollBehavior.state.collapsedFraction > 0.5
    }
}

@Composable
fun TopShowTodoItems(isIconVisibility: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Выполнено - ${5}",
            fontSize = 20.sp,
            modifier = Modifier.alpha(0.5f)
        )

        VisibilityIcon(isIconVisibility)
    }
}

@Composable
fun VisibilityIcon(isIconVisibility: MutableState<Boolean>, modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id =
        if(isIconVisibility.value)
            R.drawable.visibility_24px
        else
            R.drawable.visibility_off_24px
        ),
        contentDescription = null,
        modifier = modifier.clickable {
            isIconVisibility.value = !isIconVisibility.value
        },
        tint = Color.Blue
    )
}

@Composable
fun TaskList(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp)
    ) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(100) {
                TodoItem(text = "Item $it")
            }
        }
    }
}

@Composable
fun TodoItem(text: String, modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CustomCheckBox(isChecked = checked, text = text, Modifier.padding(end = 10.dp), onValueChange = { checked = it } )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "",
            //modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun CustomCheckBox(isChecked: Boolean, text: String, modifier: Modifier = Modifier, onValueChange: (Boolean) -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            //.fillMaxWidth()
            .height(48.dp)
            .toggleable(
                value = isChecked,
                role = Role.Checkbox,
                onValueChange = onValueChange
            ),
        //horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                //.padding(3.dp)
                .size(32.dp)
                .border(
                    width = 2.dp,
                    color = if (isChecked) Color.Green else Color.LightGray,
                    shape = RoundedCornerShape(50.dp)
                )
                .background(
                    color = if (isChecked) Color.Green else Color.White,
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if(isChecked){
                Icon(imageVector = Icons.Default.Check, contentDescription = "", modifier = Modifier.padding(3.dp), tint = Color.White)
            }
        }

        Text(
            text = text,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp),
        )

//        Spacer(modifier = Modifier.weight(1f))
//
//        Icon(
//            imageVector = Icons.Default.KeyboardArrowRight,
//            contentDescription = "",
//            //modifier = Modifier.align(Alignment.End)
//        )
    }
}