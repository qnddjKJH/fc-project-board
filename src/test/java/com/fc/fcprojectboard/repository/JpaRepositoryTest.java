package com.fc.fcprojectboard.repository;

import com.fc.fcprojectboard.config.jpa.JpaConfig;
import com.fc.fcprojectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/*
 * test data 를 대량으로 생성해주는 서비스가 존재
 * https://www.mockaroo.com
 */
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private ArticleRepository articleRepository;
    private ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    public void givenTestData_whenSelecting_thenWorksFine() throws Exception {
        // given

        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertThat(articles).isNotNull().hasSize(123);
    }

    @DisplayName("insert 테스트")
    @Test
    public void givenTestData_whenInserting_thenWorksFine() throws Exception {
        // given
        long previousCount = articleRepository.count();

        // when
        Article savedArticle = articleRepository.save(Article.of("new article", "new content", "#spring"));

        // then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    public void givenTestData_whenUpdating_thenWorksFine() throws Exception {
        // given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#SpringBoot";
        article.setHashtag(updatedHashtag);

        // when
        Article savedArticle = articleRepository.saveAndFlush(article);
//        articleRepository.flush();

        // then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    public void givenTestData_whenDeleting_thenWorksFine() throws Exception {
        // given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        // when
        articleRepository.delete(article);

        // then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }
}