package com.xover.imagesearch.service;

import com.xover.imagesearch.document.ImageDetailsDocument;
import com.xover.imagesearch.exception.RecordsNotFoundException;
import com.xover.imagesearch.repository.ImageDetailsSearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImageDetailsService Unit Tests")
class ImageDetailsServiceImplTest {

    @Mock
    private ImageDetailsSearchRepository imageDetailsSearchRepository;

    @InjectMocks
    private ImageDetailsServiceImpl imageDetailsService;

    private List<ImageDetailsDocument> docs;

    @BeforeEach()
    public void setUp() {
        docs = new ArrayList<>();
        ImageDetailsDocument imageDetailsDocument = new ImageDetailsDocument();
        imageDetailsDocument.setFileType("png");
        imageDetailsDocument.setDescription("test");
        imageDetailsDocument.setFileSize(1000l);
        docs.add(imageDetailsDocument);
    }
    
    @Test
    @DisplayName("should call proper repository method when all parameters given.")
    public void shouldCallProperRepositoryMethodWhenAllParamsGiven() {
        given(imageDetailsSearchRepository.findByDescriptionAndFileTypeAndFileSizeAllIgnoreCase(any(String.class), any(String.class), any(Long.class), any(Pageable.class))).willReturn(docs);

        final List<ImageDetailsDocument> expected = imageDetailsService.search(Optional.of("test"), Optional.of("png"), Optional.of(111l), Pageable.unpaged());

        assertNotNull(expected);
        assertEquals(expected, docs);
        assertEquals(1, expected.size());

        verify(imageDetailsSearchRepository).findByDescriptionAndFileTypeAndFileSizeAllIgnoreCase(any(String.class), any(String.class), any(Long.class), any(Pageable.class));
    }

    @Test
    @DisplayName("should call proper repository method when only description parameter is given.")
    public void shouldCallProperRepositoryMethodWhenOnlyDescriptionParamGiven() {
        given(imageDetailsSearchRepository.findByDescription(any(String.class), any(Pageable.class))).willReturn(docs);

        final List<ImageDetailsDocument> expected = imageDetailsService.search(Optional.of("test"), Optional.empty(), Optional.empty(), Pageable.unpaged());

        assertNotNull(expected);
        assertEquals(expected, docs);
        assertEquals(1, expected.size());

        verify(imageDetailsSearchRepository).findByDescription(any(String.class), any(Pageable.class));
    }

    @Test
    @DisplayName("should call proper repository method when no parameter is given.")
    public void shouldCallProperRepositoryMethodWhenNoParamGiven() {
        Page<ImageDetailsDocument> pagedResponse = new PageImpl(docs);
        given(imageDetailsSearchRepository.findAll(any(Pageable.class))).willReturn(pagedResponse);

        final List<ImageDetailsDocument> expected = imageDetailsService.search(Optional.empty(), Optional.empty(), Optional.empty(), Pageable.unpaged());

        assertNotNull(expected);

        verify(imageDetailsSearchRepository).findAll( any(Pageable.class));
    }

    @Test
    @DisplayName("should throw records not found exception when not data received from ES.")
    public void shouldThrowRecordsNotFoundExceptionWhenNoDataReturnedFromRepository() {
        Page<ImageDetailsDocument> pagedResponse = new PageImpl(new ArrayList());
        given(imageDetailsSearchRepository.findAll(any(Pageable.class))).willReturn(pagedResponse);

        RecordsNotFoundException exception = assertThrows(RecordsNotFoundException.class, () -> {
            imageDetailsService.search(Optional.empty(), Optional.empty(), Optional.empty(), Pageable.unpaged());
        });
    }

    @Test
    @DisplayName("should call proper repository method when only fileType parameter is given.")
    public void shouldCallProperRepositoryMethodWhenOnlyFileTypeParamGiven() {

        given(imageDetailsSearchRepository.findByFileType(any(String.class), any(Pageable.class))).willReturn(docs);

        final List<ImageDetailsDocument> expected = imageDetailsService.search(Optional.empty(), Optional.of("test"), Optional.empty(), Pageable.unpaged());

        assertNotNull(expected);
        assertEquals(expected, docs);
        assertEquals(1, expected.size());

        verify(imageDetailsSearchRepository).findByFileType(any(String.class), any(Pageable.class));
    }

    @Test
    @DisplayName("should call proper repository method when only fileSize parameter is given.")
    public void shouldCallProperRepositoryMethodWhenOnlyFileSizeParamGiven() {

        given(imageDetailsSearchRepository.findByFileSize(any(Long.class), any(Pageable.class))).willReturn(docs);

        final List<ImageDetailsDocument> expected = imageDetailsService.search(Optional.empty(), Optional.empty(), Optional.of(111l), Pageable.unpaged());

        assertNotNull(expected);
        assertEquals(expected, docs);
        assertEquals(1, expected.size());

        verify(imageDetailsSearchRepository).findByFileSize(any(Long.class), any(Pageable.class));
    }

    @Test
    @DisplayName("should call proper repository method when description and fileSize parameter are given.")
    public void shouldCallProperRepositoryMethodWhenDescriptionAndFileSizeParamGiven() {

        given(imageDetailsSearchRepository.findByDescriptionAndFileSizeAllIgnoreCase(any(String.class),any(Long.class), any(Pageable.class))).willReturn(docs);

        final List<ImageDetailsDocument> expected = imageDetailsService.search(Optional.of("test"), Optional.empty(), Optional.of(111l), Pageable.unpaged());

        assertNotNull(expected);
        assertEquals(expected, docs);
        assertEquals(1, expected.size());

        verify(imageDetailsSearchRepository).findByDescriptionAndFileSizeAllIgnoreCase(any(String.class),any(Long.class), any(Pageable.class));
    }

    @Test
    @DisplayName("should call proper repository method when description and fileType parameter are given.")
    public void shouldCallProperRepositoryMethodWhenDescriptionAndFileTypeParamGiven() {

        given(imageDetailsSearchRepository.findByFileTypeAndDescriptionAllIgnoreCase(any(String.class),any(String.class), any(Pageable.class))).willReturn(docs);

        final List<ImageDetailsDocument> expected = imageDetailsService.search(Optional.of("test"), Optional.of("png"), Optional.empty(), Pageable.unpaged());

        assertNotNull(expected);
        assertEquals(expected, docs);
        assertEquals(1, expected.size());

        verify(imageDetailsSearchRepository).findByFileTypeAndDescriptionAllIgnoreCase(any(String.class),any(String.class), any(Pageable.class));
    }

    @Test
    @DisplayName("should call proper repository method when fileType and fileSize parameter are given.")
    public void shouldCallProperRepositoryMethodWhenFileTypeAndFileSizeParamGiven() {

        given(imageDetailsSearchRepository.findByFileTypeAndFileSizeAllIgnoreCase(any(String.class),any(Long.class), any(Pageable.class))).willReturn(docs);

        final List<ImageDetailsDocument> expected = imageDetailsService.search(Optional.empty(), Optional.of("png"), Optional.of(111l), Pageable.unpaged());

        assertNotNull(expected);
        assertEquals(expected, docs);
        assertEquals(1, expected.size());

        verify(imageDetailsSearchRepository).findByFileTypeAndFileSizeAllIgnoreCase(any(String.class),any(Long.class), any(Pageable.class));
    }
}