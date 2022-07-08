package com.example.myHorseServer.service;
import com.example.myHorseServer.dto.LoginDto;
import com.example.myHorseServer.dto.gamer.*;
import com.example.myHorseServer.model.Gamer;
import com.example.myHorseServer.model.Role;
import com.example.myHorseServer.repository.AuthmeRepository;
import com.example.myHorseServer.repository.GamerRepository;
import com.example.myHorseServer.repository.RoleRepository;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

public class ServiceTest {

    @Mock
    private GamerRepository gamerRepository = Mockito.mock(GamerRepository.class);

    @Mock
    private PasswordEncoder paswordEncoder = Mockito.mock(PasswordEncoder.class);

    @Mock
    private RoleRepository roleRepository = Mockito.mock(RoleRepository.class);

    @Mock
    private AuthmeRepository authmeRepository;


    private GamerService gamerService;

    @Test
    public void gamerRegistrationTest(){
        GamerRegisterDto gamer = new GamerRegisterDto();
    }
    @BeforeEach
    void initGamerCase(){
        gamerService = new GamerService(gamerRepository,roleRepository,paswordEncoder,authmeRepository);
    }

    @Test
    public void savedGamerHasRegistrationDate(){
        GamerRegisterDto gamerRegisterDtoTest = new GamerRegisterDto();
        gamerRegisterDtoTest.setEmail("test@gamer.pl");
        gamerRegisterDtoTest.setNickname("gamertest");
        gamerRegisterDtoTest.setPassword("gamertestpassword");
        when(gamerRepository.findByEmail("test@gamer.pl")).thenReturn(Optional.empty());
        when(gamerRepository.save(any(Gamer.class))).then(returnsFirstArg());
        when(paswordEncoder.encode(any(CharSequence.class))).thenReturn("GamerTestPassword");
        when(roleRepository.findByRoleName("gracz")).thenReturn(Optional.of(new Role(1, "role")));
        GamerRegisterResponse savedGamer = gamerService.register(gamerRegisterDtoTest);
       assertTrue(savedGamer.equals(new GamerRegisterResponse(new GamerDataDto(
               null,
               null,
               "gamertest",
               0,
               null,
               null,
               0,
               null,
               0,
               0,
               0,
               "test@gamer.pl",
               "GamerTestPassword"
       ),"Registration SUCCESSFULL")));
        }

        @Test
    public void deleteTest(){
            GamerDeleteDto gamerDeleteDtoTest = new GamerDeleteDto();
            gamerDeleteDtoTest.setEmail("test@gamer.pl");
            when(gamerRepository.findByEmail("test@gamer.pl")).thenReturn(Optional.empty());
            GamerDeleteResponse deleteGamer = gamerService.delete(gamerDeleteDtoTest.getEmail());
            assertTrue(deleteGamer.getGamer().getEmail().isEmpty());
        }
}
