package com.xover.imagesearch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "image_details3")
@EntityListeners(AuditingEntityListener.class)
public class ImageDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "description", nullable = false)
    @NotBlank(message = "Description can not be blank")
    private String description;

    @Column(name = "file_type" , nullable = false)
    @NotBlank(message = "File Type can not be blank")
    private String fileType;

    @Column(name = "file_size" , nullable = false)
    @Min(value = 1, message = "File Size can not be 0")
    private Long fileSize;

    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private Instant createdDate;

    @Column(name = "last_modified_date", nullable = false)
    @LastModifiedDate
    private Instant lastModifiedDate;
}
