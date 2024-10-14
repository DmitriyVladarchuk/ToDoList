package com.example.todolist.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.Models.TaskItem
import com.example.todolist.R
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun Home(modifier: Modifier, viewModel: HomeViewModel = viewModel()) {
    Main()
}

@OptIn(ExperimentalMaterial3Api::class)
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
                        VisibilityIcon(isIconVisibility)
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
fun VisibilityIcon(isIconVisibility: MutableState<Boolean>) {
    Icon(
        painter = painterResource(id =
        if(isIconVisibility.value)
            R.drawable.visibility_24px
        else
            R.drawable.visibility_off_24px
        ),
        contentDescription = null,
        modifier = Modifier
            .padding(end = 16.dp)
            .clickable {
            isIconVisibility.value = !isIconVisibility.value
        },
        tint = Color.Blue
    )
}

@Composable
fun TaskList(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Card(
            //modifier = modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(20.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(10.dp)) {
                items(100) {
                    TodoItem(TaskItem(textTask = "Task $it", importance = 0))
                }
            }
        }
    }
}


@Composable
fun TodoItem(item: TaskItem) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CustomCheckBox(item = item, onValueChange = { /* checked = it */ })

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "More details",
            modifier = Modifier.size(48.dp)
        )
    }
}


@Composable
fun CustomCheckBox(item: TaskItem, onValueChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .toggleable(
                value = item.isDone,
                role = Role.Checkbox,
                onValueChange = onValueChange
            ),
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(32.dp)
                .border(
                    width = 2.dp,
                    color = if (item.isDone) Color.Green else Color.LightGray,
                    shape = RoundedCornerShape(50.dp)
                )
                .background(
                    color = if (item.isDone) Color.Green else Color.White,
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if(item.isDone){
                Icon(imageVector = Icons.Default.Check, contentDescription = "Checked", modifier = androidx.compose.ui.Modifier.padding(
                    3.dp
                ), tint = Color.White)
            }
        }

        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(
                text = item.textTask,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp,
            )
            if (item.deadline != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.alpha(0.5f)
                ) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Deadline")
                    val dateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
                    Text(
                        text = dateFormat.format(item.deadline!!),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }
        }
    }
}