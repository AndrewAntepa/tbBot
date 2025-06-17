package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification_tasks")
public class NotificationTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    private String message;

    public NotificationTaskEntity(Long chat_id, LocalDateTime dateTime, String message) {
        this.chatId = chat_id;
        this.dateTime = dateTime;
        this.message = message;
    }

    public NotificationTaskEntity(){}

    public Long getId() {
        return id;
    }

    public Long getChatId() {
        return chatId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTaskEntity that = (NotificationTaskEntity) o;
        return id == that.id && Objects.equals(chatId, that.chatId) && Objects.equals(dateTime, that.dateTime) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, dateTime, message);
    }

    @Override
    public String toString() {
        return "Дата = " + dateTime +
                ", задача = " + message;
    }
}
