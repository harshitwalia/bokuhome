package com.boku.backend;

import com.boku.backend.api.controllers.TransferController;
import com.boku.backend.api.controllers.UserController;
import com.boku.backend.api.controllers.WithdrawalController;
import com.boku.backend.api.dtos.TransferRequestDto;
import com.boku.backend.api.dtos.TransferResponseDto;
import com.boku.backend.api.dtos.UserDto;
import com.boku.backend.api.dtos.WithdrawalRequestDto;
import com.boku.backend.api.repositories.TransferRepository;
import com.boku.backend.api.repositories.UserRepository;
import com.boku.backend.api.services.TransferService;
import com.boku.backend.api.services.UserService;
import com.boku.backend.api.services.WithdrawalService;
import com.boku.backend.external.Address;
import com.boku.backend.external.WithdrawalServiceStub;

import java.util.UUID;
public class TestApplication {

    public static void main(String[] args) {
        // Initialize Repositories
        UserRepository userRepository = new UserRepository();
        TransferRepository transferRepository = new TransferRepository();

        // Initialize services
        UserService userService = new UserService(userRepository);
        TransferService transferService = new TransferService(userRepository,transferRepository);
        WithdrawalServiceStub withdrawalServiceStub = new WithdrawalServiceStub();
        WithdrawalService withdrawalService = new WithdrawalService(userRepository,withdrawalServiceStub);

        // Initialize controllers
        UserController userController = new UserController(userService);
        TransferController transferController = new TransferController(transferService);
        WithdrawalController withdrawalController = new WithdrawalController(withdrawalService);

        UserDto user = userController.createUser(new UserDto(UUID.randomUUID(),"John Doe", 100.0)).getBody();
        UserDto recipient = userController.createUser(new UserDto(UUID.randomUUID(),"Doe John", 100.0)).getBody();
        TransferResponseDto transfer = transferController.makeTransfer(new TransferRequestDto(user.getId(), recipient.getId(), 50.0)).getBody();

        System.out.println(userController.getUser(user.getId()).getBody().getBalance());//Prints 50.0
        System.out.println(userController.getUser(recipient.getId()).getBody().getBalance());//Prints 150.0


        withdrawalController.requestWithdrawal(new WithdrawalRequestDto(user.getId(),new Address("abc"),50.0));
        System.out.println(userController.getUser(user.getId()).getBody().getBalance());//Prints 0.0

        // Shutdown any resources or connections if necessary
    }
}

