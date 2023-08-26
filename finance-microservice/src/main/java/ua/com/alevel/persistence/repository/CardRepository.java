package ua.com.alevel.persistence.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.Card;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.List;

@Repository
public interface CardRepository extends BaseRepository<Card> {

    Card findCardByNumber(String number);

    List<Card> findCardsByOwnerId(String ownerId);
}
