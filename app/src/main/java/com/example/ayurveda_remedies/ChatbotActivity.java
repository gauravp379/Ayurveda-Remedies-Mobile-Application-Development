package com.example.ayurveda_remedies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.ayurveda_remedies.data.RemedyRepository;
import com.example.ayurveda_remedies.models.Remedy;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {

    private RecyclerView rvChatMessages;
    private EditText etChatInput;
    private ImageView btnSendMessage, btnCloseChatbot;
    private Button btnQuickQuiz, btnQuickRemedies, btnQuickWellness;
    
    private List<ChatMessage> messages = new ArrayList<>();
    private ChatAdapter adapter;
    private RemedyRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        ProfileUtils.loadProfileIntoViews(this, R.id.ivProfilePic, R.id.tvUserName);


        repository = RemedyRepository.getInstance();

        rvChatMessages = findViewById(R.id.rvChatMessages);
        etChatInput = findViewById(R.id.etChatInput);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        btnCloseChatbot = findViewById(R.id.btnCloseChatbot);
        btnQuickQuiz = findViewById(R.id.btnQuickQuiz);
        btnQuickRemedies = findViewById(R.id.btnQuickRemedies);
        btnQuickWellness = findViewById(R.id.btnQuickWellness);

        adapter = new ChatAdapter(messages);
        rvChatMessages.setLayoutManager(new LinearLayoutManager(this));
        rvChatMessages.setAdapter(adapter);

        // Welcome message
        addBotMessage("Hello! I'm AyurBot 🙏\n\nI can help you with:\n• Ayurvedic remedies\n• Dosha information\n• Wellness tracking\n• Health tips\n\nAsk me anything!");

        btnSendMessage.setOnClickListener(v -> sendMessage());
        btnCloseChatbot.setOnClickListener(v -> finish());

        btnQuickQuiz.setOnClickListener(v -> {
            addUserMessage("Take quiz");
            addBotMessage("Great! Starting the Dosha Quiz...");
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, DoshaQuizActivity.class));
            }, 800);
        });

        btnQuickRemedies.setOnClickListener(v -> {
            addUserMessage("Show remedies");
            addBotMessage("Here are some popular remedies:");
            showRemedySuggestions(repository.getPopularRemedies(3));
        });

        btnQuickWellness.setOnClickListener(v -> {
            addUserMessage("Wellness tracker");
            addBotMessage("Opening Daily Wellness Tracker...");
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, DailyWellnessActivity.class));
            }, 800);
        });

        // Add FAQ as first message after welcome
        new Handler().postDelayed(() -> {
            addBotMessage("\n💡 Frequently Asked Questions:\n\n1. What is my dosha?\n2. How to use wellness tracker?\n3. Show me remedies for stress\n4. What are the benefits of Ayurveda?\n5. How to track my progress?\n\nJust type the number or ask anything!");
        }, 1000);
    }

    private void sendMessage() {
        String input = etChatInput.getText().toString().trim().toLowerCase();
        if (input.isEmpty()) return;

        addUserMessage(etChatInput.getText().toString().trim());
        etChatInput.setText("");

        // Process with delay for natural feel
        new Handler().postDelayed(() -> processInput(input), 500);
    }

    private void processInput(String input) {
        // FAQ number handling
        if (input.equals("1")) {
            addBotMessage("To discover your dosha type, take our quiz! It analyzes your physical and mental characteristics.");
            new Handler().postDelayed(() -> startActivity(new Intent(this, DoshaQuizActivity.class)), 1500);
            return;
        } else if (input.equals("2")) {
            addBotMessage("The Wellness Tracker helps you:\n\n• Set reminders for yoga, meditation, and meals\n• Track your daily streaks\n• Build healthy habits\n\nOpening now...");
            new Handler().postDelayed(() -> startActivity(new Intent(this, DailyWellnessActivity.class)), 1500);
            return;
        } else if (input.equals("3")) {
            showRemediesFor("Mental Wellness");
            addBotMessage("Here are stress-relief remedies:");
            return;
        } else if (input.equals("4")) {
            addBotMessage("Ayurveda Benefits:\n\n✅ Natural healing\n✅ Personalized wellness\n✅ Holistic approach\n✅ Prevents diseases\n✅ Improves mental clarity\n✅ Balances body & mind");
            return;
        } else if (input.equals("5")) {
            addBotMessage("Track your progress with:\n\n📊 Progress Tracker - Log dosha entries\n📅 Health Calendar - Track symptoms\n🧘 Wellness Streaks - Monitor habits\n\nOpening Progress Tracker...");
            new Handler().postDelayed(() -> startActivity(new Intent(this, ProgressActivity.class)), 1500);
            return;
        }
        
        // Simple pattern matching
        if (input.contains("hello") || input.contains("hi") || input.contains("hey")) {
            addBotMessage("Hello! 🙏 How can I help you today?");
        }
        else if (input.contains("headache") || input.contains("head pain")) {
            showRemediesFor("Digestive Health");
            addBotMessage("Try these remedies for headache relief!");
        }
        else if (input.contains("cold") || input.contains("cough")) {
            showRemediesFor("Respiratory Health");
            addBotMessage("Here are remedies for cold and cough:");
        }
        else if (input.contains("stress") || input.contains("anxiety")) {
            showRemediesFor("Mental Wellness");
            addBotMessage("Try these stress-relief remedies:");
        }
        else if (input.contains("vata")) {
            addBotMessage("Vata dosha governs movement, circulation, and breathing.\n\nCharacteristics: Creative, energetic, quick thinking\n\nImbalance signs: Anxiety, dry skin, irregular digestion\n\nRemedies: Warm foods, regular routine, grounding practices");
        }
        else if (input.contains("pitta")) {
            addBotMessage("Pitta dosha governs digestion, metabolism, and energy production.\n\nCharacteristics: Ambitious, intelligent, focused\n\nImbalance signs: Anger, inflammation, acid reflux\n\nRemedies: Cooling foods, moderate exercise, relaxation");
        }
        else if (input.contains("kapha")) {
            addBotMessage("Kapha dosha governs structure, lubrication, and immunity.\n\nCharacteristics: Calm, steady, strong\n\nImbalance signs: Weight gain, lethargy, congestion\n\nRemedies: Light foods, vigorous exercise, stimulation");
        }
        else if (input.contains("quiz") || input.contains("test") || input.contains("dosha quiz")) {
            addBotMessage("I can help you discover your dosha type! Would you like to take the quiz?");
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, DoshaQuizActivity.class));
            }, 1500);
        }
        else if (input.contains("remedy") || input.contains("remedies") || input.contains("cure")) {
            addBotMessage("I have many Ayurvedic remedies! What are you looking for?\n\n• Digestive issues\n• Stress relief\n• Skin care\n• Immunity\n• Hair care\n\nOr browse all remedies:");
            showRemedySuggestions(repository.getPopularRemedies(5));
        }
        else if (input.contains("wellness") || input.contains("tracker") || input.contains("reminder")) {
            addBotMessage("The Daily Wellness Tracker helps you:\n• Set reminders for yoga, meditation, diet\n• Track daily streaks\n• Monitor progress\n\nWould you like to open it?");
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, DailyWellnessActivity.class));
            }, 1500);
        }
        else if (input.contains("calendar") || input.contains("notes") || input.contains("symptoms")) {
            addBotMessage("The Health Calendar lets you track symptoms and notes by date. Opening...");
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, CalendarActivity.class));
            }, 1000);
        }
        else if (input.contains("progress") || input.contains("track")) {
            addBotMessage("Track your dosha balance over time with the Progress Tracker. Opening...");
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, ProgressActivity.class));
            }, 1000);
        }
        else if (input.contains("thank")) {
            addBotMessage("You're welcome! 🙏 Feel free to ask anything else!");
        }
        else if (input.contains("feedback") || input.contains("review") || input.contains("rating")) {
            addBotMessage("I'd love to hear your feedback! Opening the feedback form...");
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, FeedbackActivity.class));
            }, 1000);
        }
        else if (input.contains("faq") || input.contains("questions") || input.contains("help")) {
            addBotMessage("💡 Frequently Asked Questions:\n\n1. What is my dosha?\n2. How to use wellness tracker?\n3. Show me remedies for stress\n4. What are the benefits of Ayurveda?\n5. How to track my progress?\n\nJust type the number!");
        }
        else if (input.contains("bye") || input.contains("exit")) {
            addBotMessage("Take care! Stay healthy 🌿");
            new Handler().postDelayed(this::finish, 1500);
        }
        else {
            addBotMessage("I can help you with:\n\n• Ayurvedic remedies\n• Dosha information (Vata, Pitta, Kapha)\n• Take the Dosha Quiz\n• Daily wellness tracking\n• Health calendar\n\nWhat would you like to know?");
        }
    }

    private void showRemediesFor(String category) {
        List<Remedy> remedies = repository.searchRemedies(category);
        if (!remedies.isEmpty()) {
            showRemedySuggestions(remedies.subList(0, Math.min(3, remedies.size())));
        }
    }

    private void showRemedySuggestions(List<Remedy> remedies) {
        for (Remedy remedy : remedies) {
            addBotMessage("• " + remedy.getName() + "\n  " + remedy.getShortDescription());
        }
    }

    private void addUserMessage(String text) {
        messages.add(new ChatMessage(text, true));
        adapter.notifyItemInserted(messages.size() - 1);
        rvChatMessages.smoothScrollToPosition(messages.size() - 1);
    }

    private void addBotMessage(String text) {
        messages.add(new ChatMessage(text, false));
        adapter.notifyItemInserted(messages.size() - 1);
        rvChatMessages.smoothScrollToPosition(messages.size() - 1);
    }

    // Simple data class
    static class ChatMessage {
        String text;
        boolean isUser;

        ChatMessage(String text, boolean isUser) {
            this.text = text;
            this.isUser = isUser;
        }
    }

    // Simple adapter
    class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
        List<ChatMessage> messages;

        ChatAdapter(List<ChatMessage> messages) {
            this.messages = messages;
        }

        @Override
        public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_message, parent, false);
            return new ChatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ChatViewHolder holder, int position) {
            ChatMessage msg = messages.get(position);
            holder.tvMessage.setText(msg.text);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.cvMessage.getLayoutParams();
            
            if (msg.isUser) {
                params.gravity = Gravity.END;
                holder.cvMessage.setCardBackgroundColor(0xFF4CAF50);
                holder.tvMessage.setTextColor(0xFFFFFFFF);
            } else {
                params.gravity = Gravity.START;
                holder.cvMessage.setCardBackgroundColor(0xFFFFFFFF);
                holder.tvMessage.setTextColor(0xFF000000);
            }
            holder.cvMessage.setLayoutParams(params);
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        class ChatViewHolder extends RecyclerView.ViewHolder {
            TextView tvMessage;
            MaterialCardView cvMessage;

            ChatViewHolder(View itemView) {
                super(itemView);
                tvMessage = itemView.findViewById(R.id.tvMessage);
                cvMessage = itemView.findViewById(R.id.cvMessage);
            }
        }
    }
}
