package com.slop.slopbackend.entity;

import com.slop.slopbackend.exception.ApiRuntimeException;
import com.slop.slopbackend.utility.RandomKeyGenerator;
import com.slop.slopbackend.utility.UserRole;
import com.slop.slopbackend.utility.UserSchool;
import com.slop.slopbackend.utility.UserSpecialization;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private  String fullName;

    @Column(nullable = false,unique = true)
    private String registrationId;

    @Column(nullable = false)
    private String bio="I love dogs.";

    @Column(nullable = false,unique = true)
    private String emailId;

    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole= UserRole.USER;

    @Enumerated(value=EnumType.STRING)
    private UserSpecialization userSpecialization;

    @Enumerated(value = EnumType.STRING)
    private UserSchool userSchool;
    @Column(nullable = false,updatable = false)
    private String secretKey;
    @PrePersist
    public void prePersist() {
        generateSecretKey();
    }

    @PreUpdate
    public void preUpdate() {
        if (secretKey.isBlank()) {
            generateSecretKey();
        }
    }
    private void generateSecretKey(){
        this.secretKey= RandomKeyGenerator.generateRandomKey();
    }
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || Hibernate.getClass(this) != Hibernate.getClass(object))
            return false;
        UserEntity that = (UserEntity) object;
        return id != null && Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
