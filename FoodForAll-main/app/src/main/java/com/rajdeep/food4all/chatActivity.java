package com.rajdeep.food4all;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class chatActivity extends AppCompatActivity {

    // MainActivity.java

    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private Button sendButton;
    private List<Message> messages = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),homeScreenActivity.class);
                startActivity(intent);
            }
        });

        // Initialize views
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        // Set up RecyclerView
        chatAdapter = new ChatAdapter(messages);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);

        // Add welcome message
        addMessage("Hi! How can I help you with donations today?", false);

        // Handle send button click
        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString();
            if (!message.isEmpty()) {
                sendMessage(message);
                messageInput.setText("");
            }
        });
    }

    private void sendMessage(String messageText) {
        // Add user message
        addMessage(messageText, true);

        // Get automated response
        String response = getResponse(messageText);

        // Add bot response with small delay
        new Handler().postDelayed(() -> {
            addMessage(response, false);
        }, 500);
    }

    private void addMessage(String text, boolean isUser) {
        messages.add(new Message(text, isUser));
        chatAdapter.notifyItemInserted(messages.size() - 1);
        chatRecyclerView.scrollToPosition(messages.size() - 1);
    }

    private String getResponse(String message) {
        // Simple response logic
        message = message.toLowerCase();
        if (message.contains("minimum") || message.contains("how much")) {
            return "The minimum donation amount is $5.";
        } else if (message.contains("tax") || message.contains("deduct")) {
            return "Yes, all donations are tax-deductible!";
        } else if (message.contains("track") || message.contains("history")) {
            return "You can track all your donations in your account dashboard.";
        } else if (message.contains("anonymous")) {
            return "Yes, you can choose to donate anonymously.";
        } else {
            return "I'm here to help! You can ask about minimum donations, tax deductions, or tracking your donations.";
        }
    }
}