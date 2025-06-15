package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTaskEntity;
import pro.sky.telegrambot.repositiry.NotificationTaskRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;

@Service
public class NotificationTaskService {
    private final NotificationTaskRepository notificationTaskRepository;
    Logger logger = Logger.getLogger(NotificationTaskService.class.getName());

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @Transactional
    public void save(NotificationTaskEntity entity){
        notificationTaskRepository.save(entity);
    }

    public void delete(NotificationTaskEntity entity){
        notificationTaskRepository.deleteById(entity.getId());
    }

    public List<NotificationTaskEntity> getAll(){
        return notificationTaskRepository.findAll();
    }

    public List<NotificationTaskEntity> getByLocalDateTime(){
        return notificationTaskRepository.findAllByDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }
}
