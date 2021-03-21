package ru.pankov.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chat_session")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatSessionEntity {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "method_id")
    private Integer methodId;
}
