package com.xover.imagesearch.repository;

import com.xover.imagesearch.document.ImageDetailsDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDetailsSearchRepository extends ElasticsearchRepository<ImageDetailsDocument, Long> {

    List<ImageDetailsDocument> findByDescription(String description, Pageable pageable);

    List<ImageDetailsDocument> findByFileType (String fileType, Pageable pageable);

    List<ImageDetailsDocument> findByFileSize(Long fileSize, Pageable pageable);

    List<ImageDetailsDocument> findByFileTypeAndDescriptionAllIgnoreCase(String fileType, String description, Pageable pageable);

    List<ImageDetailsDocument> findByDescriptionAndFileSizeAllIgnoreCase(String description, Long fileSize, Pageable pageable);

    List<ImageDetailsDocument> findByFileTypeAndFileSizeAllIgnoreCase(String fileType, Long fileSize, Pageable pageable);

    List<ImageDetailsDocument> findByDescriptionAndFileTypeAndFileSizeAllIgnoreCase(String description, String fileType, Long fileSize, Pageable pageable);
}
