# record

- modelgen

``` cmd
cd cmdmybatis
java -jar mybatis-generator-core-1.4.1-GG.jar -configfile generatorConfig.xml -overwrite
```

- api:  http://localhost:8081/api/swagger-ui.html#/

- mybatis: Before generating the entity, please delete the corresponding XML files.

- swagger: “Bearer ”+token

- test account:

    | email             |   login   | password |
    |:------------------|:---------:|---------:|
    | admin@admin.admin | testAdmin |    !2e4S |
    | user@user.user    | testUser1 |    12345 |
    | test@163.com      |   test    |   123456 |
