package com.example.myjava.controller;

import com.example.myjava.dto.ArticleForm;
import com.example.myjava.entity.Article;
import com.example.myjava.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j// for logging
public class ArticleController {

    @Autowired// 내장 객체를 이용해 자동 연결
    private ArticleRepository articleRepository;
    @GetMapping("articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());
        Article article=form.toEntity();//Dto to Entity
        log.info(article.toString());
        Article saved = articleRepository.save(article);//give Entity to Repository
        log.info(saved.toString());
        return "";
    }
}
