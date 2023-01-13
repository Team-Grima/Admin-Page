package com.example.myjava.controller;

import com.example.myjava.dto.ArticleForm;
import com.example.myjava.entity.Article;
import com.example.myjava.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = "+id);

        //get data from id
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //register data to model
        model.addAttribute("article",articleEntity);

        //show page
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        // get all article
        List<Article> articleEntityList=articleRepository.findAll();
        // article to view
        model.addAttribute("articleList",articleEntityList);
        //view page
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //get data
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //register data to model
        model.addAttribute("article",articleEntity);
        //view page
        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());
        //DTO to entity
        Article articleEntity=form.toEntity();
        log.info(articleEntity.toString());
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // save entity into db
        if(target!=null){
            articleRepository.save(articleEntity);
        }
        //redirect
        return "redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청 확인");

        //get target
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        //delete target
        if(target!=null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","deleted");
        }
        //redirect
        return "redirect:/articles";
    }
}
