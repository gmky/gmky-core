INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (1, NOW(), 'system', NOW(), 'system', 'view', 'permission',
                                           'View permission');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (2, NOW(), 'system', NOW(), 'system', 'edit', 'permission',
                                           'Edit permission');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (3, NOW(), 'system', NOW(), 'system', 'delete', 'permission',
                                           'Delete permission');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (4, NOW(), 'system', NOW(), 'system', 'approve', 'permission',
                                           'Approve permission');

INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (5, NOW(), 'system', NOW(), 'system', 'view', 'permissionset',
                                           'View permission set');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (6, NOW(), 'system', NOW(), 'system', 'edit', 'permissionset',
                                           'Edit permission set');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (7, NOW(), 'system', NOW(), 'system', 'delete', 'permissionset',
                                           'Delete permission set');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (8, NOW(), 'system', NOW(), 'system', 'approve', 'permissionset',
                                           'Approve permission set');

INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (9, NOW(), 'system', NOW(), 'system', 'view', 'role', 'View role');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (10, NOW(), 'system', NOW(), 'system', 'edit', 'role', 'Edit role');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (11, NOW(), 'system', NOW(), 'system', 'delete', 'role', 'Delete role');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (12, NOW(), 'system', NOW(), 'system', 'approve', 'role', 'Approve role');

INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (13, NOW(), 'system', NOW(), 'system', 'view', 'profile', 'View profile');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (14, NOW(), 'system', NOW(), 'system', 'edit', 'profile', 'Edit profile');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (15, NOW(), 'system', NOW(), 'system', 'delete', 'profile', 'Delete profile');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (16, NOW(), 'system', NOW(), 'system', 'approve', 'profile',
                                           'Approve profile');

INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (17, NOW(), 'system', NOW(), 'system', 'view', 'user', 'View user');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (18, NOW(), 'system', NOW(), 'system', 'edit', 'user', 'Edit user');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (19, NOW(), 'system', NOW(), 'system', 'delete', 'user', 'Delete user');
INSERT INTO permission(id, created_at, created_by, updated_at, updated_by, permission_code, resource_code,
                       description) value (20, NOW(), 'system', NOW(), 'system', 'approve', 'user',
                                           'Approve user');

# Create permission set
INSERT INTO permission_set(id, created_at, created_by, updated_at, updated_by, name, ps_type, description) VALUE (1,
                                                                                                                  NOW(),
                                                                                                                  'system',
                                                                                                                  NOW(),
                                                                                                                  'system',
                                                                                                                  'PermissionFullAccess',
                                                                                                                  'TEMPLATE',
                                                                                                                  'Permission full access');
INSERT INTO permission_set(id, created_at, created_by, updated_at, updated_by, name, ps_type, description) VALUE (2,
                                                                                                                  NOW(),
                                                                                                                  'system',
                                                                                                                  NOW(),
                                                                                                                  'system',
                                                                                                                  'PermissionSetFullAccess',
                                                                                                                  'TEMPLATE',
                                                                                                                  'Permission set full access');
INSERT INTO permission_set(id, created_at, created_by, updated_at, updated_by, name, ps_type, description) VALUE (3,
                                                                                                                  NOW(),
                                                                                                                  'system',
                                                                                                                  NOW(),
                                                                                                                  'system',
                                                                                                                  'RoleFullAccess',
                                                                                                                  'TEMPLATE',
                                                                                                                  'Role full access');
INSERT INTO permission_set(id, created_at, created_by, updated_at, updated_by, name, ps_type, description) VALUE (4,
                                                                                                                  NOW(),
                                                                                                                  'system',
                                                                                                                  NOW(),
                                                                                                                  'system',
                                                                                                                  'ProfileFullAccess',
                                                                                                                  'TEMPLATE',
                                                                                                                  'Profile full access');

INSERT INTO permission_set(id, created_at, created_by, updated_at, updated_by, name, ps_type, description) VALUE (5,
                                                                                                                  NOW(),
                                                                                                                  'system',
                                                                                                                  NOW(),
                                                                                                                  'system',
                                                                                                                  'UserFullAccess',
                                                                                                                  'TEMPLATE',
                                                                                                                  'User full access');

# Link permission with permission set
INSERT INTO ps_item(permission_id, ps_id) VALUE (1, 1);
INSERT INTO ps_item(permission_id, ps_id) VALUE (2, 1);
INSERT INTO ps_item(permission_id, ps_id) VALUE (3, 1);
INSERT INTO ps_item(permission_id, ps_id) VALUE (4, 1);

INSERT INTO ps_item(permission_id, ps_id) VALUE (5, 2);
INSERT INTO ps_item(permission_id, ps_id) VALUE (6, 2);
INSERT INTO ps_item(permission_id, ps_id) VALUE (7, 2);
INSERT INTO ps_item(permission_id, ps_id) VALUE (8, 2);

INSERT INTO ps_item(permission_id, ps_id) VALUE (9, 3);
INSERT INTO ps_item(permission_id, ps_id) VALUE (10, 3);
INSERT INTO ps_item(permission_id, ps_id) VALUE (11, 3);
INSERT INTO ps_item(permission_id, ps_id) VALUE (12, 3);

INSERT INTO ps_item(permission_id, ps_id) VALUE (13, 4);
INSERT INTO ps_item(permission_id, ps_id) VALUE (14, 4);
INSERT INTO ps_item(permission_id, ps_id) VALUE (15, 4);
INSERT INTO ps_item(permission_id, ps_id) VALUE (16, 4);

INSERT INTO ps_item(permission_id, ps_id) VALUE (17, 5);
INSERT INTO ps_item(permission_id, ps_id) VALUE (18, 5);
INSERT INTO ps_item(permission_id, ps_id) VALUE (19, 5);
INSERT INTO ps_item(permission_id, ps_id) VALUE (20, 5);

# Create role
INSERT INTO role(id, created_at, created_by, updated_at, updated_by, name, description, role_type, is_enable,
                 is_default) VALUE (1, NOW(), 'system', NOW(), 'system', 'Administrator', 'Administrator', 'TEMPLATE',
                                    true, false);

# Link permission set with role
INSERT INTO ps_role(ps_id, role_id) VALUE (1, 1);
INSERT INTO ps_role(ps_id, role_id) VALUE (2, 1);
INSERT INTO ps_role(ps_id, role_id) VALUE (3, 1);
INSERT INTO ps_role(ps_id, role_id) VALUE (4, 1);
INSERT INTO ps_role(ps_id, role_id) VALUE (5, 1);