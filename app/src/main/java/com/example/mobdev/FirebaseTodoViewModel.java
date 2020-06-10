package com.example.mobdev;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.function.Consumer;

public class FirebaseTodoViewModel {

    private FirebaseTodoModel todoModel;

    public FirebaseTodoViewModel() {
        todoModel = new FirebaseTodoModel();
    }

    public void addTodoItem(TodoItem item) {
        todoModel.addTodoItem(item);
    }

    public void getTodoItems(Consumer<ArrayList<TodoItem>> responseCallback) {
        todoModel.getTodoItems(
                (QuerySnapshot querySnapshot) -> {
                    if (querySnapshot != null) {
                        ArrayList<TodoItem> todoItems = new ArrayList<>();
                        for (DocumentSnapshot todoSnapshot : querySnapshot.getDocuments()) {
                            TodoItem item = todoSnapshot.toObject(TodoItem.class);
                            assert item != null;
                            item.uid = todoSnapshot.getId();
                            item.name = todoSnapshot.get("name").toString();
                            item.imageUrl = todoSnapshot.get("imageUrl").toString();
                            item.lat = todoSnapshot.get("lat").toString();
                            item.longitude = todoSnapshot.get("longitude").toString();
                            item.liked = todoSnapshot.getBoolean("liked");
                            todoItems.add(item);
                        }
                        responseCallback.accept(todoItems);
                    }
                },
                (databaseError -> System.out.println("Error reading Todo Items: " + databaseError))
        );
    }

    public void updateTodoItem(TodoItem item) {
        todoModel.updateTodoItemById(item);
    }

    public void clear() {
        todoModel.clear();
    }
}

