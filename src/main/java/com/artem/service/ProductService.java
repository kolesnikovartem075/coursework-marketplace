package com.artem.service;

import com.artem.database.entity.ProductCatalog;
import com.artem.database.repository.ProductRepository;
import com.artem.dto.CustomerCreateEditDto;
import com.artem.dto.CustomerReadDto;
import com.artem.dto.ProductCreateEditDto;
import com.artem.dto.ProductReadDto;
import com.artem.mapper.ProductCreateEditMapper;
import com.artem.mapper.ProductReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {


    private final ImageService imageService;
    private final ProductRepository productRepository;
    private final ProductReadMapper productReadMapper = Mappers.getMapper(ProductReadMapper.class);
    private final ProductCreateEditMapper productCreateEditMapper;

    public List<ProductReadDto> findAll() {
        return productRepository.findAll().stream()
                .map(productReadMapper::map)
                .toList();
    }

    public Optional<ProductReadDto> findById(Long id) {
        return productRepository.findById(id)
                .map(productReadMapper::map);
    }

    @Transactional
    public ProductReadDto create(ProductCreateEditDto productDto) {
        return Optional.of(productDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return productCreateEditMapper.map(dto);
                })
                .map(productRepository::save)
                .map(productReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ProductReadDto> update(Long id, ProductCreateEditDto productDto) {
        return productRepository.findById(id)
                .map(entity -> {
                    uploadImage(productDto.getImage());
                    return productCreateEditMapper.map(productDto, entity);
                })
                .map(productRepository::saveAndFlush)
                .map(productReadMapper::map);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public Optional<byte[]> findAvatar(Long id) {
        return productRepository.findById(id)
                .map(ProductCatalog::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::downloadImage);
    }

    @Transactional
    public boolean delete(Long id) {
        return productRepository.findById(id)
                .map(entity -> {
                    productRepository.delete(entity);
                    productRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}