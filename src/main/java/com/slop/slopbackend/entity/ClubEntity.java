package com.slop.slopbackend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "clubs")
public class ClubEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id ;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true,nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String clubName;

    @Column(nullable = false)
    private String clubSlug;

    @Column(nullable = false)
    private String clubDescription;
}
