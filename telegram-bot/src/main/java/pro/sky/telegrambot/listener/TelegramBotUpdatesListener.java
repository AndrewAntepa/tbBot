package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTaskEntity;
import pro.sky.telegrambot.repositiry.NotificationTaskRepository;
import pro.sky.telegrambot.service.NotificationTaskService;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final static String WELCOME_MESSAGE = "Ну дарова емае";

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);



    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private NotificationTaskService notificationTaskService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            String textMessage = update.message().text();
            Long chat_id = update.message().chat().id();

            if (textMessage.equals("/start")) {
                telegramBot.execute(new SendMessage(chat_id, WELCOME_MESSAGE));
            } else {
                Pattern pattern = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");
                Matcher matcher = pattern.matcher(textMessage);

                if(matcher.matches()){
                    String dateAndTimeFromUser = matcher.group(1);
                    String message = matcher.group(3);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

                    try {
                        LocalDateTime dateTime = LocalDateTime.parse(dateAndTimeFromUser, formatter);
                        if (dateTime.isBefore(LocalDateTime.now())) {
                            telegramBot.execute(new SendMessage(chat_id, "Дата должна быть в будущем!"));
                            return;
                        }
                        NotificationTaskEntity entity = new NotificationTaskEntity(chat_id, dateTime, message);
                        notificationTaskService.save(entity);

                        telegramBot.execute(new SendMessage(chat_id, "Напоминание сохранено!"));
                    } catch (DateTimeParseException e) {
                        telegramBot.execute(new SendMessage(chat_id, "Неверный формат даты!"));
                    }
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
