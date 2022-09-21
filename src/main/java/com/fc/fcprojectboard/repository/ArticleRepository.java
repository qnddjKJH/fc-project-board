package com.fc.fcprojectboard.repository;

import com.fc.fcprojectboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}