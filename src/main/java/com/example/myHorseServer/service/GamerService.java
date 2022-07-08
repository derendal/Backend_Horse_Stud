package com.example.myHorseServer.service;

import com.example.myHorseServer.dto.gamer.*;
import com.example.myHorseServer.exception.NotFoundException;
import com.example.myHorseServer.model.Gamer;
import com.example.myHorseServer.repository.AuthmeRepository;
import com.example.myHorseServer.repository.GamerRepository;
import com.example.myHorseServer.repository.RoleRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class GamerService implements UserDetailsService {


    @Autowired
    private final GamerRepository gamerRepository;

    @Autowired
     private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthmeRepository authmeRepository;


    public boolean login(String email, String password) {
        Gamer gto = gamerRepository.findByEmail(email).orElseThrow(() -> new NotFoundException());
        return gto.getEmail().equalsIgnoreCase(email) && gto.getPassword().equals(password);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return gamerRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(format("Gamer with emial - %s, not found", email))
                );
    }

    public Iterable<Gamer> findAll(){
        return gamerRepository.findAll();
    }

    public void changePassword(ChangePasswordDto dto) {
        Gamer gamer = gamerRepository.findByEmail(dto.getEmail()).orElseThrow(()-> new NotFoundException());
        if(passwordEncoder.matches(dto.getOldPassword(), gamer.getPassword())) {
            gamer.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            gamerRepository.save(gamer);
        } else
            throw new RuntimeException("Incorrect password");
    }

    public void changeRole(ChangeGamerRole role){
        Gamer gamer = gamerRepository.findByEmail(role.getEmail()).orElseThrow(()-> new NotFoundException());
        Gamer gamerToChange = gamerRepository.findByEmail(role.getEmailGamerToChange()).orElseThrow(()-> new NotFoundException());//sprawdzenie czy nasz gracz któremu chcemy zmienić role istnieje w bazie
        role.setAdminRole(gamer.getRole());
        if(role.getAdminRole().equals("0")){
            if(gamerToChange.getRole().equals(role.getNewRole())){
                throw new RuntimeException("The player has already been given such a role");
            }else {
                gamerToChange.setRole(role.getNewRole());
                gamerRepository.save(gamerToChange);
                System.out.println("Admin powers granted");
            }
        }else throw new RuntimeException("You are not authorized to change the role");
    }

    public void changeInformationGame(ChangeInformationGame informations){
        Gamer gamer = gamerRepository.findByEmail(informations.getEmial()).orElseThrow(()-> new NotFoundException());
        if (!(informations.getLastLogin()).after(informations.getNewlastLogin()) ||
                !(informations.getLastLogout()).after(informations.getNewlastLogout())){
            gamer.setLastLogin(informations.getNewlastLogin());
            gamer.setLastLogout(informations.getNewlastLogout());
            informations.setNewspendTime((int) (informations.getNewlastLogout().getTime() - informations.getNewlastLogin().getTime()));
            gamer.setSpendTime(informations.getSpendTime()+ informations.getNewspendTime());
            gamerRepository.save(gamer);
            System.out.println("Time was saved");
        }else throw new RuntimeException("Login time unchanged");
    }

    public Gamer changePoints(ChangePointsDto points){
        System.out.println("my points!!!!!!!!!!! " +points.getGamer().getEmail());
        Gamer gamer = gamerRepository.findByEmail(points.getGamer().getEmail()).orElseThrow(()-> new NotFoundException());
        if(gamer.getPoints() >= points.getPoints()){
            gamer.setPoints(points.getPoints());
            gamerRepository.save(gamer);
            return gamer;
        }else throw  new RuntimeException("brak wystarczajacej ilości punktów");
    }

    public void changeGamerPosition(ChangeGamerPosition position){
        Gamer gamer = gamerRepository.findByEmail(position.getEmail()).orElseThrow(()-> new NotFoundException());
        if(position.getLastLogout().after(gamer.getLastLogout())){
            gamer.setLoc_x(position.getLoc_x());
            gamer.setLoc_y(position.getLoc_y());
            gamer.setLoc_z(position.getLoc_z());
            gamerRepository.save(gamer);
            System.out.println("A new location has been saved");
        } else throw new RuntimeException("The last login time was not saved correctly");
    }

    public void changeData(ChangeDataDto dto){
        Gamer gamer = gamerRepository.findByEmail(dto.getGamerEmail()).orElseThrow(()-> new NotFoundException());
        if(dto.getNewemail().isEmpty() && dto.getNewnickname().isEmpty()){
                System.out.println("No changes");
                throw new RuntimeException("No changes to email or nickname");
        }else {
            if(!dto.getNewemail().isEmpty()){
            gamer.setEmail(dto.getNewemail());
            gamerRepository.save(gamer);
                System.out.println("Change email");
        }else if(!dto.getNewnickname().isEmpty()){
                gamer.setNickname(dto.getNewnickname());
                gamerRepository.save(gamer);
                System.out.println("Change nickname");
            }else{
                gamer.setEmail(dto.getNewemail());
                gamer.setNickname(dto.getNewnickname());
                gamerRepository.save(gamer);
                System.out.println("Change email and nickname");
            }
        }
    }

    public GamerDeleteResponse delete(String email) {
        Gamer deleted = gamerRepository.findByEmail(email).orElseThrow(()-> new NotFoundException());
        gamerRepository.deleteById(deleted.getGamerId());

            return new GamerDeleteResponse(new GamerDataDto(
                        deleted.getGamerId(),
                        deleted.getAuthmeId(),
                        deleted.getNickname(),
                        deleted.getPoints(),
                        deleted.getLastLogin(),
                        deleted.getLastLogout(),
                        deleted.getSpendTime(),
                        deleted.getRole(),
                        deleted.getLoc_x(),
                        deleted.getLoc_y(),
                        deleted.getLoc_z(),
                        deleted.getEmail(),
                        deleted.getPassword()
                ), "Deleted successfull");
    }

    public GamerRegisterResponse register(GamerRegisterDto gamerRegisterDto){
       Integer authmeId = authmeRepository.findByUsername(gamerRegisterDto.getNickname()).get().getId();
        if(gamerRepository.findByEmail(gamerRegisterDto.getEmail()).isEmpty()){
            Gamer gamer = new Gamer();
            gamer.setAuthmeId(authmeId);
            gamer.setEmail(gamerRegisterDto.getEmail());
            gamer.setPassword(passwordEncoder.encode(gamerRegisterDto.getPassword()));
            gamer.setNickname(gamerRegisterDto.getNickname());
            gamer.setRole(roleRepository.findByRoleName("gracz").orElseThrow(() -> new NotFoundException()));
            gamer.setLastLogin(Date.valueOf(LocalDate.now()));
            gamer.setLastLogout(Date.valueOf(LocalDate.now()));
            gamer.setPoints(0);
            gamer.setSpendTime(0);
            gamer.setLoc_x(0);
            gamer.setLoc_y(0);
            gamer.setLoc_z(0);
            gamer = gamerRepository.save(gamer);
            return new GamerRegisterResponse(new GamerDataDto(
                    gamer.getGamerId(),
                    gamer.getAuthmeId(),
                    gamer.getNickname(),
                    0,
                    null,
                    null,
                    0,
                    null,
                    0,
                    0,
                    0,
                    gamer.getEmail(),
                    gamer.getPassword()
            ),"Registration SUCCESSFULL");
        }
        return new GamerRegisterResponse(null,"Email in use");
    }
}
