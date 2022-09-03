package com.github.fabriciolfj.accountservice.controller;

import com.github.fabriciolfj.accountservice.business.usecase.AccountCreate;
import com.github.fabriciolfj.accountservice.business.usecase.GetAccount;
import com.github.fabriciolfj.accountservice.business.usecase.GetAllAccount;
import com.github.fabriciolfj.accountservice.controller.converter.AccountDtoConverter;
import com.github.fabriciolfj.accountservice.controller.dto.AccountRequest;
import com.github.fabriciolfj.accountservice.controller.dto.AccountResponse;
import com.github.fabriciolfj.accountservice.provider.AccountConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountCreate create;
    private final GetAccount getAccount;
    private final GetAllAccount getAllAccount;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody final AccountRequest request) {
        create.execute(AccountDtoConverter.toEntity(request));
    }

    @GetMapping("/{code}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AccountResponse findAccount(@PathVariable("code") final String code) {
        var entity = getAccount.execute(code);

        return AccountDtoConverter.toResponse(entity);
    }

    @GetMapping("/{customer}/{dateStart}/{dateEnd}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<AccountResponse> listAccount(@PathVariable("customer") final String customer, @PathVariable("dateStart") final String dateStart,
                                            @PathVariable("dateEnd") final String dateEnd) {
        var accounts = getAllAccount.execute(dateStart, dateEnd, customer);

        return accounts.stream().map(AccountDtoConverter::toResponse).collect(Collectors.toList());
    }
}
