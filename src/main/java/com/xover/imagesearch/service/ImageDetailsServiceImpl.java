package com.xover.imagesearch.service;

import com.xover.imagesearch.document.ImageDetailsDocument;
import com.xover.imagesearch.exception.RecordsNotFoundException;
import com.xover.imagesearch.repository.ImageDetailsSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageDetailsServiceImpl implements ImageDetailsService {

    private final ImageDetailsSearchRepository imageDetailsSearchRepository;

    /*
      Had to write ugly if-else as spring data elastic search repository interface allows limited methods to query AWS ES.
     */
    @Override
    public List<ImageDetailsDocument> search(Optional<String> description, Optional<String> fileType, Optional<Long> fileSize, Pageable pageable) {
        List<ImageDetailsDocument> result = null;

        if (description.isPresent() && !fileType.isPresent() && !fileSize.isPresent()) {
            result = imageDetailsSearchRepository.findByDescription(description.get(), pageable);
        } else if (!description.isPresent() && fileType.isPresent() && !fileSize.isPresent()) {
            result = imageDetailsSearchRepository.findByFileType(fileType.get(), pageable);
        } else if (!description.isPresent() && !fileType.isPresent() && fileSize.isPresent() && fileSize.get().longValue() > 0) {
            result = imageDetailsSearchRepository.findByFileSize(fileSize.get(), pageable);
        } else if (description.isPresent() && fileType.isPresent() && !fileSize.isPresent()) {
            result = imageDetailsSearchRepository
                    .findByFileTypeAndDescriptionAllIgnoreCase(
                            fileType.get(), description.get(), pageable);
        } else if (description.isPresent() && fileSize.isPresent() && fileSize.get().longValue() > 0 && !fileType.isPresent()) {
            result = imageDetailsSearchRepository
                    .findByDescriptionAndFileSizeAllIgnoreCase(
                            description.get(), fileSize.get(), pageable);

        } else if (fileType.isPresent() && fileSize.isPresent() && fileSize.get().longValue() > 0 && !description.isPresent()) {
            result = imageDetailsSearchRepository
                    .findByFileTypeAndFileSizeAllIgnoreCase(
                            fileType.get(), fileSize.get(), pageable);
        }  else if (description.isPresent() && fileType.isPresent() && fileSize.isPresent() && fileSize.get().longValue() > 0 ) {
            result = imageDetailsSearchRepository
                    .findByDescriptionAndFileTypeAndFileSizeAllIgnoreCase(
                            description.get(), fileType.get(), fileSize.get(), pageable);
        }
        else {
            result = imageDetailsSearchRepository.findAll(pageable).toList();
        }
        if(result == null || result.size() == 0) {
            throw new RecordsNotFoundException();
        }
        return  result;
    }

}
