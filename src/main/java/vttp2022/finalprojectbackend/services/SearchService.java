package vttp2022.finalprojectbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.finalprojectbackend.models.Flat;
import vttp2022.finalprojectbackend.models.SearchRequest;
import vttp2022.finalprojectbackend.repositories.SearchRepository;

@Service
public class SearchService {

    @Autowired
    private SearchRepository searchRepo;

    public Optional<List<Flat>> getResultsFromAPI(SearchRequest searchReq) {

        List<Flat> resultList = searchRepo.getResultsFromAPI(searchReq.getQuery(), searchReq.getLimit());

        if (!resultList.isEmpty()) {
            return Optional.of(resultList);
        } else {
            return Optional.empty();
        }
    }

}
