
# Procedure

- 生成实体:生成model、dao、mapper.xml

``` cmd
cd cmdmybatis
java -jar mybatis-generator-core-1.4.1-GG.jar -configfile generatorConfig.xml -overwrite
```

- 添加dto

- 添加service

- 添加
- 添加controller

# UserMapper

``` xml
   <resultMap id="BaseResultMap" type="com.gen.springbootserver.mybatis.model.User">
    <id column="id" jdbcType="BIGINT" property="id" />
    <result column="first_name" jdbcType="VARCHAR" property="firstName" />
    <result column="last_name" jdbcType="VARCHAR" property="lastName" />
    <result column="login" jdbcType="VARCHAR" property="login" />
    <result column="email" jdbcType="VARCHAR" property="email" />
    <result column="age" jdbcType="INTEGER" property="age" />
    <result column="password_hash" jdbcType="VARCHAR" property="passwordHash" />
    <result column="is_deleted" jdbcType="BIT" property="isDeleted" />
    <result column="settings_id" jdbcType="BIGINT" property="settingsId" />
    <result column="address_city" jdbcType="VARCHAR" property="addressCity" />
    <result column="address_street" jdbcType="VARCHAR" property="addressStreet" />
    <result column="address_zip_code" jdbcType="VARCHAR" property="addressZipCode" />
    <result column="address_lat" jdbcType="DOUBLE" property="addressLat" />
    <result column="address_lng" jdbcType="DOUBLE" property="addressLng" />
    <result column="created_at" jdbcType="TIMESTAMP" property="createdAt" />
    <result column="updated_at" jdbcType="TIMESTAMP" property="updatedAt" />
    <association property="settings" column="settings_id" select="getSettings"/>
    <association property="image" column="id" select="getImage"/>
    <collection property="roles" column="id" select="getuserRoles">
    </collection>
  </resultMap>
  <resultMap id="BaseResultMap1" type="com.gen.springbootserver.mybatis.model.Role">
    <id column="id" jdbcType="BIGINT" property="id" />
    <result column="is_default" property="isDefault"/>
    <result column="name" property="name"/>
  </resultMap>


    <select id="getuserRoles" parameterType="java.lang.Long" resultMap="BaseResultMap1">
    SELECT id,is_default ,name FROM role where id in(select role_id from user_roles where user_id=#{id})
  </select>
  <select id="getSettings" parameterType="java.lang.Long" resultType="com.gen.springbootserver.mybatis.model.Settings">
        SELECT id , theme_name themeName FROM settings WHERE id=#{id}
  </select>
  <select id="getImage" parameterType="java.lang.Long" resultType="com.gen.springbootserver.mybatis.model.Image">
        SELECT user_id id , image  FROM image WHERE user_id=#{id}
  </select>
```

# User.java

``` java
 private Set<Role> roles;
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    private Settings settings;

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

```

# sql

``` sql
  CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `login` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `age` int DEFAULT NULL,
  `password_hash` varchar(255) NOT NULL,
  `is_deleted` bit(1) DEFAULT b'0',
  `settings_id` bigint DEFAULT NULL,
  `address_city` varchar(255) DEFAULT NULL,
  `address_street` varchar(255) DEFAULT NULL,
  `address_zip_code` varchar(255) DEFAULT NULL,
  `address_lat` double DEFAULT NULL,
  `address_lng` double DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_default` bit(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  `creater` varchar(45) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `settings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `theme_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `restore_password` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiresIn` timestamp NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `expiresIn_UNIQUE` (`expiresIn`),
  KEY `user_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `image` (
  `user_id` bigint NOT NULL,
  `image` blob,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roles_menus` (
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
  `creater` varchar(45) DEFAULT NULL,
  `creattime` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sort` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `link` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `is_outer_link` bit(1) DEFAULT NULL COMMENT '是否为外链',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限标识',
  `hidden` bit(1) DEFAULT b'0',
  `createtime` datetime DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `is_default` bit(1) DEFAULT b'0',
  `parentname` varchar(255) DEFAULT NULL,
  `is_home` bit(1) DEFAULT b'0',
  `is_group` bit(1) DEFAULT b'0',
  `is_delete` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- insert
INSERT INTO settings (id, theme_name) VALUES
  (1, 'default'), (2, 'cosmic');

INSERT INTO user (id, first_name, last_name, age, login, email, password_hash, is_deleted, settings_id) VALUES
-- Hashed "!2e4S"
  (1, 'Admin', 'Admin', 25, 'testAdmin', 'admin@admin.admin', '$2a$10$S.w55797etFyQugmxjgHAe22yd6fLcVu2ErgcjjBTjNP2zCg2.cqW', false, 1),
-- Hashed "12345"
  (2, 'User1', 'User1', 20, 'testUser1', 'user@user.user', '$2a$10$wgEjvYPY9gnw4cN/Jl6wAePzLHA8A/V23DNuRXvEkJkYDvpxNWm2O', false, 2);

INSERT INTO image (user_id, image) VALUES
-- Bytes of the default picture
(1, null),
(2, null);

INSERT INTO role (id, name, is_default) VALUES
  (1, 'USER', 1), (2, 'ADMIN', 0);

INSERT INTO user_roles (user_id, role_id) VALUES
  (1, 2), (2, 1);


INSERT INTO menu (sort,name, title, icon, type, is_default,hidden,is_outer_link, is_home, is_group, is_delete)
 VALUES ('0', 'gg-sys','sys用户管理',  'person-outline',  'catalog',true, false,false,false,false, false);
 INSERT INTO menu (sort,name, title, icon, type, is_default,hidden,is_outer_link, is_home, is_group, is_delete)
 VALUES ('0', 'gg-menu','sys菜单管理',  'person-outline',  'catalog',true, false,false,false,false, false);
INSERT INTO menu 
 (sort, name, title, icon, link, type,  parentname, is_default,is_outer_link, hidden, is_home, is_group, is_delete)
 VALUES ('0', 'gg-sys-table', '用户列表', 'person-done-outline', '/pages/users/userstable', 'menu','gg-sys', true,false, false, false, false, false);
INSERT INTO menu (sort, name, title, icon, link, type,  parentname, is_default,is_outer_link, hidden, is_home, is_group, is_delete)
 VALUES ('0', 'gg-sys-new', '新用户', 'person-done-outline', '/pages/users/usernew', 'menu','gg-sys', true,false,false, false, false, false);
 INSERT INTO menu (sort, name, title, icon, link, type,  parentname, is_default,is_outer_link, hidden, is_home, is_group, is_delete)
 VALUES ('0', 'gg-menu-table', '权限分配', 'person-done-outline', '/pages/menu/menutable', 'menu','gg-menu', true,false,false, false, false, false);
```
