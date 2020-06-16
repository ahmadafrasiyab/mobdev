package com.example.mobdev;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FirebaseTodoModel {
    private FirebaseFirestore db;
    private List<ListenerRegistration> listeners;

    public FirebaseTodoModel() {
        db = FirebaseFirestore.getInstance();
        listeners = new ArrayList<>();
    }

    public void addTodoItem(TodoItem item) {
        CollectionReference todoItemsRef = db.collection("matches");
        todoItemsRef.add(item);
    }

    public void getTodoItems(Consumer<QuerySnapshot> dataChangedCallback, Consumer<FirebaseFirestoreException> dataErrorCallback) {
        ListenerRegistration listener = db.collection("matches")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        dataErrorCallback.accept(e);
                    }
                    dataChangedCallback.accept(queryDocumentSnapshots);
                });
        listeners.add(listener);
    }

    public void updateTodoItemById(TodoItem item) {
        DocumentReference todoItemRef = db.collection("matches").document(item.getUid());
        Map<String, Object> data = new HashMap<>();
        data.put("name", item.getName());
        data.put("imageUrl", item.getImageUrl());
        data.put("lat", item.getLat());
        data.put("longitude", item.getLongitude());
        data.put("uid", item.getUid());
        data.put("liked", item.getLiked());
        todoItemRef.update(data);
    }

    public void clear() {
        // Clear all the listeners onPause
        listeners.forEach(ListenerRegistration::remove);
    }
}
