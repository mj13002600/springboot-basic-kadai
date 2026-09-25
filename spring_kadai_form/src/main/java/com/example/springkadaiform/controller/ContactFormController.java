package com.example.springkadaiform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springkadaiform.form.ContactForm;

@Controller
public class ContactFormController {

    // フォーム画面の表示（/form）
    @GetMapping("/form")
    public String showForm(Model model) {
        // リダイレクト時にモデルへ設定されていない（初回アクセス）場合のみ、新しいフォームを渡す
        if (!model.containsAttribute("contactForm")) {
            model.addAttribute("contactForm", new ContactForm());
        }
        return "contactFormView";
    }

    // 確認画面の表示処理（/confirm）
    @PostMapping("/confirm")
    public String confirmForm(
            @Validated ContactForm contactForm, 
            BindingResult result, 
            RedirectAttributes redirectAttributes,
            Model model) {

        // バリデーションエラーがある場合、/form へリダイレクトする
        if (result.hasErrors()) {
            // エラー情報と入力された値をフラッシュ属性（リダイレクト後まで保持されるデータ）に格納
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contactForm", result);
            redirectAttributes.addFlashAttribute("contactForm", contactForm);
            
            return "redirect:/form";
        }

        // エラーがない場合は確認画面を表示（/confirm）
        model.addAttribute("contactForm", contactForm);
        return "confirmView";
    }
}