package com.marketplace.product.products_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketplace.product.products_service.dto.ReviewRequest;
import com.marketplace.product.products_service.dto.ReviewResponse;
import com.marketplace.product.products_service.models.Product;
import com.marketplace.product.products_service.models.Review;
import com.marketplace.product.products_service.repositories.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
  
  private final ReviewRepository repository;
  
  @Autowired
  private final ProductService productService;

  public List<ReviewResponse> getAll() {
    return repository.findAll().stream().map(this::mapToDTO).toList();
  }

  public ReviewResponse add(ReviewRequest reviewReq) throws Exception {
    Product product = productService.findOneProduct(reviewReq.productId);

    Review review = new Review();
    review.setProduct(product);
    review.setMessage(reviewReq.message);
    review.setReviewerId(reviewReq.reviewerId);;
    return mapToDTO(repository.save(review));
  }

  public ReviewResponse findOne(int id) {
    return repository.findById(id).stream().map(this::mapToDTO).toList().get(0);
  }

  public String delete(int id) {
    repository.deleteById(id);
    return "Review deleted successfully";
  }

  private ReviewResponse mapToDTO(Review review) {
    return ReviewResponse.builder()
            .id(review.id)
            .message(review.message)
            .product(review.product)
            .reviewerId(review.reviewerId)
            .created_at(review.created_at)
            .updated_at(review.updated_at)
            .build();
  }
}
