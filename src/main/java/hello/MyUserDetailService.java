package hello;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


/**
 * Этот класс нужен чтобы загружать с базы данных профили пользователей и роли
 * и использовать их для авторизации в спринг секьюрити.
 */
@Component
public class MyUserDetailService implements UserDetailsService {


    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepository.findDistinctByName(username);
        if (account == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }else{
            UserDetails details = User.builder()   //Юзер имплементит детали так что все в порядке
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .username(account.getName())                 //логин
                    .password(account.getEncodedPassword())      //пароль
                    .authorities(getAuthorities(account))        //полномочия, берем из ролей
                    .build();
            return details;
        }
    }

    /**
     *
     * @param account класс аккаунта, связан с ролью многие ко многим
     * @return Set с набором ролей, обернутых в SimpleGrantedAuthority
     */
    private Set<GrantedAuthority> getAuthorities(Account account){
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : account.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;

    }


}
