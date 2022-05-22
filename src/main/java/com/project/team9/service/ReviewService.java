package com.project.team9.service;

import com.project.team9.dto.ClientReviewDTO;
import com.project.team9.dto.ReviewScoresDTO;
import com.project.team9.model.review.ClientReview;
import com.project.team9.model.user.Client;
import com.project.team9.repo.ClientRepository;
import com.project.team9.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository repository;
    private final ClientRepository clientRepository;

    @Autowired
    public ReviewService(ReviewRepository repository, ClientRepository clientRepository) {
        this.repository = repository;
        this.clientRepository = clientRepository;
    }

    public List<ClientReviewDTO> getReviews(Long resource_id) {
        List<ClientReview> reviews =  repository.findByResourceId(resource_id);
        List<ClientReviewDTO> reviewsDTO = new ArrayList<ClientReviewDTO>();
        for (ClientReview review : reviews){
            reviewsDTO.add(getDTOFromClientReview(review));
        }
        return reviewsDTO;
    }

    private ClientReviewDTO getDTOFromClientReview(ClientReview review){
        Client client = clientRepository.getById(review.getClientId());
        String name = client.getFirstName() + " " + client.getLastName();
        return new ClientReviewDTO(review.getId(), client.getProfileImg().getPath(), name, review.getRating(), review.getText(), client.getId());
    }

    public ReviewScoresDTO getReviewScores(Long resource_id) {
        List<ClientReviewDTO> allReviews = this.getReviews(resource_id);
        HashMap<String, Integer> scores = new HashMap<>();
        scores.put("five", 0);
        scores.put("four", 0);
        scores.put("three", 0);
        scores.put("two", 0);
        scores.put("one", 0);
        for (ClientReviewDTO reviewDTO : allReviews){
            if (reviewDTO.getRating() == 5){
                scores.merge("five", 1, Integer::sum);
            }
            if (reviewDTO.getRating() == 4){
                scores.merge("four", 1, Integer::sum);
            }
            if (reviewDTO.getRating() == 3){
                scores.merge("three", 1, Integer::sum);
            }
            if (reviewDTO.getRating() == 2){
                scores.merge("two", 1, Integer::sum);
            }
            if (reviewDTO.getRating() == 1){
                scores.merge("one", 1, Integer::sum);
            }
        }
        int num = scores.get("five") + scores.get("four") + scores.get("three") + scores.get("two") + scores.get("one");
        double scale = Math.pow(10, 1);
        double fives = (double)scores.get("five") / num * 100;
        fives = Math.round(fives * scale) / scale;
        double fours = (double)scores.get("four") / num * 100;
        fours = Math.round(fours * scale) / scale;
        double threes = (double)scores.get("three") / num * 100;
        threes = Math.round(threes * scale) / scale;
        double twos = (double)scores.get("two") / num * 100;
        twos = Math.round(twos * scale) / scale;
        double ones = (double)scores.get("one") / num * 100;
        ones = Math.round(ones * scale) / scale;
        return new ReviewScoresDTO(fives, fours, threes, twos, ones);
    }
}
