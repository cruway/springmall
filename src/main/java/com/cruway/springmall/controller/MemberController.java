package com.cruway.springmall.controller;

import com.cruway.springmall.controller.form.MemberForm;
import com.cruway.springmall.domain.Member;
import com.cruway.springmall.domain.embeded.Address;
import com.cruway.springmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if(result.hasErrors()) {
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = Member.builder()
                .name(form.getName())
                .homeAddress(address)
                .build();

        memberService.join(member);
        return "redirect:/";
    }


    // TODO: 必要なデータだけ使う場合、dtoクラスを作成して渡すこと
    // TODO: 外部APIを作る場合はEntityをそのまま使わないこと。テンプレートエンジンも同時に作る場合は大丈夫
    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}