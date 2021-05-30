package spring.com.service;

import spring.com.model.Role;

import java.util.List;

public interface RoleService {

    Role getRoleById(int id);

    List<Role> getAllRoles();

    void deleteRole(int id);

    Role saveRole(Role role);

    Role updateRole(Role role);

    Role getRoleByName(String roleName);

}
