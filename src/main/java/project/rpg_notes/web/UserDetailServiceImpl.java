package project.rpg_notes.web;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import project.rpg_notes.domain.AppUser;
import project.rpg_notes.domain.AppUserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService  {
	
	//@Autowired
	AppUserRepository repository;
	
	// Constructor Injection
	public UserDetailServiceImpl(AppUserRepository appUserRepository) {
		this.repository = appUserRepository; 
	}
	


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {   
    	AppUser curruser = repository.findByUsername(username);
        UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPasswordHash(), 
        		AuthorityUtils.createAuthorityList(curruser.getRole()));
        return user;
    }   
} 
