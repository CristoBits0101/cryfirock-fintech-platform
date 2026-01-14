package com.cryfirock.auth.service.application;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import com.cryfirock.auth.dto.UserUpdateDto;
import com.cryfirock.auth.entity.Role;
import com.cryfirock.auth.entity.User;
import com.cryfirock.auth.helper.RolesHelper;
import com.cryfirock.auth.mapper.UserMapper;
import com.cryfirock.auth.model.AccountStatus;
import com.cryfirock.auth.repository.JpaUserRepository;

/**
 * 1. Pruebas unitarias para UserServiceImpl.
 * 2. Verifica el correcto funcionamiento de las operaciones CRUD.
 * 3. Utiliza JUnit 5 y Mockito para las pruebas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@ExtendWith(MockitoExtension.class) @SuppressWarnings("unused")
class UserServiceImplTest {
    @Mock
    private JpaUserRepository userRepository;

    @Mock
    private RolesHelper rolesHelper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private Environment environment;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private Role roleUser;

    @BeforeEach
    void setUp() {
        roleUser = new Role("ROLE_USER");

        testUser = new User();
        testUser.setId(1L);
        testUser.setGivenName("Juan");
        testUser.setFamilyName("Pérez");
        testUser.setEmail("juan@test.com");
        testUser.setPhoneNumber("123456789");
        testUser.setUsername("juanperez");
        testUser.setPasswordHash("password123");
        testUser.setDob(LocalDate.of(1990, 1, 1));
        testUser.setAddress("Calle Test 123");
        testUser.setEnabled(AccountStatus.ACTIVE);
        testUser.setAdmin(false);
    }

    @Nested @DisplayName("Tests para save")
    class SaveTests {

        @Test @DisplayName("Debe guardar un usuario correctamente")
        void shouldSaveUserSuccessfully() {
            // Arrange
            when(rolesHelper.assignRoles(any(User.class))).thenReturn(List.of(roleUser));
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            // Act
            User result = userService.save(testUser);

            // Assert
            assertNotNull(result);
            assertEquals("Juan", result.getGivenName());
            verify(rolesHelper).assignRoles(testUser);
            verify(userRepository).save(testUser);
        }

        @Test @DisplayName("Debe codificar la contraseña al guardar")
        void shouldEncodePasswordWhenSaving() {
            // Arrange
            testUser.setPasswordHash("plainPassword");
            when(rolesHelper.assignRoles(any(User.class))).thenReturn(List.of(roleUser));
            when(userRepository.save(any(User.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            // Act
            User result = userService.save(testUser);

            // Assert
            assertNotNull(result.getPasswordHash());
            assertTrue(result.getPasswordHash().startsWith("$2"));
        }

        @Test @DisplayName("No debe re-codificar una contraseña ya codificada")
        void shouldNotReEncodeAlreadyEncodedPassword() {
            // Arrange
            String bcryptHash = "$2a$10$N9qo8uLOickgx2ZMRZoMy.Q3LjNBhGGkCcBv3Wv/NQVXUjKKJfWAW";
            testUser.setPasswordHash(bcryptHash);
            when(rolesHelper.assignRoles(any(User.class))).thenReturn(List.of(roleUser));
            when(userRepository.save(any(User.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            // Act
            User result = userService.save(testUser);

            // Assert
            assertEquals(bcryptHash, result.getPasswordHash());
        }
    }

    @Nested @DisplayName("Tests para findById")
    class FindByIdTests {

        @Test @DisplayName("Debe encontrar usuario por ID")
        void shouldFindUserById() {
            // Arrange
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

            // Act
            Optional<User> result = userService.findById(1L);

            // Assert
            assertTrue(result.isPresent());
            assertEquals("Juan", result.get().getGivenName());
        }

        @Test @DisplayName("Debe retornar vacío si el usuario no existe")
        void shouldReturnEmptyWhenUserNotFound() {
            // Arrange
            when(userRepository.findById(999L)).thenReturn(Optional.empty());

            // Act
            Optional<User> result = userService.findById(999L);

            // Assert
            assertTrue(result.isEmpty());
        }
    }

    @Nested @DisplayName("Tests para findAll")
    class FindAllTests {

        @Test @DisplayName("Debe retornar todos los usuarios con puerto")
        void shouldReturnAllUsersWithPort() {
            // Arrange
            when(userRepository.findAll()).thenReturn(List.of(testUser));
            when(environment.getProperty("local.server.port")).thenReturn("8080");

            // Act
            List<User> result = userService.findAll();

            // Assert
            assertEquals(1, result.size());
            assertEquals(8080, result.get(0).getPort());
        }

        @Test @DisplayName("Debe retornar lista vacía si no hay usuarios")
        void shouldReturnEmptyListWhenNoUsers() {
            // Arrange
            when(userRepository.findAll()).thenReturn(List.of());

            // Act
            List<User> result = userService.findAll();

            // Assert
            assertTrue(result.isEmpty());
        }
    }

    @Nested @DisplayName("Tests para update con User")
    class UpdateWithUserTests {

        @Test @DisplayName("Debe actualizar un usuario existente")
        void shouldUpdateExistingUser() {
            // Arrange
            User updatedUser = new User();
            updatedUser.setGivenName("Carlos");
            updatedUser.setFamilyName("García");
            updatedUser.setEmail("carlos@test.com");
            updatedUser.setPhoneNumber("987654321");
            updatedUser.setUsername("carlosgarcia");
            updatedUser.setDob(LocalDate.of(1985, 5, 15));
            updatedUser.setAddress("Avenida Nueva 456");
            updatedUser.setEnabled(AccountStatus.ACTIVE);

            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(rolesHelper.assignRoles(any(User.class))).thenReturn(List.of(roleUser));
            when(userRepository.save(any(User.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            // Act
            Optional<User> result = userService.update(1L, updatedUser);

            // Assert
            assertTrue(result.isPresent());
            assertEquals("Carlos", result.get().getGivenName());
            assertEquals("García", result.get().getFamilyName());
        }

        @Test @DisplayName("Debe retornar vacío si el usuario no existe")
        void shouldReturnEmptyWhenUserNotExistsForUpdate() {
            // Arrange
            when(userRepository.findById(999L)).thenReturn(Optional.empty());

            // Act
            Optional<User> result = userService.update(999L, testUser);

            // Assert
            assertTrue(result.isEmpty());
            verify(userRepository, never()).save(any());
        }

        @Test @DisplayName("Debe actualizar contraseña si se proporciona")
        void shouldUpdatePasswordWhenProvided() {
            // Arrange
            User updatedUser = new User();
            updatedUser.setGivenName("Juan");
            updatedUser.setFamilyName("Pérez");
            updatedUser.setEmail("juan@test.com");
            updatedUser.setPhoneNumber("123456789");
            updatedUser.setUsername("juanperez");
            updatedUser.setPasswordHash("newPassword123");
            updatedUser.setDob(LocalDate.of(1990, 1, 1));
            updatedUser.setAddress("Calle Test 123");
            updatedUser.setEnabled(AccountStatus.ACTIVE);

            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(rolesHelper.assignRoles(any(User.class))).thenReturn(List.of(roleUser));
            when(userRepository.save(any(User.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            // Act
            Optional<User> result = userService.update(1L, updatedUser);

            // Assert
            assertTrue(result.isPresent());
            assertTrue(result.get().getPasswordHash().startsWith("$2"));
        }
    }

    @Nested @DisplayName("Tests para update con DTO")
    class UpdateWithDtoTests {

        @Test @DisplayName("Debe actualizar usuario con DTO")
        void shouldUpdateUserWithDto() {
            // Arrange
            UserUpdateDto dto = new UserUpdateDto(
                    "Carlos", "García", LocalDate.of(1985, 5, 15),
                    "carlos@test.com", "987654321", "Avenida Nueva 456",
                    "carlosgarcia", null, false, AccountStatus.ACTIVE);

            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(rolesHelper.assignRoles(any(User.class))).thenReturn(List.of(roleUser));
            when(userRepository.save(any(User.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));
            doNothing().when(userMapper).update(any(User.class), any(UserUpdateDto.class));

            // Act
            Optional<User> result = userService.update(1L, dto);

            // Assert
            assertTrue(result.isPresent());
            verify(userMapper).update(any(User.class), eq(dto));
        }

        @Test @DisplayName("Debe actualizar contraseña con DTO si se proporciona")
        void shouldUpdatePasswordWithDtoWhenProvided() {
            // Arrange
            UserUpdateDto dto = new UserUpdateDto(
                    "Carlos", "García", LocalDate.of(1985, 5, 15),
                    "carlos@test.com", "987654321", "Avenida Nueva 456",
                    "carlosgarcia", "newPassword123", false, AccountStatus.ACTIVE);

            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(rolesHelper.assignRoles(any(User.class))).thenReturn(List.of(roleUser));
            when(userRepository.save(any(User.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));
            doNothing().when(userMapper).update(any(User.class), any(UserUpdateDto.class));

            // Act
            Optional<User> result = userService.update(1L, dto);

            // Assert
            assertTrue(result.isPresent());
            assertTrue(result.get().getPasswordHash().startsWith("$2"));
        }
    }

    @Nested @DisplayName("Tests para deleteById")
    class DeleteByIdTests {

        @Test @DisplayName("Debe eliminar usuario existente")
        void shouldDeleteExistingUser() {
            // Arrange
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            doNothing().when(userRepository).delete(testUser);

            // Act
            Optional<User> result = userService.deleteById(1L);

            // Assert
            assertTrue(result.isPresent());
            assertEquals("Juan", result.get().getGivenName());
            verify(userRepository).delete(testUser);
        }

        @Test @DisplayName("Debe retornar vacío si el usuario no existe")
        void shouldReturnEmptyWhenUserNotExistsForDelete() {
            // Arrange
            when(userRepository.findById(999L)).thenReturn(Optional.empty());

            // Act
            Optional<User> result = userService.deleteById(999L);

            // Assert
            assertTrue(result.isEmpty());
            verify(userRepository, never()).delete(any());
        }
    }
}
