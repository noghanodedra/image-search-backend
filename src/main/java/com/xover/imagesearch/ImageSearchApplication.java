package com.xover.imagesearch;

import com.xover.imagesearch.document.ImageDetailsDocument;
import com.xover.imagesearch.model.ImageDetails;
import com.xover.imagesearch.repository.ImageDetailsRepository;
import com.xover.imagesearch.repository.ImageDetailsSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication(exclude =
		{SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
public class ImageSearchApplication {

	@Autowired
	private ImageDetailsRepository imageDetailsRepository;

	@Autowired
	private ImageDetailsSearchRepository imageDetailsSearchRepository;

	public static void main(String[] args) {
		//java.security.Security.setProperty("networkaddress.cache.ttl" , "60");
		SpringApplication.run(ImageSearchApplication.class, args);
	}

	/*
	 * Retrieve data from database and feed into Elasticsearch cluster
	 */
	@PostConstruct
	public void syncElasticsearch() {
		        log.info("AWS RDS to ES migration started...");
				imageDetailsRepository.flush();
				List<ImageDetails> data = imageDetailsRepository.findAll();
				// delete Elasticsearch documents if already populated
				long documentCount = imageDetailsSearchRepository.count();
				if (documentCount > 0) {
					imageDetailsSearchRepository.deleteAll();
				}
		        log.info("Records to migrate:"+documentCount);
				List<ImageDetailsDocument> docs = data.stream().map(temp -> {
					ImageDetailsDocument doc = new ImageDetailsDocument();
					doc.setId(temp.getId());
					doc.setDescription(temp.getDescription());
					doc.setFileSize(temp.getFileSize());
					doc.setFileType(temp.getFileType());
					return doc;
				}).collect(Collectors.toList());
				imageDetailsSearchRepository.saveAll(docs);
				log.info("AWS RDS to ES migration completed!");

				//List<ImageDetailsDocument> docs2 = imageDetailsSearchRepository.findByDescription("test", PageRequest.of(0, 2));
				//System.out.println("found:::"+docs2.size());
				//docs2.forEach(entity -> System.out.println(entity));

	}

}
