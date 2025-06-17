package pro.sky.telegrambot.repositiry;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.NotificationTaskEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationTaskRepository extends JpaRepository<NotificationTaskEntity, Long> {
    List<NotificationTaskEntity> findAllByDateTime(LocalDateTime dateTime);
}
