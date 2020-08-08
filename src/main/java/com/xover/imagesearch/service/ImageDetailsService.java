package com.xover.imagesearch.service;

import com.xover.imagesearch.document.ImageDetailsDocument;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ImageDetailsService {
    List<ImageDetailsDocument> search(Optional<String> description, Optional<String> fileType, Optional<Long> fileSize, Pageable pageable);
}
