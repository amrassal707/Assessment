package PassBoard.Assessment;

import PassBoard.Assessment.DAO.UserRepo;
import PassBoard.Assessment.DTOs.UserDTO;
import PassBoard.Assessment.Models.User;
import PassBoard.Assessment.Services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepo userRepo;

	@InjectMocks
	private  UserService userService;

	@Test
	void testGetAll() {
		// Mock data
		User user1 = new User();
		user1.setName("Ahmed");
		user1.setBalance(100L);

		User user2 = new User();
		user2.setName("Ibrahim");
		user2.setBalance(150L);

		when(userRepo.findAll()).thenReturn(Arrays.asList(user1, user2));

		// Test
		List<UserDTO> result = userService.getAll();

		// Verify
		assertEquals(2, result.size());
		assertEquals("Ahmed", result.get(0).getName());
		assertEquals(100L, result.get(0).getBalance());
		assertEquals("Ibrahim", result.get(1).getName());
		assertEquals(150L, result.get(1).getBalance());
	}

	@Test
	void testCreateUser_Success() {
		// Mock data
		UserDTO userDTO = new UserDTO();
		userDTO.setName("NewUser");
		userDTO.setBalance(200L);

		when(userRepo.findAll()).thenReturn(List.of());

		// Test
		String result = userService.createUser(userDTO);

		// Verify
		verify(userRepo, times(1)).save(any(User.class));
		assertEquals("Saved", result);
	}

	@Test
	void testCreateUser_AlreadyExists() {
		// Mock data
		UserDTO userDTO = new UserDTO();
		userDTO.setName("ExistingUser");
		userDTO.setBalance(200L);

		User existingUser = new User();
		existingUser.setName("ExistingUser");

		when(userRepo.findAll()).thenReturn(List.of(existingUser));

		// Test
		String result = userService.createUser(userDTO);

		// Verify
		verify(userRepo, never()).save(any(User.class));
		assertEquals("already exists with that name", result);
	}

	
}
