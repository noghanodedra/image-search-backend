package com.xover.imagesearch.repository;

import com.xover.imagesearch.model.ImageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDetailsRepository extends JpaRepository<ImageDetails, Long> {

}
