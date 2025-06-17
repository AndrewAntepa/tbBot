package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTaskEntity;
import pro.sky.telegrambot.service.NotificationTaskService;

import java.util.List;

@Service
public class SendNotificationsService {
    @Autowired
    private NotificationTaskService notificationTaskService;

    @Autowired
    private TelegramBot telegramBot;

    @Scheduled(cron = "0 0/1 * * * *")
    public void notifyTG(){
        List<NotificationTaskEntity> allNotifications = notificationTaskService.getByLocalDateTime();
        allNotifications.forEach(entity -> {
            telegramBot.execute(new SendMessage(entity.getChatId(), entity.getMessage()));
            allNotifications.remove(entity);
        });
    }
}
