package com.xover.imagesearch.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "image_details", createIndex = true)
public class ImageDetailsDocument {
    @Id
    private long id;
    private String description;
    private String fileType;
    private Long fileSize;
}
